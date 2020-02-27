package com.zp.code.utils;

import com.zp.code.DTO.Response;

/**
 * @author zhangpeng
 * @since 1.0.0
 */
public class ResultUtil {
    public static Response success(Object object) {
        Response response = new Response();
        response.setCode(0);
        response.setMsg("success");
        response.setData(object);
        return response;
    }

    public static Response success() {
        return success(null);
    }

    public static Response error(int code, String msg) {
        Response response = new Response();
        response.setCode(code);
        response.setMsg(msg);
        return response;
    }

}
