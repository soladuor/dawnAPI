package com.example.bean;

import java.sql.Timestamp;

public class Whitelist {
    private Integer id; // 唯一标识符（自增）
    private String ip_address; // ipv4地址（非空）
    private String description; // 描述（非空）
    private String city; // 归属地（非空）
    private Timestamp created_at; // 建立时间戳（有默认值）

    public Whitelist() {
    }

    public Whitelist(String ip_address, String description, String city) {
        this.ip_address = ip_address;
        this.description = description;
        this.city = city;
    }

    public Whitelist(Integer id, String ip_address, String description, String city, Timestamp created_at) {
        this.id = id;
        this.ip_address = ip_address;
        this.description = description;
        this.city = city;
        this.created_at = created_at;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIp_address() {
        return ip_address;
    }

    public void setIp_address(String ip_address) {
        this.ip_address = ip_address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    @Override
    public String toString() {
        return "Whitelist{" +
                "id=" + id +
                ", ip_address='" + ip_address + '\'' +
                ", description='" + description + '\'' +
                ", city='" + city + '\'' +
                ", created_at=" + created_at +
                '}';
    }
}
