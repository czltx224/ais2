package com.xbwl.finance.Service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.entity.FiReconciliationAccount;
import com.xbwl.finance.Service.IFiReconciliationAccountService;
import com.xbwl.finance.dao.IFiReconciliationAccountDao;

/**
 * author shuw
 * time Dec 7, 2011 2:30:09 PM
 */
@Service("fiReconciliationAccountServiceImpl")
@Transactional(rollbackFor={Exception.class})
public class FiReconciliationAccountServiceImpl  extends
								BaseServiceImpl<FiReconciliationAccount, Long> implements
								IFiReconciliationAccountService {

	@Resource(name="fiReconciliationAccountHibernateDaoImpl")
	private IFiReconciliationAccountDao fiReconciliationAccountDao;
	
	public void save(FiReconciliationAccount entity) {
		User user= WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		Long bussDepartId=Long.parseLong(user.get("bussDepart")+"");
		if(entity.getId()==null){
			List<FiReconciliationAccount>list =find(" from FiReconciliationAccount f where f.isDelete=? and f.departId=? and f.nature=? ",entity.getIsDelete(),bussDepartId,entity.getNature());
			if(list.size()>0){
				throw new ServiceException("此类型的账号已存在不重复录入");
			}
		}
		super.save(entity);
	}

	@Override
	public IBaseDAO<FiReconciliationAccount, Long> getBaseDao() {
		return fiReconciliationAccountDao;
	}

}
