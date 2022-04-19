package com.graduation.service.impl;

import com.graduation.entity.Competition;
import com.graduation.entity.QualificationExam;
import com.graduation.entity.UserExam;
import com.graduation.mapper.UserExamMapper;
import com.graduation.service.IUserExamService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Mercy
 * @since 2022-04-18
 */
@Service
public class UserExamServiceImpl extends ServiceImpl<UserExamMapper, UserExam> implements IUserExamService {

    @Resource
    private UserExamMapper userExamMapper;

    @Override
    public List<QualificationExam> selectByUserOrder(Long userId) {
//        得到用户已经预报名比赛目录的信息
        return userExamMapper.getQualificationListByUserId(userId);
    }

    @Override
    public Boolean delCart(Long userId, Long examId) {
        return userExamMapper.delCart(userId,examId);
    }

    @Override
    public Boolean changeIsPay(Long userId, Long examId) {
        if (userExamMapper.changeIsPay(userId,examId)>0){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 用于用户已经支付的订单项目查询
     * @param userId
     * @return
     */
    @Override
    public List<QualificationExam> seleceAllPay(Long userId) {
        return userExamMapper.selectAllPay(userId);
    }

    @Override
    public List<Competition> selectByUserCompetitionOrder(Long userId) {
        return userExamMapper.selectAllCompetition(userId);
    }

    @Override
    public List<Competition> seleceAllPayCompetition(Long userId) {
        return userExamMapper.selectAllPayCompetition(userId);
    }
}
