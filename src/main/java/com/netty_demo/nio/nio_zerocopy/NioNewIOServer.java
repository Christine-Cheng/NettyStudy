package com.netty_demo.nio.nio_zerocopy;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @Describe: 服务器端
 * @Author Happy
 * @Create 2023/2/17-21:59
 **/
public class NioNewIOServer {
    public static void main(String[] args) throws IOException {
        InetSocketAddress netAddress = new InetSocketAddress(7001);
        
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.bind(netAddress);
        
        ByteBuffer byteBuffer = ByteBuffer.allocate(4096);
        
        while (true) {
            SocketChannel socketChannel = serverSocketChannel.accept();
            int readCount = 0;
            try {
                while (-1 != readCount) {
                    readCount = socketChannel.read(byteBuffer);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            byteBuffer.rewind();//倒带 buffer的position = 0  mark 作废
        }
        
        
    }
}
