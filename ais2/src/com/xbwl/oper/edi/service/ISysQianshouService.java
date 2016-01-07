package com.xbwl.oper.edi.service;

import java.util.Map;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.SysQianshou;

/**
 * 短信签收记录表服务层接口
 * @author czl
 * @date 2012-05-28
 *
 */
public interface ISysQianshouService extends IBaseService<SysQianshou, String> {

	/**
	 * 扫描短信签收记录表，并进行相关处理
	 * @throws Exception
	 */
	public void scanningSysQianshouService()throws Exception;

	/**
	 * 短信签收历史记录查询SQL获取
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String findRecordList(Map<String, String> map)throws Exception;

}
