package com.xbwl.oper.stock.service;

import java.util.List;
import java.util.Map;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.OprFaxIn;
import com.xbwl.entity.OprReturnGoods;
import com.xbwl.entity.OprStoreArea;

/**
 * @author CaoZhili
 * �����������ӿ�
 */
public interface IOprStoreAreaService extends IBaseService<OprStoreArea, Long> {

	/**
	 * �ӿ��̱��в�ѯȥ��
	 * @param custprops
	 * @param cusName
	 * @return
	 * @throws Exception
	 */
	public List<Map> findCusNameService(String[] custprops,String cusName)throws Exception;

	/**
	 * �ӿ��̱��в�ѯ��������
	 * @param pkAreacl
	 * @return
	 * @throws Exception
	 */
	public List<Map>  findPkAreaclService(String pkAreacl)throws Exception;
	/**
	 * ��ÿ������
	 * @param oprStoreArea
	 * @param ofi
	 * @return
	 * @throws Exception
	 */
	public String getStockArea(OprStoreArea oprStoreArea,OprFaxIn ofi)throws Exception;
}
