package com.variousdemo.idempotency;


import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Describe: 系统接口API
 * @Author Happy
 * @Create 2023/3/31-13:11
 **/
@RequestMapping("/system")
@RestController
public class SystemController {
    
    @Resource
    private SystemService systemService;
    
    /**
     * 获取系统数据
     * @return
     */
    @GetMapping("/getSystemInfo")
    public List<SystemInfo> getSystemInfo(){
        System.out.println("获取系统数据");
        List<SystemInfo> list = systemService.getSystemInfo();
        return list;
    }
}
