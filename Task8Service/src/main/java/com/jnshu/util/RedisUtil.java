package com.jnshu.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

//redis缓存工具类
@Component
public class RedisUtil {
    public final static Logger logger= LogManager.getLogger(RedisUtil.class);
    //注入redis模板属性
    private RedisTemplate redisTemplate;
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    /**
     * 指定缓存失效时间
     *
     * @param key
     *            键
     * @param time
     *            时间(秒)
     * 注意保证使用的key是存在的
     * redisTemplate.expire(key,timeout,timeunit);
     * 参数说明  key 需要设置的key  timeout：key的生存时间  timeuint：时间单位（小时，分钟，秒……）
     * 可以用 ttl key 命令查看该key的生存时间
     */
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            //如果生存时间超过0，表示插入成功
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据key 获取过期时间
     * 内部封装的ttl命令
     * @param key
     *  键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断key是否存在
     * 内部封装了exists命令 判断key是否存在
     * @param key
     *            键
     * @return true 存在 false不存在
     */
    public boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 功能描述：删除缓存
     *
     * @param key 可为多个
     * @return
     * @author Jesson
     * @date 2018/8/27 10:13
     */
    //这个注解用来抑制警告，就是忽略关于unchecked的警告：执行了未检查的转换时的警告，例如当使用集合时没有用泛型 (Generics) 来指定集合保存的类型。
    @SuppressWarnings("unchecked")
    public boolean del(String... key) {
        if (key != null && key.length > 0) {
            try {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                //将数组转换成列表删除
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
            return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    /**
     * 普通缓存获取
     *
     * @param key
     *            键
     * @return 值
     */
    public Object get(String key) {
        //缓存单例对象，调用get方法
        //三元运算符，如果key为null就复制为null，如果不是，就取值为后面的get的值
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 普通缓存放入
     *
     * @param key
     *            键
     * @param value
     *            值
     * @return true成功 false失败
     */
    public boolean set(String key, Object value) {
        logger.error("传入的数据为："+key+"......"+value);
        try {
            logger.error("准备set。。。。。。。");
            redisTemplate.opsForValue().set(key, value);
            logger.error("set成功。。。。。。。");
            return true;
        } catch (Exception e) {
            logger.error("set失败。。。。。。。。。");
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key
     *            键
     * @param value
     *            值
     * @param time
     *            时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                //封装set方法，存活时间大于零设置成功
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                //不大于0就不设置
                set(key, value);
            }
            //不管设没设存活时间，设置成功就返回true
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将list<object>转换成json字符串存储
     * @param userList
     * @param key
     * @return
     */
    public Boolean setJsonString(List<Object> userList, String key){

        try {
            logger.debug("将list转化成json字符串存储");
            String jsonList=JSON.toJSONString(userList);
            logger.debug("存储Json字符串");
            redisTemplate.opsForValue().set(key,jsonList);

            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 获取Json格式对象
     * @param key
     * @param clazz
     * @return
     */
    @SuppressWarnings("unchecked")
    public List getJsonString(String key, Class <Object> clazz){
        logger.debug("-----------进入 getJsonString 方法----------");
        try{
            logger.debug("获取String类");
            String getJsonList= (String) redisTemplate.opsForValue().get(key);

            logger.debug("getJsonList :"+getJsonList);
            logger.debug("类型转换");
            List<Object> list= JSONObject.parseArray(getJsonList,clazz);
            return list;
        }catch (Exception e){
            e.getMessage();
            return null;
        }
    }

    /**
     * 设置list缓存
     * @param key
     * @param value
     * @return
     */
    public boolean setList(String key, Object value){
        try {
            //rightPushAll封装了rpush方法，将缓存放在末尾
            redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e){
            logger.error("设置Student缓存失败", e);
            return false;
        }
    }


    /**
     * 获取list缓存
     * @param key
     * @return
     */
    public List getList(String key){
        try {
            //range封装了lrange
            return redisTemplate.opsForList().range(key, 0 ,-1);
        } catch (Exception e){
            logger.error("获取Student缓存失败", e);
            return null;
        }
    }
}