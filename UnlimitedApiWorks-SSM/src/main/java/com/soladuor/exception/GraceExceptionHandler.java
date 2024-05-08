package com.soladuor.exception;

import com.soladuor.utils.result.JSONResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常拦截处理
 */
@ControllerAdvice // 参考文档：https://juejin.cn/post/6844904168025489421
@Slf4j
public class GraceExceptionHandler {

    @ExceptionHandler(MyCustomException.class)
    @ResponseBody
    public JSONResult returnMyCustomException(MyCustomException e) {
        log.error(e.getMessage());
        return JSONResult.errorMsg(e.getMessage());
    }

}
