package com.soladuor.exception;

import com.soladuor.utils.result.JSONResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 统一异常拦截处理
 */
@RestControllerAdvice // 参考文档：https://juejin.cn/post/6844904168025489421
@Slf4j
public class GraceExceptionHandler {

    @ExceptionHandler(MyCustomException.class)
    public JSONResult returnMyCustomException(MyCustomException e) {
        log.error(e.getMessage());
        return JSONResult.errorMsg(e.getMessage());
    }

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public JSONResult handleRuntimeException(RuntimeException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生未知异常.", requestURI, e);
        return JSONResult.errorMsg(e.getMessage());
    }

}
