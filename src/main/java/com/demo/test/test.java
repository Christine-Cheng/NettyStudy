package com.demo.test;

import com.demo.nio.buffer.Test02NioBufferPutGetDataType;

/**
 * @Describe:
 * @Author: HAPPY
 * @Date: 2022-11-03 9:51 星期四
 **/
public class test {
    public static void main(String[] args) {
        Test02NioBufferPutGetDataType nioBufferPutGetDataType = new Test02NioBufferPutGetDataType();
    
        Class<? extends Test02NioBufferPutGetDataType> aClass = nioBufferPutGetDataType.getClass();
        ClassLoader classLoader = nioBufferPutGetDataType.getClass().getClassLoader();
        String simpleName = nioBufferPutGetDataType.getClass().getSimpleName();
        System.out.println(simpleName);
    }
}
