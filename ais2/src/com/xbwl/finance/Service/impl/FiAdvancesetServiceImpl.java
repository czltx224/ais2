package com.xbwl.finance.Service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.entity.FiAdvanceset;
import com.xbwl.finance.Service.IFiAdvancesetServie;
import com.xbwl.finance.dao.IFiAdvancesetDao;

@Service("fiAdvancesetServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class FiAdvancesetServiceImpl extends
		BaseServiceImpl<FiAdvanceset, Long> implements IFiAdvancesetServie {

	@Resource(name = "fiAdvancesetHibernateDaoImpl")
	private IFiAdvancesetDao fiAdvancesetDao;

	@Override
	public IBaseDAO getBaseDao() {
		return this.fiAdvancesetDao;
	}

}
