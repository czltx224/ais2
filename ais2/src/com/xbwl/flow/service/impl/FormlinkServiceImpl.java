package com.xbwl.flow.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.entity.FlowFormlink;
import com.xbwl.flow.dao.IFormlinkDao;
import com.xbwl.flow.service.IFormlinkService;

/**
 * 流程管理-表单关联服务层实现类
 *@author LiuHao
 *@time Feb 14, 2012 4:37:48 PM
 */
@Service("formlinkServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class FormlinkServiceImpl extends BaseServiceImpl<FlowFormlink,Long> implements
		IFormlinkService {
	@Resource(name="formlinkHibernateDaoImpl")
	private IFormlinkDao formlinkDao;
	@Override
	public IBaseDAO getBaseDao() {
		return formlinkDao;
	}
}
