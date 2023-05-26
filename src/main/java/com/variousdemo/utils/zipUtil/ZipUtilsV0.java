package com.variousdemo.utils.zipUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
//TODO 待验证
/**
 * Java实现将文件或者文件夹压缩成zip
 *
 *  最近碰到个需要下载zip压缩包的需求，于是我在网上找了下别人写好的zip工具类。但找了好多篇博客，总是发现有bug。因此就自己来写了个工具类。
 *  这个工具类的功能为：
 * （1）可以压缩文件，也可以压缩文件夹
 * （2）同时支持压缩多级文件夹，工具内部做了递归处理
 * （3）碰到空的文件夹，也可以压缩
 * （4）可以选择是否保留原来的目录结构，如果不保留，所有文件跑压缩包根目录去了，且空文件夹直接舍弃。注意：如果不保留文件原来目录结构，在碰到文件名相同的文件时，会压缩失败。
 * （5）代码中提供了2个压缩文件的方法，一个的输入参数为文件夹路径，一个为文件列表，可根据实际需求选择方法。
 */

/**
 * https://www.cnblogs.com/zeng1994/p/7862288.html
 * ZipUtils
 * @author 	ZENG.XIAO.YAN
 * @date 	2017年11月19日 下午7:16:08
 * @version v1.0
 */
public class ZipUtilsV0 {
    
    private static final int  BUFFER_SIZE = 2 * 1024;
    
