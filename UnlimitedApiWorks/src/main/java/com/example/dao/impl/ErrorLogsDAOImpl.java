package com.example.dao.impl;

import com.example.bean.ErrorLog;
import com.example.dao.BaseDAOImpl;
import com.example.dao.ErrorLogsDAO;

import java.sql.SQLException;
import java.util.List;

public class ErrorLogsDAOImpl extends BaseDAOImpl implements ErrorLogsDAO {
    @Override
    public void addErrorLog(ErrorLog errorLog) {
        String sql = "INSERT INTO error_logs (error_type, error_message,error_stack) VALUES (?, ?,?)";
        try {
            update(sql, errorLog.getError_type(), errorLog.getError_message(), errorLog.getError_stack());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ErrorLog> getErrorList() {
        String sql = "SELECT * FROM error_logs";
        try {
            return getList(ErrorLog.class, sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAllErrorLog() {
        String sql = "DELETE FROM error_logs";
        try {
            update(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteErrorLogById(Integer id) {
        String sql = "DELETE FROM error_logs WHERE id = ?";
        try {
            update(sql, id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
