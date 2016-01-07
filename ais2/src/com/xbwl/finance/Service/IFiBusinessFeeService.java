package com.xbwl.finance.Service;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FiBusinessFee;

/**
 * author CaoZhili
 * time Oct 17, 2011 3:38:13 PM
 * 业务费管理服务层接口 
 */

public interface IFiBusinessFeeService extends IBaseService<FiBusinessFee, Long> {

	/**业务费管理状态修改方法
	 * @param idStrings 要修改的业务费ID数组
	 * @param status 要修改的状态
	 * @param workflowNo 流程号
	 * @param amount 业务费
	 * @throws Exception 服务层异常
	 */
	public void auditStatusService(String[] idStrings,Long status,String workflowNo,Double amount)throws Exception;

}
