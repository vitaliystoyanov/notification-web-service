package com.app.mvc.dao;

import com.app.mvc.dao.annotation.Column;
import com.app.mvc.dao.annotation.Table;
import com.app.mvc.dao.util.ResultSetParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;

public class MysqlDAO implements DAOInterface {

    private static final Logger logger = LogManager.getLogger(MysqlDAO.class);
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/main";

    private static MysqlDAO dao = null;

    private MysqlDAO() throws ClassNotFoundException {
        Class.forName(DRIVER);
        logger.info("MySQL driver loaded successfully");
    }

    public static MysqlDAO getDAO() {
        if (dao == null) {
            try {
                dao = new MysqlDAO();
            } catch (ClassNotFoundException e) {
                logger.error("Class of driver not found", e);
            }
            logger.debug("DAO object created");
        }
        return dao;
    }

    @Override
    public <T> int create(T instance) {
        int id = 0;
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

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS)) {
                int parameterIndex = 1;

                for (Field field : fields) {
                    if (field.isAnnotationPresent(Column.class) && !field.getAnnotation(Column.class).primaryKey()) {
                        try {
                            PropertyDescriptor descriptor = new PropertyDescriptor(field.getName(), entity);
                            preparedStatement.setObject(parameterIndex, descriptor.getReadMethod().invoke(instance));
                            parameterIndex++;
                        } catch (Exception e) {
                            logger.error("", e);
                            e.printStackTrace();
                        }
                    }
                }
                logger.info("Execute SQL query: " + preparedStatement);
                preparedStatement.executeUpdate();

                ResultSet keys = preparedStatement.getGeneratedKeys();
                if (keys.next()) {
                    id = keys.getInt(1);
                    //logger.trace("returned id after SQL query: " + id);
                }
            }
            return id;
        } catch (SQLException e) {
            logger.error("", e);
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public <T> T retrieve(Class<?> entity, int id) {

        T instance = null;

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {

            StringBuilder sql = new StringBuilder("SELECT * FROM ");
            sql.append(entity.getAnnotation(Table.class).name());
            sql.append(" WHERE ID=");
            sql.append(id);

            try (Statement statement = connection.createStatement()) {

                ResultSet rs = statement.executeQuery(sql.toString());
                instance = ResultSetParser.parseResultSetToInstance(rs, entity);
                logger.trace("Executed SQL QUERY: " + statement.toString());
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
        ArrayList<T> entityList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            StringBuilder sql = new StringBuilder("SELECT * FROM ");
            sql.append(entity.getAnnotation(Table.class).name());

            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(sql.toString());
                resultSet.beforeFirst();
                entityList = ResultSetParser.parseResultSetToArray(resultSet, entity);
            } catch (SQLException e) {
                logger.error("", e);
                e.printStackTrace();
            }
            logger.info("Executed SQL QUERY: " + sql.toString());
        } catch (SQLException e) {
            logger.error("", e);
            e.printStackTrace();
        }
        return entityList;
    }

    @Override
    public <T> ArrayList<T> retrieveAllWithWhere(Class<?> entity, String[] argsWhere, String[] valueWhere) {
        ArrayList<T> entityList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            StringBuilder sql = new StringBuilder("SELECT * FROM ");
            sql.append(entity.getAnnotation(Table.class).name());
            sql.append(" WHERE ");
            for (int i = 0; i < argsWhere.length; i++) {
                sql.append(argsWhere[i]);
                sql.append("=");
                sql.append(valueWhere[i]);
                if (i != argsWhere.length - 1) sql.append(" AND ");
            }

            logger.debug(sql.toString());

            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(sql.toString());
                resultSet.beforeFirst();
                entityList = ResultSetParser.parseResultSetToArray(resultSet, entity);
            } catch (SQLException e) {
                logger.error("", e);
                e.printStackTrace();
            }
            logger.info("Executed SQL QUERY: " + sql.toString());
        } catch (SQLException e) {
            logger.error("", e);
            e.printStackTrace();
        }
        return entityList;
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
                        PropertyDescriptor descriptor = new PropertyDescriptor(field.getName(), entity);
                        id = (int) descriptor.getReadMethod().invoke(instance);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }

            sql = new StringBuilder(sql.substring(0, sql.length() - 2));
            sql.append(" WHERE ID=");
            sql.append(id);

            try (Statement st = connection.createStatement()) {
                st.executeUpdate(sql.toString());
                logger.info("Executed SQL QUERY: " + sql.toString());
            }

        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("", e);
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
                        PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), entity);
                        id = (int) propertyDescriptor.getReadMethod().invoke(instance);
                    } catch (Exception e) {
                        logger.error(e);
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
                logger.info("Executed SQL QUERY: " + preparedStatement);
            }

        } catch (SQLException e) {
            logger.error(e);
            e.printStackTrace();
        }
    }
}