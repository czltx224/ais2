package com.xbwl.oper.reports.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.entity.OprSignLimitation;
import com.xbwl.oper.reports.dao.IOprSignLimitationDao;
import com.xbwl.oper.reports.service.IOprSignLimitationService;

/**
 * author CaoZhili time Nov 15, 2011 3:04:39 PM
 */
@Service("oprSignLimitationServiceImpl")
@Transactional(rollbackFor=Exception.class)
public class OprSignLimitationServiceImpl extends
		BaseServiceImpl<OprSignLimitation, Long> implements
		IOprSignLimitationService {

	@Resource(name="oprSignLimitationHibernateDaoImpl")
	private IOprSignLimitationDao oprSignLimitationDao;
	
	@Override
	public IBaseDAO<OprSignLimitation, Long> getBaseDao() {
		return this.oprSignLimitationDao;
	}

	@Override
	public void save(OprSignLimitation entity) {

		Query query = null;
		if(null!=entity.getId() && entity.getId()>0){
			query = this.oprSignLimitationDao.createQuery("from OprSignLimitation where operationType=? and deptId=? and id!=?",
			entity.getOperationType(),entity.getDeptId(),entity.getId());
		
		}else{
			query = this.oprSignLimitationDao.createQuery("from OprSignLimitation where operationType=? and deptId=?",
			entity.getOperationType(),entity.getDeptId());
		}
		List<OprSignLimitation> list = query.list();
		if(null==list || list.size()==0){
			super.save(entity);
		}else{
			throw new ServiceException("该签收标准已经存在！");
		}
	}
}
