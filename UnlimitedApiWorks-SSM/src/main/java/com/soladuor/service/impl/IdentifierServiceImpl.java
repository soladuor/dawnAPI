package com.soladuor.service.impl;


import com.soladuor.mapper.IdentifierMapper;
import com.soladuor.pojo.Identifier;
import com.soladuor.service.IdentifierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IdentifierServiceImpl implements IdentifierService {

    @Autowired
    IdentifierMapper identifierMapper;

    @Override
    public List<Identifier> getIdentifierList() {
        return identifierMapper.getIdentifierList();
    }

    // 合作方Id
    @Override
    public String getPartnerId() {
        return identifierMapper.getIdentifierByKey("partnerId").getValue();
    }

    // 密钥
    @Override
    public String getSecret() {
        return identifierMapper.getIdentifierByKey("secret").getValue();
    }

    // 辩证云接口根路径
    @Override
    public String getRootSrc() {
        return identifierMapper.getIdentifierByKey("rootSrc").getValue();

    }

    // 工作室面板 Token
    @Override
    public String getPartnerToken() {
        return identifierMapper.getIdentifierByKey("partnerToken").getValue();
    }

}
