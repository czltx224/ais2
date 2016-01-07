package com.xbwl.cus.service;

import com.xbwl.common.orm.Page;
import com.xbwl.common.service.IBaseService;
import com.xbwl.cus.vo.ReportSerarchVo;
import com.xbwl.entity.CusRateAnalyse;

/**
 *@author LiuHao
 *@time May 21, 2012 3:44:43 PM
 */
public interface ICusRateAnalyseService extends IBaseService<CusRateAnalyse,Long> {
	/**
	 * º€∏Ò ’€ø€∑÷Œˆ
	 * @author LiuHao
	 * @time May 21, 2012 3:51:51 PM 
	 * @param page
	 * @param searchVo
	 * @return
	 * @throws Exception
	 */
	public Page rateAnalyse(Page page,ReportSerarchVo searchVo)throws Exception;
}
