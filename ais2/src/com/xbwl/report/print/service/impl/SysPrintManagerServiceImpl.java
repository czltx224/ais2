package com.xbwl.report.print.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.ralasafe.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.entity.SysPrintManager;
import com.xbwl.report.print.dao.ISysPrintManagerDao;
import com.xbwl.report.print.service.ISysPrintManagerService;

/**
 * @author Administrator
 * @createTime 2:23:09 PM
 * @updateName Administrator
 * @updateTime 2:23:09 PM
 * 
 */

@Service("sysPrintManagerServiceImpl")
@Transactional(rollbackFor=Exception.class)
public class SysPrintManagerServiceImpl extends BaseServiceImpl<SysPrintManager,Long> implements ISysPrintManagerService{
	

	@Resource(name="sysPrintManagerHibernateImpl")
	private ISysPrintManagerDao sysPrintManagerDao;
	
	@Override
	public IBaseDAO getBaseDao() {
		return sysPrintManagerDao;
	}
	
	public SysPrintManager getModeByCode(String code,User user){
		
		
		SysPrintManager printManager=null;
		
			
			printManager = (SysPrintManager) sysPrintManagerDao.getSession()
							.createQuery(
									" from SysPrintManager where startDate<=? and ?<endDate and code=? and status!=0 and departCode like ? order by id")
							.setParameter(0, new Date()).setParameter(1, new Date()).setParameter(2,code).setString(3, user.get("departId")+"%")
							.setMaxResults(1).uniqueResult();
			
			if(null==printManager) {
				printManager =  (SysPrintManager) sysPrintManagerDao.getSession()
							.createQuery(
									" from SysPrintManager where startDate<=? and ?<endDate and code=? and status!=0   order by id")
							.setParameter(0, new Date()).setParameter(1, new Date()).setParameter(2,code)
							.setMaxResults(1).uniqueResult();
			}
			
			return printManager;
				
				
		}
		
		
	}

