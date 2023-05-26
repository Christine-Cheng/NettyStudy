package com.variousdemo.idempotency;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @Describe:
 * @Author Happy
 * @Create 2023/3/31-13:08
 **/
@RestController
@RequestMapping("/test")
public class TestController {
    
    @Resource
    private RedisTemplate redisTemplate;
    
    @GetMapping("/redisTest/{value}")
    public String redistTest(@PathVariable("value") String value){
        //将数据存入Redis
        redisTemplate.opsForValue().set("istource", "itsource=" + value, 2, TimeUnit.MINUTES);
        //从Redis中取值
        String valueFromRedis = (String)redisTemplate.opsForValue().get("istource");
        return valueFromRedis;
    }
    
    @GetMapping("/test01")
    public String test(){
        return "欢迎来到itsource-Redis学习课堂";
    }
}
