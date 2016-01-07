package com.xbwl.finance.dto;

import java.util.List;

import com.xbwl.finance.dto.impl.FiInterfaceProDto;

public interface IFiInterface {

	/**
	 * @Title: 业务调用财务总接口(业务费)
	 */
	public String addFinanceInfo(List<FiInterfaceProDto> listfiInterfaceDto)
			throws Exception;
	
	/**
	 * @Title: 对账单生成应收应付接口
	 */
	public String reconciliationToFiPayment(List<FiInterfaceProDto> listfiInterfaceDto)throws Exception;
	
	/**
	 * @Title: 出库调用财务总接口
	 */
	public String outStockToFi(List<FiInterfaceProDto> listfiInterfaceDto)throws Exception;
	
	
	/**
	 *  @Title: 调用财务总现结接口
	 */
	public String currentToFiPayment(List<FiInterfaceProDto> listfiInterfaceDto)throws Exception;
	
	/**
	 *  @Title: 到车、发车确认调用财务内部成本接口
	 */
	public String internalCostToFi(List<FiInterfaceProDto> listfiInterfaceDto)throws Exception;
	
	
	/**
	 *  @Title:撤销到车、发车确认时撤销财务内部成本接口
	 */
	public String invalidInternalCostToFi(List<FiInterfaceProDto> listfiInterfaceDto)throws Exception;
	
	
	/**
	 *  @Title: 到车确认（手动新增）调用财务提货成本接口(提货成本表、超重表)
	 */
	public String storageToFiDeliverCost(List<FiInterfaceProDto> listfiInterfaceDto)throws Exception;
	
	/**
	 *  @Title: 更改申请调用财务接口
	 */
	public String changeToFi(List<FiInterfaceProDto> listfiInterfaceDto)throws Exception;
	

	/**
	 * 业务调用财务作废总接口
	 * @param listfiInterfaceDto
	 * @return 作废的记录数
	 * @throws Exception
	 */
	public int invalidToFi(List<FiInterfaceProDto> listfiInterfaceDto) throws Exception;
	
	/**
	 *  @Title: 作废应收应付款单
	 */
	public int invalidToFiPayment(FiInterfaceProDto fiInterfaceProDto)throws Exception;
	
	/**
	 * 根据配送单号作废应收应付数据
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public String invalidToFiPaymentByDno(FiInterfaceProDto fiInterfaceProDto)throws Exception;
	
	/**
	 * 根据配送单号作废欠款明细
	 * @param fiInterfaceProDto
	 * @return
	 * @throws Exception
	 */
	public String invalidToFiReceivabledetailByDno(FiInterfaceProDto fiInterfaceProDto)throws Exception;
	
	/**
	 *  @Title: 作废欠款明细
	 */
	public int invalidToFiReceivabledetail(FiInterfaceProDto fiInterfaceProDto)throws Exception;
	
	/**
	 *  @Title: 作废预存款
	 */
	public String invalidToFiAdvance(FiInterfaceProDto fiInterfaceProDto)throws Exception;
	
	/**
	 *  @Title: 更改付款单金额(只有提货成本撤销才能用)
	 */
	public String changePaymentAmount(List<FiInterfaceProDto> listfiInterfaceDto)throws Exception;
	
	/**
	 * 业务写入收入总接口
	 * @param listfiInterfaceDto
	 * @return
	 * @throws Exception
	 */
	public String currentToFiIncome(List<FiInterfaceProDto> listfiInterfaceDto)throws Exception;
	
	/**
	 * 业务写入成本总接口
	 * @param listfiInterfaceDto
	 * @return
	 * @throws Exception
	 */
	public String currentToFiFiCost(List<FiInterfaceProDto> listfiInterfaceDto)throws Exception;
	
	/**
	 * 返货作废应收应付
	 * @param listfiInterfaceDto
	 * @return
	 * @throws Exception
	 */
	public String oprReturnToFi(List<FiInterfaceProDto> listfiInterfaceDto)throws Exception;
	
	/**
	 * 返货成本写入中转成本
	 * @param listfiInterfaceDto
	 * @return
	 * @throws Exception
	 */
	public String oprReturnToFiTransitcost(List<FiInterfaceProDto> listfiInterfaceDto)throws Exception;
	
	/**
	 * 修改提货成本金额
	 * @param listfiInterfaceDto(String sourceData:数据来源,Long sourceNo:来源单号,double amount:撤销总金额)
	 * @return
	 * @throws Exception
	 */
	public String revocationFiDeliveryCost(List<FiInterfaceProDto> listfiInterfaceDto)throws Exception;
	
	/**
	 * 作废外发成本
	 * @param listfiInterfaceDto
	 * @return
	 * @throws Exception
	 */
	//public String invalidOutCost(List<FiInterfaceProDto> listfiInterfaceDto)throws Exception;
	
}
