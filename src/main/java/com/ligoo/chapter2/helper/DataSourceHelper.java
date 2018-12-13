package com.ligoo.chapter2.helper;

import com.ligoo.chapter2.util.CollectionUtil;
import com.ligoo.chapter2.util.PropsUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @Author: Administrator
 * @Date: 2018/12/12 16:34:24
 * @Description: 数据库连接帮助类
 */
public final class DataSourceHelper {
    private final static Logger LOGGER = LoggerFactory.getLogger(DataSourceHelper.class);

    private final static String DRIVER;
    private final static String URL;
    private final static String USERNAME;
    private final static String PASSWORD;

    private static final QueryRunner QUERY_RUNNER = new QueryRunner();
    private static final ThreadLocal<Connection> CONNECTION_HOLDER = new ThreadLocal<Connection>();


    static {
        Properties datasource = PropsUtil.loadProps("datasource.properties");
        DRIVER = datasource.getProperty("jdbc.driver");
        URL = datasource.getProperty("jdbc.url");
        USERNAME = datasource.getProperty("jdbc.username");
        PASSWORD = datasource.getProperty("jdbc.password");
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            LOGGER.error("cannot load jdbc driver.", e);
        }
    }

    /**
     * description: 获取数据库连接
     * author: Administrator
     * date: 2018/12/12 16:35
     *
     * @param:
     * @return:
     */
    public static Connection getConnection(){
        Connection conn = CONNECTION_HOLDER.get();
        if(conn == null) {
            try {
                conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            } catch (SQLException e) {
                LOGGER.error("get connection failure", e);
                throw new RuntimeException(e);
            }finally {
                CONNECTION_HOLDER.set(conn);
            }
        }
        return conn;
    }

    /**
     * description: 关闭数据库连接
     * author: Administrator
     * date: 2018/12/12 16:35
     *
     * @param:
     * @return:
     */
    public static void closeConnection(){
        Connection connection = CONNECTION_HOLDER.get();
        if(connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.error("close connection failure", e);
                throw new RuntimeException(e);
            }finally {
                CONNECTION_HOLDER.remove();
            }
        }
    }

    /**
     * description: 程序实体列表
     * author: Administrator
     * date: 2018/12/12 17:16
     *
     * @param: entityClass 实体类类文件
     * @param sql 查询语句
     * @param params 查询参数
     * @return:
     */
    public static <T> List<T> queryEntityList(Class<T> entityClass, String sql, Object... params){
        List<T> entityList;
        try {
            Connection conn = getConnection();
            entityList = QUERY_RUNNER.query(conn, sql, new BeanListHandler<T>(entityClass), params);
        } catch (SQLException e) {
            LOGGER.error("query entity list failure", e);
            throw new RuntimeException(e);
        }finally {
            closeConnection();
        }
        return entityList;
    }

    /**
     * description: 查询实体
     * author: Administrator
     * date: 2018/12/12 17:18
     *
     * @param:
     * @return:
     */
    public static <T> T queryEntity(Class<T> entityClass, String sql, Object... params){
        T entity;
        try {
            Connection conn = getConnection();
            entity = QUERY_RUNNER.query(conn, sql, new BeanHandler<T>(entityClass), params);
        } catch (SQLException e) {
            LOGGER.error("query entity list failure", e);
            throw new RuntimeException(e);
        }finally {
            closeConnection();
        }
        return entity;
    }

    /**
     * description: 以Map列表形式返回查询信息
     * author: Administrator
     * date: 2018/12/12 17:29
     *
     * @param: sql 程序语句
     * @param params 查询参数
     * @return:
     */
    public static List<Map<String, Object>> executeQuery(String sql, Object... params){
        List<Map<String, Object>> result = null;
        try {
            Connection connection = getConnection();
            result = QUERY_RUNNER.query(connection,sql, new MapListHandler(), params);
        } catch (SQLException e) {
            LOGGER.error("execute query failure", e);
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * description: 执行更新,插入,删除通用方法
     * author: Administrator
     * date: 2018/12/12 17:32
     *
     * @param:
     * @return:
     */
    public static int executeUpdate(String sql, Object... params){
        int rows = 0;   //更新数据库影响行数
        try {
            Connection connection = getConnection();
            rows = QUERY_RUNNER.update(connection, sql, params);
        } catch (SQLException e) {
            LOGGER.error("execute update failure.", e);
            throw new RuntimeException(e);
        }finally {
            closeConnection();
        }
        return rows;
    }

    /**
     * description: 插入实体类
     * author: Administrator
     * date: 2018/12/12 17:51
     *
     * @param:
     * @return:
     */
    public static <T> boolean insertEntity(Class<T> entityClass, Map<String, Object> fieldMap){
        if(CollectionUtil.isEmpty(fieldMap)){
            LOGGER.error("can not insert entity: fieldMap is empty.");
            return false;
        }
        String sql = "insert into " + getTableName(entityClass);
        StringBuilder columns = new StringBuilder("(");
        StringBuilder values = new StringBuilder("(");
        for (String fieldName: fieldMap.keySet()){
            columns.append(fieldName).append(", ");
            values.append("?, ");
        }
        columns.replace(columns.lastIndexOf(", "), columns.length(), ") ");
        values.replace(values.lastIndexOf(", "), values.length(), ") ");
        sql += columns + " values " + values;
        Object[] params = fieldMap.values().toArray();
        return executeUpdate(sql, params) == 1;
    }

    public static <T> boolean updateEntity(Class<T> entityClass, long id, Map<String, Object> fieldMap){
        if(CollectionUtil.isEmpty(fieldMap)){
            LOGGER.error("can not update entity: fieldMap is empty.");
            return false;
        }
        String sql = "update " + getTableName(entityClass) + " set ";
        StringBuilder columns = new StringBuilder();
        for (String fieldName: fieldMap.keySet()){
            columns.append(fieldName).append("=?, ");
        }
        sql += columns.replace(columns.lastIndexOf(", "), columns.length(), " where id=?");
        List<Object> paramList = new ArrayList<>();
        paramList.addAll(fieldMap.values());
        paramList.add(id);
        Object[] params = paramList.toArray();
        return executeUpdate(sql, params) == 1;
    }

    /**
     * description: 根据id删除实体数据
     * author: Administrator
     * date: 2018/12/12 17:54
     *
     * @param:
     * @return:
     */
    public static <T> boolean deleteEntity(Class<T> entityClass, long id){
        String sql = "delete from " + getTableName(entityClass) + " where id = ?";
        return executeUpdate(sql, id) == 1;
    }

    /**
     * description: 根据实体类获取数据库表名
     * author: Administrator
     * date: 2018/12/12 17:36
     *
     * @param:
     * @return:
     */
    public static String getTableName(Class<?> clazz){
        return clazz.getSimpleName().toLowerCase();
    }
}
