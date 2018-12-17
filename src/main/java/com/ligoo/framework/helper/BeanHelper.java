package com.ligoo.framework.helper;

import com.ligoo.framework.util.ReflectionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Author: Administrator
 * @Date: 2018/12/14 14:35:51
 * @Description:Bean助手类
 */
public class BeanHelper {
    private static final Map<Class<?>, Object> BEAN_MAP = new HashMap<>();
    static {
        Set<Class<?>> classSet = ClassHelper.getBeanClassSet();
        for (Class<?> clazz: classSet){
            Object obj = ReflectionUtil.newInstance(clazz);
            BEAN_MAP.put(clazz, obj);
        }
    }

    /**
     * description: 获取bean映射
     * author: Administrator
     * date: 2018/12/14 14:38
     *
     * @param:
     * @return:
     */
    public static Map<Class<?>, Object> getBeanMap(){
        return BEAN_MAP;
    }


    /**
     * description: 获取bean实例
     * author: Administrator
     * date: 2018/12/14 14:39
     *
     * @param:
     * @return:
     */
    public static Object getBean(Class<?> clazz){
        if(!BEAN_MAP.containsKey(clazz)){
            throw new RuntimeException("can not get bean by class " + clazz);
        }
        return BEAN_MAP.get(clazz);
    }
}
