package com.zp.code.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangpeng
 * @since 1.0.0
 */
public class CommandLineUtils {

    private static final Logger logger = LoggerFactory.getLogger(CommandLineUtils.class);


    /**
     * 创建、提交 java文件
     * @param projectId
     * @param code
     * @return
     */
    public static boolean createFile(String projectId, String code) {

        byte[] sourceByte = code.getBytes();
        if (null != sourceByte) {
            try {
                File file = new File("./ProSource/" + projectId + ".java");        //文件路径（路径+文件名）
                if (!file.exists()) {    //文件不存在则创建文件，先创建目录
                    File dir = new File(file.getParent());
                    dir.mkdirs();
                    file.createNewFile();
                }
                FileOutputStream outStream = new FileOutputStream(file);    //文件输出流用于将数据写入文件
                outStream.write(sourceByte);
                outStream.close();    //关闭文件输出流
            } catch (Exception e) {
                logger.error("[CreateFile] err:{}", e);
                return false;
            }
            logger.info("[CreateFile] projectId:{}", projectId);
        }
        return true;
    }



    public static void main(String args[]) {
        String sourceString = "public class test{\n" +
                "    public static void main(String[] args) {\n" +
                "        System.out.println(\"aaa\");\n" +
                "    }\n" +
                "}";    //待写入字符串
        CommandLineUtils.createFile("123e", sourceString);
    }

}
