package com.graduation.service;

import com.graduation.entity.TComment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Mercy
 * @since 2022-04-21
 */
public interface ITCommentService extends IService<TComment> {

    List<TComment> findCommentDetail(Integer articleId);
}
