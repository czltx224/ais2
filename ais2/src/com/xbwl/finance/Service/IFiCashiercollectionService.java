package com.xbwl.finance.Service;

import java.util.Map;

import org.ralasafe.user.User;
import org.springframework.stereotype.Service;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FiCashiercollection;

public interface IFiCashiercollectionService extends IBaseService<FiCashiercollection,Long> {
	/**
	* @Title: 保存出纳收款单 
	* @Description: TODO(1、保存出纳收款单实体，2、记录资金账号流水与资金账号余额.) 
	* @throws
	 */
	public void saveCashiercollection(FiCashiercollection fiCashiercollection) throws Exception;
	
	/**
	 * 把导入的Excel数据保存到出纳收款表
	 * @param batchNo
	 * @throws Exception
	 */
	public void saveExcelData(Long batchNo) throws Exception;
	
	/**
	 * 将导入的POS数据保存到出纳收款单
	 * @param batchNo
	 * @throws Exception
	 */
	public void saveExcelPosData(Long batchNo) throws Exception;
	
	
	/**
	* @Title: 保存出纳收款单核销 
	* @param @param map request参数
	 */
	public void saveVerification(Map map,User user) throws Exception;
	
	/**
	 * 作废出纳收款单
	 * @param fiCashiercollection
	 * @throws Exception
	 */
	public void invalidCashiercollection(FiCashiercollection fiCashiercollection) throws Exception;
	
	/**
	 * 手工核销
	 * @param map
	 * @param user
	 * @throws Exception
	 */
	public void manualVerification(Map map,User user) throws Exception;
}
