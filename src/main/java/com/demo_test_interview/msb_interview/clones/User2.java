package com.demo_test_interview.msb_interview.clones;

import java.io.Serializable;
import java.util.Date;

/**
 * 原型类：被克隆的类型
 * 深度克隆测试
 * @author dengp
 *
 */
public class User2 implements Cloneable,Serializable{
    
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
     * 深度克隆(deep clone)
     */
    public Object clone() throws CloneNotSupportedException{
        Object object = super.clone();
        // 实现深度克隆(deep clone)
        User2 user = (User2)object;
        user.birth = (Date) this.birth.clone();
        return object;
    }
}
