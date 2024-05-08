package com.soladuor.config;

import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;

public class MybatisConfig {

    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource) throws IOException {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        // 设置数据源
        factoryBean.setDataSource(dataSource);

        // 设置 Mybatis 核心配置文件路径（如果还想用XML文件管理一部分配置，可以用此方法）
        // factoryBean.setConfigLocation(new ClassPathResource("mybatis-config.xml"));

        // 如果想连 Mybatis 核心XML配置文件都省了，可以用官方的 Configuration 配置方式
        Configuration configuration = new Configuration();
        // 驼峰命名自动映射（默认false）
        configuration.setMapUnderscoreToCamelCase(true);
        factoryBean.setConfiguration(configuration);

        // 分页插件设置【多个插件可以用逗号分隔】
        // factoryBean.setPlugins(new PageInterceptor());

        // 设置类型别名
        factoryBean.setTypeAliasesPackage("com.soladuor.pojo");
        // 设置Mapper映射文件路径（如果使用了注解配置Mapper，这里可以不写）
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
        return factoryBean;
    }

    // 扫描Mapper接口（装配MapperScannerConfigurer管理Mapper代理对象）
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
        configurer.setBasePackage("com.soladuor.mapper");
        return configurer;
    }

}
