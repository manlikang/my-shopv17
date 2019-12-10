package com.fuhan.commons.base;

import com.github.pagehelper.PageInfo;

import java.util.List;

public interface IBaseService<T> {

    int deleteByPrimaryKey(Integer id);

    int insert(T t);

    int insertSelective(T t);

    T selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(T t);

    int updateByPrimaryKey(T t);

    List<T> getList();
    PageInfo<T> getListByPage(int currentPage, int pageSize);
}
