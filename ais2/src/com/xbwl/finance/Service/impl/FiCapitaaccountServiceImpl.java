package com.xbwl.finance.Service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.entity.FiCapitaaccount;
import com.xbwl.finance.Service.IFiCapitaaccountService;
import com.xbwl.finance.dao.IFiCapitaaccountDao;

@Service("fiCapitaaccountServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class FiCapitaaccountServiceImpl extends BaseServiceImpl<FiCapitaaccount,Long> implements
		IFiCapitaaccountService {

	@Resource(name="fiCapitaaccountHibernateDaoImpl")
	private IFiCapitaaccountDao fiCapitaaccountDao;
	
	@Override
	public IBaseDAO getBaseDao() {
		return fiCapitaaccountDao;
	}


}
