package com.variousdemo.idempotency;

import lombok.Data;

/**
 * @Describe: 系统信息实体类
 * @Author Happy
 * @Create 2023/3/31-13:12
 **/
@Data
public class SystemInfo {
    private Long id;
    private String key;
    private String value;
}
