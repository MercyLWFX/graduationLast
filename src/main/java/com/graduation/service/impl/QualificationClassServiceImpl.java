package com.graduation.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.graduation.entity.QualificationClass;
import com.graduation.mapper.QualificationClassMapper;
import com.graduation.service.IQualificationClassService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Mercy
 * @since 2022-04-14
 */
@Service
public class QualificationClassServiceImpl extends ServiceImpl<QualificationClassMapper, QualificationClass> implements IQualificationClassService {

    @Resource
    private QualificationClassMapper qualificationClassMapper;

    @Override
    public Page<QualificationClass> findPage(Page<QualificationClass> page, String categoryName) {

        return qualificationClassMapper.findPage(page,categoryName);
    }

    @Override
    public List<QualificationClass> findAll() {
        return qualificationClassMapper.findAll();
    }
}
