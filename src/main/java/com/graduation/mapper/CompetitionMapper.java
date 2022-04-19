package com.graduation.mapper;

import com.graduation.entity.Competition;
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
 * @since 2022-04-19
 */
public interface CompetitionMapper extends BaseMapper<Competition> {

    @Select("select count from competition where id=#{examId}")
    int currentCount(Long examId);

    @Update("update competition set count=#{count} where id=#{examId}")
    void updateCurrentCount(@Param("examId") Long examId,@Param("count") int count);
}
