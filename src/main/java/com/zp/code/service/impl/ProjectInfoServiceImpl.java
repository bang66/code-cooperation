package com.zp.code.service.impl;

import com.alibaba.fastjson.JSON;
import com.zp.code.DTO.*;
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

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhangpeng
 * @since 1.0.0
 */
@Service("projectInfoService")
public class ProjectInfoServiceImpl extends BaseService implements ProjectInfoService {

    private static final Logger logger = LoggerFactory.getLogger(ProjectInfoServiceImpl.class);

    @Override
    public List<ProjectListDTO> getJoinProject(UserInfo userInfo) {
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
    public List<ProjectListDTO> getFavouriteProject(UserInfo userInfo) {
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
                if (null == commentInfo.getMessage()) {
                    continue;
                }
                Optional<UserInfo> optionalUserInfo = userInfoJPA.findById(commentInfo.getUserId());
                UserInfo userInfo = optionalUserInfo.orElseThrow(() -> new BizException(BizError.DATA_MISS));
                String userName = userInfo.getName();
                MessageDTO messageDTO = MessageDTO.builder()
                        .message(commentInfo.getMessage())
                        .userId(commentInfo.getUserId())
                        .userName(userName)
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
        String userSignature = userInfo.getSignature();
        List<CommentInfo> commentInfoList = commentInfoJPA.findAll();
        List<CommentInfo> commentInfos = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(commentInfoList)) {
            Map<String, List<CommentInfo>> commentInfoMap = commentInfoList.stream().collect(Collectors.groupingBy(CommentInfo::getProjectId));
            List<CommentInfo> finalCommentInfos = commentInfos;
            commentInfoMap.entrySet().stream().forEach(commentInfo -> finalCommentInfos.add(commentInfo.getValue().get(0)));
            commentInfos = finalCommentInfos;
        }

        if (CollectionUtils.isNotEmpty(commentInfos) && commentInfos.size() > 5) {
            commentInfos = commentInfos.subList(0, 4);
        }
        List<ProjectListDTO> projectList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(commentInfos)) {
            SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (CommentInfo commentInfo : commentInfos) {
                Optional<ProjectInfo> optionalProjectInfo = projectInfoJPA.findByProjectId(commentInfo.getProjectId());
                ProjectInfo projectInfo = optionalProjectInfo.orElseThrow(() -> new BizException(BizError.DATA_MISS));
                String code = CommandLineUtils.readCode(projectInfo.getProjectId(), projectInfo.getProjectName());
                ProjectListDTO projectListDTO = ProjectListDTO.builder()
                        .projectId(projectInfo.getProjectId())
                        .name(projectInfo.getProjectName())
                        .code(code)
                        .createTime(dateformat.format(projectInfo.getCreateTime()))
                        .build();
                projectList.add(projectListDTO);
            }
        }
        return HomePageDTO.builder()
                .userName(userName)
                .userSignature(userSignature)
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
    public void submitProject(ProjectSubmitDTO projectSubmitDTO, UserInfo userInfo) {
        if (Objects.isNull(projectSubmitDTO) || Objects.isNull(projectSubmitDTO.getCode())) {
            throw new BizException(BizError.PARAM_ERROR);
        }
        String projectId = projectSubmitDTO.getProjectId();
        String code = projectSubmitDTO.getCode();
        if (StringUtils.isAnyBlank(projectId)) {
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

    @Override
    public void favouriteProject(String projectId, UserInfo userInfo) {
        if (StringUtils.isAnyBlank(projectId)) {
            throw new BizException(BizError.PARAM_ERROR);
        }
        if (Objects.isNull(userInfo) || Objects.isNull(userInfo.getId())) {
            throw new BizException(BizError.ILLEGAL_REQUEST);
        }
        List<String> favouriteList = new ArrayList<>();
        if (StringUtils.isNoneEmpty(userInfo.getFavorites())) {
            favouriteList = JSON.parseArray(userInfo.getFavorites(), String.class);
            if (favouriteList.contains(projectId)) {
                throw new BizException(BizError.PROJECT_ALREADY_FAVOURITED);
            }
        }
        favouriteList.add(projectId);
        String favouriteStr = JSON.toJSONString(favouriteList);
        UserInfo userInfoNow = new UserInfo();
        userInfoNow.setId(userInfo.getId());
        userInfoNow.setEmlAddr(userInfo.getEmlAddr());
        userInfoNow.setName(userInfo.getName());
        userInfoNow.setPasswd(userInfo.getPasswd());
        userInfoNow.setToken(userInfo.getToken());
        userInfoNow.setCreateTime(userInfo.getCreateTime());
        userInfoNow.setFavorites(favouriteStr);
        userInfoNow.setUpdateTime(System.currentTimeMillis());
        userInfoJPA.save(userInfoNow);
    }
}
