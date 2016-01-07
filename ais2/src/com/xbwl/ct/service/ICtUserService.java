package com.xbwl.ct.service;

import java.util.List;
import java.util.Map;

import com.xbwl.common.service.IBaseService;

import dto.CtUserDto;

/**
 * EDI 登陆用户操作
 * author shuw
 * time May 2, 2012 9:55:12 AM
 */
public interface ICtUserService extends IBaseService<CtUserDto, Long> {

	/**
	 * 查找EDI用户
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List findCtUser(Map<String, String> map)throws Exception;

	/**
	 * 删除EDI用户
	 * @param split
	 * @throws Exception
	 */
	public void deleteCtUser(String[] ids)throws Exception;

}
