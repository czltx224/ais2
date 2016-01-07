package com.xbwl.sys.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.entity.BasSpecialArea;
import com.xbwl.sys.dao.IBasSpecialAreaDao;
import com.xbwl.sys.service.IBasSpecialAreaService;

/**
 * author shuw
 * time Apr 12, 2012 3:48:56 PM
 */
@Service("basSpecialAreaServiceImpl")
@Transactional(rollbackFor={Exception.class})
public class BasSpecialAreaServiceImpl extends BaseServiceImpl<BasSpecialArea, Long>
						implements IBasSpecialAreaService{

	@Resource(name="basSpecialAreaHibernateDaoImpl")
	private IBasSpecialAreaDao basSpecialAreaDao;
	
	public IBaseDAO<BasSpecialArea, Long> getBaseDao() {
		return basSpecialAreaDao;
	}

}
