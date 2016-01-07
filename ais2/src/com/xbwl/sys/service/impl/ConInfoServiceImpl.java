package com.xbwl.sys.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.orm.Page;
import com.xbwl.common.orm.PropertyFilter;
import com.xbwl.common.orm.PropertyFilter.MatchType;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.entity.ConsigneeInfo;
import com.xbwl.sys.dao.IConInfoDao;
import com.xbwl.sys.service.IConInfoService;

/**
 *@author LiuHao
 *@time 2011-7-19 ÏÂÎç03:29:45
 */
@Service("conInfoServiceImpl")
@Transactional(rollbackFor={Exception.class})
public class ConInfoServiceImpl extends BaseServiceImpl<ConsigneeInfo, Long> implements
		IConInfoService {
	@Resource(name="conInfoHibernateDaoImpl")
	private IConInfoDao conInfoDao;
	@Override
	public IBaseDAO<ConsigneeInfo, Long> getBaseDao() {
		return conInfoDao;
	}
	
	public List<ConsigneeInfo> findConsigneeInfoByTel(String tel) throws Exception{
		List<ConsigneeInfo> list=conInfoDao.createQuery("from ConsigneeInfo ci where ci.consigneeTel like ?", tel+'%').setMaxResults(20).list();
		return list;
	}

}
