package com.variousdemo.msb_interview.javaSE.oop;

/**
 * @Describe: 局部内部类
 * @Author Happy
 * @Create 2023/3/8-17:06
 **/
public class OOPDemo02 {
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Outter02 out = new Outter02();
        out.show();
    }
}

/**
 * 方法中包含有类:局部内部类
 */
class Outter02{
    
     int num1 = 20;//num1的生命周期是跟着Outter02的
    
    /**
     * 在JDK1.8之后 把局部内部类中使用的外部方法的局部变量默认的提升为 final
     * 在JDK1.8之前 这里会强制要求我们将 局部变量声明为final类型
     */
    public void show(){
        // 为什么什么需要加final修饰？  原因是:方法的生命周期(为了避免生命周期不同步)
        /**
         * 因为存在GC回收.当new了Inner()对象之后,此处执行完成代码:打印num2后,参数num2会被GC干掉
         */
         final int num2  = 999;
        
        //定义一个局部内部类
        //在方法回调的时候用的比较多.
        //内部类的生命周期和方法没有关系,和GC有关系
        class Inner{
            public void info(){
                //这里Outter02对象的参数num1没有final,此处引用的时候,是Inner与Outter02之间有引用关系
                System.out.println("inner info ..." + num1);
                //局部内部类的参数num2的生命周期是随Inner()的
                System.out.println("inner info ..." + num2);
            }
        }
        // 我们要使用内部类对象 调用其中的方法才会执行
        Inner in = new Inner();
        // num = 50;
        in.info();
        System.out.println(num2);
    }
}
