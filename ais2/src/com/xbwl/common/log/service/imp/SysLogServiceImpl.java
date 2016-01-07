package com.xbwl.common.log.service.imp;
// Generated 2010-11-26 11:23:16 by Hibernate Tools 3.2.1.GA


import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.log.dao.ISysLogDAO;
import com.xbwl.common.log.service.ISysLogService;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.entity.SysLog;


/**
 * 日志操作Service层实现类
 * 
 * @author yab
 */

@Service("sysLogService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
public class SysLogServiceImpl extends BaseServiceImpl< SysLog,Long> implements
		ISysLogService {

	@Resource(name="sysLogHibernate")
	private ISysLogDAO sysLogDao;

	public ISysLogDAO getBaseDao() {
		return sysLogDao;
	}
	
	/**
	 * 重写保存日志方法
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
	public void save(SysLog sysLog){
		sysLogDao.save(sysLog);
	}
}
