package com.soladuor.service;


import com.soladuor.pojo.Whitelist;

import java.util.List;

public interface WhitelistService {

    List<Whitelist> getWhiteList();

    void addWhitelist(String ip, String description);

    void deleteWhitelistById(String id);

    void updateDescById(String id, String description);
}
