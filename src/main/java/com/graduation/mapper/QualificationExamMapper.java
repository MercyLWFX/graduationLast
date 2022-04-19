package com.graduation.mapper;

import com.graduation.entity.QualificationExam;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Mercy
 * @since 2022-04-14
 */
public interface QualificationExamMapper extends BaseMapper<QualificationExam> {

    @Select("select count from qualification_exam where id=#{examId}")
    int currentCount(Long examId);

    @Update("update qualification_exam set count=#{count} where id=#{examId}")
    void updateCurrentCount(@Param("examId") Long examId, @Param("count") int count);
}
