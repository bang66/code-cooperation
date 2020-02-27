package com.zp.code.handle;

import com.zp.code.DTO.Response;

/**
 * @author zhangpeng
 * @since 1.0.0
 */
public class BizException extends RuntimeException {

    private Integer code;

    private String msg;

    public BizException(Response response) {
        this.msg = response.getMsg();
        this.code = response.getCode();

    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
