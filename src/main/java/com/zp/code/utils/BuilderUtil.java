package com.zp.code.utils;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * @author zhangpeng
 * @since 1.0.0
 */
public class BuilderUtil {

    public static String generateToken() {
        String token = RandomStringUtils.randomAlphanumeric(16);
        return token;
    }
}
