package com.app.mvc.entity;

import com.app.mvc.dao.annotation.Column;
import com.app.mvc.dao.annotation.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Table(name = "typeRequest")
public class TypeRequest {

    @JsonIgnore
    @Column(name = "id", primaryKey = true, type = Integer.class)
    private int id;

    @Column(name = "name", type = String.class)
    private String name;

    public TypeRequest() {
    }

    public TypeRequest(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TypeRequest{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
