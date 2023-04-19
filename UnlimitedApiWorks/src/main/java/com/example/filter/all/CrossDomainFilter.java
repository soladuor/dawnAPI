package com.example.filter.all;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "CrossDomainFilter")
public class CrossDomainFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        // 解决跨域问题，允许所有域名访问
        ((HttpServletResponse) response).setHeader("Access-Control-Allow-Origin", "*");
        chain.doFilter(request, response);
    }
}
