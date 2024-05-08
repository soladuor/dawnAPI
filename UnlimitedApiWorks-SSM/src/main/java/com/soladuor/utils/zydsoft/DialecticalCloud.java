package com.soladuor.utils.zydsoft;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soladuor.service.IdentifierService;
import com.soladuor.utils.BaseHttpUtil;
import com.soladuor.utils.BaseUtil;
import com.soladuor.utils.ErrorLogger;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.SortedMap;

@Component
public class DialecticalCloud {
    private static String token = null; // 当前token

    static IdentifierService identifierService;

    // 使用set方法装配静态属性
    @Autowired
    public void setIdentifierService(IdentifierService identifierService) {
        DialecticalCloud.identifierService = identifierService;
    }

    private static String getBaseUrl() {
        return identifierService.getRootSrc() + "/open/api";
    }

    public static String doGet(String url, SortedMap<Object, Object> parameters) {
        String baseUrl = getBaseUrl();
        HttpGet httpGet = new HttpGet(baseUrl + "/" + url + "?" + createGetSign(parameters));
        httpGet.addHeader("Authorization", "Bearer " + token);
        // 如果发现客户端或服务器出现了类似于长时间等待或资源耗尽等问题，可以尝试使用"Connection: close"请求头关闭连接
        // httpGet.addHeader("Connection", "close");
        return BaseHttpUtil.doGet(httpGet, true);
    }

    public static String doPost(String url, SortedMap<Object, Object> param, String post_params) {
        String baseUrl = getBaseUrl();
        HttpPost httpPost = new HttpPost(baseUrl + "/" + url + "?" + createPostSign(param, post_params));
        httpPost.addHeader("Authorization", "Bearer " + token);
        httpPost.addHeader("Content-Type", "application/json");
        // 如果发现客户端或服务器出现了类似于长时间等待或资源耗尽等问题，可以尝试使用"Connection: close"请求头关闭连接
        // httpPost.addHeader("Connection", "close");
        return BaseHttpUtil.doPost(httpPost, post_params, true);
    }

    public static String createGetSign(SortedMap<Object, Object> parameters) {
        // long timestamp = System.currentTimeMillis() / 1000;
        // parameters.put("timestamp", timestamp); // 添加时间戳
        StringBuilder stringA = BaseUtil.asciiSortAndConcatenateParams(parameters);
        // 加密并转换为大写字符
        // stringA是StringBuffer类型，可以用stringA.toString()转换为String类型
        String sign = DigestUtils.sha256Hex(stringA + token).toUpperCase();
        return stringA + "sign=" + sign;
    }

    public static String createPostSign(SortedMap<Object, Object> param, String post_params) {
        // long timestamp = System.currentTimeMillis() / 1000;
        // param.put("timestamp", timestamp); // 添加时间戳
        String paramStr = BaseUtil.asciiSortAndConcatenateParams(param).toString();
        param.put("post_params", post_params);
        // 加密并转换为大写字符
        StringBuilder stringA = BaseUtil.asciiSortAndConcatenateParams(param);
        String sign = DigestUtils.sha256Hex(stringA + token).toUpperCase();
        return paramStr + "sign=" + sign;
    }

    public static String getTokenFromApi() {
        long timestamp = System.currentTimeMillis() / 1000;
        String baseUrl = getBaseUrl();
        IdentifierService service = identifierService;
        String partnerId = service.getPartnerId();
        String secret = service.getSecret();
        String url = baseUrl + "/token?partnerId=" + partnerId + "&secret=" + secret + "&timestamp=" + timestamp;
        String respText = BaseHttpUtil.doGet(url, true);
        // 成功的返回
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(respText);

            JsonNode newToken = jsonNode.get("token");
            // JsonNode expiresIn = jsonNode.get("expiresIn");
            if (newToken == null) {
                JsonNode code = jsonNode.get("code");
                JsonNode message = jsonNode.get("message");
                ErrorLogger.error("token返回值为空", code + " " + message);
            } else {
                token = newToken.asText();
                System.out.println("token更新成功: " + newToken);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return token;
    }

}
