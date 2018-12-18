package com.ligoo.chapter4.aop;

import org.springframework.aop.framework.ProxyFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author: Administrator
 * @Date: 2018/12/18 09:48:32
 * @Description:
 */
public class Test {
    public static void main(String[] args){
       new Test().testAspect();
    }
    public void testBeforeAndAfterAdvice(){
        Greeting greeting = new GreetingImpl();
        greeting.sayName("pangyazhou");
        System.out.println("=====================");
        ProxyFactory factory = new ProxyFactory();
        factory.setTarget(greeting);
        factory.addAdvice(new GreetingBeforeAndAfterAdvice());
        Greeting greetingProxy = (Greeting) factory.getProxy();
        greetingProxy.sayName("pangyazhou");
        greetingProxy.sayBye();
    }
    public void testAroundAdvice(){
        Greeting greeting = new GreetingImpl();
        greeting.sayName("pangyazhou");
        System.out.println("=====================");
        ProxyFactory factory = new ProxyFactory();
        factory.setTarget(greeting);
        factory.addAdvice(new GreetingAroundAdvice());
        Greeting greetingProxy = (Greeting) factory.getProxy();
        greetingProxy.sayName("pangyazhou");
        greetingProxy.sayBye();
    }

    public void testXmlConfig(){
        ApplicationContext context = new ClassPathXmlApplicationContext("com/ligoo/chapter4/aop/Spring.xml");
        Greeting greeting = (Greeting) context.getBean("greetingProxy");
        greeting.sayName("pangyazhou");
        greeting.sayBye();
    }
    public void testClassProxy(){
        ApplicationContext context = new ClassPathXmlApplicationContext("com/ligoo/chapter4/aop/Spring.xml");
        GreetingImpl greetingProxy = (GreetingImpl) context.getBean("greetingProxy");
        greetingProxy.sayName("pangyazhou");
        greetingProxy.sayBye();

        Apology apology = (Apology) greetingProxy;
        apology.saySorry("lixiaolong");

    }
    public void testAspect(){
        ApplicationContext context = new ClassPathXmlApplicationContext("com/ligoo/chapter4/aop/Spring.xml");
        Greeting greetingProxy = (GreetingImpl) context.getBean("greetingImpl");
        greetingProxy.sayName("pangyazhou");
        greetingProxy.sayBye();
        ((GreetingImpl) greetingProxy).goodMorning("pangyazhou");
        ((GreetingImpl) greetingProxy).goodNight("pangyazhou");
    }
}
