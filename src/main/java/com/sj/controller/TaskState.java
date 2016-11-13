package com.sj.controller;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by fan.shijun on 2016-01-13.
 */

@XmlRootElement(name="TaskProcess")
public class TaskState {
    String ProcessGuid;
    String TaskID;
    String Status;
    String Percent;

    @XmlElement(name="ProcessGuid")
    public String getProcessGuid() {
        return ProcessGuid;
    }

    public void setProcessGuid(String processGuid) {
        ProcessGuid = processGuid;
    }

    @XmlElement(name="TaskID")
    public String getTaskID() {
        return TaskID;
    }

    public void setTaskID(String taskID) {
        TaskID = taskID;
    }

    @XmlElement(name="Status")
    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    @XmlElement(name="Percent")
    public String getPercent() {
        return Percent;
    }

    public void setPercent(String percent) {
        Percent = percent;
    }
}
