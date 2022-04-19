package com.graduation.mapper;

import com.graduation.entity.Competition;
import com.graduation.entity.QualificationExam;
import com.graduation.entity.SysUser;
import com.graduation.entity.UserExam;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Mercy
 * @since 2022-04-18
 */
public interface UserExamMapper extends BaseMapper<UserExam> {

    List<QualificationExam> getQualificationListByUserId(@Param("userId") Long userId);

    @Delete("delete from user_exam where user_id=#{userId} and exam_id=#{examId}")
    Boolean delCart(@Param("userId") Long userId,@Param("examId") Long examId);

    @Update("update user_exam set ispay=1 where user_id=#{userId} and exam_id=#{examId}")
    int changeIsPay(@Param("userId") Long userId, @Param("examId") Long examId);

    List<QualificationExam> selectAllPay(Long userId);

    List<Competition> selectAllCompetition(Long userId);

    List<Competition> selectAllPayCompetition(Long userId);

    List<SysUser> selectAllApplicants(Long examId);
}
