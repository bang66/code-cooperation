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
@Table(name = "comment_info")
public class CommentInfo extends BasicModel implements Serializable {

    private static final long serialVersionUID = -3208942184045865168L;

    private String projectId;

    private String userId;

    private String message;

    private long createTime;

    private long updateTime;
}
