package com.xbwl.cus.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.xbwl.common.orm.Page;
import com.xbwl.common.service.IBaseService;
import com.xbwl.cus.vo.ReportSerarchVo;
import com.xbwl.entity.ProductAnalyse;

/**
 *@author LiuHao
 *@time Dec 3, 2011 3:12:04 PM
 */
public interface IProAnalyseService extends IBaseService<ProductAnalyse,Long>{
	/**
	 * 产品总体统计
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public Page findWholePro(Page page,ReportSerarchVo searchVo)throws Exception;
	/**
	 * 查询产品货量趋势
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public Page findIncomePro(Page page,ReportSerarchVo searchVo) throws Exception;
	/**
	 * 货量等级分析
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public Page findGoodsRank(Page page,ReportSerarchVo searchVo)throws Exception;
	/**
	 * 获得条件查询的部分条件 及分组信息
	 * @author LiuHao
	 * @time May 21, 2012 3:54:05 PM 
	 * @param searchVo
	 * @return
	 * @throws Exception
	 */
	public Map getSqlStr(ReportSerarchVo searchVo)throws Exception;
}
