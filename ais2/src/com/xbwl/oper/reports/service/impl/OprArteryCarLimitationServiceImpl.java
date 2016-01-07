package com.xbwl.oper.reports.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.entity.OprArteryCarLimitation;
import com.xbwl.oper.reports.dao.IOprArteryCarLimitationDao;
import com.xbwl.oper.reports.service.IOprArteryCarLimitationService;

/**
 * author CaoZhili time Nov 15, 2011 4:58:54 PM
 */
@Service("oprArteryCarLimitationServiceImpl")
@Transactional(rollbackFor=Exception.class)
public class OprArteryCarLimitationServiceImpl extends
		BaseServiceImpl<OprArteryCarLimitation, Long> implements
		IOprArteryCarLimitationService {

	@Resource(name="oprArteryCarLimitationHibernateDaoImpl")
	private IOprArteryCarLimitationDao oprArteryCarLimitationDao;
	
	@Override
	public IBaseDAO<OprArteryCarLimitation, Long> getBaseDao() {
		return this.oprArteryCarLimitationDao;
	}

	@Override
	public void save(OprArteryCarLimitation entity) {
		
		if(entity.getStartDepartId().equals(entity.getEndDepartId())){
			throw new ServiceException("始发部门和达到部门不允许相同！");
		}
		Query query = null;
		if(null!=entity.getId() && entity.getId()>0){
			query = this.oprArteryCarLimitationDao.createQuery("from OprArteryCarLimitation where startDepartId=? and endDepartId=? and deptId=? and outCarTime=? and id!=?",
			entity.getStartDepartId(),entity.getEndDepartId(),entity.getDeptId(),entity.getOutCarTime(),entity.getId());
		}else{
			query =this.oprArteryCarLimitationDao.createQuery("from OprArteryCarLimitation where startDepartId=? and endDepartId=? and deptId=? and outCarTime=?",
			entity.getStartDepartId(),entity.getEndDepartId(),entity.getDeptId(),entity.getOutCarTime());
		}
		List<OprArteryCarLimitation> list = query.list();
		
		if(null==list || list.size()==0){
			super.save(entity);
		}else{
			throw new ServiceException("该部门次干线车已经存在！");
		}
	}
	

}
