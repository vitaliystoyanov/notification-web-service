package com.app.mvc.dao;

import java.util.List;

public interface DAOInterface extends CRUDInterface {

    <T> List<T> retrieveAll(Class<?> T);

    <T> List<T> retrieveAllWithWhere(Class<?> T, String[] argsWhere, String[] valueWhere);

}
