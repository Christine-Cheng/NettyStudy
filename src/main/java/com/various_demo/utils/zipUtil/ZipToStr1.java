//package com.various_demo.utils.zipUtil;
//
//import sun.misc.BASE64Decoder;
//import sun.misc.BASE64Encoder;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
////TODO 已验证
///**
// * zip与str通过base64互转第二版
// */
//public class ZipToStr1 {
//
//    public static void main(String[] args) {
//        sendZipByStr();
//    }
//
//    /**
//     * 获得对账单数据流（.zip）,并将.zip通过byte[]保存成string进行传送
//     * String获得后转成byte[],然后保存成.zip
//     */
//    public static void sendZipByStr() {
//        try {
//            File zipFile = new File("staticFile/file02.zip");
//            FileInputStream zipFis = new FileInputStream(zipFile);
//
//            String filename = zipFile.getName().split(".zip")[0] + "A1" + ".zip";
//            FileOutputStream outstream = new FileOutputStream(filename);
//
//            //将获得数据流转换成byte，然后转成String，向内网传送
//            byte[] temp = new byte[1024];
//            int b;
//            while ((b = zipFis.read(temp)) != -1) {
//                //fileStr+=byte2hex(temp);
//                //fileStr+=new String(temp,"ISO-8859-1");
//                outstream.write(temp, 0, b);
//            }
//            outstream.flush();
//            outstream.close();
//            //fis.close();
//            zipFis.close();
//            //获取本地文件转换成字符换
//            //File file2 = new File(filename + ".zip");
//            File file2 = new File(filename);
//            FileInputStream inputstream = new FileInputStream(file2);//本地文件的输入流
//            String fileStr = "";//内网传送数据，file的转byte的字符串
//            BASE64Encoder encoder = new BASE64Encoder();
//            while ((b = inputstream.read(temp)) != -1) {
//                fileStr += encoder.encode(temp);
//            }
//            inputstream.close();
//            System.out.println("文件1:\r\n" + fileStr);
//            //删除本地文件
//            file2.delete();
//            //测试保存文件
//            //File ftpfile = new File("G:/" + filename + "2222222222222.zip");
//
//            File newFile = new File("./staticFile2/" + "22233.zip");
//            //FileOutputStream ftpOutstream = new FileOutputStream(ftpfile);
//            FileOutputStream ftpOutstream = new FileOutputStream(newFile);
//            BASE64Decoder decoder = new BASE64Decoder();
//            byte[] appByte = decoder.decodeBuffer(fileStr);
//            ftpOutstream.write(appByte);
//
//            //outstream.write(fileStr.getBytes("ISO-8859-1"));
//            ftpOutstream.flush();
//            ftpOutstream.close();
//            System.out.println("ok");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//
//        }
//    }
//
//    //获取文件名
//    private String getDownloadFileName(String urlStr) {
//        String tempStr = urlStr.substring(urlStr.indexOf("downloadFileName") + 17, urlStr.length());
//        tempStr = tempStr.substring(0, tempStr.indexOf(".zip"));
//        return tempStr;
//    }
//
//}
