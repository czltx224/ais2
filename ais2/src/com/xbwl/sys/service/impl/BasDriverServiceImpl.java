package com.xbwl.sys.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.entity.BasDriver;
import com.xbwl.sys.dao.IBasDriverDao;
import com.xbwl.sys.service.IBasDriverService;

/**
 * author CaoZhili
 * time Jun 22, 2011 2:20:27 PM
 */
@Service("basDriverServiceImpl")
@Transactional(rollbackFor={Exception.class})
public class BasDriverServiceImpl extends BaseServiceImpl<BasDriver,Long> implements
		IBasDriverService {

	@Resource(name="basDriverHibernateDaoImpl")
	private IBasDriverDao basDriverDao;
	
	@Override
	public IBaseDAO<BasDriver, Long> getBaseDao() {
		return this.basDriverDao;
	}

	

}
