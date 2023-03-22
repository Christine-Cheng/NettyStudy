package com.various_demo.utils.zipUtil;

import org.apache.commons.io.Charsets;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

//TODO 待验证

/**
 * https://blog.csdn.net/weixin_41608476/article/details/124754787
 */
public class StrToZip {
    /**
     * 生成zip文件
     * Title: saveToDescription
     * Description:
     *
     * @param Description
     * @param UID
     * @param shop
     * @param itemid
     * @return
     * @author Zhangjl
     */
    public static String saveToDescription(String Description, String UID, String shop, String itemid) {
        
        //本地地址
        String startpath = "./TestFile";
        //网络访问地址
        String ipPath = "http://localhost:8080/description";
        
        File f = null;
        String fileName = "";
        String path = startpath + File.separator + UID + File.separator + shop;
        path = path.replace("/", "_").replace(" ", "_");
        fileName = itemid;
        f = new File(path);
        if (!f.exists()) {
            if (!f.mkdirs()) {
                System.out.println("不能创建目录：" + path);
                return "";
            }
        }
    
        //原来的样子
        //File file = new File(path, fileName + ".zip");
        //try (ZipOutputStream zos = new ZipOutputStream(fileOps)) {
        //    zos.putNextEntry(new ZipEntry(fileName + ".txt"));
        //    zos.write(Description.getBytes(Charsets.UTF_8));
        //    zos.closeEntry();
        //    zos.flush();
        //
        //    String url = file.getAbsolutePath();
        //    url = url.replace(startpath, ipPath).replace("\\", "/");
        //    return url;
        //} catch (IOException e) {
        //    e.printStackTrace();
        //}
        
        
        File file = new File(path, fileName + ".zip");
        OutputStream fileOps = null;
        try {
            fileOps = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try (ZipOutputStream zos = new ZipOutputStream(fileOps)) {
            zos.putNextEntry(new ZipEntry(fileName + ".txt"));
            zos.write(Description.getBytes(Charsets.UTF_8));
            zos.closeEntry();
            zos.flush();
            
            String url = file.getAbsolutePath();
            url = url.replace(startpath, ipPath).replace("\\", "/");
            return url;
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return "";
        
    }
    
    public static boolean isNullOrEmpty(Object obj) {
        return obj == null || "".equals(obj.toString());
    }
    
    
    /**
     * 解压读取zip的内容
     * Title: readDescribeFromFile
     * Description:
     *
     * @param descriptionURL
     * @return
     * @author Zhangjl
     */
    public static String readDescribeFromFile(String descriptionURL) {
        
        if (!isNullOrEmpty(descriptionURL)) {
            String tempFile = "";
            
            if (descriptionURL.contains("向易"))
                descriptionURL = descriptionURL.replace("向易", "%E5%90%91%E6%98%93");
            // 读取描述文件的代码
            try {
                if (!isNullOrEmpty(descriptionURL)) {
                    URL url = new URL(descriptionURL);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(3 * 1000);
                    // 得到输入流
                    InputStream inputStream = conn.getInputStream();
                    ZipInputStream zipInputStream = new ZipInputStream(inputStream);
                    BufferedInputStream bs = new BufferedInputStream(zipInputStream);
                    java.util.zip.ZipEntry nextEntry;
                    nextEntry = zipInputStream.getNextEntry();
                    while (nextEntry != null) {
                        tempFile = nextEntry.getName();
                        File file = new File(tempFile);
                        // 如果是目录，创建目录
                        if (tempFile.endsWith("/")) {
                            file.mkdir();
                        } else {
                            // 文件则写入具体的路径中
                            FileOutputStream fileOutputStream = new FileOutputStream(file);
                            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
                            int n;
                            byte[] bytes = new byte[1024];
                            while ((n = zipInputStream.read(bytes)) != -1) {
                                bufferedOutputStream.write(bytes, 0, n);
                            }
                            // 关闭流
                            bufferedOutputStream.close();
                            fileOutputStream.close();
                        }
                        // 关闭当前布姆
                        zipInputStream.closeEntry();
                        // 读取下一个目录，作为循环条件
                        nextEntry = zipInputStream.getNextEntry();
                    }
                    String returnContent = readFile(tempFile);
                    deleteFile(tempFile);
                    return returnContent;
                    
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
        
    }
    
    public static String readFile(String fileName) {
        // custName = new String(custName.getBytes("ISO-8859-1"), "UTF-8");
        String fileContent = "";
        try {
            File f = new File(fileName);
            if (f.isFile() && f.exists()) {
                InputStreamReader read = new InputStreamReader(new FileInputStream(f), "UTF-8");
                BufferedReader reader = new BufferedReader(read);
                String line;
                while ((line = reader.readLine()) != null) {
                    fileContent += line;
                    fileContent += "\\n";
                }
                read.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileContent;
    }
    
    public static boolean deleteFile(String sPath) {
        Boolean flag = false;
        File file = new File(sPath);
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }
    
}
