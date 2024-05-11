package com.soladuor.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

public class IPUtils {

    private final static String[] headerNames = {
            "x-forwarded-for", // Squid 服务代理
            "Proxy-Client-IP", // apache 服务代理
            "WL-Proxy-Client-IP", // WebLogic 服务代理
            "HTTP_CLIENT_IP", // 一些代理服务器
            "HTTP_X_FORWARDED_FOR",
            "X-Real-IP", // nginx 服务代理
    };

    private static final String IPBaseURL = "https://whois.pconline.com.cn/ipJson.jsp?json=true";
    private static String nativeIp = null; // 本机ip

    /**
     * 获取请求的ip地址
     *
     * @param request 请求
     * @return ip地址
     */
    public static String getIpAddress(HttpServletRequest request) {
        if (request == null) {
            return "unknown";
        }

        String ip = null;
        // 获取请求头中的 ip 地址
        for (String header : headerNames) {
            String value = request.getHeader(header);
            if (!isUnknown(value)) {
                ip = value;
                break;
            }
        }

        // 如果 ip 地址是 unknown，则直接使用 getRemoteAddr() 获取
        if (isUnknown(ip)) {
            ip = request.getRemoteAddr();
        }

        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : getMultistageReverseProxyIp(ip);
    }

    /**
     * 从多级反向代理中获得第一个非 unknown IP 地址
     *
     * @param ip 获得的IP地址
     * @return 第一个非 unknown IP地址
     */
    public static String getMultistageReverseProxyIp(String ip) {
        // 多级反向代理检测
        if (!StringUtils.isEmpty(ip)) {
            // 对于通过多个代理的情况，第一个IP为客户端真实IP，多个IP按照','分割
            final String[] ips = ip.trim().split(",");
            for (String subIp : ips) {
                if (!isUnknown(subIp)) {
                    return subIp;
                }
            }
        }
        return ip;
    }

    /**
     * 检测给字符串是否为 unknown 或者为空白
     *
     * @param checkString 被检测的字符串
     * @return 是否未知
     */
    public static boolean isUnknown(String checkString) {
        return StringUtils.isBlank(checkString) || "unknown".equalsIgnoreCase(checkString);
    }

    /**
     * 根据ip获取城市
     *
     * @param ip ip地址
     * @return 城市
     */
    public static String getCityByIP(String ip) throws JsonProcessingException {
        String respText = HttpUtils.doGet(IPBaseURL + "&ip=" + ip, true);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(respText);
        System.out.println(jsonNode);
        return jsonNode.get("addr").asText();
    }

    /**
     * 获取本机ip
     *
     * @return 本机ip
     */
    public static String getNativeIp() throws JsonProcessingException {
        if (nativeIp != null) {
            return nativeIp;
        }
        String respText = HttpUtils.doGet(IPBaseURL, true);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(respText);
        String ip = jsonNode.get("ip").asText();
        nativeIp = ip;
        return ip;
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
