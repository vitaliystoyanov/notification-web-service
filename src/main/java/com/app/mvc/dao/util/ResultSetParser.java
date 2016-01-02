package com.app.mvc.dao.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;

import com.app.mvc.dao.annotation.Column;

public abstract class ResultSetParser {

    public static <T> T parseResultSet(ResultSet rs, Class<?> entity) {

        try {
            @SuppressWarnings("unchecked")
            T instance = (T) entity.newInstance();
            Field[] fields = entity.getDeclaredFields();

            while (rs.next()) {
                for (Field field : fields) {
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), entity);
                    Method write = propertyDescriptor.getWriteMethod();

                    if (field.isAnnotationPresent(Column.class)) {
                        Class typeField = field.getAnnotation(Column.class).type();
                        try {
                            write.invoke(instance, rs.getObject(field.getAnnotation(Column.class).name(), typeField));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            return instance;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
