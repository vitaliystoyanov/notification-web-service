package com.app.mvc.dao;

import com.app.mvc.dao.annotation.Column;
import com.app.mvc.dao.annotation.Table;
import com.app.mvc.dao.annotation.UnixTimestamp;
import com.app.mvc.dao.util.ResultSetParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.ArrayList;

public class MysqlDAO implements DAOInterface {

    private static final Logger logger = LogManager.getLogger(MysqlDAO.class);
    private static final String USER = "kwg3yh92g0cd32qv";
    private static final String PASSWORD = "stk3i3or1fwgnqz0";
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://ivgz2rnl5rh7sphb.chr7pe7iynqr.eu-west-1.rds.amazonaws.com:3306/tiuega4c0alfyp4x";

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
            logger.info("DAO object created");
        }
        return dao;
    }

    private static Connection getConnection() throws SQLException {
        URI jdbUri = null;
        try {
            jdbUri = new URI(System.getenv("JAWSDB_MARIA_URL"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        String username = jdbUri.getUserInfo().split(":")[0];
        String password = jdbUri.getUserInfo().split(":")[1];
        String port = String.valueOf(jdbUri.getPort());
        String jdbUrl = "jdbc:mysql://" + jdbUri.getHost() + ":" + port + jdbUri.getPath();

        return DriverManager.getConnection(jdbUrl, username, password);
    }

    @Override
    public <T> int create(T instance) {
        int id = 0;
        try (Connection connection = getConnection()) {

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
                    if (field.isAnnotationPresent(UnixTimestamp.class)) {
                        sql1.append("UNIX_TIMESTAMP(now()),");
                    } else {
                        sql1.append("?,");
                    }
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
                            if (!field.isAnnotationPresent(UnixTimestamp.class)) {
                                PropertyDescriptor descriptor = new PropertyDescriptor(field.getName(), entity);
                                preparedStatement.setObject(parameterIndex, descriptor.getReadMethod().invoke(instance));
                                parameterIndex++;
                            }
                        } catch (Exception e) {
                            logger.error("Error in adding parameters", e);
                        }
                    }
                }
                logger.info("Execute SQL query: " + preparedStatement);
                preparedStatement.executeUpdate();

                ResultSet keys = preparedStatement.getGeneratedKeys();
                if (keys.next()) {
                    id = keys.getInt(1);
                    logger.trace("ID returned after executing SQL query: " + id);
                }
            }
            return id;
        } catch (SQLException e) {
            logger.error("Error in executing the SQL query", e);
        }
        return id;
    }

    @Override
    public <T> T retrieve(Class<?> entity, int id) {

        T instance = null;

        try (Connection connection = getConnection()) {

            StringBuilder sql = new StringBuilder("SELECT * FROM ");
            sql.append(entity.getAnnotation(Table.class).name());
            sql.append(" WHERE ID=");
            sql.append(id);

            try (Statement statement = connection.createStatement()) {

                ResultSet rs = statement.executeQuery(sql.toString());
                instance = ResultSetParser.parseResultSetToInstance(rs, entity);
                logger.info("Executed SQL QUERY: " + statement.toString());
            } catch (SQLException e) {
                logger.error("Error in executing the SQL query", e);
            }
        } catch (SQLException e) {
            logger.error("Error in executing the SQL query", e);
        }
        return instance;
    }

    @Override
    public <T> ArrayList<T> retrieveAll(Class<?> entity) {
        ArrayList<T> entityList = new ArrayList<>();

        try (Connection connection = getConnection()) {
            StringBuilder sql = new StringBuilder("SELECT * FROM ");
            sql.append(entity.getAnnotation(Table.class).name());

            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(sql.toString());
                resultSet.beforeFirst();
                entityList = ResultSetParser.parseResultSetToArray(resultSet, entity);
            } catch (SQLException e) {
                logger.error("Error in executing the SQL query", e);
            }
            logger.info("Executed SQL QUERY: " + sql.toString());
        } catch (SQLException e) {
            logger.error("Error in executing the SQL query", e);
        }
        return entityList;
    }

    @Override
    public <T> ArrayList<T> retrieveAllWithWhere(Class<?> entity, String[] argsWhere, String[] valueWhere) {
        ArrayList<T> entityList = new ArrayList<>();

        try (Connection connection = getConnection()) {
            StringBuilder sql = new StringBuilder("SELECT * FROM ");
            sql.append(entity.getAnnotation(Table.class).name());
            sql.append(" WHERE ");
            for (int i = 0; i < argsWhere.length; i++) {
                sql.append(argsWhere[i]);
                sql.append("=");
                sql.append("\"" + valueWhere[i] + "\"");
                if (i != argsWhere.length - 1) sql.append(" AND ");
            }

            logger.debug(sql.toString());

            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(sql.toString());
                resultSet.beforeFirst();
                entityList = ResultSetParser.parseResultSetToArray(resultSet, entity);
            } catch (SQLException e) {
                logger.error("Error in executing the SQL query", e);
            }
            logger.info("Executed SQL QUERY: " + sql.toString());
        } catch (SQLException e) {
            logger.error("Error in executing the SQL query", e);
        }
        return entityList;
    }

    @Override
    public <T> void update(T instance) {
        try (Connection connection = getConnection()) {

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
                        PropertyDescriptor descriptor = new PropertyDescriptor(field.getName(), entity);
                        sql.append(descriptor.getReadMethod().invoke(instance));
                        sql.append("', ");
                    }
                }
            } catch (Exception e) {
                logger.error("Error in reading parameters", e);
            }

            int id = 0;
            for (Field field : fields) {
                if (field.isAnnotationPresent(Column.class) && field.getAnnotation(Column.class).primaryKey()) {
                    try {
                        PropertyDescriptor descriptor = new PropertyDescriptor(field.getName(), entity);
                        id = (int) descriptor.getReadMethod().invoke(instance);
                    } catch (Exception e) {
                        logger.error("Error in adding parameters", e);
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
            logger.error("Error in executing the SQL query", e);
        }
    }

    @Override
    public <T> void delete(T instance) {
        try (Connection connection = getConnection()) {

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
            String sql = "DELETE FROM " + table + " WHERE ID=?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
                logger.info("Executed SQL QUERY: " + preparedStatement);
            }

        } catch (SQLException e) {
            logger.error("Error in executing the SQL query", e);
        }
    }
}