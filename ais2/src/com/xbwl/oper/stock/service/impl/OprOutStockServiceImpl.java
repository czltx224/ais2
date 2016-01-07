package com.xbwl.oper.stock.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.entity.OprNode;
import com.xbwl.oper.fax.vo.OprFaxInVo;
import com.xbwl.oper.stock.dao.IOprOutStockDao;
import com.xbwl.oper.stock.service.IOprNodeService;
import com.xbwl.oper.stock.service.IOprOutStockService;

/**
 * author shuw
 * time Aug 22, 2011 11:41:57 AM
 */
@Service("oprOutStockServiceImpl")
@Transactional(rollbackFor={Exception.class})
public class OprOutStockServiceImpl extends BaseServiceImpl<OprFaxInVo, Long> implements IOprOutStockService{

	@Resource(name = "oprOutStockHibernateDaoImpl")
	private IOprOutStockDao oprOutStockDao;
	
	@Override
	public IBaseDAO<OprFaxInVo, Long> getBaseDao() {
		return oprOutStockDao;
	}

}
