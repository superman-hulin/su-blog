package com.su.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户权限
 * </p>
 *
 * @author molamola
 * @since 2019-11-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UmsPermission implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * per_(八位)
     */
    private String id;

    /**
     * 角色名
     */
    private String name;

    /**
     * 角色描述
     */
    private String description;

    /**
     * 权限验证值
     */
    private String value;

    /**
     * 可以访问的url
     */
    private String url;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


}
