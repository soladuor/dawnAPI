package com.example.filter.all;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "CrossDomainFilter", urlPatterns = "/*")
public class CrossDomainFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletResponse resp = (HttpServletResponse) response;
        // 解决跨域问题，允许所有域名访问
        resp.setHeader("Access-Control-Allow-Origin", "*");
        // 设置响应头信息以允许跨域访问
        resp.setHeader("Access-Control-Allow-Headers", "content-type");
        chain.doFilter(request, response);
    }
}
