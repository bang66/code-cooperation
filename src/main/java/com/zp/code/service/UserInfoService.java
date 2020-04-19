package com.zp.code.service;

import com.zp.code.model.UserInfo;

/**
 * @author zhangpeng
 * @since 1.0.0
 */

public interface UserInfoService {

    /**
     * 用户登录
     *
     * @param emlAddr
     * @param passwd
     * @return
     */
    UserInfo login(String emlAddr, String passwd);

    /**
     * 用户注册
     *
     * @param emlAddr
     * @param passwd
     * @param code
     * @return
     */
    UserInfo regist(String emlAddr, String passwd, String code);

    /**
     * 校验用户token并根据token获取userInfo
     *
     * @param token
     * @return
     */
    UserInfo checkToken(String token);

    /**
     * 修改用户信息
     *
     * @param userName
     * @param signature
     * @param userInfo
     */
    void updateUserInfo(String userName, String signature, UserInfo userInfo);

}
