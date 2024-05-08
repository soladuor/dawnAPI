package com.soladuor.service;


import com.soladuor.pojo.Identifier;

import java.util.List;

public interface IdentifierService {

    List<Identifier> getIdentifierList();

    // 合作方Id
    String getPartnerId();

    // 密钥
    String getSecret();

    // 辩证云接口根路径
    String getRootSrc();

    // 工作室面板 Token
    String getPartnerToken();

}
