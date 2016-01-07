package com.xbwl.oper.reports.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.entity.OprScanningLimitation;
import com.xbwl.oper.reports.dao.IOprScanningLimitationDao;
import com.xbwl.oper.reports.service.IOprScanningLimitationService;

/**
 * author CaoZhili time Nov 15, 2011 4:36:25 PM
 */
@Service("oprScanningLimitationServiceImpl")
@Transactional(rollbackFor=Exception.class)
public class OprScanningLimitationServiceImpl extends
		BaseServiceImpl<OprScanningLimitation, Long> implements
		IOprScanningLimitationService {

	@Resource(name="oprScanningLimitationHibernateDaoImpl")
	private IOprScanningLimitationDao oprScanningLimitationDao;
	
	@Override
	public IBaseDAO<OprScanningLimitation, Long> getBaseDao() {
		return this.oprScanningLimitationDao;
	}

	@Override
	public void save(OprScanningLimitation entity) {
		Query query = null;
		if(null!=entity.getId() && entity.getId()>0){
			query = this.oprScanningLimitationDao.createQuery("from OprScanningLimitation where operationType=? and deptId=? and id!=?",
			entity.getOperationType(),entity.getDeptId(),entity.getId());
		
		}else{
			query = this.oprScanningLimitationDao.createQuery("from OprScanningLimitation where operationType=? and deptId=?",
			entity.getOperationType(),entity.getDeptId());
		}
		List<OprScanningLimitation> list = query.list();
		if(null==list || list.size()==0){
			super.save(entity);
		}else{
			throw new ServiceException("该扫描标准已经存在！");
		}
	}
	
}
