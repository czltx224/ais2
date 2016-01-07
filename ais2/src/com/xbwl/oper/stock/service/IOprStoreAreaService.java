package com.xbwl.oper.stock.service;

import java.util.List;
import java.util.Map;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.OprFaxIn;
import com.xbwl.entity.OprReturnGoods;
import com.xbwl.entity.OprStoreArea;

/**
 * @author CaoZhili
 * 区域管理服务层接口
 */
public interface IOprStoreAreaService extends IBaseService<OprStoreArea, Long> {

	/**
	 * 从客商表中查询去向
	 * @param custprops
	 * @param cusName
	 * @return
	 * @throws Exception
	 */
	public List<Map> findCusNameService(String[] custprops,String cusName)throws Exception;

	/**
	 * 从客商表中查询代理区域
	 * @param pkAreacl
	 * @return
	 * @throws Exception
	 */
	public List<Map>  findPkAreaclService(String pkAreacl)throws Exception;
	/**
	 * 获得库存区域
	 * @param oprStoreArea
	 * @param ofi
	 * @return
	 * @throws Exception
	 */
	public String getStockArea(OprStoreArea oprStoreArea,OprFaxIn ofi)throws Exception;
}
