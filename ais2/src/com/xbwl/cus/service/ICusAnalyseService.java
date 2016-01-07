package com.xbwl.cus.service;

import java.util.Date;
import java.util.List;

import com.xbwl.common.orm.Page;
import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.CusAnalyse;

/**
 *@author LiuHao
 *@time Dec 17, 2011 11:11:00 AM
 */
public interface ICusAnalyseService extends IBaseService<CusAnalyse,Long> {
	/**
	 * 月度客户等级分析
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public Page findCusRankMsg(Page page,Date startDate,Date endDate ,String dateType)throws Exception;
	/**
	 * 客户等级年度统计
	 * @param countType
	 * @param countDate
	 * @param departId
	 * @return
	 * @throws Exception
	 */
	public Page findCusMonRankMsg(Page page,String countType,String startCount,String endCount,Long departId,String countRange,Long cusRecordId)throws Exception;
	/**
	 * 客户比较
	 * @param countType
	 * @param startDate
	 * @param endDate
	 * @param cusType
	 * @return
	 * @throws Exception
	 */
	public Page findCusRankThan(Page page,String countType,Date startDate,Date endDate,String cusType)throws Exception;
	/**
	 * 客服员客户对比
	 * @author LiuHao
	 * @time Apr 6, 2012 3:00:22 PM 
	 * @param countType
	 * @param startDate
	 * @param endDate
	 * @param cusService
	 * @return
	 * @throws Exception
	 */
	public Page findCusVsCus(Page page,String countType,Date startDate,Date endDate,String cusService)throws Exception;
}
