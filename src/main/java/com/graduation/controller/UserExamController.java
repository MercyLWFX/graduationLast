package com.graduation.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.graduation.common.Constants;
import com.graduation.common.Result;
import com.graduation.config.AuthAccess;
import com.graduation.entity.SysUser;
import com.graduation.utils.TokenUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.graduation.service.IUserExamService;
import com.graduation.entity.UserExam;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Mercy
 * @since 2022-04-18
 */
@RestController
@RequestMapping("/sign")
public class UserExamController {

    @Resource
    private IUserExamService userExamService;

    @GetMapping("/pre")
    public Result pre(@RequestParam Long userId,
                      @RequestParam Long examId,
                      @RequestParam Boolean ispay) {
        UserExam userExam = new UserExam();
        userExam.setUserId(userId);
        userExam.setExamId(examId);
        userExam.setIspay(ispay);
        System.out.println("------------" + userExam);
        QueryWrapper<UserExam> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("exam_id", examId);
        if (userExamService.getOne(queryWrapper) == null) {
            userExamService.save(userExam);
            return Result.success();
        } else {
            return Result.error(Constants.CODE_600, "已加入预报名,不要重复添加");
        }
    }


    @PostMapping("/changeList")
    public Result changeList(@RequestBody List<Long> ids) {
        SysUser sysUser = TokenUtils.getCurrentUser();
        Long userId = sysUser.getId();
        for (Long examId : ids) {
            if (userExamService.changeIsPay(userId, examId)) {
                continue;
            } else {
                return Result.error(Constants.CODE_500, "服务器错误，请稍后再试");
            }
        }
        return Result.success();
    }

    @GetMapping("/changeOne")
    public Result changeOne(@RequestParam Long examId) {
        SysUser sysUser = TokenUtils.getCurrentUser();
        Long userId = sysUser.getId();

        if (userExamService.changeIsPay(userId, examId)) {
            return Result.success();
        } else {
            return Result.error(Constants.CODE_500, "服务器错误，请稍后再试");
        }
    }

    /**
     * 购物车数据
     *
     * @param userId
     * @return
     */
    @AuthAccess
    @GetMapping("/prepay")
    public Result isPre(@RequestParam Long userId) {
        return Result.success(userExamService.selectByUserOrder(userId));
    }

    @GetMapping("/prepayII")
    public Result isPreII(@RequestParam Long userId){
        return Result.success(userExamService.selectByUserCompetitionOrder(userId));
    }

    @GetMapping("/dopay")
    public Result isPay(@RequestParam Long userId) {
        return Result.success(userExamService.seleceAllPay(userId));
    }

    @GetMapping("/payCompetition")
    public Result isPayCompetition(@RequestParam Long userId){
        return Result.success(userExamService.seleceAllPayCompetition(userId));
    }


    @DeleteMapping("/del")
    public Result delete(@RequestParam Long userId, @RequestParam Long examId) {
        Boolean flag = userExamService.delCart(userId, examId);
        if (flag) {
            return Result.success();
        } else {
            return Result.error(Constants.CODE_500, "服务器内部错误,请稍后重试");
        }

    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Long> ids) {
//        userExamService.removeByIds(ids);
        SysUser sysUser = TokenUtils.getCurrentUser();
        Long useId = sysUser.getId();
        for (Long num : ids) {
            QueryWrapper<UserExam> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id", useId);
            queryWrapper.eq("exam_id", num);
            userExamService.remove(queryWrapper);
        }
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        return Result.success(userExamService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(userExamService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        QueryWrapper<UserExam> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return Result.success(userExamService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

}

