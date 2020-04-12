package com.zp.code.DTO;

import lombok.Builder;
import lombok.Data;

/**
 * @author zhangpeng
 * @since 1.0.0
 */

@Data
@Builder
public class ProjectListDTO {

    private String projectId;

    private String name;

    private String code;

    private String createTime;

}
