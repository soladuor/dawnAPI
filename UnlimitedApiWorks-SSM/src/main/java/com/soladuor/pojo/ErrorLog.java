package com.soladuor.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data // get、set、toString
public class ErrorLog {
    private Integer id; // 唯一标识符（非空，自增）
    private String errorType; // 报错类型（非空）
    private String errorMessage; // 错误信息（非空）
    private String errorTime; // 错误发生时间（非空，默认当前时间）
    private String errorStack; // 错误堆栈信息（可为空）

    public ErrorLog(String errorType, String errorMessage) {
        this.errorType = errorType;
        this.errorMessage = errorMessage;
    }

    public ErrorLog(String errorType, String errorMessage, String errorStack) {
        this.errorType = errorType;
        this.errorMessage = errorMessage;
        this.errorStack = errorStack;
    }
}
