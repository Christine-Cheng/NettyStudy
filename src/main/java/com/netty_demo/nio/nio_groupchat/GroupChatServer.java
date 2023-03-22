package com.netty_demo.nio.nio_groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @Describe: NIO 网络编程应用实例-群聊系统
 * @Author Happy
 * @Create 2023/2/15-14:54
 **/
public class GroupChatServer {
    //定义属性
    private Selector selector;
    private ServerSocketChannel listenSocketChannel;
    private static final int PORT = 6667;
    
    //建立构造器;初始化
    public GroupChatServer() {
        try {
            //得到选择器selector
            selector = Selector.open();
            
            //创建ServerSocketChannel
            listenSocketChannel = ServerSocketChannel.open();
            
            //绑定端口
            listenSocketChannel.socket().bind(new InetSocketAddress(PORT));
            
            //设置为非阻塞
            listenSocketChannel.configureBlocking(false);
            
            //注册serverSocketChannel到selector上面
            listenSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //监听
    public void listen() {
        try {
            while (true) {
                int count = selector.select(2000);
                
                if (count > 0) {//有事件进行处理
                    Set<SelectionKey> sKeysSet = selector.selectedKeys();
                    Iterator<SelectionKey> sKeyIterator = sKeysSet.iterator();
                    
                    //遍历selectionKey集合
                    while (sKeyIterator.hasNext()) {
                        SelectionKey selectionKey = sKeyIterator.next();
                        
                        //监听accept
                        if (selectionKey.isAcceptable()) {
                            //建立连接
                            SocketChannel socketChannel = listenSocketChannel.accept();
                            //设置为非阻塞
                            socketChannel.configureBlocking(false);
                            //注册socketChannel到selector
                            socketChannel.register(selector, SelectionKey.OP_READ);
                            //提示
                            System.out.println(socketChannel.getRemoteAddress() + "上线了");
                        }
                        
                        //监听可读
                        if (selectionKey.isReadable()) {//通道发送read事件,即通道是可读状态
                            //处理读(调用方法)
                            readData(selectionKey);
                        }
                        
                        //手动从集合中移除当前SelectionKey,防止重复操作
                        sKeyIterator.remove();
                    }
                } else {
                    System.out.println("等待...");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        
        }
    }
    
    /**
     * 读取客户端消息
     */
    private void readData(SelectionKey key) {
        //取到关联的channel
        SocketChannel sc = null;
        
        try {
            //取得channel
            sc = (SocketChannel) key.channel();
            
            //创建buffer
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            
            int count = sc.read(byteBuffer);
            //根据count的值做处理
            if (count > 0) {
                //把缓存中的数据转换为字符串
                String msg = new String(byteBuffer.array());
                //输出该消息
                System.out.println("form 客户端:" + msg);
                
                /**
                 * 向其他客户端转发消息(需要排除自己),专门写一个方法来处理
                 */
                sendMsgToOtherClient(msg, sc);
            }
            
        } catch (IOException e) {
            try {
                System.out.println(sc.getRemoteAddress() + "离线了");
                
                //取消注册
                key.cancel();
                //关闭通道
                sc.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    /**
     * 转发消息给其他客户(通道)
     *
     * @param msg
     * @param selfChannel
     * @throws IOException
     */
    private void sendMsgToOtherClient(String msg, SocketChannel selfChannel) throws IOException {
        System.out.println("服务器转发消息...");
        System.out.println("服务器转发数据给客户端线程: " + Thread.currentThread().getName());
        //遍历,所有注册到selector上的SocketChannel,并排除selfChannel
        for (SelectionKey key : selector.keys()) {
            //通过key,取出对应的SocketChannel
            Channel targetChannel = key.channel();
            
            //排除自己
            if (targetChannel instanceof SocketChannel && targetChannel != selfChannel) {
                //转型
                SocketChannel socketChannel = (SocketChannel) targetChannel;
                //将msg,存储到buffer
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                //将buffer的数据写入到 通道
                socketChannel.write(buffer);
            }
        }
    }
    
    
    public static void main(String[] args) {
        //创建服务器对象
        GroupChatServer groupChatServer = new GroupChatServer();
        
        groupChatServer.listen();
    }
}
