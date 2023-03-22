package com.a_demo_test_interview.aop;

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
    
    
    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            1, 1, 1,
            TimeUnit.SECONDS, new LinkedBlockingQueue<>(100));
    
    /**
     * 1.定义切入点
     * 2.横切逻辑
     * 3.植入(spring完成)
     */
    @Pointcut("@annotation(com.a_demo_test_interview.aop.RecordOperate)")
    public void pointCut() {
    }
    
    @Around("pointCut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object result = proceedingJoinPoint.proceed();
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
                /**
                 * operateLogDo.setOrderId();//此处的日志模型中 订单号为orderId ,
                 *
                 * 那么用反射+if判断的方式 去取不同类的订单号字段的值是不现实的,字段都不一定一样.
                 *
                 * 而在Service的注解中"@RecordOperate"写回调函数显然不可行,因为在Java的注解中只能是基本类型和class类型
                 *
                 * 那么方法是,定义一个接口,通过指定的方式去转换,根据不同入参,把值去给到标准模型.
                 * 例如定义接口Covert,然后把入参转化为日志标准模型OperateLogDo,这样就可以根据不同的入去实现Covert接口就行
                 * 由此诞生了SaveOrderCovert,UpdateOrderCovert,通过实现Covert接口,
                 * 把SaveOrder的orderId字段值和UpdateOrder的id字段赋值给日志标准模型OperateLogDo
                 *
                 * 而在注解中 "@RecordOperate" 中去添加Covert的泛型, 就可以实现通过注解获取各自的对象属性了
                 */
                
                
    
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
