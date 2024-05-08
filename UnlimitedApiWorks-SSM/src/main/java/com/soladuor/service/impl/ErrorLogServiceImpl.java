package com.soladuor.service.impl;

import com.soladuor.mapper.ErrorLogMapper;
import com.soladuor.pojo.ErrorLog;
import com.soladuor.service.ErrorLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ErrorLogServiceImpl implements ErrorLogService {
    @Autowired
    ErrorLogMapper errorLogMapper;

    @Override
    public void addErrorLog(ErrorLog errorLog) {
        errorLogMapper.addErrorLog(errorLog);
    }

    @Override
    public List<ErrorLog> getErrorList() {
        return errorLogMapper.getErrorList();
    }

    @Override
    public void deleteAllErrorLog() {
        errorLogMapper.deleteAllErrorLog();
    }

    @Override
    public void deleteErrorLogById(Integer id) {
        errorLogMapper.deleteErrorLogById(id);
    }
}
