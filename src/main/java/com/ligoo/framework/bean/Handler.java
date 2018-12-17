package com.ligoo.framework.bean;

import java.lang.reflect.Method;

/**
 * @Author: Administrator
 * @Date: 2018/12/14 15:13:16
 * @Description: 封装Action信息
 */
public class Handler {
    // Controller类
    private Class<?> controllerClass;
    // Action方法
    private Method actionMethod;

    public Handler(Class<?> controllerClass, Method actionMethod) {
        this.controllerClass = controllerClass;
        this.actionMethod = actionMethod;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public Method getActionMethod() {
        return actionMethod;
    }
}
