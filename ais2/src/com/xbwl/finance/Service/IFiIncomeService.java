package com.xbwl.finance.Service;

import java.util.Map;

import com.xbwl.common.orm.Page;
import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FiIncome;
import com.xbwl.finance.vo.FiIncomeBalanceVo;

/**
 *@author LiuHao
 *@time Aug 26, 2011 6:34:26 PM
 */
public interface IFiIncomeService extends IBaseService<FiIncome,Long> {

	/**
	 * 收入汇总
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String getTotalFiInIncome(Map map) throws Exception;


	/**
	 * 盈亏平衡报表
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public String getAllincomeBalanceVo(FiIncomeBalanceVo vo) throws Exception;
	
	/**
	 * 生成部门收入交账报表
	 * @param departId 业务部门ID
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @param seq 交账单号
	 * @throws Exception
	 */
	public void addAccountSingle(Long departId,String startTime,String endTime,Long seq) throws Exception;
	
	/**
	 * 根据交账单号查询收入明细
	 * @param map
	 */
	public Page<FiIncome> findFiIncomeDetail(Page page,Map map) throws Exception;
}
