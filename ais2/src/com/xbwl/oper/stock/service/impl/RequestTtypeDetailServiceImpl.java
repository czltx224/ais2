package com.xbwl.oper.stock.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.entity.RequestTypeDetail;
import com.xbwl.oper.stock.dao.IRequestTypeDetailDao;
import com.xbwl.oper.stock.service.IRequestTypeDetailService;

/**
 *@author LiuHao
 *@time Nov 30, 2011 5:16:11 PM
 */
@Service("requestTtypeDetailServiceImpl")
@Transactional(rollbackFor={Exception.class})
public class RequestTtypeDetailServiceImpl extends BaseServiceImpl<RequestTypeDetail,Long> implements
		IRequestTypeDetailService {
	@Resource(name="requestTypeDetailHibernateDaoImpl")
	private IRequestTypeDetailDao requestTypeDetailDao;
	@Override
	public IBaseDAO getBaseDao() {
		return requestTypeDetailDao;
	}
}
