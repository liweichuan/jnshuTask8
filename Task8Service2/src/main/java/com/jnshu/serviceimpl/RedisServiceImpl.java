package com.jnshu.serviceimpl;

import com.jnshu.service.RedisService;

import com.jnshu.util.RedisUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("RedisServiceImpl")
public class RedisServiceImpl implements RedisService {
    @Autowired
    RedisUtil redisUtil;
    private final static Logger logger= LogManager.getLogger(RedisServiceImpl.class);

    @Override
    public Boolean add(String key, Object value, long time) {
        return redisUtil.set(key, value, time);
    }

    @Override
    public Boolean add(String key, Object value) {
        logger.error(key);
        logger.error(value);
        //redisUtil是空对象？可是本地在server单元测试中没有问题
        if (redisUtil==null){
            logger.error("redis工具类为空");
        }
        Boolean result=redisUtil.set(key, value);
        return result;
    }

    @Override
    public Boolean hasKey(String key) {
        return redisUtil.hasKey(key);
    }

    @Override
    public Boolean delKey(String... key) {
        return redisUtil.del(key);
    }

    @Override
    public Object getKey(String key) {
        return redisUtil.get(key);
    }

    @Override
    public Boolean setJsonString(List<Object> userList, String key) {
        return redisUtil.setJsonString(userList,key);
    }

    @Override
    public List getJsonString(String key, Class clazz) {
        return redisUtil.getJsonString(key,clazz);
    }
}
