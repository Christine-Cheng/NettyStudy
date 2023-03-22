package com.various_demo.msb_interview.javaSE;

public class Demo01TryFinally {
    
    
    public static void main(String[] args) {
        System.out.println(fun1() + "");
    }
    
    /**
     * 结论：try catch finally
     * 在try语块中,返回数据被缓存起来,返回的res映射的值是99
     * 在finally语块中,res映射的值依旧是99,但是做了其他操作res = res*100; 此时
     *
     * @return
     */
    public static int fun1() {
        int res = 99;
        try {
            //res = res / 0;
            return res; // 返回的数据被缓存起来。
        } catch (Exception e) {
            res++;
            return res;
        } finally {
            System.out.println("-----");
            res = res * 100;
            // return res;//9900
        }
        
    }
}
