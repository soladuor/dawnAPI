package com.example.servlet.api.zydsoft.utils;

import com.alibaba.fastjson2.JSONObject;
import com.example.singleton.IdentifierSingleton;
import com.example.utils.BaseHttpUtil;
import com.example.utils.ErrorLogger;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;

import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DialecticalCloud {
    private static String token = null; // 当前token

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public static String doGet(String url, SortedMap<Object, Object> parameters) {
        String baseUrl = IdentifierSingleton.getInstance().getBaseUrl();
        HttpGet httpGet = new HttpGet(baseUrl + url + "?" + createGetSign(parameters, false));
        httpGet.addHeader("Authorization", "Bearer " + token);
        return BaseHttpUtil.doGet(httpGet, true);
    }

    public static String doPost(String url, SortedMap<Object, Object> param, JSONObject post_params) {
        String baseUrl = IdentifierSingleton.getInstance().getBaseUrl();
        HttpPost httpPost = new HttpPost(baseUrl + url + "?" + createPostSign(param, post_params));
        httpPost.addHeader("Authorization", "Bearer " + token);
        httpPost.addHeader("Content-Type", "application/json");
        return BaseHttpUtil.doPost(httpPost, post_params, true);
    }

    public static String createGetSign(SortedMap<Object, Object> parameters, boolean isPost) {
        StringBuilder stringA = new StringBuilder();
        Set<Map.Entry<Object, Object>> entrySet = parameters.entrySet();  // 所有参与传参的参数按照accsii排序（升序）
        for (Map.Entry<Object, Object> e : entrySet) {
            String key = (String) e.getKey();
            Object value = e.getValue();
            // 空值不传递，不参与签名组串
            if (null != value && !"".equals(value)) {
                stringA.append(key + "=" + value + "&");
            }
        }
        if (isPost) {
            return stringA.toString();
        }
        // 加密并转换为大写字符
        // stringA是StringBuffer类型，可以用stringA.toString()转换为String类型
        String sign = DigestUtils.sha256Hex(stringA + token).toUpperCase();
        return stringA + "sign=" + sign;
    }

    public static String createPostSign(SortedMap<Object, Object> param, JSONObject post_params) {
        StringBuilder stringA = new StringBuilder();
        long timestamp = System.currentTimeMillis() / 1000;
        String paramStr = createGetSign(param, true);
        stringA.append(paramStr).append("post_params=").append(post_params)
                .append("&timestamp=").append(timestamp)
                .append("&");
        // 加密并转换为大写字符
        String sign = DigestUtils.sha256Hex(stringA + token).toUpperCase();
        return stringA + "&sign=" + sign;
    }

    public static String getTokenFromApi() {
        long timestamp = System.currentTimeMillis() / 1000;
        String baseUrl = IdentifierSingleton.getInstance().getBaseUrl();
        String partnerId = IdentifierSingleton.getInstance().getPartnerId();
        String secret = IdentifierSingleton.getInstance().getSecret();
        String url = baseUrl + "/token?partnerId=" + partnerId + "&secret=" + secret + "&timestamp=" + timestamp;
        String respText = BaseHttpUtil.doGet(url, true);
        // 成功的返回
        Object token = com.alibaba.fastjson2.JSONObject.parseObject(respText).get("token");
        // Object expiresIn = com.alibaba.fastjson2.JSONObject.parseObject(respText).get("expiresIn");
        if (token == null) {
            Object code = com.alibaba.fastjson2.JSONObject.parseObject(respText).get("code");
            Object message = com.alibaba.fastjson2.JSONObject.parseObject(respText).get("message");
            ErrorLogger.error("token返回值为空", code + " " + message);
        } else {
            System.out.println("token更新成功: " + token);
        }
        return (String) token;
    }


    public void start() {
        // 定时线程池，设置好频率（比如1Min）它会按照这个间隔按部就班的工作
        // 但是，如果其中一次调度任务卡住的话，不仅这次调度失败，而且整个线程池也会停在这次调度上
        // 解决方案是添加一个try catch
        final Runnable updateTask = new Runnable() {
            public void run() {
                try {
                    // 获取新token的代码
                    String newToken = DialecticalCloud.getTokenFromApi();
                    if (newToken != null) {
                        // 更新当前token
                        token = newToken;
                    } else {
                        ErrorLogger.error("token没有更新", "自动任务没有得到token");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        // 每隔7000秒执行一次更新任务
        scheduler.scheduleAtFixedRate(updateTask, 0, 7000, TimeUnit.SECONDS);
    }

    // 停止计时任务
    public void stop() {
        scheduler.shutdown();
    }

}
