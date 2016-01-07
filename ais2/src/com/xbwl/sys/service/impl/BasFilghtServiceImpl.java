package com.xbwl.sys.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.springframework.stereotype.Service;

import com.gdcn.bpaf.common.exception.ServiceException;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.orm.Page;
import com.xbwl.common.orm.PropertyFilter;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.entity.BasFlight;
import com.xbwl.sys.dao.IBasFlightDao;
import com.xbwl.sys.service.IBasFlightService;

@Service("basFilghtServiceImpl")
public class BasFilghtServiceImpl extends BaseServiceImpl<BasFlight,Long> implements
		IBasFlightService {
	
	@Resource(name="basFlightHibernateDaoImpl")
	private IBasFlightDao baseFlightDao;
	@Override
	public IBaseDAO getBaseDao() {
		return baseFlightDao;
	}
	@Override
	public void save(BasFlight entity) {
		Query query = null;
		if(null!=entity.getId() && entity.getId()>0){
			query = this.baseFlightDao.createQuery("from BasFlight where flightNumber=? and id!=?",
				 entity.getFlightNumber(),entity.getId());
		}else{
			query =this.baseFlightDao.createQuery("from BasFlight where flightNumber=? ",
				entity.getFlightNumber());
		}
		List<BasFlight> list = query.list();
		
		if(null==list || list.size()==0){
				super.save(entity);
		}else{
			throw new ServiceException("该航班已经存在！");
		}
	}
	
	public List<BasFlight> findFlightNo(String flightNo) throws Exception {
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		PropertyFilter filter = null;
		List<BasFlight> list = null;
		if(null!=flightNo && !"".equals(flightNo.trim())){
			filter = new PropertyFilter("LIKES_flightNumber",flightNo.toUpperCase());
			filters.add(filter);
		}
		Page page = new Page();
		page.setLimit(50);
		page.setStart(0);
		Page<BasFlight> rePage = this.baseFlightDao.findPage(page, filters);
		list = rePage.getResult();
		return list;
	}
}
