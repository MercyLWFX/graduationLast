package com.graduation.mapper;

import com.graduation.entity.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Mercy
 * @since 2022-04-12
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {
    @Select("select roleid from sys_role where flag = #{flag}")
    Integer selectByFlag(@Param("flag") String flag);

}
