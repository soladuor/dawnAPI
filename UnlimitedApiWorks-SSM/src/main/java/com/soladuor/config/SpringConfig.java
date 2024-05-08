package com.soladuor.config;

import org.springframework.context.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(
        value = {"com.soladuor"},
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class})
                // , @ComponentScan.Filter(type = FilterType.REGEX, pattern = {"com.soladuor.config.*"})
        }
)
@Import({JdbcConfig.class, MybatisConfig.class})
@EnableTransactionManagement // 开启事务注解
@EnableAspectJAutoProxy // 开启AspectJ注解支持
public class SpringConfig {
}
