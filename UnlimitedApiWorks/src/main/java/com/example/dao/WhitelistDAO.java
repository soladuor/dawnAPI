package com.example.dao;

import com.example.bean.Whitelist;

import java.util.List;

public interface WhitelistDAO {
    /**
     * 获取所有白名单ip
     */
    List<Whitelist> getAllWhitelist();

    // /**
    //  * 获取所有白名单ip Map
    //  */
    // Map<String, String> getAllWhiteMap();

    /**
     * 添加白名单ip
     *
     * @param whitelist 白名单ip
     */
    void addWhitelist(Whitelist whitelist);

    /**
     * 删除ip信息
     *
     * @param id ip的唯一表示
     */
    void deleteWhitelistById(String id);
}
