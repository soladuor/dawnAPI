package com.soladuor.utils;

import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

public class BaseUtils {

    /**
     * 判断字符串是否为空
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * 判断多个字符串是否为空
     */
    public static boolean stringsIsEmpty(String... str) {
        for (String s : str) {
            if (s == null || s.length() == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * ascii 排序并拼接参数
     */
    public static StringBuilder asciiSortAndConcatenateParams(SortedMap<Object, Object> parameters) {
        StringBuilder stringA = new StringBuilder();
        Set<Map.Entry<Object, Object>> entrySet = parameters.entrySet();  // 所有参与传参的参数按照accsii排序（升序）
        for (Map.Entry<Object, Object> e : entrySet) {
            String key = (String) e.getKey();
            Object value = e.getValue();
            // 空值不传递，不参与签名组串
            if (null != value && !"".equals(value)) {
                stringA.append(key).append("=").append(value).append("&");
            }
        }
        return stringA;
    }
}
