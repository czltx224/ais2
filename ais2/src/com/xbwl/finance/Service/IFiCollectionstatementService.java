package com.xbwl.finance.Service;

import org.ralasafe.user.User;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FiCollectionstatement;

public interface IFiCollectionstatementService extends IBaseService<FiCollectionstatement,Long> {
	/**
	 * 
	* @Title: 对账单是否为未审核
	* @Description: TODO
	* @param @param reconciliationNos
	* @param @return    true:未审核，false：已审核或已作废或已收银
	* @return boolean    返回类型 
	* @throws
	 */
	public boolean isConfirmReview(String reconciliationNos);

	/**
	 * 
	* @Title: 对账单审核
	* @Description: TODO(1、自动更新对账单对账状态，2、将对账信息写入应收应付表) 
	* @param @param reconciliationNos    对账单列表 
	* @param @param user    当前操作用户
	* @return void    返回类型 
	* @throws
	 */
	public void confirmReview(String reconciliationNos,User user);
	
	/**
	 * 
	* @Title: 对账单状态是否为已审核 
	* @Description: TODO
	* @param @param reconciliationNos
	* @param @return    true:已审核,false为未审核
	* @return boolean    返回类型 
	* @throws
	 */
	public boolean isRevocationReview(String reconciliationNos);
	
	/**
	 * 撤销对账单审核
	* @Title: RevocationReview 
	* @Description: TODO(1、自动更新对账单对账状态，2、自动作废应收应付表) 
	* @param @param reconciliationNos  对账单列表 
	* @param @param user    当前操作用户
	* @return void    返回类型 
	* @throws
	 */
	public void revocationReview(String reconciliationNos,User user);
}
