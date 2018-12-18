package com.ligoo.framework.helper;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.ligoo.framework.annotation.Aspect;
import com.ligoo.framework.proxy.AspectProxy;
import com.ligoo.framework.proxy.Proxy;
import com.ligoo.framework.proxy.ProxyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * @Author: Administrator
 * @Date: 2018/12/18 14:09:30
 * @Description: 切面助手类
 */
public final class AopHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(AopHelper.class);

    /**
     * description: 静态代码,初始化
     * author: Administrator
     * date: 2018/12/18 14:37
     *
     * @param:
     * @return:
     */
    static {
        try{
            Map<Class<?>, Set<Class<?>>> proxyMap = createProxyMap();
            Map<Class<?>, List<Proxy>> targetMap = createTargetMap(proxyMap);
            for (Map.Entry<Class<?>, List<Proxy>> proxyEntry: targetMap.entrySet()){
                Class<?> targetClass = proxyEntry.getKey();
                List<Proxy> proxyList = proxyEntry.getValue();
                Object proxy = ProxyManager.createProxy(targetClass, proxyList);
                BeanHelper.setBean(targetClass, proxy);
            }
        }catch (Exception e){
            LOGGER.error("aop failure", e);
        }
    }

    /**
     * description: 获取带有Aspect注解指定注解的所有类
     * author: Administrator
     * date: 2018/12/18 14:13
     *
     * @param:
     * @return:
     */
    private static Set<Class<?>> createTargetClassSet(Aspect aspect){
        Set<Class<?>> classSet = new HashSet<>();
        Class<? extends Annotation> annotation = aspect.value();
        if(annotation != null && !annotation.equals(Aspect.class)){
            classSet.addAll(ClassHelper.getClassSetByAnnotation(annotation));
        }
        return classSet;
    }

    /**
     * description: 获取所有的切面代理类与目标类的映射
     * author: Administrator
     * date: 2018/12/18 14:23
     *
     * @param:
     * @return:
     */
    private static Map<Class<?>, Set<Class<?>>> createProxyMap(){
        Map<Class<?>, Set<Class<?>>> proxyMap = new HashMap<>();
        // AspectProxy类的子类集合
        Set<Class<?>> proxyClassSet = ClassHelper.getClassSetBySuper(AspectProxy.class);
        for (Class<?> clazz: proxyClassSet){
            // 有Aspect注解的类
            if(clazz.isAnnotationPresent(Aspect.class)){
                Aspect aspect = clazz.getAnnotation(Aspect.class);
                // 有Aspect注解中注解类的类
                Set<Class<?>> targetClassSet = createTargetClassSet(aspect);
                proxyMap.put(clazz, targetClassSet);
            }
        }
        return proxyMap;
    }


    /**
     * description: 获取目标类与代理对象集合的映射
     * author: Administrator
     * date: 2018/12/18 14:34
     *
     * @param: 
     * @return: 
     */
    private static Map<Class<?>, List<Proxy>> createTargetMap(Map<Class<?>, Set<Class<?>>> proxyMap) throws Exception {
        Map<Class<?>, List<Proxy>> targetMap = new HashMap<>();
        for (Map.Entry<Class<?>, Set<Class<?>>> proxyEntry: proxyMap.entrySet()){
            Class<?> proxyClass = proxyEntry.getKey();
            Set<Class<?>> targetClassSet = proxyEntry.getValue();
            for (Class<?> targetClass: targetClassSet){
                Proxy proxy = (Proxy) proxyClass.newInstance();
                if(targetMap.containsKey(targetClass)){
                    targetMap.get(targetClass).add(proxy);
                }else {
                    List<Proxy> proxyList = new ArrayList<>();
                    proxyList.add(proxy);
                    targetMap.put(targetClass, proxyList);
                }
            }
        }
        return targetMap;
    }
}
