package com.soladuor.exception;

/**
 * 优雅异常处理，便于调用
 * （其实就是 throw new RuntimeException 的封装）
 */
public class GraceException {

    public static void display(String errMsg) {
        throw new MyCustomException(errMsg);
    }
}
