package com.xbwl.finance.Service;

import java.util.List;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FiReceipt;

/**
 * author shuw
 * time Dec 20, 2011 10:22:41 AM
 */
//�վ�Service�ӿ�
public interface IFiReceiptService extends IBaseService<FiReceipt, Long> {

	/**
	 * �����վݣ�״̬�޸�
	 * @param ids  id����
	 * @return
	 * @throws Exception
	 */
	public String deleteByStatus(List<Long >ids) throws Exception;
}
