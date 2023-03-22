package com.demo_test_interview.msb_interview.clones;

/**
 * @Describe:
 * 原型模式和直接new对象方式的比较
 * 当我们需要大量的同一类型对象的时候可以使用原型模式，下面是两种方式的性能对比:
 *
 *
 * 测试普通new方式创建对象和clone方式创建对象的效率差异！
 * 如果需要短时间创建大量对象，并且new的过程比较耗时。则可以考虑使用原型模式！
 * @Author Happy
 * @Create 2023/3/17-23:37
 **/
public class DeepCloning2CreateObjTest {
    public static void main(String[] args) throws Exception {
        testNew(1000);
        testClone(1000);
    }
    
    public static void testNew(int size){
        long start = System.currentTimeMillis();
        for(int i=0;i<size;i++){
            User4 t = new User4();
        }
        long end = System.currentTimeMillis();
        System.out.println("new的方式创建耗时："+(end-start));
    }
    
    public static void testClone(int size) throws CloneNotSupportedException{
        long start = System.currentTimeMillis();
        User4 t = new User4();
        for(int i=0;i<size;i++){
            User4 temp = (User4) t.clone();
        }
        long end = System.currentTimeMillis();
        System.out.println("clone的方式创建耗时："+(end-start));
    }
    
    
  
}


class User4 implements Cloneable {  //用户
    public User4() {
        try {
            Thread.sleep(10);  //模拟创建对象耗时的过程!
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    protected Object clone() throws CloneNotSupportedException {
        Object obj = super.clone();  //直接调用object对象的clone()方法！
        return obj;
    }
}
