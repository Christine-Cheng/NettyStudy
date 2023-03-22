package com.various_demo.test;

import java.util.ArrayList;
import java.util.List;

/**
 * @Describe:
 * @Author: HAPPY
 * @Date: 2022-11-09 10:52 星期三
 **/
public class TestPartition {
    public static void main(String[] args) {
        List<Integer> stringList = new ArrayList<>();
        for (int i = 0; i < 150; i++) {
            stringList.add(i + 1);
        }
        List<List<Integer>> lists = splitList(stringList, 300);
        System.out.println(lists.size());
        lists.forEach(o-> System.out.println(o));
    
        
    
    }
    
   
    /**
     * 切分list
     *
     * @param sourceList
     * @param groupSize  每组定长
     * @return
     */
    public static <T> List<List<T>> splitList(List<T> sourceList, int groupSize) {
        int length = sourceList.size();
        // 计算可以分成多少组
        int num = (length + groupSize - 1) / groupSize;
        List<List<T>> newList = new ArrayList<>(num);
        for (int i = 0; i < num; i++) {
            // 开始位置
            int fromIndex = i * groupSize;
            // 结束位置
            int toIndex = (i + 1) * groupSize < length ? (i + 1) * groupSize : length;
            newList.add(sourceList.subList(fromIndex, toIndex));
        }
        return newList;
    }
    
    
    
    //public static List lambdaSplit(List list, int groupSize) {
    //    // 指定大小
    //    int maxNum = 100;
    //    // 切分次数
    //    int step = (list.size() + maxNum - 1) / maxNum;
    //    List<List<String>> collectList =
    //            Stream.iterate(0, n -> n + 1)
    //                    .limit(step)
    //                    .parallel()
    //                    .map(a -> list.stream()
    //                            .skip(a * maxNum)
    //                            .limit(maxNum)
    //                            .parallel()
    //                            .collect(Collectors.toList()))
    //                    .collect(Collectors.toList());
    //    return collectList;
    //}
}
