package com.a_demo_test_interview.msb_interview.javaSE;

/**
 * @Describe:
 * @Author Happy
 * @Create 2023/3/19-8:57
 **/
public class Demo02TryFinally {
    public static void main(String[] args) {
        Too too = new Too();
        StringBuilder t1 = test(too);
        System.out.println("return语句返回的:" + t1 + "\t返回值的hashCode:" + t1.hashCode()); //460141958
        System.out.println("finaly里面修改的:" + too.num + "\tfinaly的hashCode:" + too.num.hashCode()); // 1163157884
        
    }
    
    public static StringBuilder test(Too too) {
        try {
            too.num = new StringBuilder("try");
            System.out.println("try字符串的hashcode:" + ("try").hashCode()); // 115131
            System.out.println("StringBuilder里的try的hashCode:" + too.num.hashCode());//--语句1   460141958
            return too.num; //语句2 460141958
        } finally {
            too.num = new StringBuilder("finaly");//语句3  1163157884
            System.out.println("finaly的hashCode:" + too.num.hashCode());//语句4
        }
    }
}

class Too {
    StringBuilder num = new StringBuilder("你好");
}
