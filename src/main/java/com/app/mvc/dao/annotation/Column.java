package com.app.mvc.dao.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {

    public String name();
    public Class type();

    public boolean primaryKey() default false;
    public boolean foreignKey() default false;

}
