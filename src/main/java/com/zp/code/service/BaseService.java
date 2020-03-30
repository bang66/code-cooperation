package com.zp.code.service;

import com.zp.code.repository.CommentInfoJPA;
import com.zp.code.repository.ProjectInfoJPA;
import com.zp.code.repository.UserInfoJPA;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author zhangpeng
 * @since 1.0.0
 */
public class BaseService {

    @Autowired
    protected UserInfoJPA userInfoJPA;

    @Autowired
    protected CommentInfoJPA commentInfoJPA;

    @Autowired
    protected ProjectInfoJPA projectInfoJPA;
}
