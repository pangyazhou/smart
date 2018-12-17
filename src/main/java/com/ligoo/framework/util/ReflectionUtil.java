package com.ligoo.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @Author: Administrator
 * @Date: 2018/12/14 14:19:22
 * @Description:反射工具类
 */
public class ReflectionUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReflectionUtil.class);

    
    /**
     * description: 创建实例
     * author: Administrator
     * date: 2018/12/14 14:22
     *
     * @param: clazz 类类型
     * @return: 
     */
    public static Object newInstance(Class<?> clazz){
        Object instance;
        try {
            instance = clazz.newInstance();
        } catch (Exception e) {
            LOGGER.error("new instance failure", e);
            throw new RuntimeException(e);
        }
        return instance;
    }

    /**
     * description: 调用方法
     * author: Administrator
     * date: 2018/12/14 14:22
     *
     * @param: obj 方法所属对象
     * @param method 调用的方法
     * @param args 调用方法的参数
     * @return: 调用方法返回值
     */
    public static Object invokeMethod(Object obj, Method method, Object... args){
        Object result;
        try {
            method.setAccessible(true);
            result = method.invoke(obj, args);
        } catch (Exception e) {
            LOGGER.error("invoke method failure.", e);
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * description: 设置属性值
     * author: Administrator
     * date: 2018/12/14 14:23
     *
     * @param: obj 属性所属对象
     * @param field 属性
     * @param value 设置的属性值
     * @return:
     */
    public static void setField(Object obj, Field field, Object value){
        try {
            field.setAccessible(true);
            field.set(obj, value);
        } catch (Exception e) {
            LOGGER.error("set field failure", e);
            throw new RuntimeException(e);
        }
    }
}
