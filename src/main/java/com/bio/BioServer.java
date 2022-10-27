package com.bio;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Describe:
 * @Author: HAPPY
 * @Date: 2022-10-27 11:29 星期四
 **/
public class BioServer {
    /**
     * 实验步骤
     * 1.terminal窗口进行发送  telnet 127.0.0.1 6666 链接服务
     * 2.CTRL+]进入文字输入
     * 3.发送指令 send + "输入的文字"
     * 4.观察打印的线程,可以证明一个客户链接一个线程.
     */
    public static void main(String[] args) throws Exception {
        //利用线程池机制
        //思路
        //1.创建线程池
        //2.当有客户机链接就创建一个线程
        
        //创建线程池
        ExecutorService newCacheThreadPool = Executors.newCachedThreadPool();
        //创建一个服务socket 设置port:6666
        ServerSocket serverSocket = new ServerSocket(6666);
        System.out.println("服务器启动QWQ");
        while (true) {
            //监听等待客户端链接
            final Socket socket = serverSocket.accept();
            //一个客户端链接跑一个线程,与他通讯
            newCacheThreadPool.execute(new Runnable() {
                @Override
                public void run() { //重写方法处理client的链接
                    //System.out.println("线程 id:" + Thread.currentThread().getId() + "线程名称 name:" + Thread.currentThread().getName());
                    clientHandler(socket);
                    
                }
            });
        }
        
    }
    
    //处理客户端链接
    public static void clientHandler(Socket socket) {
        try {
            System.out.println("线程 id:" + Thread.currentThread().getId() + "线程名称 name:" + Thread.currentThread().getName());
            
            //通过socket接受客户的输入流
            InputStream inputStream = socket.getInputStream();
            //缓存
            byte[] bytes = new byte[1024];
            //循环读取数据
            while (true) {
                System.out.println("线程 id:" + Thread.currentThread().getId() + "线程名称 name:" + Thread.currentThread().getName());
    
                int read = inputStream.read(bytes);
                if (read > -1) {
                    //client输入的数据
                    System.out.println(new String(bytes, 0, read));
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("关闭client链接");
            try {
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
    }
    
}
