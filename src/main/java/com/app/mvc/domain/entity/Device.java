package com.app.mvc.domain.entity;

import com.app.mvc.dao.annotation.Column;
import com.app.mvc.dao.annotation.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Table(name = "Device")
public class Device {

    @Column(name = "ID", primaryKey = true, type = Integer.class)
    private int id;

    @Column(name = "SystemID", type = String.class)
    private String systemID;

    @Column(name = "OS", type = String.class)
    private String os;

    @JsonIgnore
    @Column(name = "CounterRequests", type = Integer.class)
    private int counterRequest;

    public Device() {
    }

    public Device(String systemID, String os) {
        this.systemID = systemID;
        this.os = os;
    }

    public Device(int id, String os, String systemID) {
        this.id = id;
        this.os = os;
        this.systemID = systemID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSystemID() {
        return systemID;
    }

    public void setSystemID(String systemID) {
        this.systemID = systemID;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public int getCounterRequest() {
        return counterRequest;
    }

    public void setCounterRequest(int counterRequest) {
        this.counterRequest = counterRequest;
    }

    @Override
    public String toString() {
        return "Device{" +
                "id=" + id +
                ", systemID='" + systemID + '\'' +
                ", os='" + os + '\'' +
                ", counterRequest=" + counterRequest +
                '}';
    }
}
