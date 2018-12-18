package com.ligoo.chapter4.aspect;

import com.ligoo.framework.annotation.Aspect;
import com.ligoo.framework.annotation.Controller;
import com.ligoo.framework.proxy.AspectProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * @Author: Administrator
 * @Date: 2018/12/18 13:50:43
 * @Description:
 */
@Aspect(Controller.class)
public class ControllerAspect extends AspectProxy {
    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerAspect.class);
    private long begin;

    @Override
    public void before(Class<?> clazz, Method method, Object[] params) throws Throwable {
        LOGGER.debug("------------------begin--------------------------");
        LOGGER.debug(String.format("class: %s", clazz.getName()));
        LOGGER.debug(String.format("method: %s", method.getName()));
        begin = System.currentTimeMillis();
    }

    @Override
    public void after(Class<?> clazz, Method method, Object[] params, Object result) throws Throwable {
        LOGGER.debug(String.format("time: %dms", System.currentTimeMillis() - begin));
        LOGGER.debug("------------------end----------------------------");
    }
}
