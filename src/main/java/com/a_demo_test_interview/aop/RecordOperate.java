package com.a_demo_test_interview.aop;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Describe:
 * @Author Happy
 * @Create 2023/3/3-15:58
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface RecordOperate {
    
    String desc() default "";
    
    Class<? extends Covert> covert();
}
