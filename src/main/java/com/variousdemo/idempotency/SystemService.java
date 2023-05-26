package com.variousdemo.idempotency;


import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Describe: Service层
 * @Author Happy
 * @Create 2023/3/31-13:11
 **/
@Service
public class SystemService {
    
    //模拟从数据库中查询数据，假设这份数据不经常改动，可能几年才会修改一次
    public List<SystemInfo> getSystemInfo(){
        List<SystemInfo> list = new ArrayList<>();
        SystemInfo systemInfo = null;
        for(int i=1; i<=100; i++){
            systemInfo = new SystemInfo();
            systemInfo.setId(Long.valueOf(i));
            systemInfo.setKey("key值_" + i);
            systemInfo.setValue("value值_" + i);
            list.add(systemInfo);
        }
        return list;
    }
}
