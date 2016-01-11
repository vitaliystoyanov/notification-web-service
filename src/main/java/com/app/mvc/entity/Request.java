package com.app.mvc.entity;

import com.app.mvc.dao.annotation.Column;
import com.app.mvc.dao.annotation.Table;
import com.app.mvc.dao.annotation.UnixTimestamp;

@Table(name = "request")
public class Request {

    @Column(name = "id", primaryKey = true, type = Integer.class)
    private int id;

    @UnixTimestamp
    @Column(name = "createdAt", type = Integer.class)
    private int createdAt;

    @Column(name = "idDeviceFK", foreignKey = true, type = Integer.class)
    private int idDevice;

    public Request() {
    }

    public Request(int idDevice) {
        this.idDevice = idDevice;
    }

    public int getIdDevice() {
        return idDevice;
    }

    public int getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(int createdAt) {
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdDevice(int idDevice) {
        this.idDevice = idDevice;
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", idDevice=" + idDevice +
                '}';
    }
}
