package com.xbwl.oper.stock.service;

import java.util.List;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.OprMainOrderAdjust;
import com.xbwl.oper.stock.vo.OprMainOrderAdjustVo;

/**
 * @author CaoZhili time Aug 8, 2011 9:56:44 AM
 * 
 * 主单调整记录表服务层接口
 */
public interface IOprMainOrderAdjustService extends
		IBaseService<OprMainOrderAdjust, Long> {


	/**
	 * 修改主单号和货物重量
	 * @param mainIds 修改对象的集合
	 * @throws ServiceException 服务层异常
	 */
	public void updateMainNoAndWeightService(List<OprMainOrderAdjustVo> mainIds) throws Exception;

}
