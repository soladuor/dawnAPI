package com.example.utils;

import com.example.bean.ErrorLog;
import com.example.dao.impl.ErrorLogsDAOImpl;

public class ErrorLogger {

    private static ErrorLogsDAOImpl errorLogsDAO = new ErrorLogsDAOImpl();

    public static void logException(String error_type, Exception e) {
        ErrorLog errorLog = new ErrorLog(error_type, e.getMessage(), getStackTrace(e));
        errorLogsDAO.addErrorLog(errorLog);
    }

    public static void error(String error_type, String error_message) {
        ErrorLog errorLog = new ErrorLog(error_type, error_message);
        errorLogsDAO.addErrorLog(errorLog);
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

