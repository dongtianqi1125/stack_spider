package com.esy.stack.dao.impl;

import com.esy.stack.entity.ArticleDetail;
import com.esy.stack.entity.ArticleDetailWithBLOBs;

public interface ArticleDetailMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ArticleDetailWithBLOBs record);

    int insertSelective(ArticleDetailWithBLOBs record);

    ArticleDetailWithBLOBs selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ArticleDetailWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(ArticleDetailWithBLOBs record);

    int updateByPrimaryKey(ArticleDetail record);
}