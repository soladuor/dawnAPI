package com.example.servlet.api.tencent;

import com.example.servlet.api.tencent.utils.TencentCloudNpl;
import com.example.utils.BaseUtil;
import com.example.utils.ErrorLogger;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.nlp.v20190408.NlpClient;
import com.tencentcloudapi.nlp.v20190408.models.AutoSummarizationRequest;
import com.tencentcloudapi.nlp.v20190408.models.AutoSummarizationResponse;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
不安全的方式（直接把秘钥放到代码）：
Credential cred = new Credential("SecretId", "SecretKey");
采用更安全的方式来使用密钥：
   1. 读取环境变量 TENCENTCLOUD_SECRET_ID 和 TENCENTCLOUD_SECRET_KEY 获取 secretId 和 secretKey
   代码：Credential cred = new EnvironmentVariableCredentialsProvider().getCredentials();
   2. 配置文件
   路径要求为：
   Windows: c:\Users\NAME\.tencentcloud\credentials
   Linux: ~/.tencentcloud/credentials 或 /etc/tencentcloud/credentials
   格式：
   [default]
   secret_id = xxxxx
   secret_key = xxxxx
   代码：Credential cred = new ProfileCredentialsProvider().getCredentials();
   3. 实例角色 略
   4. 凭证提供链
   腾讯云 Java SDK 提供了凭证提供链，它会默认以环境变量->配置文件->实例角色的顺序尝试获取凭证，并返回第一个获取到的凭证
   Credential cred = new DefaultCredentialsProvider().getCredentials();
System.getenv("xxx")可以拿到环境变量的信息
*/

/**
 * 自动摘要
 */
@WebServlet(name = "AutoSummarization", value = "/api/nlp/AutoSummarization")
public class AutoSummarization extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String text = request.getParameter("Text");
        if (BaseUtil.isEmpty(text)) {
            // text = "";
            response.getWriter().write("{\"code\":400,\"message\":\"text不能为空\"}");
            return;
        }
        try {
            NlpClient client = TencentCloudNpl.NlpClientConfig(
                    "nlp.tencentcloudapi.com",
                    "ap-guangzhou");
            // 实例化一个请求对象,每个接口都会对应一个request对象
            AutoSummarizationRequest req = new AutoSummarizationRequest();
            req.setText(text);
            String length = request.getParameter("Length");
            if (length != null) {
                req.setLength(Long.parseLong(length));
            }
            // 返回的resp是一个AutoSummarizationResponse的实例，与请求对象对应
            AutoSummarizationResponse resp = client.AutoSummarization(req);
            // 输出json格式的字符串回包
            // System.out.println(AutoSummarizationResponse.toJsonString(resp));
            response.getWriter().write(AutoSummarizationResponse.toJsonString(resp));
        } catch (TencentCloudSDKException e) {
            ErrorLogger.logException("腾讯云SDK获取摘要报错", e);
            response.getWriter().write("{\"code\":400,\"message\":\"" + e.getMessage() + "\"}");
        }
    }
}
