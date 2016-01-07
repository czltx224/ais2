package com.xbwl.sys.service;

import java.util.List;

import org.apache.poi.hssf.record.formula.functions.T;

import com.xbwl.common.orm.Page;
import com.xbwl.common.orm.PropertyFilter;
import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.OprExceptionType;
import com.xbwl.entity.SysCommonMenu;

/**
 * author shuw
 * time Mar 1, 2012 3:23:20 PM
 * 系统常用菜单 
 */
public interface ISysCommonMenuService extends IBaseService<SysCommonMenu, Long>{

	/**
	 * 保存用户常用菜单集合，并验证权限
	 * @param list
	 * @throws Exception
	 */
	public void saveList(List<SysCommonMenu> list) throws Exception;
	
	/**
	 * 用户查询调用方法，主要是在查询前去掉用户已无权限的菜单
	 * @param page
	 * @param list 条件集合
	 * @return
	 * @throws Exception
	 */
	public Page<SysCommonMenu> queryList(Page  page,List<PropertyFilter> list ) throws  Exception;
}
