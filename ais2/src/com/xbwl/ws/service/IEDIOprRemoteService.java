package com.xbwl.ws.service;

import com.xbwl.ws.client.result.base.WSResult;

import dto.OprHistoryDto;
import dto.OprRemarkDto;
import dto.OprSignDto;

/**
 * EDI远程调用接口
 * @author czl
 * @date 2012-05-22
 */
public interface IEDIOprRemoteService {

	/**
	 * 保存到签收表的外部接口
	 * @return
	 */
	public WSResult saveToOprSign(OprSignDto dto);
	
	
	/**
	 * 更新备注外部接口
	 * @param dto
	 * @return
	 */
	public WSResult saveNewRemark(OprRemarkDto dto);
	
	/**
	 * 保存到OPR_HISTRORY操作历史记录表
	 * @param dto
	 * @return
	 */
	public WSResult saveToOprHistroy(OprHistoryDto dto);
}
