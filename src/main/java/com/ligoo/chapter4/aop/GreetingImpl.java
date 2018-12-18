package com.ligoo.chapter4.aop;

import org.springframework.stereotype.Component;

/**
 * @Author: Administrator
 * @Date: 2018/12/18 09:48:10
 * @Description:
 */
@Component
public class GreetingImpl implements Greeting {
    @Override
    public void sayName(String name) {
        System.out.println("hello," + name);
    }

    @Override
    public void sayBye() {
        System.out.println("good bye!");
    }

    @Tag
    public void goodMorning(String name){
        System.out.println("Good Morning " + name);
    }
    public void goodNight(String name){
        System.out.println("Good Night " + name);
    }

}
