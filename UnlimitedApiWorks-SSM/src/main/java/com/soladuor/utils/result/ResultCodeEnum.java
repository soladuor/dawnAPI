package com.soladuor.utils.result;


/**
 * 统一返回结果状态信息类
 */
public enum ResultCodeEnum {
    // 成功
    SUCCESS(200, "成功"),
    // 错误
    ILLEGAL_REQUEST(400, "错误请求"),
    UNAUTHORIZED(401, "未经授权"),
    FORBIDDEN(403, "禁止访问"),
    NOT_FOUND(404, "找不到资源"),
    PARAM_ERROR(405, "参数错误"),
    DATA_ERROR(406, "数据异常"),
    REPEAT_SUBMIT(407, "重复提交"),
    // 失败
    FAIL(500, "失败"),
    LOGIN_AUTH(501, "未登陆"),
    PERMISSION(502, "没有权限"),
    ;

    private final Integer code;

    private final String message;

    ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
