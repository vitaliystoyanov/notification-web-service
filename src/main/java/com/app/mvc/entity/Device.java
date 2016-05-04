package com.app.mvc.entity;

import com.app.mvc.dao.annotation.Column;
import com.app.mvc.dao.annotation.Table;

@Table(name = "device")
public class Device {

    @Column(name = "ID", primaryKey = true, type = Integer.class)
    private int id;

    @Column(name = "SystemID", type = String.class)
    private String systemID;

    @Column(name = "CounterRequests", type = Integer.class)
    private int counterRequest;

    public Device() {
    }

    public Device(String systemID) {
        this.systemID = systemID;
    }

    public Device(int id, String systemID) {
        this.id = id;
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
                ", counterRequest=" + counterRequest +
                '}';
    }
}
