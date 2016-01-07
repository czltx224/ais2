package com.xbwl.oper.takegoods.service;

import java.util.Date;
import java.util.List;

import com.xbwl.common.orm.Page;
import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.OprShuntApply;
import com.xbwl.entity.OprShuntApplyDetail;

/**
 *@author LiuHao
 *@time Dec 5, 2011 11:31:49 AM
 */
public interface IOprShuntApplyDetailService extends IBaseService<OprShuntApplyDetail,Long> {
	/**
	 * �����������
	 * @param osa
	 * @throws Exception
	 */
	public void shuntApplyAduit(OprShuntApplyDetail osa)throws Exception;
	/**
	 * ��˳���
	 * @param osaId
	 * @throws Exception
	 */
	public void repeatAduit(Long osadId)throws Exception;
	/**
	 * ��ѯ���ĳ��κ�
	 */
	public Long findMaxRouteNumber(String carNo)throws Exception;
	/**
	 * �������
	 * @return
	 * @throws Exception
	 */
	public Page findCarGuard(Page page,Date date)throws Exception;
}
