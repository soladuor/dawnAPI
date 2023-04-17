package com.example.servlet.page;

import com.example.servlet.BaseServlet;
import com.example.utils.BaseUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

// AdminLoginServlet通过MD5加密9901次的结果
@WebServlet(name = "LoginServlet", value = "/page/55AF7DD54A9DA8280A2EBD20659B9734")
public class LoginServlet extends BaseServlet {
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String code = request.getParameter("code");
        if (BaseUtil.stringsIsEmpty(name, password) || !BaseUtil.isEmpty(code)) {
            // 输入验证码则登录失败
            Object kaptchaCode = request.getSession().getAttribute("kaptchaCode");
            if (kaptchaCode != null) {
                if (kaptchaCode.equals(code)) {
                    response.getWriter().write("登录成功,眼神不错啊"); // 其实是登录失败（笑
                } else {
                    response.getWriter().write("登录失败————这都看不清，眼神不行啊"); // 登录失败（笑
                }
                return;
            }
            // 用非法手段不加载验证码直接登录的
            response.getWriter().write("登录失败"); // 登录失败
            return;
        }
        Long timePass = Long.valueOf(password);
        Long sysTime = System.currentTimeMillis() / 1000;
        long timeCheck = Math.abs(sysTime - timePass);
        if ("admin".equals(name) && timeCheck < 60) {
            // 真 · 登录成功
            HttpSession session = request.getSession();
            session.setAttribute("login", true);
            session.setMaxInactiveInterval(3600); // session存活1小时
            // request.getRequestDispatcher("/WEB-INF/admin/index.jsp").forward(request, response);
            response.sendRedirect(request.getContextPath() + "/page/white/2214086D382FE0823550CAD6B19EA205?flag=getAllList");
        } else {
            response.getWriter().write("登录成功"); // 其实是登录失败（笑
        }
    }
}
