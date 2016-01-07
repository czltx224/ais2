package com.xbwl.report.print.service;

import org.ralasafe.user.User;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.SysPrintManager;

/**
 * @author Administrator
 * @createTime 2:22:04 PM
 * @updateName Administrator
 * @updateTime 2:22:04 PM
 * 
 */

public interface ISysPrintManagerService  extends IBaseService<SysPrintManager, Long>{
	
	/**通过code获取在模板，有时间和状态,部门限制
	 * @param code
	 * @return
	 */
	public SysPrintManager getModeByCode(String code,User user);

}
