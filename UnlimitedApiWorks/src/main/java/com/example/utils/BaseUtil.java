package com.example.utils;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

public class BaseUtil {
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean stringsIsEmpty(String... str) {
        for (String s : str) {
            if (s == null || s.length() == 0) {
                return true;
            }
        }
        return false;
    }

    public static String md5Encryption9901(String str) {
        // Map<String, Integer> map = new HashMap<>();
        System.out.println(str);
        for (int i = 0; i < 9901; i++) {
            str = DigestUtils.md5Hex(str).toUpperCase();
            // map.put(str, i);
        }
        // System.out.println(str);
        // System.out.println(str.length());
        // System.out.println(map.size());
        return str;
    }

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
