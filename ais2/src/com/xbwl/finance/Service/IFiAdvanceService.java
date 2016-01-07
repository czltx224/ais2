package com.xbwl.finance.Service;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FiAdvance;

/**
 * @author Administrator
 *Ԥ�����˺Ž����嵥�����ӿ�
 */
public interface IFiAdvanceService extends IBaseService<FiAdvance,Long> {
	
	/**
	 * �嵥����
	 * @param fiAdvance
	 * @throws Exception
	 */
	public void saveFiAdvance(FiAdvance fiAdvance) throws Exception;
	
	/**
	 * �嵥ɾ�� 
	 * @param id
	 * @param fiAdvancesetId
	 * @throws Exception
	 */
	public void deleteStatus(Long id,Long fiAdvancesetId) throws Exception;
}
