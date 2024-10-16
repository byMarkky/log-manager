package org.marco.utils;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import org.marco.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is only to export the logs in an XML file
 */
@XmlRootElement(name = "logAplicacion")
public class Wrapper {
    private List<Log> logs = new ArrayList<>();

    public Wrapper(List<Log> logs) {
        this.logs = logs;
    }

    public Wrapper(){
    }

    @XmlElement(name = "log")
    public List<Log> getLogs() {
        return logs;
    }

    public void setLogs(List<Log> logs) {
        this.logs = logs;
    }
}

