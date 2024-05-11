package com.soladuor.service.impl;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.soladuor.exception.GraceException;
import com.soladuor.mapper.WhitelistMapper;
import com.soladuor.pojo.Whitelist;
import com.soladuor.service.WhitelistService;
import com.soladuor.utils.IPUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WhitelistServiceImpl implements WhitelistService {

    @Autowired
    WhitelistMapper whitelistMapper;

    @Override
    public Map<String, String> getWhiteMap() {
        List<Whitelist> allWhitelist = whitelistMapper.getAllWhitelist();// 更新List
        Map<String, String> WhiteMap = new HashMap<>();
        for (Whitelist white : allWhitelist) {
            WhiteMap.put(white.getIpAddress(), white.getDescription());
        }
        return WhiteMap;
    }

    @Override
    public List<Whitelist> getWhiteList() {
        return whitelistMapper.getAllWhitelist();
    }

    @Override
    public void addWhitelist(String ip, String description) {
        try {
            String city = IPUtils.getCityByIP(ip);
            Whitelist whitelist = new Whitelist(ip, description, city);
            whitelistMapper.addWhitelist(whitelist);
        } catch (JsonProcessingException e) {
            // throw new RuntimeException(e);
            GraceException.display("IP对应城市获取异常");
        }
    }

    @Override
    public void deleteWhitelistById(String id) {
        whitelistMapper.deleteWhitelistById(id);
    }
}
