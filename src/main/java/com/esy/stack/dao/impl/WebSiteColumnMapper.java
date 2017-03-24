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

    int updateStatus(WebSiteColumn record);

    /**
     * 检查所有栏目是否都有处理
     * <p>用于最后改变栏目状态为未处理前的判断</p>
     * @return 0 false 1 true
     */
    int checkIfAllHandler();
}