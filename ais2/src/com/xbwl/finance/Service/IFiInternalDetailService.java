package com.xbwl.finance.Service;

import java.util.Map;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FiInternalDetail;
import com.xbwl.entity.FiInternalDivide;

/**
 * author CaoZhili time Oct 20, 2011 1:51:46 PM
 * �ڲ�������ϸ�����ӿ�
 */

public interface IFiInternalDetailService extends
		IBaseService<FiInternalDetail, Long> {

	/**�ڲ����㱨���ѯSQL��ȡ
	 * @param map ��ѯ��������
	 * @return ��ѯSQL
	 * @throws Exception ������쳣
	 */
	public String reportListService(Map<String, String> map)throws Exception;

	/**�ڲ�������ˣ����������ڲ�������ϸ��¼
	 * @param entity �ڲ�����ʵ��
	 * @throws Exception ������쳣
	 */
	public void saveTwoDepart(FiInternalDivide entity) throws Exception;

	/**�ڲ����ֳ�����ˣ����������ڲ�������ϸ��¼
	 * @param divideId Ҫ�������ڲ����ֱ�ID
	 * @throws Exception ������쳣
	 */
	public void cancelAuditDivideService(Long divideId)throws Exception;

}
