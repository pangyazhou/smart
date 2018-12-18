package com.ligoo.framework.annotation;

import java.lang.annotation.*;

/**
 * @Author: Administrator
 * @Date: 2018/12/18 11:29:06
 * @Description: 切面注解
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {

    Class<? extends Annotation> value();
}
