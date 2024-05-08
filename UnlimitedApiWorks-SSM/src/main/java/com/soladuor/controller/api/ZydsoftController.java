package com.soladuor.controller.api;

import com.soladuor.utils.ErrorLogger;
import com.soladuor.utils.result.JSONResult;
import com.soladuor.utils.zydsoft.DialecticalCloud;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

@RestController
@RequestMapping("/api/dialecticalApi")
public class ZydsoftController {

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

    @GetMapping
    public Object getDialecticalApi(@RequestParam String url, HttpServletRequest request) {
        SortedMap<Object, Object> parameters = reqParamTraver(request);
        return DialecticalCloud.doGet(url, parameters);
    }

    @PostMapping
    public Object postDialecticalApi(@RequestParam String url, @RequestBody String reqBody, HttpServletRequest request) {
        SortedMap<Object, Object> param = reqParamTraver(request);
        // 获取请求头中的Content-Type字段
        String contentType = request.getContentType();
        try {
            if (contentType != null && contentType.contains("application/json")) {
                // 检查是否为JSON数据格式（失败会到try catch）
                // JSONObject jsonObject = JSON.parseObject(reqBody);
                return DialecticalCloud.doPost(url, param, reqBody);
            } else {
                return JSONResult.build(400, "contentType不符合规则", null);
            }
        } catch (Exception e) {
            ErrorLogger.logException("辩证云请求异常", e);
            return JSONResult.build(400, "参数不符合规则 reqBody: " + reqBody, null);
        }
    }

}
