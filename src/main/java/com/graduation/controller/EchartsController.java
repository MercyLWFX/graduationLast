package com.graduation.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Quarter;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.graduation.common.Constants;
import com.graduation.common.Result;
import com.graduation.entity.Competition;
import com.graduation.entity.Files;
import com.graduation.entity.QualificationExam;
import com.graduation.entity.SysUser;
import com.graduation.mapper.CompetitionMapper;
import com.graduation.mapper.FilesMapper;

import com.graduation.mapper.QualificationExamMapper;
import com.graduation.mapper.SysUserMapper;
import com.graduation.service.ISysUserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/echarts")
public class EchartsController {

    @Autowired
    private ISysUserService userService;

    @Resource
    private FilesMapper fileMapper;

    @Resource
    private QualificationExamMapper examMapper;

    @Resource
    private CompetitionMapper competitionMapper;

    @Resource
    private SysUserMapper userMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/userCount")
    public Result getUserCount(){
        QueryWrapper<SysUser> queryWrapper=new QueryWrapper<>();
        return Result.success(userMapper.selectCount(queryWrapper));
    }

    @GetMapping("/examCount")
    public Result getExamCount(){
        QueryWrapper<QualificationExam> queryWrapper=new QueryWrapper<>();
        return Result.success(examMapper.selectCount(queryWrapper));
    }

    @GetMapping("/competitionCount")
    public Result getCompetitionCount(){
        QueryWrapper<Competition> queryWrapper=new QueryWrapper<>();
        return Result.success(competitionMapper.selectCount(queryWrapper));
    }

    @GetMapping("/example")
    public Result get() {
        Map<String, Object> map = new HashMap<>();
        map.put("x", CollUtil.newArrayList("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"));
        map.put("y", CollUtil.newArrayList(150, 230, 224, 218, 135, 147, 260));
        return Result.success(map);
    }

    @GetMapping("/members")
    public Result members() {
        List<SysUser> list = userService.list();
        int q1 = 0; // 第一季度
        int q2 = 0; // 第二季度
        int q3 = 0; // 第三季度
        int q4 = 0; // 第四季度
        for (SysUser user : list) {
            Date createTime = user.getCreateTime();
            Quarter quarter = DateUtil.quarterEnum(createTime);
            switch (quarter) {
                case Q1: q1 += 1; break;
                case Q2: q2 += 1; break;
                case Q3: q3 += 1; break;
                case Q4: q4 += 1; break;
                default: break;
            }
        }
        return Result.success(CollUtil.newArrayList(q1, q2, q3, q4));
    }
//
//    @AuthAccess
//    @GetMapping("/file/front/all")
////    @Cacheable(value = "files" ,key = "'frontAll'")
//    public Result frontAll() {
//        // 1. 从缓存获取数据
//        String jsonStr = stringRedisTemplate.opsForValue().get(Constants.FILES_KEY);
//        List<Files> files;
//        if (StrUtil.isBlank(jsonStr)) {  // 2. 取出来的json是空的
//            files = fileMapper.selectList(null);  // 3. 从数据库取出数据
//            // 4. 再去缓存到redis
//            stringRedisTemplate.opsForValue().set(Constants.FILES_KEY, JSONUtil.toJsonStr(files));
//        } else {
//            // 减轻数据库的压力
//            // 5. 如果有, 从redis缓存中获取数据
//            files = JSONUtil.toBean(jsonStr, new TypeReference<List<Files>>() {
//            }, true);
//        }
//        return Result.success(files);
//    }

}
