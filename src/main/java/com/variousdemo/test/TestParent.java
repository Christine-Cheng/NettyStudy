package com.variousdemo.test;

/**
 * @Describe:
 * @Author: HAPPY
 * @Date: 2022-11-04 15:33 星期五
 **/
public class TestParent {
    
    {System.out.println("Parent code block");}
    
    static {System.out.println("Parent static code block");}
    
    public TestParent() {
        System.out.println("Parent constructor");
    }
    
    
    
}
