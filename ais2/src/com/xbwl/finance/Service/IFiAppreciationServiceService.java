package com.xbwl.finance.Service;

import java.util.List;

import org.ralasafe.user.User;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FiAppreciationService;

/**
 * author shuw
 * time Oct 26, 2011 1:46:31 PM
 */

public interface IFiAppreciationServiceService extends IBaseService<FiAppreciationService, Long> {

	/**
	 * �ͷ����
	 * @param list id����
	 * @param user
	 */
	public void saveService(List<Long>list ,User user ) throws Exception; 

	/**
	 * ������
	 * @param list  id����
	 * @param user
	 * @throws Exception
	 */
	public void saveFiAudit(List<Long>list ,User user) throws Exception	;
}
