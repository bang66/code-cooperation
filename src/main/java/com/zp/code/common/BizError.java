package com.zp.code.common;

import com.zp.code.DTO.Response;

/**
 * @author zhangpeng
 * @since 1.0.0
 */
public class BizError {
    public static Response PARAM_ERROR = new Response(1000, "prams error");
    public static Response SYSTEM_ERROR = new Response(1001, "system error");
    public static Response ILLEGAL_REQUEST = new Response(1002, "illegal request");

}
