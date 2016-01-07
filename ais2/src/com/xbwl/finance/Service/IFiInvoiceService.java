package com.xbwl.finance.Service;

import org.hibernate.persister.entity.Loadable;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FiInvoice;

public interface IFiInvoiceService extends IBaseService<FiInvoice,Long> {
	
	/**
	 * 
	* @Title: ·¢Æ±×÷·Ï 
	* @param @param ids
	 */
	public String invalid(String ids) throws Exception;
	
	/**
	 * 
	* @Title: ·¢Æ±ÉóºË
	* @param @param ids
	 */
	public String review(String ids) throws Exception;
	
}
