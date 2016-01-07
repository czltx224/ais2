package com.xbwl.finance.Service;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FiInternalDivide;

/**
 * author CaoZhili time Oct 21, 2011 4:30:35 PM
 * 内部结算划分访问层接口
 */

public interface IFiInternalDivideService extends
		IBaseService<FiInternalDivide, Long> {

	/**内部结算划分审核方法
	 * @param fiInternalDivide 要审核的实体对象
	 * @throws Exception 服务层异常
	 */
	public void auditService(FiInternalDivide fiInternalDivide)throws Exception;

	/**内部结算划分撤销审核方法
	 * @param strIds 要撤销的ID数组
	 * @throws Exception 服务层异常
	 */
	public void cancelAuditService(String[] strIds)throws Exception;

	/**内部结算划分作废方法
	 * @param strIds 要作废的ID数组
	 * @throws Exception 服务层异常
	 */
	public void invalidService(String[] strIds) throws Exception;

}
