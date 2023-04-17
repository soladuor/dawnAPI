package com.example.utils;

/**
 * @version 1.0
 * 功能：设置异步请求响应结果的格式（为了团队开发的规范化）
 * 最终是将CommonResult的对象变为json字符串返给js
 */
public class CommonResult {
    private boolean flag; // 确定本次响应的结果是否正常
    private Object resultData; // 响应结果的数据
    private String message; // 信息

    public CommonResult() {
    }

    /**
     * 请求处理成功
     *
     * @return
     */
    public static CommonResult ok() {
        return new CommonResult().setFlag(true);
    }

    /**
     * 请求处理成功   `
     *
     * @return
     */
    public static CommonResult error() {
        return new CommonResult().setFlag(false);
    }

    /*
     set方法返回自己的好处之一是可以拼接式set
        例如可以直接返回CommonResult.ok().setMessage("成功").setResultData(xxx);
     */

    public CommonResult setFlag(boolean flag) {
        this.flag = flag;
        return this;
    }

    public CommonResult setResultData(Object resultData) {
        this.resultData = resultData;
        return this;
    }

    public CommonResult setMessage(String message) {
        this.message = message;
        return this;
    }


    public boolean isFlag() {
        return flag;
    }

    public Object getResultData() {
        return resultData;
    }

    public String getMessage() {
        return message;
    }


    @Override
    public String toString() {
        return "CommonResult{" +
                "flag=" + flag +
                ", resultData=" + resultData +
                ", message='" + message + '\'' +
                '}';
    }
}
