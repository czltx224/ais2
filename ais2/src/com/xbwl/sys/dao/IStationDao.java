package com.xbwl.sys.dao;

import java.util.List;

import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.entity.BasArea;
import com.xbwl.entity.SysStation;

/**
 *author LiuHao
 *time Jun 21, 2011 9:35:19 AM
 */
public interface IStationDao extends IBaseDAO<SysStation,Long> {
	public List<SysStation> findStationByParentId(Long parentId) throws Exception;
}
