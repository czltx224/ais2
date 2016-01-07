package com.xbwl.finance.Service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.Query;
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
import com.xbwl.common.utils.DoubleUtil;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.FiIncomeAccount;
import com.xbwl.finance.Service.IFiIncomeAccountService;
import com.xbwl.finance.Service.IFiIncomeService;
import com.xbwl.finance.Service.IFiPaidService;
import com.xbwl.finance.dao.IFiIncomeAccountDao;

@Service("fiIncomeAccountServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class FiIncomeAccountServiceImpl extends BaseServiceImpl<FiIncomeAccount,Long> implements
		IFiIncomeAccountService {

	@Resource(name="fiIncomeAccountHibernateDaoImpl")
	private IFiIncomeAccountDao fiIncomeAccountDao;
	
	//收入
	@Resource(name="fiIncomeServiceImpl")
	private IFiIncomeService fiIncomeService;
	
	//实收实付
	@Resource(name = "fiPaidServiceImpl")
	private IFiPaidService fiPaidService;
	
	@Override
	public IBaseDAO getBaseDao() {
		return this.fiIncomeAccountDao;
	}

	public void addAccountSingle(Long departId,Date accountData) throws Exception{
		Long seq=null;
		String startTime =null;
		String endTime = null;
		String hql=null;
		String sqlseq=null;
		
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		if(accountData!=null){
			startTime = formatter.format(accountData)+" 00:00:00";
			endTime = formatter.format(accountData)+" 23:59:59";
		}else{
			throw new ServiceException("交账日期不存在！");
		}
		
		//验证是否已交账
		hql="from FiIncomeAccount f where f.accountData=? and f.status=1 and f.departId=?";
		List<FiIncomeAccount> list=this.fiIncomeAccountDao.find(hql,accountData,departId);
		if(list.size()>0){
			throw new ServiceException(formatter.format(accountData)+"已生成过了交账报表，不允许重复生成!");
		}
		//生成交账批次号
		sqlseq="select SEQ_BATCH.nextval SEQ from dual";
		Map seqid=(Map) this.fiIncomeAccountDao.createSQLQuery(sqlseq).list().get(0) ;
		seq=Long.valueOf(seqid.get("SEQ")+"");
		
		//生成收入交账报表
		this.fiIncomeService.addAccountSingle(departId, startTime, endTime, seq);
		
		//生成收银交账报表
		this.fiPaidService.addAccountSingle(departId, startTime, endTime, seq);
	}
	
	public void revocation(Long batchNo) throws Exception{
		String hql=null;
		if(batchNo==null){
			throw new ServiceException("交账单号为空");
		}
		
		hql="from FiIncomeAccount f where f.batchNo=?";
		List<FiIncomeAccount> list=this.fiIncomeAccountDao.find(hql,batchNo);
		if(list==null&&list.size()==0){
			throw new ServiceException("交账单号对账的交账报表不存在");
		}
		for(FiIncomeAccount fiIncomeAccount:list){
			if(fiIncomeAccount.getStatus()==0L){
				throw new ServiceException("交账单号对应的"+fiIncomeAccount.getTypeName()+"报表已作废！");
			}else if(fiIncomeAccount.getAccountStatus()==1L){
				throw new ServiceException("交账单号对应的"+fiIncomeAccount.getTypeName()+"报表已经交账！");
			}
			
			fiIncomeAccount.setStatus(0L);
			this.fiIncomeAccountDao.save(fiIncomeAccount);
		}
		
	}
	
	public void confirmAccountSingle(Long batchNo) throws Exception{
		String hql=null;
		if(batchNo==null){
			throw new ServiceException("交账单号为空");
		}
		
		hql="from FiIncomeAccount f where f.batchNo=?";
		List<FiIncomeAccount> list=this.fiIncomeAccountDao.find(hql,batchNo);
		if(list==null&&list.size()==0){
			throw new ServiceException("交账单号对账的交账报表不存在");
		}
		for(FiIncomeAccount fiIncomeAccount:list){
			if(fiIncomeAccount.getStatus()==0L){
				throw new ServiceException("交账单号对应的"+fiIncomeAccount.getTypeName()+"报表已作废！");
			}else if(fiIncomeAccount.getAccountStatus()==1L){
				throw new ServiceException("交账单号对应的"+fiIncomeAccount.getTypeName()+"报表已经交账！");
			}
			
			fiIncomeAccount.setAccountStatus(1L);
			this.fiIncomeAccountDao.save(fiIncomeAccount);
		}
		
	}
}
