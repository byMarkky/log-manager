package org.marco;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * Method to encapsulate the log data
 * @version 1.0
 * @see LogManager
 * @see LogLevel
 */
@XmlRootElement
public class Log {
    private final String date;
    private final LogLevel level;
    private final String msg;

    public Log(String date, LogLevel level, String msg) {
        this.date = date;
        this.level = level;
        this.msg = msg;
    }

    public Log(String date, String level, String msg) {
        this.date = date;
        this.level = LogLevel.valueOf(level);
        this.msg = msg;
    }

    public Log(){
        this.date = null;
        this.level = null;
        this.msg = null;
    }

    @XmlElement(name = "fechaHora")
    public String getDate() {
        return date;
    }
    @XmlElement(name = "nivel")
    public LogLevel getLevel() {
        return level;
    }
    @XmlElement(name = "mensaje")
    public String getMsg() {
        return msg;
    }

    /**
     * Method to show the string in a pretty form
     * @return pretty string :)
     */
    public String pretty() {
        return String.format("[%s] [%s] %s", this.date, this.level.toString(), this.msg);
    }

    @Override
    public String toString() {
        return "Log{" +
                "date='" + date + '\'' +
                ", level=" + level +
                ", msg='" + msg + '\'' +
                '}';
    }
}
