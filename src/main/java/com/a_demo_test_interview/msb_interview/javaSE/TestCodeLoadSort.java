package com.a_demo_test_interview.msb_interview.javaSE;

/**
 * @Describe: 代码块加载顺序
 * 执行结果:
 * 加载静态代码块
 * 加载主函数
 * 加载代码块
 * 加载构造函数
 * --------------以下主函数调用的方法-------------
 * 姓名: 张三
 * 年龄: 25
 * <p>
 * 由此可见:
 * 加载顺序: 1.静态代码块  2.主函数  3.代码块  4.加载构造函数  5.主函数调用的方法
 * @Author Happy
 * @Create 2023/3/14-14:41
 **/
public class TestCodeLoadSort {
    private String name = "张三";
    private int age = 25;
    
    public TestCodeLoadSort() {
        System.out.println("加载构造函数");
    }
    
    public TestCodeLoadSort(String name) {
        this.name = name;
    }
    
    public TestCodeLoadSort(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    {
        System.out.println("加载代码块");
    }
    
    static {
        System.out.println("加载静态代码块");
    }
    
    public void test() {
        System.out.println("--------------以下主函数调用的方法-------------");
        System.out.println("姓名: " + name);
        System.out.println("年龄: " + age);
    }
    
    public static void main(String[] args) {
        System.out.println("加载主函数");
        TestCodeLoadSort testCodeLoadSort = new TestCodeLoadSort();
        testCodeLoadSort.test();
        
    }
    

}
