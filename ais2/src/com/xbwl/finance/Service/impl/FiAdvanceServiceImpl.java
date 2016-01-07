package com.xbwl.finance.Service.impl;

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
import com.xbwl.common.utils.DoubleUtil;
import com.xbwl.entity.FiAdvance;
import com.xbwl.entity.FiAdvanceset;
import com.xbwl.finance.Service.IFiAdvanceService;
import com.xbwl.finance.dao.IFiAdvanceDao;
import com.xbwl.finance.dao.IFiAdvancesetDao;

@Service("fiAdvanceServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class FiAdvanceServiceImpl extends BaseServiceImpl<FiAdvance,Long> implements
		IFiAdvanceService {
	
	@Resource(name="fiAdvanceHibernateDaoImpl")
	private IFiAdvanceDao fiAdvanceDao;
	
	@Resource(name = "fiAdvancesetHibernateDaoImpl")
	private IFiAdvancesetDao fiAdvancesetDao;
	
	@Override
	public IBaseDAO getBaseDao() {
		return this.fiAdvanceDao;
	}

	public void saveFiAdvance(FiAdvance fiAdvance) throws Exception {
		FiAdvanceset fiAdvanceset =fiAdvancesetDao.get(fiAdvance.getFiAdvanceId());
		if(fiAdvance.getSettlementType()==1l){
			fiAdvanceset.setOpeningBalance(DoubleUtil.add(fiAdvanceset.getOpeningBalance(), fiAdvance.getSettlementAmount()));
		}
		if(fiAdvance.getSettlementType()==2l){
			fiAdvanceset.setOpeningBalance(DoubleUtil.sub(fiAdvanceset.getOpeningBalance(), fiAdvance.getSettlementAmount()));
		}
		fiAdvance.setSettlementBalance(fiAdvanceset.getOpeningBalance());
		
		fiAdvancesetDao.save(fiAdvanceset);
		fiAdvanceDao.save(fiAdvance);
	}

	public void deleteStatus(Long id, Long fiAdvancesetId) throws Exception {
		FiAdvance fiAdvance  =fiAdvanceDao.get(id);
		FiAdvanceset fiAdvanceset = fiAdvancesetDao.get(fiAdvancesetId);
		if(fiAdvance.getSettlementType()==1l){
			fiAdvanceset.setOpeningBalance(DoubleUtil.sub(fiAdvanceset.getOpeningBalance(), fiAdvance.getSettlementAmount()));
		}
		if(fiAdvance.getSettlementType()==2l){
			fiAdvanceset.setOpeningBalance(DoubleUtil.add(fiAdvanceset.getOpeningBalance(), fiAdvance.getSettlementAmount()));
		}
		fiAdvance.setStatus(0l);
		fiAdvancesetDao.save(fiAdvanceset);
		fiAdvanceDao.save(fiAdvance);
	}

}
