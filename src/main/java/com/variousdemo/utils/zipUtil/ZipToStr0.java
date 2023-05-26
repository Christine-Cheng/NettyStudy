//package com.TestZip;
//
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.InputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.zip.ZipOutputStream;
//import javax.servlet.http.HttpServletResponse;
//import net.sf.json.JSONObject;
//import org.codehaus.jackson.map.ObjectMapper;
//import sun.misc.BASE64Decoder;
//import sun.misc.BASE64Encoder;
//import com.alipay.api.AlipayClient;
//import com.alipay.api.DefaultAlipayClient;
//import com.alipay.api.request.AlipayDataDataserviceBillDownloadurlQueryRequest;
//import com.alipay.api.response.AlipayDataDataserviceBillDownloadurlQueryResponse;
//import com.log.AppLogUtil;
//import com.service.ReceiverHandler;
//
/**
 * 已验证 此用于参考zip与str通过base64互转
 */
///**
// * @Describe: 此用于参考zip与str通过base64互转
// * https://blog.csdn.net/myfmyfmyfmyf/article/details/52517187
// * https://www.cnblogs.com/xzhuan/articles/zipbase64.html
// * @Author Happy
// * @Create 2023/1/5-22:51
// **/
//public class ZipToStr0 {
//    /**
//     * 获得对账单数据流（.zip）,并将.zip通过byte[]保存成string进行传送
//     * String获得后转成byte[],然后保存成.zip
//     */
//    public String sendZipByStr() {
//        try {
//            String aliGateWay = "https://openapi.alipay.com/gateway.do";//支付宝网关
//            String dateStr = "2016-09-09";//日期  以天为最低单位  2016-09-06
//            String appId = "***********";//appid
//            //商户私钥
//            String privateKey = "********************************************************";
//            String strType = "json";//数据格式
//            String chartType = "utf-8";//编码格式
//            //支付宝公钥
//            String aliPublicKey = "************************************************";
//            AlipayClient alipayClient = new DefaultAlipayClient(aliGateWay, appId, privateKey, strType, chartType, aliPublicKey);
//            AlipayDataDataserviceBillDownloadurlQueryRequest request = new AlipayDataDataserviceBillDownloadurlQueryRequest();
//            request.setBizContent("{" +
//                    "    \"bill_type\":\"trade\"," +
//                    "    \"bill_date\":\"" + dateStr + "\"" +
//                    "  }");
//            AlipayDataDataserviceBillDownloadurlQueryResponse response = alipayClient.execute(request);
//            //获得下载对账单地址
//            System.out.println(response.getBillDownloadUrl());
//            System.out.println(response.getCode());
//            System.out.println(response.getMsg());
//            if (response.isSuccess()) {
//                //调用成功，下载对账文件
//                String urlStr = response.getBillDownloadUrl();
//                try {
//                    //创建文件链接
////					URL url = new URL("https://zos.alipayobjects.com/rmsportal/sDdxhkwUKuHUDLe.png");
//                    URL url = new URL(urlStr);
//                    HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
//                    //设置链接参数
//                    httpUrlConnection.setConnectTimeout(5 * 1000);
//                    httpUrlConnection.setDoInput(true);//打开输入输出流
//                    httpUrlConnection.setDoOutput(true);
//                    httpUrlConnection.setUseCaches(false);
//                    httpUrlConnection.setRequestMethod("GET");
//                    httpUrlConnection.setRequestProperty("Charsert", "UTF-8");
//                    httpUrlConnection.setRequestProperty("Connection", "Keep-Alive");
//                    //建立链接
//                    httpUrlConnection.connect();
//                    //获得输入流，文件为zip格式，
//                    //支付宝提供
//                    //20886126836996110156_20160906.csv.zip内包含
//                    //20886126836996110156_20160906_业务明细.csv
//                    //20886126836996110156_20160906_业务明细(汇总).csv
//                    InputStream fis = httpUrlConnection.getInputStream();
//                    //从url中获得文件名downloadFileName=20886126836996110156_20160909.csv.zip
//                    //直接将流转换成字符串出现zip内最后一个文件异常，只能先保存到本地，然后进行转换
//                    String filename = getDownloadFileName(urlStr);
//                    File file = new File(filename + ".zip");
//                    FileOutputStream outstream = new FileOutputStream(file);
//                    //将获得数据流转换成byte，然后转成String，向内网传送
//                    byte[] temp = new byte[1024];
//                    int b;
//                    while ((b = fis.read(temp)) != -1) {
////				    	fileStr+=byte2hex(temp);
////				    	fileStr+=new String(temp,"ISO-8859-1");
//                        outstream.write(temp, 0, b);
//                    }
//                    outstream.flush();
//                    outstream.close();
//                    fis.close();
//                    //获取本地文件转换成字符换
//                    File file2 = new File(filename + ".zip");
//                    FileInputStream inputstream = new FileInputStream(file2);//本地文件的输入流
//                    String fileStr = "";//内网传送数据，file的转byte的字符串
//                    BASE64Encoder encoder = new BASE64Encoder();
//                    while ((b = inputstream.read(temp)) != -1) {
//                        fileStr += encoder.encode(temp);
//                    }
//                    inputstream.close();
//                    System.out.println("文件1:" + fileStr);
//                    //删除本地文件
//                    file2.delete();
//                    //测试保存文件
//                    File ftpfile = new File("G:/" + filename + "2222222222222.zip");
//                    FileOutputStream ftpOutstream = new FileOutputStream(ftpfile);
//                    BASE64Decoder decoder = new BASE64Decoder();
//                    byte[] appByte = decoder.decodeBuffer(fileStr);
//                    ftpOutstream.write(appByte);
//
////				    outstream.write(fileStr.getBytes("ISO-8859-1"));
//                    ftpOutstream.flush();
//                    ftpOutstream.close();
//                    System.out.println("ok");
//                    //成功向内网传送
//                    return "{\"code\": 1,\"result\": \"" + fileStr + "\"}";
//                } catch (Exception e) {
//                    AppLogUtil.getAppLoger().error("从支付宝获取对账单,根据对账单地址获取对账单异常：" + e.getMessage());
//                    e.printStackTrace();
//                    return createErrorMsg("fail").toString();
//                }
//            } else {
//                AppLogUtil.getAppLoger().error("从支付宝获取对账单,获取对账单地址失败");
//                return createErrorMsg("fail").toString();
//            }
//        } catch (Exception e) {
//            AppLogUtil.getAppLoger().error("从支付宝获取对账单,获取对账单地址失败：" + e.getMessage());
//            e.printStackTrace();
//            return createErrorMsg("fail").toString();
//        }
//    }
//
//
//    /**
//     * 生成错误信息
//     *
//     * @param msg
//     * @return
//     */
//    private JSONObject createErrorMsg(String msg) {
//        String error = "{\"code\": 1,\"result\": \"" + msg + "\"}";
//        return JSONObject.fromObject(error);
//
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
