package com.graduation.mapper;

import com.graduation.entity.TComment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Mercy
 * @since 2022-04-21
 */
public interface TCommentMapper extends BaseMapper<TComment> {


    @Select("select c.*,u.nickname,u.avatar_url from t_comment c left join sys_user u on c.user_id = u.id " +
            "where c.article_id = #{articleId} order by id desc")
    List<TComment> findCommentDetail(@Param("articleId") Integer articleId);
}
