package com.xbwl.oper.reports.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdcn.bpaf.common.exception.ServiceException;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.orm.PropertyFilter;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.entity.OprScanNotIncludeCustomer;
import com.xbwl.oper.reports.dao.IOprScanNotIncludeCustomerDao;
import com.xbwl.oper.reports.service.IOprScanNotIncludeCustomerService;

/**
 * 签收和回单确收报表剔除代理服务层实现类
 * @author czl
 * @date 2012-04-20
 */
@Service("oprScanNotIncludeCustomerServiceImpl")
@Transactional(rollbackFor=Exception.class)
public class OprScanNotIncludeCustomerServiceImpl extends
		BaseServiceImpl<OprScanNotIncludeCustomer, Long> implements
		IOprScanNotIncludeCustomerService {

	@Resource(name="oprScanNotIncludeCustomerHibernateDaoImpl")
	private IOprScanNotIncludeCustomerDao oprScanNotIncludeCustomerDao;
	
	@Override
	public IBaseDAO<OprScanNotIncludeCustomer, Long> getBaseDao() {
		return this.oprScanNotIncludeCustomerDao;
	}

	@Override
	public void save(OprScanNotIncludeCustomer entity) {
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		PropertyFilter filter = null;
		if(null!=entity.getId() && entity.getId()>0){
			filter = new PropertyFilter("NEL_id",entity.getId()+"");
			filters.add(filter);
		}
		if(null!=entity.getDepartId() && entity.getDepartId()>0){
			filter = new PropertyFilter("EQL_departId",entity.getDepartId()+"");
			filters.add(filter);
		}
		if(null!=entity.getCusId() && entity.getCusId()>0){
			filter = new PropertyFilter("EQL_cusId",entity.getCusId()+"");
			filters.add(filter);
		}
		if(null!=entity.getCusName() && !"".equals(entity.getCusName())){
			filter = new PropertyFilter("EQS_cusName",entity.getCusName());
			filters.add(filter);
		}
		List<OprScanNotIncludeCustomer> list = this.oprScanNotIncludeCustomerDao.find(filters);
		if(null!=list && list.size()>0){
			throw new ServiceException("名称为"+entity.getCusName()+"的代理在剔除表中已经存在！");
		}else{
			super.save(entity);
		}
	}
}
