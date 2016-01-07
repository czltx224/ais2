package com.xbwl.cus.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.cus.dao.IConsigneeInfoDao;
import com.xbwl.cus.service.IConsigneeInfoService;
import com.xbwl.entity.ConsigneeInfo;

/**
 * 收货人信息统计
 * author CaoZhili time Oct 9, 2011 2:37:20 PM
 */
@Service("consigneeInfoServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class ConsigneeInfoServiceImpl extends
		BaseServiceImpl<ConsigneeInfo, Long> implements IConsigneeInfoService {

	@Resource(name = "consigneeInfoHibernateDaoImpl")
	private IConsigneeInfoDao consigneeInfoDao;

	@Override
	public IBaseDAO<ConsigneeInfo, Long> getBaseDao() {
		return this.consigneeInfoDao;
	}

}
