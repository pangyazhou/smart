package com.ligoo.chapter4.test;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @Author: Administrator
 * @Date: 2018/12/17 14:41:07
 * @Description:
 */
public class CglibProxy implements MethodInterceptor {

    private static CglibProxy instance = new CglibProxy();

    public static CglibProxy getInstance(){
        return instance;
    }

    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<T> clazz){
        return (T) Enhancer.create(clazz, this);
    }
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        before();
        Object result = methodProxy.invokeSuper(obj, args);
        after();
        return result;
    }
    private void before(){
        System.out.println("cglib before");
    }
    private void after(){
        System.out.println("cglib after");
    }
}
