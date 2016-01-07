package com.xbwl.finance.Service.impl;

import java.util.ArrayList;
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

import com.xbwl.common.bean.ValidateInfo;
import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.log.anno.ModuleName;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.orm.Page;
import com.xbwl.common.orm.PropertyFilter;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.common.utils.DoubleUtil;
import com.xbwl.common.utils.LogType;
import com.xbwl.entity.FiProblemreceivable;
import com.xbwl.entity.FiReceivabledetail;
import com.xbwl.entity.FiReceivablestatement;
import com.xbwl.finance.Service.IFiProblemreceivableService;
import com.xbwl.finance.Service.IFiReceivablestatementService;
import com.xbwl.finance.dao.IFiProblemreceivableDao;
import com.xbwl.finance.dao.IFiReceivabledetailDao;
import com.xbwl.finance.dao.IFiReceivablestatementDao;
import com.xbwl.finance.dto.IFiInterface;
import com.xbwl.finance.dto.impl.FiInterfaceProDto;
import com.xbwl.oper.stock.service.IOprHistoryService;

@Service("fiProblemreceivableServiceImpl")
@Transactional(rollbackFor={Exception.class})
public class FiProblemreceivableServiceImpl extends
		BaseServiceImpl<FiProblemreceivable, Long> implements
		IFiProblemreceivableService {
	
	@Resource(name="fiProblemreceivableHibernateDaoImpl")
	private IFiProblemreceivableDao fiProblemreceivableDao;
	
	@Resource(name = "fiReceivabledetailHibernateDaoImpl")
	private IFiReceivabledetailDao fiReceivabledetailDao;
	
	//���˵�
	@Resource(name = "fiReceivablestatementServiceImpl")
	private IFiReceivablestatementService fiReceivablestatementService;
	
	//��־
	@Resource(name = "oprHistoryServiceImpl")
	private IOprHistoryService oprHistoryService;
	
	@Resource(name="fiInterfaceImpl")
	private IFiInterface fiInterface;
	
	@Override
	public IBaseDAO getBaseDao() {
		return this.fiProblemreceivableDao;
	}

	public void revocationRegister(FiProblemreceivable fiProblemreceivable) throws Exception{
		
		if(fiProblemreceivable.getStatus()!=1L){
			if(fiProblemreceivable.getStatus()==0L){
				throw new ServiceException("����ʧ�ܣ���ǰ�����˿������ϣ�");
			}else if(fiProblemreceivable.getStatus()==2L){
				throw new ServiceException("����ʧ�ܣ���ǰ�����˿�����ˣ�");
			}else{
				throw new ServiceException("�����˿�״̬�����ڣ�����ϵϵͳ����Ա��");
			}
		}

		//�����˿��¼��״̬����Ϊ��ȡ�� 
		fiProblemreceivable.setStatus(Long.valueOf(0));
		this.fiProblemreceivableDao.save(fiProblemreceivable);
		
		//���¹�Ӧ��������ϸ�����˿������Ϣ
		FiReceivabledetail fiReceivabledetail=this.fiReceivabledetailDao.get(fiProblemreceivable.getSourceNo());
		fiReceivabledetail.setProblemStatus(Long.valueOf(0));
		this.fiReceivabledetailDao.save(fiReceivabledetail);
		
		//���¶��˵��������˿�����Ӧ�ս��(��Ʊ��ԭӦ�ս���Բ������)
		FiReceivablestatement frt=this.fiReceivablestatementService.get(fiReceivabledetail.getReconciliationNo());
		//frt.setAccountsBalance(frt.getAccountsBalance()+fiProblemreceivable.getProblemAmount());// Ӧ�����
		frt.setProblemAmount(fiReceivabledetail.getProblemAmount()-fiProblemreceivable.getProblemAmount());  // �����˿���
		this.fiReceivablestatementService.save(frt);
		
		//������־
		oprHistoryService.saveFiLog(fiReceivabledetail.getDno(), "���˵��ţ�"+frt.getId()+",����������ϸ���ţ�"+fiReceivabledetail.getId()+",���������˿��"+fiProblemreceivable.getProblemAmount() ,54L);
	}
	
	@ModuleName(value="�����˿����",logType=LogType.fi)
	public void audit(FiProblemreceivable fiProblemreceivable) throws Exception{
		if(fiProblemreceivable.getStatus()==0L){
			throw new ServiceException("����ʧ�ܣ���ǰ���������ϣ�");
		}else if(fiProblemreceivable.getStatus()==2L){
			throw new ServiceException("����ʧ�ܣ���ǰ��������ˣ�");
		}else if(fiProblemreceivable.getStatus()==1L){
			User user= WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
			//�����˿�״̬
			fiProblemreceivable.setStatus(2L);
			fiProblemreceivable.setVerRemark(fiProblemreceivable.getVerRemark());
			
			//���¹�Ӧ��������ϸ�����˿������Ϣ
			FiReceivabledetail fiReceivabledetail=this.fiReceivabledetailDao.get(fiProblemreceivable.getSourceNo());
			if(fiReceivabledetail==null)  throw new ServiceException("����ʧ�ܣ������˿��Ӧ�Ŀ���������ϸ��¼�����ڣ�");
			fiReceivabledetail.setProblemStatus(Long.valueOf(0));
			fiReceivabledetail.setProblemAmount(DoubleUtil.add(fiReceivabledetail.getProblemAmount(),fiProblemreceivable.getProblemAmount()));
			this.fiReceivabledetailDao.save(fiReceivabledetail);
			
			//���¶��˵��������˿�����Ӧ�ս��(��Ʊ��ԭӦ�ս���Բ������)
			FiReceivablestatement frt=this.fiReceivablestatementService.get(fiReceivabledetail.getReconciliationNo());
			if(frt==null)  throw new ServiceException("����ʧ�ܣ������˿��Ӧ�Ķ��˵���¼�����ڣ�");
			frt.setProblemAmount(fiProblemreceivable.getProblemAmount());  // �����˿���
			this.fiReceivablestatementService.save(frt);
			
			
			//������д��Ӧ��Ӧ����
			FiInterfaceProDto fpd=new FiInterfaceProDto();
			List<FiInterfaceProDto> listfiInterfaceDto=new ArrayList<FiInterfaceProDto>();
			fpd.setAmount(fiProblemreceivable.getProblemAmount());
			fpd.setDocumentsType("�����˿�");
			fpd.setDocumentsSmalltype("���͵�");
			fpd.setDocumentsNo(fiProblemreceivable.getDno());
			fpd.setCustomerId(fiProblemreceivable.getCustomerId());
			fpd.setCustomerName(fiProblemreceivable.getCustomerName());
			fpd.setCostType("�����˿�");
			fpd.setSourceData("�����˿�");
			fpd.setSourceNo(fiProblemreceivable.getId());
			fpd.setDno(fiProblemreceivable.getDno());
			fpd.setSettlementType(2L);//���
			fpd.setDepartId(Long.valueOf(user.get("bussDepart")+""));
			fpd.setDepartName(user.get("rightDepart")+"");
			listfiInterfaceDto.add(fpd);
			
			this.fiProblemreceivableDao.save(fiProblemreceivable);//�����˿��
			this.fiInterface.currentToFiPayment(listfiInterfaceDto);//Ӧ�����
			
			//������־
			oprHistoryService.saveFiLog(fiReceivabledetail.getDno(), "�����˿�ID��"+fiProblemreceivable.getId()+",��������˿��"+fiProblemreceivable.getProblemAmount() ,55L);
			
		}else{
			throw new ServiceException("����ʧ�ܣ���ǰ����״̬�����ڣ�");
		}
	}
	
	public void problemreceivableRegister(FiProblemreceivable fiProblemreceivable) throws Exception{
		if(fiProblemreceivable.getVerificationStatus()==1L){
			//�����˿�״̬
			fiProblemreceivable.setStatus(1L);
			this.fiProblemreceivableDao.save(fiProblemreceivable);//�����˿��
			
			FiInterfaceProDto fpd=new FiInterfaceProDto();
			fpd.setSourceData("�����˿�");
			fpd.setSourceNo(fiProblemreceivable.getId());
			this.fiInterface.invalidToFiPayment(fpd);
			
			//������־
			oprHistoryService.saveFiLog(fiProblemreceivable.getDno(), "�����˿�ID��"+fiProblemreceivable.getId()+",������"+fiProblemreceivable.getProblemAmount() ,55L);
		}else if(fiProblemreceivable.getVerificationStatus()==2L||fiProblemreceivable.getVerificationStatus()==3L){
			throw new ServiceException("����ʧ�ܣ���ǰ�����Ѻ���,�������ٳ�����ˣ�");
		}else{
			throw new ServiceException("����ʧ�ܣ���ǰ���ݺ���״̬������,����ϵϵͳ����Ա��");
		}
	}
	
	@ModuleName(value="�����˿����",logType=LogType.fi)
	public void verfiProblemreceivable(Double amount,Long fiProblemreceivableId) throws Exception{
		Double verificationAmountNo=0.0;//δ�������
		Double problemAmount=0.0; // �����˿���
		Double verificationAmount=0.0; //�������
		
		FiProblemreceivable fiProblemreceivable=this.fiProblemreceivableDao.get(fiProblemreceivableId);
		if(fiProblemreceivable==null) throw new ServiceException("�����˿��¼������!");
		problemAmount=fiProblemreceivable.getProblemAmount();
		verificationAmount=fiProblemreceivable.getVerificationAmount();
		verificationAmountNo=DoubleUtil.sub(problemAmount,verificationAmount);
		if(amount>=verificationAmountNo){
			fiProblemreceivable.setVerificationStatus(3L);
		}else{
			fiProblemreceivable.setVerificationStatus(2L);
		}
		fiProblemreceivable.setVerificationAmount(amount);
		fiProblemreceivable.setVerificationTime(new Date());
		this.fiProblemreceivableDao.save(fiProblemreceivable);
		
		//���¹�Ӧ��������ϸ�����˿������Ϣ
		FiReceivabledetail fiReceivabledetail=this.fiReceivabledetailDao.get(fiProblemreceivable.getSourceNo());
		if(fiReceivabledetail==null)  throw new ServiceException("����ʧ�ܣ������˿��Ӧ�Ŀ���������ϸ��¼�����ڣ�");
		fiReceivabledetail.setEliminationAmount(fiProblemreceivable.getVerificationAmount());
		//�Ѻ������+�ѳ������>=Ӧ�ս��
		if(DoubleUtil.add(fiReceivabledetail.getVerificationAmount(),fiReceivabledetail.getEliminationAmount())>=fiReceivabledetail.getAmount()){
			fiReceivabledetail.setVerificationStatus(3L);//�Ѻ���
		}else{
			fiReceivabledetail.setVerificationStatus(1L);//δ����
		}
		this.fiReceivabledetailDao.save(fiReceivabledetail);
		
		//���¶��˵��������˿�����Ӧ�ս��(��Ʊ��ԭӦ�ս���Բ������)
		FiReceivablestatement frt=this.fiReceivablestatementService.get(fiReceivabledetail.getReconciliationNo());
		if(frt==null)  throw new ServiceException("����ʧ�ܣ������˿��Ӧ�Ķ��˵���¼�����ڣ�");
		frt.setEliminationAmount(fiProblemreceivable.getVerificationAmount());  // �����˿���
		this.fiReceivablestatementService.save(frt);
		
	}
	
	@ModuleName(value="�����˿������",logType=LogType.fi)
	public void verfiProblemreceivableRegister(Double amount,Long fiProblemreceivableId) throws Exception{
		Double verificationAmountNo=0.0;//δ�������
		Double verificationAmount=0.0; //�������
		
		FiProblemreceivable fiProblemreceivable=this.fiProblemreceivableDao.get(fiProblemreceivableId);
		if(fiProblemreceivable==null) throw new ServiceException("�����˿��¼������!");
		verificationAmount=fiProblemreceivable.getVerificationAmount();
		verificationAmountNo=DoubleUtil.sub(verificationAmount,amount);
		fiProblemreceivable.setVerificationAmount(verificationAmountNo);
		fiProblemreceivable.setVerificationTime(null);
		this.fiProblemreceivableDao.save(fiProblemreceivable);
		
		//���¹�Ӧ��������ϸ�����˿������Ϣ
		FiReceivabledetail fiReceivabledetail=this.fiReceivabledetailDao.get(fiProblemreceivable.getSourceNo());
		if(fiReceivabledetail==null)  throw new ServiceException("����ʧ�ܣ������˿��Ӧ�Ŀ���������ϸ��¼�����ڣ�");
		fiReceivabledetail.setEliminationAmount(DoubleUtil.sub(fiReceivabledetail.getEliminationAmount(),amount));
		//�Ѻ������+�ѳ������>=Ӧ�ս��
		if(DoubleUtil.add(fiReceivabledetail.getVerificationAmount(),fiReceivabledetail.getEliminationAmount())>=fiReceivabledetail.getAmount()){
			fiReceivabledetail.setVerificationStatus(3L);//�Ѻ���
		}else{
			fiReceivabledetail.setVerificationStatus(1L);//δ����
		}
		this.fiReceivabledetailDao.save(fiReceivabledetail);
		
		//���¶��˵��������˿�����Ӧ�ս��(��Ʊ��ԭӦ�ս���Բ������)
		FiReceivablestatement frt=this.fiReceivablestatementService.get(fiReceivabledetail.getReconciliationNo());
		if(frt==null)  throw new ServiceException("����ʧ�ܣ������˿��Ӧ�Ķ��˵���¼�����ڣ�");
		frt.setEliminationAmount(DoubleUtil.sub(frt.getEliminationAmount(), amount));  // �����˿���
		this.fiReceivablestatementService.save(frt);
		
		//������־
		oprHistoryService.saveFiLog(fiProblemreceivable.getDno(), "�����˿�ID��"+fiProblemreceivable.getId()+",������"+amount ,57L);
	}

}
