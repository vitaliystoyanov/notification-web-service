package com.app.mvc.controller.request;

import com.app.mvc.entity.LevelDanger;
import com.app.mvc.entity.Location;
import com.app.mvc.entity.TypeRequest;

public class Body {

    private int id;
    private Location location;
    private TypeRequest typeRequest;
    private LevelDanger levelDanger;

    public Body() {
    }

    public Body(int id, Location location, LevelDanger levelDanger, TypeRequest typeRequest) {
        this.id = id;
        this.location = location;
        this.levelDanger = levelDanger;
        this.typeRequest = typeRequest;
    }

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
        return "Body{" +
                "location=" + location.toString() +
                ", typeRequest=" + typeRequest.toString() +
                ", levelDanger=" + levelDanger.toString() +
                '}';
    }
}
