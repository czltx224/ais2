package com.xbwl.finance.Service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.entity.BasSpecialTrainLineDetail;
import com.xbwl.finance.Service.IBasSpecialTrainLineDetailService;
import com.xbwl.finance.dao.IBasSpecialTrainLineDetailDao;

/**
 * @author CaoZhili time Aug 12, 2011 11:38:43 AM
 * 
 * 专车线路细表服务层实现类
 */
@Service("basSpecialTrainLineDetailServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class BasSpecialTrainLineDetailServiceImpl extends
		BaseServiceImpl<BasSpecialTrainLineDetail, Long> implements
		IBasSpecialTrainLineDetailService {

	@Resource(name="basSpecialTrainLineDetailHibernateDaoImpl")
	private IBasSpecialTrainLineDetailDao basSpecialTrainLineDetailDao;
	
	@Override
	public IBaseDAO<BasSpecialTrainLineDetail, Long> getBaseDao() {

		return this.basSpecialTrainLineDetailDao;
	}

	@Override
	public void save(BasSpecialTrainLineDetail entity) {
		String sql = "";
		Query query = null;
		if(null!=entity.getId() && entity.getId()>0){
			sql = "select *from bas_special_train_line_detail t,bas_special_train_line b where t.special_train_line_id=b.id and b.depart_id=? and t.area_name=? and t.id!=?";
			query = this.createSQLQuery(sql, entity.getDepartId(),entity.getAreaName(),entity.getId());
		}else{
			sql = "select *from bas_special_train_line_detail t,bas_special_train_line b where t.special_train_line_id=b.id and b.depart_id=? and t.area_name=?";
			query = this.createSQLQuery(sql, entity.getDepartId(),entity.getAreaName());
		}
		
		List list = query.list();
		
		if(null!=list && list.size()>0){
			throw new ServiceException("该专车区域已经存在！");
		}else{
			super.save(entity);
		}
	}
}
