package com.xbwl.cus.service;

import java.util.Map;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.CusService;

/**
 * author CaoZhili time Oct 10, 2011 4:35:01 PM
 */

public interface ICusServiceService extends IBaseService<CusService, Long> {

	/**
	 * 分配客户保存方法
	 * 
	 * @param splitStrings 保存的客户ID 数组
	 * @param userId 保存的用户ID
	 * @param flag 是否修改客服员
	 * @throws Exception
	 */
	public void saveCusService(String[] splitStrings, Long userId, Boolean flag)
			throws Exception;

	/**
	 * 分配客户移除方法
	 * @param splitStrings
	 * @param bussDepart 要移除客户的部门
	 * @throws Exception
	 */
	public void moveCusService(String[] splitStrings,Long bussDepart)
			throws Exception;

	/**
	 * 客服员指派客户查询方法
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String findServiceDesignate(Map<String, String> map)throws Exception;

	/**
	 * 保存指派客服员
	 * @param cusRecordId
	 * @param userCode
	 * @throws Exception
	 */
	public void saveServiceDesignate(Long cusRecordId, String userCode)throws Exception;

	/**
	 * 删除指派客服员
	 * @param cusRecordId
	 * @param userCode
	 * @throws Exception
	 */
	public void deleteServiceDesignate(Long cusRecordId, String userCode)throws Exception;

}
