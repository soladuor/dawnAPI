package com.soladuor.controller.interceptor;

import com.soladuor.pojo.Whitelist;
import com.soladuor.service.WhitelistService;
import com.soladuor.utils.IPUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Slf4j
@Component
public class ApiInterceptor implements HandlerInterceptor {

    @Autowired
    WhitelistService whitelistService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ipAddress = IPUtils.getIpAddress(request);
        List<Whitelist> whiteList = whitelistService.getWhiteList();
        for (Whitelist white : whiteList) {
            String whiteIp = white.getIpAddress();
            if (whiteIp.equals(ipAddress) || whiteIp.equals("0.0.0.0")) {
                // 白名单中有此ip或者有0.0.0.0保留ip
                // 获取请求总共次数，避免次数过多
                Integer times = (Integer) request.getServletContext().getAttribute("apiTimes");
                // 已经在 WebConfig 的 onStartup 中设置了
                // if (times == null) times = 10000; // 初始化,默认1万次
                if (times <= 0) {
                    response.getWriter().write(
                            "{\"code\":400,\"message\":\"请求次数过多\"}"
                    );
                    return false;
                }
                times--; // 每次请求次数减1
                request.getServletContext().setAttribute("apiTimes", times);
                return true; // 放行
            }
        }
        response.getWriter().write(
                "{\"code\":400,\"message\":\"ip " + ipAddress + "不在白名单中\"}"
        );
        return false;
    }
}
