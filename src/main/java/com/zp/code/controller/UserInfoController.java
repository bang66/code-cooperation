package com.zp.code.controller;

import com.zp.code.aop.WebLog;
import com.zp.code.model.UserInfo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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


    @RequestMapping(value = "/v1/login", method = RequestMethod.POST)
    @WebLog
    public Object login(@RequestParam String emlAddr,
                        @RequestParam String passwd) {
        UserInfo userInfo = userInfoService.login(emlAddr, passwd);
        return success(userInfo);
    }

    @RequestMapping(value = "/v1/regist", method = RequestMethod.POST)
    @WebLog
    public Object regist(@RequestParam String emlAddr,
                         @RequestParam String passwd,
                         @RequestParam String code) {
        UserInfo userInfo = userInfoService.regist(emlAddr, passwd, code);
        return success(userInfo);
    }

    @RequestMapping(value = "/v1/send/code", method = RequestMethod.GET)
    @WebLog
    public Object regist(@RequestParam String emlAddr) {
        commonService.sendCode(emlAddr);
        return success();
    }

    @RequestMapping(value = "/v1/join/project", method = RequestMethod.GET)
    @WebLog
    public Object getJoinProject() {
        UserInfo userInfo = getUserInfoByToken();
        userInfoService.joinProject(userInfo);
        return success();
    }
}
