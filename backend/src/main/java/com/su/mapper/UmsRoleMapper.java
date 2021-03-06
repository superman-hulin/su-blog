package com.su.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.su.entity.UmsRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 用户角色 Mapper 接口
 * </p>
 *
 * @author molamola
 * @since 2019-11-26
 */
@Repository
public interface UmsRoleMapper extends BaseMapper<UmsRole> {

    /**
     * 根据userId查询role
     */
    List<UmsRole> findRoleByUserId(@Param("userId") String userId);

    /**
     * 删除user与role的关系
     * @param userId
     * @param roleId
     * @return
     */
    Integer deleteUserRoleRelation(@Param("userId") String userId,
                                   @Param("roleId") String roleId);

    /**
     * 添加user与role的关系
     * @param userId
     * @param roleId
     * @return
     */
    Integer addUserRoleRelation(@Param("userId") String userId,
                                   @Param("roleId") String roleId);
}
