package com.app.mvc.entity;

import com.app.mvc.dao.annotation.Column;
import com.app.mvc.dao.annotation.Table;

@Table(name = "description")
public class Description {

    @Column(name = "idRequestsFK", foreignKey = true, type = Integer.class)
    private int idRequest;

    @Column(name = "idTypeRequestFK", foreignKey = true, type = Integer.class)
    private int idTypeRequest;

    @Column(name = "idLocationFK", foreignKey = true, type = Integer.class)
    private int idLocation;

    @Column(name = "idLevelDangerFK", foreignKey = true, type = Integer.class)
    private int idLevelDanger;

    public Description() {
    }

    public Description(int idTypeRequest, int idLevelDanger, int idLocation) {
        this.idTypeRequest = idTypeRequest;
        this.idLevelDanger = idLevelDanger;
        this.idLocation = idLocation;
    }

    public int getIdRequest() {
        return idRequest;
    }

    public void setIdRequest(int idRequest) {
        this.idRequest = idRequest;
    }

    public int getIdLevelDanger() {
        return idLevelDanger;
    }

    public void setIdLevelDanger(int idLevelDanger) {
        this.idLevelDanger = idLevelDanger;
    }

    public int getIdLocation() {
        return idLocation;
    }

    public void setIdLocation(int idLocation) {
        this.idLocation = idLocation;
    }

    public int getIdTypeRequest() {
        return idTypeRequest;
    }

    public void setIdTypeRequest(int idTypeRequest) {
        this.idTypeRequest = idTypeRequest;
    }

    @Override
    public String toString() {
        return "Description{" +
                "idRequest=" + idRequest +
                ", idTypeRequest=" + idTypeRequest +
                ", idLocation=" + idLocation +
                ", idLevelDanger=" + idLevelDanger +
                '}';
    }
}
