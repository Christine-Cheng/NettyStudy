package com.netty_demo.nio.nio_netty.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Describe:
 * @Author Happy
 * @Create 2023/2/21-15:29
 **/
public class NettyServer {
    public static void main(String[] args) throws InterruptedException {
        
        
        //创建BossGroup 和 WorkerGruop
        /**
         * 说明:
         * 1.创建俩个线程组boosGroup和workerGroup
         * 2.bossGroup只是处理连接请求,workerGroup完成真正的客户端业务处理
         * 3.俩个线程组合都是无限循环
         */
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            //创建服务器端的启动对象,配置参数
            ServerBootstrap bootstrap = new ServerBootstrap();
            
            //用链式编程配置参数
            bootstrap.group(bossGroup, workerGroup)//设置俩个线程组
                    .channel(NioServerSocketChannel.class)//使用NioSocketChannel作为服务器的通实现
                    .option(ChannelOption.SO_BACKLOG, 128)//设置线程队列得到连接个数
                    .childOption(ChannelOption.SO_KEEPALIVE, true)//设置保持活动连接状态
                    .childHandler(new ChannelInitializer<SocketChannel>() {//创建一个通道测试对象(匿名对象)
                        //给pipeLine设置处理器
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new NettyServerHandler());//将handler加入到管道pipeline()
                        }
                    });//给workerGroup的EventLoop对应的管道设置处理器
            System.out.println("...服务器 is ready...");
            
            //绑定一个端口并且同步,生成一个ChannelFuture对象
            //启动服务器(并绑定端口)
            ChannelFuture channelFuture = bootstrap.bind(6668).sync();
            
            //对关闭通道进行监听(当有通道关闭的消息或者事件的时候才会触发)
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    
        if (bossGroup.equals(workerGroup)) {
        
        }
        
    }
}
