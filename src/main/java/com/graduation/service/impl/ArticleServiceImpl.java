package com.graduation.service.impl;

import com.graduation.entity.Article;
import com.graduation.mapper.ArticleMapper;
import com.graduation.service.IArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Mercy
 * @since 2022-04-21
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {

}
