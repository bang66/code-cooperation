package com.zp.code.DTO;

import lombok.Builder;
import lombok.Data;

/**
 * @author zhangpeng
 * @since 1.0.0
 */

@Data
@Builder
public class MessageDTO {
    private Long userId;

    private String message;
}
