package com.xbwl.sys.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.dozer.DozerBeanMapper;
import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.orm.Page;
import com.xbwl.common.orm.PropertyFilter;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.entity.SysDepart;
import com.xbwl.entity.SysStation;
import com.xbwl.rbac.vo.SysDepartVo;
import com.xbwl.sys.dao.IStationDao;
import com.xbwl.sys.service.IStationService;

/**
 *author LiuHao
 *time Jun 21, 2011 9:33:46 AM
 */
@Service("stationServiceImpl")
public class StationServiceImpl extends BaseServiceImpl<SysStation,Long> implements
		IStationService {
	@Resource(name="stationHibernateDaoImpl")
	private IStationDao stationDao;
	@Resource
	private  DozerBeanMapper dozer;
	
	@Override
	public IBaseDAO getBaseDao() {
		return stationDao;
	}
	public IStationDao getStationDao() {
		return stationDao;
	}
	public void setStationDao(IStationDao stationDao) {
		this.stationDao = stationDao;
	}
	
	@Transactional(rollbackFor={Exception.class},readOnly=true)
	public List<SysStation> findStationByParentId(Long parentId)
			throws Exception {
		return stationDao.findStationByParentId(parentId);
	}
//	public Page<SysStation> findPageByStation(Page page,
//			Long parentId) throws Exception {
//		List voList = new ArrayList();
//		stationDao.findPage(page,"from SysStation ss where ss.parentStation.stationId=?",parentId);
//		List resultList=page.getResult();
//		for (Object object : resultList) {
//			voList.add(dozer.map(object, SysStationVo.class));
//			}
//		page.setResult(voList);
//		return page;
//	}
	

}
