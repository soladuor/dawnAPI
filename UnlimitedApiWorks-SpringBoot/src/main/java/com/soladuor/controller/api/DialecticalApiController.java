package com.soladuor.controller.api;

import com.soladuor.utils.zydsoft.DialecticalCloud;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

@RestController
@RequestMapping("/api/dialecticalApi")
public class DialecticalApiController {

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

    @PostMapping(consumes = {"application/json"})
    public Object postDialecticalApi(@RequestParam String url, @RequestBody String reqBody, HttpServletRequest request) {
        SortedMap<Object, Object> param = reqParamTraver(request);
        return DialecticalCloud.doPost(url, param, reqBody);
    }

}
