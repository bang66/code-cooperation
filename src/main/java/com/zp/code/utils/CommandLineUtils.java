package com.zp.code.utils;

import com.zp.code.model.CommentInfo;
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
     *
     * @param projectId
     * @param code
     * @return
     */
    public static boolean createFile(String projectId, String projectName, String code) {

        byte[] sourceByte = code.getBytes();
        if (null != sourceByte) {
            try {
                File file = new File("./ProSource/" + projectId + "/" + projectName + ".java");        //文件路径（路径+文件名）
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
            logger.info("[CreateFile] projectId:{}, projectName:{}", projectId, projectName);
        }
        return true;
    }


    /**
     * 创建 Dockerfile文件
     *
     * @param projectId
     * @param projectName
     * @return
     */
    private static boolean createDockerFile(String projectId, String projectName) {
        String content = "FROM java:7\n" +
                "\n" +
                "COPY . /usr/src/javaapp\n" +
                "WORKDIR /usr/src/javaapp/" + projectId + "\n" +
                "RUN javac " + projectName + ".java\n" +
                "CMD [\"java\", \"" + projectName + "\"]";
        byte[] sourceByte = content.getBytes();
        if (null != sourceByte) {
            try {
                File file = new File("./ProSource/Dockerfile");        //文件路径（路径+文件名）
                if (!file.exists()) {    //文件不存在则创建文件，先创建目录
                    File dir = new File(file.getParent());
                    dir.mkdirs();
                    file.createNewFile();
                }
                FileOutputStream outStream = new FileOutputStream(file);    //文件输出流用于将数据写入文件
                outStream.write(sourceByte);
                outStream.close();    //关闭文件输出流
            } catch (Exception e) {
                logger.error("[CreateDockerFile] err:{}", e);
                return false;
            }
            logger.info("[CreateDockerFile] projectId:{}, projectName:{}", projectId, projectName);
        }
        return true;
    }

    /**
     * 运行项目程序
     *
     * @param projectId
     * @param projectName
     */
    public static String runProject(String projectId, String projectName) {
        //创建dockerfile
        createDockerFile(projectId, projectName);

        // 运行
        return execCommand("sh docker.sh");
    }

    /**
     * 执行shell命令
     *
     * @param command
     */
    private static String execCommand(String command) {
        StringBuffer resBuffer = new StringBuffer();
        Process process;
        List<String> processList = new ArrayList<>();
        try {
            process = Runtime.getRuntime().exec(command);
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                processList.add(line);
            }
            input.close();
        } catch (IOException e) {
            logger.error("[CommandErr] err:{}", e);
        }

        int resIndex = 0;
        for (int i = 0; i < processList.size(); i++) {
            if (processList.get(i).startsWith("Successfully")) {
                resIndex = i;
            }
        }
        resIndex += 1;
        for (int i = resIndex; i < processList.size(); i++) {
            String line = processList.get(i);
            logger.info("[CommandResult] Midres:{}", line);
            resBuffer.append(line + "\n");
        }
        logger.info("[CommandResult] res:{}", resBuffer.toString());
        return resBuffer.toString();
    }

    /**
     * 读取项目代码
     * @param projectId
     * @param projectName
     */
    public static String readCode(String projectId, String projectName) {
        StringBuffer codeRes = new StringBuffer();
        try {
            BufferedReader in = new BufferedReader(new FileReader("./ProSource/" + projectId + "/" + projectName +".java"));
            String str;
            while ((str = in.readLine()) != null) {
                codeRes.append(str.toString() + "\n");
            }
        } catch (IOException e) {
            logger.error("[ReadCode] err:{}", e);
        }
        return codeRes.toString();
    }


    public static void main(String[] args) {
        CommandLineUtils.runProject("123e", "Test");
//        System.out.println(CommandLineUtils.readCode("123e", "Test"));
    }

}
