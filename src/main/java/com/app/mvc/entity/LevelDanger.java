package com.app.mvc.entity;

import com.app.mvc.dao.annotation.Column;
import com.app.mvc.dao.annotation.Table;

@Table(name = "levelDanger")
public class LevelDanger {

    @Column(name = "id", primaryKey = true, type = Integer.class)
    private int id;

    @Column(name = "level", type = Integer.class)
    private int level;

    @Column(name = "name", type = String.class)
    private String name;

    public LevelDanger() {
    }

    public LevelDanger(String name, int level) {
        this.name = name;
        this.level = level;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "LevelDanger{" +
                "id=" + id +
                ", level=" + level +
                ", name='" + name + '\'' +
                '}';
    }
}
