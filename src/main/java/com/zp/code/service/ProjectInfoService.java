package com.zp.code.service;

import com.zp.code.DTO.HomePageDTO;
import com.zp.code.DTO.ProjectDetailDTO;
import com.zp.code.DTO.ProjectListDTO;
import com.zp.code.model.UserInfo;

import java.util.List;

/**
 * @author zhangpeng
 * @since 1.0.0
 */

public interface ProjectInfoService {

    /**
     * 我参与的项目
     * @param userInfo
     * @return
     */
    List<ProjectListDTO> joinProject(UserInfo userInfo);

    /**
     * 我收藏的项目
     * @param userInfo
     * @return
     */
    List<ProjectListDTO> favouriteProject(UserInfo userInfo);

    /**
     * 获取项目详情
     * @param projectId
     * @return
     */
    ProjectDetailDTO getprojectDetail(String projectId);

    /**
     * 提交评论
     * @param projectId
     * @param comment
     * @param userInfo
     */
    void saveComment(String projectId, String comment, UserInfo userInfo);

    /**
     * 获取首页
     * @param userInfo
     * @return
     */
    HomePageDTO getHomePage(UserInfo userInfo);

    /**
     * 创建项目
     * @param projectName
     * @param projectDesc
     * @param userInfo
     */
    String createProject(String projectName, String projectDesc, UserInfo userInfo);

    /**
     * 提交项目(代码)
     * @param projectId
     * @param code
     * @param userInfo
     */
    void submitProject(String projectId, String code, UserInfo userInfo);

}
