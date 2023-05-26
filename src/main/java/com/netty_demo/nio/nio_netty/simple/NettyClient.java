package com.netty_demo.nio.nio_netty.simple;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.HashMap;

/**
 * @Describe:
 * @Author Happy
 * @Create 2023/2/21-17:19
 **/
public class NettyClient {
    public static void main(String[] args) throws InterruptedException {
        //客户端需要一个事件循环组
        EventLoopGroup clientGroup = new NioEventLoopGroup();
    
        try {
            //创建客户端启动对象
            //注意客户端使用的不是ServerBootStrap,而是Bootstrap
            Bootstrap bootstrap = new Bootstrap();
        
            //设置相关参数
            bootstrap.group(clientGroup)//设置线程组
                    .channel(NioSocketChannel.class)//设置客户端通道实现类(反射)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new NettyClientHandler());//加入自己的处理器
                        }
                    });
            System.out.println("客户端 ok ...");
        
            //启动客户端,去连接服务器端
            //关于ChannelFuture要分析,涉及到netty的异步模型
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 6668).sync();
        
            //对关闭通道进行监听(当有通道关闭的消息或者事件的时候才会触发)
            channelFuture.channel().closeFuture().sync();
    
    
            HashMap<Integer, String> test = new HashMap<>();
        } finally {
            clientGroup.shutdownGracefully();
        }
    }
}
