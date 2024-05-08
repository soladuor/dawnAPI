package com.soladuor.service;


import com.soladuor.pojo.ErrorLog;

import java.util.List;

public interface ErrorLogService {
    void addErrorLog(ErrorLog errorLog);

    List<ErrorLog> getErrorList();

    void deleteAllErrorLog();

    void deleteErrorLogById(Integer id);
}
