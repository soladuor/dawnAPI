package com.example.servlet.api.zydsoft;


import com.example.servlet.api.zydsoft.utils.DialecticalCloud;
import com.example.utils.BaseHttpUtil;
import com.google.gson.Gson;
import org.apache.http.client.methods.HttpGet;
import org.junit.Test;

import java.util.SortedMap;
import java.util.TreeMap;

public class TestHttp {
    Gson gson = new Gson();

    // @Test
    // public void t1() {
    //     Map<String, Object> postParams = new HashMap<>();
    //     postParams.put("medicalhistory", new String[]{"高血压", "心脏病"});
    //     postParams.put("name", "李四");
    //     postParams.put("gender", "女");
    //     postParams.put("birthday", "1976-11-13T16:00:00.000Z");
    //     postParams.put("relation", "夫妻");
    //     postParams.put("weight", 60);
    //     postParams.put("height", 165);
    //     postParams.put("address", "北京市");
    //     postParams.put("other", "无");
    //
    //     String patientsId = "642ebfd61abfab0011f38169";
    //     String post_params = gson.toJson(postParams);
    //     String timestamp = System.currentTimeMillis() / 1000 + "";
    //     String token = DialecticalCloud.getToken();
    //     String stringA = "patientsId=" + patientsId +
    //             "&post_params=" + post_params +
    //             "&timestamp=" + timestamp;
    //     String stringSignTemp = stringA + "&" + token;
    //     String signValue = DigestUtils.sha256Hex(stringSignTemp).toUpperCase();
    //     String url = "patientsId=" + patientsId + "&sign=" + signValue + "&timestamp=" + timestamp;
    //     String s = DialecticalCloud.doGet("/disease_type?" + url);
    //     // System.out.println(s);
    // }


    @Test
    public void testSign() {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJwYXJ0bmVySWQiOiI2NDJlYmZkNjFhYmZhYjAwMTFmMzgxNjkiLCJpbnZhbGlkIjpmYWxzZSwiaWF0IjoxNjgxMTc2Mjg5LCJleHAiOjE2ODExODM0ODl9.au7ib2Weo1j3X3cYh3csS2CwdZNs_AjzTg4MPDhZqi8";
        SortedMap<Object, Object> map = new TreeMap<>();
        map.put("page", 1);
        map.put("pageSize", 20);
        System.out.println(DialecticalCloud.createGetSign(map, false));
    }

    @Test
    public void t2() {
        String timestamp = System.currentTimeMillis() / 1000 + "";
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJwYXJ0bmVySWQiOiI2NDJlYmZkNjFhYmZhYjAwMTFmMzgxNjkiLCJpbnZhbGlkIjpmYWxzZSwiaWF0IjoxNjgxNjQwMzg1LCJleHAiOjE2ODE2NDc1ODV9.041kYhn55vfzJxZlcihDWfofaVleRkTZOlxuEx5MBP4";
        SortedMap<Object, Object> map = new TreeMap<>();
        map.put("timestamp", timestamp);
        map.put("page", 1);
        map.put("pageSize", 20);
        String url = "/disease_type";
        System.out.println("url= " + url);
        String baseUrl = "https://t.zydsoft.cn/open/api";
        HttpGet httpGet = new HttpGet(baseUrl + url + "?" + DialecticalCloud.createGetSign(map, false));
        System.out.println("httpGet= " + httpGet);
        httpGet.addHeader("Authorization", "Bearer " + token);
        String s = BaseHttpUtil.doGet(httpGet, true);
        System.out.println("s= " + s);
    }

    @Test
    public void test3() {
        String timestamp = System.currentTimeMillis() / 1000 + "";
        SortedMap<Object, Object> map = new TreeMap<>();
        map.put("timestamp", timestamp);
        map.put("page", 1);
        map.put("pageSize", 20);
        String url = "/disease_type?" + DialecticalCloud.createGetSign(map, false);
    }
}
