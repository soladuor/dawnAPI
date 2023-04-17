package com.example.listener;

import com.example.servlet.api.zydsoft.utils.DialecticalCloud;
import com.example.singleton.IdentifierSingleton;
import com.example.singleton.WhitelistSingleton;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class InitListener implements ServletContextListener {

    public InitListener() {
    }

    private final DialecticalCloud dialecticalCloud = new DialecticalCloud();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        IdentifierSingleton.getInstance().updateIdentifier();
        WhitelistSingleton.getInstance().updateWhitelist();
        System.out.println("初始化完成, 开始获取token");
        dialecticalCloud.start(); // 开始获取token
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("程序销毁，结束获取token");
        dialecticalCloud.stop(); // 结束获取token
    }
}
