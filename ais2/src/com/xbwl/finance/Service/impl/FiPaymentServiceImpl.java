package com.xbwl.finance.Service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.log.anno.ModuleName;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.orm.Page;
import com.xbwl.common.orm.hibernate.pojo.AuditableEntity;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.common.utils.DoubleUtil;
import com.xbwl.common.utils.LogType;
import com.xbwl.common.utils.XbwlInt;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.Customer;
import com.xbwl.entity.FiAdvance;
import com.xbwl.entity.FiAdvanceset;
import com.xbwl.entity.FiArrearset;
import com.xbwl.entity.FiCapitaaccount;
import com.xbwl.entity.FiCapitaaccountset;
import com.xbwl.entity.FiCashiercollection;
import com.xbwl.entity.FiCheck;
import com.xbwl.entity.FiPaid;
import com.xbwl.entity.FiPayment;
import com.xbwl.entity.FiReceivabledetail;
import com.xbwl.entity.OprStatus;
import com.xbwl.finance.Service.IFiAdvancebpService;
import com.xbwl.finance.Service.IFiAdvancesetServie;
import com.xbwl.finance.Service.IFiArrearsetService;
import com.xbwl.finance.Service.IFiCheckService;
import com.xbwl.finance.Service.IFiDeliverycostService;
import com.xbwl.finance.Service.IFiOutCostService;
import com.xbwl.finance.Service.IFiPaymentService;
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
import com.xbwl.oper.fax.service.IOprFaxInService;
import com.xbwl.oper.stock.service.IOprHistoryService;
import com.xbwl.oper.stock.service.IOprSignRouteService;
import com.xbwl.oper.stock.service.IOprStatusService;
import com.xbwl.sys.service.ICustomerService;

import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;

/**
 * @author ������ TODO ����������Ӧ��Ӧ������
 * @param <User>
 * @2011-7-16
 * 
 */
