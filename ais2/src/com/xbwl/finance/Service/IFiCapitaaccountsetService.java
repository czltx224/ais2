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
	
	
	/**保存出纳收款单
	 * @param fiCapitaaccountset
	 * @throws Exception
	 */
	public void saveFiCapitaaccountset(FiCapitaaccountset fiCapitaaccountset) throws Exception;
	
	/**查询账号信息
	 * @param accountType 账号类型
	 * @param userId 用户ID
	 * @param departId 部门ID
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public Page<FiCapitaaccountset> findAccountList(Map map,Page page) throws Exception;
}
