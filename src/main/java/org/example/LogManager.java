package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogManager {

    private List<Log> logs;
    private List<String> incorrectLogs;

    LogManager() {
        this.logs = new ArrayList<>();
        this.incorrectLogs = new ArrayList<>();
    }

    public void loadLogs(String fileName) {
        File file = new File(fileName);

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] splited = formatLine(line);

                this.logs.add(new Log(splited[0], splited[1], splited[2]));

                // TODO Implement the validation of the log
                
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String[] formatLine(String line) {
        String[] res = new String[3];
        Pattern pattern = Pattern.compile("\\[(\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2})]\\s*\\[(\\w+)\\]\\s*(.*)");
        Matcher matcher = pattern.matcher(line);
        if (matcher.matches()) {
            res[0] = matcher.group(1);
            res[1] = matcher.group(2);
            res[2] = matcher.group(3);
        }

        return res;
    }

    public void showLogs() {
        if (this.logs.isEmpty()) {
            System.out.println("No logs at the moment");
            return;
        }
        for (Log log : this.logs) {
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
