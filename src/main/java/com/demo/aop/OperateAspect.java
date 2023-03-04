package com.demo.aop;

import com.google.gson.Gson;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Describe:
 * @Author Happy
 * @Create 2023/3/3-15:59
 **/
@Component
@Aspect
public class OperateAspect {
    
    private Boolean result = true;
    
    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            1, 1, 1,
            TimeUnit.SECONDS, new LinkedBlockingQueue<>(100));
    
    /**
     * 1.定义切入点
     * 2.横切逻辑
     * 3.植入(spring完成)
     */
    @Pointcut("@annotation(com.demo.aop.RecordOperate)")
    public void pointCut() {
    }
    
    @Around("pointCut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object proceed = proceedingJoinPoint.proceed();
        threadPoolExecutor.execute(() -> {
            try {
                MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
                RecordOperate annotation = signature.getMethod().getAnnotation(RecordOperate.class);
                
                Class<? extends Covert> covert = annotation.covert();
                Covert logCovert = logCovert = covert.newInstance();
                logCovert.covert(proceedingJoinPoint.getArgs()[0]);
                
                OperateLogDo operateLogDo = new OperateLogDo();
                operateLogDo.setDesc(annotation.desc());
                operateLogDo.setResult(result.toString());
    
                System.out.println("insert operating "+ new Gson().toJson(operateLogDo));
                
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            
        });
        return result;
    }
}
