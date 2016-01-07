package com.xbwl.oper.stock.service;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.OprSign;

/**
 * author shuw
 * time 2011-7-14 上午11:02:32
 */

public interface IOprSignService extends IBaseService<OprSign,Long>{
	
	/**
	 * 自提出库签收
	 * @param oprSign
	 * @param storeFee  库存费
	 * @param consigneeRate 提送费率
	 * @param consigneeFee  到付提送费
	 * @param totalCpValueAddFee  总的增值费
	 * @throws Exception
	 */
	public void saveSignStatusByFaxIn(OprSign oprSign,
			Double storeFee,Double  consigneeRate,Double  consigneeFee,Double totalCpValueAddFee) throws Exception;
	/**
	 * 签单作废
	 * @param dnos 配送单号数组
	 * @throws Exception
	 */
	public void delSign(String[] dnos) throws Exception;

	/**
	 * 定时任务保存到签收表方法
	 * @param sign
	 * @throws Exception
	 */
	public void saveTask(OprSign sign)throws Exception;
}
