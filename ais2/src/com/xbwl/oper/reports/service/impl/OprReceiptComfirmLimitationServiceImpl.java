package com.xbwl.oper.reports.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.entity.OprReceiptComfirmLimitation;
import com.xbwl.oper.reports.dao.IOprReceiptComfirmLimitationDao;
import com.xbwl.oper.reports.service.IOprReceiptComfirmLimitationService;

/**
 * author CaoZhili time Nov 15, 2011 5:29:34 PM
 */
@Service("oprReceiptComfirmLimitationServiceImpl")
@Transactional(rollbackFor=Exception.class)
public class OprReceiptComfirmLimitationServiceImpl extends
		BaseServiceImpl<OprReceiptComfirmLimitation, Long> implements
		IOprReceiptComfirmLimitationService {

	@Resource(name="oprReceiptComfirmLimitationHibernateDaoImpl")
	private IOprReceiptComfirmLimitationDao oprReceiptComfirmLimitationDao;
	
	@Override
	public IBaseDAO<OprReceiptComfirmLimitation, Long> getBaseDao() {
		return this.oprReceiptComfirmLimitationDao;
	}

	@Override
	public void save(OprReceiptComfirmLimitation entity) {
		Query query = null;
		if(null!=entity.getId() && entity.getId()>0){
			query = this.oprReceiptComfirmLimitationDao.createQuery("from OprReceiptComfirmLimitation where operationType=? and deptId=? and id!=?",
			entity.getOperationType(),entity.getDeptId(),entity.getId());
		
		}else{
			query = this.oprReceiptComfirmLimitationDao.createQuery("from OprReceiptComfirmLimitation where operationType=? and deptId=?",
			entity.getOperationType(),entity.getDeptId());
		}
		List<OprReceiptComfirmLimitation> list = query.list();
		if(null==list || list.size()==0){
			super.save(entity);
		}else{
			throw new ServiceException("该回单确收标准已经存在！");
		}
	}
	

}
