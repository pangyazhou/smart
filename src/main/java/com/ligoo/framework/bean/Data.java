package com.ligoo.framework.bean;

/**
 * @Author: Administrator
 * @Date: 2018/12/14 15:45:18
 * @Description: 数据对象类
 */
public class Data {
    // 模型数据
    private Object model;

    public Data(Object model) {
        this.model = model;
    }

    public Object getModel() {
        return model;
    }
}
