package com.soladuor.controller;

import com.soladuor.utils.BaseUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class LoginController {

    // AdminLoginServlet通过MD5加密9901次的结果
    @RequestMapping(
            value = "/page/55AF7DD54A9DA8280A2EBD20659B9734"
            // 防止返回的中文乱码
            , produces = {"text/plain;charset=UTF-8"}
    )
    @ResponseBody
    public String login(String name, String password, String code, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (BaseUtil.stringsIsEmpty(name, password) || !BaseUtil.isEmpty(code)) {
            // 输入验证码则登录失败
            Object kaptchaCode = request.getSession().getAttribute("kaptchaCode");
            if (kaptchaCode != null) {
                if (kaptchaCode.equals(code)) {
                    return "登录成功,眼神不错啊"; // 其实是登录失败（笑
                }
                return "登录失败————这都看不清，眼神不行啊"; // 登录失败（笑
            }
            // 用非法手段不加载验证码直接登录的
            return "登录失败"; // 登录失败
        }
        Long timePass = Long.valueOf(password);
        Long sysTime = System.currentTimeMillis() / 1000;
        long timeCheck = Math.abs(sysTime - timePass);
        if ("admin".equals(name) && timeCheck < 60) {
            // 真 · 登录成功
            HttpSession session = request.getSession();
            session.setAttribute("login", true);
            session.setMaxInactiveInterval(3600); // session存活1小时
            // return "admin/index";
            response.sendRedirect(request.getContextPath() + "/page/white/2214086D382FE0823550CAD6B19EA205/getAllList");
            return "成功";
        } else {
            return "登录成功"; // 其实是登录失败（笑
        }
    }

}
