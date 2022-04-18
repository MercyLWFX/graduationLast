package com.graduation.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.graduation.entity.SysUser;
import com.graduation.service.ISysUserService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class TokenUtils {

    private static ISysUserService userService;

    @Resource
    private ISysUserService service;

    @PostConstruct
    public void setUserService() {
        userService = service;
    }

    /**
     * 生成token
     *
     * @param userId
     * @param sign
     * @return
     */
    public static String genToken(String userId, String sign) {
        return JWT.create().withAudience(userId) // 将 user id 保存到 token 里面,作为载荷
                .withExpiresAt(DateUtil.offsetHour(new Date(), 2)) // 2小时后token过期
                .sign(Algorithm.HMAC256(sign)); // 以 password 作为 token 的密钥
    }


    /**
     * 获取当前登录的用户信息
     */
    public static SysUser getCurrentUser() {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader("token");
            if (StrUtil.isNotBlank(token)) {
                Long userId = Long.valueOf(JWT.decode(token).getAudience().get(0));
//                System.out.println(userId);
                return userService.getById(userId);
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
}
