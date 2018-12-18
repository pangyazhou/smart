package com.ligoo.chapter4.aop;

import org.springframework.aop.ThrowsAdvice;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @Author: Administrator
 * @Date: 2018/12/18 10:14:34
 * @Description:
 */
@Component
public class GreetingThrowAdvice implements ThrowsAdvice {
    public void afterThrowing(Method method, Object[] args, Object target, Exception e){
        System.out.println("--------------------Throw Exception-----------------------");
        System.out.println("Target Class: " + target.getClass().getName());
        System.out.println("Method name: " + method.getName());
        System.out.println("Exception message: " + e.getMessage());
        System.out.println("----------------------------------------------------------");
    }
}
