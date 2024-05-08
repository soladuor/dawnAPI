package com.soladuor.mapper;


import com.soladuor.pojo.ErrorLog;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ErrorLogMapper {
    void addErrorLog(ErrorLog errorLog);

    List<ErrorLog> getErrorList();

    void deleteAllErrorLog();

    void deleteErrorLogById(Integer id);
}
