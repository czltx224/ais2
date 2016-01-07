package com.xbwl.oper.reports.service;

import java.util.Map;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.OprEnterPortReport;

/**
 * author CaoZhili
 * time Nov 9, 2011 4:34:21 PM
 */

public interface IOprEnterPortReportService extends IBaseService<OprEnterPortReport, Long>{

	/**进港时效报表查询语句获取方法
	 * @param map 查询条件集合
	 * @return 查询SQL
	 * @throws Exception 服务层异常
	 */
	public String findListService(Map<String, String> map)throws Exception;

	/**出港时效报表查询语句获取方法
	 * @param map 查询条件集合
	 * @return 查询SQL
	 * @throws Exception 服务层异常
	 */
	public String findOutListService(Map<String, String> map)throws Exception;

	/**干线车时效报表查询语句获取
	 * @param map 查询条件集合
	 * @return 查询SQL
	 * @throws Exception 服务层异常
	 */
	public String findArteryCarListService(Map<String, String> map)throws Exception;

	/**签单时效报表查询语句获取
	 * @param map 查询条件集合
	 * @return 查询SQL
	 * @throws Exception 服务层异常
	 */
	public String findSignListService(Map<String, String> map) throws  Exception;

	/**回单确收时效报表查询语句获取
	 * @param map 查询条件集合
	 * @return 查询SQL
	 * @throws Exception 服务层异常
	 */
	public String findReceiptComfirmListService(Map<String, String> map) throws Exception;

	/**扫描时效报表查询语句获取
	 * @param map 查询条件集合
	 * @return 查询SQL
	 * @throws Exception 服务层异常
	 */
	public String findScanningListService(Map<String, String> map)throws Exception;

	/**车辆装载率报表查询语句获取
	 * @param map 查询条件集合
	 * @return 查询SQL
	 * @throws Exception 服务层异常
	 */
	public String findCarLoadingRateListService(Map<String, String> map) throws Exception;

	/**干线车统计报表查询语句获取
	 * @param map 查询条件集合
	 * @return 查询SQL
	 * @throws Exception 服务层异常
	 */
	public String findArteryCarCountService(Map<String, String> map)throws Exception;

}
