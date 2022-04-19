package com.graduation.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.graduation.common.Constants;
import com.graduation.common.Result;
import com.graduation.config.AuthAccess;
import com.graduation.controller.dto.UserScore;
import com.graduation.entity.SysUser;
import com.graduation.utils.TokenUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    @GetMapping("/statistics")
    public Result statistics(@RequestParam Long examId){
        Double avg=userExamService.avg(examId);
//        Double mode=userExamService.mode(examId);
        Double max=userExamService.max(examId);
        Double min=userExamService.min(examId);
        Map<String,Double> info=new HashMap<>();
        info.put("avg",avg);
        info.put("max",max);
        info.put("min",min);
        return Result.success(info);
    }

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

    @GetMapping("/publish")
    public Result publish(@RequestParam Long examId){
        return Result.success(userExamService.getApplicants(examId));
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

    @PostMapping("/scores")
    public Result scores(@RequestBody List<UserScore> scores){
        UserExam userExam=new UserExam();
        for (UserScore score:scores) {
            QueryWrapper<UserExam> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("user_id",score.getId());
            queryWrapper.eq("exam_id",score.getExamId());
            userExam.setScore(score.getScore());
            if (!userExamService.update(userExam,queryWrapper)){
                return Result.error(Constants.CODE_500,"后台错误，请稍后重试");
            }
        }
        return Result.success();
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

    @GetMapping("/check/{id}")
    public Result check(@PathVariable Long id) {
        SysUser sysUser=TokenUtils.getCurrentUser();
        Long userId=sysUser.getId();
        QueryWrapper<UserExam> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        queryWrapper.eq("exam_id",id);
        if (userExamService.getOne(queryWrapper)!=null){
            queryWrapper.eq("ispay",1);
            if (userExamService.getOne(queryWrapper)!=null){
                return Result.error(Constants.CODE_600,"不要重复报名");
            }else {
                QueryWrapper<UserExam> wrapper=new QueryWrapper<>();
                wrapper.eq("user_id",userId);
                wrapper.eq("exam_id",id);
                return Result.success(userExamService.getOne(wrapper));
            }
        }else {
            return Result.success();
        }
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        QueryWrapper<UserExam> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return Result.success(userExamService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

}

