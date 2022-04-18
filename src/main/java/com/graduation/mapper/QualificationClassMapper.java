package com.graduation.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.graduation.entity.QualificationClass;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Mercy
 * @since 2022-04-14
 */
public interface QualificationClassMapper extends BaseMapper<QualificationClass> {

    Page<QualificationClass> findPage(Page<QualificationClass> page, @Param("categoryName") String categoryName);
}
