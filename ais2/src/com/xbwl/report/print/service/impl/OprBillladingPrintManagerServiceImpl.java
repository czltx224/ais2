package com.xbwl.report.print.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.entity.OprBillladingPrintManager;
import com.xbwl.report.print.dao.IOprBillladingPrintManagerDao;
import com.xbwl.report.print.service.IOprBillladingPrintManagerService;

/**
 * author CaoZhili time Nov 1, 2011 9:54:07 AM
 */
@Service("oprBillladingPrintManagerServiceImpl")
@Transactional(rollbackFor=Exception.class)
public class OprBillladingPrintManagerServiceImpl extends
		BaseServiceImpl<OprBillladingPrintManager, Long> implements
		IOprBillladingPrintManagerService {

	@Resource(name="oprBillladingPrintManagerDaoImpl")
	private IOprBillladingPrintManagerDao oprBillladingPrintManagerDao;
	@Override
	public IBaseDAO<OprBillladingPrintManager, Long> getBaseDao() {
		return this.oprBillladingPrintManagerDao;
	}

}
