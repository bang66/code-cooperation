package com.zp.code.service.impl;

import com.zp.code.common.BizError;
import com.zp.code.handle.BizException;
import com.zp.code.model.UserInfo;
import com.zp.code.repository.UserInfoJPA;
import com.zp.code.service.UserInfoService;
import com.zp.code.utils.BuilderUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author zhangpeng
 * @since 1.0.0
 */
@Service("userInfoService")
public class UserInfoServiceImpl implements UserInfoService {

    private static final Logger logger = LoggerFactory.getLogger(UserInfoServiceImpl.class);


    @Autowired
    protected UserInfoJPA userInfoJPA;

    @Override
    public UserInfo login(String emlAddr, String passwd) {
        if (StringUtils.isAnyBlank(emlAddr, passwd)) {
            throw new BizException(BizError.PARAM_ERROR);
        }
        Optional<UserInfo> optionalUserInfo = userInfoJPA.findByEmlAddr(emlAddr);
        UserInfo userInfo = optionalUserInfo.orElseThrow(() -> new BizException(BizError.ACCOUNT_NOT_REGIST));
        if (StringUtils.isNoneBlank(userInfo.getPasswd()) && userInfo.getPasswd() != passwd) {
            throw new BizException(BizError.ACCOUNT_ERROR);
        }
        return userInfo;
    }

    @Override
    public UserInfo regist(String emlAddr, String passwd, String code) {
        if (StringUtils.isAnyBlank(emlAddr, passwd, code)) {
            throw new BizException(BizError.PARAM_ERROR);
        }
        String getCode = CommonServiceImpl.codeMap.get(emlAddr);
        if (StringUtils.isBlank(getCode)) {
            throw new BizException(BizError.NOT_SEND_CODE);
        }
        if (!code.equals(getCode)) {
            throw new BizException(BizError.CODE_ERROR);
        }
        UserInfo userInfo = UserInfo.builder()
                .emlAddr(emlAddr)
                .passwd(passwd)
                .token(BuilderUtil.generateToken())
                .createTime(System.currentTimeMillis())
                .build();
        userInfoJPA.save(userInfo);
        logger.info("[Regist] userInfo save db : {}", userInfo);
        return userInfo;
    }

    @Override
    public UserInfo checkToken(String token) {
        if (StringUtils.isBlank(token)) {
            throw new BizException(BizError.NOT_SEND_CODE);
        }
        Optional<UserInfo> optionalUserInfo = userInfoJPA.findByToken(token);
        UserInfo userInfo = optionalUserInfo.orElseThrow(() -> new BizException(BizError.ILLEGAL_REQUEST));
        return userInfo;
    }

    @Override
    public void joinProject(UserInfo userInfo) {

    }
}
