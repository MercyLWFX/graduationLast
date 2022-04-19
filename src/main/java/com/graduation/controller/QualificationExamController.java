package com.graduation.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.graduation.common.Constants;
import com.graduation.common.Result;
import com.graduation.config.AuthAccess;
import com.graduation.entity.QualificationClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.graduation.service.IQualificationExamService;
import com.graduation.entity.QualificationExam;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Mercy
 * @since 2022-04-14
 */
@RestController
@RequestMapping("/exam")
public class QualificationExamController {

    @Resource
    private IQualificationExamService examService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        if (examService.removeById(id)){
            flushRedis(Constants.CLASS_KEY);
            return Result.success();
        }else {
            return Result.error(Constants.CODE_600,"此项不存在");
        }


    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        if (examService.removeByIds(ids)){
            flushRedis(Constants.CLASS_KEY);
            return Result.success();
        }else {
            return Result.error(Constants.CODE_600,"参数错误");
        }
    }

    @GetMapping("/allExam")
    @AuthAccess
    public Result findAll() {
        return Result.success(examService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(examService.getById(id));
    }


    @AuthAccess
    @GetMapping("/detail/{examName}")
    public Result findDetail(@PathVariable String examName){
        QueryWrapper<QualificationExam> wrapper = new QueryWrapper<>();
        wrapper.eq("exam_name", examName);
        if (examService.getOne(wrapper)!=null){
            return Result.success(examService.getOne(wrapper));
        }else {
            return Result.error(Constants.CODE_600,"no found");
        }
    }

    @PostMapping("/insert")
    public Result saveUser(@RequestBody QualificationExam user) {

//        System.out.println("======================================"+user.toString());

        QueryWrapper<QualificationExam> wrapper = new QueryWrapper<>();
        wrapper.eq("exam_name", user.getExamName());
        wrapper.eq("ascription", user.getAscription());
        if (examService.getOne(wrapper) != null && user.getId() == null) {
            return Result.error(Constants.CODE_401,"名字被占用已被占用");
        }
        examService.saveOrUpdate(user);
        flushRedis(Constants.CLASS_KEY);
        return Result.success();
    }

    @AuthAccess
    @GetMapping("/pages")
    public IPage<QualificationExam> findPages(@RequestParam Integer pageNum,
                                               @RequestParam Integer pageSize,
                                               @RequestParam(required = false) String examName,
                                              @RequestParam(required = false) String ascription
    ) {
        IPage<QualificationExam> page = new Page<>(pageNum, pageSize);
        QueryWrapper<QualificationExam> queryWrapper = new QueryWrapper<>();


        if (examName != null) {
            queryWrapper.eq("exam_name", examName);
        }

        if (ascription != null) {
            queryWrapper.eq("ascription", ascription);
        }

        IPage<QualificationExam> userIPage = examService.page(page, queryWrapper);
//        System.out.println(userIPage);

        return userIPage;
    }

//    删除缓存
    private void flushRedis(String key){
        redisTemplate.delete(key);
    }

}

