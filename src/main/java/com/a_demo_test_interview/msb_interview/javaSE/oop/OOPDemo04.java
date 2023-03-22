package com.a_demo_test_interview.msb_interview.javaSE.oop;

/**
 * @Describe:
 * @Author Happy
 * @Create 2023/3/16-17:04
 **/
public class OOPDemo04 {
    public static void main(String[] args) {
    
    }
    
}

interface Person1{
    void sleep();
}

/*class User implements Person{

	@Override
	public void sleep() {
		System.out.println("睡觉真舒服啊....");

	}

}*/

/**
 * 匿名内部类的方式
 *
 * 接口的实现多样,通过匿名内部类可以灵活的处理
 */
class X1{
    
    void fun1(Person1 person1){
        person1.sleep();
    }
    
    void fun2(){
        this.fun1(new Person1() {
            @Override
            public void sleep() {
                System.out.println("AAAAAAAAA......睡觉真舒服..........");
            }
        });
    
        this.fun1(new Person1(){
            @Override
            public void sleep() {
                // 文件扫描
            }
        });
        
        this.fun1(new Person1(){
            @Override
            public void sleep() {
                // 数据清洗
            }
        });
    
        /**
         * JDK8 中 lambda 表达式
         */
        this.fun1(()->{
            System.out.println("BBBBBBBBB.........睡觉真舒服...........");
        });
    }
}
