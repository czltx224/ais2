package com.xbwl.report.print.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.entity.SysPrintRight;
import com.xbwl.report.print.dao.ISysPrintRightDao;
import com.xbwl.report.print.service.ISysPrintRightService;

/**
 * @author Administrator
 * @createTime 6:37:52 PM
 * @updateName Administrator
 * @updateTime 6:37:52 PM
 * 
 */

@Service("sysPrintRightServiceImpl")
@Transactional(rollbackFor=Exception.class)
public class SysPrintRightServiceImpl extends BaseServiceImpl<SysPrintRight,Long> implements
		ISysPrintRightService {
	
	@Resource(name="sysPrintRightHibernateImpl")
	private ISysPrintRightDao sysPrintRightDaoImpl;

	@Override
	public IBaseDAO getBaseDao() {
		return sysPrintRightDaoImpl;
	}

}
