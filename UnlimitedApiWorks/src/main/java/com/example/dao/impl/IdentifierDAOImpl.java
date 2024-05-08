package com.example.dao.impl;

import com.example.bean.Identifier;
import com.example.dao.BaseDAOImpl;
import com.example.dao.IdentifierDAO;

import java.sql.SQLException;
import java.util.List;

public class IdentifierDAOImpl extends BaseDAOImpl implements IdentifierDAO {
    @Override
    public List<Identifier> getIdentifierList() {
        String sql = "SELECT * FROM identifier";
        try {
            return getList(Identifier.class, sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
