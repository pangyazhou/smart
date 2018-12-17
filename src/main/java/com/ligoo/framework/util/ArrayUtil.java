package com.ligoo.framework.util;

import org.apache.commons.lang3.ArrayUtils;

/**
 * @Author: Administrator
 * @Date: 2018/12/14 15:04:16
 * @Description:数组工具类
 */
public class ArrayUtil {
    /**
     * description: 判断数组是否为空
     * author: Administrator
     * date: 2018/12/14 15:05
     *
     * @param:
     * @return:
     */
    public static boolean isEmpty(Object[] array){
        return ArrayUtils.isEmpty(array);
    }

    /**
     * description: 判断数组是否为非空
     * author: Administrator
     * date: 2018/12/14 15:05
     *
     * @param:
     * @return:
     */
    public static boolean isNotEmpty(Object[] array){
        return !ArrayUtils.isEmpty(array);
    }
}
