package com.su.service;

import com.su.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhaoguoshun
 * @since 2020-12-11
 */
public interface UserService extends IService<User> {
    User findUserByUsername(String username);

    String login(String username, String password);

    String refreshToken(String token);

    //String uploadHeaderIcon(MultipartFile header, String userId);

    void updatePassword(String old, String now, User user);
}
