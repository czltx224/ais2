package com.xbwl.report.print.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.entity.OprPrint;
import com.xbwl.report.print.dao.IOprPrintDao;
import com.xbwl.report.print.service.IOprPrintService;

/**
 * @author Administrator
 * @createTime 3:42:27 PM
 * @updateName Administrator
 * @updateTime 3:42:27 PM
 * 
 */

@Service("oprPrintServiceImpl")
@Transactional(rollbackFor=Exception.class)
public class OprPrintServiceImpl extends BaseServiceImpl<OprPrint,Long> implements
	IOprPrintService  {

	@Resource(name="oprPrintHibernateImpl")
	private IOprPrintDao oprPrintDao;

	@Override
	public IBaseDAO getBaseDao() {
		return oprPrintDao;
	}

}
