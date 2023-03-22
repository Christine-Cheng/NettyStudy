package com.netty_demo.nio.buffer_channel;

import java.nio.ByteBuffer;

/**
 * @Describe: Buffer和Channel的注意事项和细节2: buffer只读
 * @Author: HAPPY
 * @Date: 2022-10-31 11:22 星期一
 **/
public class Test07NioBufferReadOnly {
    
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(33);
        for (int i = 0; i < byteBuffer.capacity(); i++) {
            byteBuffer.put((byte) i);
        }
        
        //反转:读取
        byteBuffer.flip();
        
        //设置只读
        ByteBuffer readOnlyBuffer = byteBuffer.asReadOnlyBuffer();
        System.out.println(readOnlyBuffer.getClass());//class是java.nio.HeapByteBufferR

        while (readOnlyBuffer.hasRemaining()) {
            System.out.println(readOnlyBuffer.get());
        }

        //测试只读后是否能够继续写入
        readOnlyBuffer.put((byte) 33);
        //结果: 报错异常java.nio.ReadOnlyBufferException
    }
}
