package com.ligoo.framework.helper;

import com.ligoo.framework.annotation.Inject;
import com.ligoo.framework.util.ArrayUtil;
import com.ligoo.framework.util.CollectionUtil;
import com.ligoo.framework.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * @Author: Administrator
 * @Date: 2018/12/14 14:48:33
 * @Description: 控制反转助手类
 */
public class IocHelper {
    static {
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        if(CollectionUtil.isNotEmpty(beanMap)){
            //遍历beanMap
            for (Map.Entry<Class<?>, Object> beanEntry: beanMap.entrySet()){
                Class<?> beanClass = beanEntry.getKey();
                Object beanInstance = beanEntry.getValue();
                // 获取beanClass所有的成员变量
                Field[] fields = beanClass.getDeclaredFields();
                if(ArrayUtil.isNotEmpty(fields)){
                    // 遍历bean field
                    for (Field field: fields){
                        // 有Inject注解
                        if(field.isAnnotationPresent(Inject.class)){
                            Class<?> beanFieldClass = field.getType();
                            Object beanFieldInstance = BeanHelper.getBean(beanFieldClass);
                            if(beanFieldInstance != null){
                                ReflectionUtil.setField(beanInstance, field, beanFieldInstance);
                            }
                        }
                    }
                }
            }
        }
    }
}
