package com.ligoo.chapter2.util;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import java.util.Collection;
import java.util.Map;

/**
 * @Author: Administrator
 * @Date: 2018/12/12 15:29:44
 * @Description:集合工具类
 */
public class CollectionUtil {
    /**
     * description: 判断集合为空
     * author: Administrator
     * date: 2018/12/12 15:31
     *
     * @param:
     * @return:
     */
    public static boolean isEmpty(Collection<?> collection){
        return CollectionUtils.isEmpty(collection);
    }

    /**
     * description: 判断集合非空
     * author: Administrator
     * date: 2018/12/12 15:31
     *
     * @param:
     * @return:
     */
    public static boolean isNotEmpty(Collection<?> collection){
        return !isEmpty(collection);
    }

    /**
     * description: 判断map为空
     * author: Administrator
     * date: 2018/12/12 15:31
     *
     * @param:
     * @return:
     */
    public static boolean isEmpty(Map<?,?> map){
        return MapUtils.isEmpty(map);
    }

    /**
     * description: 判断map非空
     * author: Administrator
     * date: 2018/12/12 15:31
     *
     * @param:
     * @return:
     */
    public static boolean isNotEmpty(Map<?,?> map){
        return !isEmpty(map);
    }
}
