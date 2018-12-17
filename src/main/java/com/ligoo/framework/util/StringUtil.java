package com.ligoo.framework.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @Author: Administrator
 * @Date: 2018/12/12 15:26:26
 * @Description:字符串操作类
 */
public class StringUtil {
    /**
     * description: 判断字符串是否为空
     * author: Administrator
     * date: 2018/12/12 15:27
     *
     * @param:
     * @return:
     */
    public static boolean isEmpty(String str){
        if(str != null){
            str = str.trim();
        }
        return StringUtils.isEmpty(str);
    }

    /**
     * description: 判断字符串是否非空
     * author: Administrator
     * date: 2018/12/12 15:27
     *
     * @param:
     * @return:
     */
    public static boolean isNotEmpty(String str){
        return !isEmpty(str);
    }

    /**
     * description: 分隔字符串
     * author: Administrator
     * date: 2018/12/14 16:37
     *
     * @param:
     * @return:
     */
    public static String[] splitString(String str, String separatorChars){
        return StringUtils.split(str, separatorChars);
    }
}
