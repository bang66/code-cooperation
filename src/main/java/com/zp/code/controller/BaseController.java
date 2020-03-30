package com.zp.code.controller;

import com.zp.code.model.UserInfo;
import com.zp.code.service.CommonService;
import com.zp.code.service.ProjectInfoService;
import com.zp.code.service.UserInfoService;
import com.zp.code.utils.CommonParamUtils;
import com.zp.code.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class BaseController {
    private static Logger logger = LoggerFactory.getLogger(BaseController.class);

    @Autowired
    protected UserInfoService userInfoService;

    @Autowired
    protected CommonService commonService;

    @Autowired
    protected ProjectInfoService projectInfoService;

    protected UserInfo getUserInfoByToken() {
        String token = CommonParamUtils.get().getToken();
        UserInfo userInfo = userInfoService.checkToken(token);
        logger.info("[UserInfo] {} ", userInfo);
        return userInfo;
    }

    protected Object success() {
        return ResultUtil.success();
    }

    protected Object success(Object object) {
        return ResultUtil.success(object);
    }

}
