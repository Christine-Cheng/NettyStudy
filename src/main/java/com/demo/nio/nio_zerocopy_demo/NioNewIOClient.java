package com.demo.nio.nio_zerocopy_demo;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * @Describe: 客户端
 * @Author Happy
 * @Create 2023/2/17-21:59
 **/
public class NioNewIOClient {
    
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost", 7001));
        String filename = "protoc-3.6.1-win32.zip";
        
        //得到一个文件channel
        FileChannel fileChannel = new FileInputStream(filename).getChannel();
        
        long startTime = System.currentTimeMillis();
        
        //在Linux下一个transferTo 方法就可以完成传输
        //在windows中,一次调用transferTo 只能传输8MB,就需要分段进行传输文件,而且要注意传输时的位置
        //传输时候的位置: ==> 考虑 文件大小/8MB 除不尽的话多传一次
        long transferCount = fileChannel.transferTo(0, fileChannel.size(), socketChannel);
        
        long endTime = System.currentTimeMillis();
        System.out.println("发送的总的字节数 =" + transferCount + " 耗时: " + (endTime - startTime));
        
        fileChannel.close();
    }
}
