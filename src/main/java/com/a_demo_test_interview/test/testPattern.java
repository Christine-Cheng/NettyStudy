package com.a_demo_test_interview.test;

/**
 * @Describe: split 切不出来的时候
 * @Author: HAPPY
 * @Date: 2022-11-04 16:25 星期五
 **/
public class testPattern {
    public static void main(String[] args) {
        //split 切不出来的时候,加 参数 limit=-1
        String str = "@!@@!@@!@@!@@!@@!@@!@@!@@!@@!@@!@@!@@!@";
        String[] split = str.split("@!@", -1);
        System.out.println(split.length);
    }
}
