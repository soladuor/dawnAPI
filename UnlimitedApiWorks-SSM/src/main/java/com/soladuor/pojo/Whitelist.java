package com.soladuor.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Data // get、set、toString
public class Whitelist {
    private Integer id; // 唯一标识符（自增）
    private String ipAddress; // ipv4地址（非空）
    private String description; // 描述（非空）
    private String city; // 归属地（非空）
    private Timestamp createdAt; // 建立时间戳（有默认值）

    public Whitelist(String ipAddress, String description, String city) {
        this.ipAddress = ipAddress;
        this.description = description;
        this.city = city;
    }
}
