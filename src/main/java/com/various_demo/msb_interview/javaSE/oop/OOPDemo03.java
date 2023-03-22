package com.various_demo.msb_interview.javaSE.oop;

/**
 * @Describe: 匿名内部类
 *
 *
 * @Author Happy
 * @Create 2023/3/16-16:49
 **/
public class OOPDemo03 {
    public static void main(String[] args) {
        new X().fun2();
    }
    
    
}

interface Person{
     void sleep();
}


/**
 * 以下俩个User,如果需要重复实现接口方法,就需要不停的去写重复的代码.此时可以用匿名内部类的方式
 */
class User implements Person {
    
    @Override
    public void sleep() {
        System.out.println("睡觉真舒服");
    }
}

class User2 implements Person{
    
    @Override
    public void sleep() {
        // .....
        System.out.println("xxxx....");
    }
}

class X {
    void fun1(Person person){
        person.sleep();
    };
    
    void fun2(){
        this.fun1(new User());
    };
}
