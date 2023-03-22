package com.various_demo.msb_interview.clones;

import java.util.Date;

/**
 * @Describe: 浅克隆
 * https://blog.csdn.net/qq_38526573/article/details/87633257
 *
 * 原型模式
 * 在java中我们知道通过new关键字创建的对象是非常繁琐的(类加载判断，内存分配，初始化等)，
 * 在我们需要大量对象的情况下，原型模式就是我们可以考虑实现的方式。
 * 原型模式我们也称为克隆模式，即一个某个对象为原型克隆出来一个一模一样的对象，
 * 该对象的属性和原型对象一模一样。而且对于原型对象没有任何影响。
 * 原型模式的克隆方式有两种：浅克隆和深度克隆
 *
 *
 * 浅克隆
 * 被复制对象的所有变量都含有与原来的对象相同的值，而所有的对其他对象的引用仍然指向原来的对象。
 * 换言之，浅复制仅仅复制所考虑的对象，而不复制它所引用的对象。
 * Object类提供的方法clone只是拷贝本对象,其对象内部的数组、引用对象等都不拷贝，还是指向原生对象的内部元素地址
 *
 * 实现
 *  被克隆的对象必须Cloneable,Serializable这两个接口
 *
 * 浅克隆的问题:
 *  虽然产生了两个完全不同的对象，但是被复制的对象的所有变量都含有与原来的对象相同的值，
 *  而所有的对其他对象的引用都仍然指向原来的对象。
 *
 * @Author Happy
 * @Create 2023/3/17-17:12
 **/
public class ShallowCloning {
    public static void main(String[] args) throws CloneNotSupportedException {
        Date date =  new Date(1231231231231l);
        User1 user1 = new User1();
        user1.setName("波波烤鸭");
        user1.setAge(18);
        user1.setBirth(date);
        System.out.println("----输出原型对象的属性------");
        System.out.println(user1);
        System.out.println(user1.getName());
        System.out.println(user1.getBirth());
        // 克隆对象
        User1 userClone =(User1) user1.clone();
        // 修改原型对象中的属性
        date.setTime(123231231231l);
        System.out.println(userClone.getBirth());
    
        // 修改参数
        userClone.setName("dpb");
        System.out.println("-------克隆对象的属性-----");
        System.out.println(userClone);
        System.out.println(userClone.getName());
        System.out.println(userClone.getBirth());
    }
}
