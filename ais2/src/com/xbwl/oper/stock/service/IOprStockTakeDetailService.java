package com.xbwl.oper.stock.service;

import java.util.List;

import com.xbwl.entity.OprStocktakeDetail;
import com.xbwl.common.service.IBaseService;


/**库存盘点明细表
 * @author Administrator
 *
 */
public interface IOprStockTakeDetailService extends IBaseService<OprStocktakeDetail,Long> {

	/**
	 * 保存实收件数list对象，修改盘点对象状态
	 * @param oprList
	 */
	public void saveRealPieceById(List<OprStocktakeDetail> oprList) ;
}
