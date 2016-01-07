package com.xbwl.finance.Service;

import org.ralasafe.user.User;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FiApplyfund;

public interface IFiApplyfundService extends IBaseService<FiApplyfund,Long> {
	
	/**
	 * 保存资金申请单
	 * @param fiApplyfund
	 * @throws Exception
	 */
	public void saveApplyfund(FiApplyfund fiApplyfund) throws Exception;
	
	/**
	 * 作废资金申请单
	 * @param fiApplyfund
	 * @throws Exception
	 */
	public void invalidApplyfund(FiApplyfund fiApplyfund) throws Exception;
	
	/**
	 * 审核资金申请单
	 * @param fiApplyfund
	 * @throws Exception
	 */
	public void auditApplyfund(FiApplyfund fiApplyfund) throws Exception;
	
	/**
	 * 生成资金坐支单
	 * @param fiApplyfund
	 * @throws Exception
	 */
	public void fundstransferSit(FiApplyfund fiApplyfund) throws Exception;
	
	/**
	 * 收款确认(申请人)
	 * @param id 资金申请单ID
	 * @param user
	 * @throws Exception
	 */
	public void receivablesConfirmation(Long id,User user) throws Exception;
	

}
