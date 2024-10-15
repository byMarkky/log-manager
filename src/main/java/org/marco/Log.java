package org.marco;

/**
 * Method to encapsulate the log data
 * @version 1.0
 * @see LogManager
 * @see LogLevel
 */
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

    public String getDate() {
        return date;
    }

    public LogLevel getLevel() {
        return level;
    }

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
