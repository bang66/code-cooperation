package com.zp.code.model.JPABasic;

/**
 * @author zhangpeng
 * @since 1.0.0
 */

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class JPABase {
    transient boolean willBeSaved = false;

    public JPABase() {
    }
}
