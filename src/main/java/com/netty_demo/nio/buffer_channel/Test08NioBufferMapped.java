package com.netty_demo.nio.buffer_channel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Describe: Buffer和Channel的注意事项和细节3: MappedByteBuffer可以在内存(堆外内存)中直接修改文件,操作系统不需要拷贝一次
 * MappedByteBuffer,可以让文件直接在内存（堆外的内存）中进行修改,而如何同步到文件由NIO 来完成
 * @Author: HAPPY
 * @Date: 2022-10-31 14:30 星期一
 **/
public class Test08NioBufferMapped {
    public static void main(String[] args) {
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile("./staticFile/file01.txt", "rw");//rw:读写模式
            //获取channel
            FileChannel channel = randomAccessFile.getChannel();
    
            /**
             * 参数1: FileChannel.MapMode.READ_WRITE 设置读写模式
             * 参数2: 0  可以直接修改的起始位置
             * 参数3: 5  是映射到内存的大小(不是索引位置) ,即将file01.txt 的多少个字节映射到内存
             * 0-5 即是从position index = 0开始,可修改的字节大小,实际position index范围[0-4]
             * MappedByteBuffer的实际类型DirectByteBuffer
             */
            MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
            mappedByteBuffer.put(0, (byte) 'H');
            mappedByteBuffer.put(4, (byte) 'o');
            
            //mappedByteBuffer.put(5, (byte) '/');//IndexOutOfBoundsException
            //结论:MappedByteBuffer中的第三个参数是可修改的字节大小,而不是索引位置
            
            System.out.println("修改成功~");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
