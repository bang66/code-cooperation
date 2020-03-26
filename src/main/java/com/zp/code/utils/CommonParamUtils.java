package com.zp.code.utils;

import com.zp.code.DTO.CommonParams;

/**
 * @author zhangpeng
 * @since 1.0.0
 */
public class CommonParamUtils {

    private static ThreadLocal<CommonParams> commonParamsThreadLocal = new ThreadLocal<>();

    public static CommonParams get() {
        return commonParamsThreadLocal.get();
    }

    public static void set(CommonParams commonParams) {
        commonParamsThreadLocal.set(commonParams);
    }

    public static void remove() {
        commonParamsThreadLocal.remove();
    }

}
