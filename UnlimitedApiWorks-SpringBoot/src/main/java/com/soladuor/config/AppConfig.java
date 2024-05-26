package com.soladuor.config;

import com.google.code.kaptcha.servlet.KaptchaServlet;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

@Configuration
public class AppConfig implements ServletContextInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        servletContext.setAttribute("apiTimes", 10000); // 初始化,默认1万次
    }

    /**
     * 注册Servlet组件
     */
    @Bean
    public ServletRegistrationBean<?> exampleServlet1() {
        // 设置验证码servlet
        ServletRegistrationBean<KaptchaServlet> kaptchaServlet = new ServletRegistrationBean<>(new KaptchaServlet());
        kaptchaServlet.setName("KaptchaServlet");
        // KaptchaServlet通过MD5加密9901次的结果
        kaptchaServlet.addUrlMappings("/page/code/A4F1C01849C3FE3179AD265145B34BF1");
        // 验证码字符长度
        kaptchaServlet.addInitParameter("kaptcha.textproducer.char.length", "7");
        // 修改验证码默认session的名字
        kaptchaServlet.addInitParameter("kaptcha.session.key", "kaptchaCode");
        return kaptchaServlet;
    }

    // @Bean
    // public FilterRegistrationBean<Filter> exampleFilter() {
    //     // 处理中文乱码
    //     CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
    //     characterEncodingFilter.setEncoding("UTF-8");
    //     characterEncodingFilter.setForceEncoding(true);
    //
    //     FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
    //     registrationBean.setFilter(characterEncodingFilter);
    //     registrationBean.addUrlPatterns("/*"); // 设置Filter应用的URL模式
    //     return registrationBean;
    // }


}
