package com.variousdemo.msb_interview.clones;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

/**
 * @Describe: 深度克隆:第二种是通过序列化和反序列化实现
 *
 * 被复制对象的所有变量都含有与原来的对象相同的值，除去那些引用其他对象的变量。
 * 那些引用其他对象的变量将指向被复制过的新对象，而不再是原有的那些被引用的对象。
 * 换言之，深复制把要复制的对象所引用的对象都复制了一遍。
 *
 * 深度克隆(deep clone)有两种实现方式
 *      第一种是在浅克隆的基础上实现
 *      第二种是通过序列化和反序列化实现
 *
 * 名称:    说明:
 * 序列化	把对象转换为字节序列的过程
 * 反序列化	把字节序列恢复为对象的过程
 * @Author Happy
 * @Create 2023/3/17-23:30
 **/
public class DeepCloning2 {
    public static void main(String[] args) throws CloneNotSupportedException, Exception {
        Date date =  new Date(1231231231231L);
        User1 user = new User1();
        user.setName("波波烤鸭");
        user.setAge(18);
        user.setBirth(date);
        System.out.println("-----原型对象的属性------");
        System.out.println(user);
        System.out.println(user.getName());
        System.out.println(user.getBirth());
    
        //使用序列化和反序列化实现深复制
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream    oos = new ObjectOutputStream(bos);
        oos.writeObject(user);
        byte[] bytes = bos.toByteArray();
    
        ByteArrayInputStream  bis = new ByteArrayInputStream(bytes);
        ObjectInputStream	  ois = new ObjectInputStream(bis);
    
        //克隆好的对象！
        User1 user1 = (User1) ois.readObject();
    
        // 修改原型对象的值
        date.setTime(221321321321321L);
        System.out.println(user.getBirth());
    
        System.out.println("------克隆对象的属性-------");
        System.out.println(user1);
        System.out.println(user1.getName());
        System.out.println(user1.getBirth());
    }
}
