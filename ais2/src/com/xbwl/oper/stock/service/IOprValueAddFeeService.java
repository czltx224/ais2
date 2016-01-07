package com.xbwl.oper.stock.service;

import java.util.Date;
import java.util.List;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.OprValueAddFee;

/**
 *@author LiuHao
 *@time Sep 29, 2011 2:15:05 PM
 */
public interface IOprValueAddFeeService extends IBaseService<OprValueAddFee, Long>{
	/**
	 * 查询改变的增值服务费明细
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public List<OprValueAddFee> findChangeAddFee(List<OprValueAddFee> list,Long dno)throws Exception;
	
	/**
	 * 增值服务费月度统计
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public List findAddFeeMsg(Date date,String dateType,String departCode)throws Exception;
	/**
	 * 部门增值服务费统计
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public List findDepartFeeMsg(Date date,String dateType)throws Exception;
	/**
	 * 增值服务费同比、环比                   
	 * @param beforeDate
	 * @param afterDate
	 * @return
	 * @throws Exception
	 */
	public List findFeeThan(Date beforeDate,Date afterDate)throws Exception;
}
