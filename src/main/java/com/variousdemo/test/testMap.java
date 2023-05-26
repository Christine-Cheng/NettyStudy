package com.variousdemo.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Describe:
 * @Author Happy
 * @Create 2023/4/2-11:30
 **/
public class testMap {
    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("姓名", "小明");
        map.put("性别", "男");
        map.put("年龄", "9");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println(entry);
        }
        
        for (String mapKey : map.keySet()) {
            System.out.println(mapKey);
        }
        
        for (String mapValue : map.values()) {
            System.out.println(mapValue);
        }
        
        List<String> list = new ArrayList<>();
        list.add("小明");
        list.add("小红");
        list.add("老王");
        list = list.stream().
                filter(o -> !"老王".equals(o)).
                collect(Collectors.toList());
        list.removeIf(o -> o.equals("小红"));
        System.out.println(list);
        
        
        List numberList = new ArrayList(Arrays.asList(1, 2, 3, 4, 5));
        long last = System.currentTimeMillis();
        List collect = (List) numberList.stream().filter(a -> !a.equals(2)).collect(Collectors.toList());
        System.out.println(System.currentTimeMillis() - last);//41~44
        System.out.println(collect);
        numberList.removeIf(o -> o.equals(5));
        System.out.println(numberList);
        
    }
}
