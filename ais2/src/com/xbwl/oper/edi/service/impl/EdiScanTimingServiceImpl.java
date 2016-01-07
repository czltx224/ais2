package com.xbwl.oper.edi.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.entity.EdiScanTiming;
import com.xbwl.oper.edi.dao.IEdiScanTimingDao;
import com.xbwl.oper.edi.service.IEdiScanTimingService;

/**
 * 扫描时间记录表服务层操作类
 * @author czl
 * @date 2012-05-24
 */
@Service("ediScanTimingServiceImpl")
@Transactional(rollbackFor=Exception.class)
public class EdiScanTimingServiceImpl extends
		BaseServiceImpl<EdiScanTiming, Long> implements IEdiScanTimingService {

	@Resource(name="ediScanTimingHibernateDaoImpl")
	private IEdiScanTimingDao ediScanTimingDao;
	
	@Override
	public IBaseDAO<EdiScanTiming, Long> getBaseDao() {
		return this.ediScanTimingDao;
	}

}
