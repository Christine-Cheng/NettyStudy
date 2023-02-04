package com.demo.nio.nio_server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @Describe: 一、目的：
 * 1) 编写一个 NIO 入门案例，实现服务器端和客户端之间的数据简单通讯（非阻塞）
 * 2) 目的：理解NIO非阻塞网络编程机制
 * <p>
 * 二、理解NIO非阻塞网络的 Selector、SelectionKey、ServerSocketChannel和SocketChannel之间的关系
 * 1. 当客户端连接时，会通过ServerSocketChannel 得到 SocketChannel
 * 2. Selector 进行监听 select 方法, 返回有事件发生的通道的个数.
 * 3. 将socketChannel注册到Selector上,register(Selector sel, int ops), 一个selector上可以注册多个SocketChannel
 * 4. 注册后返回一个 SelectionKey, 会和该Selector 关联(集合)
 * 5. 进一步得到各个 SelectionKey (有事件发生)
 * 6. 在通过 SelectionKey 反向获取SocketChannel , 方法 channel()
 * 7. 可以通过 得到的 channel , 完成业务处
 * @Author Happy
 * @Create 2023/1/20-23:42
 **/
public class NioServer {
    public static void main(String[] args) throws IOException {
        //创建ServerSocketChannel -> ServerSocket
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        
        /**
         * Selector的真正实现类是WindowsSelectorImpl
         */
        //创建一个Selector对象
        Selector selector = Selector.open();
        
        //绑定一个端口,并在服务器端监听
        int port = 6666;
        InetSocketAddress inetSocketAddress = new InetSocketAddress(port);
        serverSocketChannel.socket().bind(inetSocketAddress);
        
        //设置为非阻塞
        serverSocketChannel.configureBlocking(false);
        
        //把serverSocketChannel注册到selector中,监听事件为OP_ACCEPT
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);//需要把服务端的channel注册到selector中.
        
        //循环获取客户端连接
        while (true) {
            //等待1秒钟,如果没有事件发生则返回0.(当前serverSocketChannel没有事件发生)
            if (selector.select(1000) == 0) {//没有事件发生
                System.out.println("服务器等待了1秒~,无连接...");
                continue;
            }
            
            //如果selector.select(1000)返回值>0,则获取相关的SelectionKey的集合
            //1.如果返回的>0,表示已经获取到监听的事件
            //2. selector.selectedKeys() 返回关注事件的集合
            //通过selectionKeys方向获取通道.
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            
            //遍历Set<SelectionKey>,使用迭代器遍历
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
            
            while (keyIterator.hasNext()) {
                //获取到SelectionKey
                SelectionKey key = keyIterator.next();
    
                /**
                 * 客户端不管怎样骚操作,都要经历这一步注册,与服务器建立连接
                 */
                //根据key,对应的通道发生的事件做相应处理
                if (key.isAcceptable()) {//若事件是OP_ACCEPT,有客户端连接服务器
                    //对当前客户端生成一个新的SocketChannel
                    SocketChannel socketChannel = serverSocketChannel.accept();//serverSocketChannel.accept()是阻塞的,又当前是SelectionKey.isAcceptable()连接成功要处理业务,所以是否阻塞已经不重要了
    
                    System.out.println("客户端连接成功~~~;生成了一个SocketChannel: " + socketChannel.hashCode());
                    
                    //将SocketChannel设置为非阻塞
                    socketChannel.configureBlocking(false);
                    
                    //将当前生成的socketChannel注册到selector,当前监听事件是OP_READ,同时给SocketChannel关联一个Buffer
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }
    
                /**
                 * 建立完成连接进行数据交互
                 */
                if (key.isReadable()) {//发生事件OP_READ
                    //通过key方向获取到对应channel
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    //获取到该channel关联的buffer
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    socketChannel.read(buffer);
                    System.out.println("from 客户端:" + new String(buffer.array()));
                }
                
                //手动从集合中移除当前SelectionKey,防止重复操作
                keyIterator.remove();
            }
            
        }
    }
}
