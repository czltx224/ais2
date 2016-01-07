package com.xbwl.cus.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.cus.dao.ICusOverManagerDao;
import com.xbwl.cus.service.ICusOverManagerService;
import com.xbwl.entity.CusOverweightManager;

/**
 * 主单超重设置服务层实现类
 *@author LiuHao
 *@time Oct 25, 2011 10:44:03 AM
 */
@Service("cusOverManagerServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class CusOverManagerServiceImpl extends BaseServiceImpl<CusOverweightManager,Long> implements
		ICusOverManagerService {
	@Resource(name="cusOverManagerHibernateDaoImpl")
	private ICusOverManagerDao cusOverManagerDao;
	@Override
	public IBaseDAO getBaseDao() {
		return cusOverManagerDao;
	}
}
