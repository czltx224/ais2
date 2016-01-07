package com.xbwl.oper.reports.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.hibernate.Query;
import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdcn.bpaf.common.exception.ServiceException;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.entity.OprEnterPortStandard;
import com.xbwl.oper.reports.dao.IOprEnterPortStandardDao;
import com.xbwl.oper.reports.service.IOprEnterPortStandardService;

/**
 * author CaoZhili time Nov 9, 2011 11:01:43 AM
 */
@Service("oprEnterPortStandardServiceImpl")
@Transactional(rollbackFor=Exception.class)
public class OprEnterPortStandardServiceImpl extends
		BaseServiceImpl<OprEnterPortStandard, Long> implements
		IOprEnterPortStandardService {

	@Resource(name="oprEnterPortStandardHibernateDaoImpl")
	private IOprEnterPortStandardDao enterPortStandardDao;
	
	@Override
	public IBaseDAO<OprEnterPortStandard, Long> getBaseDao() {
		return this.enterPortStandardDao;
	}

	@Override
	public void save(OprEnterPortStandard entity) {
		User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		String userName = user.get("name") + "";
		entity.setLastCountName(userName);
		entity.setLastCountTime(new Date());
		
		Query query = null;
		if(null!=entity.getId() && entity.getId()>0){
			query = this.enterPortStandardDao.createQuery("from OprEnterPortStandard where deptId=? and id!=?",
			entity.getDeptId(),entity.getId());
		}else{
			query =this.enterPortStandardDao.createQuery("from OprEnterPortStandard where deptId=? ",
			entity.getDeptId());
		}
		List<OprEnterPortStandard> list = query.list();
		
		if(null==list || list.size()==0){
				super.save(entity);
		}else{
			throw new ServiceException("该部门已经存在！");
		}
	}
}
