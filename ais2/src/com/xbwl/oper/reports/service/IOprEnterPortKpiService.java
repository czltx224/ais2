package com.xbwl.oper.reports.service;

import java.util.Map;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.OprEnterPortKpi;

/**
 * author CaoZhili
 * time Nov 10, 2011 1:47:08 PM
 */

public interface IOprEnterPortKpiService  extends IBaseService<OprEnterPortKpi, Long> {

	/**KPI统计报表
	 * @param map 条件集合
	 * @return 查询SQL
	 * @throws Exception 服务层异常
	 */
	public String findKpiReportService(Map<String, String> map)throws Exception;

}
