package com.demo.nio.buffer_channel;

import java.nio.ByteBuffer;

/**
 * @Describe: Buffer和Channel的注意事项和细节1: Buffer类型化和只读
 * 1) ByteBuffer 支持类型化的put 和get, put 放入的是什么数据类型，get 就应该使用相应的数据类型来取出，否
 * 则可能有BufferUnderflowException 异常
 *
 * 注意:放入的数据类型是什么顺序,取出的时候就是什么顺序,不然肯能内存溢出,或者报错BufferUnderflowException
 *
 * @Author: HAPPY
 * @Date: 2022-10-31 10:57 星期一
 **/
public class Test06NioBufferPutGetDataType {
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(64);
        //类型化放置数据
        byteBuffer.putInt(99);
        byteBuffer.putLong(9L);
        byteBuffer.putChar('衡');
        byteBuffer.putShort((short) 3);
        
        //反转
        byteBuffer.flip();
    
        System.out.println(byteBuffer.getInt());
        System.out.println(byteBuffer.getLong());
        System.out.println(byteBuffer.getChar());
        System.out.println(byteBuffer.getShort());
    }
}
