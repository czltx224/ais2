package com.xbwl.oper.stock.service;

import java.util.Map;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.OprReturnGoods;
import com.xbwl.entity.OprStock;

/**
 * @author Administrator
 *操作库存表
 */
public interface IOprStockService  extends IBaseService<OprStock,Long>{

	
	/**
	 * 返货保存库存信息
	 * @param oprReturnGoods (dno,returnNum,returnDepart 三个参数设置即可)
	 * @throws ServiceException
	 */
	public void saveReturnGoodsStock(OprReturnGoods oprReturnGoods)throws Exception;
	
	
	/**
	 * 获取正常库存查询语句
	 * @param map 查询条件map
	 * @return 查询语句字符串
	 * @throws Exception 服务层异常
	 */
	public String getStockSql(Map<String,String> map)throws Exception;


	/**正常库存转异常库存
	 * @param ids 库存ID数组
	 * @param remark 备注
	 * @throws Exception
	 */
	public void toExceptionStockService(String[] ids,String remark)throws Exception;
}
