package com.xbwl.oper.stock.service;

import java.util.List;

import com.xbwl.entity.OprStocktakeDetail;
import com.xbwl.common.service.IBaseService;


/**����̵���ϸ��
 * @author Administrator
 *
 */
public interface IOprStockTakeDetailService extends IBaseService<OprStocktakeDetail,Long> {

	/**
	 * ����ʵ�ռ���list�����޸��̵����״̬
	 * @param oprList
	 */
	public void saveRealPieceById(List<OprStocktakeDetail> oprList) ;
}
