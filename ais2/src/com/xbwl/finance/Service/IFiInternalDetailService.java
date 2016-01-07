package com.xbwl.finance.Service;

import java.util.Map;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FiInternalDetail;
import com.xbwl.entity.FiInternalDivide;

/**
 * author CaoZhili time Oct 20, 2011 1:51:46 PM
 * 内部结算明细服务层接口
 */

public interface IFiInternalDetailService extends
		IBaseService<FiInternalDetail, Long> {

	/**内部结算报表查询SQL获取
	 * @param map 查询条件集合
	 * @return 查询SQL
	 * @throws Exception 服务层异常
	 */
	public String reportListService(Map<String, String> map)throws Exception;

	/**内部划分审核，保存两条内部结算明细记录
	 * @param entity 内部划分实体
	 * @throws Exception 服务层异常
	 */
	public void saveTwoDepart(FiInternalDivide entity) throws Exception;

	/**内部划分撤销审核，撤销两条内部结算明细记录
	 * @param divideId 要撤销的内部划分表ID
	 * @throws Exception 服务层异常
	 */
	public void cancelAuditDivideService(Long divideId)throws Exception;

}
