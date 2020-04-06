package com.zp.code.controller;

import com.zp.code.DTO.HomePageDTO;
import com.zp.code.DTO.ProjectDetailDTO;
import com.zp.code.DTO.ProjectListDTO;
import com.zp.code.aop.WebLog;
import com.zp.code.model.UserInfo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zhangpeng
 * @since 1.0.0
 */
@RestController
@RequestMapping(value = "/api")
public class ProjectInfoController extends BaseController {


    @RequestMapping(value = "/v1/project/join", method = RequestMethod.GET)
    @WebLog
    public Object getJoinProject() {
        UserInfo userInfo = getUserInfoByToken();
        List<ProjectListDTO> projectListDTOS = projectInfoService.joinProject(userInfo);
        return success(projectListDTOS);
    }

    @RequestMapping(value = "/v1/project/favourite", method = RequestMethod.GET)
    @WebLog
    public Object getFavouriteProject() {
        UserInfo userInfo = getUserInfoByToken();
        List<ProjectListDTO> projectListDTOS = projectInfoService.favouriteProject(userInfo);
        return success(projectListDTOS);
    }

    @RequestMapping(value = "/v1/project/detail", method = RequestMethod.GET)
    @WebLog
    public Object getProjectDetail(@RequestParam String projectId) {
        ProjectDetailDTO projectDetailDTO = projectInfoService.getprojectDetail(projectId);
        return success(projectDetailDTO);
    }

    @RequestMapping(value = "/v1/comment/submit", method = RequestMethod.POST)
    @WebLog
    public Object saveComment(@RequestParam String projectId,
                              @RequestParam String comment) {
        UserInfo userInfo = getUserInfoByToken();
        projectInfoService.saveComment(projectId, comment, userInfo);
        return success();
    }

    @RequestMapping(value = "/v1/homePage/get", method = RequestMethod.GET)
    @WebLog
    public Object getHomePage() {
        UserInfo userInfo = getUserInfoByToken();
        HomePageDTO homePage = projectInfoService.getHomePage(userInfo);
        return success(homePage);
    }

    @RequestMapping(value = "/v1/project/create", method = RequestMethod.POST)
    @WebLog
    public Object getProjectDetail(@RequestParam String projectName,
                                   @RequestParam String projectDesc) {
        UserInfo userInfo = getUserInfoByToken();
        String projectId = projectInfoService.createProject(projectName, projectDesc, userInfo);
        return success(projectId);
    }

    @RequestMapping(value = "/v1/project/submit", method = RequestMethod.POST)
    @WebLog
    public Object submitProject(@RequestParam String projectId,
                                   @RequestParam String code) {
        UserInfo userInfo = getUserInfoByToken();
        projectInfoService.submitProject(projectId, code, userInfo);
        return success(projectId);
    }

    @RequestMapping(value = "/v1/project/run", method = RequestMethod.GET)
    @WebLog
    public Object runProject(@RequestParam String projectId) {
        String res = projectInfoService.runProject(projectId);
        return success(res);
    }
}
