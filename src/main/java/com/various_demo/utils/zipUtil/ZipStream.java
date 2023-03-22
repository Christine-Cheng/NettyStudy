package com.various_demo.utils.zipUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
//TODO 待验证
/**
 * @Describe: 文件压缩流实现文件压缩和解压缩
 * https://blog.csdn.net/qq_40424244/article/details/82902238
 * @Author Happy
 * @Create 2023/1/5-23:13
 **/
public class ZipStream {
    public static void main(String[] args) {
        //test demo
        File[] files = new File[3];
        files[0] = new File("E:\\waster\\Archiver1\\1.txt");
        files[1] = new File("E:\\waster\\Archiver1\\2.png");
        files[2] = new File("E:\\waster\\Archiver1\\3.txt");
        File zip = new File("E:\\waster\\Archiver2\\bsm.zip");
        toZip(files, zip);
        File path = new File("E:\\waster\\Archiver2");
        unZip(zip, path);
        
    }
    
    public static void toZip(File[] src,File tar) {
        try {
            FileOutputStream fos = new FileOutputStream(tar);
            ZipOutputStream zos = new ZipOutputStream(fos);
            for(int i=0; i<src.length; ++i) {
                String fileName = src[i].getName();//获得文件名
                FileInputStream fis = new FileInputStream(src[i]);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                int len = -1;
                byte[] buf = new byte[1024];
                while((len = fis.read(buf)) != -1) {
                    baos.write(buf, 0, len);
                }
                baos.close();
                fis.close();
                byte[] fileContent = baos.toByteArray();//获得文件内容
                ZipEntry zipEntry = new ZipEntry(fileName);//利用文件名创建条目
                zos.putNextEntry(zipEntry);//插入条目
                zos.write(fileContent);//写入条目内容
                zos.closeEntry();//关闭条目
            }
            zos.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void unZip(File zipFile, File path) {
        try {
            if(!path.exists()) {
                path.mkdirs();
            }
            FileInputStream fis = new FileInputStream(zipFile);
            ZipInputStream zis = new ZipInputStream(fis);
            ZipEntry zipEntry = null;
            while((zipEntry = zis.getNextEntry()) != null) {//获取条目
                String fileName = zipEntry.getName();//获取文件名
                File file = new File(path.getAbsolutePath() + "/./" + fileName);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                int len = -1;
                byte[] buf = new byte[1024];
                while((len = zis.read(buf)) != -1) {
                    baos.write(buf, 0, len);
                }
                baos.close();
                byte[] fileContent = baos.toByteArray();//获取条目内容（即文件内容）
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(fileContent);
                fos.close();
            }
            
            zis.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
