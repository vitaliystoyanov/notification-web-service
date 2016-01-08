package com.app.mvc.controller.response;

import com.app.mvc.entity.LevelDanger;
import com.app.mvc.entity.Location;
import com.app.mvc.entity.TypeRequest;

public class CommonRequest {

    private Location location;
    private TypeRequest typeRequest;
    private LevelDanger levelDanger;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public LevelDanger getLevelDanger() {
        return levelDanger;
    }

    public void setLevelDanger(LevelDanger levelDanger) {
        this.levelDanger = levelDanger;
    }

    public TypeRequest getTypeRequest() {
        return typeRequest;
    }

    public void setTypeRequest(TypeRequest typeRequest) {
        this.typeRequest = typeRequest;
    }

    @Override
    public String toString() {
        return "CommonRequest{" +
                "location=" + location.toString() +
                ", typeRequest=" + typeRequest.toString() +
                ", levelDanger=" + levelDanger.toString() +
                '}';
    }
}
