package com.su.framework.filter;

import com.su.framework.component.SuUserDetailsService;
import com.su.framework.jwt.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author : molamola
 * @Project: edu
 * @Description: 用于解析header中token的filter
 * @date : 2019-11-27 14:57
 **/
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    /**
     * token的拼接
     */
//    @Value("${jwt.tokenHead}")
//    private String tokenHead;

    /**
     * 请求头里的Authorization
     */
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private SuUserDetailsService suUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 验证token
        String token = request.getHeader(this.tokenHeader);
        if (null != token){
            //String authToken = authHeader.substring(this.tokenHeader.length());
            // 获取用户名
            String username = jwtTokenUtil.getUserNameFromToken(token);
            // 服务重启或单点登录进入（有token无session情况）
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                UserDetails userDetails = suUserDetailsService.loadUserByUsername(username);
                // 如果token有效
                if (jwtTokenUtil.validateToken(token, userDetails)){
                    // request中放入用户名
                    request.setAttribute("username",username);
                    request.setAttribute("token", token);
                    // 鉴权通过
                    UsernamePasswordAuthenticationToken authentication
                            = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        // 放行
        filterChain.doFilter(request,response);
    }
}
