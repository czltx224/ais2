package com.xbwl.sys.dao.hibernateDaoImpl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.Page;
import com.xbwl.common.orm.PropertyFilter;
import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.SysStation;
import com.xbwl.sys.dao.IStationDao;

/**
 *author LiuHao
 *time Jun 21, 2011 9:36:19 AM
 */
@Repository("stationHibernateDaoImpl")
public class StationHibernateDaoImpl extends BaseDAOHibernateImpl<SysStation,Long> implements
		IStationDao {

	public List<SysStation> findStationByParentId(Long parentId)
			throws Exception {
		List<SysStation> list=this.find("from SysStation ss where ss.parentStationId=?", parentId);
		return list;
	}

}
