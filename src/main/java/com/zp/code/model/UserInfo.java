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
@Table(name = "user_info")
public class UserInfo extends BasicModel implements Serializable {

    private static final long serialVersionUID = 6595107830322457445L;

    private String emlAddr;

    private String passwd;

    private String token;

    private String name;

    /** ["pid1", "pid2"] */
    private String favorites;

    private long createTime;

    private long updateTime;
}
