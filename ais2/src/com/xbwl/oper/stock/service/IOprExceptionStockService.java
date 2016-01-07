package com.xbwl.oper.stock.service;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.OprExceptionStock;
import com.xbwl.entity.OprReturnGoods;
import com.xbwl.oper.stock.vo.OprExceptionStockVo;

/**
 * author CaoZhili
 * time Nov 18, 2011 9:04:47 AM
 */

public interface IOprExceptionStockService extends IBaseService<OprExceptionStock, Long> {

	/**异常出库
	 * @param vo 异常出库保存数据对象
	 * @throws Exception 服务层异常
	 */
	public void outStockService(OprExceptionStockVo vo) throws Exception;

	/**保存返货入库到异常库存
	 * @param oprReturnGoods
	 * @throws Exception
	 */
	public void saveReturnGoodsStock(OprReturnGoods oprReturnGoods) throws Exception;

	/**异常库存转正常库存
	 * @param ids 异常库存ID数组
	 * @param remark 备注
	 * @throws Exception 服务层异常
	 */
	public void toNormalStockService(String[] ids,String remark) throws Exception;
	
}
