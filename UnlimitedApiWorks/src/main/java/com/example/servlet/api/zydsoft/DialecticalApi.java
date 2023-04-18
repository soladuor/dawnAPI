package com.example.servlet.api.zydsoft;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.example.servlet.api.zydsoft.utils.DialecticalCloud;
import com.example.utils.BaseUtil;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

@WebServlet(name = "DialecticalApi", value = "/api/dialecticalApi")
public class DialecticalApi extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = request.getParameter("url");
        if (BaseUtil.isEmpty(url)) {
            response.getWriter().write("{\"code\":400,\"message\":\"参数不符合规则\"}");
            return;
        }
        SortedMap<Object, Object> parameters = reqParamTraver(request);
        response.getWriter().write(DialecticalCloud.doGet(url, parameters));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = request.getParameter("url");
        if (BaseUtil.isEmpty(url)) {
            response.getWriter().write("{\"code\":400,\"message\":\"参数不符合规则\"}");
            return;
        }
        SortedMap<Object, Object> param = reqParamTraver(request);
        // 获取请求头中的Content-Type字段
        String contentType = request.getContentType();
        try {
            if (contentType != null && contentType.contains("application/json")) {
                // 获取请求体中的JSON数据
                InputStream inputStream = request.getInputStream();
                // 将JSON数据转换为字符串
                String reqBody = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
                // 检查是否为JSON数据格式（失败会到try catch）
                JSONObject jsonObject = JSON.parseObject(reqBody);
                response.getWriter().write(DialecticalCloud.doPost(url, param, jsonObject));
            } else {
                response.getWriter().write("{\"code\":400,\"message\":\"contentType不符合规则\"}");
            }
        } catch (Exception e) {
            response.getWriter().write("{\"code\":400,\"message\":\"参数不符合规则\"}");
            throw new RuntimeException(e);
        }
    }

    /**
     * 遍历请求参数（不包括url）
     *
     * @param request 请求
     * @return 请求参数TreeMap
     */
    protected TreeMap<Object, Object> reqParamTraver(HttpServletRequest request) {
        TreeMap<Object, Object> parameters = new TreeMap<>();
        // request.getParameterMap()返回的是一个Map<String, String[]>类型的数据，得转一下
        // 遍历
        for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
            String key = entry.getKey();
            if (!"url".equals(key)) {
                String[] value = entry.getValue();
                if (value.length > 1) {
                    parameters.put(key, value);
                } else if (value.length == 1) {
                    parameters.put(key, value[0]);
                }
            }
        }
        return parameters;
    }
}
