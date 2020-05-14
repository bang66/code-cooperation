package com.zp.code.interceptor;


import com.zp.code.DTO.CommonParams;
import com.zp.code.common.BizError;
import com.zp.code.handle.BizException;
import com.zp.code.model.UserInfo;
import com.zp.code.service.UserInfoService;
import com.zp.code.utils.CommonParamUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class ApiInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(ApiInterceptor.class);

    @Autowired
    private UserInfoService userInfoService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setHeader("Access-Control-Allow-Methods", "GET, POST");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, *");
        response.setHeader("P3P", "CP=\"IDC DSP COR CURa ADMa OUR IND PHY ONL COM STA\"");
        response.setHeader("Access-Control-Allow-Origin", "*");
        if (!(handler instanceof HandlerMethod)) {
            return false;
        }
        String token = request.getHeader("t");
        if (StringUtils.isEmpty(token)) {
            throw new BizException(BizError.ILLEGAL_REQUEST);
        }
        logger.info("[Request] Token:{}", token);
        UserInfo userInfo = userInfoService.checkToken(token);
        CommonParams params = CommonParams.builder().token(token).build();
        CommonParamUtils.set(params);
        logger.info("[Request] Params:{}", params);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
    }
}
