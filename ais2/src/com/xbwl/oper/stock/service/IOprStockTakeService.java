package com.xbwl.oper.stock.service;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.OprStocktake;


/**
 * @author shuw
 *盘点主表
 */
public interface IOprStockTakeService extends IBaseService<OprStocktake,Long> {

	/**
	 * 保存主盘点表，然后查出相关库存信息保存到盘点明细表
	 * @param opr
	 * @param departId
	 * @return
	 */
	public String saveOprStockTakeQueryOprStock(OprStocktake opr,Long  departId);
	
}
