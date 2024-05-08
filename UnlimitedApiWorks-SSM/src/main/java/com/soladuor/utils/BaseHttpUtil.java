package com.soladuor.utils;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.IOException;

public class BaseHttpUtil {
    /**
     * 配置https请求的一些参数
     *
     * @param isHttps 是否为 https 请求
     * @return 配置后的 CloseableHttpClient 对象
     */
    protected static CloseableHttpClient httpsConfig(boolean isHttps) throws Exception {
        CloseableHttpClient httpClient;
        // 这里我加了一个是否需要创建一个https连接的判断
        if (isHttps) {
            // 配置https请求的一些参数
            SSLContext sslContext = SSLContextBuilder.create().useProtocol(SSLConnectionSocketFactory.SSL).loadTrustMaterial((x, y) -> true).build();
            RequestConfig config = RequestConfig.custom().setConnectTimeout(5000).setSocketTimeout(5000).build();
            httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).setSSLContext(sslContext).setSSLHostnameVerifier((x, y) -> true).build();
        } else {
            httpClient = HttpClientBuilder.create().build();
        }
        return httpClient;
    }

    /**
     * 处理响应报文
     *
     * @param response        响应报文
     * @param responseContent 响应报文内容
     * @return 响应报文内容
     * @throws IOException IO异常
     */
    protected static String httpOK(CloseableHttpResponse response, String responseContent) throws IOException {
        // org.apache.http.HttpStatus
        // if (response.getStatusLine().getStatusCode() == org.apache.http.HttpStatus.SC_OK) {
        responseContent = EntityUtils.toString(response.getEntity(), "utf-8");
        // } else {
        //     ErrorLogger.error("请求失败", EntityUtils.toString(response.getEntity(), "utf-8"));
        // }
        return responseContent;
    }

    /**
     * 以get方式调用第三方接口
     *
     * @param url     请求的地址
     * @param isHttps 是否为 https 请求
     * @return 响应报文
     */
    public static String doGet(String url, boolean isHttps) {
        String responseContent = "{}"; // 响应报文
        // 1.创建HttpClient对象
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        try {
            // 配置https请求的一些参数
            httpClient = httpsConfig(isHttps);
            // 2.生成get请求对象，并设置请求头信息
            HttpGet httpGet = new HttpGet(url);
            httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.81 Safari/537.36");
            // 3.执行请求
            response = httpClient.execute(httpGet);
            // 4.处理响应信息
            responseContent = httpOK(response, responseContent);
        } catch (Exception e) {
            e.printStackTrace();
            ErrorLogger.logException("baseGet请求出错", e);
        } finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseContent;
    }

    /**
     * 以get方式调用第三方接口（自定义请求头）
     *
     * @param httpGet get请求对象（可以提前配置一些自定义的非内置请求头）
     * @param isHttps 是否为 https 请求
     * @return 响应报文
     */
    public static String doGet(HttpGet httpGet, boolean isHttps) {
        String responseContent = "{}"; // 响应报文
        // 1.创建HttpClient对象
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        try {
            // 配置https请求的一些参数
            httpClient = httpsConfig(isHttps);
            // 2.生成get请求对象，并设置基础请求头信息
            httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.81 Safari/537.36");
            // 3.执行请求
            response = httpClient.execute(httpGet);
            // 4.处理响应信息
            responseContent = httpOK(response, responseContent);
        } catch (Exception e) {
            e.printStackTrace();
            ErrorLogger.logException("baseGet请求出错", e);
        } finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseContent;
    }

    /**
     * 以post方式调用第三方接口
     *
     * @param url         请求的地址
     * @param paramEntity 请求体
     * @param isHttps     是否为 https 请求
     * @return 响应报文
     */
    public static String doPost(String url, String paramEntity, boolean isHttps) {
        String responseContent = "{}"; // 响应报文
        // 1.创建HttpClient对象
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        try {
            // 配置https请求的一些参数
            httpClient = httpsConfig(isHttps);
            // 2.生成post请求对象，并设置请求头信息
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.81 Safari/537.36");
            // 3.设置请求参数
            if (!BaseUtil.isEmpty(paramEntity)) {
                StringEntity entity = new StringEntity(paramEntity, ContentType.create("application/json", "utf-8"));
                httpPost.setEntity(entity);
            }
            // 4.执行请求
            response = httpClient.execute(httpPost);
            // 5.处理响应信息
            responseContent = httpOK(response, responseContent);
        } catch (Exception e) {
            e.printStackTrace();
            ErrorLogger.logException("basePost请求出错", e);
        } finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseContent;
    }

    /**
     * 以post方式调用第三方接口（自定义请求头）
     *
     * @param httpPost    post请求对象（可以提前配置一些自定义的非内置请求头）
     * @param paramEntity 请求体
     * @param isHttps     是否为 https 请求
     * @return 响应报文
     */
    public static String doPost(HttpPost httpPost, String paramEntity, boolean isHttps) {
        String responseContent = "{}"; // 响应报文
        // 1.创建HttpClient对象
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        try {
            // 配置https请求的一些参数
            httpClient = httpsConfig(isHttps);
            // 2.生成post请求对象，并设置请求头信息
            httpPost.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.81 Safari/537.36");
            // 3.设置请求参数
            if (!BaseUtil.isEmpty(paramEntity)) {
                StringEntity entity = new StringEntity(paramEntity, ContentType.create("application/json", "utf-8"));
                httpPost.setEntity(entity);
            }
            // 4.执行请求
            response = httpClient.execute(httpPost);
            // 5.处理响应信息
            responseContent = httpOK(response, responseContent);
        } catch (Exception e) {
            e.printStackTrace();
            ErrorLogger.logException("basePost请求出错", e);
        } finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseContent;
    }
}
