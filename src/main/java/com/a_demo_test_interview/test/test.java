package com.a_demo_test_interview.test;

import com.demo.nio.buffer_channel.Test06NioBufferPutGetDataType;

/**
 * @Describe:
 * @Author: HAPPY
 * @Date: 2022-11-03 9:51 星期四
 **/
public class test {
    public static void main(String[] args) {
        Test06NioBufferPutGetDataType nioBufferPutGetDataType = new Test06NioBufferPutGetDataType();
    
        Class<? extends Test06NioBufferPutGetDataType> aClass = nioBufferPutGetDataType.getClass();
        ClassLoader classLoader = nioBufferPutGetDataType.getClass().getClassLoader();
        String simpleName = nioBufferPutGetDataType.getClass().getSimpleName();
        System.out.println(simpleName);
    }
}
