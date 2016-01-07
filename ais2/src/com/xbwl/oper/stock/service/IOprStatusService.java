package com.xbwl.oper.stock.service;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.OprStatus;

/**
 * @author CaoZhili time Jul 6, 2011 2:54:38 PM
 * 状态辅助服务层接口
 */
public interface IOprStatusService extends IBaseService<OprStatus, Long> {
	/**
	 * 根据主单号查询货物状态信息
	 * @param dno
	 * @param status
	 * @return
	 * @throws Exception
	 */
	public OprStatus findStatusByDno(Long dno) throws Exception;
	
	/**
	 * 财务收银时回写传真状态表收银状态
	 * @param dno 
	 * @throws Exception
	 */
	public void verificationCashStatusByFiPayment(Long dno) throws Exception;
	
	/**
	 * 财务收银中转外发公司月结对账单时回写传真状态表收银状态。
	 * @param dno 
	 * @throws Exception
	 */
	public void verificationCashStatusByFiReceivabledetail(Long dno) throws Exception;
	
	/**
	 * 财务撤销收银
	 * @param dno
	 * @throws Exception
	 */
	public void revocationCashStatus(Long dno) throws Exception;
}

