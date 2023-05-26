package com.variousdemo.aop;

import lombok.Data;

/**
 * @Describe: 定义一个操作类的标准模型
 * @Author Happy
 * @Create 2023/3/3-15:38
 **/
@Data
public class OperateLogDo {
    
    private Long orderId;
    
    private String desc;
    
    private String result;
}
