package com.ligoo.framework.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * @Author: Administrator
 * @Date: 2018/12/18 13:34:53
 * @Description: 切面代理
 */
public abstract class AspectProxy implements Proxy {
    private static final Logger LOGGER = LoggerFactory.getLogger(AspectProxy.class);

    @Override
    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result = null;
        Class<?> clazz = proxyChain.getTargetClass();
        Method method = proxyChain.getTargetMethod();
        Object[] params = proxyChain.getMehtodParams();

        begin();
        try{
            if(intercept(clazz, method, params)){
                before(clazz, method, params);
                result = proxyChain.doProxyChain();
                after(clazz, method, params, result);
            }else {
                result = proxyChain.doProxyChain();
            }
        }catch (Exception e){
            LOGGER.error("proxy failure", e);
            error(clazz, method, params, e);
            throw e;
        }finally {
            end();
        }
        return result;
    }

    public void begin(){

    }
    public boolean intercept(Class<?> clazz, Method method, Object[] params) throws  Throwable{
        return true;
    }

    public void before(Class<?> clazz, Method method, Object[] params) throws  Throwable{

    }

    public void after(Class<?> clazz, Method method, Object[] params, Object result) throws Throwable{

    }
    public void error(Class<?> clazz, Method method, Object[] params, Throwable e){

    }

    public void end(){

    }
}
