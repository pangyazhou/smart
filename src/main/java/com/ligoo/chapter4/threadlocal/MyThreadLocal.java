package com.ligoo.chapter4.threadlocal;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Administrator
 * @Date: 2018/12/18 15:40:16
 * @Description: 线程局部变量实现
 */
public class MyThreadLocal <T>{
    private Map<Thread,T> container = Collections.synchronizedMap(new HashMap<>());

    public T get(){
        Thread thread = Thread.currentThread();
        T value = container.get(thread);
        if(value == null && !container.containsKey(thread)){
            value = initialValue();
            container.put(thread, value);
        }
        return value;
    }
    public void set(T value){
        container.put(Thread.currentThread(), value);
    }

    public void remove(){
        container.remove(Thread.currentThread());
    }
    protected T initialValue(){
        return null;
    }
}
