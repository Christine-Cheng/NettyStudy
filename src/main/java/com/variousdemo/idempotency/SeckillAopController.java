package com.variousdemo.idempotency;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

/**
 * @Describe:
 * @Author Happy
 * @Create 2023/3/30-17:52
 **/
@Slf4j
public class SeckillAopController {
    @Autowired
    private RedisTool redisTool;
    
    /**
     * 用户下单接口
     */
    @GetMapping("/saveOrder/{productId}")
    @RedisLock
    public ResponseEntity<String> saveOrder(@PathVariable("productId") String productId) {
        //这里应该是从数据库查询得到商品剩余库存的，我这里是模拟，直接从Redis中获取到剩余库存
        Object test_product = redisTool.getCacheObject("test_product");
        if (test_product == null) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("lock error");
        }
        //stock的值，就是当前商品的库存剩余量
        int stock = Integer.parseInt(test_product.toString());
        //判断库存是否大于0，有库存才做库存扣减
        if (stock > 0) {
            //库存扣减（这里假设每个人只能下单买一个）
            int currentStock = stock - 1;
            //更新库存值
            redisTool.setCacheObject("test_product", currentStock, -1, TimeUnit.SECONDS);
            return ResponseEntity.status(HttpStatus.OK).body("save order success,current stock:" + currentStock);
        } else {
            //运行到此处，说明该线程虽然获取到了锁，但是晚了，库存已经被抢光了
            log.info("线程：{}，拿到锁了，但是库存不足了", Thread.currentThread().getName());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("stock is zero");
        }
    }
}
