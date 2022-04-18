package com.graduation.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.graduation.common.Constants;
import com.graduation.controller.dto.UserDTo;
import com.graduation.controller.dto.UserPasswordDTO;
import com.graduation.entity.SysMenu;
import com.graduation.entity.SysUser;
import com.graduation.exception.ServiceException;
import com.graduation.mapper.RoleMenuMapper;
import com.graduation.mapper.SysRoleMapper;
import com.graduation.mapper.SysUserMapper;
import com.graduation.service.ISysMenuService;
import com.graduation.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.graduation.utils.TokenUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Mercy
 * @since 2022-04-13
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    private  static  final Log LOG=Log.get();

    @Resource
    private SysRoleMapper roleMapper;

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Resource
    private ISysMenuService menuService;

    @Resource
    private SysUserMapper userMapper;

    public boolean saveUser(SysUser user) {
        return saveOrUpdate(user);
    }

    @Override
    public UserDTo login(UserDTo user) {

        SysUser one = getUserInfo(user);
        if (one!=null){
            BeanUtil.copyProperties(one,user,true);
            //设置token
            String token= TokenUtils.genToken(one.getId().toString(),one.getPassword());
            user.setToken(token);

            String rolename = one.getRolename();


            List<SysMenu> roleMenus = getRoleMenus(rolename);
            user.setMenus(roleMenus);

            return user;
        }else {
            throw new ServiceException(Constants.CODE_600,"用户名或密码错误");
        }
    }

    @Override
    public boolean registers(UserDTo user) {
        SysUser one = getUserInfo(user);
        if (one==null){
            one=new SysUser();
            BeanUtil.copyProperties(user,one,true);
            one.setRolename("ROLE_USER");
            return save(one);
        }else {
            throw new ServiceException(Constants.CODE_600,"该用户名已被使用");
        }
    }

    @Override
    public void updatePassword(UserPasswordDTO userPasswordDTO) {
        int update = userMapper.updatePassword(userPasswordDTO);
        if (update < 1) {
            throw new ServiceException(Constants.CODE_600, "密码错误");
        }
    }

    @Override
    public Boolean pay(UserDTo userDTo) {
        int update=userMapper.updateUserMoney(userDTo);
        if (update<1){
            return false;
        }
        return true;
    }

    public SysUser getUserInfo(UserDTo user){
        QueryWrapper<SysUser> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("username",user.getUsername());
//        queryWrapper.eq("password",user.getPassword());
        SysUser one = null;
        try {
            one = getOne(queryWrapper);
        } catch (Exception e) {
            throw new ServiceException(Constants.CODE_500,"系统错误");
        }
        return one;
    }


    /**
     * 获取当前角色的菜单列表
     * @param rolename
     * @return
     */
    private List<SysMenu> getRoleMenus(String rolename){
        Integer roleId = roleMapper.selectByFlag(rolename);
        //            当前角色所有id集合
        List<Integer> menuIds = roleMenuMapper.selectByRoleId(roleId);

//            查出系统所有菜单
        List<SysMenu> menus = menuService.findMenus("");
//            new一个筛选完成之后的list
        List<SysMenu> roleMenus = new ArrayList<>();
//            筛选当前用户角色的菜单
        for (SysMenu menu:menus) {
            if (menuIds.contains(menu.getId())){
                roleMenus.add(menu);
            }
            List<SysMenu> children = menu.getChildren();
//                removeIf移除children里面不在menuIds集合中的元素
            children.removeIf(child->!menuIds.contains(child.getId()));

        }
        return roleMenus;
    }

}
