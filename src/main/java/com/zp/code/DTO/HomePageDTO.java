package com.zp.code.DTO;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author zhangpeng
 * @since 1.0.0
 */

@Data
@Builder
public class HomePageDTO {

    private String userName;

    private String userSignature;

    private List<ProjectListDTO> projectList;

}
