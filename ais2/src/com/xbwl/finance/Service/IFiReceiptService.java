package com.xbwl.finance.Service;

import java.util.List;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FiReceipt;

/**
 * author shuw
 * time Dec 20, 2011 10:22:41 AM
 */
//收据Service接口
public interface IFiReceiptService extends IBaseService<FiReceipt, Long> {

	/**
	 * 作废收据，状态修改
	 * @param ids  id集合
	 * @return
	 * @throws Exception
	 */
	public String deleteByStatus(List<Long >ids) throws Exception;
}
