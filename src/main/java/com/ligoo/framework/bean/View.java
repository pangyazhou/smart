package com.ligoo.framework.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Administrator
 * @Date: 2018/12/14 15:42:38
 * @Description: 视图解析类
 */
public class View {
    // 视图路径
    private String path;
    // 模型数据
    private Map<String, Object> model;

    public View(String path) {
        this.path = path;
        model = new HashMap<>();
    }

    /**
     * description: 为视图添加模型数据
     * author: Administrator
     * date: 2018/12/14 15:44
     *
     * @param:
     * @return:
     */
    public View addModel(String key, Object value){
        model.put(key, value);
        return this;
    }


    public String getPath() {
        return path;
    }

    public Map<String, Object> getModel() {
        return model;
    }
}
