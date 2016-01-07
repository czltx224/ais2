package com.xbwl.oper.stock.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.hibernate.Query;
import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.entity.OprLoadingbrigadTime;
import com.xbwl.oper.stock.dao.IOprLoadingbrigadeTimeDao;
import com.xbwl.oper.stock.service.IOprLoadingbrigadeTimeService;

/**
 * author shuw
 * time Sep 20, 2011 7:58:08 PM
 */
@Service("oprLoadingbrigadeTimeServiceImpl")
@Transactional(rollbackFor=Exception.class)
public class OprLoadingbrigadeTimeServiceImpl extends
				BaseServiceImpl<OprLoadingbrigadTime, Long> implements
				IOprLoadingbrigadeTimeService {

	@Resource(name = "oprLoadingbrigadeTimeHibernateDaoImpl")
	private IOprLoadingbrigadeTimeDao oprLoadingbrigadeTimeDao;
	
	@Override
	public IBaseDAO<OprLoadingbrigadTime, Long> getBaseDao() {
		return oprLoadingbrigadeTimeDao;
	}

//	@Override
//	public void save(OprLoadingbrigadTime entity) {
//		User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
//		Long bussDepartId = Long.parseLong(user.get("bussDepart") + "");
//		
//		Query query = null;
//		if(null!=entity.getId() && entity.getId()>0){
//			query = this.oprLoadingbrigadeTimeDao.createQuery("from OprLoadingbrigadTime where departId=? and id!=?",
//					bussDepartId,entity.getId());
//		}else{
//			query = this.oprLoadingbrigadeTimeDao.createQuery("from OprLoadingbrigadTime where departId=?",
//					bussDepartId);
//		}
//		List<OprLoadingbrigadTime> list = query.list();
//		if(null==list || list.size()==0){
//			super.save(entity);
//		}else{
//			throw new ServiceException("该部门已经有排班！");
//		}
//	}
	
	
}
