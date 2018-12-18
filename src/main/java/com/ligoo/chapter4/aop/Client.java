package com.ligoo.chapter4.aop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author: Administrator
 * @Date: 2018/12/18 10:56:38
 * @Description:
 */
public class Client {
    public static void main(String[] args){
        ApplicationContext context = new ClassPathXmlApplicationContext("com/ligoo/chapter4/aop/Spring.xml");
        GreetingImpl greetingProxy = (GreetingImpl) context.getBean("greetingImpl");
        greetingProxy.sayName("pangyazhou");
        greetingProxy.goodMorning("lixiaolong");
    }
}
