package com.ligoo.framework.helper;

import com.ligoo.framework.annotation.Action;
import com.ligoo.framework.bean.Handler;
import com.ligoo.framework.bean.Request;
import com.ligoo.framework.util.ArrayUtil;
import com.ligoo.framework.util.CollectionUtil;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Author: Administrator
 * @Date: 2018/12/14 15:15:03
 * @Description: 控制器助手类
 */
public class ControllerHelper {
    // 存放请求与处理器的映射关系
    private static final Map<Request,Handler> ACTION_MAP = new HashMap<>();
    static {
        Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
        if(CollectionUtil.isNotEmpty(controllerClassSet)) {
            // 遍历controllerClassSet
            for (Class<?> clazz : controllerClassSet) {
                // 获取Controller类中定义的方法
                Method[] methods = clazz.getDeclaredMethods();
                if(ArrayUtil.isNotEmpty(methods)){
                    // 遍历方法数组
                    for (Method method: methods){
                        if(method.isAnnotationPresent(Action.class)){
                            Action action = method.getAnnotation(Action.class);
                            String mapping = action.value();
                            if(mapping.matches("\\w+:/\\w*")){
                                String[] array = mapping.split(":");
                                if(ArrayUtil.isNotEmpty(array) && array.length == 2){
                                    String requestMethod = array[0];
                                    String requestPath = array[1];
                                    Request request = new Request(requestMethod, requestPath);
                                    Handler handler = new Handler(clazz, method);
                                    ACTION_MAP.put(request, handler);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * description: 根据请求方法与路径获取处理类
     * author: Administrator
     * date: 2018/12/14 15:31
     *
     * @param:
     * @return:
     */
    public static Handler getHandler(String requestMethod, String requestPath){
        Request request = new Request(requestMethod, requestPath);
        return ACTION_MAP.get(request);
    }
}
