package com.xbwl.oper.reports.service;

import java.util.Map;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.OprLoadingbrigadeWeight;

/**
 * author CaoZhili
 * time Sep 30, 2011 9:38:30 AM
 */

public interface IOperationReportService  extends IBaseService<OprLoadingbrigadeWeight,Long>{

	/**获取送货货量查询SQL
	 * @param map 条件集合
	 * @return 查询SQL
	 * @throws Exception
	 */
	public String getSendGoodsService(Map<String, String> map)throws Exception;
	
	/**获取送货货量明细查询SQL
	 * @param map 条件集合
	 * @return 查询SQL
	 * @throws Exception
	 */
	public String getSendGoodsDetailService(Map<String, String> map)throws Exception;

	/**获取卸货时效报表查询SQL
	 * @param map  条件集合
	 * @return 查询SQL
	 * @throws Exception
	 */
	public String getUnloadingTimeListService(Map<String, String> map)throws Exception;

	/**获取卸货时效报表明细查询SQL
	 * @param map 条件集合
	 * @return 查询SQL
	 * @throws Exception
	 */
	public String getUnloadingTimeDetailListService(Map<String, String> map) throws Exception;

	/**送货货量明细汇总统计
	 * @param map
	 * @throws Exception
	 */
	public String findSendGoodsDetailCount(Map<String, String> map)throws Exception;

	/**拼接送货盈利报表统计查询SQL
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String findSendGoodsProfitsService(Map<String, String> map)throws Exception;
	
	/**
	 * 中转汇总统计查询SQL获取
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String findTransitCountService(Map<String,String> map)throws Exception;
	
	
	/**
	 * 中转明细查询SQL获取
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String findTransitDetailFindService(Map<String,String> map)throws Exception;

	/**
	 * 中转汇总统计查询合计SQL获取
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String findTransitCountTotalService(Map<String, String> map)throws Exception;

	/**
	 * 中转明细查询合计SQL获取
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String findTransitDetailTotalService(Map<String, String> map)throws Exception;

	/**
	 * EDI货物时效查询SQL获取
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String findEdiAgingQueryService(Map<String, String> map)throws Exception;

	/**
	 * EDI货物时效汇总统计查询SQL获取
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String findEdiAgingCountService(Map<String, String> map)throws Exception;

	/**
	 * EDI货物时效查询合计SQL获取
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String findEdiAgingQueryTotalService(Map<String, String> map)throws Exception;

	/**
	 * EDI货物时效汇总合计SQL获取
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String findEdiAgingCountTotalService(Map<String, String> map)throws Exception;

}
