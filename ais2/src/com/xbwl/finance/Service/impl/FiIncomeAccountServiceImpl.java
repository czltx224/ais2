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
	
	//����
	@Resource(name="fiIncomeServiceImpl")
	private IFiIncomeService fiIncomeService;
	
	//ʵ��ʵ��
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
			throw new ServiceException("�������ڲ����ڣ�");
		}
		
		//��֤�Ƿ��ѽ���
		hql="from FiIncomeAccount f where f.accountData=? and f.status=1 and f.departId=?";
		List<FiIncomeAccount> list=this.fiIncomeAccountDao.find(hql,accountData,departId);
		if(list.size()>0){
			throw new ServiceException(formatter.format(accountData)+"�����ɹ��˽��˱����������ظ�����!");
		}
		//���ɽ������κ�
		sqlseq="select SEQ_BATCH.nextval SEQ from dual";
		Map seqid=(Map) this.fiIncomeAccountDao.createSQLQuery(sqlseq).list().get(0) ;
		seq=Long.valueOf(seqid.get("SEQ")+"");
		
		//�������뽻�˱���
		this.fiIncomeService.addAccountSingle(departId, startTime, endTime, seq);
		
		//�����������˱���
		this.fiPaidService.addAccountSingle(departId, startTime, endTime, seq);
	}
	
	public void revocation(Long batchNo) throws Exception{
		String hql=null;
		if(batchNo==null){
			throw new ServiceException("���˵���Ϊ��");
		}
		
		hql="from FiIncomeAccount f where f.batchNo=?";
		List<FiIncomeAccount> list=this.fiIncomeAccountDao.find(hql,batchNo);
		if(list==null&&list.size()==0){
			throw new ServiceException("���˵��Ŷ��˵Ľ��˱�������");
		}
		for(FiIncomeAccount fiIncomeAccount:list){
			if(fiIncomeAccount.getStatus()==0L){
				throw new ServiceException("���˵��Ŷ�Ӧ��"+fiIncomeAccount.getTypeName()+"���������ϣ�");
			}else if(fiIncomeAccount.getAccountStatus()==1L){
				throw new ServiceException("���˵��Ŷ�Ӧ��"+fiIncomeAccount.getTypeName()+"�����Ѿ����ˣ�");
			}
			
			fiIncomeAccount.setStatus(0L);
			this.fiIncomeAccountDao.save(fiIncomeAccount);
		}
		
	}
	
	public void confirmAccountSingle(Long batchNo) throws Exception{
		String hql=null;
		if(batchNo==null){
			throw new ServiceException("���˵���Ϊ��");
		}
		
		hql="from FiIncomeAccount f where f.batchNo=?";
		List<FiIncomeAccount> list=this.fiIncomeAccountDao.find(hql,batchNo);
		if(list==null&&list.size()==0){
			throw new ServiceException("���˵��Ŷ��˵Ľ��˱�������");
		}
		for(FiIncomeAccount fiIncomeAccount:list){
			if(fiIncomeAccount.getStatus()==0L){
				throw new ServiceException("���˵��Ŷ�Ӧ��"+fiIncomeAccount.getTypeName()+"���������ϣ�");
			}else if(fiIncomeAccount.getAccountStatus()==1L){
				throw new ServiceException("���˵��Ŷ�Ӧ��"+fiIncomeAccount.getTypeName()+"�����Ѿ����ˣ�");
			}
			
			fiIncomeAccount.setAccountStatus(1L);
			this.fiIncomeAccountDao.save(fiIncomeAccount);
		}
		
	}
}
