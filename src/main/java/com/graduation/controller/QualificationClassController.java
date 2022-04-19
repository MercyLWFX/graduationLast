package com.graduation.controller;


import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.graduation.common.Constants;
import com.graduation.common.Result;
import com.graduation.config.AuthAccess;
import com.graduation.entity.SysUser;
import com.graduation.service.IQualificationClassService;
import com.graduation.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.graduation.entity.QualificationClass;

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
@RequestMapping("/classes")
public class QualificationClassController {

    @Resource
    private IQualificationClassService classService;

    @Autowired
    private StringRedisTemplate redisTemplate;


    // 新增或者更新
//    @PostMapping
//    public Result save(@RequestBody QualificationClass qualificationClass) {
//        classService.saveOrUpdate(qualificationClass);
//        return Result.success();
//    }

    @DeleteMapping("/del/{id}")
    public Result delete(@PathVariable Integer id) {
        if (classService.removeById(id)){
            return Result.success();
        }else {
            return Result.error(Constants.CODE_600,"id不存在");
        }

    }

    @PostMapping("/batchdel")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        classService.removeByIds(ids);
        return Result.success();
    }

    @AuthAccess
    @GetMapping("/all")
    public Result findAll() {
        //        1.从缓存中获取数据
        String jsonStr = redisTemplate.opsForValue().get(Constants.CLASS_KEY);
        List<QualificationClass> qualificationClasses;
        if (StrUtil.isBlank(jsonStr)){
//            2.取出来的json是空的
            qualificationClasses=  classService.findAll();
//            3从数据库取出数据之后，
//            4.再去缓存到redis
            redisTemplate.opsForValue().set(Constants.CLASS_KEY,JSONUtil.toJsonStr(qualificationClasses));
        }else {
//            5.从redis缓存中获取数据
            qualificationClasses = JSONUtil.toBean(jsonStr, new TypeReference<List<QualificationClass>>() {
            }, true);
        }

        return Result.success(qualificationClasses);
//        return Result.success(classService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(classService.getById(id));
    }

    @PostMapping("/insert")
    public Result saveUser(@RequestBody QualificationClass user) {

//        System.out.println("======================================"+user.toString());

        QueryWrapper<QualificationClass> wrapper = new QueryWrapper<>();
        wrapper.eq("category_name", user.getCategoryName());
        wrapper.eq("ascription", user.getAscription());
        if (classService.getOne(wrapper) != null && user.getCategoryId() == null) {
            return Result.error(Constants.CODE_401,"部门名字或部门归属类别代码已被占用");
        }
        classService.saveOrUpdate(user);
        return Result.success();
    }

    @AuthAccess
    @GetMapping("/pages")
//    @Cacheable(value = "QualificationClass",key="#categoryId")
    public Result findPages(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize,
                                    @RequestParam(required = false) String categoryName
                                   
    ) {



        return Result.success(classService.findPage(new Page<>(pageNum,pageSize),categoryName));
    }

}

