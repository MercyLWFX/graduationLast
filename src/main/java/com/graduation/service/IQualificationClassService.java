package com.graduation.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.graduation.entity.QualificationClass;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Mercy
 * @since 2022-04-14
 */
public interface IQualificationClassService extends IService<QualificationClass> {

    Page<QualificationClass> findPage(Page<QualificationClass> objectPage, String categoryName);
}
