package com.a_demo_test_interview.msb_interview.clones;

/**
 * @Describe:
 * @Author Happy
 * @Create 2023/3/17-23:37
 **/
public class DeepCloning1CreateObjTest {
    public static void testNew(int size){
        long start = System.currentTimeMillis();
        for(int i=0;i<size;i++){
            User3 t = new User3();
        }
        long end = System.currentTimeMillis();
        System.out.println("new的方式创建耗时："+(end-start));
    }
    
    public static void testClone(int size) throws CloneNotSupportedException{
        long start = System.currentTimeMillis();
        User3 t = new User3();
        for(int i=0;i<size;i++){
            User3 temp = (User3) t.clone();
        }
        long end = System.currentTimeMillis();
        System.out.println("clone的方式创建耗时："+(end-start));
    }
    
    
    public static void main(String[] args) throws Exception {
        testNew(10);
        testClone(10);
    }
}


class User3 implements Cloneable {  //用户
    public User3() {
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
