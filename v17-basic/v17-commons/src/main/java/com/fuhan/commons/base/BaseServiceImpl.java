package com.fuhan.commons.base;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author : FuHan
 * @description : ***
 * @date: 2019/10/29
 */
public abstract   class BaseServiceImpl<T> implements IBaseService<T> {

    public abstract IBaseDao<T> getDao();

    public int deleteByPrimaryKey(Integer id) {
        return getDao().deleteByPrimaryKey(id);
    }

    public int insert(T t) {
        return getDao().insert(t);
    }

    public int insertSelective(T t) {
        return getDao().insertSelective(t);
    }

    public T selectByPrimaryKey(Integer id) {
        return getDao().selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(T t) {
        return getDao().updateByPrimaryKeySelective(t);
    }

    public int updateByPrimaryKey(T t) {
        return getDao().updateByPrimaryKey(t);
    }

    public List<T> getList() {
        return getDao().getList();
    }

    public PageInfo<T> getListByPage(int currentPage, int pageSize) {
        PageHelper.startPage(currentPage,pageSize);
        List<T> list = getDao().getList();
        return new PageInfo<T>(list);
    }


}
