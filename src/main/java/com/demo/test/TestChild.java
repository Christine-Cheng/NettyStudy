package com.demo.test;

/**
 * @Describe:
 * @Author: HAPPY
 * @Date: 2022-11-04 15:33 星期五
 **/
public class TestChild extends TestParent{
    public static void main(String[] args) {
        System.out.println("testBlockLoader");
    }
    
    {System.out.println("child code block");}
    
    static {System.out.println("child static code block");}
    
    public TestChild() {
        System.out.println("child constructor");
    
        TestParent testParent = new TestParent();
        String simpleName = testParent.getClass().getSimpleName();
        System.out.println(simpleName);
    }
}
