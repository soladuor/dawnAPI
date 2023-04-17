package com.example.singleton;

import com.example.bean.Whitelist;
import com.example.dao.impl.WhitelistDAOImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WhitelistSingleton {
    private volatile Map<String, String> whiteMap; // 白名单Map
    private volatile List<Whitelist> whiteList; // 白名单列表
    private static volatile WhitelistSingleton instance;

    private WhitelistSingleton() {
    }

    public static WhitelistSingleton getInstance() {
        if (instance == null) {
            synchronized (WhitelistSingleton.class) {
                if (instance == null) {
                    instance = new WhitelistSingleton();
                }
            }
        }
        return instance;
    }

    public synchronized void updateWhitelist() {
        WhitelistDAOImpl whitelistDAO = new WhitelistDAOImpl();
        this.whiteList = whitelistDAO.getAllWhitelist(); // 更新List
        Map<String, String> WhiteMap = new HashMap<>();
        for (Whitelist white : whiteList) {
            WhiteMap.put(white.getIp_address(), white.getDescription());
        }
        this.whiteMap = WhiteMap; // 更新Map
    }

    public synchronized Map<String, String> getWhiteMap() {
        updateWhitelist();
        return this.whiteMap;
    }

    public synchronized List<Whitelist> getWhiteList() {
        updateWhitelist();
        return this.whiteList;
    }
}
