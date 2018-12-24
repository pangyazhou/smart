package com.ligoo.chapter4.test;

import java.util.LinkedHashMap;

/**
 * @Author: Administrator
 * @Date: 2018/12/17 14:26:06
 * @Description:
 */
public class Test {
    public static void main(String[] args){
        Hello hello = new HelloImpl();
        hello.say();
        System.out.println("================");
        Hello helloProxy = new HelloProxy(hello);
        helloProxy.say();
        System.out.println("----------------");
        Hello helloDynamicProxy = new DynamicProxy(hello).getProxy();
        helloDynamicProxy.say();
        System.out.println("#####################");
        Hello helloCglibProxy =CglibProxy.getInstance().getProxy(HelloImpl.class);
        helloCglibProxy.say();
    }
}
