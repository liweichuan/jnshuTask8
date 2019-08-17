package com.jnshu.service;

import java.util.List;

public interface RedisService {
    //添加缓存，同时指定缓存的时间
    Boolean add(String key, Object value, long time);
    //添加缓存
    Boolean add(String key, Object value);
    //查看缓存是否存在
    Boolean hasKey(String key);
    //删除缓存
    Boolean delKey(String... key);
    //普通缓存获取
    Object getKey(String key);
    //将list<object>转换成json字符串存储
    Boolean setJsonString(List<Object> userList, String key);
    //获取list缓存
    List getJsonString(String key, Class clazz);
}
