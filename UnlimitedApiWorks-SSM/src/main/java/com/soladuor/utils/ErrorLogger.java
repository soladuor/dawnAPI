package com.soladuor.utils;


import com.soladuor.mapper.ErrorLogMapper;
import com.soladuor.pojo.ErrorLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ErrorLogger {

    static ErrorLogMapper errorLogMapper;

    @Autowired
    public void setErrorLogMapper(ErrorLogMapper errorLogMapper) {
        ErrorLogger.errorLogMapper = errorLogMapper;
    }

    public static void logException(String error_type, Exception e) {
        ErrorLog errorLog = new ErrorLog(error_type, e.getMessage(), getStackTrace(e));
        errorLogMapper.addErrorLog(errorLog);
    }

    public static void error(String error_type, String error_message) {
        ErrorLog errorLog = new ErrorLog(error_type, error_message);
        errorLogMapper.addErrorLog(errorLog);
    }

    private static String getStackTrace(Exception e) {
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement element : e.getStackTrace()) {
            sb.append(element.toString());
            sb.append("\n");
        }
        return sb.toString();
    }
}

