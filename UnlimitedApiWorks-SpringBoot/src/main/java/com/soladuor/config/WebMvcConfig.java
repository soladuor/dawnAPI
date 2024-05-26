package com.soladuor.config;

import com.soladuor.controller.interceptor.ApiInterceptor;
import com.soladuor.controller.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    // 装配视图控制器
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        // admin通过MD5加密9901次的结果（登录页面）
        registry.addViewController("/page/617BBA0E07A4F2E0CE96767C47B97609").setViewName("login");
    }

    // 拦截器需用注入的方式引入
    @Autowired
    private ApiInterceptor apiInterceptor;
    @Autowired
    private LoginInterceptor loginInterceptor;

    // 添加拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiInterceptor).addPathPatterns("/api/**");
        registry.addInterceptor(loginInterceptor).addPathPatterns("/WEB-INF/admin/**", "/page/white/**");
        // xxxInterceptor.addPathPatterns("/user/**", "/file/**")
        //         .excludePathPatterns("/user/login", "/user/signup", "/file/download/**");
    }

}

