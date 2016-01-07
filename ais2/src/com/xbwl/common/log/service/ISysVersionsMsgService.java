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
	
	// REVIEW-ACCEPT 增加方法注释
	 /**用户获取SVN的代码更新信息，并插入数据库
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
