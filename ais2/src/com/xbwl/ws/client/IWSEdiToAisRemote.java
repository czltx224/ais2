package com.xbwl.ws.client;

import java.util.List;
import java.util.Map;

import com.xbwl.ws.client.result.base.WSResult;

import dto.CtEstimateDto;
import dto.CtTmdDto;
import dto.CtUserDto;
import dto.OprSignDto;

/**
 * EDI对AIS2.0外部接口
 * @author czl
 * @date 2012-05-24
 */
public interface IWSEdiToAisRemote {

	/**
	 * 保存到EDI主表
	 * @param dto
	 * @return
	 */
	public WSResult saveToCtTmd(CtTmdDto dto);
	
	/**
	 * 保存到预计货量表
	 * @return
	 */
	public WSResult saveToCtEstimate(CtEstimateDto dto);
	
	/**
	 * 保存CT_USER用户对外接口
	 * @param dto
	 * @return
	 */
	public WSResult saveToCtUser(CtUserDto dto);
	
	/**
	 * 查找EDI用户对外接口
	 * @param map
	 * @return
	 */
	public List findCtUser(Map<String,String> map);

	/**
	 * 删除EDI用户远程接口
	 * @param ids
	 * @return
	 */
	public WSResult deleteCtuser(String[] ids);
	
	/**
	 * 短信签收扫描回写EDI签收表
	 * @param dto
	 * @return
	 */
	public WSResult messageRemoteSign(OprSignDto dto);
	
}
