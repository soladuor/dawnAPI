package com.example.servlet.api.tencent.utils;

import com.example.singleton.IdentifierSingleton;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.nlp.v20190408.NlpClient;

public class TencentCloudNpl {
    public static NlpClient NlpClientConfig(String endpoint, String region) {
        String SecretId = IdentifierSingleton.getInstance().getTencentSecretId();
        String SecretKey = IdentifierSingleton.getInstance().getTencentSecretKey();
        Credential cred = new Credential(SecretId, SecretKey);
        // 实例化一个http选项，可选的，没有特殊需求可以跳过
        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setEndpoint(endpoint);
        // 实例化一个client选项，可选的，没有特殊需求可以跳过
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setHttpProfile(httpProfile);
        // 实例化要请求产品的client对象,clientProfile是可选的
        return new NlpClient(cred, region, clientProfile);
    }
}
