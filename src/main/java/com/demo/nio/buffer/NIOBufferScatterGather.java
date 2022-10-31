package com.demo.nio.buffer;


import java.io.IOException;
import java.nio.channels.ServerSocketChannel;

/**
 * @Describe: NIO 还支持通过多个Buffer (即Buffer 数组) 完成读写操作
 * 通过scattering()和 gathering 处理
 * Scattering: 将数据写入到buffer 时,可以采用buffer 数组,依次写入[分散]
 * Gathering: 从buffer 读取数据时,可以采用buffer 数组,依次读[聚合]
 * @Author: HAPPY
 * @Date: 2022-10-31 16:51 星期一
 **/
public class NIOBufferScatterGather {
    public static void main(String[] args) {
    
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            
            
            
            
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
