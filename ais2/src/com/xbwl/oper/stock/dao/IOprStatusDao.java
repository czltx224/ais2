package com.xbwl.oper.stock.dao;

import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.entity.OprStatus;

/**
 * author CaoZhili time Jul 6, 2011 2:52:05 PM
 */
public interface IOprStatusDao extends IBaseDAO<OprStatus, Long> {
	/**
	 * ���������Ų�ѯ����״̬��Ϣ
	 * @param dno
	 * @param status
	 * @return
	 * @throws Exception
	 */
	public OprStatus findStatusByDno(Long dno) throws Exception;
}
