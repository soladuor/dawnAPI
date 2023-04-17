package com.example.dao;

import com.example.bean.ErrorLog;

import java.util.List;

public interface ErrorLogsDAO {
    void addErrorLog(ErrorLog errorLog);

    List<ErrorLog> getErrorList();

    void deleteAllErrorLog();

    void deleteErrorLogById(Integer id);
}
