package com.demo.nio.buffer_channel;

import java.nio.IntBuffer;

/**
 * @Describe:
 * 1.debug理解buffer的4个参数性质:
 * capacity()容量,即是可以容纳最大数据,在缓冲区创建时被设定且不可以改变
 * position()位置,即是下一个要被读或者写的元素的索引,每次写缓冲区数据时都会改变值,为下一次读写准备
 * limit()即是缓冲区的当前终点(对当前数组的最多操作多少个数),不能对缓冲区超过极限的位置进行读写操作,且极限不可以修改
 * mark()标记
 *
 * 2.以及flip()切换读写  put()存放数据  get()获取数据
 *
 * 3.Buffer的7种子数据类型:int  float  char  double  short  long  byte
 *
 * 4.缓冲区(Buffer):缓冲区本质上是一个可以读写数据的内存块,可以理解成是一个容器对象(含数组),
 * 该对象提供了一组方法,可以更轻松地使用内存块,缓冲区对象内置了一些机制,能够跟踪和记录缓冲区的状态变化
 *
 *
 *
 * @Author: HAPPY
 * @Date: 2022-10-27 16:23 星期四
 **/
public class Test01BasicBuffer {
    //理解buffer的flip()方法切换读写
    //debug理解Buffer的是个属性capacity position limit mark
    public static void main(String[] args) {
        //创建一个Buffer, 大小为5, 即可以存放5 个int
        IntBuffer intBuffer = IntBuffer.allocate(5);
    
        //向buffer 存放数据
        //intBuffer.put(2);
        //intBuffer.put(4);
        //intBuffer.put(6);
        //intBuffer.put(8);
        //intBuffer.put(10);
    
        for(int i = 0; i < intBuffer.capacity(); i++) {//capacity()取出容量大小
            intBuffer.put( i * 2);
        }
    
        //intBuffer.position();
        //intBuffer.mark();
        //intBuffer.limit();
        //intBuffer.capacity();
    
    
        /**
         * public final Buffer flip() {
         *    limit = position;//读数据不能超过,最大容量
         *    position = 0;//位置,回到初始位置0
         *    mark = -1;
         *    return this;
         * }
         */
        intBuffer.flip();//读写切换 写buffer 为 读buffer
        
        //intBuffer.position(1);//从第二个索引开始读
        //intBuffer.limit(3);//限制位置索引到第三个
        
        while (intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get());
        }
    }
    
    
}
