package com.demo.nio;

import java.nio.IntBuffer;

/**
 * @Describe: debug理解buffer的4个参数性质,以及flip()切换读写
 * @Author: HAPPY
 * @Date: 2022-10-27 16:23 星期四
 **/
public class BasicBuffer {
    //理解buffer的flip()方法切换读写
    //debug理解Buffer的是个属性capacity position limit mark
    public static void main(String[] args) {
        IntBuffer intBuffer = IntBuffer.allocate(5);
        //intBuffer.put(2);
        //intBuffer.put(4);
        //intBuffer.put(6);
        //intBuffer.put(8);
        //intBuffer.put(10);
    
        for(int i = 0; i < intBuffer.capacity(); i++) {
            intBuffer.put( i * 2);
        }
    
        //intBuffer.position();
        //intBuffer.mark();
        //intBuffer.limit();
        //intBuffer.capacity();
        
        intBuffer.flip();//读写切换 写buffer 为 读buffer
        while (intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get());
        }
    }
    
}
