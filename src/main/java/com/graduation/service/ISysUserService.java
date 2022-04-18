package com.graduation.service;

import com.graduation.controller.dto.UserDTo;
import com.graduation.controller.dto.UserPasswordDTO;
import com.graduation.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Mercy
 * @since 2022-04-13
 */
public interface ISysUserService extends IService<SysUser> {
    UserDTo login(UserDTo user);

    boolean registers(UserDTo user);

    void updatePassword(UserPasswordDTO userPasswordDTO);
}
