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
	
	/**ͨ��code��ȡ��ģ�壬��ʱ���״̬,��������
	 * @param code
	 * @return
	 */
	public SysPrintManager getModeByCode(String code,User user);

}
