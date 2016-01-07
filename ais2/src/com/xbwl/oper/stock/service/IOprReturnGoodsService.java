package com.xbwl.oper.stock.service;

import java.util.Map;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.OprFaxIn;
import com.xbwl.entity.OprReturnGoods;

/**
 * @author CaoZhili
 * time Jul 30, 2011 10:43:16 AM
 * 
 * ������������ӿ�
 */
public interface IOprReturnGoodsService extends IBaseService<OprReturnGoods, Long>{
	
	/**
	 * �����ǼǱ��淽��
	 * @param oprReturnGoods ������ʵ�����
	 * @throws Exception ������쳣
	 */
	public void saveRegistrationService(OprReturnGoods oprReturnGoods) throws Exception;

	
	/**
	 * ������Ᵽ�淽��
	 * @param ids ������ID�ַ���
	 * @param returnStatus ����״̬������Ҫ�޸ĵ�״̬
	 * @throws Exception ������쳣
	 */
	public void saveEnterStockService(String ids, Long returnStatus)  throws Exception;


	
	/**
	 * �ж��Ƿ���Է����Ǽǣ�����һ���������
	 * @param dno
	 * @return һ���������
	 * @throws Exception
	 */
	public OprFaxIn allowRegistration(Long dno) throws Exception;
	
	/**
	 * @param dno Ҫ���ҵ����͵���
	 * @return ����������
	 * @throws Exception ������쳣
	 */
	public Long findMaxOvermemoNoByDno(Long dno)throws Exception;

	/**������ˣ����ò���ӿںͳɱ��ӿ�
	 * @param returnGoodsId �������
	 * @throws Exception ������쳣
	 */
	public void auditReturnGoods(Long returnGoodsId)throws Exception;


	/**����ͳ��
	 * @param map ����ͳ������
	 * @throws Exception ������쳣
	 */
	public String findTotalCountService(Map<String, String> map)throws Exception;
}
