package com.app.mvc.controller.response;

import com.app.mvc.entity.LevelDanger;
import com.app.mvc.entity.Location;
import com.app.mvc.entity.TypeRequest;

public class ResponseData {

    private int id;
    private Location location;
    private TypeRequest type;
    private LevelDanger danger;
    private String createdAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public TypeRequest getType() {
        return type;
    }

    public void setType(TypeRequest type) {
        this.type = type;
    }

    public LevelDanger getDanger() {
        return danger;
    }

    public void setDanger(LevelDanger danger) {
        this.danger = danger;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "ResponseData{" +
                "id=" + id +
                ", createdAt='" + createdAt + '\'' +
                ", location=" + location +
                ", type=" + type +
                ", danger=" + danger +
                '}';
    }
}
