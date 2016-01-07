package com.xbwl.sys.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.orm.Page;
import com.xbwl.common.orm.PropertyFilter;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.entity.BasCusService;
import com.xbwl.sys.dao.IBasCusServiceDao;
import com.xbwl.sys.service.IBasCusService;

/**
 *@author LiuHao
 *@time Jul 29, 2011 4:14:03 PM
 */
@Service("basCusServiceImpl")
@Transactional(rollbackFor={Exception.class})
public class BasCusServiceImpl extends BaseServiceImpl<BasCusService,Long> implements
		IBasCusService {
	@Resource(name="basCusServiceHibernateDaoImpl")
	private IBasCusServiceDao basCusServiceDao;
	@Override
	public IBaseDAO getBaseDao() {
		return basCusServiceDao;
	}
	public BasCusService getCusServiceByCusId(Long cusId, Long bussId)
			throws Exception {
		List<BasCusService> list=this.find("from BasCusService bcs where bcs.cusId=? and bcs.departId=?", cusId,bussId);
		if(list.size()<=0){
			throw new ServiceException("客商ID:"+cusId+"对应的客服员信息不存在");
		}
		return list.get(0);
	}
}
