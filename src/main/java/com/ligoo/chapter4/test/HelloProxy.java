package com.ligoo.chapter4.test;

/**
 * @Author: Administrator
 * @Date: 2018/12/17 14:26:35
 * @Description:
 */
public class HelloProxy implements Hello {
    Hello hello;

    public HelloProxy(Hello hello) {
        this.hello = hello;
    }

    @Override
    public void say() {
        before();
        hello.say();
        after();
    }
    private void before(){
        System.out.println("before");
    }
    private void after(){
        System.out.println("after");
    }
}
