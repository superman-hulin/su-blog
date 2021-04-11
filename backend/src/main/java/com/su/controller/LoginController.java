package com.su.controller;

import com.su.service.UserService;
import com.su.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {

    @Autowired
    private UserService userService;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @PostMapping("/login")
    public Map login(@RequestBody Map<String,String> paramMap){
        String userName=paramMap.get("userName");
        String password=paramMap.get("password");
        System.out.println(userName+":"+password);
        String token = userService.login(userName, password);
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put(tokenHeader, token);
        //tokenMap.put("tokenHead", tokenHead);
        return Result.ok(tokenMap, "登录成功");


    }


}
