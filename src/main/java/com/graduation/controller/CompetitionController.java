package com.graduation.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.graduation.common.Constants;
import com.graduation.common.Result;
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
        competition.setId(idWorker.nextId());
        SysUser user= TokenUtils.getCurrentUser();
        competition.setUserId(user.getId());
        if (competitionService.saveOrUpdate(competition)){
            return Result.success();
        }else {
            return Result.error(Constants.CODE_500,"请稍后重试");
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
                           @RequestParam Integer pageSize) {
        QueryWrapper<Competition> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return Result.success(competitionService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

}

