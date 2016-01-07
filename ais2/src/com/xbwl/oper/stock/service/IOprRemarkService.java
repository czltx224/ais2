package com.xbwl.oper.stock.service;

import java.util.List;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.OprRemark;

/**
 * @author CaoZhili time Jul 19, 2011 4:12:24 PM
 * 
 * 备注记录服务层接口
 */
public interface IOprRemarkService extends IBaseService<OprRemark, Long> {
	
	/**
	 * 综合查询多票添加一个备注
	 * @param dnos 配送单号集合	
	 * @param remark	备注
	 * @throws Exception
	 */
	public void saveRemarks(List<Long>dnos,String remark) throws Exception;

	/**备注保存方法
	 * @param dno 配送单号
	 * @param remark 备注
	 * @throws Exception 异常
	 */
	public void saveRemark(Long dno,String remark) throws Exception;
}
