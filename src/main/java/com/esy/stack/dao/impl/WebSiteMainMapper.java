package com.esy.stack.dao.impl;

import com.esy.stack.entity.WebSiteMain;

public interface WebSiteMainMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WebSiteMain record);

    int insertSelective(WebSiteMain record);

    WebSiteMain selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WebSiteMain record);

    int updateByPrimaryKey(WebSiteMain record);
}