package com.xbwl.finance.Service.impl;

import java.util.ArrayList;
import java.util.Date;
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
import com.xbwl.common.utils.DoubleUtil;
import com.xbwl.common.utils.XbwlInt;
import com.xbwl.entity.FiPayment;
import com.xbwl.entity.FiPaymentabnormal;
import com.xbwl.finance.Service.IFiPaymentabnormalService;
import com.xbwl.finance.dao.IFiPaymentDao;
import com.xbwl.finance.dao.IFiPaymentabnormalDao;
import com.xbwl.finance.dto.IFiInterface;
import com.xbwl.finance.dto.impl.FiInterfaceProDto;
import com.xbwl.oper.stock.service.IOprHistoryService;

@Service("fiPaymentabnormalServiceImpl")
@Transactional(rollbackFor={Exception.class})
public class FiPaymentabnormalServiceImpl extends
		BaseServiceImpl<FiPaymentabnormal, Long> implements
		IFiPaymentabnormalService {

	@Resource(name = "fiPaymentabnormalHibernateDaoImpl")
	private IFiPaymentabnormalDao fiPaymentabnormalDao;

	@Resource(name = "fiPaymentHibernateDaoImpl")
	private IFiPaymentDao fiPaymentDao;
	
	//��־
	@Resource(name = "oprHistoryServiceImpl")
	private IOprHistoryService oprHistoryService;

	@Resource(name="fiInterfaceImpl")
	private IFiInterface fiInterface;
	
	@Override
	public IBaseDAO getBaseDao() {
		return this.fiPaymentabnormalDao;
	}

	public String saveException(FiPaymentabnormal fiPaymentabnormal) throws Exception{
		FiPayment fp = this.fiPaymentDao.get(fiPaymentabnormal.getFiPaymentId());
		//fp.setPaymentStatus(8L);
		//this.fiPaymentDao.save(fp);
		this.fiPaymentabnormalDao.save(fiPaymentabnormal);
		
		//��д�տ�쳣�������ֶ�
		fp.setAbnormalAmount(fiPaymentabnormal.getAmount());
		this.fiPaymentDao.save(fp);
		
		//������־
		oprHistoryService.saveFiLog(fp.getDocumentsNo(), "�쳣������ID��"+fiPaymentabnormal.getId()+",�Ǽǽ��:"+fiPaymentabnormal.getAmount()+",�쳣��ע��"+fiPaymentabnormal.getRemark() ,59L);
		return "true";
	}

	public void revocationException(FiPaymentabnormal fiPaymentabnormal) throws Exception{
		if(fiPaymentabnormal.getStatus()!=1L){
			if(fiPaymentabnormal.getStatus()==0L){
				throw new ServiceException("����ʧ�ܣ���ǰ�쳣�����������ϣ�");
			}else if(fiPaymentabnormal.getStatus()==2L){
				throw new ServiceException("����ʧ�ܣ���ǰ�쳣����������ˣ�");
			}else{
				throw new ServiceException("�쳣������״̬�����ڣ�����ϵϵͳ����Ա��");
			}
		}

		fiPaymentabnormal.setStatus(Long.valueOf(0));
		this.fiPaymentabnormalDao.save(fiPaymentabnormal);
		
		//����Ӧ�տ�쳣��������
		FiPayment fp = this.fiPaymentDao.get(fiPaymentabnormal.getFiPaymentId());
		fp.setAbnormalAmount(0.0);
		this.fiPaymentDao.save(fp);
		
		//������־
		oprHistoryService.saveFiLog(fp.getDocumentsNo(), "�쳣������ID:"+ fiPaymentabnormal.getId()+",���̺�:"+fiPaymentabnormal.getWorkflowNo(),60L);
	}
	
	public void verificationException(FiPaymentabnormal fiPaymentabnormal) throws Exception{
		if(fiPaymentabnormal.getStatus()==0L){
			throw new ServiceException("����ʧ�ܣ���ǰ���������ϣ�");
		}else if(fiPaymentabnormal.getStatus()==2L){
			throw new ServiceException("����ʧ�ܣ���ǰ��������ˣ�");
		}else if(fiPaymentabnormal.getStatus()==1L){
			User user= WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
			FiPayment fp = this.fiPaymentDao.get(fiPaymentabnormal.getFiPaymentId());
			if(fp==null) throw new ServiceException("�쳣�������Ӧ��Ӧ�յ��Ų����ڣ����������!");
			double eliminationAmount=fiPaymentabnormal.getAmount();
			double settlementAmount=DoubleUtil.add(fp.getSettlementAmount(),eliminationAmount);
			
			//�����˿�״̬
			fiPaymentabnormal.setStatus(2L);
			
			//������д��Ӧ��Ӧ����
			FiInterfaceProDto fpd=new FiInterfaceProDto();
			List<FiInterfaceProDto> listfiInterfaceDto=new ArrayList<FiInterfaceProDto>();
			fpd.setAmount(fiPaymentabnormal.getAmount());
			fpd.setDocumentsType("�쳣������");
			fpd.setDocumentsSmalltype("���͵�");
			fpd.setDocumentsNo(fp.getDocumentsNo());
			fpd.setCustomerId(fp.getCustomerId());
			fpd.setCustomerName(fp.getCustomerName());
			fpd.setCostType("�쳣������");
			fpd.setSourceData("�쳣������");
			fpd.setSourceNo(fiPaymentabnormal.getId());
			fpd.setSettlementType(2L);//���
			fpd.setContacts(fp.getContacts());
			fpd.setDepartId(Long.valueOf(user.get("bussDepart")+""));
			fpd.setDepartName(user.get("rightDepart")+"");
			listfiInterfaceDto.add(fpd);
			
			//�������
			fp.setWorkflowNo(fiPaymentabnormal.getWorkflowNo());
			/*if (fp.getAmount()<=settlementAmount) {
				fp.setPaymentStatus(2L);// ���տ�
			}else if(settlementAmount==0.0) {
				fp.setPaymentStatus(1L);// δ�տ�
			}else {
				fp.setPaymentStatus(3L);// �����տ�
			}
			this.fiPaymentDao.save(fp);*/
			
			this.fiPaymentabnormalDao.save(fiPaymentabnormal);//�쳣�������
			this.fiInterface.currentToFiPayment(listfiInterfaceDto);//Ӧ�����
			
			//������־
			oprHistoryService.saveFiLog(fp.getDocumentsNo(), "�����˿�ID��"+fiPaymentabnormal.getId()+",��˽��:"+fiPaymentabnormal.getAmount(),61L);
			
		}else{
			throw new ServiceException("����ʧ�ܣ���ǰ����״̬�����ڣ�");
		}
	}
	
	public void verificationRegister(FiPaymentabnormal fiPaymentabnormal) throws Exception{
		if(fiPaymentabnormal.getVerificationStatus()==1L){
			fiPaymentabnormal.setStatus(1L);
			this.fiPaymentabnormalDao.save(fiPaymentabnormal);//�쳣�������
			
			FiInterfaceProDto fpd=new FiInterfaceProDto();
			fpd.setSourceData("�쳣������");
			fpd.setSourceNo(fiPaymentabnormal.getId());
			this.fiInterface.invalidToFiPayment(fpd);
			
			FiPayment fp = this.fiPaymentDao.get(fiPaymentabnormal.getFiPaymentId());
			//double eliminationAmount=DoubleUtil.sub(fp.getEliminationAmount(), fiPaymentabnormal.getAmount());
			//fp.setEliminationAmount(eliminationAmount);
			
			if(fp.getPaymentStatus()==2L){
				throw new ServiceException("�տ���������������ٳ������");
			}
			/*double settlementAmount=DoubleUtil.add(fp.getSettlementAmount(),eliminationAmount);
			if (fp.getAmount()<=settlementAmount) {
				fp.setPaymentStatus(2L);// ���տ�
			}else if(settlementAmount==0.0) {
				fp.setPaymentStatus(1L);// δ�տ�
			}else {
				fp.setPaymentStatus(3L);// �����տ�
			}*/
			
			//������־
			oprHistoryService.saveFiLog(fp.getDocumentsNo(), "�����˿�ID��"+fiPaymentabnormal.getId(),62L);
			
		}else if(fiPaymentabnormal.getVerificationStatus()==2L){
			throw new ServiceException("����ʧ�ܣ���ǰ�����Ѻ���,�������ٳ�����ˣ�");
		}else{
			throw new ServiceException("����ʧ�ܣ���ǰ���ݺ���״̬������,����ϵϵͳ����Ա��");
		}
	}

	@XbwlInt(isCheck=false)
	public void verfiPaymentabnormal(FiPayment fiPayment) throws Exception{
		FiPaymentabnormal fiPaymentabnormal=this.fiPaymentabnormalDao.get(fiPayment.getSourceNo());
		if(fiPaymentabnormal==null) throw new ServiceException("�쳣������δ�ҵ����޷�����");
		fiPaymentabnormal.setVerificationTime(new Date());
		fiPaymentabnormal.setVerificationAmount(DoubleUtil.add(fiPaymentabnormal.getVerificationAmount(),fiPayment.getAmount()));
		if(fiPaymentabnormal.getAmount().equals(fiPaymentabnormal.getVerificationAmount())){
			fiPaymentabnormal.setVerificationStatus(2L);
		}
		this.fiPaymentabnormalDao.save(fiPaymentabnormal);
		
	}
	
	public void verfiPaymentabnormalRegister(Double amount,Long fiPaymentabnormalId) throws Exception{
	FiPaymentabnormal fiPaymentabnormal=this.fiPaymentabnormalDao.get(fiPaymentabnormalId);
	if(fiPaymentabnormal==null) throw new ServiceException("�쳣������δ�ҵ����޷�����");
	fiPaymentabnormal.setVerificationTime(null);
	fiPaymentabnormal.setVerificationAmount(DoubleUtil.sub(fiPaymentabnormal.getVerificationAmount(), amount));
	fiPaymentabnormal.setVerificationStatus(1L);
	this.fiPaymentabnormalDao.save(fiPaymentabnormal);
	
}

}
