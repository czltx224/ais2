package com.xbwl.finance.Service;

import java.util.List;
import java.util.Map;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FiOutcost;

/**
 *@author LiuHao
 *@time Aug 29, 2011 5:49:34 PM
 */
public interface IFiOutCostService extends IBaseService<FiOutcost,Long> {
	
	/**
	 * 外发成本审核
	 * @param aa
	 * @param departId
	 * @return
	 * @throws Exception
	 */
	public String outCostAduit(List<FiOutcost> aa,Long batchNo)throws Exception;
	
	/**
	 * 撤销成本审核
	 * @param id
	 * @param ts
	 * @return
	 * @throws Exception
	 */
	public String qxFiAduit(Long id,String ts) throws Exception;
	
	/**
	 * 根据成本审核批次号更新付款状态为：1已支付
	 * @param batchNo 批次号
	 * @throws Exception
	 */
	public void payConfirmationBybatchNo(Long batchNo) throws Exception;
	
	/**
	 * 根据成本审核批次号撤销付款状态为：0未付款
	 * @param batchNo 批次号
	 * @throws Exception
	 */
	public void payConfirmationRegisterBybatchNo(Long batchNo) throws Exception;
	
	/**
	 * 返货登记审核，外发成本是否已审核提醒
	 * @return
	 * @throws Exception
	 */
	public String  returnGoodsPrompt(Long dno,Long departId) throws Exception;
	
	/**
	 * 作废外出成本数据
	 * @param id
	 * @param ts
	 * @return
	 * @throws Exception
	 */
	public String  delOutcostData (Long id ,String ts)  throws Exception;
	
	/**
	 * 撤销外发成本 信息提示
	 * @param id  外发成本ID
	 * @param ts  时间戳
	 * @return  提示字符串
	 * @throws Exception
	 */
	public String qxAmountCheck(Long id)throws Exception;
	
	/**
	 * 审核时信息提示
	 * @param aa  提示的集合id
	 * @return
	 * @throws Exception
	 */
	public Map<String,String> aduitAmountCheck(List<FiOutcost>aa ) throws Exception;
	
	/**
	 * 获取批次事情
	 * @return 返回批次号
	 * @throws Exception
	 */
	public Long getOutcostBatchNo() throws Exception;
	}
