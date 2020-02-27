package com.zp.code.handle;

import com.zp.code.DTO.Response;
import com.zp.code.common.BizError;
import com.zp.code.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zhangpeng
 * @since 1.0.0
 * 全局异常处理
 */
@ControllerAdvice
public class ExceptionHandle {

    private final static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Response handle(Exception e) {
        if (e instanceof BizException) {
            BizException bizException = (BizException) e;
            return ResultUtil.error(bizException.getCode(), bizException.getMsg());
        }else {
            logger.error("[Code-Cooperation]系统异常:{}", e);
            return ResultUtil.error(BizError.SYSTEM_ERROR.getCode(), BizError.SYSTEM_ERROR.getMsg());
        }
    }
}

