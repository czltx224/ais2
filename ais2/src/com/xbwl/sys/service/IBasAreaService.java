package com.xbwl.sys.service;

import java.util.List;

import com.xbwl.common.orm.Page;
import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.BasArea;

/**
 *author LiuHao
 *time Jun 22, 2011 11:55:45 AM
 */
public interface IBasAreaService extends IBaseService<BasArea,Long> {
	/**
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	public List<BasArea> getBasAreaTreeByPrentId(Long parentId) throws Exception;
	/**
	 * @param page
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	public Page findArea(Page page,Long parentId)throws Exception;
	/**
	 * @param page
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Page findAreaById(Page page,Long id)throws Exception;
	/**
	 * 查询地区信息
	 * @author LiuHao
	 * @time Mar 14, 2012 8:45:03 AM 
	 * @param city
	 * @param town
	 * @param street
	 * @return
	 * @throws Exception
	 */
	public Page getAreaMsg(Page page,String city,String town,String street)throws Exception;
	
	
	/**
	 * 判断在地区表存在该字段
	 * @param string 
	 * @return
	 * @throws Exception
	 */
	public boolean isBasAreaExistOfString(String string) throws Exception;
}
