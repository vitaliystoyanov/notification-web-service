package com.app.mvc.dao.util;

import com.app.mvc.dao.annotation.Column;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.ArrayList;

public abstract class ResultSetParser {

    private static final Logger logger = LogManager.getLogger(ResultSetParser.class);

    public static <T> T parseResultSetToInstance(ResultSet resultSet, Class<?> entity) {

        try {
            @SuppressWarnings("unchecked")
            T instance = (T) entity.newInstance();
            Field[] fields = entity.getDeclaredFields();

            while (resultSet.next()) {
                for (Field field : fields) {
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), entity);
                    Method write = propertyDescriptor.getWriteMethod();

                    if (field.isAnnotationPresent(Column.class)) {
                        Class typeField = field.getAnnotation(Column.class).type();
                        try {
                            write.invoke(instance, resultSet.getObject(field.getAnnotation(Column.class).name(), typeField));
                        } catch (Exception e) {
                            logger.error("", e);
                        }
                    }
                }
            }
            return instance;
        } catch (Exception e) {
            logger.error("", e);
        }
        return null;
    }

    public static <T> ArrayList<T> parseResultSetToArray(ResultSet resultSet, Class<?> entity) {
        ArrayList<T> list = new ArrayList<>();
        try {
            while (resultSet.next()) {
                @SuppressWarnings("unchecked")
                T instance = (T) entity.newInstance();
                Field[] fields = entity.getDeclaredFields();

                for (Field field : fields) {
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), entity);
                    Method write = propertyDescriptor.getWriteMethod();

                    if (field.isAnnotationPresent(Column.class)) {
                        Class typeField = field.getAnnotation(Column.class).type();
                        try {
                            write.invoke(instance, resultSet.getObject(field.getAnnotation(Column.class).name(), typeField));
                        } catch (Exception e) {
                            logger.error("", e);
                        }
                    }
                }
                list.add(instance);
            }
            return list;
        } catch (Exception e) {
            logger.error("", e);
        }
        return list;
    }

}
