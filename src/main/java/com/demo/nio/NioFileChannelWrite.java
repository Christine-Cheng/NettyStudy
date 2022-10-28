package com.demo.nio;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Describe: 理解FileChannel写,及Channel和Buffer的交互
 * @Author: HAPPY
 * @Date: 2022-10-28 15:20 星期五
 **/
public class NioFileChannelWrite {
    public static void main(String[] args) {
        try {
            String str = "Hello, Siri";
            
            //NIO是对Java原生IO流的包装
            //故创建一个输出流--->Channel
            FileOutputStream fileOutputStream = new FileOutputStream("d:\\file01.txt");
            
            //通过fileOutputStream获取对应的FileChannel
            //此fileChannel的真实类型是FileChannelImpl
            FileChannel fileChannel = fileOutputStream.getChannel();
            
            //创建一个缓冲区(字节缓冲Buffer用的最多)
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            
            //将str写入byteBuffer
            byteBuffer.put(str.getBytes());
            
            //将byteBuffer用flip转换读写
            byteBuffer.flip();//转换为读
            
            //将buffer内容通过file流写fileChannel
            fileChannel.write(byteBuffer);
            
            //关闭流
            fileOutputStream.close();
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
