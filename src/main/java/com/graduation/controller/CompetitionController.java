package com.graduation.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.graduation.common.Constants;
import com.graduation.common.Result;
import com.graduation.config.AuthAccess;
import com.graduation.entity.QualificationExam;
import com.graduation.entity.SysUser;
import com.graduation.utils.SnowflakeIdWorker;
import com.graduation.utils.TokenUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.graduation.service.ICompetitionService;
import com.graduation.entity.Competition;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Mercy
 * @since 2022-04-19
 */
@RestController
@RequestMapping("/competition")
public class CompetitionController {

    @Resource
    private ICompetitionService competitionService;

    SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);

    // 新增或者更新
    @PostMapping("/insert")
    public Result save(@RequestBody Competition competition) {
        if (competition.getId() == null) {
            competition.setId(idWorker.nextId());
            SysUser user = TokenUtils.getCurrentUser();
            competition.setUserId(user.getId());
        }
        if (competitionService.saveOrUpdate(competition)) {
            return Result.success();
        } else {
            return Result.error(Constants.CODE_500, "请稍后重试");
        }
    }

    @AuthAccess
    @GetMapping("/detail/{id}")
    public Result findDetail(@PathVariable Long id){
        QueryWrapper<Competition> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);
        if (competitionService.getOne(wrapper)!=null){
            return Result.success(competitionService.getOne(wrapper));
        }else {
            return Result.error(Constants.CODE_600,"no found");
        }
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        competitionService.removeById(id);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        competitionService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        return Result.success(competitionService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(competitionService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(required = false) String name,
                           @RequestParam(required = false) String types) {
//        SysUser sysUser = TokenUtils.getCurrentUser();
        QueryWrapper<Competition> queryWrapper = new QueryWrapper<>();
        if (name != null) {
            queryWrapper.like("name", name);
        }
        if (types != null) {
            queryWrapper.eq("types", types);
        }
//        if (!sysUser.getRolename().equals("ROLE_ADMIN")){
////                如果当前登录的人不是admin，则竞赛信息只能查看本人发布的
////                只有admin才能查看所有的竞赛信息
//            queryWrapper.eq("user_id", sysUser.getId());
//        }

        return Result.success(competitionService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }


    @GetMapping("/pages")
    public Result findPages(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(required = false) String name,
                           @RequestParam(required = false) String types) {
        SysUser sysUser = TokenUtils.getCurrentUser();
        QueryWrapper<Competition> queryWrapper = new QueryWrapper<>();
        if (name != null) {
            queryWrapper.eq("name", name);

        }
        if (types != null) {
            queryWrapper.eq("types", types);
        }
        if (!sysUser.getRolename().equals("ROLE_ADMIN")){
//                如果当前登录的人不是admin，则竞赛信息只能查看本人发布的
//                只有admin才能查看所有的竞赛信息
            queryWrapper.eq("user_id", sysUser.getId());
        }

        return Result.success(competitionService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

    @GetMapping("id")
    public Result selectById(@RequestParam Long userId) {
        QueryWrapper<Competition> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        return Result.success(competitionService.list(queryWrapper));
    }
}

