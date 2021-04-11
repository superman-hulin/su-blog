package com.su.framework.component;

import com.su.entity.User;
import com.su.framework.exception.MyException;
import com.su.service.UmsPermissionService;
import com.su.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @author : molamola
 * @Project: edu
 * @Description:
 * @date : 2019-11-27 15:40
 **/
@Component
public class SuUserDetailsService implements UserDetailsService {

//    @Autowired
//    private UmsPermissionService permissionService;

    @Autowired
    private UserService userService;

    @Override
    public SuUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 查找用户
        User user = userService.findUserByUsername(username);
        if (null == user){
            //抛出usernamenotfound异常
            throw new MyException("用户名不存在");
        }
        // 构建userDetail
        //return new SuUserDetails(user, permissionService.listPermissionsByUserId(String.valueOf(user.getId())));
        return new SuUserDetails(user);
    }
}
