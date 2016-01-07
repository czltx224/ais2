package com.xbwl.finance.Service;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.BasStSpecialTrainRate;

/**
 * @author CaoZhili time Aug 2, 2011 4:03:04 PM
 * 
 * ר����׼Э��۷����ӿ�
 */
public interface IBasStSpecialTrainRateService extends
		IBaseService<BasStSpecialTrainRate, Long> {
	
	/**
	 * �޸�ר����׼Э��۵�״̬
	 * 
	 * @param ids ר����׼Э���ID����
	 * @param status ר����׼Э���Ҫ�޸ĵ�״̬
	 * @throws Exception ������쳣
	 */
	public void updateStatusService(String[] ids, Long status)throws Exception;

	/**
	 * �ж��Ƿ���Ա���ר����׼Э���
	 * @param basStSpecialTrainRate
	 * @return
	 */
	public boolean allowSaveService(BasStSpecialTrainRate basStSpecialTrainRate);
	
}
