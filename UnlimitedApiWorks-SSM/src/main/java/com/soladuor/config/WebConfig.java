package com.soladuor.config;

import com.google.code.kaptcha.servlet.KaptchaServlet;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class WebConfig extends AbstractAnnotationConfigDispatcherServletInitializer {

    // 指定spring的配置类
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{SpringConfig.class};
    }

    // 指定SpringMVC的配置类
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{SpringMvcConfig.class};
    }

    // 指定DispatcherServlet的映射规则，即 url-pattern
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    // 添加过滤器
    @Override
    protected Filter[] getServletFilters() {
        // 处理中文乱码
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        return new Filter[]{characterEncodingFilter};
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
        // 设置验证码servlet
        ServletRegistration.Dynamic kaptchaServlet = servletContext.addServlet("KaptchaServlet", KaptchaServlet.class);
        // KaptchaServlet通过MD5加密9901次的结果
        kaptchaServlet.addMapping("/page/code/A4F1C01849C3FE3179AD265145B34BF1");
        // 验证码字符长度
        kaptchaServlet.setInitParameter("kaptcha.textproducer.char.length", "7");
        // 修改验证码默认session的名字
        kaptchaServlet.setInitParameter("kaptcha.session.key", "kaptchaCode");

        servletContext.setAttribute("apiTimes", 10000); // 初始化,默认1万次
    }
}
