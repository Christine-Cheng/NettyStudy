package com.various_demo.msb_interview.javaSE;

/**
 * @Describe: instanceof是一个双目运算符,判断是否对应的类型.
 * @Author Happy
 * @Create 2023/3/7-21:49
 **/
public class TestInstanceof {
    public static void main(String[] args) {
        
        int i = 0;
        //System.out.println("基本类型" + i instanceof Integer);//编译不通过 i必须是引用类型，不能是基本类型
        //System.out.println(i instanceof Object);//编译不通过
        
        Integer integer = new Integer(1);
        System.out.println("包装类型");
        System.out.println(integer instanceof Integer);//true
        
        //false ,在 JavaSE规范 中对 instanceof 运算符的规定就是：如果 obj 为 null，那么将返回 false。
        System.out.println("判断null是否是object");
        System.out.println(null instanceof Object);
    }
}
