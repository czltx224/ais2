package com.xbwl.finance.Service.impl;


import java.rmi.server.ServerCloneException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.log.anno.ModuleName;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.orm.Page;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.common.utils.DoubleUtil;
import com.xbwl.common.utils.LogType;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.FiAdvance;
import com.xbwl.entity.FiAdvanceset;
import com.xbwl.entity.FiCapitaaccount;
import com.xbwl.entity.FiCapitaaccountset;
import com.xbwl.entity.FiCashiercollection;
import com.xbwl.entity.FiCheck;
import com.xbwl.entity.FiFundstransfer;
import com.xbwl.entity.FiIncomeAccount;
import com.xbwl.entity.FiPaid;
import com.xbwl.entity.FiPayment;
import com.xbwl.finance.Service.IFiAdvancebpService;
import com.xbwl.finance.Service.IFiDeliverycostService;
import com.xbwl.finance.Service.IFiFundstransferService;
import com.xbwl.finance.Service.IFiIncomeAccountService;
import com.xbwl.finance.Service.IFiOutCostService;
import com.xbwl.finance.Service.IFiPaidService;
import com.xbwl.finance.Service.IFiPaymentabnormalService;
import com.xbwl.finance.Service.IFiProblemreceivableService;
import com.xbwl.finance.Service.IFiReceivabledetailService;
import com.xbwl.finance.Service.IFiReceivablestatementService;
import com.xbwl.finance.Service.IFiTransitcostService;
import com.xbwl.finance.dao.IFiAdvanceDao;
import com.xbwl.finance.dao.IFiAdvancesetDao;
import com.xbwl.finance.dao.IFiCapitaaccountDao;
import com.xbwl.finance.dao.IFiCapitaaccountsetDao;
import com.xbwl.finance.dao.IFiCashiercollectionDao;
import com.xbwl.finance.dao.IFiCheckDao;
import com.xbwl.finance.dao.IFiPaidDao;
import com.xbwl.finance.dao.IFiPaymentDao;
import com.xbwl.finance.dao.IFiReceivabledetailDao;
import com.xbwl.finance.dao.IFiReceivablestatementDao;
import com.xbwl.oper.stock.service.IOprHistoryService;
import com.xbwl.oper.stock.service.IOprSignRouteService;
import com.xbwl.oper.stock.service.IOprStatusService;

