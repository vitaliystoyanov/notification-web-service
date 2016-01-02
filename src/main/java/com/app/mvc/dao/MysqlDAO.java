package com.app.mvc.dao;

import java.sql.*;
import java.util.ArrayList;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.app.mvc.dao.annotation.*;
import com.app.mvc.dao.util.ResultSetParser;

public class MysqlDAO implements DAOInterface {

    private static final Logger logger = LogManager.getLogger(MysqlDAO.class);
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/main";

    private static MysqlDAO dao = null;

    private MysqlDAO() throws ClassNotFoundException {
        Class.forName(DRIVER);
        logger.info("MySQL driver loaded successfully.");
    }

    public static MysqlDAO getDAO() throws ClassNotFoundException {
        if (dao == null) {
            dao = new MysqlDAO();
            logger.debug("Created DAO object");
        }
        return dao;
    }

    @Override
    public <T> void create(T instance) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {

            Class<?> entity = instance.getClass();
            Field[] fields = entity.getDeclaredFields();

            StringBuilder sql = new StringBuilder("INSERT INTO ");
            StringBuilder sql1 = new StringBuilder(") VALUES (");

            sql.append(instance.getClass().getAnnotation(Table.class).name());
            sql.append(" (");

            for (Field field : fields) {
                if (field.isAnnotationPresent(Column.class) && !field.getAnnotation(Column.class).primaryKey()) {
                    sql.append(field.getAnnotation(Column.class).name());
                    sql.append(", ");
                }
            }

            for (Field field : fields) {
                if (field.isAnnotationPresent(Column.class) && !field.getAnnotation(Column.class).primaryKey()) {
                    sql1.append("?,");
                }
            }

            sql1.setLength(sql1.length() - 1);
            sql1.append(')');

            sql.setLength(sql.length() - 2);
            sql.append(sql1);

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql.toString())) {
                int parameterIndex = 1;

                for (Field field : fields) {
                    if (field.isAnnotationPresent(Column.class) && !field.getAnnotation(Column.class).primaryKey()) {
                        try {
                            //Class typeField = field.getAnnotation(Column.class).type();
                            PropertyDescriptor pdesc = new PropertyDescriptor(field.getName(), entity);
                            preparedStatement.setObject(parameterIndex, pdesc.getReadMethod().invoke(instance));
                            parameterIndex++;
                        } catch (Exception e) {
                            logger.error(e);
                            e.printStackTrace();
                        }
                    }
                }
                preparedStatement.executeUpdate();
                logger.debug("Executed SQL query: " + sql.toString());
            }
        } catch (SQLException e) {
            logger.error(e);
            e.printStackTrace();
        }
    }

    @Override
    public <T> T retrieve(Class<?> entity, int id) {

        T instance = null;

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {

            StringBuilder sql = new StringBuilder("SELECT * FROM ");
            sql.append(entity.getAnnotation(Table.class).name());
            sql.append(" WHERE ID=");
            sql.append(id);

            try (Statement st = connection.createStatement()) {

                ResultSet rs = st.executeQuery(sql.toString());
                instance = ResultSetParser.parseResultSet(rs, entity);

            } catch (SQLException e) {
                logger.error(e);
                e.printStackTrace();
            }
        } catch (SQLException e) {
            logger.error(e);
            e.printStackTrace();
        }
        return instance;
    }

    @Override
    public <T> ArrayList<T> retrieveAll(Class<?> entity) {
        // TODO Implement retrieving of all records in table.
        return null;
    }

    @Override
    public <T> void update(T instance) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {

            Class<?> entity = instance.getClass();
            Field[] fields = entity.getDeclaredFields();

            String table = instance.getClass().getAnnotation(Table.class).name();
            StringBuilder sql = new StringBuilder("UPDATE ");
            sql.append(table);
            sql.append(" SET ");

            try {
                for (Field field : fields) {
                    if (field.isAnnotationPresent(Column.class) && !field.getAnnotation(Column.class).primaryKey()) {
                        sql.append(field.getAnnotation(Column.class).name());
                        sql.append("='");
                        PropertyDescriptor pdesc = new PropertyDescriptor(field.getName(), entity);
                        sql.append(pdesc.getReadMethod().invoke(instance));
                        sql.append("', ");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            int id = 0;
            for (Field field : fields) {
                if (field.isAnnotationPresent(Column.class) && field.getAnnotation(Column.class).primaryKey()) {
                    try {
                        PropertyDescriptor pdesc = new PropertyDescriptor(field.getName(), entity);
                        id = (int) pdesc.getReadMethod().invoke(instance);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }

            sql = new StringBuilder(sql.substring(0, sql.length() - 2));
            sql.append(" WHERE ID=");
            sql.append(id);

            System.out.println(sql);

            try (Statement st = connection.createStatement()) {
                st.executeUpdate(sql.toString());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public <T> void delete(T instance) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {

            Class<?> entity = instance.getClass();
            Field[] fields = entity.getDeclaredFields();

            int id = 0;
            for (Field field : fields) {
                if (field.isAnnotationPresent(Column.class) &&
                        field.getAnnotation(Column.class).primaryKey()) {
                    try {
                        PropertyDescriptor pdesc = new PropertyDescriptor(field.getName(), entity);
                        id = (int) pdesc.getReadMethod().invoke(instance);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }

            String table = instance.getClass().getAnnotation(Table.class).name();
            StringBuilder sql = new StringBuilder("DELETE FROM ");
            sql.append(table);
            sql.append(" WHERE ID=?");

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql.toString())) {
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
                System.out.println(preparedStatement);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}