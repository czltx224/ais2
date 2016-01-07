package com.xbwl.finance.Service;

import java.io.File;
import java.util.List;

import org.ralasafe.user.User;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FiCost;
import com.xbwl.entity.FiDeliverycost;

/**
 * author shuw
 * time Oct 8, 2011 5:46:10 PM
 */

public interface IFiDeliverycostService extends IBaseService<FiDeliverycost,Long> {

	
	/**
	 * 提货成本管理模块 会计审核
	 * @param list
	 * @throws Exception
	 */
	public void saveFiAudit (List<FiDeliverycost> list,User user) throws  Exception ;
	
	/**
	 * 导入Excel对账
	 * @param excelFile
	 * @param fileName
	 * @throws Exception
	 */
	public String saveFiExcel(File excelFile,String fileName) throws Exception;
	
	/**
	 * 取消手工匹配
	 * @param id
	 * @param ts
	 * @return
	 * @throws Exception
	 */
	public String qxAudit(List<FiDeliverycost>aa) throws Exception;

	/**
	 * 保存手工匹配
	 * @param fiDeliverycost
	 * @throws Exception
	 */
	public void saveMat(List<FiDeliverycost>aa) throws Exception;
	
	/**
	 * 撤销审核
	 * @param aa
	 * @param ts
	 * @throws Exception
	 */
	public void saveQxFiAudit(List<FiDeliverycost> aa ) throws Exception;
	

	/**
	 * 根据对账单号更新付款状态为：1已支付
	 * @param batchNo 对账单号
	 * @throws Exception
	 */
	public void payConfirmationBybatchNo(Long batchNo) throws Exception;
	
	/**
	 * 根据对账单号撤销付款状态为：0已支付
	 * @param batchNo 对账单号
	 * @throws Exception
	 */
	public void payConfirmationRegisterBybatchNo(Long batchNo) throws Exception;
}
