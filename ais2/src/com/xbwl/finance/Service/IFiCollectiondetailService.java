package com.xbwl.finance.Service;

import java.util.Map;

import com.xbwl.common.bean.ValidateInfo;
import com.xbwl.common.orm.Page;
import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FiCollectiondetail;

public interface IFiCollectiondetailService extends IBaseService<FiCollectiondetail,Long> {
	
	/**
	 * 
	* @Title: saveCollectionstatement 
	* @Description: TODO(根据查询条件生成对账单) 
	* @param @param map 查询条件
	* @param @param page 分页
	* @param @param validateInfo    返回消息对账 
	* @throws
	 */
	public void saveCollectionstatement(Map map,Page page,ValidateInfo validateInfo);
	
	/**
	 * 
	* @Title: getCollectiondetailBatch 
	* @Description: TODO(根据Map作为查询条件，获取对账批次号，再返回page对象。) 
	* @param @param page
	* @param @param map 查询条件
	* @param @return    page
	* @return Page    返回类型 
	* @throws
	 */
	public Page getCollectiondetailBatch(Page page,Map map);
	
	/**
	 * 
	* @Title: updateFiReceivabledetailStatus 
	* @Description: TODO(根据对账单号更新欠款明细状态) 
	* @param @param reconciliationNo 对账单号
	* @param @param reconciliationStatus  对账单状态 
	* @return void    返回类型 
	* @throws
	 */
	public void updateStatusByreconciliationNo(Long reconciliationNo,Long reconciliationStatus);

}
