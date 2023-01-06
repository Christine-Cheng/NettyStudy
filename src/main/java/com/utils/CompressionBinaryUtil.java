package com.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

//TODO 待验证

/**
 * https://blog.csdn.net/lilongfei123321/article/details/84416807
 * <p>
 * util for compress/decompress data
 * Java压缩/解压缩二进制文件
 * 在Java中提供Deflater和Inflater工具类来压缩/解压缩数据。 这两个工具类采用zlib算法
 */
public final class CompressionBinaryUtil {
    
    /**
     * Compression level
     */
    private static enum Level {
        /**
         * Compression level for no compression.
         */
        NO_COMPRESSION(0),
        
        /**
         * Compression level for fastest compression.
         */
        BEST_SPEED(1),
        
        /**
         * Compression level for best compression.
         */
        BEST_COMPRESSION(9),
        
        /**
         * Default compression level.
         */
        DEFAULT_COMPRESSION(-1);
        
        private int level;
        
        Level(int level) {
            this.level = level;
        }
        
        public int getLevel() {
            return level;
        }
    }
    
    private static final int BUFFER_SIZE = 4 * 1024;
    
    public static byte[] compress(byte[] data, Level level) throws IOException {
        
        Deflater deflater = new Deflater();
        // set compression level
        deflater.setLevel(level.getLevel());
        deflater.setInput(data);
        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        
        deflater.finish();
        byte[] buffer = new byte[BUFFER_SIZE];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer); // returns the generated code... index
            outputStream.write(buffer, 0, count);
        }
        byte[] output = outputStream.toByteArray();
        outputStream.close();
        return output;
    }
    
    public static byte[] decompress(byte[] data) throws IOException, DataFormatException {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(
                data.length);
        byte[] buffer = new byte[BUFFER_SIZE];
        while (!inflater.finished()) {
            int count = inflater.inflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        byte[] output = outputStream.toByteArray();
        outputStream.close();
        return output;
    }
    
    
    public static void testCompress() throws Exception {
        
        BufferedInputStream in = new BufferedInputStream(new FileInputStream("E:\\myerp_error.log"));
        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
        
        byte[] temp = new byte[1024];
        int size = 0;
        while ((size = in.read(temp)) != -1) {
            out.write(temp, 0, size);
        }
        in.close();
        byte[] data = out.toByteArray();
        byte[] output = CompressionBinaryUtil.compress(data,
                CompressionBinaryUtil.Level.BEST_COMPRESSION);
        System.out.println("before : " + (data.length / 1024) + "k");
        System.out.println("after : " + (output.length / 1024) + "k");
        
        FileOutputStream fos = new FileOutputStream("E:\\myerp_error_compress.log");
        fos.write(output);
        out.close();
        fos.close();
    }
    
    public static void testDecompress() throws Exception {
        
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(
                "E:\\myerp_error_compress.log"));
        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
        byte[] temp = new byte[1024];
        int size = 0;
        while ((size = in.read(temp)) != -1) {
            out.write(temp, 0, size);
        }
        in.close();
        byte[] data = out.toByteArray();
        byte[] output = CompressionBinaryUtil.decompress(data);
        System.out.println("before : " + (data.length / 1024) + "k");
        System.out.println("after : " + (output.length / 1024) + "k");
        
        FileOutputStream fos = new FileOutputStream("E:\\myerp_error_decompress.log");
        fos.write(output);
        out.close();
        fos.close();
    }
    
    
    public static void main(String[] args) throws Exception {
//		testCompress();
        testDecompress();
    }
}
