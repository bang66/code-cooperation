package com.zp.code.service.impl;

import com.alibaba.fastjson.JSON;
import com.zp.code.DTO.HomePageDTO;
import com.zp.code.DTO.MessageDTO;
import com.zp.code.DTO.ProjectDetailDTO;
import com.zp.code.DTO.ProjectListDTO;
import com.zp.code.common.BizError;
import com.zp.code.handle.BizException;
import com.zp.code.model.CommentInfo;
import com.zp.code.model.ProjectInfo;
import com.zp.code.model.UserInfo;
import com.zp.code.service.BaseService;
import com.zp.code.service.ProjectInfoService;
import com.zp.code.utils.BuilderUtil;
import com.zp.code.utils.CommandLineUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author zhangpeng
 * @since 1.0.0
 */
@Service("projectInfoService")
public class ProjectInfoServiceImpl extends BaseService implements ProjectInfoService {

    private static final Logger logger = LoggerFactory.getLogger(ProjectInfoServiceImpl.class);

    @Override
    public List<ProjectListDTO> joinProject(UserInfo userInfo) {
        if (Objects.isNull(userInfo) || Objects.isNull(userInfo.getId())) {
            throw new BizException(BizError.PARAM_ERROR);
        }
        List<CommentInfo> commentInfoList = commentInfoJPA.findByUserId(userInfo.getId());
        List<ProjectListDTO> projectListDTOS = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(commentInfoList)) {
            for (CommentInfo commentInfo : commentInfoList) {
                Optional<ProjectInfo> optionalProjectInfo = projectInfoJPA.findByProjectId(commentInfo.getProjectId());
                ProjectInfo projectInfo = optionalProjectInfo.orElseThrow(() -> new BizException(BizError.DATA_MISS));

                String code = CommandLineUtils.readCode(projectInfo.getProjectId(), projectInfo.getProjectName());

                ProjectListDTO projectListDTO = ProjectListDTO.builder()
                        .name(projectInfo.getProjectName())
                        .projectId(commentInfo.getProjectId())
                        .code(code)
                        .build();
                projectListDTOS.add(projectListDTO);
            }
        }
        return projectListDTOS;
    }

    @Override
    public List<ProjectListDTO> favouriteProject(UserInfo userInfo) {
        if (Objects.isNull(userInfo) || Objects.isNull(userInfo.getId())) {
            throw new BizException(BizError.PARAM_ERROR);
        }
        String favourites = userInfo.getFavorites();
        List<ProjectListDTO> projectListDTOS = new ArrayList<>();
        List<String> projectIdList = JSON.parseArray(favourites, String.class);
        for (String projectId : projectIdList) {
            Optional<ProjectInfo> optionalProjectInfo = projectInfoJPA.findByProjectId(projectId);
            ProjectInfo projectInfo = optionalProjectInfo.orElseThrow(() -> new BizException(BizError.DATA_MISS));
            String code = CommandLineUtils.readCode(projectInfo.getProjectId(), projectInfo.getProjectName());
            ProjectListDTO projectListDTO = ProjectListDTO.builder()
                    .name(projectInfo.getProjectName())
                    .projectId(projectInfo.getProjectId())
                    .code(code)
                    .build();
            projectListDTOS.add(projectListDTO);
        }
        return projectListDTOS;
    }

    @Override
    public ProjectDetailDTO getprojectDetail(String projectId) {
        if (StringUtils.isBlank(projectId)) {
            throw new BizException(BizError.PARAM_ERROR);
        }
        Optional<ProjectInfo> optionalProjectInfo = projectInfoJPA.findByProjectId(projectId);
        ProjectInfo projectInfo = optionalProjectInfo.orElseThrow(() -> new BizException(BizError.DATA_MISS));
        String code = CommandLineUtils.readCode(projectInfo.getProjectId(), projectInfo.getProjectName());
        List<CommentInfo> commentInfoList = commentInfoJPA.findByProjectId(projectId);
        List<MessageDTO> messageDTOList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(commentInfoList)) {
            for (CommentInfo commentInfo : commentInfoList) {
                MessageDTO messageDTO = MessageDTO.builder()
                        .message(commentInfo.getMessage())
                        .userId(commentInfo.getUserId())
                        .build();
                messageDTOList.add(messageDTO);
            }
        }
        return ProjectDetailDTO.builder()
                .projectId(projectId)
                .name(projectInfo.getProjectName())
                .code(code)
                .desc(projectInfo.getProjectDesc())
                .messageDTOList(messageDTOList)
                .build();
    }

    @Override
    public void saveComment(String projectId, String comment, UserInfo userInfo) {
        if (Objects.isNull(userInfo) || Objects.isNull(userInfo.getId())) {
            throw new BizException(BizError.ILLEGAL_REQUEST);
        }
        if (StringUtils.isAnyBlank(projectId, comment)) {
            throw new BizException(BizError.PARAM_ERROR);
        }
        commentInfoJPA.save(CommentInfo.builder()
                .projectId(projectId)
                .message(comment)
                .userId(userInfo.getId())
                .createTime(System.currentTimeMillis())
                .build()
        );
    }

    @Override
    public HomePageDTO getHomePage(UserInfo userInfo) {
        if (Objects.isNull(userInfo) || Objects.isNull(userInfo.getId())) {
            throw new BizException(BizError.ILLEGAL_REQUEST);
        }
        String userName = userInfo.getName();
        List<CommentInfo> commentInfoList = commentInfoJPA.searchHotProjects();
        if (CollectionUtils.isNotEmpty(commentInfoList) && commentInfoList.size() > 5) {
            commentInfoList = commentInfoList.subList(0, 4);
        }
        List<ProjectListDTO> projectList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(commentInfoList)) {
            for (CommentInfo commentInfo : commentInfoList) {
                Optional<ProjectInfo> optionalProjectInfo = projectInfoJPA.findByProjectId(commentInfo.getProjectId());
                ProjectInfo projectInfo = optionalProjectInfo.orElseThrow(() -> new BizException(BizError.DATA_MISS));
                String code = CommandLineUtils.readCode(projectInfo.getProjectId(), projectInfo.getProjectName());
                ProjectListDTO projectListDTO = ProjectListDTO.builder()
                        .projectId(projectInfo.getProjectId())
                        .name(projectInfo.getProjectName())
                        .code(code)
                        .build();
                projectList.add(projectListDTO);
            }
        }
        return HomePageDTO.builder()
                .userName(userName)
                .projectList(projectList)
                .build();
    }

    @Override
    public String createProject(String projectName, String projectDesc, UserInfo userInfo) {
        if (StringUtils.isAnyBlank(projectName, projectDesc)) {
            throw new BizException(BizError.PARAM_ERROR);
        }
        if (Objects.isNull(userInfo) || Objects.isNull(userInfo.getId())) {
            throw new BizException(BizError.ILLEGAL_REQUEST);
        }
        String projectId = BuilderUtil.generateProjectId();
        boolean result = CommandLineUtils.createFile(projectId, projectName, "");
        if (!result) {
            throw new BizException(BizError.SYSTEM_ERROR);
        }

        projectInfoJPA.save(ProjectInfo.builder()
                .projectId(projectId)
                .projectName(projectName)
                .ProjectDesc(projectDesc)
                .createTime(System.currentTimeMillis())
                .build());

        commentInfoJPA.save(CommentInfo.builder()
                .projectId(projectId)
                .userId(userInfo.getId())
                .createTime(System.currentTimeMillis())
                .build());
        return projectId;
    }

    @Override
    public void submitProject(String projectId, String code, UserInfo userInfo) {
        if (StringUtils.isAnyBlank(projectId, code)) {
            throw new BizException(BizError.PARAM_ERROR);
        }
        if (Objects.isNull(userInfo) || Objects.isNull(userInfo.getId())) {
            throw new BizException(BizError.ILLEGAL_REQUEST);
        }
        Optional<ProjectInfo> optionalProjectInfo = projectInfoJPA.findByProjectId(projectId);
        ProjectInfo projectInfo = optionalProjectInfo.orElseThrow(() -> new BizException(BizError.DATA_MISS));
        boolean result = CommandLineUtils.createFile(projectId, projectInfo.getProjectName(), code);
        if (!result) {
            throw new BizException(BizError.SYSTEM_ERROR);
        }
        commentInfoJPA.save(CommentInfo.builder()
                .userId(userInfo.getId())
                .projectId(projectId)
                .createTime(System.currentTimeMillis())
                .build());
    }

    @Override
    public String runProject(String projectId) {
        if (StringUtils.isAnyBlank(projectId)) {
            throw new BizException(BizError.PARAM_ERROR);
        }
        Optional<ProjectInfo> optionalProjectInfo = projectInfoJPA.findByProjectId(projectId);
        ProjectInfo projectInfo = optionalProjectInfo.orElseThrow(() -> new BizException(BizError.DATA_MISS));
        String projectName = projectInfo.getProjectName();
        String res = CommandLineUtils.runProject(projectId, projectName);
        logger.info("[RunProject] res:{}", res);
        return res;
    }
}
