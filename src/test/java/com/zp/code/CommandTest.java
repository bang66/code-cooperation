package com.zp.code;

import com.zp.code.utils.CommandLineUtils;
import org.junit.jupiter.api.Test;

/**
 * @author zhangpeng
 * @since 1.0.0
 */
public class CommandTest extends CodeCooperationApplicationTests {

    @Test
    public void TestCreateFile() {
        String sourceString = "public class test{\n" +
                "    public static void main(String[] args) {\n" +
                "        System.out.println(\"hello world\");\n" +
                "    }\n" +
                "}";    //待写入字符串
        CommandLineUtils.createFile("23ea", "Test", sourceString);
    }

}
