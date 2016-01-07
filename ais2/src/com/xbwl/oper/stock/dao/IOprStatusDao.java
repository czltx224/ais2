package com.xbwl.oper.stock.dao;

import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.entity.OprStatus;

/**
 * author CaoZhili time Jul 6, 2011 2:52:05 PM
 */
public interface IOprStatusDao extends IBaseDAO<OprStatus, Long> {
	/**
	 * 根据主单号查询货物状态信息
	 * @param dno
	 * @param status
	 * @return
	 * @throws Exception
	 */
	public OprStatus findStatusByDno(Long dno) throws Exception;
}
