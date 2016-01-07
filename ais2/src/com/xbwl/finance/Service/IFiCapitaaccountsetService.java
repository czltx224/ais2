package com.xbwl.finance.Service;

import java.util.Map;

import org.ralasafe.user.User;

import com.xbwl.common.orm.Page;
import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FiCapitaaccountset;
import com.xbwl.finance.vo.FiCapitaaccountsetVo;

/**
 * @author Administrator
 *
 */
public interface IFiCapitaaccountsetService extends IBaseService<FiCapitaaccountset,Long> {
	
	
	/**��������տ
	 * @param fiCapitaaccountset
	 * @throws Exception
	 */
	public void saveFiCapitaaccountset(FiCapitaaccountset fiCapitaaccountset) throws Exception;
	
	/**��ѯ�˺���Ϣ
	 * @param accountType �˺�����
	 * @param userId �û�ID
	 * @param departId ����ID
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public Page<FiCapitaaccountset> findAccountList(Map map,Page page) throws Exception;
}
