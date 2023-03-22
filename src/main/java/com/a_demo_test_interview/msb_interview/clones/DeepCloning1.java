package com.a_demo_test_interview.msb_interview.clones;


import java.util.Date;

/**
 * @Describe: 深度克隆:第一种是在浅克隆的基础上实现
 * https://blog.csdn.net/qq_38526573/article/details/87633257
 *
 * 被复制对象的所有变量都含有与原来的对象相同的值，除去那些引用其他对象的变量。
 * 那些引用其他对象的变量将指向被复制过的新对象，而不再是原有的那些被引用的对象。
 * 换言之，深复制把要复制的对象所引用的对象都复制了一遍。
 *
 * 深度克隆(deep clone)有两种实现方式，
 *      第一种是在浅克隆的基础上实现，
 *      第二种是通过序列化和反序列化实现
 *
 * @Author Happy
 * @Create 2023/3/17-17:14
 **/
public class DeepCloning1 {
    public static void main(String[] args) throws CloneNotSupportedException {
        Date date =  new Date(1231231231231l);
        User2 user = new User2();
        user.setName("波波烤鸭");
        user.setAge(18);
        user.setBirth(date);
        System.out.println("----输出原型对象的属性------");
        System.out.println(user);
        System.out.println(user.getName());
        System.out.println(user.getBirth());
        // 克隆对象
        User2 user1 =(User2) user.clone();
        // 修改原型对象中的属性
        date.setTime(123231231231l);
        System.out.println(user.getBirth());
    
        // 修改参数
        user1.setName("dpb");
        System.out.println("-------克隆对象的属性-----");
        System.out.println(user1);
        System.out.println(user1.getName());
        System.out.println(user1.getBirth());
    }
}
