package com.zp.code;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CodeCooperationApplication {

    private static Logger logger = LoggerFactory.getLogger(CodeCooperationApplication.class);


    public static void main(String[] args) throws Exception {
        SpringApplication.run(CodeCooperationApplication.class, args);
//		logger.info("[Code-Cooperation] Server Start!");
//		String eml = "13739926022@sina.cn";
//		MailUtil.sendMail(eml, "330621");
    }

}
