package com.sj.controller;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by fan.shijun on 2016-05-18.
 */
@XmlRootElement(namespace = "http://ctvit.com/soa/interface_/v1.0/schema",name = "TaskProcessQueryResponse")
public class TaskProcessQueryResponse {

    TaskState TaskProcess;
    String Description;

    @XmlElement(name="TaskProcess")
    public TaskState getTaskProcess() {
        return TaskProcess;
    }

    public void setTaskProcess(TaskState taskProcess) {
        this.TaskProcess = taskProcess;
    }

    @XmlElement(name="Description")
    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
