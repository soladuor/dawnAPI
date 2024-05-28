package com.soladuor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "com.soladuor.mapper") // 注：这里引入的是第三方的MapperScan
@ServletComponentScan // Servlet、Filter、Listener 可以直接通过 @WebServlet、@WebFilter、@WebListener 注解自动注册，无需其他代码。
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
