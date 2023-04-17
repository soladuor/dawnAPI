package com.example.filter;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "LoginFilter", urlPatterns = {"/WEB-INF/admin/*", "/page/white/*"})
public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        Boolean b = (Boolean) session.getAttribute("login");
        if (b != null) {
            if (b) {
                chain.doFilter(request, response);
                return;
            }
        }
        resp.sendRedirect(req.getContextPath() + "/page/617BBA0E07A4F2E0CE96767C47B97609"); // ToIndexServlet
        // response.getWriter().write("登录成功"); // 其实是登录失败（笑
    }
}
