package com.su.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.su.entity.UmsRole;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 用户角色 服务类
 * </p>
 *
 * @author molamola
 * @since 2019-11-26
 */
@Transactional
public interface UmsRoleService extends IService<UmsRole> {

    List<UmsRole> listRolesByUserId(String userId);

    void deleteUserRoleRelation(String userId, String roleId);

    void deleteUserRoleRelationInBatch(String userId, List<String> roleIdList);

    void addUserRoleRelation(String userId, String roleId);

    void addUserRoleRelationInBatch(String userId, List<String> roleIdList);
}
