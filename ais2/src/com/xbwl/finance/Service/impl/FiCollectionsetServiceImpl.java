package com.xbwl.finance.Service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.orm.Page;
import com.xbwl.common.orm.PropertyFilter;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.entity.FiCollectionset;
import com.xbwl.finance.Service.IFiCollectionsetService;
import com.xbwl.finance.dao.IFiCollectionsetDao;
import com.xbwl.finance.dao.IFiProblemreceivableDao;

@Service("fiCollectionsetServiceImpl")
@Transactional(rollbackFor={Exception.class})
public class FiCollectionsetServiceImpl extends BaseServiceImpl<FiCollectionset,Long> implements
		IFiCollectionsetService {
	
	@Resource(name="fiCollectionsetHibernateDaoImpl")
	private IFiCollectionsetDao fiCollectionsetDao;
	
	@Override
	public IBaseDAO getBaseDao() {
		return fiCollectionsetDao;
	}
}
