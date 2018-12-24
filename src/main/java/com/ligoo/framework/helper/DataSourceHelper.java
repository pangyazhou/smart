package com.ligoo.framework.helper;

import com.ligoo.framework.util.CollectionUtil;
import com.ligoo.framework.util.PropsUtil;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
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

    private static final QueryRunner QUERY_RUNNER;
    private static final ThreadLocal<Connection> CONNECTION_HOLDER;
    private static final BasicDataSource DATA_SOURCE;


    static {
        CONNECTION_HOLDER = new ThreadLocal<Connection>();
        QUERY_RUNNER = new QueryRunner();
        Properties datasource = PropsUtil.loadProps("smart.properties");
        String driver = datasource.getProperty("smart.framework.jdbc.driver");
        String url = datasource.getProperty("smart.framework.jdbc.url");
        String username = datasource.getProperty("smart.framework.jdbc.username");
        String password = datasource.getProperty("smart.framework.jdbc.password");

        DATA_SOURCE = new BasicDataSource();
        DATA_SOURCE.setDriverClassName(driver);
        DATA_SOURCE.setUrl(url);
        DATA_SOURCE.setUsername(username);
        DATA_SOURCE.setPassword(password);
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
                conn = DATA_SOURCE.getConnection();
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
     * description: 开启事务
     * author: Administrator
     * date: 2018/12/20 13:43
     *
     * @param:
     * @return:
     */
    public static void beginTransaction(){
        Connection conn = getConnection();
        if(conn != null){
            try {
                conn.setAutoCommit(false);
            } catch (SQLException e) {
                LOGGER.error("begin transaction failure", e);
                throw new RuntimeException(e);
            }finally {
                CONNECTION_HOLDER.set(conn);
            }
        }
    }

    /**
     * description: 提交事务
     * author: Administrator
     * date: 2018/12/20 13:43
     *
     * @param:
     * @return:
     */
    public static void commitTransaction(){
        Connection conn = getConnection();
        if(conn != null) {
            try {
                conn.commit();
                conn.close();
            } catch (SQLException e) {
                LOGGER.error("commit transaction failure", e);
                throw new RuntimeException(e);
            } finally {
                CONNECTION_HOLDER.remove();
            }
        }
    }

    /**
     * description: 事务回滚
     * author: Administrator
     * date: 2018/12/20 13:43
     *
     * @param:
     * @return:
     */
    public static void rollbackTransaction(){
        Connection conn = getConnection();
        if(conn != null){
            try {
                conn.rollback();
                conn.close();
            } catch (SQLException e) {
                LOGGER.error("transaction rollback failure", e);
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

    public static void executeSqlFile(String filePath){
        InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        try {
            String sql;
            while ((sql = reader.readLine()) != null) {
                executeUpdate(sql, null);
            }
        }catch (Exception e){
            LOGGER.error("execute sql file failure", e);
            throw new RuntimeException(e);
        }
    }
}
