package com.graduation.service;

import com.graduation.entity.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Mercy
 * @since 2022-04-12
 */
public interface ISysMenuService extends IService<SysMenu> {

    List<SysMenu> findMenus(String name);
}
