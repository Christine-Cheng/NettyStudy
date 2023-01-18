package com.demo.nio.channel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Describe:
 * 1)使用前面学习后的ByteBuffer(缓冲)和FileChannel(通道), 将file01.txt 中的数据读入到程序,并显示在控制
 * 台屏幕
 * 2)假定文件已经存在
 *
 * 利用通道缓冲的方式读取文件,将通道引入IO中
 * 理解FileChannel写,及Buffer和的Channel交互
 * @Author: HAPPY
 * @Date: 2022-10-28 17:35 星期五
 **/
public class Test02NioFileChannelRead {
    public static void main(String[] args){
        try {
            //NIO是对Java原生IO流的包装
            //故创建一个输入流--->Channel
            String filePath = "./staticFile/file01.txt";
            File file = new File(filePath);
            FileInputStream fileInputStream = new FileInputStream(file);
        
            //通过fileOutputStream获取对应的FileChannel
            //此fileChannel的真实类型是FileChannelImpl
            FileChannel fileChannel = fileInputStream.getChannel();
        
            //创建一个缓冲区(字节缓冲Buffer用的最多)
            ByteBuffer byteBuffer = ByteBuffer.allocate((int)file.length());
    
            //将fileChannel的通道数据读到buffer
            fileChannel.read(byteBuffer);
            
            //将byteBuffer中的字节转换为String
            byte[] array = byteBuffer.array();
            System.out.println(new String(array));
            
            //关闭流
            fileInputStream.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
