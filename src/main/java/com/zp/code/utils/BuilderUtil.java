package com.zp.code.utils;

import com.zp.code.common.GlobalConstant;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * @author zhangpeng
 * @since 1.0.0
 */
public class BuilderUtil {

    /**
     * 生成用户token
     * @return
     */
    public static String generateToken() {
        return RandomStringUtils.randomAlphanumeric(16);
    }

    /**
     * 生成验证码
     * @return
     */
    public static String generateCode() {
        return RandomStringUtils.random(6);
    }

    /**
     * 生成projecetId
     * @return
     */
    public static String generateProjectId() {
        return RandomStringUtils.randomAlphanumeric(8);
    }

    /**
     * 生成用户名字
     * @return
     */
    public static String generateUserName() {
        return GlobalConstant.PREFIX_USER_NAME + RandomStringUtils.randomAlphanumeric(4);
    }


}
