package com.su.controller;


import com.github.pagehelper.PageInfo;
import com.su.entity.User;
import com.su.service.impl.UserServiceImpl;
import com.su.utils.RequestUtils;
import com.su.utils.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhaoguoshun
 * @since 2020-12-11
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;

//    @Value("${jwt.tokenHead}")
//    private String tokenHead;

    @Autowired
    private PasswordEncoder passwordEncoder;
    /**
     * 用户登录
     * @return
     */
//    @PostMapping("/login")
//    @ApiOperation("用户登录")
//    public Map login(@RequestBody User user){
//        String token = userServiceImpl.login(user.getUserName(), user.getPassword());
//        Map<String, String> tokenMap = new HashMap<>();
//        tokenMap.put("token", token);
//        tokenMap.put("tokenHead", tokenHead);
//        return Result.ok(tokenMap, "登录成功");
//    }

    /**
     * 用户刷新token
     * @return
     */
//    @GetMapping("/refreshToken")
//    @ApiOperation("用户刷新token")
//    public Result refreshToken(HttpServletRequest request){
//        Map<String, String> tokenMap = new HashMap<>();
//        tokenMap.put("token",userServiceImpl.refreshToken((String) request.getAttribute("token")));
//        tokenMap.put("tokenHead", tokenHead);
//        return Result.ok(tokenMap);
//    }

    @PostMapping("/create")
    public Result  create(@RequestBody User user){
        userServiceImpl.create(user);
        return Result.ok(user);
    }

    @PostMapping("/delete")
    public Result delete(Integer id){
        userServiceImpl.delete(id);
        return Result.ok();
    }

    @PostMapping("/update")
    public Result update(@RequestBody  User user){
        userServiceImpl.update(user);
        return Result.ok(user);
    }
    @PostMapping("/query")
    public Map query(@RequestBody User user){
        PageInfo<User> pageInfo = userServiceImpl.query(user);
        System.out.println(pageInfo.getList());
        return Result.ok(pageInfo);
    }

    @PostMapping("/detail")
    public Result detail(Integer id){
        User detail = userServiceImpl.detail(id);
        return Result.ok(detail);
    }

    @PostMapping("/count")
    public Result count(@RequestBody User user){
        int count = userServiceImpl.count(user);
        return Result.ok(count);
    }

    @PostMapping("/upload")
    public Result upload(@RequestParam MultipartFile file, HttpServletRequest request) throws IOException {
        String originalFilename = file.getOriginalFilename();
        //获取文件名后缀
        String ex = originalFilename.substring(originalFilename.lastIndexOf(".")+1,originalFilename.length());
        String newFileNamePrefix= UUID.randomUUID().toString();
        String newFileName=newFileNamePrefix+"."+ex;
        file.transferTo(new File("D:/upload/cms",newFileName));

        System.out.println(RequestUtils.getBasePath(request)+"upload/"+newFileName);
        //最后返回的是一个可以访问的全路径
        return Result.ok(RequestUtils.getBasePath(request)+"upload/"+newFileName);
    }

}

