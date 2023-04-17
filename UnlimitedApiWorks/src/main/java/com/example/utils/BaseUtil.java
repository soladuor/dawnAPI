package com.example.utils;

import org.apache.commons.codec.digest.DigestUtils;

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
}
