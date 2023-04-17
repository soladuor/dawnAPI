package com.example.utils;

import com.alibaba.fastjson2.JSONObject;

import javax.servlet.http.HttpServletRequest;

public class IPUtil {
    public static String getIpAddress(HttpServletRequest request) {
        String sourceIp = null;
        /*
            X-Forwarded-For：Squid 服务代理
            Proxy-Client-IP：apache 服务代理
            WL-Proxy-Client-IP：weblogic 服务代理
            HTTP_CLIENT_IP：一些代理服务器
            X-Real-IP：nginx服务代理
            request.getRemoteAddr()直接获取
         */
        String ipAddresses = request.getHeader("x-forwarded-for");
        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            ipAddresses = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            ipAddresses = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            ipAddresses = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            ipAddresses = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            ipAddresses = request.getRemoteAddr();
        }
        // 对于通过多个代理的情况，第一个IP为客户端真实IP，多个IP按照','分割
        if (!BaseUtil.isEmpty(ipAddresses)) {
            sourceIp = ipAddresses.split(",")[0];
        }
        return sourceIp;
    }

    private static final String IPBaseURL = "https://whois.pconline.com.cn/ipJson.jsp?json=true";
    private static String nativeIp = null; // 本机ip

    public static String getCityByIP(String ip) {
        String respText = BaseHttpUtil.doGet(IPBaseURL + "&ip=" + ip, true);
        JSONObject jsonObject = JSONObject.parseObject(respText);
        System.out.println(jsonObject);
        if (nativeIp == null) {
            nativeIp = getNativeIp();
        }
        if (jsonObject.get("ip").equals(nativeIp)) {
            ErrorLogger.error("添加ip错误", ip + "为非正常ip");
            return "非正常ip";
        }
        return (String) JSONObject.parseObject(respText).get("addr");
    }

    public static String getNativeIp() {
        String respText = BaseHttpUtil.doGet(IPBaseURL, true);
        return (String) JSONObject.parseObject(respText).get("ip");
    }
}
