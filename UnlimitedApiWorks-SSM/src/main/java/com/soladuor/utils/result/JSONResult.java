package com.soladuor.utils.result;

/**
 * 全局统一返回结果类
 */
public class JSONResult {
    // 返回码
    private Integer code;
    // 返回消息
    private String message;
    // 返回数据
    private Object data;

    /**
     * 构建返回数据
     */
    public static JSONResult build(Integer code, String message, Object data) {
        return new JSONResult().code(code).message(message).data(data);
    }

    public static JSONResult build(ResultCodeEnum codeEnum, Object data) {
        return build(codeEnum.getCode(), codeEnum.getMessage(), data);
    }

    public static JSONResult build(Integer code, String message) {
        return build(code, message, null);
    }

    /**
     * 操作成功
     */
    public static JSONResult ok(Object data) {
        return build(ResultCodeEnum.SUCCESS, data);
    }

    public static JSONResult ok() {
        return ok(null);
    }

    /**
     * 操作失败
     */
    public static JSONResult fail(Object data) {
        return build(ResultCodeEnum.FAIL, data);
    }

    public static JSONResult fail() {
        return fail(null);
    }

    public static JSONResult errorMsg(String message) {
        return build(ResultCodeEnum.FAIL.getCode(), message);
    }

    /**
     * 链式调用
     */
    public JSONResult code(int code) {
        this.setCode(code);
        return this;
    }

    public JSONResult message(String message) {
        this.setMessage(message);
        return this;
    }

    public JSONResult data(Object data) {
        this.setData(data);
        return this;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