@Service("fiPaidServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class FiPaidServiceImpl extends BaseServiceImpl<FiPaid, Long> implements
		IFiPaidService {

	@Override
	public IBaseDAO getBaseDao() {
		return this.fiPaidDao;
	}
	
	@Resource(name = "fiPaidHibernateDaoImpl")
	private IFiPaidDao fiPaidDao;

	// Ӧ��Ӧ��Dao
	@Resource(name = "fiPaymentHibernateDaoImpl")
	private IFiPaymentDao fiPaymentDao;

	// �ʽ��˺�Dao
	@Resource(name = "fiCapitaaccountsetHibernateDaoImpl")
	private IFiCapitaaccountsetDao fiCapitaaccountsetDao;

	// �ʽ��˺���ˮDao
	@Resource(name = "fiCapitaaccountHibernateDaoImpl")
	private IFiCapitaaccountDao fiCapitaaccountDao;
	
	//�����տDao
	@Resource(name="fiCashiercollectionHibernateDaoImpl")
	private IFiCashiercollectionDao fiCashiercollectionDao;
	
	// ֧ƱDao
	@Resource(name = "fiCheckHibernateImpl")
	private IFiCheckDao fiCheckDao;
	
	//Ԥ�������Dao
	@Resource(name="fiAdvanceHibernateDaoImpl")
	private IFiAdvanceDao fiAdvanceDao;
	
	//Ԥ��������Dao
	@Resource(name = "fiAdvancesetHibernateDaoImpl")
	private IFiAdvancesetDao fiAdvancesetDao;
	
	//���˵�
	@Resource(name = "fiReceivablestatementHibernateDaoImpl")
	private IFiReceivablestatementDao fiReceivablestatementDao;
	
	//Ƿ��������ϸ
	@Resource(name = "fiReceivabledetailHibernateDaoImpl")
	private IFiReceivabledetailDao fiReceivabledetailDao;
	
	//����ɱ�
	@Resource(name = "fiDeliverycostServiceImpl")
	private IFiDeliverycostService fiDeliverycostService;
	
	//��ת�ɱ�
	@Resource(name = "fiTransitcostServiceImpl")
	private IFiTransitcostService fiTransitService;
	
	//�ⷢ�ɱ�
	@Resource(name="fiOutCostServiceImpl")
	private IFiOutCostService fiOutCostService;
	
	//�����ɱ�
	@Resource(name="oprSignRouteServiceImpl")
	private IOprSignRouteService oprSignRouteService;
	
	//���˵�
	@Resource(name = "fiReceivablestatementServiceImpl")
	private IFiReceivablestatementService fiReceivablestatementService;
	
	// Ƿ����ϸService
	@Resource(name = "fiReceivabledetailServiceImpl")
	private IFiReceivabledetailService fiReceivabledetailService;
	
	//����״̬��
	@Resource(name="oprStatusServiceImpl")
	private IOprStatusService oprStatusService;
	
	//�����˿�
	@Resource(name="fiProblemreceivableServiceImpl")
	private IFiProblemreceivableService fiProblemreceivableService;
	
	//�쳣������
	@Resource(name = "fiPaymentabnormalServiceImpl")
	private IFiPaymentabnormalService fiPaymentabnormalService;
	
	//�ʽ𽻽ӵ�
	@Resource(name="fiFundstransferServiceImpl")
	private IFiFundstransferService fiFundstransferService;
	
	//������㱨��
	@Resource(name="fiIncomeAccountServiceImpl")
	private IFiIncomeAccountService fiIncomeAccountService;
	
	//��־
	@Resource(name = "oprHistoryServiceImpl")
	private IOprHistoryService oprHistoryService;
	
	@Resource(name="fiAdvancebpServiceImpl")
	private IFiAdvancebpService fiAdvancebpService;
	
	@ModuleName(value="����ʵ��ʵ��",logType=LogType.fi)
	public String revocation(Long fiPaidId) throws Exception {
		FiPaid fiPaid = this.fiPaidDao.get(fiPaidId);
		if (fiPaid==null){
			return "ʵ�ո��δ�ҵ����޷������ո��";
		}
		if(Long.valueOf(fiPaid.getVerificationStatus())==null||fiPaid.getVerificationStatus()==1L){
			return "ʵ�ո���Ѻ������޷������ո��";
		}
		if(fiPaid.getFiFundstransferStatus()==1L){
			return "ʵ�ո�����Ͻ����޷������ո��";
		}
		
		FiPayment fiPayment = this.fiPaymentDao.get(fiPaid.getFiPaymentId());
		if (fiPaid==null){
			return "ʵ�յ���Ӧ��Ӧ�յ���δ�ҵ����޷������ո��";
		}
		
		String penyJenis = fiPaid.getPenyJenis();// ���㷽ʽ
		if (penyJenis.equals("�ֽ�") || penyJenis.equals("����")|| penyJenis.equals("POS")) {
			this.accountRevocation(fiPaid);
		}else if (penyJenis.equals("֧Ʊ")) {
			checkRevocation(fiPaid,fiPayment);
		}else if (penyJenis.equals("�ո��Գ�")) {
			paymentInfoRevocation(fiPaid);
		}else if (penyJenis.equals("Ԥ������")) {
			advanceInfoRevocation(fiPaid,fiPayment);
		}else if(penyJenis.equals("ί��ȷ��")){
			entrustInfoRevocation(fiPaid,fiPayment);
		}
		return "true";
	}
	
	//�ո���������
	private void paymentInfoRevocation(FiPaid fiPaid) throws Exception{
		double amount=0.0;
		double fiPaymentSettlementAmount =0.0;
		String costType=null;
		List<FiPaid> list=this.fiPaidDao.find("from FiPaid f where f.paidId=? and f.status=1L",fiPaid.getPaidId());
		for(FiPaid fiPaid1:list){
			FiPayment fiPayment=this.fiPaymentDao.get(fiPaid1.getFiPaymentId());
			Double fiPaidsettlementAmount = fiPaid1.getSettlementAmount();// ʵ�ո����

			if(fiPaid1.getFiPaidWriteId()!=null||"".equals(fiPaid1.getFiPaidWriteId())){
				FiPaid fp=this.fiPaidDao.get(fiPaid1.getFiPaidWriteId());
				FiPayment fiPayment1=this.fiPaymentDao.get(fp.getFiPaymentId());
				costType=fiPayment1.getCostType();
				if("�����˿�".equals(fiPayment1.getCostType())||"�쳣������".equals(fiPayment1.getCostType())){
					fiPaymentSettlementAmount = DoubleUtil.sub(fiPayment.getEliminationAmount(),fiPaidsettlementAmount);// Ӧ�ո����ѽ�����=�ѽ�����-ʵ��ʵ�����
					fiPayment.setEliminationAmount(fiPaymentSettlementAmount);
				}else{
					fiPaymentSettlementAmount = DoubleUtil.sub(fiPayment.getSettlementAmount(),fiPaidsettlementAmount);// Ӧ�ո����ѽ�����=�ѽ�����-ʵ��ʵ�����
					fiPayment.setSettlementAmount(fiPaymentSettlementAmount);
				}
			}else{
				fiPaymentSettlementAmount = DoubleUtil.sub(fiPayment.getSettlementAmount(),fiPaidsettlementAmount);// Ӧ�ո����ѽ�����=�ѽ�����-ʵ��ʵ�����
				fiPayment.setSettlementAmount(fiPaymentSettlementAmount);
			}
			amount=fiPayment.getAmount();//Ӧ���ܶ�
			fiPaymentSettlementAmount=DoubleUtil.add(fiPayment.getSettlementAmount(),fiPayment.getEliminationAmount());
			if(fiPayment.getPaymentType()==1L){//�����տ
				//Ӧ��Ӧ������
				if (fiPaymentSettlementAmount != amount) {
					fiPayment.setPaymentStatus(Long.valueOf(1));// δ�տ�
				}
			}else{
				if (fiPaymentSettlementAmount != amount) {
					fiPayment.setPaymentStatus(Long.valueOf(4));// δ����
				}
			}
			
			this.fiPaymentDao.save(fiPayment);
			
			//ʵ��ʵ������
			fiPaid1.setStatus(Long.valueOf(0));
			this.fiPaidDao.save(fiPaid1);
			
			//��������ģ���������
			if(fiPayment.getPaymentType()==1L&&!"�����˿�".equals(costType)){//�����տ
				this.revocationReceiving(fiPayment, fiPaid);
			}

			if(fiPayment.getPaymentType()==2){
				this.revocationPayment(fiPayment, fiPaid);
			}
		}
	}

	
	//Ԥ����������
	private void advanceInfoRevocation(FiPaid fiPaid, FiPayment fiPayment) throws Exception{
		Double fiPaidsettlementAmount = fiPaid.getSettlementAmount();// ʵ�ո����
		double fiPaymentSettlementAmount = fiPayment.getSettlementAmount()
				- fiPaidsettlementAmount;// Ӧ�ո����ѽ�����=�ѽ�����-ʵ��ʵ�����
		double amount=fiPayment.getAmount();
		
		if(fiPaid.getVerificationStatus()==1L) throw new ServiceException("�����Ѻ������������ٳ���");
		
		//Ӧ��Ӧ������
		if(fiPayment.getPaymentType()==1L){//�����տ
			//Ӧ��Ӧ������
			if (fiPaymentSettlementAmount !=amount) {
				fiPayment.setPaymentStatus(Long.valueOf(1));// δ�տ�
			}
		}else{
			if (fiPaymentSettlementAmount !=amount) {
				fiPayment.setPaymentStatus(Long.valueOf(4));// δ����
			}
		}
		fiPayment.setSettlementAmount(fiPaymentSettlementAmount);
		this.fiPaymentDao.save(fiPayment);
		
		//ʵ��ʵ������
		fiPaid.setStatus(Long.valueOf(0));
		this.fiPaidDao.save(fiPaid);
		
		//Ԥ�����嵥����
		FiAdvance fiAdvance=this.fiAdvanceDao.get(fiPaid.getFiAdvanceId());
		fiAdvance.setStatus(Long.valueOf(0));//����
		this.fiAdvanceDao.save(fiAdvance);
		
		//Ԥ�����˺�����
		FiAdvanceset fiAdvanceset=this.fiAdvancesetDao.get(fiAdvance.getFiAdvanceId());
		fiAdvanceset.setOpeningBalance(fiAdvanceset.getOpeningBalance()+fiPaidsettlementAmount);
		this.fiAdvancesetDao.save(fiAdvanceset);
		
		//��������ģ���������..
		this.revocationReceiving(fiPayment, fiPaid);
		
		//������־
		oprHistoryService.saveFiLog(fiPayment.getDocumentsNo(), "ʵ�յ�ID��"+fiPaid.getId()+"��������,��"+fiPaid.getSettlementAmount() , 34L);
		
	}
	
	//֧Ʊ�տ��
	private void checkRevocation(FiPaid fiPaid, FiPayment fiPayment) throws Exception{
		double fiPaidsettlementAmount = fiPaid.getSettlementAmount();// ʵ�ո����
		double fiPaymentSettlementAmount = fiPayment.getSettlementAmount()
				- fiPaidsettlementAmount;// Ӧ�ո����ѽ�����=�ѽ�����-ʵ��ʵ�����
		double amount=fiPayment.getAmount();
		if(fiPaid.getVerificationStatus()==1L) throw new ServiceException("�����Ѻ������������ٳ���");
		//Ӧ��Ӧ������
		if (fiPaymentSettlementAmount != amount) {
			fiPayment.setPaymentStatus(Long.valueOf(1));// δ�տ�
		}
		fiPayment.setSettlementAmount(fiPaymentSettlementAmount);
		this.fiPaymentDao.save(fiPayment);
		
		//ʵ��ʵ������
		fiPaid.setStatus(Long.valueOf(0));
		this.fiPaidDao.save(fiPaid);
		
		//��ȥ֧Ʊ���
		FiCheck fiCheck=this.fiCheckDao.get(fiPaid.getFiCheckId());
		fiCheck.setConfirmDate(new Date());
		fiCheck.setConfirmStatus(0l);
		this.fiCheckDao.save(fiCheck);
		
		//��������ģ���������..
		this.revocationReceiving(fiPayment, fiPaid);
		
		//������־
		oprHistoryService.saveFiLog(fiPayment.getDocumentsNo(), "ʵ�յ�ID��"+fiPaid.getId()+"��������,��"+fiPaid.getSettlementAmount() , 34L);
		
	}
	
	//ί���տ��
	private void entrustInfoRevocation(FiPaid fiPaid, FiPayment fiPayment) throws Exception{
		double fiPaidsettlementAmount = fiPaid.getSettlementAmount();// ʵ�ո����
		double amount=fiPayment.getAmount();
		double fiPaymentSettlementAmount = fiPayment.getSettlementAmount()
				- fiPaidsettlementAmount;// Ӧ�ո����ѽ�����=�ѽ�����-ʵ��ʵ�����
		
		if(fiPaid.getVerificationStatus()==1L) throw new ServiceException("�����Ѻ������������ٳ���");
		//Ӧ��Ӧ������
		if (fiPaymentSettlementAmount != amount) {
			fiPayment.setPaymentStatus(Long.valueOf(1));// δ�տ�
		}
		fiPayment.setSettlementAmount(fiPaymentSettlementAmount);
		this.fiPaymentDao.save(fiPayment);
		
		//ʵ��ʵ������
		fiPaid.setStatus(Long.valueOf(0));
		this.fiPaidDao.save(fiPaid);
		
		//�����տ����
		FiCashiercollection fiCashiercollection=this.fiCashiercollectionDao.get(fiPaid.getFiCashiercollectionId());
		fiCashiercollection.setEntrustAmount(fiCashiercollection.getEntrustAmount()-fiPaid.getSettlementAmount());//ί��ȷ�ս��=��ȷ�ս��-��ȥ�տȷ�ս��
		fiCashiercollection.setEntrustTime(new Date());
		
		this.fiCashiercollectionDao.save(fiCashiercollection);
		
		//��������ģ���������..
		this.revocationReceiving(fiPayment, fiPaid);
		
		//������־
		oprHistoryService.saveFiLog(fiPayment.getDocumentsNo(), "ʵ�յ�ID��"+fiPaid.getId()+"��������,��"+fiPaid.getSettlementAmount() , 34L);
	}
	
	//�ֽ����С�POS��������
	private void accountRevocation(FiPaid fiPaid) throws Exception{
		List<FiPaid> list=this.fiPaidDao.find("from FiPaid f where f.paidId=? and f.status=1L",fiPaid.getPaidId());
		for(FiPaid fiPaid1:list){
			FiPayment fiPayment=this.fiPaymentDao.get(fiPaid1.getFiPaymentId());
			double fiPaidsettlementAmount = fiPaid1.getSettlementAmount();// ʵ�ո����
			double fiPaymentSettlementAmount = fiPayment.getSettlementAmount()- fiPaidsettlementAmount;// Ӧ�ո����ѽ�����=�ѽ�����-ʵ��ʵ�����
			double amount=fiPayment.getAmount();
			if(fiPayment.getPaymentType()==1L){//�����տ
				//Ӧ��Ӧ������
				if (fiPaymentSettlementAmount != amount) {
					fiPayment.setPaymentStatus(Long.valueOf(1));// δ�տ�
				}
			}else{
				if (fiPaymentSettlementAmount != amount) {
					fiPayment.setPaymentStatus(Long.valueOf(4));// δ����
				}
			}
			fiPayment.setSettlementAmount(fiPaymentSettlementAmount);
			this.fiPaymentDao.save(fiPayment);
			
			//ʵ��ʵ������
			fiPaid1.setStatus(Long.valueOf(0));
			this.fiPaidDao.save(fiPaid1);
			
			FiCapitaaccount fiCapitaaccount1 = new FiCapitaaccount();
			
			//�ʽ��˺���ˮ����
			FiCapitaaccount fiCapitaaccount=this.fiCapitaaccountDao.get(fiPaid1.getFiCapitaaccountId());
			
			//�ʽ��˺�����
			FiCapitaaccountset fiCapitaaccountset=this.fiCapitaaccountsetDao.get(fiCapitaaccount.getFiCapitaaccountsetId());
			if(fiPayment.getPaymentType()==2L){
				fiCapitaaccountset.setOpeningBalance(fiCapitaaccountset.getOpeningBalance()+fiPaidsettlementAmount);
				fiCapitaaccount1.setBorrow(DoubleUtil.mul(fiCapitaaccount.getBorrow(), -1));// ��
			}else{
				fiCapitaaccountset.setOpeningBalance(fiCapitaaccountset.getOpeningBalance()-fiPaidsettlementAmount);
				fiCapitaaccount1.setLoan(DoubleUtil.mul(fiCapitaaccount.getLoan(), -1));// ��
			}
			this.fiCapitaaccountsetDao.save(fiCapitaaccountset);
			
			fiCapitaaccount1.setFiCapitaaccountsetId(fiCapitaaccountset.getId());
			
			fiCapitaaccount1.setSourceData("ʵ��ʵ��");
			fiCapitaaccount1.setRemark("��������");
			fiCapitaaccount1.setSourceNo(fiPaid1.getId());
			fiCapitaaccount1.setBalance(fiCapitaaccountset.getOpeningBalance());// ���
			
			this.fiCapitaaccountDao.save(fiCapitaaccount1);

			//��������ģ���������
			if(fiPayment.getPaymentType()==1L){//�����տ
				this.revocationReceiving(fiPayment, fiPaid);
			}else{
				this.revocationPayment(fiPayment, fiPaid);
			}

			//������־
			oprHistoryService.saveFiLog(fiPayment.getDocumentsNo(), "ʵ�յ�ID��"+fiPaid.getId()+"��������,��"+fiPaid.getSettlementAmount() , 34L);
		}
	}
	
	//�������
	private void revocationPayment(FiPayment fiPayment,FiPaid fiPaid) throws Exception{
		//����ɱ���������
		if ("����ɱ�".equals(fiPayment.getSourceData())) {
			this.fiDeliverycostService.payConfirmationRegisterBybatchNo(fiPayment.getSourceNo());
		}
		
		//��ת�ɱ�����
		if ("��ת�ɱ�".equals(fiPayment.getSourceData())) {
			this.fiTransitService.payConfirmationRegisterBybatchNo(fiPayment.getSourceNo());
		}
		
		//�ⷢ�ɱ�����
		if ("�ⷢ�ɱ�".equals(fiPayment.getSourceData())) {
			this.fiOutCostService.payConfirmationRegisterBybatchNo(fiPayment.getSourceNo());
		}
		
		//�����ɱ�����
		if ("�����ɱ�".equals(fiPayment.getSourceData())) {
			this.oprSignRouteService.payConfirmationRegisterById(fiPayment.getSourceNo());
		}
		
		if ("���˵�".equals(fiPayment.getSourceData())) {
			this.fiReceivablestatementService.revocationFiPaid(fiPayment, fiPaid);
		}
		
		//�����˿����
		if("�����˿�".equals(fiPayment.getSourceData())){
			this.fiProblemreceivableService.verfiProblemreceivableRegister(fiPaid.getSettlementAmount(), fiPayment.getSourceNo());
		}
		
		//�쳣���������
		if("�쳣������".equals(fiPayment.getSourceData())){
			this.fiPaymentabnormalService.verfiPaymentabnormalRegister(fiPaid.getSettlementAmount(), fiPayment.getSourceNo());
		}
		
		// ��дԤ������״̬
		if ("Ԥ��".equals(fiPayment.getDocumentsSmalltype())) {
			this.fiAdvancebpService.verfiFiAdvancebpRegister(fiPayment.getDocumentsNo());
		}
	}
	
	//�տ����
	private void revocationReceiving(FiPayment fiPayment,FiPaid fiPaid) throws Exception{
		
		if ("���˵�".equals(fiPayment.getSourceData())) {
			this.fiReceivablestatementService.revocationFiPaid(fiPayment, fiPaid);
		}
		
		//�ֽ�Ӧ�մ��ջ����������Ӧ�����ջ���
		if("���ջ���".equals(fiPayment.getCostType())){
			this.fiReceivabledetailService.revocationFiPaid(fiPayment);
		}
		
		//���´���״̬������״̬
		if ("���͵�".equals(fiPayment.getDocumentsSmalltype())) {
			this.oprStatusService.revocationCashStatus(fiPayment.getDocumentsNo());
		}
		
		// ��дԤ������״̬
		if ("Ԥ��".equals(fiPayment.getDocumentsSmalltype())) {
			this.fiAdvancebpService.verfiFiAdvancebpRegister(fiPayment.getDocumentsNo());
		}
		
	}

	public void verificationById(Long id,User user) throws Exception {
		FiPaid fiPaid=this.fiPaidDao.get(id);
		if(fiPaid==null){
			throw new ServiceException("����ʧ�ܣ�ʵ��ʵ���������ڣ�");
		}
		
		fiPaid.setFiFundstransferStatus(1L);//�Ͻ�״̬
		fiPaid.setVerificationAmount(fiPaid.getSettlementAmount());
		fiPaid.setVerificationUser(String.valueOf(user.get("name")));
		fiPaid.setVerificationDept(String.valueOf(user.get("departName")));
		fiPaid.setVerificationTime(new Date());
		fiPaid.setVerificationStatus(1L);
	}
	
	public void paymentVerification(String ids,User user) throws Exception {
		Long id=0L;
		if("null".equals(ids)||"".equals(ids)){
			throw new ServiceException("����ʧ�ܣ�ʵ��ʵ���������ڣ�");
		}
		String[] idsp=ids.split(",");
		if(idsp.length==0){
			throw new ServiceException("����ʧ�ܣ�ʵ��ʵ���������ڣ�");
		}
		for(int i=0;i<idsp.length;i++){
			id=Long.valueOf(idsp[i]);
			this.verificationById(id, user);
		}
	}
	
	
	public Map searchHandInAmount(Map map) throws Exception{
		String paidIds=this.tosqlids(map.get("paidIds")+"");//ʵ��ʵ��ID
		Long userId=0L;
		User user=WebRalasafe.getCurrentUser(Struts2Utils.getRequest()); 
		userId=Long.valueOf(user.get("id")+"");
		StringBuffer sb=new StringBuffer();
		sb.append("select nvl(sum(f.settlement_amount),0) as settlement_amount from fi_paid f left join fi_payment fp on f.fi_payment_id=fp.id where fp.payment_type=1 and f.verification_status=0 and f.status=1 and f.FI_FUNDSTRANSFER_STATUS=0 and f.PENY_JENIS='�ֽ�' and f.create_id=?");
		if(!"null".equals(paidIds)&&!"".equals(paidIds)){
			sb.append(" and f.paid_id in (");
			sb.append(paidIds);
			sb.append(")");
		}
		List<Map> list=this.fiPaidDao.createSQLQuery(sb.toString(), userId).list();
		Map mapReturn=new HashMap<String, Object>();
		if(list.size()>0){
			mapReturn=list.get(0);
		}else{
			throw new ServiceException("��û����Ҫ�Ͻ��Ľ��!");
		}
		return mapReturn;
	}
	
	
	public void handInConfirmation(Map map,User user) throws Exception{
		String paidIds=this.tosqlids(map.get("paidIds")+"");
		Long receivablesaccountId=Long.valueOf(map.get("receivablesaccountId")+"");//�տ��˺�ID
		Long receivablesaccountDeptid=Long.valueOf(map.get("receivablesaccountDeptid")+"");//�տ��ID
		String receivablesaccountDept=map.get("receivablesaccountDept")+"";//�տ��˲���
		Long paymentaccountId=Long.valueOf(map.get("paymentaccountId")+"");//�����˺�ID
		String remark=map.get("remark")+"";//���ע
		Double settlementAmountSum=0.0;//ʵ���ܽ��
		Double settlementAmount=Double.valueOf(map.get("settlementAmount")+"");//ʵ���ܽ��
		Double sumAmount=Double.valueOf(map.get("sumAmount")+"");//δ�Ͻ��ܽ��
		Long userId=0L;
	    userId=Long.valueOf(user.get("id")+"");
	    StringBuffer sb=new StringBuffer();
	    sb.append("select f.* from fi_paid f left join fi_payment fp on f.fi_payment_id=fp.id where fp.payment_type=1 and f.verification_status=0 and f.status=1 and f.FI_FUNDSTRANSFER_STATUS=0 and f.PENY_JENIS='�ֽ�' and f.create_id=");
	    sb.append(userId);
	    if(!"null".equals(paidIds)&&!"".equals(paidIds)){
	      sb.append(" and f.paid_id in (");
	      sb.append(paidIds);
	      sb.append(")");
	    }
	    Map mapReturn=this.searchHandInAmount(map);
	    //settlementAmount=Double.valueOf(mapReturn.get("SETTLEMENT_AMOUNT")+"");
	    //�������ӵ�
	    FiFundstransfer fiFundstransfer=new FiFundstransfer();
	    fiFundstransfer.setPaymentaccountId(paymentaccountId);
	    fiFundstransfer.setReceivablesaccountDeptid(receivablesaccountDeptid);
	    fiFundstransfer.setReceivablesaccountId(receivablesaccountId);
	    fiFundstransfer.setReceivablesaccountDept(receivablesaccountDept);
	    fiFundstransfer.setRemark(remark);
	    fiFundstransfer.setSourceData("ʵ��ʵ����");
	    fiFundstransfer.setAmount(sumAmount);
	    this.fiFundstransferService.save(fiFundstransfer);
	    
	    List<FiPaid> list=this.fiPaidDao.getSession().createSQLQuery(sb.toString()).addEntity(FiPaid.class).list();
	    if(list==null||list.size()<=0){
	    	throw new ServiceException("δ�ҵ���Ҫ�Ͻ���ʵ�յ�!");
	    }
	    
	    for(FiPaid fiPaid:list){
	    	//ʵ��ʵ���ܽ��
	    	settlementAmountSum=DoubleUtil.add(settlementAmountSum, fiPaid.getSettlementAmount());
	    	//����ʵ��ʵ��
	    	/*fiPaid.setVerificationAmount(fiPaid.getSettlementAmount());
	    	fiPaid.setVerificationStatus(1L);
	    	fiPaid.setVerificationTime(new Date());
	    	fiPaid.setVerificationUser(user.get("name")+"");*/
	    	fiPaid.setFiFundstransferId(fiFundstransfer.getId());
	    	fiPaid.setFiFundstransferStatus(1L);
	    	this.fiPaidDao.save(fiPaid);
	    }
	    
	    if(!settlementAmount.equals(settlementAmountSum)){
	    	throw new ServerCloneException("�����쳣������������!");
	    }
	    
	}
	
	// ȥ��ѡ�ж�����¼������һ�����ţ��磺(1,2,3,)��ɣ�(1,2,3)
	private String tosqlids(String ids) throws Exception {
		if (!ids.equals("")) {
			String last;
			last = ids.substring(ids.length() - 1, ids.length());
			if (last.equals(",")) {
				ids = ids.substring(0, ids.length() - 1);
			}
		}
		return ids;

	}
	
	public void addAccountSingle(Long departId,String startTime,String endTime,Long seq) throws Exception{
		Double cashAmount=0.0;//�ֽ�
		Double posAmount=0.0;//POS
		Double checkAmount=0.0;//֧Ʊ
		Double intecollectionAmount1=0.0;//�ڲ��������(�������)
		Double intecollectionAmount2=0.0;//�ڲ��������(����������)
		Double intecollectionAmount=0.0;//�ڲ��������(���������-���������յ�)
		Double eliminationAmount=0.0;//��������
		Double collectionAmount=0.0;//���ջ���
		Double consigneeAmount=0.0;//������
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date accountData = sdf.parse(startTime);
		String hql=null;
		String sql=null;
		
		//���ݺ�������ͳ���轻���տ
		//��������������
		hql="update fi_paid f set f.account_id=? where exists(select 1 from fi_payment fp where f.fi_payment_id=fp.id and fp.payment_type=1 and f.VERIFICATION_TIME between to_date('"+startTime+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+endTime+"','yyyy-mm-dd hh24:mi:ss') and f.DEPART_ID=? and f.VERIFICATION_STATUS=1 and f.status=1 and (f.peny_Jenis='�ֽ�' or f.peny_Jenis='֧Ʊ' or f.peny_Jenis='POS' or f.peny_Jenis='�ո��Գ�'))";
		this.fiPaidDao.batchSQLExecute(hql, seq,departId);
		
		//��Ĳ��Ŵ���������
		hql="update fi_paid f set f.account_ds_id=? where exists(select 1 from fi_payment fp where f.fi_payment_id=fp.id and fp.payment_type=1 and f.VERIFICATION_TIME between to_date('"+startTime+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+endTime+"','yyyy-mm-dd hh24:mi:ss') and f.DEPART_ID!=? and f.income_depart_id=? and f.VERIFICATION_STATUS=1 and f.status=1 and (f.peny_Jenis='�ֽ�' or f.peny_Jenis='֧Ʊ' or f.peny_Jenis='POS' or f.peny_Jenis='�ո��Գ�'))";
		this.fiPaidDao.batchSQLExecute(hql, seq,departId,departId);
		
		//��ȡ������ʵ����������
		sql="select nvl(sum(case when fp.peny_jenis='�ֽ�' then fp.settlement_amount else 0 end),0) as cashAmount,nvl(sum(case when fp.peny_jenis='POS' then fp.settlement_amount else 0 end),0) as posAmount,nvl(sum(case when fp.peny_jenis='֧Ʊ' then fp.settlement_amount else 0 end),0) as checkAmount,nvl(sum(case when fp.peny_jenis='�ո��Գ�' then fp.settlement_amount else 0 end),0) as eliminationAmount from fi_paid fp where fp.account_id=? and fp.DEPART_ID=?";
		Map map =(Map) this.fiPaidDao.createSQLQuery(sql, seq,departId).list().get(0);
		cashAmount=Double.valueOf(map.get("CASHAMOUNT")+"");//�ֽ�ϼ�
		posAmount=Double.valueOf(map.get("POSAMOUNT")+"");//POS�ϼ�
		checkAmount=Double.valueOf(map.get("CHECKAMOUNT")+"");//֧Ʊ�ϼ�
		eliminationAmount=Double.valueOf(map.get("ELIMINATIONAMOUNT")+"");//��������
		
		//ҵ������(���㱾���ŵĵ���������ջ���+��Ĳ��Ŵ��յ���������ջ���)
		sql="select nvl(sum(case when fy.cost_type != '���ջ���' then fp.settlement_amount else 0 end),0) as consigneeAmount,nvl(sum(case when fy.cost_type = '���ջ���' then fp.settlement_amount else 0 end),0) as collectionAmount from fi_paid fp left join fi_payment fy on fp.fi_payment_id = fy.id and (fp.account_id=? or fp.account_ds_id=?) and ((fp.DEPART_ID=? and fp.income_depart_id=?) or (fp.DEPART_ID!=? and fp.income_depart_id=?))";
		Map map1 =(Map) this.fiPaidDao.createSQLQuery(sql, seq,seq,departId,departId,departId,departId).list().get(0);
		consigneeAmount=Double.valueOf(map1.get("CONSIGNEEAMOUNT")+"");
		collectionAmount=Double.valueOf(map1.get("COLLECTIONAMOUNT")+"");
		
		//�ڲ�����(�������м��������յ�)
		sql="select nvl(sum(fp.settlement_amount),0) as intecollectionAmount from fi_paid fp left join fi_payment fy on fp.fi_payment_id = fy.id where fp.account_id=? and fp.DEPART_ID=? and fp.income_depart_id!=?";
		Map map2 =(Map) this.fiPaidDao.createSQLQuery(sql, seq,departId,departId).list().get(0);
		intecollectionAmount1=Double.valueOf(map2.get("INTECOLLECTIONAMOUNT")+"");
		
		//�ڲ�����(�ӱ��������������벿��Ϊ�����ŵ�)
		sql="select nvl(sum(fp.settlement_amount),0) as intecollectionAmount from fi_paid fp left join fi_payment fy on fp.fi_payment_id = fy.id where fp.account_ds_id=? and fp.DEPART_ID!=? and fy.income_depart_id=?";
		Map map3 =(Map) this.fiPaidDao.createSQLQuery(sql, seq,departId,departId).list().get(0);
		intecollectionAmount2=Double.valueOf(map3.get("INTECOLLECTIONAMOUNT")+"");
		
		//�ڲ��������
		intecollectionAmount=DoubleUtil.sub(intecollectionAmount1,intecollectionAmount2);
		
		FiIncomeAccount fiIncomeAccount=new FiIncomeAccount();
		fiIncomeAccount.setTypeName("����");
		fiIncomeAccount.setCashAmount(cashAmount);
		fiIncomeAccount.setPosAmount(posAmount);
		fiIncomeAccount.setCheckAmount(checkAmount);
		fiIncomeAccount.setEliminationAmount(eliminationAmount);
		fiIncomeAccount.setConsigneeAmount(consigneeAmount);
		fiIncomeAccount.setCollectionAmount(collectionAmount);
		fiIncomeAccount.setDepartId(departId);
		fiIncomeAccount.setIntecollectionAmount(intecollectionAmount);
		fiIncomeAccount.setBatchNo(seq);
		fiIncomeAccount.setAccountData(accountData);
		this.fiIncomeAccountService.save(fiIncomeAccount);
	}
	
	public Page findAccountSingle(Page page,Map map){
		Page pageReturn=null;
		StringBuffer  hql=new StringBuffer();
		hql.append("select pd.id,pd.paid_id,pd.fi_payment_id,pd.peny_jenis,pt.payment_type,pt.cost_type,pd.settlement_amount,pt.documents_type,pt.documents_smalltype,pt.documents_no,pd.create_name,pd.create_time from fi_paid pd inner join fi_payment pt on pd.fi_payment_id=pt.id");
		hql.append(" where pd.account_id=:accountId or pd.account_ds_id=:accountId");
		pageReturn=this.fiPaidDao.findPageBySqlMap(page, hql.toString(), map);
		return pageReturn;
	}
}
