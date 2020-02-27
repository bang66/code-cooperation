package com.zp.code.controller;

import com.zp.code.aop.WebLog;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangpeng
 * @since 1.0.0
 */
@RestController
@RequestMapping(value = "/api")
public class UserInfoController extends BaseController {

    @RequestMapping(value = "/v1/test", method = RequestMethod.GET)
    @WebLog
    public Object ok() {
//        throw new BizException(BizError.PARAM_ERROR);
        return success();
    }
}
