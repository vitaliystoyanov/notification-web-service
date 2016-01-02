package com.app.mvc.dao;

import java.util.List;

public interface DAOInterface extends CRUDInterface {

    public <T> List<T> retrieveAll(Class<?> T);

}
