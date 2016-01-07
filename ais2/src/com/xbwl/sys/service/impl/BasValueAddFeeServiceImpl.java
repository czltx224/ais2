package com.xbwl.sys.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.entity.BasValueAddFee;
import com.xbwl.sys.dao.IBasValueAddFeeDao;
import com.xbwl.sys.service.IBasValueAddFeeService;

/**
 * author CaoZhili
 * time Jun 28, 2011 4:37:46 PM
 */
@Service("basValueAddFeeServiceImpl")
@Transactional(rollbackFor={Exception.class})
public class BasValueAddFeeServiceImpl extends BaseServiceImpl<BasValueAddFee,Long> implements IBasValueAddFeeService {

	@Resource(name="basValueAddFeeHibernateDaoImpl")
	private IBasValueAddFeeDao basValueAddFeeDao;
	
	@Override
	public IBaseDAO<BasValueAddFee, Long> getBaseDao() {
		
		return this.basValueAddFeeDao;
	}
	
}
