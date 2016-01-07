package com.xbwl.finance.Service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.orm.Page;
import com.xbwl.common.orm.PropertyFilter;
import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.entity.FiArrearset;
import com.xbwl.finance.Service.IFiArrearsetService;
import com.xbwl.finance.dao.IFiArrearsetDao;

@Service("fiArrearsetServiceImpl")
@Transactional(rollbackFor={Exception.class})
public class FiArrearsetServiceImpl extends BaseServiceImpl<FiArrearset, Long>
		implements IFiArrearsetService {

	@Resource(name = "fiArrearsetHibernateDaoImpl")
	private IFiArrearsetDao fiArrearsetDao;

	@Override
	public IBaseDAO<FiArrearset, Long> getBaseDao() {
		return fiArrearsetDao;
	}

	public boolean isRepeat(Long customerId,Long departId){
		List<FiArrearset> list=this.fiArrearsetDao.find("from FiArrearset where customerId=? and departId=?", customerId,departId);
		if(list==null){
			return false;
		}
		
		if(list.size()>0){
			return true;
		}else{
			return false;
			}
	}

	public void save(FiArrearset entity)  {
		if(entity!=null&&(entity.getId()==null)){
			List<FiArrearset> list=this.fiArrearsetDao.find("from FiArrearset where customerId=? and departId=? ", entity.getCustomerId(),entity.getDepartId());
			if(list.size()>0){
				throw new ServiceException("此客商已存在欠款设置，不允许多次录入！");
			}
		}
		super.save(entity);
	}
	
	

}
