package com.demo.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Describe:
 * FileChannel + Buffer 进行拷贝文件
 * file1--->Channel01--->Buffer--->Channel02--->file2
 *
 * @Author: HAPPY
 * @Date: 2022-10-28 19:14 星期五
 **/
public class NioFileChannelReadWrite {
    public static void main(String[] args) {
        try {
            String filePath01 = "staticFile/file01.txt";
            String filePath02 = "staticFile/file02.txt";
            
            FileInputStream fileInputStream = new FileInputStream(filePath01);
            FileChannel fileChannelIn = fileInputStream.getChannel();
    
            FileOutputStream fileOutputStream = new FileOutputStream(filePath02);
            FileChannel fileChannelOut = fileOutputStream.getChannel();
    
            ByteBuffer byteBuffer = ByteBuffer.allocate(512);
            while (true) {//循环读取
                //注意:buffer中只有512字节所以要clear()
                /*
                public Buffer clear() {
                    position = 0;
                    limit = capacity;
                    mark = -1;
                    return this;
                }
                */
                byteBuffer.clear();
                
                int read = fileChannelIn.read(byteBuffer);//channel中数读到buffer
                
                /*
                不进行clear()的后果是:始终在读hb从0-file.length()位置的数据(最多是读取hb从0-511位置的数据)
                而buffer中的数据是不会被清除的,既是hb从0-511位置的数据不会被覆盖
                所以文件中会重复写入hb从0-511位置的数据
                */
                //System.out.println("测试不写buffer.clear()时候问题  readSize:" + read);
                System.out.println("测试写buffer.clear()时  readSize:" + read);
                
                if (read == -1) {//读完数据就退出
                    break;
                }
                
                //将buffer的数据写入fileChannelOut--->file02.txt
                byteBuffer.flip();//反转buffer为写
                //写入输出流
                fileChannelOut.write(byteBuffer);
            }
            fileOutputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
