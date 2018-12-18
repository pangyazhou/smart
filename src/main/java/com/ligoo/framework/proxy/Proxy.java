package com.ligoo.framework.proxy;

/**
 * @Author: Administrator
 * @Date: 2018/12/18 11:30:45
 * @Description: 代理接口
 */
public interface Proxy {
    /**
     * description: 执行链式代理
     * author: Administrator
     * date: 2018/12/18 11:31
     *
     * @param:
     * @return:
     */
    Object doProxy(ProxyChain proxyChain) throws Throwable;
}
