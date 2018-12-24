package com.ligoo.framework.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @Author: Administrator
 * @Date: 2018/12/24 10:00:46
 * @Description: Servlet助手类
 * 解耦Request,Response
 */
public class ServletHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServletHelper.class);

    // 局部线程变量,保存本地线程的ServletHelper对象
    private static final ThreadLocal<ServletHelper> SERVLET_HELPER_HOLDER = new ThreadLocal<ServletHelper>();

    private HttpServletRequest request;
    private HttpServletResponse response;

    /**
     * description: 私有构造方法
     * author: Administrator
     * date: 2018/12/24 10:13
     *
     * @param:
     * @return:
     */
    private ServletHelper(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    /**
     * description: 初始化局部线程变量
     * author: Administrator
     * date: 2018/12/24 10:06
     *
     * @param:
     * @return:
     */
    public static void init(HttpServletRequest request, HttpServletResponse response){
        SERVLET_HELPER_HOLDER.set(new ServletHelper(request, response));
    }

    /**
     * description: 移除局部线程变量
     * author: Administrator
     * date: 2018/12/24 10:06
     *
     * @param:
     * @return:
     */
    public static void destory(){
        SERVLET_HELPER_HOLDER.remove();
    }

    /**
     * description: 获取request对象
     * author: Administrator
     * date: 2018/12/24 10:07
     *
     * @param:
     * @return:
     */
    public static HttpServletRequest getRequest(){
        return SERVLET_HELPER_HOLDER.get().request;
    }

    /**
     * description: 获取response对象
     * author: Administrator
     * date: 2018/12/24 10:07
     *
     * @param:
     * @return:
     */
    public static HttpServletResponse getResponse(){
        return SERVLET_HELPER_HOLDER.get().response;
    }

    /**
     * description: 获取session对象
     * author: Administrator
     * date: 2018/12/24 10:07
     *
     * @param:
     * @return:
     */
    public static HttpSession getSession(){
        return getRequest().getSession();
    }

    /**
     * description: 获取ServletContext对象
     * author: Administrator
     * date: 2018/12/24 10:09
     *
     * @param: 
     * @return: 
     */
    public static ServletContext getServletContext(){
        return getRequest().getServletContext();
    }

    /**
     * description: 将属性放入request对象中
     * author: Administrator
     * date: 2018/12/24 10:11
     *
     * @param:
     * @return:
     */
    public static void  setRequestAttribute(String key, Object value){
        getRequest().setAttribute(key, value);
    }

    /**
     * description: 从request获取属性
     * author: Administrator
     * date: 2018/12/24 10:11
     *
     * @param:
     * @return:
     */
    @SuppressWarnings("unchecked")
    public static <T> T getRequestAttribute(String key){
        return (T) getRequest().getAttribute(key);
    }

    /**
     * description: 从request移除属性
     * author: Administrator
     * date: 2018/12/24 10:11
     *
     * @param:
     * @return:
     */
    public static void removeRequestAttribute(String key){
        getRequest().removeAttribute(key);
    }

    /**
     * description: 发送重定向响应
     * author: Administrator
     * date: 2018/12/24 10:13
     *
     * @param:
     * @return:
     */
    public static void sendRedirect(String location){
        try {
            getResponse().sendRedirect(getRequest().getContextPath() + location);
        } catch (IOException e) {
            LOGGER.error("send redirect failure", e);
        }
    }

    /**
     * description: 设置session属性
     * author: Administrator
     * date: 2018/12/24 10:12
     *
     * @param:
     * @return:
     */
    public static void setSessionAttribute(String key, Object value){
        getSession().setAttribute(key, value);
    }

    /**
     * description: 获取session属性
     * author: Administrator
     * date: 2018/12/24 10:12
     *
     * @param:
     * @return:
     */
    @SuppressWarnings("unchecked")
    public static <T> T getSessionAttribute(String key){
        return (T) getSession().getAttribute(key);
    }

    /**
     * description: 移除session属性
     * author: Administrator
     * date: 2018/12/24 10:12
     *
     * @param:
     * @return:
     */
    public static void removeSessionAttribute(String key){
        getSession().removeAttribute(key);
    }

    /**
     * description: 使session失效
     * author: Administrator
     * date: 2018/12/24 10:12
     *
     * @param:
     * @return:
     */
    public static void invalidateSession(){
        getSession().invalidate();
    }
}
