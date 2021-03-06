//package com.su.service.impl;
//
//import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import com.su.entity.UmsRole;
//import com.su.framework.exception.MyException;
//import com.su.mapper.UmsRoleMapper;
//import com.su.mapper.UserMapper;
//import com.su.service.UmsRoleService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
///**
// * <p>
// * 用户角色 服务实现类
// * </p>
// *
// * @author molamola
// * @since 2019-11-26
// */
//@Service
//public class UmsRoleServiceImpl extends ServiceImpl<UmsRoleMapper, UmsRole> implements UmsRoleService {
//
//    @Autowired
//    private UmsRoleMapper roleMapper;
//
//    @Autowired
//    private UserMapper userMapper;
//
//    @Override
//    public List<UmsRole> listRolesByUserId(String userId) {
//        return roleMapper.findRoleByUserId(userId);
//    }
//
//    /**
//     * 删除用户与角色的对应关系
//     * @param userId
//     * @param roleId
//     */
//    @Override
//    public void deleteUserRoleRelation(String userId, String roleId) {
//
//        Integer result = roleMapper.deleteUserRoleRelation(userId, roleId);
//        if (result != 1){
//            throw new MyException("删除用户角色关系失败");
//        }
//    }
//
//    /**
//     * 批量删除用户角色对应关系
//     * @param userId
//     * @param roleIdList
//     * @return
//     */
//    @Override
//    public void deleteUserRoleRelationInBatch(String userId, List<String> roleIdList) {
//        for (String roleId : roleIdList){
//            this.deleteUserRoleRelation(userId, roleId);
//        }
//    }
//
//    /**
//     * 添加用户角色关系
//     * @param userId
//     * @param roleId
//     */
//    @Override
//    public void addUserRoleRelation(String userId, String roleId) {
//        // 判断用户角色是否都存在
//        if (null == (userMapper.selectById(userId))){
//            throw new MyException("用户不存在");
//        }
//        if (null == roleMapper.selectById(roleId)){
//            throw new MyException("角色不存在");
//        }
//        Integer result = roleMapper.addUserRoleRelation(userId, roleId);
//        if (result != 1){
//            throw new MyException("添加用户角色关系失败");
//        }
//    }
//
//    /**
//     * 添加用户角色关系（批量）
//     * @param userId
//     * @param roleIdList
//     */
//    @Override
//    public void addUserRoleRelationInBatch(String userId, List<String> roleIdList) {
//        for (String roleId : roleIdList){
//            this.addUserRoleRelation(userId, roleId);
//        }
//    }
//}
