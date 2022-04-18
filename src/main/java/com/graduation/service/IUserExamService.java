package com.graduation.service;

import com.graduation.entity.QualificationExam;
import com.graduation.entity.UserExam;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Mercy
 * @since 2022-04-18
 */
public interface IUserExamService extends IService<UserExam> {

    List<QualificationExam> selectByUserOrder(Long userId);

    Boolean delCart(Long userId, Long examId);
}
