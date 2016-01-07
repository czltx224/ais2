package com.xbwl.finance.Service;

import java.util.Map;

import org.ralasafe.user.User;

import com.xbwl.common.orm.Page;
import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FiPaid;

public interface IFiPaidService extends IBaseService<FiPaid,Long> {
	
	/**
	* @Title: 撤销收、付款单 
	 */
	public String revocation(Long fiPaidId) throws Exception;
	
	/**
	 * 
	* @Title: 核销实收实付单据 
	* @param @param map
	* @throws
	 */
	public void verificationById(Long id,User user) throws Exception;
	
	/**
	 * 付款单核销
	 * @param ids 付款单ID列表
	 * @param user 核销人
	 * @throws Exception
	 */
	public void paymentVerification(String ids,User user) throws Exception;
	
	/**
	 * 查询资金上交总金额
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Map searchHandInAmount(Map map) throws Exception;
	
	/**
	 * 资金上交单保存
	 * @param map
	 * @param user
	 * @throws Exception
	 */
	public void handInConfirmation(Map map,User user) throws Exception;
	
	/**
	 * 生成收银核算日报表
	 * @param departId 所属部门
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @param seq 核算报表批次号
	 * @throws Exception
	 */
	public void addAccountSingle(Long departId,String startTime,String endTime,Long seq) throws Exception;
	
	/**
	 * 查询核算收银报表明细
	 * @param page
	 * @param map
	 * @return
	 */
	public Page findAccountSingle(Page page,Map map);
}
