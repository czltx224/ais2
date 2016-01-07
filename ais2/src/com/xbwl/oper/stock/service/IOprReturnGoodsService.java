package com.xbwl.oper.stock.service;

import java.util.Map;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.OprFaxIn;
import com.xbwl.entity.OprReturnGoods;

/**
 * @author CaoZhili
 * time Jul 30, 2011 10:43:16 AM
 * 
 * 返货管理服务层接口
 */
public interface IOprReturnGoodsService extends IBaseService<OprReturnGoods, Long>{
	
	/**
	 * 返货登记保存方法
	 * @param oprReturnGoods 返货表实体对象
	 * @throws Exception 服务层异常
	 */
	public void saveRegistrationService(OprReturnGoods oprReturnGoods) throws Exception;

	
	/**
	 * 返货入库保存方法
	 * @param ids 返货表ID字符串
	 * @param returnStatus 运作状态辅助表要修改的状态
	 * @throws Exception 服务层异常
	 */
	public void saveEnterStockService(String ids, Long returnStatus)  throws Exception;


	
	/**
	 * 判断是否可以返货登记，返回一个传真对象
	 * @param dno
	 * @return 一个传真对象
	 * @throws Exception
	 */
	public OprFaxIn allowRegistration(Long dno) throws Exception;
	
	/**
	 * @param dno 要查找的配送单号
	 * @return 交接主单号
	 * @throws Exception 服务层异常
	 */
	public Long findMaxOvermemoNoByDno(Long dno)throws Exception;

	/**返货审核，调用财务接口和成本接口
	 * @param returnGoodsId 返货编号
	 * @throws Exception 服务层异常
	 */
	public void auditReturnGoods(Long returnGoodsId)throws Exception;


	/**返货统计
	 * @param map 返货统计条件
	 * @throws Exception 服务层异常
	 */
	public String findTotalCountService(Map<String, String> map)throws Exception;
}
