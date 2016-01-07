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

	/**�쳣����
	 * @param vo �쳣���Ᵽ�����ݶ���
	 * @throws Exception ������쳣
	 */
	public void outStockService(OprExceptionStockVo vo) throws Exception;

	/**���淵����⵽�쳣���
	 * @param oprReturnGoods
	 * @throws Exception
	 */
	public void saveReturnGoodsStock(OprReturnGoods oprReturnGoods) throws Exception;

	/**�쳣���ת�������
	 * @param ids �쳣���ID����
	 * @param remark ��ע
	 * @throws Exception ������쳣
	 */
	public void toNormalStockService(String[] ids,String remark) throws Exception;
	
}
