package com.soladuor.utils;

import com.soladuor.exception.GraceException;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.Closeable;
import java.io.IOException;

public class HttpUtils {

    /**
     * 以get方式调用第三方接口
     *
     * @param url     请求的地址
     * @param isHttps 是否为 https 请求
     * @return 响应报文
     */
    public static String doGet(String url, boolean isHttps) {
        return doGet(new HttpGet(url), isHttps);
    }

    /**
     * 以get方式调用第三方接口（自定义请求头）
     *
     * @param httpGet get请求对象（可以提前配置一些自定义的非内置请求头）
     * @param isHttps 是否为 https 请求
     * @return 响应报文
     */
    public static String doGet(HttpGet httpGet, boolean isHttps) {
        return sendHttpRequest(httpGet, isHttps);
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
        return doPost(new HttpPost(url), paramEntity, isHttps);
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
        // 设置请求参数
        if (!BaseUtils.isEmpty(paramEntity)) {
            StringEntity entity = new StringEntity(paramEntity, ContentType.create("application/json", "utf-8"));
            httpPost.setEntity(entity);
        }
        return sendHttpRequest(httpPost, isHttps);
    }

    /**
     * 发送请求
     *
     * @param httpRequest 请求对象
     * @param isHttps     是否为 https 请求
     * @return 响应报文
     */
    public static String sendHttpRequest(HttpRequestBase httpRequest, boolean isHttps) {
        String responseContent = "{}"; // 响应报文
        // 1.创建HttpClient对象
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        try {
            // 1.配置https请求的一些参数
            httpClient = createHttpClient(isHttps);
            // 2.设置请求头信息
            httpRequest.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.81 Safari/537.36");
            // 3.执行请求
            response = httpClient.execute(httpRequest);
            // 4.处理响应信息
            responseContent = getUtf8ResponseEntity(response);
        } catch (Exception e) {
            e.printStackTrace();
            GraceException.display("发送请求出错, " + e.getMessage());
        } finally {
            // 关闭资源
            closeSource(httpClient, response);
        }
        return responseContent;
    }

    /**
     * 配置https请求的一些参数
     *
     * @param isHttps 是否为 https 请求
     * @return 配置后的 CloseableHttpClient 对象
     */
    protected static CloseableHttpClient createHttpClient(boolean isHttps) throws Exception {
        // 这里我加了一个是否需要创建一个https连接的判断
        if (isHttps) {
            // 配置https请求的一些参数 (loadTrustMaterial 里面忽略掉了对服务器端证书的校验)
            SSLContext sslContext = SSLContextBuilder.create().setProtocol(SSLConnectionSocketFactory.SSL).loadTrustMaterial((chain, authType) -> true).build();
            // 配置信息
            RequestConfig requestConfig = RequestConfig.custom()
                    // 设置连接超时时间(单位毫秒)
                    .setConnectTimeout(5000)
                    // 设置请求超时时间(单位毫秒)
                    // .setConnectionRequestTimeout(5000)
                    // socket 读写超时时间(单位毫秒)
                    .setSocketTimeout(5000)
                    // 设置是否允许重定向(默认为true)
                    // .setRedirectsEnabled(true)
                    .build();
            return HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).setSSLContext(sslContext).setSSLHostnameVerifier((x, y) -> true).build();
        } else {
            return HttpClientBuilder.create().build();
        }
    }

    /**
     * 判断是否响应成功
     *
     * @param response          响应报文
     * @param errDefaultContent 失败时默认响应报文内容
     * @return 响应报文内容
     * @throws IOException IO异常
     */
    protected static String http_200(CloseableHttpResponse response, String errDefaultContent) throws IOException {
        String responseEntity = getUtf8ResponseEntity(response);
        // Http 响应状态码不为 200 时
        // 注：部分 api 平台在返回错误信息时的错误状态码表现在 Http 状态码，而不是在响应的 data.code 中
        if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
            GraceException.display("请求失败, " + responseEntity);
            return errDefaultContent;
        }
        return responseEntity;
    }

    /**
     * 获取UTF-8响应报文内容
     *
     * @param response 响应报文
     * @return UTF-8响应报文内容
     * @throws IOException IO异常
     */
    protected static String getUtf8ResponseEntity(CloseableHttpResponse response) throws IOException {
        return EntityUtils.toString(response.getEntity(), "utf-8");
    }

    /**
     * 关闭资源
     */
    protected static void closeSource(Closeable... closeables) {
        try {
            // 遍历关闭资源
            for (Closeable closeable : closeables) {
                if (closeable != null) {
                    closeable.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
