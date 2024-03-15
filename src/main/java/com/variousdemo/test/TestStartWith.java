package com.variousdemo.test;

/**
 * @Describe:
 * @Author Happy
 * @Create 2024-01-05 15:51:23
 **/
public class TestStartWith {
    public static void main(String[] args) {
        String check1 = "一致";
        String check2 = "不一致";
        String check3 = "一致正常";
        String check4 = "不一致异常";
        String check5 = "";
        String check6 = null;
        
        System.out.println("一致1"+check1.startsWith("一致"));
        System.out.println("一致2"+check2.startsWith("一致"));
        System.out.println("一致2.2"+check2.startsWith("不一致"));
        System.out.println("一致3"+check3.startsWith("正常"));
        System.out.println("一致3.2"+check3.startsWith("一致正常"));
        System.out.println("一致4"+check4.startsWith("一致"));
        System.out.println("一致4.2"+check4.startsWith("不一致"));
    
        System.out.println("5"+ check5.startsWith("一致"));
        System.out.println("6"+ check6.startsWith("一致"));
    }
}
