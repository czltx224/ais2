package com.xbwl.sys.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.entity.BasCar;
import com.xbwl.sys.dao.IBasCarDao;
import com.xbwl.sys.service.IBasCarService;

/**
 * author CaoZhili
 *time Jun 18, 2011 10:57:36 AM
 */
@Service("basCarServiceImpl")
@Transactional(rollbackFor={Exception.class})
public class BasCarServiceImpl extends BaseServiceImpl<BasCar,Long> implements IBasCarService {

	
	@Resource(name="basCarHibernateDaoImpl")
	private IBasCarDao basCarDao;
	
	@Override
	public IBaseDAO<BasCar, Long> getBaseDao() {
		return this.basCarDao;
	}
}
