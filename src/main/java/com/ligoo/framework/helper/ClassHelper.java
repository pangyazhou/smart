package com.ligoo.framework.helper;

import com.ligoo.framework.annotation.Controller;
import com.ligoo.framework.annotation.Service;
import com.ligoo.framework.util.ClassUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author: Administrator
 * @Date: 2018/12/13 11:43:03
 * @Description:类操作助手类
 */
public class ClassHelper {
    private static final Set<Class<?>> CLASS_SET;
    static {
        String basePackage = ConfigHelper.getAppBasePackage();
        CLASS_SET = ClassUtil.getClassSet(basePackage);
    }

    /**
     * description: 获取应用包名下的所有类
     * author: Administrator
     * date: 2018/12/14 13:54
     *
     * @param: 
     * @return: 
     */
    public static Set<Class<?>> getClassSet(){
        return CLASS_SET;
    }

    /**
     * description: 获取应用包名下所有Service类
     * author: Administrator
     * date: 2018/12/14 13:55
     *
     * @param:
     * @return:
     */
    public static Set<Class<?>> getServiceClassSet(){
        Set<Class<?>> classSet = new HashSet<>();
        for (Class clazz: CLASS_SET){
            if(clazz.isAnnotationPresent(Service.class)){
                classSet.add(clazz);
            }
        }
        return classSet;
    }

    /**
     * description: 获取应用包下的Controller类
     * author: Administrator
     * date: 2018/12/14 14:06
     *
     * @param:
     * @return:
     */
    public static Set<Class<?>> getControllerClassSet(){
        Set<Class<?>> classSet = new HashSet<>();
        for (Class clazz: CLASS_SET){
            if(clazz.isAnnotationPresent(Controller.class)){
                classSet.add(clazz);
            }
        }
        return classSet;
    }
    
    /**
     * description: 获取应用包名下的Bean类
     * author: Administrator
     * date: 2018/12/14 14:07
     *
     * @param: 
     * @return: 
     */
    public static Set<Class<?>> getBeanClassSet(){
        Set<Class<?>> beanClassSet = new HashSet<>();
        beanClassSet.addAll(getServiceClassSet());
        beanClassSet.addAll(getControllerClassSet());
        return beanClassSet;
    }

}
