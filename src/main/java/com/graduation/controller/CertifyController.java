package com.graduation.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.graduation.common.Constants;
import com.graduation.common.Result;
import com.graduation.entity.SysUser;
import com.graduation.service.ISysUserService;
import com.graduation.utils.TokenUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.graduation.service.ICertifyService;
import com.graduation.entity.Certify;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Mercy
 * @since 2022-04-25
 */
@RestController
@RequestMapping("/certify")
public class CertifyController {

    @Resource
    private ICertifyService certifyService;
    @Resource
    private ISysUserService userService;

    // 新增或者更新
    @PostMapping("/update")
    public Result save(@RequestBody Certify certify) {
        QueryWrapper<Certify> certifyQueryWrapper=new QueryWrapper<>();
        certifyQueryWrapper.eq("user_id",certify.getUserId());
        certifyService.update(certify,certifyQueryWrapper);
        QueryWrapper<SysUser> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id",certify.getUserId());
        if (userService.getOne(queryWrapper)!=null&& certify.getAgree()){
            userService.changeUsername(userService.getOne(queryWrapper));
        }else if (userService.getOne(queryWrapper)!=null&& !certify.getAgree()){
            SysUser one = userService.getOne(queryWrapper);
            one.setRolename("ROLE_USER");
            userService.saveOrUpdate(one);
        }
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        certifyService.removeById(id);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Long> ids) {
        certifyService.removeByIds(ids);
        return Result.success();
    }

    @PostMapping("/submit")
    public Result submitCertify(@RequestBody Certify certify){
        certify.setUserId(TokenUtils.getCurrentUser().getId());
        if (certifyService.save(certify)){
            return Result.success();
        }else {
            return Result.error(Constants.CODE_500,"系统错误，请稍后再试");
        }
    }

    @GetMapping
    public Result findAll() {
        return Result.success(certifyService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(certifyService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        QueryWrapper<Certify> queryWrapper = new QueryWrapper<>();
        return Result.success(certifyService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

}

