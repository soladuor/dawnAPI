package com.example.filter.all;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * 全局编码拦截器
 *
 * @version 1.0
 */
@WebFilter(filterName = "EncodingFilter", urlPatterns = "/*")
public class EncodingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8"); // 处理请求乱码
        response.setContentType("text/html;charset=utf-8"); // 处理响应乱码
        chain.doFilter(request, response); // 放行
    }
}
