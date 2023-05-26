package com.variousdemo.idempotency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * @Describe:
 * @Author Happy
 * @Create 2023/3/30-22:15
 **/
@Component
public class RedisTool {
    
    @Autowired
    public RedisTemplate redisTemplate;
    
    /**
     * 根据key删除对应的value
     * @param key
     * @return
     */
    public boolean remove(final String key) {
        if (exists(key)) {
            Boolean delete = redisTemplate.delete(key);
            return delete;
        }
        return false;
    }
    
    /**
     * 根据key判断缓存中是否有对应的value
     * @param key
     * @return
     */
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }
    
    /**
     * 写入缓存设置时效时间
     * @param key
     * @param value
     * @param expireTime
     * @return
     */
    public boolean setEx(final String key, Object value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    /**
     * 获取锁
     * @param lockKey    锁
     * @param value      身份标识（保证锁不会被其他人释放）
     * @return 获取锁成功返回true, 获取锁失败返回false
     */
    public boolean lock(String lockKey, String value) {
        return redisTemplate.opsForValue().setIfAbsent(lockKey, value);
    }
    
    /**
     * 释放锁
     * @param key
     * @param value
     * @return 成功返回true, 失败返回false
     */
    public boolean unlock(String key, String value) {
        Object currentValue = redisTemplate.opsForValue().get(key);
        boolean result = false;
        if(StringUtils.hasLength(String.valueOf(currentValue)) && currentValue.equals(value)){
            result = redisTemplate.opsForValue().getOperations().delete(key);
        }
        return result;
    }
    
    /**
     * 获得缓存的基本对象。
     *
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     */
    public <T> T getCacheObject(final String key) {
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        return operation.get(key);
    }
    
    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key 缓存的键值
     * @param value 缓存的值
     * @param timeout 时间
     * @param timeUnit 时间颗粒度
     */
    public <T> void setCacheObject(final String key, final T value, final Integer timeout, final TimeUnit timeUnit) {
        if(timeout == -1){
            //不设置过期时间，表示永久有效
            redisTemplate.opsForValue().set(key, value);
        }
        else{
            redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
        }
    }
}
