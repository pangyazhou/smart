package com.ligoo.framework;

import com.ligoo.framework.helper.*;
import com.ligoo.framework.util.ClassUtil;

/**
 * @Author: Administrator
 * @Date: 2018/12/14 15:34:11
 * @Description: 助手类加载器
 */
public class HelperLoader {
    public static void init(){
        Class<?>[] classList = {
                ClassHelper.class,
                BeanHelper.class,
                AopHelper.class,
                IocHelper.class,
                ControllerHelper.class
        };
        for (Class<?> clazz: classList){
            ClassUtil.loadClass(clazz.getName(), true);
        }
    }
}
