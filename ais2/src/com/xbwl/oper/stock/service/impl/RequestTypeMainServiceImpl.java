package com.xbwl.oper.stock.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.entity.RequestTypeMain;
import com.xbwl.oper.stock.dao.IRequestTypeMainDao;
import com.xbwl.oper.stock.service.IRequestTypeMainService;

/**
 *@author LiuHao
 *@time Nov 30, 2011 5:13:53 PM
 */
@Service("requestTypeMainServiceImpl")
@Transactional(rollbackFor={Exception.class})
public class RequestTypeMainServiceImpl extends BaseServiceImpl<RequestTypeMain,Long> 
	implements IRequestTypeMainService{
	@Resource(name="requestTypeMainHibernateDaoImpl")
	private IRequestTypeMainDao requestTypeMainDao;
	@Override
	public IBaseDAO getBaseDao() {
		return requestTypeMainDao;
	}

}
