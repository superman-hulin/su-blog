package com.su.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.su.entity.User;
import com.su.framework.component.SuUserDetailsService;
import com.su.framework.config.UserConfig;
import com.su.framework.exception.MyException;
import com.su.framework.jwt.JwtTokenUtil;
import com.su.mapper.UserMapper;
import com.su.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.su.utils.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhaoguoshun
 * @since 2020-12-11
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SuUserDetailsService detailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserConfig userConfig;

    private HashSet<String> allowHeaderSuffixSet = new HashSet<>();

    @PostConstruct
    public void initAllowHeaderSuffixSet(){
        allowHeaderSuffixSet.add("jpg");
        allowHeaderSuffixSet.add("jpeg");
        allowHeaderSuffixSet.add("png");
        allowHeaderSuffixSet.add("bmp");
    }

    /**
     * 根据username查询用户
     * @param username
     * @return
     */
    @Override
    public User findUserByUsername(String username) {
        Map<String, Object> conditionMap = new HashMap();
        conditionMap.put("userName", username);
        User user=userMapper.findByName(username);
//        List<User> result = userMapper.selectByMap(conditionMap);
//        if (result.size() == 0)
//            return null;
//        if (result.size() > 1)
//            throw new MyException("相同用户名存在多个用户，请管理员检查");
//        return result.get(0);
        if(user==null){
            throw new MyException("该用户不存在");
        }
        return user;
    }


    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    @Override
    public String login(String username, String password) {
        // 根据用户名获取user
        UserDetails userDetails = detailsService.loadUserByUsername(username);
//        if (!passwordEncoder.matches(password, userDetails.getPassword())){
//            throw new BadCredentialsException("密码不正确");
//        }
        if (!userDetails.getPassword().equals(password)){
            throw new BadCredentialsException("密码不正确");
        }
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails,null,
                        userDetails.getAuthorities());
        // 存入security的session
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // 生成token
        return jwtTokenUtil.generateToken(userDetails);
    }


    @Override
    public String refreshToken(String token) {
        return jwtTokenUtil.refreshToken(token);
    }

    /**
     * 用户头像上传
     * @param
     * @return
     */
//    @Override
//    public String uploadHeaderIcon(MultipartFile header, String userId) {
//        String iconRootPath = userConfig.getHeaderIconPath();
//        if (!iconRootPath.endsWith(File.separator))
//            iconRootPath += File.separator;
//        checkSuffix(header.getOriginalFilename());
//        // 以用户id构建iconPath
//        String iconPath = iconRootPath + userId + "-" + header.getOriginalFilename();
//
//        try {
//            FileUtils.writeByteArrayToFile(new File(iconPath), header.getBytes());
//        } catch (IOException e) {
//            throw new MyException("上传头像失败");
//        }
//        // 拼接icon的url
//        String iconUrl = "/iconHeader/"+userId + "-" + header.getOriginalFilename();
//
//        return iconUrl;
//    }

    @Override
    public void updatePassword(String old, String now, User user) {
        // 1.判断老密码是否正确
        // 根据用户名获取user
        UserDetails userDetails = detailsService.loadUserByUsername(user.getUserName());
        if (!passwordEncoder.matches(old, userDetails.getPassword())){
            throw new BadCredentialsException("原密码不正确");
        }
        // 2.修改密码与更新时间
        user.setPassword(passwordEncoder.encode(now));
        //user.setUpdateTime(LocalDateTime.now());
        // 3.更新
        this.updateById(user);
    }

    private void checkSuffix(String fileName){
        String suffix =fileName.substring(
                fileName.lastIndexOf(".")+1).toLowerCase();

        if (!allowHeaderSuffixSet.contains(suffix)){
            throw new MyException("上传头像不支持后缀"+suffix);
        }
    }

    public int create(User user){
        return userMapper.create(user);
    }

    public int delete(Integer id){
        return userMapper.delete(Maps.build(id).getMap());
    }

    public int update(User user){
        System.out.println(user);
        System.out.println(Maps.build().beanToMap(user));
        return userMapper.update(Maps.build(user.getId()).beanToMapForUpdate(user));
    }

    public PageInfo<User> query(User user){
        if (user!=null && user.getPage() != null){
            PageHelper.startPage(user.getPage(),user.getLimit());
        }
        List<User> list = userMapper.query(Maps.build().beanToMap(user));
        for (int i=0; i<list.size();i++){
            String status = list.get(i).getStatus();
            if (status.equals("T")){
                list.get(i).setStatus("正常");
            }
            if (status.equals("F")){
                list.get(i).setStatus("禁用");
            }
            if (status.equals("D")){
                list.get(i).setStatus("待删除");
            }
        }

        return new PageInfo<> (list);
    }

//    public User login(String userName,String password ){
//
//        System.out.println("serviceName"+Maps.build()
//                .put("userName",userName)
//                .put("password",password)
//                .getMap());
//        return userMapper.detail(Maps.build()
//                .put("userName",userName)
//                .put("password",password)
//                .getMap());
//    }

    public User detail(Integer id){
        return userMapper.detail(Maps.build(id).getMap());
    }

    public int count(User user){
        return userMapper.count(Maps.build().beanToMap(user));
    }

}
