package com.a_demo_test_interview.msb_interview.clones;


import java.io.Serializable;
import java.util.Date;

/**
 * 浅克隆
 * 被复制对象的所有变量都含有与原来的对象相同的值，而所有的对其他对象的引用仍然指向原来的对象。
 * 换言之，浅复制仅仅复制所考虑的对象，而不复制它所引用的对象。
 * Object类提供的方法clone只是拷贝本对象 ， 其对象内部的数组、引用对象等都不拷贝，还是指向原生对象的内部元素地址
 *
 * 被克隆的对象必须Cloneable,Serializable这两个接口
 *
 *
 * 原型类：被克隆的类型
 * @author dengp
 *
 */
public class User1 implements Cloneable,Serializable{
    
    private String name;
    
    private Date birth;
    
    private int age;
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Date getBirth() {
        return birth;
    }
    
    public void setBirth(Date birth) {
        this.birth = birth;
    }
    
    public int getAge() {
        return age;
    }
    
    public void setAge(int age) {
        this.age = age;
    }
    
    /**
     * 实现克隆的方法
     */
    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }
}
