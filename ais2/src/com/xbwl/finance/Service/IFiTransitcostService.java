package com.xbwl.finance.Service;

import java.util.List;
import java.util.Map;

import org.ralasafe.user.User;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FiTransitcost;

/**
 * author shuw
 * time Oct 7, 2011 11:46:16 AM
 */

public interface IFiTransitcostService extends
						IBaseService<FiTransitcost, Long> {
	
 
	/**
	 * 中转成本审核，保存到中转成本表和成本表中
	 * @param user
	 * @param dno
	 * @throws Exception
	 */
	public String saveFiTransitcostAndFicost(User user,String ts,Long  dno) throws Exception;

	/**
	 * 中转成本审核，保存到中转成本表和成本表中
	 * @param user
	 * @param dno
	 * @throws Exception
	 */
	public String saveFiTransitcostAndFicost(User user,List<FiTransitcost> aa) throws Exception;
	
	/**
	 * 撤销会计审核
	 * @param id
	 * @param ts
	 * @throws Exception
	 */
	public void qxFiAudit(Long id,String ts,String sourceData ) throws Exception;
	
	/**
	 * 生成批次号
	 * @param id
	 * @param ts
	 * @throws Exception
	 */
	public Long getBatchNo( ) throws Exception;
	
	/**
	 * 审核状态查询SQL
	 * @param map 查询条件
	 * @param type  查询类型的判断 0是查询全部，1已审核，-1是未审核
	 * @return
	 * @throws Exception
	 */
	public String getSelectSql(Map map,Long type ) throws Exception;
	
	/**
	 * 根据批次号更新付款状态为:1已支付
	 * @param batchNo 批次号
	 * @throws Exception
	 */
	public void payConfirmationBybatchNo(Long batchNo) throws Exception;
	
	
	/**
	 * 根据批次号撤销付款状态为:0已支付
	 * @param batchNo 批次号
	 * @throws Exception
	 */
	public void payConfirmationRegisterBybatchNo(Long batchNo) throws Exception;
	
	
	/**撤销审核信息提示
	 * @param id  中转成本表ID
	 * @return info
	 * @throws Exception
	 */
	public String qxAmountCheck(Long id) throws Exception ;
}
