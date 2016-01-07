package com.xbwl.oper.reports.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.orm.PropertyFilter;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.entity.OprOutLimitation;
import com.xbwl.oper.reports.dao.IOprOutLimitationDao;
import com.xbwl.oper.reports.service.IOprOutLimitationService;

/**
 * author CaoZhili time Nov 15, 2011 1:50:51 PM
 */
@Service("oprOutLimitationServiceImpl")
@Transactional(rollbackFor=Exception.class)
public class OprOutLimitationServiceImpl extends
		BaseServiceImpl<OprOutLimitation, Long> implements
		IOprOutLimitationService {

	@Resource(name="oprOutLimitationHibernateDaoImpl")
	private IOprOutLimitationDao oprOutLimitationDao;
	
	@Override
	public IBaseDAO<OprOutLimitation, Long> getBaseDao() {
		return this.oprOutLimitationDao;
	}

	@Override
	public void save(OprOutLimitation entity) {
		
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		PropertyFilter filter = null;
		if(null!=entity.getId() && entity.getId()>0){
			filter = new PropertyFilter("NEL_id",entity.getId()+"");
			filters.add(filter);
		}
		if(null!=entity.getDeptId() && entity.getDeptId()>0){
			filter = new PropertyFilter("EQL_deptId",entity.getDeptId()+"");
			filters.add(filter);
		}
		if(null!=entity.getOperationType() && !"".equals(entity.getOperationType())){
			filter = new PropertyFilter("EQS_operationType",entity.getOperationType());
			filters.add(filter);
			if("市内送货".equals(entity.getOperationType())){//如果是市内送货则判断时间段
				if(null!=entity.getTimeStage() && !"".equals(entity.getTimeStage())){
					filter = new PropertyFilter("EQS_timeStage",entity.getTimeStage());
					filters.add(filter);
				}
			}
		}
		
		List<OprOutLimitation> list = this.oprOutLimitationDao.find(filters);
		if(null!=list && list.size()>0){
			throw new ServiceException("该出库标准已经存在！");
		}else{
			super.save(entity);
		}
	}
}
