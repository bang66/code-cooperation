package com.zp.code.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author zhangpeng
 * @since 1.0.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "project_info")
public class ProjectInfo extends BasicModel implements Serializable {

    private static final long serialVersionUID = -8199228119156677214L;

    private String projectId;

    private String projectName;

    private String ProjectDesc;

    private long createTime;

    private long updateTime;
}
