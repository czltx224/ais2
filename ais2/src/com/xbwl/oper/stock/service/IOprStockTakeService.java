package com.xbwl.oper.stock.service;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.OprStocktake;


/**
 * @author shuw
 *�̵�����
 */
public interface IOprStockTakeService extends IBaseService<OprStocktake,Long> {

	/**
	 * �������̵��Ȼ������ؿ����Ϣ���浽�̵���ϸ��
	 * @param opr
	 * @param departId
	 * @return
	 */
	public String saveOprStockTakeQueryOprStock(OprStocktake opr,Long  departId);
	
}
