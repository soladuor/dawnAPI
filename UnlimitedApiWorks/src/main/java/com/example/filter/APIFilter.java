package com.example.filter;

import com.example.singleton.WhitelistSingleton;
import com.example.utils.IPUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@WebFilter(filterName = "APIFilter", urlPatterns = "/api/*")
public class APIFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        String ipAddress = IPUtil.getIpAddress((HttpServletRequest) request);
        Map<String, String> whiteMap = WhitelistSingleton.getInstance().getWhiteMap();
        String s = whiteMap.get(ipAddress);
        if (s != null) {
            // 白名单中有此ip
            // 获取请求总共次数，避免次数过多
            Integer times = (Integer) request.getServletContext().getAttribute("apiTimes");
            if (times == null) {
                times = 10000; // 初始化,默认1万次
            } else {
                times--; // 每次请求次数减1
            }
            request.getServletContext().setAttribute("apiTimes", times);
            if (times <= 0) {
                response.getWriter().write(
                        "{\"code\":400,\"message\":\"请求次数过多\"}"
                );
                return;
            }
            chain.doFilter(request, response); // 放行
        } else {
            response.getWriter().write(
                    "{\"code\":400,\"message\":\"ip " + ipAddress + "不在白名单中\"}"
            );
        }
    }

}
