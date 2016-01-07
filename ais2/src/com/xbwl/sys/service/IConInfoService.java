package com.xbwl.sys.service;

import java.util.List;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.ConsigneeInfo;

/**
 *@author LiuHao
 *@time 2011-7-19 下午03:28:50
 */
public interface IConInfoService extends IBaseService<ConsigneeInfo, Long> {
	/**
	 * 根据收货人电话查询收货人信息
	 * @param tel
	 * @return
	 */
	public List<ConsigneeInfo> findConsigneeInfoByTel(String tel) throws Exception;

}
