package com.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 * @Describe:
 * @Author Happy
 * @Create 2023/2/15-20:28
 **/
public class GroupChatClient {
    //定义相关属性
    private final String HOST = "127.0.0.1";
    private final int PORT = 6667;
    private Selector selector;
    private SocketChannel socketChannel;
    private String username;
    
    //构造器
    //初始化
    public GroupChatClient() throws IOException {
        selector = Selector.open();
        //连接服务器
        socketChannel = socketChannel.open(new InetSocketAddress(HOST, PORT));
        //设置为非阻塞
        socketChannel.configureBlocking(false);
        //将channel 注册到selector
        socketChannel.register(selector, SelectionKey.OP_READ);
        //获取username
        username = socketChannel.getLocalAddress().toString().substring(1);
        System.out.println(username + ": is ok...");
    }
    
    //向服务器发送消息
    public void sendInfo(String info) {
        info = username + " 说: " + info;
        try {
            socketChannel.write(ByteBuffer.wrap(info.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    //读取服务器端回复的消息
    public void readInfo() {
        try {
            int readChannels = selector.select();//如果有别的工作做可以进行等待.
            if (readChannels > 0) {//有可用的通道.
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> selectionKeyIterator = selectionKeys.iterator();
                while (selectionKeyIterator.hasNext()) {
                    SelectionKey selectionKey = selectionKeyIterator.next();
                    if (selectionKey.isReadable()) {
                        //得到相关的通道
                        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                        //得到一个buffer
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        //读取
                        socketChannel.read(byteBuffer);
                        //把读到的缓冲区的数据转成
                        String msg = new String(byteBuffer.array());
                        System.out.println(msg.trim());
                    }
                    
                    //手动从集合中移除当前SelectionKey,防止重复操作
                    selectionKeyIterator.remove();
                }
            } else {
                //System.out.println("没有可以用的通道...");
                //没有通道的时候,不提示任何信息
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    public static void main(String[] args) throws IOException {
        //启动我们客户端
        GroupChatClient chatClient = new GroupChatClient();
        
        //启动一个线程,每隔3秒,读取从服务器发送数据
        new Thread() {
            public void run() {
                while (true) {
                    chatClient.readInfo();
                    try {
                        Thread.currentThread().sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
        
        //发送数据给服务端
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            chatClient.sendInfo(s);
        }
    }
}
