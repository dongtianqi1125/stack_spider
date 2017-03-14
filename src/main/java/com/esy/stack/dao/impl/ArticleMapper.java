package com.esy.stack.dao.impl;

import com.esy.stack.entity.Article;
import com.esy.stack.entity.ArticleWithBLOBs;

public interface ArticleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ArticleWithBLOBs record);

    int insertSelective(ArticleWithBLOBs record);

    ArticleWithBLOBs selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ArticleWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(ArticleWithBLOBs record);

    int updateByPrimaryKey(Article record);
}