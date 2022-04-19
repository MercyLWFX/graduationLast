package com.graduation.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.graduation.common.Result;
import com.graduation.utils.TokenUtils;
import io.swagger.models.auth.In;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.graduation.service.ITCommentService;
import com.graduation.entity.TComment;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Mercy
 * @since 2022-04-21
 */
@RestController
@RequestMapping("/comment")
public class TCommentController {

    @Resource
    private ITCommentService tCommentService;

    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody TComment comment) {
        if (comment.getId() == null) { // 新增评论
            comment.setUserId(TokenUtils.getCurrentUser().getId());
            comment.setTime(DateUtil.now());

            if (comment.getPid() != null) {  // 判断如果是回复，进行处理
                Integer pid = comment.getPid();
                TComment pComment = tCommentService.getById(pid);
                if (pComment.getOriginId() != null) {  // 如果当前回复的父级有祖宗，那么就设置相同的祖宗
                    comment.setOriginId(pComment.getOriginId());
                } else {  // 否则就设置父级为当前回复的祖宗
                    comment.setOriginId(comment.getPid());
                }
            }

        }
        tCommentService.saveOrUpdate(comment);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        tCommentService.removeById(id);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        tCommentService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        return Result.success(tCommentService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(tCommentService.getById(id));
    }

    @GetMapping("/tree/{articleId}")
    public Result findTree(@PathVariable Integer articleId) {
        List<TComment> articleComments = tCommentService.findCommentDetail(articleId);  // 查询所有的评论和回复数据
        // 查询评论数据（不包括回复）
        List<TComment> originList = articleComments.stream().filter(comment -> comment.getOriginId() == null).collect(Collectors.toList());

        // 设置评论数据的子节点，也就是回复内容
        for (TComment origin : originList) {
//            .equals(comment.getOriginId())
            List<TComment> comments = articleComments.stream().filter(comment -> origin.getId().equals(comment.getOriginId())).collect(Collectors.toList());  // 表示回复对象集合
            comments.forEach(comment -> {
                Optional<TComment> pComment = articleComments.stream().filter(c1 -> c1.getId().equals(comment.getPid())).findFirst();  // 找到当前评论的父级
                pComment.ifPresent((v -> {  // 找到父级评论的用户id和用户昵称，并设置给当前的回复对象
                    comment.setPUserId(v.getUserId());
                    comment.setPNickname(v.getNickname());
                }));
            });
            origin.setChildren(comments);
        }
        return Result.success(originList);
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        QueryWrapper<TComment> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return Result.success(tCommentService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

}

