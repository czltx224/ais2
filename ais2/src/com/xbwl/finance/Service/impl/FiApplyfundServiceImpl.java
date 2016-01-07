package com.xbwl.finance.Service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.common.utils.DoubleUtil;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.FiApplyfund;
import com.xbwl.entity.FiCapitaaccount;
import com.xbwl.entity.FiCapitaaccountset;
import com.xbwl.entity.FiFundstransfer;
import com.xbwl.finance.Service.IFiApplyfundService;
import com.xbwl.finance.Service.IFiCapitaaccountService;
import com.xbwl.finance.Service.IFiCapitaaccountsetService;
import com.xbwl.finance.Service.IFiFundstransferService;
import com.xbwl.finance.dao.IFiApplyfundDao;

@Service("fiApplyfundServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class FiApplyfundServiceImpl extends BaseServiceImpl<FiApplyfund,Long> implements
		IFiApplyfundService {

	@Resource(name="fiApplyfundHibernateDaoImpl")
	private IFiApplyfundDao fiApplyfundDao;
	
	//�ʽ𽻽ӵ�
	@Resource(name="fiFundstransferServiceImpl")
	private IFiFundstransferService fiFundstransferService;
	
	//�ʽ��˺�����
	@Resource(name = "fiCapitaaccountsetServiceImpl")
	private IFiCapitaaccountsetService fiCapitaaccountsetService;
	
	//�ʽ��˺���ˮ
	@Resource(name="fiCapitaaccountServiceImpl")
	private IFiCapitaaccountService fiCapitaaccountService;
	
	@Override
	public IBaseDAO getBaseDao() {
		return this.fiApplyfundDao;
	}
	
	public void saveApplyfund(FiApplyfund fiApplyfund) throws Exception{
		if(fiApplyfund==null) throw new ServiceException("�ʽ�����ʵ�岻���ڣ�");
		this.fiApplyfundDao.save(fiApplyfund);
	}
	
	public void invalidApplyfund(FiApplyfund fiApplyfund) throws Exception{
		if(fiApplyfund==null) throw new ServiceException("�ʽ�����ʵ�岻���ڣ�");
		if(fiApplyfund.getId()==null) throw new ServiceException("�ʽ����뵥ID�����ڣ�");
		FiApplyfund fa=this.fiApplyfundDao.get(fiApplyfund.getId());
		if(fa.getStatus()==0L)  throw new ServiceException("�����ϣ��������ظ����ϣ�");
		if(fa.getStatus()==1L)  throw new ServiceException("�ѷ�������������ϣ�");
		if(fa.getAuditStatus()==2L)  throw new ServiceException("����ˣ��������Ϻˣ�");
		fa.setStatus(0L);
		this.fiApplyfundDao.save(fa);
	}
	
	public void auditApplyfund(FiApplyfund fiApplyfund) throws Exception{
		if(fiApplyfund==null) throw new ServiceException("�ʽ�����ʵ�岻���ڣ�");
		if(fiApplyfund.getId()==null) throw new ServiceException("�ʽ����뵥ID�����ڣ�");
		if(fiApplyfund.getStatus()==0L)  throw new ServiceException("�����ϣ���������ˣ�");
		if(fiApplyfund.getStatus()==1L)  throw new ServiceException("�ѷ������������ˣ�");
		if(fiApplyfund.getAuditStatus()==2L)  throw new ServiceException("����ˣ���������ˣ�");
		
		//ֱ�Ӳ���
		if(fiApplyfund.getIsSit()==2L){
			//�����˺�
			FiCapitaaccountset fiCapitaaccountsetPayment=this.fiCapitaaccountsetService.get(fiApplyfund.getPaymentAccountId());
			//�����˺�
			FiCapitaaccountset fiCapitaaccountsetReceivables=this.fiCapitaaccountsetService.get(fiApplyfund.getAppAccountId());
			
			//�����˺���֤
			if(fiCapitaaccountsetPayment==null){
				throw new ServiceException("�����˺Ų�����!");
			}
			if(fiCapitaaccountsetPayment.getResponsibleId().equals(fiCapitaaccountsetReceivables.getResponsibleId())){
				throw new ServiceException("�������˺Ÿ������벦���˺Ÿ����˲���Ϊͬһ�ˡ�");
			}
			//�����˺���֤
			if(fiCapitaaccountsetReceivables==null){
				throw new ServiceException("�����˺Ų�����!");
			}
			
			//�����˺�������
			fiCapitaaccountsetPayment.setOpeningBalance(DoubleUtil.sub(fiCapitaaccountsetPayment.getOpeningBalance(),fiApplyfund.getPaymentAmount()));
			if(fiCapitaaccountsetPayment.getOpeningBalance()<0.0){
				throw new ServiceException("�ʽ𽻽ӵ������˺����㣡");
			}
			this.fiCapitaaccountsetService.save(fiCapitaaccountsetPayment);
			
			//�ʽ���ˮ����
			FiCapitaaccount fiCapitaaccountPayment = new FiCapitaaccount();
			fiCapitaaccountPayment.setBorrow(fiApplyfund.getPaymentAmount());
			fiCapitaaccountPayment.setBalance(fiCapitaaccountsetPayment.getOpeningBalance());// �˺����
			fiCapitaaccountPayment.setFiCapitaaccountsetId(fiCapitaaccountsetPayment.getId());
			fiCapitaaccountPayment.setSourceData("�ʽ����뵥");
			fiCapitaaccountPayment.setSourceNo(fiApplyfund.getId());
			fiCapitaaccountPayment.setRemark("���,ȷ��ֱ�Ӳ���");
			this.fiCapitaaccountService.save(fiCapitaaccountPayment);// ������ˮ��
		}
		
		fiApplyfund.setAuditStatus(2L);//�����
		fiApplyfund.setAuditTime(new Date());
		this.fiApplyfundDao.save(fiApplyfund);
	}
	
	public void fundstransferSit(FiApplyfund fiApplyfund) throws Exception{
		if(fiApplyfund==null) throw new ServiceException("�ʽ�����ʵ�岻���ڣ�");
		if(fiApplyfund.getId()==null) throw new ServiceException("�ʽ����뵥ID�����ڣ�");
		if(fiApplyfund.getStatus()==0L)  throw new ServiceException("�����ϣ���������֧��");
		if(fiApplyfund.getStatus()==1L)  throw new ServiceException("�ѷ������������֧��");
		if(fiApplyfund.getAuditStatus()!=2L)  throw new ServiceException("��δ��ˣ���������֧��");
		if(fiApplyfund.getIsSit()!=1L)  throw new ServiceException("��������֧��");
		
		//�����˺�
		FiCapitaaccountset fiCapitaaccountsetPayment=this.fiCapitaaccountsetService.get(fiApplyfund.getPaymentAccountId());
		//�����˺�
		FiCapitaaccountset fiCapitaaccountsetReceivables=this.fiCapitaaccountsetService.get(fiApplyfund.getAppAccountId());
		//�����˺���֤
		if(fiCapitaaccountsetPayment==null){
			throw new ServiceException("�����˺Ų�����!");
		}
		//�����˺���֤
		if(fiCapitaaccountsetReceivables==null){
			throw new ServiceException("�����˺Ų�����!");
		}
		
		//�����˺�������
		fiCapitaaccountsetPayment.setOpeningBalance(DoubleUtil.sub(fiCapitaaccountsetPayment.getOpeningBalance(),fiApplyfund.getPaymentAmount()));
		if(fiCapitaaccountsetPayment.getOpeningBalance()<0.0){
			throw new ServiceException("�����˺����㣡");
		}
		this.fiCapitaaccountsetService.save(fiCapitaaccountsetPayment);
		//�ʽ���ˮ����
		FiCapitaaccount fiCapitaaccountPayment = new FiCapitaaccount();
		fiCapitaaccountPayment.setBorrow(fiApplyfund.getPaymentAmount());
		fiCapitaaccountPayment.setBalance(fiCapitaaccountsetPayment.getOpeningBalance());// �˺����
		fiCapitaaccountPayment.setFiCapitaaccountsetId(fiCapitaaccountsetPayment.getId());
		fiCapitaaccountPayment.setSourceData("�ʽ����뵥");
		fiCapitaaccountPayment.setSourceNo(fiApplyfund.getId());
		fiCapitaaccountPayment.setRemark("������֧�����Զ����ٸ����˺����");
		this.fiCapitaaccountService.save(fiCapitaaccountPayment);// ������ˮ��
		
		//�����˺��������
		fiCapitaaccountsetReceivables.setOpeningBalance(DoubleUtil.add(fiCapitaaccountsetReceivables.getOpeningBalance(),fiApplyfund.getPaymentAmount()));
		this.fiCapitaaccountsetService.save(fiCapitaaccountsetReceivables);
		//�ʽ���ˮ����
		FiCapitaaccount fiCapitaaccount = new FiCapitaaccount();
		fiCapitaaccount.setLoan(fiApplyfund.getPaymentAmount());
		fiCapitaaccount.setBalance(fiCapitaaccountsetReceivables.getOpeningBalance());// �˺����
		fiCapitaaccount.setFiCapitaaccountsetId(fiCapitaaccountsetReceivables.getId());
		fiCapitaaccount.setSourceData("�ʽ����뵥");
		fiCapitaaccount.setSourceNo(fiApplyfund.getId());
		fiCapitaaccount.setRemark("������֧�����Զ������տ��˺����");
		this.fiCapitaaccountService.save(fiCapitaaccount);// ������ˮ��
		
		fiApplyfund.setReceivablesStatus(2L);
		fiApplyfund.setReceivablesTime(new Date());
		fiApplyfund.setStatus(4L);
		this.fiApplyfundDao.save(fiApplyfund);
		
		/*if(fiApplyfund.getAppAccountId()==fiApplyfund.getPaymentAccountId()) throw new ServiceException("�տ��˺��븶���˺Ų���Ϊͬһ�˺�");
		User user=WebRalasafe.getCurrentUser(Struts2Utils.getRequest());
		
		//�����ʽ𽻽ӵ�
		FiFundstransfer fiFundstransfer=new FiFundstransfer();
		fiFundstransfer.setReceivablesaccountId(fiApplyfund.getAppAccountId());//�տ��˺�
		fiFundstransfer.setReceivablesaccountDeptid(fiApplyfund.getDepartId());
		fiFundstransfer.setReceivablesaccountDept(fiApplyfund.getDepartName());
		fiFundstransfer.setPaymentaccountId(fiApplyfund.getPaymentAccountId());//�����˺�
		fiFundstransfer.setAmount(fiApplyfund.getPaymentAmount());
		fiFundstransfer.setCapitalTypeId(fiApplyfund.getCapitalTypeId());
		fiFundstransfer.setRemark(fiApplyfund.getPaymentRemark());
		fiFundstransfer.setSourceData("�ʽ����뵥");
		fiFundstransfer.setSourceNo(fiApplyfund.getId());
		fiFundstransfer.setStatus(2L);//�Զ�����
		this.fiFundstransferService.save(fiFundstransfer);
		
		//�ʽ𽻽ӵ������˺�������,�Զ�����ȷ��
		this.fiFundstransferService.paymentConfirmation(fiFundstransfer.getId(), user);
		//this.fiFundstransferService.accountHandover(fiFundstransfer);
		
		this.fiFundstransferService.save(fiFundstransfer);*/
	}
	
	public void receivablesConfirmation(Long id,User user) throws Exception{
		Long userId=Long.valueOf(user.get("id")+"");
		if(userId==null||"".equals(userId)){
			throw new ServiceException("��ȡ�û�IDʧ��!");
		}
		FiApplyfund fiApplyfund=this.fiApplyfundDao.get(id);
		if(fiApplyfund==null){
			throw new ServiceException("�ʽ����뵥������!");
		}
		
		//�������տ��˺�
		FiCapitaaccountset fiCapitaaccountsetReceivables=this.fiCapitaaccountsetService.get(fiApplyfund.getAppAccountId());
		if(fiCapitaaccountsetReceivables==null) {
			throw new ServiceException("�ʽ𽻽ӵ��տ��˺Ų�����!");
		}
		if(!userId.equals(fiCapitaaccountsetReceivables.getResponsibleId())){
			throw new ServiceException("ֻ���տ����˺Ÿ����˲����տ�ȷ��");
		}
		if(fiApplyfund.getAuditStatus()==null||fiApplyfund.getAuditStatus()!=2L){
			throw new ServiceException("��ǰ�ʽ����뵥��δ��ˣ�");
		}
		
		if(fiApplyfund.getStatus()==0L||fiApplyfund.getStatus()==1L){
			throw new ServiceException("���ӵ�������!");
		}
		

		
		fiCapitaaccountsetReceivables.setOpeningBalance(DoubleUtil.add(fiCapitaaccountsetReceivables.getOpeningBalance(),fiApplyfund.getPaymentAmount()));
		this.fiCapitaaccountsetService.save(fiCapitaaccountsetReceivables);
		//�ʽ���ˮ����
		FiCapitaaccount fiCapitaaccountReceivables = new FiCapitaaccount();
		fiCapitaaccountReceivables.setLoan(fiApplyfund.getPaymentAmount());
		fiCapitaaccountReceivables.setBalance(fiCapitaaccountsetReceivables.getOpeningBalance());// �˺����
		fiCapitaaccountReceivables.setFiCapitaaccountsetId(fiCapitaaccountsetReceivables.getId());
		fiCapitaaccountReceivables.setSourceData("�ʽ����뵥");
		fiCapitaaccountReceivables.setSourceNo(fiApplyfund.getId());
		fiCapitaaccountReceivables.setRemark("�ʽ����뵥�տ�ȷ��");
		this.fiCapitaaccountService.save(fiCapitaaccountReceivables);// ������ˮ��
		
		//����ȷ��״̬
		fiApplyfund.setReceivablesStatus(2L);
		fiApplyfund.setStatus(3L);
		fiApplyfund.setReceivablesTime(new Date());
		this.fiApplyfundDao.save(fiApplyfund);
	}
}
