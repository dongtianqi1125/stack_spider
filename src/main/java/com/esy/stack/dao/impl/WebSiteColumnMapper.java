package com.esy.stack.dao.impl;

import com.esy.stack.dao.BaseDao;
import com.esy.stack.entity.WebSiteColumn;

public interface WebSiteColumnMapper extends BaseDao<WebSiteColumn>{
    int deleteByPrimaryKey(Integer id);

    int insert(WebSiteColumn record);

    int insertSelective(WebSiteColumn record);

    WebSiteColumn selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WebSiteColumn record);

    int updateByPrimaryKey(WebSiteColumn record);

    int updateStatusById(WebSiteColumn record);
}