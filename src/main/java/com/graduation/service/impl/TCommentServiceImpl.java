package com.graduation.service.impl;

import com.graduation.entity.TComment;
import com.graduation.mapper.TCommentMapper;
import com.graduation.service.ITCommentService;
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
 * @since 2022-04-21
 */
@Service
public class TCommentServiceImpl extends ServiceImpl<TCommentMapper, TComment> implements ITCommentService {

    @Resource
    private TCommentMapper commentMapper;

    @Override
    public List<TComment> findCommentDetail(Integer articleId) {
        return commentMapper.findCommentDetail(articleId);
    }
}
