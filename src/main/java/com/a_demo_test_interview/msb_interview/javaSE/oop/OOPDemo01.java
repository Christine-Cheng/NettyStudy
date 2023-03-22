package com.a_demo_test_interview.msb_interview.javaSE.oop;

/**
 * @Describe: 外部类中包含 静态成员内部类
 *
 * 静态内部类的使用场景:需要探明
 * 这个静态类是属于外部类实例,还是属于外部类类对象
 *
 *
 * @Author Happy
 * @Create 2023/3/8-15:43
 **/
public class OOPDemo01 {
    public static void main(String[] args) {
        //1.获取外部类对象
        OutterStaticInner out = new OutterStaticInner();
        out.show();
        
        //2.获取外部类的静态内部类对象,调用内部类的静态方法
        OutterStaticInner.Inner.info2();//先找到外部类.静态内部类.调用静态方法
        
        //3.获取外部类的静态内部类对象,调用内部类的普通方法
        //外部类Otter的内部对象Inner,获取其普通方法.
        //即是外部类的类型得到内部类的构造器,new得到内部类对象,然后调用内部类普通方法
        new OutterStaticInner.Inner().info1();
        
        
    }
    
}

/**
 * 外部类中包含 静态成员内部类
 */
class OutterStaticInner {
    
    public static String name = "张三";
    
    
    public static void show() {
        System.out.println(name);
    }
    
    /**
     * 定义一个静态内部类(被static修饰的成员内部类称为内部类)
     * 理清楚static修饰符
     *
     *
     *
     * 内部可以声明所有属性.
     */
    static class Inner {
        public static String name = "李四";
        
        public int age = 20;
        
        public void info1() {
            System.out.println(age);
        }
        
        public static void info2() {
            System.out.println("内部类" + name);
        }
    }
}
