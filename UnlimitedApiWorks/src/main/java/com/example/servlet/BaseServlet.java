package com.example.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

public abstract class BaseServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
        处理请求中文乱码：
        get请求：tomcat8已经处理好了
        post请求：在所有的获取请求参数之前，设置字符集 */
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        // 先获得flag的参数，进行判断
        String flag = request.getParameter("flag");
        // 通过反射获得想要调用的方法的对象
        Class<? extends BaseServlet> clazz = this.getClass();// clazz就是继承此类的类信息（因为是当前类）
        Method method;
        try {
            method = clazz.getDeclaredMethod(flag, HttpServletRequest.class, HttpServletResponse.class);
            method.setAccessible(true); // 暴力访问（为了访问私有的方法）
            // 调用这个函数
            method.invoke(this, request, response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
