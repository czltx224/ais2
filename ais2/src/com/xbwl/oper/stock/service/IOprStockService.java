package com.xbwl.oper.stock.service;

import java.util.Map;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.OprReturnGoods;
import com.xbwl.entity.OprStock;

/**
 * @author Administrator
 *��������
 */
public interface IOprStockService  extends IBaseService<OprStock,Long>{

	
	/**
	 * ������������Ϣ
	 * @param oprReturnGoods (dno,returnNum,returnDepart �����������ü���)
	 * @throws ServiceException
	 */
	public void saveReturnGoodsStock(OprReturnGoods oprReturnGoods)throws Exception;
	
	
	/**
	 * ��ȡ��������ѯ���
	 * @param map ��ѯ����map
	 * @return ��ѯ����ַ���
	 * @throws Exception ������쳣
	 */
	public String getStockSql(Map<String,String> map)throws Exception;


	/**�������ת�쳣���
	 * @param ids ���ID����
	 * @param remark ��ע
	 * @throws Exception
	 */
	public void toExceptionStockService(String[] ids,String remark)throws Exception;
}
