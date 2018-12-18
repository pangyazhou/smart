package com.ligoo.chapter4.aop;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @Author: Administrator
 * @Date: 2018/12/18 10:50:48
 * @Description:
 */
@Aspect
@Component
public class GreetingAspect {

    @Around("@annotation(com.ligoo.chapter4.aop.Tag)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        before();
        Object result = joinPoint.proceed();
        after();
        return result;
    }
    public void before(){
        System.out.println("before");
    }
    public void after(){
        System.out.println("after");
    }
}
