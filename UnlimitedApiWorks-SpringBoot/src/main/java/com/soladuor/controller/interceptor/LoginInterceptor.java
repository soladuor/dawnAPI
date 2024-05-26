package com.soladuor.controller.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        Boolean b = (Boolean) session.getAttribute("login");
        if (b != null) {
            if (b) {
                return true;
            }
        }
        response.sendRedirect(request.getContextPath() + "/page/617BBA0E07A4F2E0CE96767C47B97609"); // ToIndexServlet
        // response.getWriter().write("登录成功"); // 其实是登录失败（笑
        return false;
    }
}
