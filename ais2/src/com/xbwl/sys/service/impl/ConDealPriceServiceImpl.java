package com.xbwl.sys.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.orm.Page;
import com.xbwl.common.orm.PropertyFilter;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.entity.ConsigneeDealPrice;
import com.xbwl.sys.dao.IConDealPriceDao;
import com.xbwl.sys.service.IConDealPriceService;

/**
 *@author LiuHao
 *@time Jun 27, 2011 6:53:11 PM
 */
@Service("conDealPriceServiceImpl")
@Transactional(rollbackFor={Exception.class})
public class ConDealPriceServiceImpl extends BaseServiceImpl<ConsigneeDealPrice,Long> implements
		IConDealPriceService {
	@Resource(name="conDealPriceHibernateDaoImpl")
	private IConDealPriceDao conDealPriceDao;
	@Override
	public IBaseDAO getBaseDao() {
		return conDealPriceDao;
	}
	public Page<ConsigneeDealPrice> findConDealPrice(Page page,String cusName,String[] tels)
			throws Exception {
		return conDealPriceDao.findConDealPrice(page, cusName,tels);
	}
}
