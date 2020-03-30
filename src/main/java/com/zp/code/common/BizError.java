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
    public static Response DATA_MISS = new Response(1003, "data missing");


    //业务错误
    public static Response ACCOUNT_ERROR = new Response(2000, "account error");
    public static Response ACCOUNT_NOT_REGIST = new Response(2001, "account not regist");
    public static Response SEND_ERROR = new Response(2002, "mail send error");
    public static Response NOT_SEND_CODE = new Response(2003, "this mailAddress not recive code");
    public static Response CODE_ERROR = new Response(2004, "this code is error");


}
