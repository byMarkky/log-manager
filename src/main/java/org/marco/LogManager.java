package org.marco;

import org.marco.exceptions.InvalidFileException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogManager {

    private List<Log> logs;
    private List<Log> currentLogs;
    private List<String> incorrectLogs;

    LogManager() {
        this.logs = new ArrayList<>();
        this.currentLogs = new ArrayList<>();
        this.incorrectLogs = new ArrayList<>();
    }

    public void filter(LogLevel filterOption) {
        this.currentLogs.clear();
        if (filterOption == null) {
            for (Log log : this.logs) {
                this.currentLogs.add(log);
                System.out.println(log.pretty());
            }
        } else {
            for (Log log : this.logs) {
                if (log.getLevel().equals(filterOption)) {
                    this.currentLogs.add(log);
                    System.out.println(log.pretty());
                }
            }
        }
    }

    public void loadLogs(String fileName) {
        File file = new File(fileName);

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] splited = formatLine(line);

                Log log = new Log(splited[0], splited[1], splited[2]);
                this.logs.add(log);
                this.currentLogs.add(log);

                // TODO Implement the validation of the log
                
            }
        } catch (IOException e) {
            throw new InvalidFileException();
        }
    }

    private String[] formatLine(String line) {
        String[] res = new String[3];
        String regex = "\\[(\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2})]\\s*\\[(\\w+)\\]\\s*(.*)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);
        if (matcher.matches()) {
            res[0] = matcher.group(1);
            res[1] = matcher.group(2);
            res[2] = matcher.group(3);
        }

        return res;
    }

    public void showLogs() {
        if (this.currentLogs.isEmpty()) {
            System.out.println("No logs at the moment");
            return;
        }
        for (Log log : this.currentLogs) {
            System.out.println(log.pretty());
        }
    }

    private boolean validateLog(String[] log) {
        // TODO Finish the implementation (re-do)
        boolean isCorrect = false;

        // Check date

        // Check level
        String level = log[2];
        // Check msg
        String msg = log[3];
        return Objects.equals(msg, "") || LogLevel.exists(level);
    }

    public List<Log> getLogs() {
        return logs;
    }

    public void setLogs(List<Log> logs) {
        this.logs = logs;
    }

    @Override
    public String toString() {
        return "LogManager{" +
                "logs=" + logs +
                '}';
    }
}
