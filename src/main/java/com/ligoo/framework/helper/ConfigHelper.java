package com.ligoo.framework.helper;

import com.ligoo.framework.ConfigConstant;
import com.ligoo.framework.util.PropsUtil;

import java.util.Properties;

/**
 * @Author: Administrator
 * @Date: 2018/12/13 10:39:53
 * @Description:属性文件帮助类
 */
public class ConfigHelper {
    // 配置文件属性类
    private static final Properties CONFIG_PROPS = PropsUtil.loadProps(ConfigConstant.CONFIG_FILE);

    /**
     * description: 返回数据库连接驱动
     * author: Administrator
     * date: 2018/12/13 10:51
     *
     * @param:
     * @return:
     */
    public static String getJdbcDriver(){
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_DRIVER);
    }

    /**
     * description: 获取数据库连接URL
     * author: Administrator
     * date: 2018/12/13 10:51
     *
     * @param:
     * @return:
     */
    public static String getJdbcUrl(){
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_URL);
    }

    /**
     * description: 获取数据库连接用户名
     * author: Administrator
     * date: 2018/12/13 10:51
     *
     * @param:
     * @return:
     */
    public static String getJdbcUsername(){
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_USERNAME);
    }

    /**
     * description: 获取数据库连接密码
     * author: Administrator
     * date: 2018/12/13 10:51
     *
     * @param:
     * @return:
     */
    public static String getJdbcPassword(){
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_PASSWORD);
    }

    /**
     * description: 获取应用基础包名
     * author: Administrator
     * date: 2018/12/13 10:52
     *
     * @param:
     * @return:
     */
    public static String getAppBasePackage(){
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.APP_BASE_PACKAGE);
    }

    /**
     * description: 获取应用JSP路径
     * author: Administrator
     * date: 2018/12/13 10:52
     *
     * @param:
     * @return:
     */
    public static String getAppJspPath(){
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.APP_JSP_PATH, "/WEB-INF/view/");
    }

    /**
     * description: 获取应用静态资源路径
     * author: Administrator
     * date: 2018/12/13 10:52
     *
     * @param:
     * @return:
     */
    public static String getAppAssetPath(){
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.APP_ASSET_PATH, "/asset/");
    }
}
