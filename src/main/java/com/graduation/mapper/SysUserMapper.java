package com.graduation.mapper;

import com.graduation.controller.dto.UserDTo;
import com.graduation.controller.dto.UserPasswordDTO;
import com.graduation.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Mercy
 * @since 2022-04-13
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    @Update("update sys_user set password = #{newPassword} where username = #{username} and password = #{password}")
    int updatePassword(UserPasswordDTO userPasswordDTO);

    @Update("update sys_user set money = #{money} where id=#{id} ")
    int updateUserMoney(UserDTo userDTo);

    @Update("update sys_user set rolename='ROLE_COMPANY' where id=#{id}")
    boolean changeUsername(SysUser one);
}
