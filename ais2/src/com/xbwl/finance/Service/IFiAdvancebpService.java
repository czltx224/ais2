package com.xbwl.finance.Service;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FiAdvancebp;

public interface IFiAdvancebpService extends IBaseService<FiAdvancebp,Long> {
	
	/**
	 * 审核确认
	 * @param fiAdvancebp
	 * @throws Exception
	 */
	public void reviewConfirmation(FiAdvancebp fiAdvancebp) throws Exception;
	
	/**
	 * 撤销审核
	 * @param fiAdvancebp
	 * @throws Exception
	 */
	public void reviewRegister(FiAdvancebp fiAdvancebp) throws Exception;
	
	/**
	 * 撤销新增
	 * @param fiAdvancebp
	 * @throws Exception
	 */
	public void addRegister(FiAdvancebp fiAdvancebp) throws Exception;
	
	/**
	 * 收银时回写收银状态
	 * @param amount全部已收付金额
	 * @param fiAdvancebpId
	 * @throws Exception
	 */
	public void verfiFiAdvancebp(Double amount,Long fiAdvancebpId) throws Exception;
	
	/**
	 * 撤销收银时回写收银状态
	 * @param fiAdvancebpId
	 * @throws Exception
	 */
	public void verfiFiAdvancebpRegister(Long fiAdvancebpId) throws Exception;

}
