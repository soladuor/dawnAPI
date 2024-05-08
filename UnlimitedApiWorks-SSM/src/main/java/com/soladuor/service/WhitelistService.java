package com.soladuor.service;


import com.soladuor.pojo.Whitelist;

import java.util.List;
import java.util.Map;

public interface WhitelistService {

    Map<String, String> getWhiteMap();

    List<Whitelist> getWhiteList();

    void addWhitelist(String ip, String description);

    void deleteWhitelistById(String id);
}
