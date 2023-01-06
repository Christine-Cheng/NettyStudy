package com.utils.zipUtil;


import lombok.extern.slf4j.Slf4j;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 已验证 zip与str通过base64互转第三版,通过剪贴板实现内外网的数据传输
 */

/**
 * 1.文件压缩为在.zip
 *
 * 2.文件流读取加密转换为字符串
 *
 * 3.写入剪贴板
 *
 * 4.字符串保存再.txt中
 *
 * 5.文件流读取.txt中的字符串,然后再解密字符串,重新生成.zip文件
 */

/**
 * @Describe: zip与str通过base64互转第三版,通过剪贴板实现内外网的数据传输
 * @Author Happy
 * @Create 2023/1/4-10:47
 **/
@Slf4j
public class ZipToStr2Clipboard {
    
    private static final int BUFFER_SIZE = 2 * 1024;
    
    public static void main(String[] args) {
        /**
         * TODO 使用时候,获取文件路径
         * zip生成加密字符串复制剪贴板
         */
        //String filePath = "staticFile/file02.zip";
        //zipConvertBase64StrToClipboard(filePath);
    
        
        /**
         * TODO 使用时候,获取文件路径
         * 从剪贴板黏贴字符串解密生成zip
         */
        String fileOutPath = "./staticFile2/api_lib.zip";
        base64StrConvertZipFromClipboard(fileOutPath);
    }
    
    
    /**
     * zip压缩文件转换为BASE64Encoder加密字符串
     */
    public static void zipConvertBase64StrToClipboard(String filePath) {
        //将获得数据流转换成byte,然后转成String
        String zipToBase64Str = base64StrFromZip(filePath);
        log.info("\r设置到剪贴板的base64ZipStr:\r");
        log.info("\r" + zipToBase64Str);
        //复制到剪贴板
        setClipboardString(zipToBase64Str);
    }
    
    /**
     * 加密字符串转换为zip
     */
    public static void base64StrConvertZipFromClipboard(String filePath) {
        String base64Str = getClipboardString();
        log.info("\r从剪贴板取出的base64ZipStr:\r");
        log.info("\r" + base64Str);
        if (base64Str != null && !"".equals(base64Str)) {
            base64StrToZip(base64Str, filePath);
        } else {
            System.out.println("从剪贴板取文件失败！！！");
        }
    }
    
    /**
     * 把文本设置到剪贴板(复制)
     */
    public static void setClipboardString(String str) {
        // 获取系统剪贴板
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        // 封装文本内容
        Transferable trans = new StringSelection(str);
        // 把文本内容设置到系统剪贴板
        clipboard.setContents(trans, null);
    }
    
    /**
     * 从剪贴板中获取文本(粘贴)
     */
    public static String getClipboardString() {
        // 获取系统剪贴板
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        // 获取剪贴板中的内容
        Transferable trans = clipboard.getContents(null);
        String clipboardStr = "";
        if (trans != null) {
            // 判断剪贴板中的内容是否支持文本
            if (trans.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                try {
                    // 获取剪贴板中的文本内容
                    clipboardStr = (String) trans.getTransferData(DataFlavor.stringFlavor);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return clipboardStr;
    }
    
    //由zip文件获取base64字符串
    public static String base64StrFromZip(String filePath) {
        File file = new File(filePath);
        FileInputStream fis = null;
        String base64Str = "";//file的转byte的字符串
        try {
            fis = new FileInputStream(file);
            BASE64Encoder encoder = new BASE64Encoder();
            byte[] temp = new byte[1024];
            int b;
            while ((b = fis.read(temp)) != -1) {
                base64Str += encoder.encode(temp);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    fis = null;
                }
            }
        }
        return base64Str;
    }
    
    //由base64转换为zip
    public static void base64StrToZip(String fileStr, String filePath) {
        try {
            //File newFile = new File("./staticFile2/" + "22233.zip");
            File newFile = new File(filePath);
            FileOutputStream fos = new FileOutputStream(newFile);
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] appByte = decoder.decodeBuffer(fileStr);
            fos.write(appByte);
            
            //outstream.write(fileStr.getBytes("ISO-8859-1"));
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    //public static String toBase64Zip(List<File> srcFiles) {
    //    log.info("开始压缩文件    [{}]", srcFiles);
    //    long start = System.currentTimeMillis();
    //    String base64toZip = "";
    //    ZipOutputStream zos = null;
    //    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    //    try {
    //        zos = new ZipOutputStream(baos);
    //
    //        for (File srcFile : srcFiles) {
    //            byte[] buf = new byte[BUFFER_SIZE];
    //            log.info("压缩的文件名称    [{}]   ", srcFile.getName() + "压缩的文件大小      [{}] ", srcFile.length());
    //            zos.putNextEntry(new ZipEntry(srcFile.getName()));
    //            int len;
    //            FileInputStream in = new FileInputStream(srcFile);
    //            while ((len = in.read(buf)) != -1) {
    //                zos.write(buf, 0, len);
    //            }
    //            zos.closeEntry();
    //            in.close();
    //        }
    //        long end = System.currentTimeMillis();
    //        log.info("压缩完成，耗时：    [{}] ms", (end - start));
    //    } catch (Exception e) {
    //        throw new RuntimeException("zip error from ZipToBase64", e);
    //    } finally {
    //        if (zos != null) {
    //            try {
    //                zos.close();
    //            } catch (IOException e) {
    //                e.printStackTrace();
    //            }
    //        }
    //    }
    //
    //    byte[] refereeFileBase64Bytes =Base64.getEncoder().encode(baos.toByteArray());
    //    try {
    //        base64toZip = new String(refereeFileBase64Bytes, "UTF-8");
    //    } catch (Exception e) {
    //        throw new RuntimeException("压缩流出现异常", e);
    //    }
    //    return base64toZip;
    //}
    //
    //
    //
    //
    //public static void base64ToFile(String base64, String path) {
    //    log.info("生成zip文件url==" + path);
    //    try {
    //        byte[] byteBase64 = Base64.getDecoder().decode(base64);
    //        ByteArrayInputStream byteArray = new ByteArrayInputStream(byteBase64);
    //        ZipInputStream zipInput = new ZipInputStream(byteArray);
    //        ZipEntry entry = zipInput.getNextEntry();
    //        File fileOut = null;
    //        File file = new File(path);
    //        String parent = file.getParent();
    //        BufferedOutputStream bos = null;
    //        while (entry != null && !entry.isDirectory()) {
    //            log.info("文件名称:    [{}]", entry.getName());
    //            fileOut = new File(parent, entry.getName());
    //            if (!fileOut.exists()) {
    //                (new File(fileOut.getParent())).mkdirs();
    //            }
    //            bos = new BufferedOutputStream(new FileOutputStream(fileOut));
    //            int offo = -1;
    //            byte[] buffer = new byte[BUFFER_SIZE];
    //            while ((offo = zipInput.read(buffer)) != -1) {
    //                bos.write(buffer, 0, offo);
    //            }
    //            bos.flush();
    //            // 获取 下一个文件
    //            entry = zipInput.getNextEntry();
    //        }
    //        bos.close();
    //        zipInput.close();
    //        byteArray.close();
    //    } catch (Exception e) {
    //        throw new RuntimeException("解压流出现异常", e);
    //    }
    //}
    
}
