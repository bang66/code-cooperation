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
public class ProjectDetailDTO {

    private String projectId;

    private String name;

    private String code;

    private String desc;

    private List<MessageDTO> messageDTOList;

}
