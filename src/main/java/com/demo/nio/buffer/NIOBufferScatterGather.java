package com.demo.nio.buffer;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * @Describe: NIO 还支持通过多个Buffer (即Buffer 数组) 完成读写操作
 * 通过scattering()和 gathering 处理
 * Scattering: 将数据写入到buffer 时,可以采用buffer 数组,依次写入[分散]
 * Gathering: 从buffer 读取数据时,可以采用buffer 数组,依次读[聚合]
 * @Author: HAPPY
 * @Date: 2022-10-31 16:51 星期一
 **/
public class NIOBufferScatterGather {
    public static void main(String[] args) {
    
        try {
            //建立服务端SocketChannel并启动
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            //建立Socket端口地址
            InetSocketAddress inetSocketAddress = new InetSocketAddress(7777);
            //绑定端口
            serverSocketChannel.socket().bind(inetSocketAddress);
            
            //建立buffer[]数组
            ByteBuffer[] byteBufferArr = new ByteBuffer[2];
            byteBufferArr[0] = ByteBuffer.allocate(5);
            byteBufferArr[1] = ByteBuffer.allocate(3);
            
            //等待客户链接
            SocketChannel socketChannel = serverSocketChannel.accept();
            
            int byteMsg = 8;//假设从客户端接受8个字节
            while (true) {
                int byteRead = 0;
                while (byteMsg < byteRead) {
                    long l = socketChannel.read(byteBufferArr);
                    byteRead += l;
                    System.out.println("字节数" + byteRead);
                    Arrays.asList(byteBufferArr).stream().map(byteBuffer ->
                                    "position=" + byteBuffer.position() + "," +
                                    "limit=" + byteBuffer.limit()).
                            forEach(System.out::println);
                }
            }
            
            
            //反转buffer
            //for (int i = 0; i < byteBufferArr.length; i++) {
            //    byteBufferArr[i].flip();
            //}
            
            //向客户端输出
            
            
            
            
    
    
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
