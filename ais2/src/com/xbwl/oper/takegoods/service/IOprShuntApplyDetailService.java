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
	 * 调车申请审核
	 * @param osa
	 * @throws Exception
	 */
	public void shuntApplyAduit(OprShuntApplyDetail osa)throws Exception;
	/**
	 * 审核撤销
	 * @param osaId
	 * @throws Exception
	 */
	public void repeatAduit(Long osadId)throws Exception;
	/**
	 * 查询最大的车次号
	 */
	public Long findMaxRouteNumber(String carNo)throws Exception;
	/**
	 * 车辆监控
	 * @return
	 * @throws Exception
	 */
	public Page findCarGuard(Page page,Date date)throws Exception;
}
