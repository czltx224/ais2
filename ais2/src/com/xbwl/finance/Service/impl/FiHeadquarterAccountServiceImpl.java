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
	
	//�ʽ��˺�����
	@Resource(name = "fiCapitaaccountsetServiceImpl")
	private IFiCapitaaccountsetService fiCapitaaccountsetService;
	
	//�ʽ��˺���ˮ
	@Resource(name="fiCapitaaccountServiceImpl")
	private IFiCapitaaccountService fiCapitaaccountService;
	
	@Override
	public IBaseDAO getBaseDao() {
		return this.fiHeadquarterAccountDao;
	}
	
	public void verification(FiHeadquarterAccount fiHeadquarterAccount,User user) throws Exception{
		if(fiHeadquarterAccount.getStatus()==0L){
			throw new ServerCloneException("����������!");
		}
		if(fiHeadquarterAccount.getVerificationStatus()==2L){
			throw new ServerCloneException("�����Ѻ���!");
		}
		
		//�����˺�������
		FiCapitaaccountset fiCapitaaccountsetPayment=this.fiCapitaaccountsetService.get(fiHeadquarterAccount.getVerificationCapId());//�ʽ𽻽ӵ��˺�
		if(fiCapitaaccountsetPayment==null) throw new ServiceException("�����˺Ų�����!");
		fiCapitaaccountsetPayment.setOpeningBalance(DoubleUtil.sub(fiCapitaaccountsetPayment.getOpeningBalance(),fiHeadquarterAccount.getAmount()));
		if(fiCapitaaccountsetPayment.getOpeningBalance()<0.0){
			throw new ServiceException("�����˺����㣡");
		}
		this.fiCapitaaccountsetService.save(fiCapitaaccountsetPayment);
		//�ʽ���ˮ����
		FiCapitaaccount fiCapitaaccountPayment = new FiCapitaaccount();
		fiCapitaaccountPayment.setBorrow(fiHeadquarterAccount.getAmount());
		fiCapitaaccountPayment.setBalance(fiCapitaaccountsetPayment.getOpeningBalance());// �˺����
		fiCapitaaccountPayment.setFiCapitaaccountsetId(fiCapitaaccountsetPayment.getId());
		fiCapitaaccountPayment.setSourceData("�ܲ����ӵ�");
		fiCapitaaccountPayment.setRemark("�ܲ������վ��ֹ�����");
		fiCapitaaccountPayment.setSourceNo(fiHeadquarterAccount.getId());
		this.fiCapitaaccountService.save(fiCapitaaccountPayment);// ������ˮ��
		
		//�޸��ܲ����ӵ�����״̬
		fiHeadquarterAccount.setVerificationStatus(2L);
		fiHeadquarterAccount.setVerificationDate(new Date());
		fiHeadquarterAccount.setVerificationUser(user.get("name")+"");
		this.fiHeadquarterAccountDao.save(fiHeadquarterAccount);
	}
	
	public void revocation(Long id) throws Exception{
		FiHeadquarterAccount fiHeadquarterAccount=this.fiHeadquarterAccountDao.get(id);
		if(fiHeadquarterAccount==null){
			throw new ServerCloneException("���ݲ�����!");
		}
		if(fiHeadquarterAccount.getStatus()==0L){
			throw new ServerCloneException("�����Ѿ����ϣ��������ظ�����!");
		}
		if(fiHeadquarterAccount.getVerificationStatus()==2L){
			throw new ServerCloneException("�����Ѻ���������������!");
		}
		fiHeadquarterAccount.setStatus(0L);
		this.fiHeadquarterAccountDao.save(fiHeadquarterAccount);
		
	}
}