@Service("fiPaymentServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class FiPaymentServiceImpl extends BaseServiceImpl<FiPayment, Long>
		implements IFiPaymentService {

	@Resource(name = "fiPaymentHibernateDaoImpl")
	private IFiPaymentDao fiPaymentDao;

	// �ʽ��˺�Dao
	@Resource(name = "fiCapitaaccountsetHibernateDaoImpl")
	private IFiCapitaaccountsetDao fiCapitaaccountsetDao;

	// �ʽ��˺���ˮDao
	@Resource(name = "fiCapitaaccountHibernateDaoImpl")
	private IFiCapitaaccountDao fiCapitaaccountDao;

	// ʵ��ʵ��Dao
	@Resource(name = "fiPaidHibernateDaoImpl")
	private IFiPaidDao fiPaidDao;

	// ֧ƱDao
	@Resource(name = "fiCheckHibernateImpl")
	private IFiCheckDao fiCheckDao;

	// Ԥ��������Dao
	@Resource(name = "fiAdvancesetHibernateDaoImpl")
	private IFiAdvancesetDao fiAdvancesetDao;

	// Ԥ�������Dao
	@Resource(name = "fiAdvanceHibernateDaoImpl")
	private IFiAdvanceDao fiAdvanceDao;

	// Ƿ����ϸService
	@Resource(name = "fiReceivabledetailServiceImpl")
	private IFiReceivabledetailService fiReceivabledetailService;

	// �����տDao
	@Resource(name = "fiCashiercollectionHibernateDaoImpl")
	private IFiCashiercollectionDao fiCashiercollectionDao;
	
	//����
	@Resource(name = "customerServiceImpl")
	private ICustomerService customerService;
	
	//����Ƿ������
	@Resource(name="fiArrearsetServiceImpl")
	private IFiArrearsetService fiArrearsetService;
	
	//���˵�
	@Resource(name = "fiReceivablestatementServiceImpl")
	private IFiReceivablestatementService fiReceivablestatementService;
	
	//�����˿�
	@Resource(name="fiProblemreceivableServiceImpl")
	private IFiProblemreceivableService fiProblemreceivableService;
	
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
	
	//����״̬��
	@Resource(name="oprStatusServiceImpl")
	private IOprStatusService oprStatusService;
	
	//�쳣������
	@Resource(name = "fiPaymentabnormalServiceImpl")
	private IFiPaymentabnormalService fiPaymentabnormalService;
	
	//��־
	@Resource(name = "oprHistoryServiceImpl")
	private IOprHistoryService oprHistoryService;
	
	@Resource(name="fiAdvancebpServiceImpl")
	private IFiAdvancebpService fiAdvancebpService;
	
	@Override
	public IBaseDAO<FiPayment, Long> getBaseDao() {
		return fiPaymentDao;
	}

	@ModuleName(value="�ո����ѯδ������",logType=LogType.buss)
	public Map searchReceiving(Map map) throws Exception {
		StringBuffer sqlbuffer = new StringBuffer();
		String documentsSmalltype = (String) map.get("documentsSmalltype");// ����С��
		String ids = this.tosqlids((String) map.get("ids"));
		Double amount=0.0;//�����ܽ��
		Double settlementAmount=0.0;//�ѽ���
		Double eliminationAmount=0.0;//�ѳ���
		Double thesettlementAmount=0.0;//���ν��������
		if (documentsSmalltype.equals("���͵�")) {
			sqlbuffer
					.append("select nvl(sum(a.amount),0) as amount,nvl(sum(a.settlement_amount),0) as settlement_amount,nvl(sum(a.elimination_amount),0) as elimination_amount from fi_payment a where a.documents_no in (select distinct(documents_no) from fi_payment where id in (");
			sqlbuffer.append(ids);
			sqlbuffer.append(")");
		} else {
			sqlbuffer.append("select nvl(sum(a.amount),0) as amount,nvl(sum(a.settlement_amount),0) as settlement_amount,nvl(sum(a.elimination_amount),0) as elimination_amount from fi_payment a where a.id in (");
			sqlbuffer.append(ids);
		}
		sqlbuffer.append(") and (a.payment_status=1 or a.payment_status=3) and a.status=1");
		List<Map> list=this.fiPaymentDao.createSQLQuery(sqlbuffer.toString()).list();
		Map m=list.get(0);
		amount=Double.valueOf(m.get("AMOUNT")+"");
		settlementAmount=Double.valueOf(m.get("SETTLEMENT_AMOUNT")+"");
		eliminationAmount=Double.valueOf(m.get("ELIMINATION_AMOUNT")+"");
		
		//���ν�����
		//REVIEW ����1���nvlû�б�Ҫ
		String sql="select nvl((nvl(sum(a.amount),0)-nvl(sum(a.settlement_amount),0)-nvl(sum(a.elimination_amount),0)),0) as thesettlementAmount from fi_payment a where a.id in ("+ids+")";
		List<Map> list1=this.fiPaymentDao.createSQLQuery(sql).list();
		Map m1=list1.get(0);
		thesettlementAmount=Double.valueOf(m1.get("THESETTLEMENTAMOUNT")+"");
		
		Map mapreturn=new HashMap<String,Object>();
		mapreturn.put("amount",amount);
		mapreturn.put("settlementAmount",settlementAmount);
		mapreturn.put("eliminationAmount",eliminationAmount);
		mapreturn.put("thesettlementAmount", thesettlementAmount);
		
		return mapreturn;
	}

	public Page<FiPayment> searchPayment(Page page, Map map) throws Exception {
		String sql = this.PaymentSql(map);
		return this.getPageBySql(page, sql);
	}

	@ModuleName(value="�տ�ȷ��",logType=LogType.fi)
	public void saveReceiving(Map<String,Object> map,User user) throws Exception{
		String penyJenis = map.get("penyJenis").toString();// �տʽ

		String sql = this.PaymentSql(map);
		List<FiPayment> list = this.fiPaymentDao.getSession().createSQLQuery(
				sql).addEntity(FiPayment.class).list();

		if (penyJenis.equals("�ֽ�") || penyJenis.equals("����")
				|| penyJenis.equals("POS")) {
			accountReceiving(list, map);
		} else if (penyJenis.equals("֧Ʊ")) {
			checkReceiving(list, map);
		} else if (penyJenis.equals("�ո��Գ�")) {
			paymentInfo(list, map);
		} else if (penyJenis.equals("Ԥ������")) {
			advanceInfo(list, map);
		} else if (penyJenis.equals("ί��ȷ��")) {
			entrustInfo(list, map,user);
		}
	}

	// ����˺Ÿ���(�ֽ����С�POS)
	@ModuleName(value="����ȷ��",logType=LogType.fi)
	public void savePayment(Map<String, Object> map) throws Exception {
		Long seq=0L;
		String accountNum = null; // �˺�
		String accountName = null; // �˺�����
		String bank = null; // ������
		Double openingBalance = 0.0; // ���

		String sql = this.PaymentSql(map);
		List<FiPayment> list = this.fiPaymentDao.getSession().createSQLQuery(
				sql).addEntity(FiPayment.class).list();

		Long accountNumId = Long.valueOf(map.get("selectIds").toString());// �տ��˺�id
		Double thesettlementAmount = Double.valueOf(map.get("settlementAmount")
				.toString());// ���θ�����
		String penyJenis = map.get("penyJenis").toString();// �տʽ

		// ��ȡ�˺���Ϣ
		FiCapitaaccountset fiCapitaaccountset = this.fiCapitaaccountsetDao
				.get(accountNumId);
		accountNum = fiCapitaaccountset.getAccountNum();// �ո����˺�
		accountName = fiCapitaaccountset.getAccountName();// �տ��˺�����
		bank = fiCapitaaccountset.getBank();// �տ����
		openingBalance = fiCapitaaccountset.getOpeningBalance();// �˺����

		String sqlseq="select SEQ_FI_PAID.nextval SEQ from dual";
		Map seqid=(Map) this.fiPaidDao.createSQLQuery(sqlseq).list().get(0) ;
		seq=Long.valueOf(seqid.get("SEQ")+"");
		
		User user=WebRalasafe.getCurrentUser(Struts2Utils.getRequest()); 
		Long createId=Long.valueOf(user.get("id")+"");
		
		for (int i = 0; i < list.size(); i++) {
			FiPayment fiPayment = list.get(i);
			
			if(fiPayment.getReviewStatus()==0L){
				throw new ServiceException("���ݻ�δ��ˣ�������֧����");
			}
			
			Double thisamount = DoubleUtil.sub(fiPayment.getAmount(), fiPayment.getSettlementAmount()==null?0.0:fiPayment.getSettlementAmount());// ������Ҫ������
			
			if (Double.doubleToLongBits(thisamount) > Double
					.doubleToLongBits(thesettlementAmount)) {// �����տ���>�տ�ʣ���ܽ��
				thisamount = thesettlementAmount;
			}

			fiPayment.setSettlementAmount(thisamount
					+ fiPayment.getSettlementAmount());// ʵ���ܽ��
			if (Double.doubleToLongBits(fiPayment.getSettlementAmount()) >= Double
					.doubleToLongBits(fiPayment.getAmount())) {
				fiPayment.setPaymentStatus(Long.valueOf(5));// �Ѹ���
			}
			this.fiPaymentDao.save(fiPayment);// ����Ӧ��Ӧ����

			
			openingBalance=DoubleUtil.sub(openingBalance , thisamount);
			if(openingBalance<0.0){
				throw new ServiceException("�����˺����㣬������ɱ��θ��");
			}
			// �����˺���ˮ���
			FiCapitaaccount fiCapitaaccount = new FiCapitaaccount();
			fiCapitaaccount.setFiCapitaaccountsetId(accountNumId);
			fiCapitaaccount.setBorrow(thisamount);// ��
			fiCapitaaccount.setSourceData("���");
			fiCapitaaccount.setSourceNo(fiPayment.getId());
			fiCapitaaccount.setBalance(openingBalance);// ���

			this.fiCapitaaccountDao.save(fiCapitaaccount);

			// ����ʵ��ʵ����
			FiPaid fiPaid = new FiPaid();
			fiPaid.setPaidId(seq);
			fiPaid.setFiCapitaaccountId(fiCapitaaccount.getId());
			fiPaid.setAccountNum(accountNum);
			fiPaid.setAccountName(accountName);
			fiPaid.setBank(bank);
			fiPaid.setFiPaymentId(fiPayment.getId());
			fiPaid.setPenyJenis(penyJenis);// ���㷽ʽ
			fiPaid.setSettlementAmount(thisamount);// ����ʵ�����
			fiPaid.setVerificationStatus(0L);
			fiPaid.setCreateId(createId);
			fiPaid.setIncomeDepartId(fiPayment.getIncomeDepartId());//���벿��
			this.fiPaidDao.save(fiPaid);

			thesettlementAmount = DoubleUtil.sub(thesettlementAmount , thisamount);// ֧Ʊʣ����
			
			//���ݸ����������ģ�鵥��
			this.verificationPayment(fiPayment,thisamount);
			//���������־
			oprHistoryService.saveFiLog(fiPayment.getDocumentsNo(), "Ӧ����ID��"+fiPayment.getId()+"ͨ���ֽ𸶿�,��������Ϊ��"+fiPayment.getCostType()+",�����"+thisamount , 86L);
			
			if (thesettlementAmount <= 0) {
				continue;
			}
		}
		// ���¸����ʺ���� 
		fiCapitaaccountset.setOpeningBalance(openingBalance);
		this.fiCapitaaccountsetDao.save(fiCapitaaccountset);
	}

	
	// ί��ȷ��
	private void entrustInfo(List<FiPayment> list, Map map,User user) throws Exception {
		Long seq=0L;
		Double settlementAmount=0.0;//���ν�����
		Long accountId=null;//�˺�Id
		String accountNum = null; // �˺�
		String accountName = null; // �˺�����
		String bank = null; // ������
		
		Long createId=Long.valueOf(user.get("id")+"");
		
		String penyJenis = map.get("penyJenis").toString();// �տʽ
		String selectIds = this.tosqlids((String) map.get("selectIds"));// �����տ
		String documentsSmalltype = map.get("documentsSmalltype").toString();// ����С��
		settlementAmount=Double.valueOf(String.valueOf(map.get("settlementAmount")));
		StringBuffer sqlbf = new StringBuffer();
		sqlbf.append("select a.* from Fi_Cashiercollection a where a.id in (");
		sqlbf.append(selectIds);
		sqlbf.append(")");
		// �����տ�б�
		List<FiCashiercollection> list1 = this.fiCashiercollectionDao.getSession().createSQLQuery(sqlbf.toString()).addEntity(FiCashiercollection.class).list();
		FiCashiercollection fc = list1.get(0);// һ��ֻ��ѡ��һ���տ
		
		if(list1.size()<1) throw new ServiceException("��ѡ������տ!");
		if(list1.size()>1) throw new ServiceException("ֻ��ѡ��һ�������տ!");
		
		Double collectionAmount = DoubleUtil.sub(fc.getCollectionAmount(),fc.getEntrustAmount());// �տ���˽��-��ί��ȷ�Ͻ��
		if(list.size()<1) throw new ServiceException("��ѡ������տ!");
		if(list.size()>1) throw new ServiceException("ֻ��ѡ��һ���տ!");
		
		String sqlseq="select SEQ_FI_PAID.nextval SEQ from dual";
		Map seqid=(Map) this.fiPaidDao.createSQLQuery(sqlseq).list().get(0) ;
		seq=Long.valueOf(seqid.get("SEQ")+"");
		
		
		for (int i = 0; i < list.size(); i++) {
			FiPayment fiPayment = list.get(i);
			if (Double.doubleToLongBits(settlementAmount) > Double.doubleToLongBits(collectionAmount)) {// �����տ���>�����տ���
				settlementAmount = collectionAmount;
			}
			
			//�տ����жϣ�δί��ȷ�Ͻ��+���θ�����
			collectionAmount = DoubleUtil.add(fc.getEntrustAmount(), settlementAmount);
			if (collectionAmount >fc.getCollectionAmount()) {
				break;
			}

			fiPayment.setSettlementAmount(settlementAmount
					+ fiPayment.getSettlementAmount());// ʵ���ܽ��
			if (Double.doubleToLongBits(fiPayment.getSettlementAmount()) >= Double
					.doubleToLongBits(fiPayment.getAmount())) {
				fiPayment.setPaymentStatus(Long.valueOf(2));// ���տ�
			}
			this.fiPaymentDao.save(fiPayment);// ����Ӧ��Ӧ����

			// ����ʵ��ʵ����
			FiPaid fiPaid = new FiPaid();
			fiPaid.setPaidId(seq);
			fiPaid.setFiPaymentId(fiPayment.getId());
			fiPaid.setPenyJenis(penyJenis);// ���㷽ʽ
			fiPaid.setSettlementAmount(settlementAmount);// ����ʵ�ս��
			fiPaid.setFiCashiercollectionId(fc.getId());// �����տID
			fiPaid.setFiCapitaaccountId(accountId);
			fiPaid.setAccountNum(accountNum);
			fiPaid.setAccountName(accountName);
			fiPaid.setBank(bank);
			fiPaid.setVerificationStatus(0L);
			fiPaid.setCreateId(createId);
			fiPaid.setIncomeDepartId(fiPayment.getIncomeDepartId());//���벿��
			this.fiPaidDao.save(fiPaid);
			
			// Ƿ����ϸ����
			if ("���˵�".equals(documentsSmalltype)) {
				this.fiReceivablestatementService.verificationReceistatment(settlementAmount, fiPayment.getDocumentsNo());
			}

			fc.setEntrustAmount(collectionAmount);// ί��ȷ���ܽ��
			//������־
			oprHistoryService.saveFiLog(fiPayment.getDocumentsNo(), "Ӧ�յ�ID��"+fiPayment.getId()+"ͨ��ί��ȷ������,��������Ϊ��"+fiPayment.getCostType()+",������"+settlementAmount , 34L);
		}
		// �����տʣ�ڽ��
		fc.setEntrustUser(String.valueOf(user.get("name")));
		fc.setEntrustTime(new Date());
		this.fiCashiercollectionDao.save(fc);
		
		/*
		//�����տ�ʣ�ڽ�����Ԥ���
		if(collectionAmount>0){
			List<FiAdvanceset> fatlist=this.fiAdvancesetDao.find("from FiAdvanceset  where customerId=? and isdelete=1", list.get(0).getCustomerId());
				if(fatlist.size()==0){
					throw new ServiceException("�˿���δ����Ԥ����˺ţ����ܽ��໹����"+collectionAmount+"����Ԥ����˺�!");
				}
				if(fatlist.size()>1){
					throw new ServiceException("�˿�����"+fatlist.size()+"��Ԥ����˺��˺�!");
				}
				FiAdvanceset fiAdvanceset=fatlist.get(0);
				fiAdvanceset.setOpeningBalance(DoubleUtil.add(fiAdvanceset.getOpeningBalance(), collectionAmount));
				this.fiAdvancesetDao.save(fiAdvanceset);
				
				FiAdvance fiAdvance=new FiAdvance();
				fiAdvance.setCustomerId(list.get(0).getCustomerId());
				fiAdvance.setCustomerName(list.get(0).getCustomerName());
				fiAdvance.setSettlementType(1L);
				fiAdvance.setSettlementAmount(collectionAmount);
				fiAdvance.setSettlementBalance(DoubleUtil.add(fiAdvanceset.getOpeningBalance(), collectionAmount));
				fiAdvance.setSourceData("Ӧ�ն໹��");
				fiAdvance.setSourceNo(fc.getId());
				fiAdvanceDao.save(fiAdvance);
		}
		*/


	}

	// Ԥ������
	private void advanceInfo(List<FiPayment> list, Map map) throws Exception {
		Long seq=0L;
		Double settlementAmount=0.0;//���ν�����
		String penyJenis = map.get("penyJenis").toString();// �տʽ
		String selectIds = this.tosqlids((String) map.get("selectIds"));// �����տ
		String documentsSmalltype = map.get("documentsSmalltype").toString();// ����С��
		settlementAmount=Double.valueOf(String.valueOf(map.get("settlementAmount")));
		StringBuffer sqlbf = new StringBuffer();
		sqlbf.append("select a.* from Fi_Advanceset a where a.id in (");
		sqlbf.append(selectIds);
		sqlbf.append(")");
		
		User user=WebRalasafe.getCurrentUser(Struts2Utils.getRequest()); 
		Long createId=Long.valueOf(user.get("id")+"");
		// �����б�
		List<FiAdvanceset> list1 = this.fiAdvancesetDao.getSession().createSQLQuery(sqlbf.toString()).addEntity(FiAdvanceset.class).list();
		FiAdvanceset fa = list1.get(0);
		Double openingBalance = fa.getOpeningBalance();// Ԥ���˺����
		if(openingBalance<=0.0) throw new ServiceException("Ԥ����˺����㣡");
		
		String sqlseq="select SEQ_FI_PAID.nextval SEQ from dual";
		Map seqid=(Map) this.fiPaidDao.createSQLQuery(sqlseq).list().get(0) ;
		seq=Long.valueOf(seqid.get("SEQ")+"");
		
		for (int i = 0; i < list.size(); i++) {
			FiPayment fiPayment = list.get(i);
			//Double thisamount = DoubleUtil.sub(fiPayment.getAmount(), fiPayment.getSettlementAmount());// ������Ҫ�տ���

			if (openingBalance <= 0) {
				break;
			}
			if (Double.doubleToLongBits(settlementAmount) > Double.doubleToLongBits(openingBalance)) {// �����տ���>�����տ���
				settlementAmount = openingBalance;
			}
			
			
			openingBalance = DoubleUtil.sub(openingBalance , settlementAmount);// Ԥ���˺����

			fiPayment.setSettlementAmount(settlementAmount
					+ fiPayment.getSettlementAmount());// ʵ���ܽ��
			if (Double.doubleToLongBits(fiPayment.getSettlementAmount()) >= Double
					.doubleToLongBits(fiPayment.getAmount())) {
				fiPayment.setPaymentStatus(Long.valueOf(2));// ���տ�
			}
			this.fiPaymentDao.save(fiPayment);// ����Ӧ��Ӧ����


			// Ԥ������㵥
			FiAdvance fiAdvance = new FiAdvance();
			fiAdvance.setSettlementType(Long.valueOf(2));// ����
			fiAdvance.setCustomerId(fa.getCustomerId());// Ԥ����������ã�����ID
			fiAdvance.setCustomerName(fa.getCustomerName());
			fiAdvance.setSettlementAmount(settlementAmount);// ���ν�����
			fiAdvance.setSettlementBalance(openingBalance);// ���
			fiAdvance.setSourceData("Ӧ�տ");
			fiAdvance.setSourceNo(fiPayment.getId());
			fiAdvance.setFiAdvanceId(fa.getId());
			this.fiAdvanceDao.save(fiAdvance);
			
			// ����ʵ��ʵ����
			FiPaid fiPaid = new FiPaid();
			fiPaid.setPaidId(seq);
			fiPaid.setFiPaymentId(fiPayment.getId());
			fiPaid.setPenyJenis(penyJenis);// ���㷽ʽ
			fiPaid.setSettlementAmount(settlementAmount);// ����ʵ�ս��
			fiPaid.setVerificationStatus(0L);
			fiPaid.setFiAdvanceId(fiAdvance.getId());
			fiPaid.setCreateId(createId);
			fiPaid.setIncomeDepartId(fiPayment.getIncomeDepartId());//���벿��
			this.fiPaidDao.save(fiPaid);

			// �տ��������ģ������****
			this.verificationReceiving(fiPayment, settlementAmount);
			//������־
			oprHistoryService.saveFiLog(fiPayment.getDocumentsNo(), "Ӧ�յ�ID��"+fiPayment.getId()+"ͨ��Ԥ��������,��������Ϊ��"+fiPayment.getCostType()+",������"+settlementAmount , 34L);
			
		}
		// Ԥ�����������
		fa.setOpeningBalance(openingBalance);
		this.fiAdvancesetDao.save(fa);
	}

	// �ո��Գ�
	private void paymentInfo(List<FiPayment> list, Map map) throws Exception {
		User user=WebRalasafe.getCurrentUser(Struts2Utils.getRequest()); 
		Long createId=Long.valueOf(user.get("id")+"");
		// ʵ������
		Long seq=0L;
		String penyJenis = map.get("penyJenis").toString();// �տʽ
		String selectIds = this.tosqlids((String) map.get("selectIds"));// ���
		String documentsSmalltype = map.get("documentsSmalltype").toString();// ����С��

		StringBuffer sqlbf = new StringBuffer();
		sqlbf.append("select a.* from fi_payment a where a.id in (");
		sqlbf.append(selectIds);
		sqlbf.append(")");
		if(list.size()<1) throw new ServiceException("����ʧ�ܣ���ѡ��Ӧ�յ���!");
		if(list.size()>1) throw new ServiceException("����ʧ�ܣ�ֻ��ѡ��һ��Ӧ�յ��ŶԳ�!");
		FiPayment collection = list.get(0);// �տ��б�(ֻ����һ���տ)
		Double thesettlementAmount = DoubleUtil.sub(collection.getAmount(), DoubleUtil.add(collection.getSettlementAmount(),collection.getEliminationAmount()));// �����տ���
		
		// �����б�
		List<FiPayment> list1 = this.fiPaymentDao.getSession().createSQLQuery(sqlbf.toString()).addEntity(FiPayment.class).list();
		if(list1.size()<1) throw new ServiceException("����ʧ�ܣ���ѡ��Ӧ������!");
		if(list1.size()>1) throw new ServiceException("����ʧ�ܣ�ֻ��ѡ��һ��Ӧ�����ŶԳ�!");
		
		String sqlseq="select SEQ_FI_PAID.nextval SEQ from dual";
		Map seqid=(Map) this.fiPaidDao.createSQLQuery(sqlseq).list().get(0) ;
		seq=Long.valueOf(seqid.get("SEQ")+"");

		// �������
		FiPayment fiPayment = list1.get(0);// Ӧ���
		FiPaid fiPaidcot = new FiPaid(); // ʵ���
		Double thefiPayment = DoubleUtil.sub(fiPayment.getAmount(), DoubleUtil.add(fiPayment.getSettlementAmount(),fiPayment.getEliminationAmount()));// ���θ�����
		
		if(Double.doubleToLongBits(thesettlementAmount) < Double.doubleToLongBits(thefiPayment)){
			thefiPayment=thesettlementAmount;
		}
		
		fiPayment.setSettlementAmount(DoubleUtil.add(thefiPayment,fiPayment.getSettlementAmount()));// �Ѹ����
		
		if (Double.doubleToLongBits(fiPayment.getAmount()) == Double.doubleToLongBits(fiPayment.getSettlementAmount())) {
			fiPayment.setPaymentStatus(Long.valueOf(5));// �Ѹ���
		}
		this.fiPaymentDao.save(fiPayment);

		// ʵ�����
		//fiPaidcot.setId(seq);
		fiPaidcot.setPaidId(seq);
		fiPaidcot.setSettlementAmount(thefiPayment);// ʵ���ܽ��(Ӧ���ܶ�-�Ѹ��ܽ��)
		fiPaidcot.setPenyJenis(penyJenis);// ���㷽ʽ
		fiPaidcot.setFiPaymentId(fiPayment.getId());// Ӧ������
		fiPaidcot.setVerificationStatus(0L);
		fiPaidcot.setCreateId(createId);
		this.fiPaidDao.save(fiPaidcot);

		// �տ����
		if("�����˿�".equals(fiPayment.getCostType())||"�쳣������".equals(fiPayment.getCostType())){
			collection.setEliminationAmount(thefiPayment);
		}else{
			collection.setSettlementAmount(collection.getSettlementAmount()+thefiPayment);
		}
		if(collection.getEliminationAmount()==null||"".equals(collection.getEliminationAmount())){
			collection.setEliminationAmount(0.0);
		}
		if (Double.doubleToLongBits(DoubleUtil.add(collection.getSettlementAmount(),collection.getEliminationAmount())) >= Double
				.doubleToLongBits(collection.getAmount())) {
			collection.setPaymentStatus(2L);// ���տ�
		}
		this.fiPaymentDao.save(collection);
		FiPaid fiPaid = new FiPaid();// ʵ�յ�����
		fiPaid.setPaidId(seq);
		fiPaid.setSettlementAmount(thefiPayment);
		fiPaid.setFiPaymentId(collection.getId());// Ӧ�յ���
		fiPaid.setPenyJenis(penyJenis);// ���㷽ʽ
		fiPaid.setVerificationStatus(0L);
		fiPaid.setFiPaidWriteId(fiPaidcot.getId());//������ӦID
		fiPaid.setCreateId(createId);
		fiPaid.setIncomeDepartId(fiPayment.getIncomeDepartId());//���벿��
		this.fiPaidDao.save(fiPaid);// ����ʵ��ʵ����
		
		
		// �տ��������ģ������****
		if(!"�����˿�".equals(fiPayment.getCostType())){
			this.verificationReceiving(collection, thefiPayment);
		}
		//�����������ģ������***
		this.verificationPayment(fiPayment, thefiPayment);
		
		//�տ������־
		oprHistoryService.saveFiLog(fiPayment.getDocumentsNo(), "Ӧ�յ�ID��"+collection.getId()+"ͨ���ո���������,��������Ϊ��"+collection.getCostType()+",������"+thefiPayment , 34L);
		//���������־
		oprHistoryService.saveFiLog(fiPayment.getDocumentsNo(), "Ӧ����ID��"+fiPayment.getId()+"ͨ���ո���������,��������Ϊ��"+fiPayment.getCostType()+",�����"+thefiPayment , 86L);
	}

	// ֧Ʊ����
	private void checkReceiving(List<FiPayment> list, Map map) throws Exception{
		User user=WebRalasafe.getCurrentUser(Struts2Utils.getRequest()); 
		Long createId=Long.valueOf(user.get("id")+"");
		
		Double thesettlementAmount = Double.valueOf(map.get("settlementAmount").toString());// �����տ���
		String documentsSmalltype = map.get("documentsSmalltype").toString();// ����С��
		String penyJenis = map.get("penyJenis").toString();// �տʽ
		String checkUser=map.get("documentsSmalltype").toString();
		Long customerId=Long.valueOf(map.get("checkcustomerId").toString());

		if(list.size()==0) throw new ServiceException("�տ�����ڣ�������ѡ��");
		if(list.size()>1) throw new ServiceException("�տ���ڶ�����¼��������ѡ��");
		FiPayment fiPayment=list.get(0);
		
		List<Customer> listCustomer=this.customerService.find("from Customer c where c.id=?",customerId);
		if(listCustomer.size()==0) throw new ServiceException("���̲����ڣ�������ѡ��");
		if(listCustomer.size()>1) throw new ServiceException("���̴��ڶ�����¼��������ѡ��");
		
		Customer customer=listCustomer.get(0);
		
		if(customer==null) throw new ServiceException("���̲�����");
		
		FiCheck fiCheck = new FiCheck();
		fiCheck.setCustomerId(customerId);
		fiCheck.setCustomerName(customer.getCusName());
		fiCheck.setCheckNo(String.valueOf(map.get("checkNo")));
		fiCheck.setAmount(Double.valueOf(map.get("checkamount").toString()));
		fiCheck.setCheckUser(checkUser);
		fiCheck.setRemark(map.get("checkRemark").toString());
		fiCheck.setFiPaymentId(list.get(0).getId());
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			fiCheck.setCheckDate(sdf.parse(map.get("checkDate").toString()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		this.fiCheckDao.save(fiCheck);
		//������־
		oprHistoryService.saveFiLog(fiPayment.getDocumentsNo(), "Ӧ�յ�ID��"+fiPayment.getId()+"ͨ��֧Ʊ����,��������Ϊ��"+fiPayment.getCostType()+",������"+thesettlementAmount , 34L);

	}

	//֧Ʊ�������
	@XbwlInt(isCheck=false)
	public void checkAudit(FiCheck fiCheck) throws Exception{
		User user=WebRalasafe.getCurrentUser(Struts2Utils.getRequest()); 
		Long createId=Long.valueOf(user.get("id")+"");
		Long seq=0L;
		if(fiCheck==null) throw new ServiceException("֧Ʊ������");
		FiPayment fiPayment=this.fiPaymentDao.get(fiCheck.getFiPaymentId());
		if(fiPayment==null) throw new ServiceException("֧Ʊ��Ӧ��Ӧ�յ���");
		
		// ��ȡ�˺���Ϣ
		FiCapitaaccountset fiCapitaaccountset = this.fiCapitaaccountsetDao.get(fiCheck.getTodepositFiCapitaaccountset());
		if(fiPayment==null) throw new ServiceException("�ʹ��˺Ų�����");
		
		String documentsSmalltype=fiPayment.getDocumentsSmalltype();
		
		Double fiCheckAmount=fiCheck.getAmount();//֧Ʊ���
		//Ӧ���ܽ��
		Double settlementAmount = DoubleUtil.sub(fiPayment.getAmount(), fiPayment.getSettlementAmount()==null?0.0:fiPayment.getSettlementAmount());// ������Ҫ������
		if (Double.doubleToLongBits(settlementAmount) > Double.doubleToLongBits(fiCheckAmount)) {// �����տ���>�����տ���
			settlementAmount = fiCheckAmount;
		}
		
		fiPayment.setSettlementAmount(settlementAmount
				+ fiPayment.getSettlementAmount());// ʵ���ܽ��
		if (Double.doubleToLongBits(fiPayment.getSettlementAmount()) >= Double
				.doubleToLongBits(fiPayment.getAmount())) {
			fiPayment.setPaymentStatus(Long.valueOf(2));// ���տ�
		//} else {
			//fiPayment.setPaymentStatus(Long.valueOf(3));// �����տ�
		}
		this.fiPaymentDao.save(fiPayment);// ����Ӧ��Ӧ����
		

		String sqlseq="select SEQ_FI_PAID.nextval SEQ from dual";
		Map seqid=(Map) this.fiPaidDao.createSQLQuery(sqlseq).list().get(0) ;
		seq=Long.valueOf(seqid.get("SEQ")+"");
		
		// ����ʵ��ʵ����
		FiPaid fiPaid = new FiPaid();
		fiPaid.setPaidId(seq);
		fiPaid.setFiPaymentId(fiPayment.getId());
		fiPaid.setPenyJenis("֧Ʊ");// ���㷽ʽ
		fiPaid.setSettlementAmount(settlementAmount);// ����ʵ�ս��
		fiPaid.setFiCheckId(fiCheck.getId());
		fiPaid.setAccountNum(fiCapitaaccountset.getAccountNum());
		fiPaid.setAccountName(fiCapitaaccountset.getAccountName());
		fiPaid.setBank(fiCapitaaccountset.getBank());
		fiPaid.setVerificationStatus(0L);
		fiPaid.setCreateId(createId);
		fiPaid.setIncomeDepartId(fiPayment.getIncomeDepartId());//���벿��
		this.fiPaidDao.save(fiPaid);
		
		// ��������ģ������****
		this.verificationReceiving(fiPayment, settlementAmount);
		
	}
	
	// �˺��տ�(�ֽ����С�POS)
	private void accountReceiving(List<FiPayment> list, Map map) throws Exception{
		User user=WebRalasafe.getCurrentUser(Struts2Utils.getRequest()); 
		Long createId=Long.valueOf(user.get("id")+"");
		Long seq=0L;
		String accountNum = null; // �˺�
		String accountName = null; // �˺�����
		String bank = null; // ������
		Double openingBalance = 0.0; // ���
		double thisamount =0.0;//�����տ���
		double settlementAmount=0.0;
		
		String sqlseq="select SEQ_FI_PAID.nextval SEQ from dual";
		Map seqid=(Map) this.fiPaidDao.createSQLQuery(sqlseq).list().get(0) ;
		seq=Long.valueOf(seqid.get("SEQ")+"");

		Long accountNumId = Long.valueOf(map.get("selectIds").toString());// �տ��˺�id
		Double thesettlementAmount = Double.valueOf(map.get("settlementAmount")
				.toString());// �����տ���
		String penyJenis = map.get("penyJenis").toString();// �տʽ

		// ��ȡ�˺���Ϣ
		FiCapitaaccountset fiCapitaaccountset = this.fiCapitaaccountsetDao
				.get(accountNumId);
		accountNum = fiCapitaaccountset.getAccountNum();// �ո����˺�
		accountName = fiCapitaaccountset.getAccountName();// �տ��˺�����
		bank = fiCapitaaccountset.getBank();// �տ����
		openingBalance = fiCapitaaccountset.getOpeningBalance();// �˺����
		if(openingBalance==null||"".equals(openingBalance)){
			openingBalance=0.0;
		};

		for (int i = 0; i < list.size(); i++) {
			FiPayment fiPayment = list.get(i);
			
			thisamount = DoubleUtil.sub(DoubleUtil.sub(fiPayment.getAmount(), fiPayment.getSettlementAmount()),fiPayment.getEliminationAmount());// ������Ҫ�տ���
			if(thisamount>thesettlementAmount){
				thisamount=thesettlementAmount;
			}
			fiPayment.setSettlementAmount(DoubleUtil.add(thisamount,fiPayment.getSettlementAmount()));// ʵ���ܽ��
			settlementAmount=DoubleUtil.add(fiPayment.getSettlementAmount(), fiPayment.getEliminationAmount());//�ѽ�����=�ѽ�����+�����˿���
			
			if (Double.doubleToLongBits(settlementAmount) >= Double.doubleToLongBits(fiPayment.getAmount())) {
				fiPayment.setPaymentStatus(Long.valueOf(2));// ���տ�
			//} else {
				//fiPayment.setPaymentStatus(Long.valueOf(3));// �����տ�
			}
			this.fiPaymentDao.save(fiPayment);// ����Ӧ��Ӧ����
			openingBalance=DoubleUtil.add(openingBalance , thisamount);
			
			
			//д���ֽ���ˮ
			FiCapitaaccount fiCapitaaccount = new FiCapitaaccount();
			fiCapitaaccount.setFiCapitaaccountsetId(accountNumId);
			fiCapitaaccount.setLoan(thisamount);// ��
			fiCapitaaccount.setSourceData("�տ");
			fiCapitaaccount.setSourceNo(fiPayment.getId());
			fiCapitaaccount.setBalance(openingBalance);// ���
			fiCapitaaccount.setStatus(1L);
			this.fiCapitaaccountDao.save(fiCapitaaccount);// �����˺���ˮ���

			// ����ʵ��ʵ����
			FiPaid fiPaid = new FiPaid();
			fiPaid.setPaidId(seq);
			fiPaid.setAccountNum(accountNum);
			fiPaid.setAccountName(accountName);
			fiPaid.setBank(bank);
			fiPaid.setFiPaymentId(fiPayment.getId());
			fiPaid.setPenyJenis(penyJenis);// ���㷽ʽ
			fiPaid.setFiCapitaaccountId(fiCapitaaccount.getId());
			fiPaid.setSettlementAmount(thisamount);// ����ʵ�ս��
			fiPaid.setVerificationStatus(0L);
			fiPaid.setCreateId(createId);
			fiPaid.setIncomeDepartId(fiPayment.getIncomeDepartId());//���벿��
			this.fiPaidDao.save(fiPaid);

			
			// ��������ģ������****
			this.verificationReceiving(fiPayment, thisamount);
			
			thesettlementAmount = DoubleUtil.sub(thesettlementAmount , thisamount);// ����ʣ����
			
			//������־
			oprHistoryService.saveFiLog(fiPayment.getDocumentsNo(), "Ӧ�յ�ID��"+fiPayment.getId()+"ͨ���ֽ�����,�������ͣ�"+fiPayment.getCostType()+",��"+thisamount , 34L);
			
			if (thesettlementAmount <= 0) {
				continue;
			}
		}

		// �����տ��ʺ����
		fiCapitaaccountset.setOpeningBalance(openingBalance);// ���˺����+����ʵ���ܽ��
		this.fiCapitaaccountsetDao.save(fiCapitaaccountset);// �����˺����
	}

	// �粿��ί���ո���
	public void saveEntrust(Map<String, Object> map, FiPayment fiPayment)
			throws Exception {
		String sql = this.PaymentSql(map).toString();
		List<FiPayment> list = this.fiPaymentDao.getSession().createSQLQuery(
				sql).addEntity(FiPayment.class).list();
		for (Iterator<FiPayment> it = list.iterator(); it.hasNext();) {
			FiPayment fp = it.next();
			fp.setEntrustDeptid(fiPayment.getEntrustDeptid());
			fp.setEntrustDept(fiPayment.getEntrustDept());
			fp.setEntrustRemark(fiPayment.getEntrustRemark());
			fp.setEntrustTime(new Date());
			fp.setEntrustUser(fiPayment.getEntrustUser());
			this.fiPaymentDao.save(fp);
			//������־
			oprHistoryService.saveFiLog(fp.getDocumentsNo(), "Ӧ�յ�ID��"+fp.getId()+",ί�б�ע��"+fiPayment.getEntrustRemark() ,67L);
		}
	}


	// ƴ�Ӹ��SQL
	private String PaymentSql(Map map) throws Exception {
		StringBuffer sqlbuffer = new StringBuffer();
		String ids = this.tosqlids((String) map.get("ids"));
		sqlbuffer.append("select a.* from fi_payment a where a.id in (");
		sqlbuffer.append(ids);
		sqlbuffer.append(") order by a.amount");
		return sqlbuffer.toString();
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
	
	//����
	@ModuleName(value="���˱���",logType=LogType.fi)
	public void saveLosses(Map<String,Object> map) throws Exception{
		String sql = this.PaymentSql(map);
		Long customerId=Long.valueOf((String)map.get("customerId"));
		Long departId=Long.valueOf((String)map.get("departId"));
		String remark=(String)map.get("remark");
		
		List<FiArrearset> listfiList=fiArrearsetService.find("from FiArrearset f where f.customerId=? and f.departId=?",customerId,departId);
		if(listfiList.size()==0) throw new ServiceException("���˿��̲����ڣ�������ѡ��");
		if(listfiList.size()>1) throw new ServiceException("�����ڿ���Ƿ��������ͬһ���̴��ڶ�����¼��������ѡ��");
		FiArrearset fiArrearset=listfiList.get(0);
		if(!"��".equals(fiArrearset.getIspaytoarrears())){
			throw new ServiceException("��ǰ���̲�������תǷ�������ѡ��");
		}
		Customer customer=this.customerService.get(customerId);
		if(customer==null) throw new ServiceException("���˿��̲����ڣ�������ѡ��");
		List<FiPayment> list = this.fiPaymentDao.getSession().createSQLQuery(
				sql).addEntity(FiPayment.class).list();
		if(list.size()==0) throw new ServiceException("�տ�����ڣ�������ѡ��");
		if(list.size()>1) throw new ServiceException("һ��ֻ��ѡ��һ���տ���й��ˣ�������ѡ��");
		FiPayment fiPayment=list.get(0);
		
		FiReceivabledetail fiReceivabledetail=new FiReceivabledetail();
		fiReceivabledetail.setPaymentType(1L);//�տ
		fiReceivabledetail.setDno(fiPayment.getDocumentsNo());
		fiReceivabledetail.setSourceData("Ӧ�չ���");
		fiReceivabledetail.setCostType(fiPayment.getCostType());
		fiReceivabledetail.setSourceNo(fiPayment.getId());
		fiReceivabledetail.setAmount(DoubleUtil.sub(fiPayment.getAmount(), fiPayment.getSettlementAmount()));
		fiReceivabledetail.setCustomerId(customer.getId());
		fiReceivabledetail.setCustomerName(customer.getCusName());
		fiReceivabledetail.setRemark(remark);
		
		fiReceivabledetail.setDepartId(departId);
		
		fiPayment.setPaymentStatus(9L);
		this.fiPaymentDao.save(fiPayment);
		this.fiReceivabledetailService.save(fiReceivabledetail);
		
		//������־
		oprHistoryService.saveFiLog(fiReceivabledetail.getDno(), "Ӧ�յ�ID��"+fiPayment.getId()+",���˿��̣�"+customer.getCusName() ,65L);
		
		//����Ӧ�����ջ���
		if("���ջ���".equals(fiPayment.getCostType())){
			this.fiReceivabledetailService.verificationReceistatment(fiPayment);
		}
		
		//���´���״̬������״̬
		if ("���͵�".equals(fiPayment.getDocumentsSmalltype())) {
			this.oprStatusService.verificationCashStatusByFiPayment(fiPayment.getDocumentsNo());
		}
	}
	//�������
	@XbwlInt(isCheck=false)
	private void verificationPayment(FiPayment fiPayment,Double thisamount) throws Exception{
		// Ƿ����ϸ����
		if ("���˵�".equals(fiPayment.getDocumentsSmalltype())) {
			this.fiReceivablestatementService.verificationReceistatment(thisamount, fiPayment.getDocumentsNo());
		}
		
		//����ɱ�����
		if ("����ɱ�".equals(fiPayment.getSourceData())) {
			this.fiDeliverycostService.payConfirmationBybatchNo(fiPayment.getSourceNo());
		}
		
		//��ת�ɱ�����
		if ("��ת�ɱ�".equals(fiPayment.getSourceData())) {
			this.fiTransitService.payConfirmationBybatchNo(fiPayment.getSourceNo());
		}
		
		//�ⷢ�ɱ�����
		if ("�ⷢ�ɱ�".equals(fiPayment.getSourceData())) {
			this.fiOutCostService.payConfirmationBybatchNo(fiPayment.getSourceNo());
		}
		
		//�����ɱ�����
		if ("�����ɱ�".equals(fiPayment.getSourceData())) {
			this.oprSignRouteService.payConfirmationById(fiPayment.getSourceNo());
		}
		
		//�����˿����
		if("�����˿�".equals(fiPayment.getSourceData())){
			this.fiProblemreceivableService.verfiProblemreceivable(thisamount, fiPayment.getSourceNo());
		}
		
		//�쳣���������
		if("�쳣������".equals(fiPayment.getSourceData())){
			this.fiPaymentabnormalService.verfiPaymentabnormal(fiPayment);
		}
		
		// ��дԤ������״̬(���ȫ������)
		if ("Ԥ��".equals(fiPayment.getDocumentsSmalltype())) {
			this.fiAdvancebpService.verfiFiAdvancebp(fiPayment.getSettlementAmount(), fiPayment.getDocumentsNo());
		}
	}

	//�տ����
	@XbwlInt(isCheck=false)
	private void verificationReceiving(FiPayment fiPayment,Double thisamount) throws Exception{
		// Ƿ����ϸ����
		if ("���˵�".equals(fiPayment.getDocumentsSmalltype())) {
			this.fiReceivablestatementService.verificationReceistatment(thisamount, fiPayment.getDocumentsNo());
		}
		
		// ��дԤ������״̬(���ȫ������)
		if ("Ԥ��".equals(fiPayment.getDocumentsSmalltype())) {
			this.fiAdvancebpService.verfiFiAdvancebp(fiPayment.getSettlementAmount(), fiPayment.getDocumentsNo());
		}
		
		
		//����Ӧ�����ջ���
		if("���ջ���".equals(fiPayment.getCostType())){
			this.fiReceivabledetailService.verificationReceistatment(fiPayment);
		}
		
		//���´���״̬������״̬
		if ("���͵�".equals(fiPayment.getDocumentsSmalltype())) {
			this.oprStatusService.verificationCashStatusByFiPayment(fiPayment.getDocumentsNo());
		}
	}
	
	public void audit(Long id,User user) throws Exception{
		FiPayment fiPayment=this.fiPaymentDao.get(id);
		if(fiPayment==null){
			throw new ServiceException("���ݲ�����!");
		}
		if(fiPayment.getStatus()==0L){
			throw new ServiceException("����������!");
		}
		if(fiPayment.getPaymentStatus()==2L){
			throw new ServiceException("�������տ�ȷ��!");
		}
		if(fiPayment.getPaymentStatus()==5L){
			throw new ServiceException("�����Ѹ���ȷ��!");
		}
		fiPayment.setReviewStatus(1L);//�����
		fiPayment.setReviewDate(new Date());
		fiPayment.setReviewUser(user.get("name")+"");
		this.fiPaymentDao.save(fiPayment);
	}
	
	public void revocationAudit(Long id,User user) throws Exception{
		FiPayment fiPayment=this.fiPaymentDao.get(id);
		if(fiPayment==null){
			throw new ServiceException("���ݲ�����!");
		}
		if(fiPayment.getStatus()==0L){
			throw new ServiceException("����������!");
		}
		if(fiPayment.getPaymentStatus()==2L){
			throw new ServiceException("�������տ�ȷ��!");
		}
		if(fiPayment.getPaymentStatus()==5L){
			throw new ServiceException("�����Ѹ���ȷ��!");
		}
		if(fiPayment.getReviewStatus()==0L){
			throw new ServiceException("���ݻ�δ���!");
		}
		if(fiPayment.getReviewStatus()==1L){
			throw new ServiceException("��������ˣ��������ظ����");
		}
		fiPayment.setReviewStatus(1L);//�����
		fiPayment.setReviewDate(new Date());
		fiPayment.setReviewUser(user.get("name")+"");
		this.fiPaymentDao.save(fiPayment);
	}
}
