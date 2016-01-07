package com.xbwl.sys.service;

import java.util.List;

import com.xbwl.common.orm.Page;
import com.xbwl.common.orm.PropertyFilter;
import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.SysStation;

/**
 *author LiuHao
 *time Jun 21, 2011 9:32:33 AM
 */
public interface IStationService extends IBaseService<SysStation,Long> {
	/**
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	public List<SysStation> findStationByParentId(Long parentId) throws Exception;
	
//	//分页查询岗位信息，封装到VO
//	public Page<SysStation> findPageByStation(Page page,Long parentId) throws Exception;
}
