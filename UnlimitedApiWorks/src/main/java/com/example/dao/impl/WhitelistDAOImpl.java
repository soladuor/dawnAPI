package com.example.dao.impl;

import com.example.bean.Whitelist;
import com.example.dao.BaseDAOImpl;
import com.example.dao.WhitelistDAO;

import java.sql.SQLException;
import java.util.List;

public class WhitelistDAOImpl extends BaseDAOImpl implements WhitelistDAO {
    @Override
    public List<Whitelist> getAllWhitelist() {
        String sql = "SELECT * FROM whitelist";
        try {
            return getList(Whitelist.class, sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addWhitelist(Whitelist whitelist) {
        String sql = "INSERT INTO whitelist(ip_address, description, city)" +
                " VALUES(?,?,?)";
        try {
            update(sql, whitelist.getIp_address(), whitelist.getDescription(), whitelist.getCity());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteWhitelistById(String id) {
        String sql = "DELETE FROM whitelist WHERE id = ?";
        try {
            update(sql, id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
