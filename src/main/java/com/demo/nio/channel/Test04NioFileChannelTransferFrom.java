package com.demo.nio.channel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * @Describe: transferFrom从目标通道复制数据到当前通道
 * 1) 使用FileChannel(通道)和方法transferFrom ,完成文件的拷贝
 * 2) 拷贝一张图片
 *
 * Channel拷贝文件
 * transferFrom 复制图片
 * @Author: HAPPY
 * @Date: 2022-10-31 10:20 星期一
 **/
public class Test04NioFileChannelTransferFrom {
    public static void main(String[] args) {
        try {
            long timeMillis1 = System.currentTimeMillis();
            //文件路径
            String fileInPath = "staticFile/KongFuPanda.jpeg";
            String fileOutPath = "staticFile/KongFuPandaCopy2.jpeg";
            //创建文件流
            FileInputStream fileInputStream = new FileInputStream(fileInPath);
            FileOutputStream fileOutputStream = new FileOutputStream(fileOutPath);
            //获取各个流的channel
            FileChannel sourceCh = fileInputStream.getChannel();
            FileChannel targetCh = fileOutputStream.getChannel();
            //channel使用transferFrom从源channel复制到目标channel
            targetCh.transferFrom(sourceCh, 0, sourceCh.size());
    
            long timeMillis2 = System.currentTimeMillis();
            System.out.println(timeMillis2-timeMillis1);
            //关闭通道
            targetCh.close();
            sourceCh.close();
            //关闭流
            fileOutputStream.close();
            fileInputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
