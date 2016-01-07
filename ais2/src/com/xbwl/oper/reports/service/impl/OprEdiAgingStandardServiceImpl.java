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
import com.xbwl.entity.OprEdiAgingStandard;
import com.xbwl.oper.reports.dao.IOprEdiAgingStandardDao;
import com.xbwl.oper.reports.service.IOprEdiAgingStandardService;

/**
 * 中转时效标准服务层实现类
 * @author czl
 * @date 2012-05-19
 *
 */
@Service("oprEdiAgingStandardServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class OprEdiAgingStandardServiceImpl extends
		BaseServiceImpl<OprEdiAgingStandard, Long> implements
		IOprEdiAgingStandardService {

	@Resource(name = "oprEdiAgingStandardHibernateDaoImpl")
	private IOprEdiAgingStandardDao oprEdiAgingStandardDao;

	@Override
	public IBaseDAO<OprEdiAgingStandard, Long> getBaseDao() {
		return this.oprEdiAgingStandardDao;
	}

	@Override
	public void save(OprEdiAgingStandard entity) {
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
		
		List<OprEdiAgingStandard> list = this.oprEdiAgingStandardDao.find(filters);
		if(null!=list && list.size()>0){
			throw new ServiceException(entity.getDeptName()+"中转时效标准已经存在！");
		}else{
			super.save(entity);
		}
	}

}