    /**
     * 压缩成ZIP 方法1
     * @param srcDir 压缩文件夹路径
     * @param out    压缩文件输出流
     * @param KeepDirStructure  是否保留原来的目录结构,true:保留目录结构;
     * 							false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
     * @throws RuntimeException 压缩失败会抛出运行时异常
     */
    public static void toZip(String srcDir, OutputStream out, boolean KeepDirStructure)
            throws RuntimeException{
        
        long start = System.currentTimeMillis();
        ZipOutputStream zos = null ;
        try {
            zos = new ZipOutputStream(out);
            File sourceFile = new File(srcDir);
            compress(sourceFile,zos,sourceFile.getName(),KeepDirStructure);
            long end = System.currentTimeMillis();
            System.out.println("压缩完成，耗时：" + (end - start) +" ms");
        } catch (Exception e) {
            throw new RuntimeException("zip error from ZipUtils",e);
        }finally{
            if(zos != null){
                try {
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
    }
    
    /**
     * 压缩成ZIP 方法2
     * @param srcFiles 需要压缩的文件列表
     * @param out 	        压缩文件输出流
     * @throws RuntimeException 压缩失败会抛出运行时异常
     */
    public static void toZip(List<File> srcFiles , OutputStream out)throws RuntimeException {
        long start = System.currentTimeMillis();
        ZipOutputStream zos = null ;
        try {
            zos = new ZipOutputStream(out);
            for (File srcFile : srcFiles) {
                byte[] buf = new byte[BUFFER_SIZE];
                zos.putNextEntry(new ZipEntry(srcFile.getName()));
                int len;
                FileInputStream in = new FileInputStream(srcFile);
                while ((len = in.read(buf)) != -1){
                    zos.write(buf, 0, len);
                }
                zos.closeEntry();
                in.close();
            }
            long end = System.currentTimeMillis();
            System.out.println("压缩完成，耗时：" + (end - start) +" ms");
        } catch (Exception e) {
            throw new RuntimeException("zip error from ZipUtils",e);
        }finally{
            if(zos != null){
                try {
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    
    /**
     * 递归压缩方法
     * @param sourceFile 源文件
     * @param zos		 zip输出流
     * @param name		 压缩后的名称
     * @param KeepDirStructure  是否保留原来的目录结构,true:保留目录结构;
     * 							false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
     * @throws Exception
     */
    private static void compress(File sourceFile, ZipOutputStream zos, String name,
                                 boolean KeepDirStructure) throws Exception{
        byte[] buf = new byte[BUFFER_SIZE];
        if(sourceFile.isFile()){
            // 向zip输出流中添加一个zip实体，构造器中name为zip实体的文件的名字
            zos.putNextEntry(new ZipEntry(name));
            // copy文件到zip输出流中
            int len;
            FileInputStream in = new FileInputStream(sourceFile);
            while ((len = in.read(buf)) != -1){
                zos.write(buf, 0, len);
            }
            // Complete the entry
            zos.closeEntry();
            in.close();
        } else {
            File[] listFiles = sourceFile.listFiles();
            if(listFiles == null || listFiles.length == 0){
                // 需要保留原来的文件结构时,需要对空文件夹进行处理
                if(KeepDirStructure){
                    // 空文件夹的处理
                    zos.putNextEntry(new ZipEntry(name + "/"));
                    // 没有文件，不需要文件的copy
                    zos.closeEntry();
                }
                
            }else {
                for (File file : listFiles) {
                    // 判断是否需要保留原来的文件结构
                    if (KeepDirStructure) {
                        // 注意：file.getName()前面需要带上父文件夹的名字加一斜杠,
                        // 不然最后压缩包中就不能保留原来的文件结构,即：所有文件都跑到压缩包根目录下了
                        compress(file, zos, name + "/" + file.getName(),KeepDirStructure);
                    } else {
                        compress(file, zos, file.getName(),KeepDirStructure);
                    }
                    
                }
            }
        }
    }
    
    public static void main(String[] args) throws Exception {
        /** 测试压缩方法1  */
        FileOutputStream fos1 = new FileOutputStream(new File("c:/mytest01.zip"));
        ZipUtilsV0.toZip("D:/log", fos1,true);
        
        /** 测试压缩方法2  */
        List<File> fileList = new ArrayList<>();
        fileList.add(new File("D:/Java/jdk1.7.0_45_64bit/bin/jar.exe"));
        fileList.add(new File("D:/Java/jdk1.7.0_45_64bit/bin/java.exe"));
        FileOutputStream fos2 = new FileOutputStream(new File("c:/mytest02.zip"));
        ZipUtilsV0.toZip(fileList, fos2);
    }


    /**
     * 二、注意事项
     *     写该工具类时，有些注意事项说一下：
     *         （1）支持选择是否保留原来的文件目录结构，如果不保留，那么空文件夹直接不用处理。
     *         （1）碰到空文件夹时，如果需要保留目录结构，则直接添加个ZipEntry就可以了，不过就是这个entry的名字后面需要带上一斜杠（/）表示这个是目录。
     *         （2）递归时，不需要把zip输出流关闭，zip输出流的关闭应该是在调用完递归方法后面关闭
     *         （3）递归时，如果是个文件夹且需要保留目录结构，那么在调用方法压缩他的子文件时，需要把文件夹的名字加一斜杠给添加到子文件名字前面，这样压缩后才有多级目录。
     *
     * 三、如何在javaWeb项目中使用该工具类
     *     这个工具类在web项目中的使用场景就是多文件下载，我就简单说个下载多个excel表格的案例吧。
     *     代码中的步骤为：
     *         （1）创建一个临时文件夹
     *         （2）将要下载的文件生成至该临时文件夹内
     *         （3）当所有文件生成完后，获取HttpServletResponse获取设置下载的header
     *         （4）调用工具类的方法，传入上面生成的临时文件夹路径及response获取的输出流；这样就下载出来zip包了
     *         （5）递归删除掉上面生成的临时文件夹和文件
     *
     *     下面为一个示例代码的代码片段，不是完整代码，简单看一下代码中的步骤
     */
    //	if(userList.size() > 0){
    //		/** 下面为下载zip压缩包相关流程 */
    //		HttpServletRequest request = ServletActionContext.getRequest();
    //		FileWriter writer;
    //		/** 1.创建临时文件夹  */
    //		String rootPath = request.getSession().getServletContext().getRealPath("/");
    //		File temDir = new File(rootPath + "/" + UUID.randomUUID().toString().replaceAll("-", ""));
    //		if(!temDir.exists()){
    //			temDir.mkdirs();
    //		}
    //
    //		/** 2.生成需要下载的文件，存放在临时文件夹内 */
    //		// 这里我们直接来10个内容相同的文件为例，但这个10个文件名不可以相同
    //		for (int i = 0; i < 10; i++) {
    //			dataMap.put("userList", userList);
    //			Map<String, String> endMap = new HashMap<>();
    //			endMap.put("user", "老王");
    //			endMap.put("time", "2017-10-10 10:50:55");
    //			dataMap.put("endMap", endMap);
    //			Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);
    //			cfg.setServletContextForTemplateLoading(ServletActionContext.getServletContext(), "/ftl");
    //			Template template = cfg.getTemplate("exportExcel.ftl");
    //			writer = new FileWriter(temDir.getPath()+"/excel"+ i +".xls");
    //			template.process(dataMap, writer);
    //			writer.flush();
    //			writer.close();
    //		}
    //
    //		/** 3.设置response的header */
    //		HttpServletResponse response = ServletActionContext.getResponse();
    //		response.setContentType("application/zip");
    //		response.setHeader("Content-Disposition", "attachment; filename=excel.zip");
    //
    //		/** 4.调用工具类，下载zip压缩包 */
    //		// 这里我们不需要保留目录结构
    //		ZipUtils.toZip(temDir.getPath(), response.getOutputStream(),false);
    //
    //		/** 5.删除临时文件和文件夹 */
    //		// 这里我没写递归，直接就这样删除了
    //		File[] listFiles = temDir.listFiles();
    //		for (int i = 0; i < listFiles.length; i++) {
    //			listFiles[i].delete();
    //		}
    //		temDir.delete();
    //	}
    
    //public void zipWeb(){
    //    if(userList.size() > 0){
    //        /** 下面为下载zip压缩包相关流程 */
    //        HttpServletRequest request = ServletActionContext.getRequest();
    //        FileWriter writer;
    //        /** 1.创建临时文件夹  */
    //        String rootPath = request.getSession().getServletContext().getRealPath("/");
    //        File temDir = new File(rootPath + "/" + UUID.randomUUID().toString().replaceAll("-", ""));
    //        if(!temDir.exists()){
    //            temDir.mkdirs();
    //        }
    //
    //        /** 2.生成需要下载的文件，存放在临时文件夹内 */
    //        // 这里我们直接来10个内容相同的文件为例，但这个10个文件名不可以相同
    //        for (int i = 0; i < 10; i++) {
    //            dataMap.put("userList", userList);
    //            Map<String, String> endMap = new HashMap<>();
    //            endMap.put("user", "老王");
    //            endMap.put("time", "2017-10-10 10:50:55");
    //            dataMap.put("endMap", endMap);
    //            Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);
    //            cfg.setServletContextForTemplateLoading(ServletActionContext.getServletContext(), "/ftl");
    //            Template template = cfg.getTemplate("exportExcel.ftl");
    //            writer = new FileWriter(temDir.getPath()+"/excel"+ i +".xls");
    //            template.process(dataMap, writer);
    //            writer.flush();
    //            writer.close();
    //        }
    //
    //        /** 3.设置response的header */
    //        HttpServletResponse response = ServletActionContext.getResponse();
    //        response.setContentType("application/zip");
    //        response.setHeader("Content-Disposition", "attachment; filename=excel.zip");
    //
    //        /** 4.调用工具类，下载zip压缩包 */
    //        // 这里我们不需要保留目录结构
    //        ZipUtils.toZip(temDir.getPath(), response.getOutputStream(),false);
    //
    //        /** 5.删除临时文件和文件夹 */
    //        // 这里我没写递归，直接就这样删除了
    //        File[] listFiles = temDir.listFiles();
    //        for (int i = 0; i < listFiles.length; i++) {
    //            listFiles[i].delete();
    //        }
    //        temDir.delete();
    //    }
    //}
    
}

