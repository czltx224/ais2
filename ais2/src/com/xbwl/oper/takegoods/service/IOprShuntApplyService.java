package com.xbwl.oper.takegoods.service;

import java.util.List;

import com.xbwl.common.orm.Page;
import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.OprShuntApply;

/**
 *@author LiuHao
 *@time Dec 5, 2011 11:31:49 AM
 */
public interface IOprShuntApplyService extends IBaseService<OprShuntApply,Long> {
	/**
	 * 调车申请
	 * @param list
	 * @throws Exception
	 */
	public void shuntApply(List<OprShuntApply> list)throws Exception;
	/**
	 * 调车申请查询
	 * @param page
	 * @param filghtNo
	 * @param takeAddr
	 * @return
	 * @throws Exception
	 */
	public Page findShuntApply(Page page,String filghtNo,String takeAddr)throws Exception;
}
