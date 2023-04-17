package com.example.servlet.api.tencent;

import com.example.singleton.IdentifierSingleton;
import com.example.utils.BaseUtil;
import com.example.utils.ErrorLogger;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.nlp.v20190408.NlpClient;
import com.tencentcloudapi.nlp.v20190408.models.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "KeywordsExtraction", value = "/api/tencent/KeywordsExtraction")
public class KeywordsExtraction extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String text = request.getParameter("Text");
        if (BaseUtil.isEmpty(text)) {
            // text = "";
            response.getWriter().write("{\"code\":400,\"message\":\"text不能为空\"}");
            return;
        }
        try {
            String SecretId = IdentifierSingleton.getInstance().getTencentSecretId();
            String SecretKey = IdentifierSingleton.getInstance().getTencentSecretKey();
            Credential cred = new Credential(SecretId, SecretKey);
            // 实例化一个http选项，可选的，没有特殊需求可以跳过
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("nlp.tencentcloudapi.com");
            // 实例化一个client选项，可选的，没有特殊需求可以跳过
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            // 实例化要请求产品的client对象,clientProfile是可选的
            NlpClient client = new NlpClient(cred, "ap-guangzhou", clientProfile);
            // 实例化一个请求对象,每个接口都会对应一个request对象
            KeywordsExtractionRequest req = new KeywordsExtractionRequest();
            req.setText(text);
            String n = request.getParameter("Num");
            if (n != null) {
                req.setNum(Long.parseLong(n));
            }
            // 返回的resp是一个KeywordsExtractionResponse的实例，与请求对象对应
            KeywordsExtractionResponse resp = client.KeywordsExtraction(req);
            // 输出json格式的字符串回包
            // System.out.println(KeywordsExtractionResponse.toJsonString(resp));
            response.getWriter().write(KeywordsExtractionResponse.toJsonString(resp));
        } catch (TencentCloudSDKException e) {
            ErrorLogger.logException("腾讯云SDK获取摘要报错", e);
            response.getWriter().write("{\"code\":400,\"message\":\"" + e.getMessage() + "\"}");
        }
    }
}
