package com.zp.code.controller;

import com.zp.code.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class BaseController {
    private static Logger logger = LoggerFactory.getLogger(BaseController.class);

    protected void getUserInfoByToken() {

    }

    protected Object success() {
        return ResultUtil.success();
    }

    protected Object success(Object object) {
        return ResultUtil.success(object);
    }

}
