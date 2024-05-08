package com.example.singleton;

import com.example.bean.Identifier;
import com.example.dao.impl.IdentifierDAOImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IdentifierSingleton {
    private static volatile IdentifierSingleton instance;
    private volatile Map<String, String> identifierMap; // 所有标识map
    private volatile List<Identifier> identifierList; // 所有标识list

    private IdentifierSingleton() {
    }

    public static IdentifierSingleton getInstance() {
        if (instance == null) {
            synchronized (IdentifierSingleton.class) {
                if (instance == null) {
                    instance = new IdentifierSingleton();
                }
            }
        }
        return instance;
    }

    public synchronized void updateIdentifier() {
        IdentifierDAOImpl identifierDAO = new IdentifierDAOImpl();
        identifierList = identifierDAO.getIdentifierList();
        Map<String, String> tempMap = new HashMap<>();
        for (Identifier identifier : identifierList) {
            tempMap.put(identifier.getKey(), identifier.getValue());
        }
        identifierMap = tempMap;
    }

    public List<Identifier> getIdentifierList() {
        updateIdentifier();
        return identifierList;
    }

    // 合作方Id
    public synchronized String getPartnerId() {
        return identifierMap.get("partnerId");
    }

    // 密钥
    public synchronized String getSecret() {
        return identifierMap.get("secret");
    }

    // 辩证云接口
    public synchronized String getBaseUrl() {
        return identifierMap.get("baseUrl");
    }

    // 腾讯云secret_key
    public synchronized String getTencentSecretKey() {
        return identifierMap.get("secret_key");
    }

    // 腾讯云secret_id
    public synchronized String getTencentSecretId() {
        return identifierMap.get("secret_id");
    }

}
