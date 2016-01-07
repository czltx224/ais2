package com.xbwl.finance.Service;

import org.ralasafe.user.User;

import com.xbwl.common.orm.Page;
import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FiPaid;
import com.xbwl.entity.FiPayment;
import com.xbwl.entity.FiReceivablestatement;

public interface IFiReceivablestatementService extends
		IBaseService<FiReceivablestatement, Long> {

	/**
	 * 
	* @Title:对账单是否为未审核
	* @Description:  对账单是否为未审核
	* @param @param reconciliationNos    对账单列表
	* @return boolean  true:未审核，false：已审核或已作废或已收银
	 */
	public boolean isConfirmReview(String reconciliationNos);

	
	/**
	 * 
	* @Title: 对账单审核
	* @Description: 对账单审核(1、自动更新对账单对账状态，2、将对账信息写入应收应付表) 
	* @param @param reconciliationNos    对账单列表 
	* @param @param user    当前操作用户
	* @return void    返回类型 
	 */
	public void confirmReview(String reconciliationNos,User user)throws Exception;
	
	
	/**
	 * 
	* @Title: 对账单状态是否为已审核 
	* @Description: 对账单状态是否为已审核 
	* @param @param reconciliationNos    对账单列表 
	* @param @return    true:已审核,false为未审核
	 */
	public boolean isRevocationReview(String reconciliationNos);
	
	
	/**
	* @Title: 撤销对账单审核 
	* @Description: TODO(1、自动更新对账单对账状态，2、自动作废应收应付表) 
	* @param @param reconciliationNos  对账单列表 
	* @param @param user    当前操作用户
	 */
	public void revocationReview(String reconciliationNos,User user) throws Exception;
	
	
	/**
	 * 
	* @Title: 对账单作废 
	* @Description: 对账单作废
	* @param @param reconciliationNos    对账单列表 
	 */
	public void invalid(String reconciliationNos) throws Exception;
	/**
	 * 汇总客商对账信息
	 * @author LiuHao
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public Page findCusReceistatment(Page page)throws Exception;
	
	/**
	 * 核销对账单
	 * @param amount 核销金额
	 *  @param costType 冲销费用类型
	 * @param reconciliationNo 对账单号
	 * @throws Exception
	 */
	public void verificationReceistatment(Double amount,Long reconciliationNo) throws Exception;
	
	/**
	 * 撤销实收实付时，撤销对账单、往来明细、应付代收货款收银状态
	 * @param fiPayment
	 * @param fiPaid
	 * @throws Exception
	 */
	public  void revocationFiPaid(FiPayment fiPayment,FiPaid fiPaid)  throws Exception;
	
	/**
	 * 导出Excel 特殊的Excel，并且需要模版
	 * @param id
	 * @throws Exception
	 */
	public void exporterExcel(Long id) throws Exception;
}

