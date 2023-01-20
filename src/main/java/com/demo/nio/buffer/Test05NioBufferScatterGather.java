package com.demo.nio.buffer;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * @Describe: Buffer和Channel的注意事项和细节4: NIO 还支持通过多个Buffer (即Buffer 数组) 完成读写操作
 * 通过scattering()和 gathering 处理
 * Scattering: 将数据写入到buffer 时,可以采用buffer 数组,依次写入[分散]
 * Gathering: 从buffer 读取数据时,可以采用buffer 数组,依次读[聚合]
 *
 * 例子:
 * telnet 建立连接后,比较 send hello  和 send  helloabc 对position 和 limit的影响
 *
 * @Author: HAPPY
 * @Date: 2022-10-31 16:51 星期一
 **/
public class Test05NioBufferScatterGather {
    public static void main(String[] args) throws IOException {
        //使用ServerSocketChannel 和SocketChannel 网络
        
        //建立服务端SocketChannel并启动
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //建立Socket端口地址
        int port = 7777;
        InetSocketAddress inetSocketAddress = new InetSocketAddress(port);
        //绑定端口
        serverSocketChannel.socket().bind(inetSocketAddress);
        System.out.println("服务建立...;端口:" + port);
        
        //建立buffer[]数组
        ByteBuffer[] byteBufferArr = new ByteBuffer[2];
        byteBufferArr[0] = ByteBuffer.allocate(5);
        byteBufferArr[1] = ByteBuffer.allocate(3);
        
        //等待客户链接
        System.out.println("等待客户链接");
        SocketChannel socketChannel = serverSocketChannel.accept();
        System.out.println("客户已连接");
        
        int msgLength = 8;//假设从客户端接受8个字节
        while (true) {//循环读取
            int byteRead = 0;
            while (byteRead < msgLength) {
                long l = socketChannel.read(byteBufferArr);
                byteRead += l;//累计读取字节数
                System.out.println("字节数" + byteRead);
                //使用流打印当前buffer的position和limit
                Arrays.asList(byteBufferArr).stream().map(byteBuffer ->
                                "position=" + byteBuffer.position() + "," +
                                        "limit=" + byteBuffer.limit()).
                        forEach(System.out::println);
            }
            
            //将所有的buffer进行flip
            Arrays.asList(byteBufferArr).forEach(buffer -> buffer.flip());
            
            //将数据读出显示到客户端
            long byteWrite = 0;
            while (byteWrite < msgLength) {
                socketChannel.write(byteBufferArr);
                long l = socketChannel.write(byteBufferArr);
                byteWrite += l;
            }
            
            //将所有的buffer进行clear
            Arrays.asList(byteBufferArr).forEach(buffer -> buffer.clear());
            
            System.out.println("byteRead=" + byteRead + "  byteWrite=" + byteWrite + "  msgLength=" + msgLength);
        }
    }
}
