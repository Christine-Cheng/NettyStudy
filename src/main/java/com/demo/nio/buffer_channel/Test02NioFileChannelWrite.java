package com.demo.nio.buffer_channel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 0)理解NIO的基本组件Selector,Channel和Buffer的关系
 *
 * 1) NIO 的通道类似于流,但有些区别如下:
 *  通道可以同时进行读写,而流只能读或者只能写
 *  通道可以实现异步读写数据
 *  通道可以从缓冲读数据,也可以写数据到缓冲:
 * 2) BIO 中的stream 是单向的,例如FileInputStream 对象只能进行读取数据的操作,而NIO 中的通道(Channel)
 * 是双向的,可以读操作,也可以写操作.
 * 3) Channel 在NIO 中是一个接口
 * public interface Channel extends Closeable{}
 * 4) 常用的Channel 类有: FileChannel,DatagramChannel,ServerSocketChannel 和SocketChannel .
 *  【ServerSocketChannel 类似ServerSocket , SocketChannel 类似Socket】
 */

/**
 * @Describe:
 * 1) 使用前面学习后的ByteBuffer(缓冲) 和FileChannel(通道)， 将"Hello, Siri" 写入到file01.txt 中
 * 2) 文件不存在就创建
 *
 * 利用通道缓冲的方式写入文件,将通道引入IO中
 * 理解FileChannel写,及Channel和Buffer的交互
 * @Author: HAPPY
 * @Date: 2022-10-28 15:20 星期五
 **/
public class Test02NioFileChannelWrite {
    public static void main(String[] args) {
        try {
            String str = "Hello, Siri";
            
            String filePath = "./staticFile/file01.txt";
            File file = new File(filePath);
            if (file.exists()) {
                Files.delete(Paths.get(filePath));
            } else {
                file.getParentFile().mkdir();
            }
            file.createNewFile();
    
            //NIO是对Java原生IO流的包装
            //故创建一个输出流--->Channel
            //通过debug可以知道,FileOutputStream是包含FileChannel的
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            
            //通过fileOutputStream获取对应的FileChannel
            //此fileChannel的真实类型是FileChannelImpl
            FileChannel fileChannel = fileOutputStream.getChannel();
            
            //创建一个缓冲区(字节缓冲Buffer用的最多)
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            
            //将str写入byteBuffer
            byteBuffer.put(str.getBytes());
            
            //将byteBuffer用flip转换读写
            byteBuffer.flip();//转换为读
            
            //将buffer内容通过file流写fileChannel
            fileChannel.write(byteBuffer);
            
            //关闭流
            fileOutputStream.close();
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
