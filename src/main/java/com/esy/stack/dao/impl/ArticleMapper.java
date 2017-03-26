package com.esy.stack.dao.impl;

import com.esy.stack.dao.BaseDao;
import com.esy.stack.entity.Article;
import com.esy.stack.entity.Article;

public interface ArticleMapper extends BaseDao<Article> {
    int deleteByPrimaryKey(Long id);

    int insert(Article record);

    int insertSelective(Article record);

    Article selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Article record);

    int updateByPrimaryKey(Article record);

    String selectCharSetByArticleId(long id);

    int countWaitArticle(Article article);
}