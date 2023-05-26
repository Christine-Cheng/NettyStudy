package com.variousdemo.idempotency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

/**
 * @Describe: Redis配置类，目的是做序列化
 * @Author Happy
 * @Create 2023/3/31-13:10
 **/
@Configuration
public class CacheConfig extends CachingConfigurerSupport {
    
    @Autowired
    private RedisConnectionFactory factory;
    
    /**
     * 向Spring容器中注入一个 RedisTemplate 对象
     * 采用 GenericJackson2JsonRedisSerializer 这个序列化器进行序列化
     */
    @Bean
    public RedisTemplate<Object, Object> redisTemplate() {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        //序列化器
        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        //String数据key的序列化
        redisTemplate.setKeySerializer(genericJackson2JsonRedisSerializer);
        //String数据value的序列化
        redisTemplate.setValueSerializer(genericJackson2JsonRedisSerializer);
        
        //hash结构key的序列化
        redisTemplate.setHashKeySerializer(genericJackson2JsonRedisSerializer);
        //hash结构value的序列化
        redisTemplate.setHashValueSerializer(genericJackson2JsonRedisSerializer);
        return redisTemplate;
    }
}
