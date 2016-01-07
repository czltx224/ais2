package com.xbwl.oper.stock.service;

import java.util.List;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.OprSignRoute;

/**
 * author shuw
 * time Aug 2, 2011 1:51:12 PM
 */

public interface IOprSignRouteService extends IBaseService<OprSignRoute, Long>{

	/**
	 * @param departId
	 * @return  获得车次号
	 * @throws Exception
	 */
	public String getCarTimesNum(Long departId) throws Exception;
	
	
	/**
	 * @param aa   车辆成本管理，财务审核保存到成本表
	 * @throws Exception
	 */
	public void fiAuditByName(List<OprSignRoute>aa) throws Exception;

	/**
	 * 撤销会计审核 车队审核
	 * @param id
	 * @param ts
	 * @throws Exception
	 */
	public void qxFiAudit(Long id,String ts) throws Exception;
	
	/**
	 * 根据成本ID更新付款状态
	 * @param id 成本ID
	 * @param payStatus 付款状态（0未付款，1已支付）
	 * @throws Exception
	 */
	public void payConfirmationById(Long id) throws Exception;
	
	/**
	 * 根据成本ID撤销付款状态
	 * @param id 成本ID
	 * @param payStatus 付款状态（0未付款，1已支付）
	 * @throws Exception
	 */
	public void payConfirmationRegisterById(Long id) throws Exception;
	
	/**
	 * 车队审核
	 * @param aa   
	 * @throws Exception
	 */
	public void carAuditByName(List<OprSignRoute> aa) throws Exception;
	
	/**
	 * 根据车次号，删除车辆成本数据
	 * @param routeNumber
	 * @throws Exception
	 */
	public void cancelCarByRouteNumber(Long routeNumber)throws Exception;
	
	/**
	 * 获得折合票数
	 * @param weight 重量
	 * @throws Exception
	 */
	public double getVotesPiece(double weight,double startWeight,double levleWeight,double twoLevelWeight)throws Exception;
}
