package com.xbwl.common.log.service;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.SysVersionsMsg;

/**
 * @author Administrator
 * @createTime 11:35:34 AM
 * @updateName Administrator
 * @updateTime 11:35:34 AM
 * 
 */

public interface ISysVersionsMsgService extends IBaseService<SysVersionsMsg,Long> {
	
	// REVIEW-ACCEPT ���ӷ���ע��
	 /**�û���ȡSVN�Ĵ��������Ϣ�����������ݿ�
	 * @param url
	 * @param targetPaths
	 * @param userName
	 * @param password
	 * @param startRevision
	 * @param endRevision
	 */
	 //FIXED
	public void inserMsg();

}
