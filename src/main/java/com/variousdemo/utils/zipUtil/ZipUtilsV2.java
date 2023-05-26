package com.variousdemo.utils.zipUtil;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
//TODO 待验证

/**
 * 前言:
 * 之前实现过一版Java的zip
 * 链接：https://leanote.zzzmh.cn/blog/post/5cac404e16199b2e40000035
 *
 * 之前这一版针对的是文件或者文件夹，压缩成Zip压缩包。
 * 这个和服务器上在线生成压缩包的需求有点出入。
 * 服务器上的内容是存数据库的，如果要先产生一个临时文件，
 * 再压缩成Zip，就会产生很多临时文件，需要在写清理脚本，设计上不合理。
 * 在研究了Java8内置的zip包后，发现其实他本质就是一个OutputStream
 * 所以理论上其实可以直接把String作为文本的内容压到Zip中的文件里，文件的路径和文件名都可以按需求指定
 */

/**
 * 此版:https://zzzmh.cn/post/f7ae964e1a324bf383f6c5c521f7b550
 *
 * @author zmh
 * @date 2020-01-07
 * <p>
 * 文件流转压缩包工具类
 */
public class ZipUtilsV2 {
    /**
     * 缓存区大小
     */
    private static final int BUFFER_SIZE = 2 * 1024;
    
    private static final String GZIP_ENCODE_UTF_8 = "UTF-8";
    
    /**
     * 压缩核心方法
     */
    private static void compress(ZipOutputStream zos, String path, String name, String data) throws Exception {
        byte[] buf = new byte[BUFFER_SIZE];
        zos.putNextEntry(new ZipEntry(path + name));
        int len;
        //ByteArrayInputStream in = new ByteArrayInputStream(data.getBytes(GzipUtils.GZIP_ENCODE_UTF_8));
        ByteArrayInputStream in = new ByteArrayInputStream(data.getBytes(GZIP_ENCODE_UTF_8));
        while ((len = in.read(buf)) != -1) {
            zos.write(buf, 0, len);
        }
        zos.closeEntry();
        in.close();
    }
    
    
    /**
     * 文本直接转zip压缩成文件
     *
     * @param list -> map -> path 路径; name 文件名; data 具体文本内容;
     * @param out 传入输出流
     * @throws RuntimeException 抛出异常
     */
    public static void toZip(List<Map<String, String>> list, OutputStream out) throws RuntimeException {
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(out);
            for (Map<String, String> map : list) {
                String path = map.get("path");
                String name = map.get("name");
                String data = map.get("data");
                compress(zos, path, name, data);
            }
        } catch (Exception e) {
            throw new RuntimeException("zip error from ZipUtilsV1", e);
        } finally {
            if (zos != null) {
                try {
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public static void main(String[] args) throws Exception {
        List<Map<String, String>> list = new ArrayList<>();
        OutputStream outputStream = new FileOutputStream(new File("/home/zzzmh/Desktop/临时文件/test.zip"));
        Map<String,String> m1 = new HashMap(){{put("path","/f1/f2/f3/");put("name","1.txt");put("data","abcdefg");}};
        Map<String,String> m2 = new HashMap(){{put("path","/f1/f2/f3/f4/");put("name","2.txt");put("data","abcdefg");}};
        Map<String,String> m3 = new HashMap(){{put("path","");put("name","3.txt");put("data","abcdefg");}};
        
        list.add(m1);
        list.add(m2);
        list.add(m3);
        toZip(list, outputStream);
        if (outputStream != null) {
            outputStream.close();
        }
    }
}
