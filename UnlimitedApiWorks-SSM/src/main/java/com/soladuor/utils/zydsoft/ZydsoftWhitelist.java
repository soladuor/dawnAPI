package com.soladuor.utils.zydsoft;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.soladuor.exception.GraceException;
import com.soladuor.service.IdentifierService;
import com.soladuor.utils.HttpUtils;
import com.soladuor.utils.IPUtils;
import com.soladuor.utils.crypto.AnalogCryptoJS;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ZydsoftWhitelist {

    private static final String passphrase = "nkldsx@#li8#$20.Ef(pBsZ3#th[M#_KW02a/:G/#VDss9";
    static ObjectMapper objectMapper = new ObjectMapper();
    static IdentifierService identifierService;

    // 从数据库中拿到工作室面板 Token 并解密
    private static String getToken() {
        String partnerToken = identifierService.getPartnerToken();
        return parseValue(partnerToken);
    }

    // 获取辩证云工作室面板接口路径
    private static String getBaseUrl() {
        String rootSrc = identifierService.getRootSrc();
        return rootSrc + "/partner/api";
    }

    /**
     * 加载当前配置（包括白名单）
     *
     * @return 响应结果（JSON字符串）
     */
    public static String request_load() {
        String load = "/partner/load?";
        String url = createRequestParams(null);
        HttpGet httpGet = new HttpGet(getBaseUrl() + load + url);
        configureRequestHeader(httpGet);
        return HttpUtils.doGet(httpGet, true);
    }

    /**
     * 修改当前配置
     *
     * @param postParams 请求体参数（JSON字符串）
     * @return 响应结果 成功 {"success":true} 失败 {"code":412,"message":"无效的sign"}
     */
    public static String request_modify(String postParams) {
        String modify = "/partner/modify?";
        String url = createRequestParams(postParams);
        HttpPost httpPost = new HttpPost(getBaseUrl() + modify + url);
        configureRequestHeader(httpPost);
        // String json = objectMapper.writeValueAsString(postParams); // 对象转JSON字符串
        String resp = HttpUtils.doPost(httpPost, postParams, true);
        // 修改之后需要调用一下 request_token_delete 函数
        String tokenDelete = request_token_delete();
        System.out.println("request_token_delete() = " + tokenDelete);
        return resp;
    }

    /**
     * 面板页面保存成功弹窗，在修改之后调用（不是很理解每为什么要调用，只用修改api也能修改成功）
     *
     * @return 响应结果 {"success":true}
     */
    protected static String request_token_delete() {
        String delete = "/token/delete?";
        String url = createRequestParams(null);
        HttpGet httpGet = new HttpGet(getBaseUrl() + delete + url);
        configureRequestHeader(httpGet);
        return HttpUtils.doGet(httpGet, true);
    }

    /**
     * 获取 ip 白名单
     *
     * @return 白名单列表
     */
    public static LinkedHashSet<String> getWhitelist() {
        try {
            // 获取配置
            String requestLoad = request_load();
            System.out.println("request_load = " + requestLoad);
            // 拿到白名单列表
            String whitelist = objectMapper.readTree(requestLoad).path("whitelist").toString();
            System.out.println("whitelist = " + whitelist);
            return objectMapper.readValue(whitelist, LinkedHashSet.class);
            // String[] strings = objectMapper.readValue(whitelist, String[].class);
            // return Arrays.asList(strings);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 设置白名单列表
     *
     * @return 设置成功返回 true
     */
    public static boolean setWhitelist(LinkedHashSet<String> whitelist) {
        // 最多可添加30个ip地址
        if (whitelist.size() > 30) {
            GraceException.display("最多可添加30个ip地址");
        }
        // 获取配置
        String requestLoad = request_load();
        // 将JSON中的白名单替换为新的白名单
        try {
            JsonNode jsonNode = objectMapper.readTree(requestLoad);
            // 获取白名单节点
            ArrayNode whitelistNode = (ArrayNode) jsonNode.get("whitelist");
            // 清空原有白名单
            whitelistNode.removeAll();
            // 添加新的白名单
            whitelist.forEach(whitelistNode::add);
            // 发送修改配置请求
            String requestModify = request_modify(jsonNode.toString());
            // 使用path避免空指针异常
            return objectMapper.readTree(requestModify).path("success").asBoolean();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 添加本机ip到白名单(只有在本机ip不在白名单中时才添加)
     *
     * @return 设置成功返回 true
     */
    public static boolean addNativeIpToWhitelist() throws JsonProcessingException {
        // 利用 LinkedHashSet 的特性去重，且可以保持原有顺序
        LinkedHashSet<String> list = getWhitelist();
        String ip = IPUtils.getNativeIp();
        if (IPUtils.isIp(ip)) {
            list.add(ip);
            return setWhitelist(list);
        }
        return true;
    }

    /**
     * 从加密的 token 或者 partnerId 中拿到值
     *
     * @param ciphertext 解密的信息
     */
    private static String parseValue(String ciphertext) {
        try {
            String json = AnalogCryptoJS.decrypt_AES_CBC(ciphertext, passphrase);
            JsonNode tokenNode = objectMapper.readTree(json);
            return tokenNode.get("value").asText();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 配置请求头信息
     *
     * @param httpRequestBase Http 请求
     */
    private static void configureRequestHeader(HttpRequestBase httpRequestBase) {
        String rootSrc = identifierService.getRootSrc();
        httpRequestBase.addHeader("Authorization", "Bearer " + getToken());
        httpRequestBase.addHeader("referer", rootSrc + "/partner/");
    }

    /**
     * 生成请求的 sign 签名
     *
     * @param params 请求参数（包括请求体）
     * @return sign 签名
     */
    private static String sign(Map<String, String> params) {
        ArrayList<String> list = new ArrayList<>();
        params.keySet().stream().sorted().forEach(key -> {
            String value = params.get(key);
            if (value != null && !value.isEmpty()) {
                list.add(key + "=" + value);
            }
        });

        list.add(getToken() + "&rqj3S0B6a19mREts2EREz3kqXU1Ik04A");
        // String[] j = new String[]{"a19mREts", "2EREz3kq"};
        // list.add(getToken() + "&rqj3S0B6" + String.join("", j) + "XU1Ik04A");

        return DigestUtils.sha256Hex(String.join("&", list)).toUpperCase();
    }

    /**
     * 生成请求链接参数
     *
     * @param postParams 请求体参数（JSON字符串）
     * @return sign字符
     */
    private static String createRequestParams(String postParams) {
        String partnerId = identifierService.getPartnerId();
        long timestamp = System.currentTimeMillis() / 1000;
        String language = "simplifiedChinese";
        // 构造参数
        HashMap<String, String> params = new LinkedHashMap<>();
        params.put("partnerId", partnerId);
        params.put("timestamp", String.valueOf(timestamp));
        params.put("language", language);

        // 构造请求参数字符串
        StringBuilder reqParamsBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            reqParamsBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        String reqParams = reqParamsBuilder.toString();

        // 有 post_params 就加上，没有就不加
        if (postParams != null && !postParams.isEmpty()) {
            params.put("post_params", postParams);
        }

        String signStr = sign(params);
        return reqParams + "sign=" + signStr;
    }

    @Autowired
    public void setIdentifierService(IdentifierService identifierService) {
        ZydsoftWhitelist.identifierService = identifierService;
    }
}
