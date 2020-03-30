package com.zp.code.service;

import com.zp.code.DTO.ProjectListDTO;
import com.zp.code.model.UserInfo;

import java.util.List;

/**
 * @author zhangpeng
 * @since 1.0.0
 */

public interface UserInfoService {

    UserInfo login(String emlAddr, String passwd);

    UserInfo regist(String emlAddr, String passwd, String code);

    UserInfo checkToken(String token);

}
