package com.app.mvc.entity;

import com.app.mvc.dao.annotation.Column;
import com.app.mvc.dao.annotation.Table;

@Table(name = "event")
public class Event {

    @Column(name = "id", primaryKey = true, type = Integer.class)
    private int id;

    @Column(name = "idRequestFK", foreignKey = true, type = Integer.class)
    private int idRequest;

    public Event() {
    }

    public Event(int idRequest) {
        this.idRequest = idRequest;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdRequest() {
        return idRequest;
    }

    public void setIdRequest(int idRequest) {
        this.idRequest = idRequest;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", idRequest=" + idRequest +
                '}';
    }
}
