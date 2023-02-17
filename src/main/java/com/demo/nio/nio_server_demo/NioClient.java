package com.demo.nio.nio_server_demo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @Describe:
 * @Author Happy
 * @Create 2023/1/22-0:19
 **/
public class NioClient {
    public static void main(String[] args) throws IOException {
        //得到一个网络通道
        SocketChannel socketChannel = SocketChannel.open();
        
        //设置非阻塞
        socketChannel.configureBlocking(false);
        
        //提供服务器端的ip和端口
        String ip = "";
        int port = 6666;
        InetSocketAddress inetSocketAddress = new InetSocketAddress(ip, port);
        
        //连接服务器
        if (!socketChannel.connect(inetSocketAddress)) {
            while (!socketChannel.finishConnect()) {
                System.out.println("因为连接需要时间,客户端不会阻塞,可以做其他工作...");
            }
        }
        
        //若连接成功,就发送数据
        String str = "Hello,FirstNettyTest~~~";//TODO 测试字符串
        //Wraps a byte array into a buffer.
        ByteBuffer byteBuffer = ByteBuffer.wrap(str.getBytes());
        //发送数据,将buffer数据写入channel
        socketChannel.write(byteBuffer);
        System.in.read();
        
        
        
    }
}
