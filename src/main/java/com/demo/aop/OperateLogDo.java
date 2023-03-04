package com.demo.aop;

import lombok.Data;

/**
 * @Describe:
 * @Author Happy
 * @Create 2023/3/3-15:38
 **/
@Data
public class OperateLogDo {
    
    private Long orderId;
    
    private String desc;
    
    private String result;
}
