package org.marco;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import org.marco.exceptions.InvalidFileException;
import org.marco.interfaces.ExportJSON;
import org.marco.interfaces.ExportXML;
import org.marco.utils.Wrapper;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class to manage all the logic of the logs
 * @version 1.0
 * @since 1.0
 * @see Log
 * @see LogLevel
 */
public class LogManager implements ExportXML, ExportJSON {

    private final List<Log> logs;
    private final List<Log> currentLogs;
    private int incorrectLines;

    LogManager() {
        this.logs = new ArrayList<>();
        this.currentLogs = new ArrayList<>();
        this.incorrectLines = 0;
    }

    /**
     * Method to export the current logs to JSON format using GSON
     * @param fileName Name of the file to export the logs
     */
    @Override
    public void exportJSON(String fileName) {
        // Use this initialization instead new Gson() to pretty print the json
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        File file = new File(fileName);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write(gson.toJson(this.currentLogs));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Export the Logs to XML using JAXB
     * @param fileName Name of the file
     */
    @Override
    public void exportXML(String fileName) {
        try {
            JAXBContext context = JAXBContext.newInstance(Wrapper.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(new Wrapper(this.currentLogs), new File(fileName));
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method to filter the logs by the LogLevel
     * @param filterOption LogLevel Object to filter
     */
    public void filter(LogLevel filterOption) {
        this.currentLogs.clear();

        // If filter is null, do not filter the logs
        if (filterOption == null) {
            for (Log log : this.logs) {
                this.currentLogs.add(log);
                System.out.println(log.pretty());
            }
            return;
        }

        // Else filter the logs by the level
        for (Log log : this.logs) {
            if (log.getLevel().equals(filterOption)) {
                this.currentLogs.add(log);
                System.out.println(log.pretty());
            }
        }
    }

    /**
     * Method to load the logs from the file given by the user
     * @param fileName Name of the file
     */
    public void loadLogs(String fileName) {
        File file = new File(fileName);

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] splited = formatLine(line);

                if (validateLog(splited)) {
                    Log log = new Log(splited[0], splited[1], splited[2]);
                    this.logs.add(log);
                    this.currentLogs.add(log);
                } else {
                    incorrectLines++;
                }

                // TODO Implement the validation of the log
                
            }
        } catch (IOException e) {
            throw new InvalidFileException();
        }

        System.out.format("No se han podido cargar %d lineas debido a que estan mal formadas.\n", this.incorrectLines);
    }

    /**
     * Method to format the log line with Patern<br>
     * --- REGEX EXPLANATION ---<br>
     * We want to dispatch this line format from the log file:<br>
     * [YYYY-MM-dd HH:mm:ss] [LEVEL] Message<br>
     * So, the first part get the timestamp with:<br>
     * - \\d{N}: matches exactly N digits<br>
     * To get the leve just use:<br>
     * - \\w+: that matches one or N word characters<br>
     * And finally, to get the message just matches all the content with:<br>
     * - (.*)<br>
     * Between the timestamp, the log level and the message can be N spaces
     * that can be removed with:<br>
     * - \\s+: matches zero or N spaces<br>
     * @param line Line read form the file
     * @return Vector of strings formated
     */
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

    /**
     * Validate if the string given can be a valid Log
     * @param log Parts of the log candidate
     * @return if is a valid log candidate
     */
    private boolean validateLog(String[] log) {

        // If the log level does not exist
        if (!LogLevel.exists(log[1])) return false;

        // Validate the date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        try {
            LocalDateTime.parse(log[0], formatter);
        } catch (DateTimeParseException e) {
            return false; // Return false if parsing fails
        }

        return true;
    }

    public List<Log> getLogs() {
        return logs;
    }

    @Override
    public String toString() {
        return "LogManager{" +
                "logs=" + logs +
                '}';
    }
}
