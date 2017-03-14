package com.esy.stack.dao;

import com.github.pagehelper.Page;

public interface BaseDao<T> {

	Page<T> select(T record);
}
