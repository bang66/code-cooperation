package com.zp.code.service.impl;

import com.zp.code.common.BizError;
import com.zp.code.handle.BizException;
import com.zp.code.interceptor.ApiInterceptor;
import com.zp.code.service.CommonService;
import com.zp.code.utils.BuilderUtil;
import com.zp.code.utils.MailUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangpeng
 * @since 1.0.0
 */
@Service("commonService")
public class CommonServiceImpl implements CommonService {

    private static final Logger logger = LoggerFactory.getLogger(CommonServiceImpl.class);

    public static Map<String, String> codeMap = new HashMap<>();

    @Override
    public void sendCode(String emlAddr) {
        String code = BuilderUtil.generateCode();
        codeMap.put(emlAddr, code);
        try {
            MailUtil.sendMail(emlAddr, code);
        } catch (Exception e) {
            logger.error("[Mail] mail send error : {}", e);
            throw new BizException(BizError.SEND_ERROR);
        }
    }
}
