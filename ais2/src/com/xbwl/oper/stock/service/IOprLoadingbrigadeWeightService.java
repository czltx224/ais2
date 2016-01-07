package com.xbwl.oper.stock.service;

import java.util.List;
import java.util.Map;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.OprLoadingbrigadeWeight;
import com.xbwl.oper.stock.vo.LoadingbrigadeTypeEnum;

/**
 * @author CaoZhili time Jul 8, 2011 10:08:11 AM
 * 
 * װж�����������ӿ�
 */
public interface IOprLoadingbrigadeWeightService extends
		IBaseService<OprLoadingbrigadeWeight, Long> {
	
	/**�������ͳ�Ʋ�ѯ����ȡ����
	 * @param map ��ѯ��������
	 * @return ��ѯSQL
	 * @throws Exception
	 */
	public String findTakeGoods(Map<String, String> map) throws Exception ;

	/**
	 * ���浽װж������������ˡ�����ʱ�䡢�޸��ˡ��޸�ʱ�䡢ʱ������Բ�д
	 * @param list ��Ҫ���浽װж��������е����ݼ���
	 * @param enumType ����Ĳ�������
	 */
	public void saveLoadingWeight(List<OprLoadingbrigadeWeight> list,LoadingbrigadeTypeEnum enumType)throws Exception;

	/**
	 * ��дװж�ֲ������ͳ�Ʋ�ѯ����
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String findSqlListService(Map<String, String> map)throws Exception;

}
