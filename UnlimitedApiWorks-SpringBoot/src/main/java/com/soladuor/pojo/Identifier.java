package com.soladuor.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data // get、set、toString
public class Identifier {
    private String key; // 标识名
    private String value; // 值
    private String description; // 描述

}
