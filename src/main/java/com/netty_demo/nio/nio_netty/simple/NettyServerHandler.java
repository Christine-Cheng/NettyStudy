package com.netty_demo.nio.nio_netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @Describe:
 * 说明:
 * 1.定义一个handler,需要继承netty 规定好的某个HandlerAdapter(规范)
 * 2.这是我们定义一个Handler,才能称为一个Handler.
 * @Author Happy
 * @Create 2023/2/21-16:21
 **/
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    
    //读取数据的事件(这里可以读取客户端发送的消息)
    
    /**
     * 1.ChannelHandlerContext ctx:上下文对象,含有管道pipeline,通道channel,地址
     * 2.Object msg:就是客户端的数据 默认Object
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
    
        System.out.println("server ctx = "+ ctx);
        //将msg转成一个ByteBuf
        //ByteBuf 是netty 提供的,不是NIO的ByteBuffer
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("客户端发送的消息是: "+ byteBuf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端地址: ");
    }
    
    /**
     * 数据读取完毕
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
        
        //writeAndFlush 是 write + flush
        //将数据写入到缓存,并且刷新
        //一般讲,我们对这发送的数据进行编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello 客户端~喵", CharsetUtil.UTF_8));
    }
    
    //处理异常,一般是要关闭通道
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        
        ctx.close();
    }
}
