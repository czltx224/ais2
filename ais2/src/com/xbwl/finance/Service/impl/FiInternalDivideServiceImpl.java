package com.xbwl.finance.Service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.entity.FiInternalDivide;
import com.xbwl.finance.Service.IFiInternalDetailService;
import com.xbwl.finance.Service.IFiInternalDivideService;
import com.xbwl.finance.dao.IFiInternalDivideDao;

/**
 * author CaoZhili time Oct 21, 2011 4:31:36 PM
 * 内部结算划分访问层实现类
 */
@Service("fiInternalDivideServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class FiInternalDivideServiceImpl extends
		BaseServiceImpl<FiInternalDivide, Long> implements
		IFiInternalDivideService {
	
	@Resource(name="fiInternalDivideHibernateDaoImpl")
	private IFiInternalDivideDao fiInternalDivideDao;
	
	@Resource(name = "fiInternalDetailServiceImpl")
	private IFiInternalDetailService fiInternalDetailService;
	
	@Override
	public IBaseDAO<FiInternalDivide, Long> getBaseDao() {
		return this.fiInternalDivideDao;
	}

	public void auditService(FiInternalDivide fiInternalDivide)
			throws Exception {
		FiInternalDivide entity = this.fiInternalDivideDao.get(fiInternalDivide.getId());
			entity.setAmount(fiInternalDivide.getAmount());
			entity.setAuditStatus(fiInternalDivide.getAuditStatus());
			entity.setAuditName(fiInternalDivide.getAuditName());
			entity.setAuditRemark(fiInternalDivide.getAuditRemark());
			entity.setAuditTime(fiInternalDivide.getAuditTime());
			
		this.fiInternalDivideDao.save(entity);
		this.fiInternalDetailService.saveTwoDepart(entity);
	}

	public void cancelAuditService(String[] strIds) throws Exception {
		for (int i = 0; i < strIds.length; i++) {
			
			FiInternalDivide divide = this.fiInternalDivideDao.get(Long.valueOf(strIds[i]));
			divide.setAuditName("");
			divide.setAuditRemark("");
			divide.setAuditStatus(1l);
			divide.setAuditTime(null);
			this.fiInternalDivideDao.save(divide);
			
			this.fiInternalDetailService.cancelAuditDivideService(Long.valueOf(strIds[i]));
		}
	}

	public void invalidService(String[] strIds) throws Exception {
		for (int i = 0; i < strIds.length; i++) {
			FiInternalDivide entity = this.fiInternalDivideDao.get(Long.valueOf(strIds[i]));
			entity.setAuditStatus(0l);
			this.fiInternalDivideDao.save(entity);
		}
	}

}
