package com.app.mvc.entity;

import com.app.mvc.dao.annotation.Column;
import com.app.mvc.dao.annotation.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Table(name = "location")
public class Location {

    @JsonIgnore
    @Column(name = "id", primaryKey = true, type = Integer.class)
    private int id;

    @Column(name = "radius", type = Integer.class)
    private int radius;

    @Column(name = "longitude", type = Double.class)
    private double longitude;

    @Column(name = "latitude", type = Double.class)
    private double latitude;

    public Location() {
    }

    public Location(int radius, double longitude, double latitude) {
        this.radius = radius;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", radius=" + radius +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}

