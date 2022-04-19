package com.graduation.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.graduation.common.Result;
import com.graduation.utils.TokenUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.graduation.service.IApplicationFormService;
import com.graduation.entity.ApplicationForm;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Mercy
 * @since 2022-05-03
 */
@RestController
@RequestMapping("/form")
public class ApplicationFormController {

    @Resource
    private IApplicationFormService applicationFormService;

    // 新增或者更新
    @PostMapping("/insert")
    public Result save(@RequestBody ApplicationForm applicationForm) {
        applicationForm.setId(TokenUtils.getCurrentUser().getId());
        if (applicationFormService.saveOrUpdate(applicationForm)){
            return Result.success();
        }else {
            return Result.error();
        }
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Long id) {
        return Result.success(applicationFormService.getById(id));
    }
}

