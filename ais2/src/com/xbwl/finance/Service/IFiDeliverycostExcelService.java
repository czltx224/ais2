package com.xbwl.finance.Service;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FiCost;
import com.xbwl.entity.FiDeliverycostExcel;

/**
 * author shuw
 * time Oct 22, 2011 10:55:11 AM
 */

public interface IFiDeliverycostExcelService extends IBaseService<FiDeliverycostExcel,Long>{

	
 
	/**
	 * 对账审核
	 * @param batchNo  批次号
	 * @param cusName 客商名称
	 * @param cusId 客商ID
	 * @param money 总金额
	 * @param code  ID码
	 * @return
	 * @throws Exception
	 */
	public String auditFi(String batchNo,String cusName,Long cusId,Double money,String code) throws Exception;
	
	/**
	 * 获取批次号（唯一）
	 * @param bussDepartId
	 * @return
	 * @throws Exception
	 */
	public Long getBatchNO(Long bussDepartId) throws Exception;
	
	/**
	 * 自动匹配账单
	 * @param batchNo
	 * @return
	 * @throws Exception
	 */
	public String  compareStatus(String batchNo) throws Exception;
	
	/**
	 * 修正账单（由多改少）
	 * @param batchNo
	 * @return
	 * @throws Exception
	 */
	public String  updateFax(String batchNo) throws Exception;
	
	/**
	 * 修正账单（全部）
	 * @param batchNo
	 * @return
	 * @throws Exception
	 */
	public String  updateAllFax(String batchNo) throws Exception;
	
	/**
	 * 汇总SQL
	 * @param batchNo
	 * @return
	 * @throws Exception
	 */
	public String  getTotalAmount(String batchNo) throws Exception;
	
	
}
