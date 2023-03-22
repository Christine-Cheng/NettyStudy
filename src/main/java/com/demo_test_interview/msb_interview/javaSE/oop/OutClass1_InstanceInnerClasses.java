package com.demo_test_interview.msb_interview.javaSE.oop;

/**
 * @Describe: 内部类: 成员内部类
 *
 * 普通成员内部类:属于外部实例对象
 *
 * @Author Happy
 * @Create 2023/3/7-23:04
 **/
public class OutClass1_InstanceInnerClasses {
    public static void main(String[] args) {
        Inner Inner = new OutClass1_InstanceInnerClasses().new Inner();
        System.out.println();
        Student student=new Student();
        student.show();
    }
    
    private String name = "张三";
    
    void info(){
        System.out.println("");
    }
    
    /**
     * 普通成员内部类:属于外部实例对象
     */
    class Inner {
        public void show(){
            System.out.println(name);
        }
    }
    
    public void test2(){
    
    }
}

class Student{
    
    public void show(){
        //Inner(); 是无法直接获取的,
        System.out.println("我是学生");
        
    }

}
