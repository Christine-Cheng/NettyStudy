package com.utils.zipUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
//TODO 待验证
/**
 * https://www.cnblogs.com/qqqq122418812/p/11309104.html
 * https://blog.csdn.net/q15102780705/article/details/117293211
 *
 * <p>
 * Title: ZipToBase64.java<／p>
 * <p>
 * Description: 压缩 转 base64 网络传输<／p>
 *
 * @author: ken chen
 * @date: 2019年7月26日
 * @param: srcFiles 需要压缩的文件
 * @return Base64 压缩文件后进行base64编码
 * @version 1.0
 */
public class ZipToBase64 {
    
    private static final int BUFFER_SIZE = 2 * 1024;
    
    private static final Logger log = LoggerFactory.getLogger(ZipToBase64.class);
    
    public static String toBase64Zip(List<File> srcFiles) throws RuntimeException {
        log.info("开始压缩文件    [{}]", srcFiles);
        long start = System.currentTimeMillis();
        String base64toZip = "";
        ZipOutputStream zos = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            zos = new ZipOutputStream(baos);
            for (File srcFile : srcFiles) {
                byte[] buf = new byte[BUFFER_SIZE];
                log.info("压缩的文件名称    [{}]   ", srcFile.getName() + "压缩的文件大小      [{}] ", srcFile.length());
                zos.putNextEntry(new ZipEntry(srcFile.getName()));
                int len;
                FileInputStream in = new FileInputStream(srcFile);
                while ((len = in.read(buf)) != -1) {
                    zos.write(buf, 0, len);
                }
                zos.closeEntry();
                in.close();
            }
            long end = System.currentTimeMillis();
            log.info("压缩完成，耗时：    [{}] ms", (end - start));
        } catch (Exception e) {
            throw new RuntimeException("zip error from ZipToBase64", e);
        } finally {
            if (zos != null) {
                try {
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        byte[] refereeFileBase64Bytes =Base64.getEncoder().encode(baos.toByteArray());
        try {
            base64toZip = new String(refereeFileBase64Bytes, "UTF-8");
        } catch (Exception e) {
            throw new RuntimeException("压缩流出现异常", e);
        }
        return base64toZip;
    }
    
    /**
     * <p>
     * Title: ZipToBase64.java<／p>
     * <p>
     * Description: Base64 解密 zip 解压缩 <／p>
     *
     * @author ken chen
     * @date 2019年7月26日
     * @param path   解压文件夹路径 文件夹父文件可更改
     * @param base64 base64加密字符
     * @return Base64 压缩文件后进行base64编码
     * @version 1.0
     */
    public static void Base64ToFile(String base64, String path) throws RuntimeException {
        log.info("解压文件地址" + path);
        try {
            byte[] byteBase64 =Base64.getDecoder().decode(base64);
            ByteArrayInputStream byteArray = new ByteArrayInputStream(byteBase64);
            ZipInputStream zipInput = new ZipInputStream(byteArray);
            ZipEntry entry = zipInput.getNextEntry();
            File fout = null;
            File file = new File(path);
            String parent = file.getParent();
            while (entry != null && !entry.isDirectory()) {
                log.info("文件名称:    [{}]", entry.getName());
                fout = new File(parent, entry.getName());
                if (!fout.exists()) {
                    (new File(fout.getParent())).mkdirs();
                }
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(fout));
                int offo = -1;
                byte[] buffer = new byte[BUFFER_SIZE];
                while ((offo = zipInput.read(buffer)) != -1) {
                    bos.write(buffer, 0, offo);
                }
                bos.close();
                // 获取 下一个文件
                entry = zipInput.getNextEntry();
            }
            zipInput.close();
            byteArray.close();
        } catch (Exception e) {
            throw new RuntimeException("解压流出现异常", e);
        }
        
    }
    
    public static void main(String[] args) {
        // 文件压缩
        List<File> fileList = new ArrayList<File>();
        fileList.add(new File("C:\\Users\\cjy\\Desktop\\qqq\\担保合同附件.pdf"));
        fileList.add(new File("C:\\Users\\cjy\\Desktop\\qqq\\融资申请表.xls"));
        fileList.add(new File("C:\\Users\\cjy\\Desktop\\qqq\\其他附件.jpg"));
        String base64 = ZipToBase64.toBase64Zip(fileList);
        log.info("文件base 64 加密：" + base64);
        ZipToBase64.Base64ToFile(base64, "C:\\Users\\cjy\\Desktop\\qqq\\新建文件夹\\a");
        
    }
}
