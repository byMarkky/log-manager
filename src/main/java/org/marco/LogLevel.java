package org.marco;

public enum LogLevel {
    INFO,
    WARNING,
    ERROR;

    public static boolean exists(String level) {
        for (LogLevel logLeve : LogLevel.values()) {
            if (logLeve.name().equals(level)) return true;
        }
        return false;
    }
}
