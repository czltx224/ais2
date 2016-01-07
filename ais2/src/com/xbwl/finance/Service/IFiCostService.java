package com.xbwl.finance.Service;

import java.util.Map;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FiCost;

/**
 * author shuw
 * �ɱ��� �����ӿ�
 * time Sep 21, 2011 3:51:03 PM
 */

public interface IFiCostService  extends IBaseService<FiCost,Long> {

	/**
	 * ����������Դ����Դ���� ��ȡ�ɱ��ĺ�
	 * @param sourceData ������Դ
	 * @param dno  ��Դ����
	 * @return  total
	 * @throws Exception
	 */
	public double sumCostBySourceDataId(String sourceData,Long  dno) throws Exception;
	
	/**
	 * ��������������ͳ�Ʒ��������ѯ
	 * @param map
	 * @throws Exception
	 */
	public String getAllCqList(Map<String,String>map) throws Exception;
}
