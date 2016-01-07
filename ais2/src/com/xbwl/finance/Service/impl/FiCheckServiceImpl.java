package com.xbwl.finance.Service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.Criterion;
import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.orm.Page;
import com.xbwl.common.orm.PropertyFilter;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.entity.FiCheck;
import com.xbwl.finance.Service.IFiCheckService;
import com.xbwl.finance.Service.IFiPaymentService;
import com.xbwl.finance.dao.IFiCheckDao;


@Service("fiCheckServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class FiCheckServiceImpl extends BaseServiceImpl<FiCheck,Long> implements
		IFiCheckService {

	@Resource(name="fiCheckHibernateImpl")
	private IFiCheckDao fiCheckDao;
	
	@Resource(name = "fiPaymentServiceImpl")
	private IFiPaymentService fiPaymentService;
	
	@Override
	public IBaseDAO getBaseDao() {
		return this.fiCheckDao;
	}

	public void deleteByStatus(Long id, String ts) throws Exception {
		FiCheck fiCheck = fiCheckDao.get(id);
		if(!ts.equals(fiCheck.getTs())){
			throw new ServiceException("时间戳已改变，别人操作了此数据，请查询后再操作");
		}
		User user= WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		fiCheck.setInvalidDate(new Date());
		fiCheck.setInvalidStatus(1l);
		fiCheck.setInvalidUser(user.get("name")+"");
		fiCheckDao.save(fiCheck);
	}
	
	public void checkAudit(Long id,String ts)throws Exception{
		FiCheck fiCheck = fiCheckDao.get(id);
		if(!ts.equals(fiCheck.getTs())){
			throw new ServiceException("时间戳已改变，别人操作了此数据，请查询后再操作");
		}
		if(fiCheck.getConfirmStatus()==1L){
			throw new ServiceException("已做过到账确认，不能重复操作");
		}
		User user= WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		fiCheck.setConfirmDate(new Date());
		fiCheck.setConfirmStatus(1l);
		fiCheck.setConfirmUser(user.get("name")+"");
		fiCheckDao.save(fiCheck);
		//
		this.fiPaymentService.checkAudit(fiCheck);
	}

}
