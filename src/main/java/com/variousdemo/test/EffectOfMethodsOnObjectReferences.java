package com.variousdemo.test;

/**
 * @Describe: 对象引用 值引用
 * @Author Happy
 * @Create 2023-12-12 13:45:48
 **/
public class EffectOfMethodsOnObjectReferences {
    public static void main(String[] args) {
        Classroom classroom = new Classroom("Object A");
        System.out.println("Before method: " + classroom.getName());
        modifyObject(classroom);
        System.out.println("After method: " + classroom.getName());
    
        System.out.println("**************************************");
    
        System.out.println("开始");
        Classroom classroom2 = new Classroom("高一九班");
        System.out.println(classroom2.getName());
        System.out.println("before method");
        modifyObjectAssigning(classroom2);
        System.out.println(classroom2.getName());
        System.out.println("after method");
        System.out.println("结束");
        
    }
    
    public static void modifyObject(Classroom obj) {
        System.out.println("分配新对象给该引用");
        obj = new Classroom("Object B");
        System.out.println("Inside method: " + obj.getName());
    }
    
    public static void modifyObjectAssigning(Classroom obj){
        System.out.println("不分配新对象给该引用");
        obj.setName("高一八班");
    }
}

class Classroom {
    private String name;
    
    public Classroom(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
}
