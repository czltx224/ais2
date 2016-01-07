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
import com.xbwl.entity.OprScanNotIncludeSendman;
import com.xbwl.oper.reports.dao.IOprScanNotIncludeSendmanDao;
import com.xbwl.oper.reports.service.IOprScanNotIncludeSendmanService;
/**
 * 签收和回单确收报表剔除送货员服务层实现类
 * @author czl
 * @date 2012-04-20
 */
@Service("oprScanNotIncludeSendmanServiceImpl")
@Transactional(rollbackFor=Exception.class)
public class OprScanNotIncludeSendmanServiceImpl extends
		BaseServiceImpl<OprScanNotIncludeSendman, Long> implements
		IOprScanNotIncludeSendmanService {

	@Resource(name="oprScanNotIncludeSendmanHibernateDaoImpl")
	private IOprScanNotIncludeSendmanDao oprScanNotIncludeSendmanDao;
	
	@Override
	public IBaseDAO<OprScanNotIncludeSendman, Long> getBaseDao() {
		return this.oprScanNotIncludeSendmanDao;
	}

	@Override
	public void save(OprScanNotIncludeSendman entity) {
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
		if(null!=entity.getUserId() && entity.getUserId()>0){
			filter = new PropertyFilter("EQL_userId",entity.getUserId()+"");
			filters.add(filter);
		}
		if(null!=entity.getUserName() && !"".equals(entity.getUserName())){
			filter = new PropertyFilter("EQS_userName",entity.getUserName());
			filters.add(filter);
		}
		List<OprScanNotIncludeSendman> list = this.oprScanNotIncludeSendmanDao.find(filters);
		if(null!=list && list.size()>0){
			throw new ServiceException("名称为"+entity.getUserName()+"的客服员在剔除表中已经存在！");
		}else{
			super.save(entity);
		}
	}
}
