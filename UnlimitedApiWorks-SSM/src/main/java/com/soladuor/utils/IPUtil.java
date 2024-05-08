package com.soladuor.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

public class IPUtil {
    private static final String IPBaseURL = "https://whois.pconline.com.cn/ipJson.jsp?json=true";
    private static String nativeIp = null; // 本机ip

    /**
     * 获取请求的ip地址
     *
     * @param request 请求
     * @return ip地址
     */
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

    /**
     * 根据ip获取城市
     *
     * @param ip ip地址
     * @return 城市
     */
    public static String getCityByIP(String ip) throws JsonProcessingException {
        String respText = BaseHttpUtil.doGet(IPBaseURL + "&ip=" + ip, true);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(respText);
        System.out.println(jsonNode);
        if (nativeIp == null) {
            nativeIp = getNativeIp();
        }
        if (jsonNode.get("ip").asText().equals(nativeIp)) {
            ErrorLogger.error("添加ip错误", ip + "为非正常ip");
            return "非正常ip";
        }
        return jsonNode.get("addr").asText();
    }

    /**
     * 获取本机ip
     *
     * @return 本机ip
     */
    public static String getNativeIp() throws JsonProcessingException {
        String respText = BaseHttpUtil.doGet(IPBaseURL, true);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(respText);
        return jsonNode.get("ip").asText();
    }

    /**
     * 判断是否为ip地址
     *
     * @param ip ip地址
     * @return 是否为ip地址
     */
    public static boolean isIp(String ip) {
        if (ip == null) return false;
        // 正则表达式验证IP地址
        String regex = "^(?:(?:1\\d{2}|2[0-4]\\d|25[0-5]|\\d\\d?)\\.){3}(?:1\\d{2}|2[0-4]\\d|25[0-5]|\\d\\d?)$";
        return Pattern.matches(regex, ip);
    }

}
