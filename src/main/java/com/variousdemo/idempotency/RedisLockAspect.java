package com.variousdemo.idempotency;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @Describe:
 * @Author Happy
 * @Create 2023/3/30-17:49
 **/
@Slf4j
@Aspect
@Component
public class RedisLockAspect {
    @Resource
    private RedisTool redisTool;
    
    /**
     * 环绕通知
     */
    @Around(value = "@annotation(redisLock)", argNames = "joinPoint,redisLock")
    public Object around(ProceedingJoinPoint joinPoint, RedisLock redisLock) throws Throwable {
        //获取入参商品ID
        String productId = "1001";
        //获取到线程名
        String threadName = Thread.currentThread().getName();
        
        Object result = null;
        try {
            /**
             * 开始尝试获取锁了，返回true表示当前线程获取到了锁，返回false表示没有获取到锁
             */
            boolean lock = getLock(productId, threadName);
            if (lock) {
                log.info("线程：{}，获取到了锁，开始处理业务", threadName);
                
                //执行业务逻辑
                result = joinPoint.proceed();
                
                //代码运行到这，业务做完，需要释放锁了
                redisTool.unlock(productId, threadName);  //释放锁
                log.info("线程：{}，业务代码处理完毕，锁已释放", threadName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    /**
     * 尝试获取锁
     */
    public boolean getLock(String key, String value){
        boolean lock = redisTool.lock(key, value);
        if(lock){
            return true;
        }else{
            //没有获取到锁的线程，进行自旋，直至拿到锁为止
            return getLock(key, value);
        }
    }
}
