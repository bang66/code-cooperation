package com.zp.code.handle;

import com.zp.code.DTO.Result;
import com.zp.code.common.BizError;

/**
 * @author zhangpeng
 * @since 1.0.0
 */
public class BizException extends RuntimeException {

    private Integer code;

    private String msg;

    public BizException(Result result) {
        this.msg = result.getMsg();
        this.code = result.getCode();

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
