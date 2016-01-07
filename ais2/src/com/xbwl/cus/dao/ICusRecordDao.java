package com.xbwl.cus.dao;

import java.util.List;

import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.entity.CusRecord;

/**
 *@author LiuHao
 *@time Oct 8, 2011 1:43:41 PM
 */
public interface ICusRecordDao extends IBaseDAO<CusRecord,Long> {
	/**
	 * 查询客户信息
	 * @param name
	 * @param bussDepart
	 * @return
	 * @throws Exception
	 */
	public List<CusRecord> findCusRecord(String name,Long bussDepart)throws Exception;
}
