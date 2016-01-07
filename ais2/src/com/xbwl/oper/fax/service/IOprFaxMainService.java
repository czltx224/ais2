package com.xbwl.oper.fax.service;

import java.util.Map;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.OprFaxMain;

/**
 * @author CaoZhili
 * time Aug 29, 2011 10:29:48 AM
 */
public interface IOprFaxMainService extends IBaseService<OprFaxMain, Long>{

	
	/**
	 * 通过主单号去查询总件数和总重量
	 * @param flightMainNo 主单号
	 * @return 传真主单表对象
	 */
	public OprFaxMain getTotalByFlightMainNo(String flightMainNo)throws Exception;
	
	
	/**
	 * 通过主单号去查询主单表实体
	 * @param flightMainNo 主单号
	 * @return 传真主单表对象
	 */
	public OprFaxMain getOprFaxMainByFlightMainNo(String flightMainNo)throws Exception;
	
	
	/**
	 * 通过主单号创建时间查询主单信息
	 * @param map  创建时间和主单号
	 * @return  SQL字符串
	 * @throws Exception
	 */
	public String findFiDeliverycost(Map map) throws Exception;
	
	/**
	 * 通过主单号创建时间查询主单信息
	 * @param map  创建时间和主单号
	 * @return  SQL字符串
	 * @throws Exception
	 */
	public OprFaxMain findFiDeliveryByMatchStatus(String flightMainNo) throws Exception;
}
