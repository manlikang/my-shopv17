package com.fuhan.mapper;

import com.fuhan.commons.base.IBaseDao;
import com.fuhan.entity.ErrorEmail;

import java.util.List;

public interface ErrorEmailMapper extends IBaseDao<ErrorEmail> {

    int updateStatus(ErrorEmail errorEmail);

    List<ErrorEmail> selectEmailByRetryNum();

}