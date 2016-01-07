package com.xbwl.finance.Service;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.BasSpecialTrainRate;

/**
 * @author CaoZhili time Aug 2, 2011 4:02:05 PM
 * 
 * ר��Э��۷����ӿ�
 */
public interface IBasSpecialTrainRateService extends
		IBaseService<BasSpecialTrainRate, Long> {

	
	/**
	 * �޸�ר��Э��۵�״̬
	 * 
	 * @param ids ר��Э���ID����
	 * @param status ר��Э���Ҫ�޸ĵ�״̬
	 * @throws Exception ������쳣
	 */
	public void updateStatusService(String[] ids, Long status)throws Exception;
	

	/**
	 * ͨ�������ʽ���������ͣ����䷽ʽ�����ż�����ۿۼ�
	 * @param basSpecialTrainRate ר��Э���ʵ�����
	 * @return �ۿۼ�
	 */
	public Double getDiscountService(BasSpecialTrainRate basSpecialTrainRate) throws Exception;


	/**
	 * �ж��Ƿ���Ա���ר��Э���
	 * @param basSpecialTrainRate
	 * @return
	 */
	public boolean allowSaveService(BasSpecialTrainRate basSpecialTrainRate);
}
