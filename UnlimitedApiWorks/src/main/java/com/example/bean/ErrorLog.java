package com.example.bean;

public class ErrorLog {
    private Integer id; // 唯一标识符（非空，自增）
    private String error_type; // 报错类型（非空）
    private String error_message; // 错误信息（非空）
    private String error_time; // 错误发生时间（非空，默认当前时间）
    private String error_stack; // 错误堆栈信息（可为空）

    public ErrorLog() {
    }

    public ErrorLog(String error_type, String error_message) {
        this.error_type = error_type;
        this.error_message = error_message;
    }

    public ErrorLog(String error_type, String error_message, String error_stack) {
        this.error_type = error_type;
        this.error_message = error_message;
        this.error_stack = error_stack;
    }

    public ErrorLog(Integer id, String error_type, String error_message, String error_time, String error_stack) {
        this.id = id;
        this.error_type = error_type;
        this.error_message = error_message;
        this.error_time = error_time;
        this.error_stack = error_stack;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getError_type() {
        return error_type;
    }

    public void setError_type(String error_type) {
        this.error_type = error_type;
    }

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }

    public String getError_time() {
        return error_time;
    }

    public void setError_time(String error_time) {
        this.error_time = error_time;
    }

    public String getError_stack() {
        return error_stack;
    }

    public void setError_stack(String error_stack) {
        this.error_stack = error_stack;
    }

    @Override
    public String toString() {
        return "ErrorLogs{" +
                "id=" + id +
                ", error_type='" + error_type + '\'' +
                ", error_message='" + error_message + '\'' +
                ", error_time='" + error_time + '\'' +
                ", error_stack='" + error_stack + '\'' +
                '}';
    }
}
