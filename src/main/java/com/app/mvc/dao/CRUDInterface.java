package com.app.mvc.dao;

public interface CRUDInterface {

    public <T> void create(T instance);

    public <T> T retrieve(Class<?> entity, int id);

    public <T> void update(T instance);

    public <T> void delete(T instance);

}
