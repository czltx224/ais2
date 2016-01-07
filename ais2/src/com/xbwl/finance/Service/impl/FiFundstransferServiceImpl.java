package com.xbwl.finance.Service.impl;

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
import com.xbwl.entity.FiCapitaaccount;
import com.xbwl.entity.FiCapitaaccountset;
import com.xbwl.entity.FiFundstransfer;
import com.xbwl.finance.Service.IFiCapitaaccountService;
import com.xbwl.finance.Service.IFiCapitaaccountsetService;
import com.xbwl.finance.Service.IFiFundstransferService;
import com.xbwl.finance.Service.IFiPaidService;
import com.xbwl.finance.dao.IFiFundstransferDao;

@Service("fiFundstransferServiceImpl")
@Transactional(rollbackFor={Exception.class})
public class FiFundstransferServiceImpl extends BaseServiceImpl<FiFundstransfer,Long> implements
		IFiFundstransferService {

	@Resource(name="fiFundstransferHibernateDaoImpl")
	private IFiFundstransferDao fiFundstransferDao;
	
	//�ʽ��˺�����
	@Resource(name = "fiCapitaaccountsetServiceImpl")
	private IFiCapitaaccountsetService fiCapitaaccountsetService;
	
	//�ʽ��˺���ˮ
	@Resource(name="fiCapitaaccountServiceImpl")
	private IFiCapitaaccountService fiCapitaaccountService;
	
	//ʵ��ʵ��
	@Resource(name = "fiPaidServiceImpl")
	private IFiPaidService fiPaidService;
	
	@Override
	public IBaseDAO getBaseDao() {
		return this.fiFundstransferDao;
	}

	public String getFundstransferNo() throws Exception {
		String carString="KGZJ" ;
		Map  times = (Map)fiFundstransferDao.createSQLQuery("  select SEQ_FI_FUNDSTRANSFER_PRINT_NO.nextval cartimes from  dual  ").list().get(0);
		Long s =Long.valueOf(times.get("CARTIMES")+"");
		return carString+s;
	}

	public void accountHandover(FiFundstransfer fiFundstransfer) throws Exception {
		//�����˺�������
		FiCapitaaccountset fiCapitaaccountsetPayment=this.fiCapitaaccountsetService.get(fiFundstransfer.getPaymentaccountId());//�ʽ𽻽ӵ��˺�
		if(fiCapitaaccountsetPayment==null) throw new ServiceException("�ʽ𽻽ӵ������˺Ų�����!");
		fiCapitaaccountsetPayment.setOpeningBalance(DoubleUtil.sub(fiCapitaaccountsetPayment.getOpeningBalance(),fiFundstransfer.getAmount()));
		if(fiCapitaaccountsetPayment.getOpeningBalance()<0.0){
			throw new ServiceException("�ʽ𽻽ӵ������˺����㣡");
		}
		this.fiCapitaaccountsetService.save(fiCapitaaccountsetPayment);
		//�ʽ���ˮ����
		FiCapitaaccount fiCapitaaccountPayment = new FiCapitaaccount();
		fiCapitaaccountPayment.setLoan(fiFundstransfer.getAmount());
		fiCapitaaccountPayment.setBalance(fiCapitaaccountsetPayment.getOpeningBalance());// �˺����
		fiCapitaaccountPayment.setFiCapitaaccountsetId(fiCapitaaccountsetPayment.getId());
		fiCapitaaccountPayment.setSourceData(fiFundstransfer.getSourceData());
		fiCapitaaccountPayment.setRemark("�ʽ����뵥�˺�����");
		fiCapitaaccountPayment.setSourceNo(fiFundstransfer.getSourceNo());
		this.fiCapitaaccountService.save(fiCapitaaccountPayment);// ������ˮ��
		
		//�����˺��������
		FiCapitaaccountset fiCapitaaccountsetReceivables=this.fiCapitaaccountsetService.get(fiFundstransfer.getReceivablesaccountId());//�ʽ𽻽ӵ��˺�
		if(fiCapitaaccountsetReceivables==null) throw new ServiceException("�ʽ𽻽ӵ��տ��˺Ų�����!");
		fiCapitaaccountsetReceivables.setOpeningBalance(DoubleUtil.add(fiCapitaaccountsetReceivables.getOpeningBalance(),fiFundstransfer.getAmount()));
		this.fiCapitaaccountsetService.save(fiCapitaaccountsetReceivables);
		//�ʽ���ˮ����
		FiCapitaaccount fiCapitaaccountReceivables = new FiCapitaaccount();
		fiCapitaaccountReceivables.setBorrow(fiFundstransfer.getAmount());
		fiCapitaaccountReceivables.setBalance(fiCapitaaccountsetReceivables.getOpeningBalance());// �˺����
		fiCapitaaccountReceivables.setFiCapitaaccountsetId(fiCapitaaccountsetReceivables.getId());
		fiCapitaaccountReceivables.setSourceData(fiFundstransfer.getSourceData());
		fiCapitaaccountReceivables.setRemark("�ʽ����뵥�˺�����");
		fiCapitaaccountReceivables.setSourceNo(fiFundstransfer.getSourceNo());
		this.fiCapitaaccountService.save(fiCapitaaccountReceivables);// ������ˮ��
		
	}
	
	public void paymentConfirmation(Long id,User user) throws Exception{
		Long userId=Long.valueOf(user.get("id")+"");
		if(userId==null||"".equals(userId)){
			throw new ServiceException("��ȡ�û�IDʧ��!");
		}
		FiFundstransfer fiFundstransfer=this.fiFundstransferDao.get(id);
		if(fiFundstransfer==null){
			throw new ServiceException("���ӵ�������!");
		}
		if(fiFundstransfer.getStatus()==0L){
			throw new ServiceException("���ӵ�������!");
		}
		
		//�����˺�
		FiCapitaaccountset fiCapitaaccountsetPayment=this.fiCapitaaccountsetService.get(fiFundstransfer.getPaymentaccountId());
		
		//�տ��˺�
		FiCapitaaccountset fiCapitaaccountsetReceivables=this.fiCapitaaccountsetService.get(fiFundstransfer.getReceivablesaccountId());
		
		if(fiCapitaaccountsetPayment==null){
			throw new ServiceException("�ʽ𽻽ӵ������˺Ų�����!");
		}
		
		if(fiCapitaaccountsetReceivables==null){
			throw new ServiceException("�ʽ𽻽ӵ��տ��˺Ų�����!");
		}
		
		if(!userId.equals(fiCapitaaccountsetPayment.getResponsibleId())){
			throw new ServiceException("����ȷ��ʧ�ܣ�ֻ�и������˺Ÿ����˲��ܸ���ȷ��!");
		}
		
		if(fiCapitaaccountsetPayment.getResponsibleId().equals(fiCapitaaccountsetReceivables.getResponsibleId())){
			throw new ServiceException("����ȷ��ʧ�ܣ������˺Ÿ��������տ��˺Ÿ����˲���Ϊͬһ�ˡ�");
		}
		
		if(fiFundstransfer.getPaymentStatus()==1L){
			throw new ServiceException("����ȷ��ʧ�ܣ���ǰ����״̬�Ѹ���");
		}
		
		fiCapitaaccountsetPayment.setOpeningBalance(DoubleUtil.sub(fiCapitaaccountsetPayment.getOpeningBalance(),fiFundstransfer.getAmount()));
		if(fiCapitaaccountsetPayment.getOpeningBalance()<0.0){
			throw new ServiceException("�ʽ𽻽ӵ������˺����㣡");
		}
		this.fiCapitaaccountsetService.save(fiCapitaaccountsetPayment);
		//�ʽ���ˮ����
		FiCapitaaccount fiCapitaaccountPayment = new FiCapitaaccount();
		fiCapitaaccountPayment.setBorrow(fiFundstransfer.getAmount());
		fiCapitaaccountPayment.setBalance(fiCapitaaccountsetPayment.getOpeningBalance());// �˺����
		fiCapitaaccountPayment.setFiCapitaaccountsetId(fiCapitaaccountsetPayment.getId());
		fiCapitaaccountPayment.setSourceData("�ʽ𽻽ӵ�");
		fiCapitaaccountPayment.setSourceNo(fiFundstransfer.getId());
		fiCapitaaccountPayment.setRemark("�ʽ𽻽ӵ�����ȷ��");
		this.fiCapitaaccountService.save(fiCapitaaccountPayment);// ������ˮ��
		
		//���潻������״̬
		fiFundstransfer.setPaymentStatus(1L);
		fiFundstransfer.setPaymentTime(new Date());
		this.fiFundstransferDao.save(fiFundstransfer);
	}
	
	public void paymentRevoke(Long id,User user) throws Exception{
		Long userId=Long.valueOf(user.get("id")+"");
		if(userId==null||"".equals(userId)){
			throw new ServiceException("��ȡ�û�IDʧ��!");
		}
		FiFundstransfer fiFundstransfer=this.fiFundstransferDao.get(id);
		if(fiFundstransfer==null){
			throw new ServiceException("���ӵ�������!");
		}
		if(fiFundstransfer.getStatus()==0L){
			throw new ServiceException("���ӵ�������!");
		}
		
		if(fiFundstransfer.getPaymentStatus()==0L){
			throw new ServiceException("�����տ�ȷ��ʧ�ܣ���ǰ���ӵ���δ����ȷ��");
		}
		
		if(fiFundstransfer.getReceivablesStatus()==1L){
			throw new ServiceException("�����տ�ȷ��ʧ�ܣ���ǰ�տ�״̬��ȷ��");
		}
		
		//�����˺�
		FiCapitaaccountset fiCapitaaccountsetPayment=this.fiCapitaaccountsetService.get(fiFundstransfer.getPaymentaccountId());
		if(fiCapitaaccountsetPayment==null){
			throw new ServiceException("�ʽ𽻽ӵ������˺Ų�����!");
		}
		fiCapitaaccountsetPayment.setOpeningBalance(DoubleUtil.add(fiCapitaaccountsetPayment.getOpeningBalance(),fiFundstransfer.getAmount()));
		if(fiCapitaaccountsetPayment.getOpeningBalance()<0.0){
			throw new ServiceException("�ʽ𽻽ӵ������˺����㣡");
		}
		this.fiCapitaaccountsetService.save(fiCapitaaccountsetPayment);
		
		//�ʽ���ˮ����
		FiCapitaaccount fiCapitaaccountPayment = new FiCapitaaccount();
		fiCapitaaccountPayment.setLoan(fiFundstransfer.getAmount());
		fiCapitaaccountPayment.setBalance(fiCapitaaccountsetPayment.getOpeningBalance());// �˺����
		fiCapitaaccountPayment.setFiCapitaaccountsetId(fiCapitaaccountsetPayment.getId());
		fiCapitaaccountPayment.setSourceData("�ʽ𽻽ӵ�");
		fiCapitaaccountPayment.setSourceNo(fiFundstransfer.getId());
		fiCapitaaccountPayment.setRemark("�ʽ𽻽ӵ���������ȷ��");
		this.fiCapitaaccountService.save(fiCapitaaccountPayment);// ������ˮ��
		
		//���潻������״̬
		fiFundstransfer.setPaymentStatus(0L);
		fiFundstransfer.setPaymentTime(new Date());
		this.fiFundstransferDao.save(fiFundstransfer);
		
	}
	
	
	public void receivablesConfirmation(Long id,User user) throws Exception{
		Long userId=Long.valueOf(user.get("id")+"");
		if(userId==null||"".equals(userId)){
			throw new ServiceException("��ȡ�û�IDʧ��!");
		}
		FiFundstransfer fiFundstransfer=this.fiFundstransferDao.get(id);
		if(fiFundstransfer==null){
			throw new ServiceException("���ӵ�������!");
		}
		
		//�տ��˺�
		FiCapitaaccountset fiCapitaaccountsetReceivables=this.fiCapitaaccountsetService.get(fiFundstransfer.getReceivablesaccountId());//�ʽ𽻽ӵ��˺�
		if(fiCapitaaccountsetReceivables==null) {
			throw new ServiceException("�ʽ𽻽ӵ��տ��˺Ų�����!");
		}
		if(!userId.equals(fiCapitaaccountsetReceivables.getResponsibleId())){
			throw new ServiceException("�տ�ȷ��ʧ�ܣ�ֻ���տ����˺Ÿ����˲����տ�ȷ��");
		}
		if(fiFundstransfer.getPaymentStatus()!=1L){
			throw new ServiceException("�տ�ȷ��ʧ�ܣ���ǰ�տ��δ����ȷ�ϣ�");
		}
		
		if(fiFundstransfer.getReceivablesStatus()==1L){
			throw new ServiceException("�տ�ȷ��ʧ�ܣ���ǰ�տ�״̬��ȷ��");
		}
		
		if(fiFundstransfer.getStatus()==0L){
			throw new ServiceException("���ӵ�������!");
		}
		

		
		fiCapitaaccountsetReceivables.setOpeningBalance(DoubleUtil.add(fiCapitaaccountsetReceivables.getOpeningBalance(),fiFundstransfer.getAmount()));
		this.fiCapitaaccountsetService.save(fiCapitaaccountsetReceivables);
		//�ʽ���ˮ����
		FiCapitaaccount fiCapitaaccountReceivables = new FiCapitaaccount();
		fiCapitaaccountReceivables.setLoan(fiFundstransfer.getAmount());
		fiCapitaaccountReceivables.setBalance(fiCapitaaccountsetReceivables.getOpeningBalance());// �˺����
		fiCapitaaccountReceivables.setFiCapitaaccountsetId(fiCapitaaccountsetReceivables.getId());
		fiCapitaaccountReceivables.setSourceData(fiFundstransfer.getSourceData());
		fiCapitaaccountReceivables.setSourceData("�ʽ𽻽ӵ�");
		fiCapitaaccountReceivables.setSourceNo(fiFundstransfer.getId());
		fiCapitaaccountReceivables.setRemark("�ʽ𽻽ӵ��տ�ȷ��");
		this.fiCapitaaccountService.save(fiCapitaaccountReceivables);// ������ˮ��
		
		//���潻���տ�״̬
		fiFundstransfer.setReceivablesStatus(1L);//���տ�
		fiFundstransfer.setReceivablesTime(new Date());
		fiFundstransfer.setStatus(2L);//�Ѻ���
		this.fiFundstransferDao.save(fiFundstransfer);
		
		//��������
		this.verificationSourceData(fiFundstransfer);
	}
	
	/**
	 * ���ӵ��տ�ȷ�Ϻ�������ģ������
	 * @param fiFundstransfer
	 */
	private void verificationSourceData(FiFundstransfer fiFundstransfer){
		if("ʵ��ʵ����".equals(fiFundstransfer.getSourceData())){
			User user=WebRalasafe.getCurrentUser(Struts2Utils.getRequest());
			this.fiPaidService.batchExecute("update FiPaid f set f.verificationAmount=f.settlementAmount,f.verificationStatus=1,f.verificationUser=?,f.verificationTime=? where f.fiFundstransferId=?", user.get("name"),new Date(),fiFundstransfer.getId());
		}
	}
	
	public void revocation(Long id) throws Exception{
		if("".equals(id)||id==null){
			throw new ServiceException("���ӵ�ID�����ڣ�");
		}
		FiFundstransfer fundstransfer=this.fiFundstransferDao.get(id);
		if(fundstransfer==null){
			throw new ServiceException("���ӵ������ڣ�");
		}
		if(fundstransfer.getPaymentStatus()==1L){
			throw new ServiceException("���ӵ����Ѹ���ȷ�ϣ���������");
		}
		if(fundstransfer.getReceivablesStatus()==1L){
			throw new ServiceException("���ӵ����Ѹ���ȷ�ϣ���������");
		}
		
		fundstransfer.setStatus(0L);
		this.fiFundstransferDao.save(fundstransfer);
		
		//��������ģ������
		this.revocationSourceData(fundstransfer);
	}
	
	/**
	 * ���ӵ��տ�ȷ�Ϻ�������ģ������
	 * @param fiFundstransfer
	 */
	private void revocationSourceData(FiFundstransfer fiFundstransfer){
		if("ʵ��ʵ����".equals(fiFundstransfer.getSourceData())){
			User user=WebRalasafe.getCurrentUser(Struts2Utils.getRequest());
			this.fiPaidService.batchExecute("update FiPaid f set f.fiFundstransferId='',f.fiFundstransferStatus=0L where f.fiFundstransferId=?", fiFundstransfer.getId());
		}
	}
}
