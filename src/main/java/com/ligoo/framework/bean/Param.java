package com.ligoo.framework.bean;


import com.ligoo.framework.util.CastUtil;
import com.ligoo.framework.util.CollectionUtil;

import java.util.Map;

/**
 * @Author: Administrator
 * @Date: 2018/12/14 15:38:45
 * @Description:请求参数包装类
 */
public class Param {
    private Map<String,Object> paramMap;

    public Param(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    /**
     * description: 获取Long型参数值
     * author: Administrator
     * date: 2018/12/14 15:40
     *
     * @param:
     * @return:
     */
    public long getLong(String name){
        return CastUtil.castLong(paramMap.get(name));
    }

    /**
     * description: 获取所有字段信息
     * author: Administrator
     * date: 2018/12/14 15:39
     *
     * @param:
     * @return:
     */
    public Map<String, Object> getParamMap() {
        return paramMap;
    }

    public boolean isEmpty(){
        return CollectionUtil.isEmpty(paramMap);
    }
}
