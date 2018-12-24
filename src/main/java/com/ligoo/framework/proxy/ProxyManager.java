package com.ligoo.framework.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @Author: Administrator
 * @Date: 2018/12/18 11:43:08
 * @Description: 代理类管理器
 */
public class ProxyManager {
    /**
     * description: 获取代理对象
     * author: Administrator
     * date: 2018/12/20 13:31
     *
     * @param:
     * @return:
     */
    @SuppressWarnings("unchecked")
    public static <T>  T createProxy(final Class<?> targetClass, final List<Proxy> proxyList){
        return (T) Enhancer.create(targetClass, new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                return new ProxyChain(targetClass, obj, method, proxy, args, proxyList).doProxyChain();
            }
        });
    }
}
