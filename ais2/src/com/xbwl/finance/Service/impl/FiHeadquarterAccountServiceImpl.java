package com.xbwl.finance.Service.impl;

import java.rmi.server.ServerCloneException;
import java.util.Date;

import javax.annotation.Resource;

import org.ralasafe.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.common.utils.DoubleUtil;
import com.xbwl.entity.FiCapitaaccount;
import com.xbwl.entity.FiCapitaaccountset;
import com.xbwl.entity.FiHeadquarterAccount;
import com.xbwl.finance.Service.IFiCapitaaccountService;
import com.xbwl.finance.Service.IFiCapitaaccountsetService;
import com.xbwl.finance.Service.IFiHeadquarterAccountService;
import com.xbwl.finance.dao.IFiHeadquarterAccountDao;

@Service("fiHeadquarterAccountServiceImpl")
@Transactional(rollbackFor={Exception.class})
public class FiHeadquarterAccountServiceImpl extends BaseServiceImpl<FiHeadquarterAccount,Long> implements
		IFiHeadquarterAccountService {

	@Resource(name="fiHeadquarterAccountHibernateDaoImpl")
	private IFiHeadquarterAccountDao fiHeadquarterAccountDao;
	
	//资金账号设置
	@Resource(name = "fiCapitaaccountsetServiceImpl")
	private IFiCapitaaccountsetService fiCapitaaccountsetService;
	
	//资金账号流水
	@Resource(name="fiCapitaaccountServiceImpl")
	private IFiCapitaaccountService fiCapitaaccountService;
	
	@Override
	public IBaseDAO getBaseDao() {
		return this.fiHeadquarterAccountDao;
	}
	
	public void verification(FiHeadquarterAccount fiHeadquarterAccount,User user) throws Exception{
		if(fiHeadquarterAccount.getStatus()==0L){
			throw new ServerCloneException("单据已作废!");
		}
		if(fiHeadquarterAccount.getVerificationStatus()==2L){
			throw new ServerCloneException("单据已核销!");
		}
		
		//拨付账号余额减少
		FiCapitaaccountset fiCapitaaccountsetPayment=this.fiCapitaaccountsetService.get(fiHeadquarterAccount.getVerificationCapId());//资金交接单账号
		if(fiCapitaaccountsetPayment==null) throw new ServiceException("核销账号不存在!");
		fiCapitaaccountsetPayment.setOpeningBalance(DoubleUtil.sub(fiCapitaaccountsetPayment.getOpeningBalance(),fiHeadquarterAccount.getAmount()));
		if(fiCapitaaccountsetPayment.getOpeningBalance()<0.0){
			throw new ServiceException("核销账号余额不足！");
		}
		this.fiCapitaaccountsetService.save(fiCapitaaccountsetPayment);
		//资金流水处理
		FiCapitaaccount fiCapitaaccountPayment = new FiCapitaaccount();
		fiCapitaaccountPayment.setBorrow(fiHeadquarterAccount.getAmount());
		fiCapitaaccountPayment.setBalance(fiCapitaaccountsetPayment.getOpeningBalance());// 账号余额
		fiCapitaaccountPayment.setFiCapitaaccountsetId(fiCapitaaccountsetPayment.getId());
		fiCapitaaccountPayment.setSourceData("总部交接单");
		fiCapitaaccountPayment.setRemark("总部返回收据手工核销");
		fiCapitaaccountPayment.setSourceNo(fiHeadquarterAccount.getId());
		this.fiCapitaaccountService.save(fiCapitaaccountPayment);// 保存流水账
		
		//修改总部交接单核销状态
		fiHeadquarterAccount.setVerificationStatus(2L);
		fiHeadquarterAccount.setVerificationDate(new Date());
		fiHeadquarterAccount.setVerificationUser(user.get("name")+"");
		this.fiHeadquarterAccountDao.save(fiHeadquarterAccount);
	}
	
	public void revocation(Long id) throws Exception{
		FiHeadquarterAccount fiHeadquarterAccount=this.fiHeadquarterAccountDao.get(id);
		if(fiHeadquarterAccount==null){
			throw new ServerCloneException("单据不存在!");
		}
		if(fiHeadquarterAccount.getStatus()==0L){
			throw new ServerCloneException("单据已经作废，不允许重复操作!");
		}
		if(fiHeadquarterAccount.getVerificationStatus()==2L){
			throw new ServerCloneException("单据已核销，不允许作废!");
		}
		fiHeadquarterAccount.setStatus(0L);
		this.fiHeadquarterAccountDao.save(fiHeadquarterAccount);
		
	}
}
