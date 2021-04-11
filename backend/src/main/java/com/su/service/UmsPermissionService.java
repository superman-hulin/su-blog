package com.su.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.su.entity.UmsPermission;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 用户权限 服务类
 * </p>
 *
 * @author molamola
 * @since 2019-11-26
 */
@Transactional
public interface UmsPermissionService extends IService<UmsPermission> {

    List<UmsPermission> listPermissionsByUserId(String userId);

    List<UmsPermission> listPermissionsByRoleId(String roleId);

    void deleteRolePermissionRelation(String roleId, String permissionId);

    void deleteRolePermissionRelationInBatch(String roleId, List<String> permissionIdList);

    void addRolePermissionRelation(String roleId, String permissionId);

    void addRolePermissionRelationInBatch(String roleId, List<String> permissionIdList);
}
