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
 * @author 阳书哲 TODO 功能描述：应收应付管理
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

	// 资金账号Dao
	@Resource(name = "fiCapitaaccountsetHibernateDaoImpl")
	private IFiCapitaaccountsetDao fiCapitaaccountsetDao;

	// 资金账号流水Dao
	@Resource(name = "fiCapitaaccountHibernateDaoImpl")
	private IFiCapitaaccountDao fiCapitaaccountDao;

	// 实收实付Dao
	@Resource(name = "fiPaidHibernateDaoImpl")
	private IFiPaidDao fiPaidDao;

	// 支票Dao
	@Resource(name = "fiCheckHibernateImpl")
	private IFiCheckDao fiCheckDao;

	// 预付款设置Dao
	@Resource(name = "fiAdvancesetHibernateDaoImpl")
	private IFiAdvancesetDao fiAdvancesetDao;

	// 预付款结算Dao
	@Resource(name = "fiAdvanceHibernateDaoImpl")
	private IFiAdvanceDao fiAdvanceDao;

	// 欠款明细Service
	@Resource(name = "fiReceivabledetailServiceImpl")
	private IFiReceivabledetailService fiReceivabledetailService;

	// 出纳收款单Dao
	@Resource(name = "fiCashiercollectionHibernateDaoImpl")
	private IFiCashiercollectionDao fiCashiercollectionDao;
	
	//客商
	@Resource(name = "customerServiceImpl")
	private ICustomerService customerService;
	
	//客商欠款设置
	@Resource(name="fiArrearsetServiceImpl")
	private IFiArrearsetService fiArrearsetService;
	
	//对账单
	@Resource(name = "fiReceivablestatementServiceImpl")
	private IFiReceivablestatementService fiReceivablestatementService;
	
	//问题账款
	@Resource(name="fiProblemreceivableServiceImpl")
	private IFiProblemreceivableService fiProblemreceivableService;
	
	//提货成本
	@Resource(name = "fiDeliverycostServiceImpl")
	private IFiDeliverycostService fiDeliverycostService;
	
	//中转成本
	@Resource(name = "fiTransitcostServiceImpl")
	private IFiTransitcostService fiTransitService;
	
	//外发成本
	@Resource(name="fiOutCostServiceImpl")
	private IFiOutCostService fiOutCostService;
	
	//车辆成本
	@Resource(name="oprSignRouteServiceImpl")
	private IOprSignRouteService oprSignRouteService;
	
	//传真状态表
	@Resource(name="oprStatusServiceImpl")
	private IOprStatusService oprStatusService;
	
	//异常到付款
	@Resource(name = "fiPaymentabnormalServiceImpl")
	private IFiPaymentabnormalService fiPaymentabnormalService;
	
	//日志
	@Resource(name = "oprHistoryServiceImpl")
	private IOprHistoryService oprHistoryService;
	
	@Resource(name="fiAdvancebpServiceImpl")
	private IFiAdvancebpService fiAdvancebpService;
	
	@Override
	public IBaseDAO<FiPayment, Long> getBaseDao() {
		return fiPaymentDao;
	}

	@ModuleName(value="收付款查询未结算金额",logType=LogType.buss)
	public Map searchReceiving(Map map) throws Exception {
		StringBuffer sqlbuffer = new StringBuffer();
		String documentsSmalltype = (String) map.get("documentsSmalltype");// 单据小类
		String ids = this.tosqlids((String) map.get("ids"));
		Double amount=0.0;//结算总金额
		Double settlementAmount=0.0;//已结算
		Double eliminationAmount=0.0;//已冲销
		Double thesettlementAmount=0.0;//本次结算总余额
		if (documentsSmalltype.equals("配送单")) {
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
		
		//本次结算金额
		//REVIEW 最外1层的nvl没有必要
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

	@ModuleName(value="收款确认",logType=LogType.fi)
	public void saveReceiving(Map<String,Object> map,User user) throws Exception{
		String penyJenis = map.get("penyJenis").toString();// 收款方式

		String sql = this.PaymentSql(map);
		List<FiPayment> list = this.fiPaymentDao.getSession().createSQLQuery(
				sql).addEntity(FiPayment.class).list();

		if (penyJenis.equals("现金") || penyJenis.equals("银行")
				|| penyJenis.equals("POS")) {
			accountReceiving(list, map);
		} else if (penyJenis.equals("支票")) {
			checkReceiving(list, map);
		} else if (penyJenis.equals("收付对冲")) {
			paymentInfo(list, map);
		} else if (penyJenis.equals("预付冲销")) {
			advanceInfo(list, map);
		} else if (penyJenis.equals("委托确收")) {
			entrustInfo(list, map,user);
		}
	}

	// 付款：账号付款(现金、银行、POS)
	@ModuleName(value="付款确认",logType=LogType.fi)
	public void savePayment(Map<String, Object> map) throws Exception {
		Long seq=0L;
		String accountNum = null; // 账号
		String accountName = null; // 账号名称
		String bank = null; // 开户行
		Double openingBalance = 0.0; // 余额

		String sql = this.PaymentSql(map);
		List<FiPayment> list = this.fiPaymentDao.getSession().createSQLQuery(
				sql).addEntity(FiPayment.class).list();

		Long accountNumId = Long.valueOf(map.get("selectIds").toString());// 收款账号id
		Double thesettlementAmount = Double.valueOf(map.get("settlementAmount")
				.toString());// 本次付款金额
		String penyJenis = map.get("penyJenis").toString();// 收款方式

		// 获取账号信息
		FiCapitaaccountset fiCapitaaccountset = this.fiCapitaaccountsetDao
				.get(accountNumId);
		accountNum = fiCapitaaccountset.getAccountNum();// 收付款账号
		accountName = fiCapitaaccountset.getAccountName();// 收款账号名称
		bank = fiCapitaaccountset.getBank();// 收款开户行
		openingBalance = fiCapitaaccountset.getOpeningBalance();// 账号余额

		String sqlseq="select SEQ_FI_PAID.nextval SEQ from dual";
		Map seqid=(Map) this.fiPaidDao.createSQLQuery(sqlseq).list().get(0) ;
		seq=Long.valueOf(seqid.get("SEQ")+"");
		
		User user=WebRalasafe.getCurrentUser(Struts2Utils.getRequest()); 
		Long createId=Long.valueOf(user.get("id")+"");
		
		for (int i = 0; i < list.size(); i++) {
			FiPayment fiPayment = list.get(i);
			
			if(fiPayment.getReviewStatus()==0L){
				throw new ServiceException("单据还未审核，不允许支付！");
			}
			
			Double thisamount = DoubleUtil.sub(fiPayment.getAmount(), fiPayment.getSettlementAmount()==null?0.0:fiPayment.getSettlementAmount());// 本次需要付款金额
			
			if (Double.doubleToLongBits(thisamount) > Double
					.doubleToLongBits(thesettlementAmount)) {// 本次收款金额>收款剩于总金额
				thisamount = thesettlementAmount;
			}

			fiPayment.setSettlementAmount(thisamount
					+ fiPayment.getSettlementAmount());// 实付总金额
			if (Double.doubleToLongBits(fiPayment.getSettlementAmount()) >= Double
					.doubleToLongBits(fiPayment.getAmount())) {
				fiPayment.setPaymentStatus(Long.valueOf(5));// 已付款
			}
			this.fiPaymentDao.save(fiPayment);// 更新应收应付表

			
			openingBalance=DoubleUtil.sub(openingBalance , thisamount);
			if(openingBalance<0.0){
				throw new ServiceException("付款账号余额不足，不能完成本次付款。");
			}
			// 新增账号流水余额
			FiCapitaaccount fiCapitaaccount = new FiCapitaaccount();
			fiCapitaaccount.setFiCapitaaccountsetId(accountNumId);
			fiCapitaaccount.setBorrow(thisamount);// 贷
			fiCapitaaccount.setSourceData("付款单");
			fiCapitaaccount.setSourceNo(fiPayment.getId());
			fiCapitaaccount.setBalance(openingBalance);// 余额

			this.fiCapitaaccountDao.save(fiCapitaaccount);

			// 新增实付实付表
			FiPaid fiPaid = new FiPaid();
			fiPaid.setPaidId(seq);
			fiPaid.setFiCapitaaccountId(fiCapitaaccount.getId());
			fiPaid.setAccountNum(accountNum);
			fiPaid.setAccountName(accountName);
			fiPaid.setBank(bank);
			fiPaid.setFiPaymentId(fiPayment.getId());
			fiPaid.setPenyJenis(penyJenis);// 结算方式
			fiPaid.setSettlementAmount(thisamount);// 本次实付金额
			fiPaid.setVerificationStatus(0L);
			fiPaid.setCreateId(createId);
			fiPaid.setIncomeDepartId(fiPayment.getIncomeDepartId());//收入部门
			this.fiPaidDao.save(fiPaid);

			thesettlementAmount = DoubleUtil.sub(thesettlementAmount , thisamount);// 支票剩余金额
			
			//根据付款单核销其它模块单据
			this.verificationPayment(fiPayment,thisamount);
			//付款单操作日志
			oprHistoryService.saveFiLog(fiPayment.getDocumentsNo(), "应付单ID："+fiPayment.getId()+"通过现金付款,费用类型为："+fiPayment.getCostType()+",付款金额："+thisamount , 86L);
			
			if (thesettlementAmount <= 0) {
				continue;
			}
		}
		// 更新付款帐号余额 
		fiCapitaaccountset.setOpeningBalance(openingBalance);
		this.fiCapitaaccountsetDao.save(fiCapitaaccountset);
	}

	
	// 委托确收
	private void entrustInfo(List<FiPayment> list, Map map,User user) throws Exception {
		Long seq=0L;
		Double settlementAmount=0.0;//本次结算金额
		Long accountId=null;//账号Id
		String accountNum = null; // 账号
		String accountName = null; // 账号名称
		String bank = null; // 开户行
		
		Long createId=Long.valueOf(user.get("id")+"");
		
		String penyJenis = map.get("penyJenis").toString();// 收款方式
		String selectIds = this.tosqlids((String) map.get("selectIds"));// 出纳收款单
		String documentsSmalltype = map.get("documentsSmalltype").toString();// 单据小类
		settlementAmount=Double.valueOf(String.valueOf(map.get("settlementAmount")));
		StringBuffer sqlbf = new StringBuffer();
		sqlbf.append("select a.* from Fi_Cashiercollection a where a.id in (");
		sqlbf.append(selectIds);
		sqlbf.append(")");
		// 出纳收款单列表
		List<FiCashiercollection> list1 = this.fiCashiercollectionDao.getSession().createSQLQuery(sqlbf.toString()).addEntity(FiCashiercollection.class).list();
		FiCashiercollection fc = list1.get(0);// 一次只能选择一张收款单
		
		if(list1.size()<1) throw new ServiceException("请选择出纳收款单!");
		if(list1.size()>1) throw new ServiceException("只能选择一个出纳收款单!");
		
		Double collectionAmount = DoubleUtil.sub(fc.getCollectionAmount(),fc.getEntrustAmount());// 收款单到账金额-已委托确认金额
		if(list.size()<1) throw new ServiceException("请选择出纳收款单!");
		if(list.size()>1) throw new ServiceException("只能选择一个收款单!");
		
		String sqlseq="select SEQ_FI_PAID.nextval SEQ from dual";
		Map seqid=(Map) this.fiPaidDao.createSQLQuery(sqlseq).list().get(0) ;
		seq=Long.valueOf(seqid.get("SEQ")+"");
		
		
		for (int i = 0; i < list.size(); i++) {
			FiPayment fiPayment = list.get(i);
			if (Double.doubleToLongBits(settlementAmount) > Double.doubleToLongBits(collectionAmount)) {// 本次收款金额>收纳收款单金额
				settlementAmount = collectionAmount;
			}
			
			//收款单余额判断：未委托确认金额+本次付款金额
			collectionAmount = DoubleUtil.add(fc.getEntrustAmount(), settlementAmount);
			if (collectionAmount >fc.getCollectionAmount()) {
				break;
			}

			fiPayment.setSettlementAmount(settlementAmount
					+ fiPayment.getSettlementAmount());// 实收总金额
			if (Double.doubleToLongBits(fiPayment.getSettlementAmount()) >= Double
					.doubleToLongBits(fiPayment.getAmount())) {
				fiPayment.setPaymentStatus(Long.valueOf(2));// 已收款
			}
			this.fiPaymentDao.save(fiPayment);// 更新应收应付表

			// 新增实收实付表
			FiPaid fiPaid = new FiPaid();
			fiPaid.setPaidId(seq);
			fiPaid.setFiPaymentId(fiPayment.getId());
			fiPaid.setPenyJenis(penyJenis);// 结算方式
			fiPaid.setSettlementAmount(settlementAmount);// 本次实收金额
			fiPaid.setFiCashiercollectionId(fc.getId());// 出纳收款单ID
			fiPaid.setFiCapitaaccountId(accountId);
			fiPaid.setAccountNum(accountNum);
			fiPaid.setAccountName(accountName);
			fiPaid.setBank(bank);
			fiPaid.setVerificationStatus(0L);
			fiPaid.setCreateId(createId);
			fiPaid.setIncomeDepartId(fiPayment.getIncomeDepartId());//收入部门
			this.fiPaidDao.save(fiPaid);
			
			// 欠款明细核销
			if ("对账单".equals(documentsSmalltype)) {
				this.fiReceivablestatementService.verificationReceistatment(settlementAmount, fiPayment.getDocumentsNo());
			}

			fc.setEntrustAmount(collectionAmount);// 委托确认总金额
			//操作日志
			oprHistoryService.saveFiLog(fiPayment.getDocumentsNo(), "应收单ID："+fiPayment.getId()+"通过委托确收收银,费用类型为："+fiPayment.getCostType()+",收银金额："+settlementAmount , 34L);
		}
		// 出纳收款单剩于金额
		fc.setEntrustUser(String.valueOf(user.get("name")));
		fc.setEntrustTime(new Date());
		this.fiCashiercollectionDao.save(fc);
		
		/*
		//出纳收款剩于金额存入预存款
		if(collectionAmount>0){
			List<FiAdvanceset> fatlist=this.fiAdvancesetDao.find("from FiAdvanceset  where customerId=? and isdelete=1", list.get(0).getCustomerId());
				if(fatlist.size()==0){
					throw new ServiceException("此客商未设置预存款账号，不能将多还款金额"+collectionAmount+"存入预存款账号!");
				}
				if(fatlist.size()>1){
					throw new ServiceException("此客商有"+fatlist.size()+"个预存款账号账号!");
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
				fiAdvance.setSourceData("应收多还款");
				fiAdvance.setSourceNo(fc.getId());
				fiAdvanceDao.save(fiAdvance);
		}
		*/


	}

	// 预付冲销
	private void advanceInfo(List<FiPayment> list, Map map) throws Exception {
		Long seq=0L;
		Double settlementAmount=0.0;//本次结算金额
		String penyJenis = map.get("penyJenis").toString();// 收款方式
		String selectIds = this.tosqlids((String) map.get("selectIds"));// 出纳收款单
		String documentsSmalltype = map.get("documentsSmalltype").toString();// 单据小类
		settlementAmount=Double.valueOf(String.valueOf(map.get("settlementAmount")));
		StringBuffer sqlbf = new StringBuffer();
		sqlbf.append("select a.* from Fi_Advanceset a where a.id in (");
		sqlbf.append(selectIds);
		sqlbf.append(")");
		
		User user=WebRalasafe.getCurrentUser(Struts2Utils.getRequest()); 
		Long createId=Long.valueOf(user.get("id")+"");
		// 付款列表
		List<FiAdvanceset> list1 = this.fiAdvancesetDao.getSession().createSQLQuery(sqlbf.toString()).addEntity(FiAdvanceset.class).list();
		FiAdvanceset fa = list1.get(0);
		Double openingBalance = fa.getOpeningBalance();// 预付账号余额
		if(openingBalance<=0.0) throw new ServiceException("预存款账号余额不足！");
		
		String sqlseq="select SEQ_FI_PAID.nextval SEQ from dual";
		Map seqid=(Map) this.fiPaidDao.createSQLQuery(sqlseq).list().get(0) ;
		seq=Long.valueOf(seqid.get("SEQ")+"");
		
		for (int i = 0; i < list.size(); i++) {
			FiPayment fiPayment = list.get(i);
			//Double thisamount = DoubleUtil.sub(fiPayment.getAmount(), fiPayment.getSettlementAmount());// 本次需要收款金额

			if (openingBalance <= 0) {
				break;
			}
			if (Double.doubleToLongBits(settlementAmount) > Double.doubleToLongBits(openingBalance)) {// 本次收款金额>收纳收款单金额
				settlementAmount = openingBalance;
			}
			
			
			openingBalance = DoubleUtil.sub(openingBalance , settlementAmount);// 预付账号余额

			fiPayment.setSettlementAmount(settlementAmount
					+ fiPayment.getSettlementAmount());// 实收总金额
			if (Double.doubleToLongBits(fiPayment.getSettlementAmount()) >= Double
					.doubleToLongBits(fiPayment.getAmount())) {
				fiPayment.setPaymentStatus(Long.valueOf(2));// 已收款
			}
			this.fiPaymentDao.save(fiPayment);// 更新应收应付表


			// 预付款结算单
			FiAdvance fiAdvance = new FiAdvance();
			fiAdvance.setSettlementType(Long.valueOf(2));// 付款
			fiAdvance.setCustomerId(fa.getCustomerId());// 预付款结算设置：客商ID
			fiAdvance.setCustomerName(fa.getCustomerName());
			fiAdvance.setSettlementAmount(settlementAmount);// 本次结算金额
			fiAdvance.setSettlementBalance(openingBalance);// 余额
			fiAdvance.setSourceData("应收款单");
			fiAdvance.setSourceNo(fiPayment.getId());
			fiAdvance.setFiAdvanceId(fa.getId());
			this.fiAdvanceDao.save(fiAdvance);
			
			// 新增实收实付表
			FiPaid fiPaid = new FiPaid();
			fiPaid.setPaidId(seq);
			fiPaid.setFiPaymentId(fiPayment.getId());
			fiPaid.setPenyJenis(penyJenis);// 结算方式
			fiPaid.setSettlementAmount(settlementAmount);// 本次实收金额
			fiPaid.setVerificationStatus(0L);
			fiPaid.setFiAdvanceId(fiAdvance.getId());
			fiPaid.setCreateId(createId);
			fiPaid.setIncomeDepartId(fiPayment.getIncomeDepartId());//收入部门
			this.fiPaidDao.save(fiPaid);

			// 收款单核销其它模块数据****
			this.verificationReceiving(fiPayment, settlementAmount);
			//操作日志
			oprHistoryService.saveFiLog(fiPayment.getDocumentsNo(), "应收单ID："+fiPayment.getId()+"通过预付款收银,费用类型为："+fiPayment.getCostType()+",收银金额："+settlementAmount , 34L);
			
		}
		// 预付款设置余额
		fa.setOpeningBalance(openingBalance);
		this.fiAdvancesetDao.save(fa);
	}

	// 收付对冲
	private void paymentInfo(List<FiPayment> list, Map map) throws Exception {
		User user=WebRalasafe.getCurrentUser(Struts2Utils.getRequest()); 
		Long createId=Long.valueOf(user.get("id")+"");
		// 实付单号
		Long seq=0L;
		String penyJenis = map.get("penyJenis").toString();// 收款方式
		String selectIds = this.tosqlids((String) map.get("selectIds"));// 付款单
		String documentsSmalltype = map.get("documentsSmalltype").toString();// 单据小类

		StringBuffer sqlbf = new StringBuffer();
		sqlbf.append("select a.* from fi_payment a where a.id in (");
		sqlbf.append(selectIds);
		sqlbf.append(")");
		if(list.size()<1) throw new ServiceException("冲销失败，请选择应收单号!");
		if(list.size()>1) throw new ServiceException("冲销失败，只能选择一个应收单号对冲!");
		FiPayment collection = list.get(0);// 收款列表(只能有一个收款单)
		Double thesettlementAmount = DoubleUtil.sub(collection.getAmount(), DoubleUtil.add(collection.getSettlementAmount(),collection.getEliminationAmount()));// 本次收款金额
		
		// 付款列表
		List<FiPayment> list1 = this.fiPaymentDao.getSession().createSQLQuery(sqlbf.toString()).addEntity(FiPayment.class).list();
		if(list1.size()<1) throw new ServiceException("冲销失败，请选择应付单号!");
		if(list1.size()>1) throw new ServiceException("冲销失败，只能选择一个应付单号对冲!");
		
		String sqlseq="select SEQ_FI_PAID.nextval SEQ from dual";
		Map seqid=(Map) this.fiPaidDao.createSQLQuery(sqlseq).list().get(0) ;
		seq=Long.valueOf(seqid.get("SEQ")+"");

		// 付款单处理
		FiPayment fiPayment = list1.get(0);// 应付款单
		FiPaid fiPaidcot = new FiPaid(); // 实付款单
		Double thefiPayment = DoubleUtil.sub(fiPayment.getAmount(), DoubleUtil.add(fiPayment.getSettlementAmount(),fiPayment.getEliminationAmount()));// 本次付款金额
		
		if(Double.doubleToLongBits(thesettlementAmount) < Double.doubleToLongBits(thefiPayment)){
			thefiPayment=thesettlementAmount;
		}
		
		fiPayment.setSettlementAmount(DoubleUtil.add(thefiPayment,fiPayment.getSettlementAmount()));// 已付金额
		
		if (Double.doubleToLongBits(fiPayment.getAmount()) == Double.doubleToLongBits(fiPayment.getSettlementAmount())) {
			fiPayment.setPaymentStatus(Long.valueOf(5));// 已付款
		}
		this.fiPaymentDao.save(fiPayment);

		// 实付款处理
		//fiPaidcot.setId(seq);
		fiPaidcot.setPaidId(seq);
		fiPaidcot.setSettlementAmount(thefiPayment);// 实付总金额(应付总额-已付总金额)
		fiPaidcot.setPenyJenis(penyJenis);// 结算方式
		fiPaidcot.setFiPaymentId(fiPayment.getId());// 应付单号
		fiPaidcot.setVerificationStatus(0L);
		fiPaidcot.setCreateId(createId);
		this.fiPaidDao.save(fiPaidcot);

		// 收款单处理
		if("问题账款".equals(fiPayment.getCostType())||"异常到付款".equals(fiPayment.getCostType())){
			collection.setEliminationAmount(thefiPayment);
		}else{
			collection.setSettlementAmount(collection.getSettlementAmount()+thefiPayment);
		}
		if(collection.getEliminationAmount()==null||"".equals(collection.getEliminationAmount())){
			collection.setEliminationAmount(0.0);
		}
		if (Double.doubleToLongBits(DoubleUtil.add(collection.getSettlementAmount(),collection.getEliminationAmount())) >= Double
				.doubleToLongBits(collection.getAmount())) {
			collection.setPaymentStatus(2L);// 已收款
		}
		this.fiPaymentDao.save(collection);
		FiPaid fiPaid = new FiPaid();// 实收单处理
		fiPaid.setPaidId(seq);
		fiPaid.setSettlementAmount(thefiPayment);
		fiPaid.setFiPaymentId(collection.getId());// 应收单号
		fiPaid.setPenyJenis(penyJenis);// 结算方式
		fiPaid.setVerificationStatus(0L);
		fiPaid.setFiPaidWriteId(fiPaidcot.getId());//冲销对应ID
		fiPaid.setCreateId(createId);
		fiPaid.setIncomeDepartId(fiPayment.getIncomeDepartId());//收入部门
		this.fiPaidDao.save(fiPaid);// 保存实收实付表
		
		
		// 收款单核销其它模块数据****
		if(!"问题账款".equals(fiPayment.getCostType())){
			this.verificationReceiving(collection, thefiPayment);
		}
		//付款单核销其它模块数据***
		this.verificationPayment(fiPayment, thefiPayment);
		
		//收款单操作日志
		oprHistoryService.saveFiLog(fiPayment.getDocumentsNo(), "应收单ID："+collection.getId()+"通过收付冲销收银,费用类型为："+collection.getCostType()+",收银金额："+thefiPayment , 34L);
		//付款单操作日志
		oprHistoryService.saveFiLog(fiPayment.getDocumentsNo(), "应付单ID："+fiPayment.getId()+"通过收付冲销付款,费用类型为："+fiPayment.getCostType()+",付款金额："+thefiPayment , 86L);
	}

	// 支票付款
	private void checkReceiving(List<FiPayment> list, Map map) throws Exception{
		User user=WebRalasafe.getCurrentUser(Struts2Utils.getRequest()); 
		Long createId=Long.valueOf(user.get("id")+"");
		
		Double thesettlementAmount = Double.valueOf(map.get("settlementAmount").toString());// 本次收款金额
		String documentsSmalltype = map.get("documentsSmalltype").toString();// 单据小类
		String penyJenis = map.get("penyJenis").toString();// 收款方式
		String checkUser=map.get("documentsSmalltype").toString();
		Long customerId=Long.valueOf(map.get("checkcustomerId").toString());

		if(list.size()==0) throw new ServiceException("收款单不存在，请重新选择！");
		if(list.size()>1) throw new ServiceException("收款单存在多条记录，请重新选择！");
		FiPayment fiPayment=list.get(0);
		
		List<Customer> listCustomer=this.customerService.find("from Customer c where c.id=?",customerId);
		if(listCustomer.size()==0) throw new ServiceException("客商不存在，请重新选择！");
		if(listCustomer.size()>1) throw new ServiceException("客商存在多条记录，请重新选择！");
		
		Customer customer=listCustomer.get(0);
		
		if(customer==null) throw new ServiceException("客商不存在");
		
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
		//操作日志
		oprHistoryService.saveFiLog(fiPayment.getDocumentsNo(), "应收单ID："+fiPayment.getId()+"通过支票收银,费用类型为："+fiPayment.getCostType()+",收银金额："+thesettlementAmount , 34L);

	}

	//支票到账审核
	@XbwlInt(isCheck=false)
	public void checkAudit(FiCheck fiCheck) throws Exception{
		User user=WebRalasafe.getCurrentUser(Struts2Utils.getRequest()); 
		Long createId=Long.valueOf(user.get("id")+"");
		Long seq=0L;
		if(fiCheck==null) throw new ServiceException("支票不存在");
		FiPayment fiPayment=this.fiPaymentDao.get(fiCheck.getFiPaymentId());
		if(fiPayment==null) throw new ServiceException("支票对应的应收单号");
		
		// 获取账号信息
		FiCapitaaccountset fiCapitaaccountset = this.fiCapitaaccountsetDao.get(fiCheck.getTodepositFiCapitaaccountset());
		if(fiPayment==null) throw new ServiceException("送存账号不存在");
		
		String documentsSmalltype=fiPayment.getDocumentsSmalltype();
		
		Double fiCheckAmount=fiCheck.getAmount();//支票金额
		//应收总金额
		Double settlementAmount = DoubleUtil.sub(fiPayment.getAmount(), fiPayment.getSettlementAmount()==null?0.0:fiPayment.getSettlementAmount());// 本次需要付款金额
		if (Double.doubleToLongBits(settlementAmount) > Double.doubleToLongBits(fiCheckAmount)) {// 本次收款金额>收纳收款单金额
			settlementAmount = fiCheckAmount;
		}
		
		fiPayment.setSettlementAmount(settlementAmount
				+ fiPayment.getSettlementAmount());// 实收总金额
		if (Double.doubleToLongBits(fiPayment.getSettlementAmount()) >= Double
				.doubleToLongBits(fiPayment.getAmount())) {
			fiPayment.setPaymentStatus(Long.valueOf(2));// 已收款
		//} else {
			//fiPayment.setPaymentStatus(Long.valueOf(3));// 部分收款
		}
		this.fiPaymentDao.save(fiPayment);// 更新应收应付表
		

		String sqlseq="select SEQ_FI_PAID.nextval SEQ from dual";
		Map seqid=(Map) this.fiPaidDao.createSQLQuery(sqlseq).list().get(0) ;
		seq=Long.valueOf(seqid.get("SEQ")+"");
		
		// 新增实收实付表
		FiPaid fiPaid = new FiPaid();
		fiPaid.setPaidId(seq);
		fiPaid.setFiPaymentId(fiPayment.getId());
		fiPaid.setPenyJenis("支票");// 结算方式
		fiPaid.setSettlementAmount(settlementAmount);// 本次实收金额
		fiPaid.setFiCheckId(fiCheck.getId());
		fiPaid.setAccountNum(fiCapitaaccountset.getAccountNum());
		fiPaid.setAccountName(fiCapitaaccountset.getAccountName());
		fiPaid.setBank(fiCapitaaccountset.getBank());
		fiPaid.setVerificationStatus(0L);
		fiPaid.setCreateId(createId);
		fiPaid.setIncomeDepartId(fiPayment.getIncomeDepartId());//收入部门
		this.fiPaidDao.save(fiPaid);
		
		// 核销其它模块数据****
		this.verificationReceiving(fiPayment, settlementAmount);
		
	}
	
	// 账号收款(现金、银行、POS)
	private void accountReceiving(List<FiPayment> list, Map map) throws Exception{
		User user=WebRalasafe.getCurrentUser(Struts2Utils.getRequest()); 
		Long createId=Long.valueOf(user.get("id")+"");
		Long seq=0L;
		String accountNum = null; // 账号
		String accountName = null; // 账号名称
		String bank = null; // 开户行
		Double openingBalance = 0.0; // 余额
		double thisamount =0.0;//本次收款金额
		double settlementAmount=0.0;
		
		String sqlseq="select SEQ_FI_PAID.nextval SEQ from dual";
		Map seqid=(Map) this.fiPaidDao.createSQLQuery(sqlseq).list().get(0) ;
		seq=Long.valueOf(seqid.get("SEQ")+"");

		Long accountNumId = Long.valueOf(map.get("selectIds").toString());// 收款账号id
		Double thesettlementAmount = Double.valueOf(map.get("settlementAmount")
				.toString());// 本次收款金额
		String penyJenis = map.get("penyJenis").toString();// 收款方式

		// 获取账号信息
		FiCapitaaccountset fiCapitaaccountset = this.fiCapitaaccountsetDao
				.get(accountNumId);
		accountNum = fiCapitaaccountset.getAccountNum();// 收付款账号
		accountName = fiCapitaaccountset.getAccountName();// 收款账号名称
		bank = fiCapitaaccountset.getBank();// 收款开户行
		openingBalance = fiCapitaaccountset.getOpeningBalance();// 账号余额
		if(openingBalance==null||"".equals(openingBalance)){
			openingBalance=0.0;
		};

		for (int i = 0; i < list.size(); i++) {
			FiPayment fiPayment = list.get(i);
			
			thisamount = DoubleUtil.sub(DoubleUtil.sub(fiPayment.getAmount(), fiPayment.getSettlementAmount()),fiPayment.getEliminationAmount());// 本次需要收款金额
			if(thisamount>thesettlementAmount){
				thisamount=thesettlementAmount;
			}
			fiPayment.setSettlementAmount(DoubleUtil.add(thisamount,fiPayment.getSettlementAmount()));// 实收总金额
			settlementAmount=DoubleUtil.add(fiPayment.getSettlementAmount(), fiPayment.getEliminationAmount());//已结算金额=已结算金额+问题账款金额
			
			if (Double.doubleToLongBits(settlementAmount) >= Double.doubleToLongBits(fiPayment.getAmount())) {
				fiPayment.setPaymentStatus(Long.valueOf(2));// 已收款
			//} else {
				//fiPayment.setPaymentStatus(Long.valueOf(3));// 部分收款
			}
			this.fiPaymentDao.save(fiPayment);// 更新应收应付表
			openingBalance=DoubleUtil.add(openingBalance , thisamount);
			
			
			//写入现金流水
			FiCapitaaccount fiCapitaaccount = new FiCapitaaccount();
			fiCapitaaccount.setFiCapitaaccountsetId(accountNumId);
			fiCapitaaccount.setLoan(thisamount);// 借
			fiCapitaaccount.setSourceData("收款单");
			fiCapitaaccount.setSourceNo(fiPayment.getId());
			fiCapitaaccount.setBalance(openingBalance);// 余额
			fiCapitaaccount.setStatus(1L);
			this.fiCapitaaccountDao.save(fiCapitaaccount);// 新增账号流水余额

			// 新增实收实付表
			FiPaid fiPaid = new FiPaid();
			fiPaid.setPaidId(seq);
			fiPaid.setAccountNum(accountNum);
			fiPaid.setAccountName(accountName);
			fiPaid.setBank(bank);
			fiPaid.setFiPaymentId(fiPayment.getId());
			fiPaid.setPenyJenis(penyJenis);// 结算方式
			fiPaid.setFiCapitaaccountId(fiCapitaaccount.getId());
			fiPaid.setSettlementAmount(thisamount);// 本次实收金额
			fiPaid.setVerificationStatus(0L);
			fiPaid.setCreateId(createId);
			fiPaid.setIncomeDepartId(fiPayment.getIncomeDepartId());//收入部门
			this.fiPaidDao.save(fiPaid);

			
			// 核销其它模块数据****
			this.verificationReceiving(fiPayment, thisamount);
			
			thesettlementAmount = DoubleUtil.sub(thesettlementAmount , thisamount);// 本次剩余金额
			
			//操作日志
			oprHistoryService.saveFiLog(fiPayment.getDocumentsNo(), "应收单ID："+fiPayment.getId()+"通过现金收银,费用类型："+fiPayment.getCostType()+",金额："+thisamount , 34L);
			
			if (thesettlementAmount <= 0) {
				continue;
			}
		}

		// 更新收款帐号余额
		fiCapitaaccountset.setOpeningBalance(openingBalance);// 新账号余额+本次实收总金额
		this.fiCapitaaccountsetDao.save(fiCapitaaccountset);// 更新账号余额
	}

	// 跨部门委托收付款
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
			//操作日志
			oprHistoryService.saveFiLog(fp.getDocumentsNo(), "应收单ID："+fp.getId()+",委托备注："+fiPayment.getEntrustRemark() ,67L);
		}
	}


	// 拼接付款单SQL
	private String PaymentSql(Map map) throws Exception {
		StringBuffer sqlbuffer = new StringBuffer();
		String ids = this.tosqlids((String) map.get("ids"));
		sqlbuffer.append("select a.* from fi_payment a where a.id in (");
		sqlbuffer.append(ids);
		sqlbuffer.append(") order by a.amount");
		return sqlbuffer.toString();
	}

	// 去掉选中多条记录后的最后一个逗号，如：(1,2,3,)变成：(1,2,3)
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
	
	//挂账
	@ModuleName(value="挂账保存",logType=LogType.fi)
	public void saveLosses(Map<String,Object> map) throws Exception{
		String sql = this.PaymentSql(map);
		Long customerId=Long.valueOf((String)map.get("customerId"));
		Long departId=Long.valueOf((String)map.get("departId"));
		String remark=(String)map.get("remark");
		
		List<FiArrearset> listfiList=fiArrearsetService.find("from FiArrearset f where f.customerId=? and f.departId=?",customerId,departId);
		if(listfiList.size()==0) throw new ServiceException("挂账客商不存在，请重新选择！");
		if(listfiList.size()>1) throw new ServiceException("客商在客商欠款设置中同一客商存在多条记录，请重新选择！");
		FiArrearset fiArrearset=listfiList.get(0);
		if(!"能".equals(fiArrearset.getIspaytoarrears())){
			throw new ServiceException("当前客商不允许到付转欠款，请重新选择！");
		}
		Customer customer=this.customerService.get(customerId);
		if(customer==null) throw new ServiceException("挂账客商不存在，请重新选择！");
		List<FiPayment> list = this.fiPaymentDao.getSession().createSQLQuery(
				sql).addEntity(FiPayment.class).list();
		if(list.size()==0) throw new ServiceException("收款单不存在，请重新选择！");
		if(list.size()>1) throw new ServiceException("一次只能选择一张收款单进行挂账，请重新选择！");
		FiPayment fiPayment=list.get(0);
		
		FiReceivabledetail fiReceivabledetail=new FiReceivabledetail();
		fiReceivabledetail.setPaymentType(1L);//收款单
		fiReceivabledetail.setDno(fiPayment.getDocumentsNo());
		fiReceivabledetail.setSourceData("应收挂账");
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
		
		//操作日志
		oprHistoryService.saveFiLog(fiReceivabledetail.getDno(), "应收单ID："+fiPayment.getId()+",挂账客商："+customer.getCusName() ,65L);
		
		//核销应付代收货款
		if("代收货款".equals(fiPayment.getCostType())){
			this.fiReceivabledetailService.verificationReceistatment(fiPayment);
		}
		
		//更新传真状态表收银状态
		if ("配送单".equals(fiPayment.getDocumentsSmalltype())) {
			this.oprStatusService.verificationCashStatusByFiPayment(fiPayment.getDocumentsNo());
		}
	}
	//付款单核销
	@XbwlInt(isCheck=false)
	private void verificationPayment(FiPayment fiPayment,Double thisamount) throws Exception{
		// 欠款明细核销
		if ("对账单".equals(fiPayment.getDocumentsSmalltype())) {
			this.fiReceivablestatementService.verificationReceistatment(thisamount, fiPayment.getDocumentsNo());
		}
		
		//提货成本核销
		if ("提货成本".equals(fiPayment.getSourceData())) {
			this.fiDeliverycostService.payConfirmationBybatchNo(fiPayment.getSourceNo());
		}
		
		//中转成本核销
		if ("中转成本".equals(fiPayment.getSourceData())) {
			this.fiTransitService.payConfirmationBybatchNo(fiPayment.getSourceNo());
		}
		
		//外发成本核销
		if ("外发成本".equals(fiPayment.getSourceData())) {
			this.fiOutCostService.payConfirmationBybatchNo(fiPayment.getSourceNo());
		}
		
		//车辆成本核销
		if ("车辆成本".equals(fiPayment.getSourceData())) {
			this.oprSignRouteService.payConfirmationById(fiPayment.getSourceNo());
		}
		
		//问题账款核销
		if("问题账款".equals(fiPayment.getSourceData())){
			this.fiProblemreceivableService.verfiProblemreceivable(thisamount, fiPayment.getSourceNo());
		}
		
		//异常到付款核销
		if("异常到付款".equals(fiPayment.getSourceData())){
			this.fiPaymentabnormalService.verfiPaymentabnormal(fiPayment);
		}
		
		// 回写预存款单收银状态(如果全额收银)
		if ("预存款单".equals(fiPayment.getDocumentsSmalltype())) {
			this.fiAdvancebpService.verfiFiAdvancebp(fiPayment.getSettlementAmount(), fiPayment.getDocumentsNo());
		}
	}

	//收款单核销
	@XbwlInt(isCheck=false)
	private void verificationReceiving(FiPayment fiPayment,Double thisamount) throws Exception{
		// 欠款明细核销
		if ("对账单".equals(fiPayment.getDocumentsSmalltype())) {
			this.fiReceivablestatementService.verificationReceistatment(thisamount, fiPayment.getDocumentsNo());
		}
		
		// 回写预存款单收银状态(如果全额收银)
		if ("预存款单".equals(fiPayment.getDocumentsSmalltype())) {
			this.fiAdvancebpService.verfiFiAdvancebp(fiPayment.getSettlementAmount(), fiPayment.getDocumentsNo());
		}
		
		
		//核销应付代收货款
		if("代收货款".equals(fiPayment.getCostType())){
			this.fiReceivabledetailService.verificationReceistatment(fiPayment);
		}
		
		//更新传真状态表收银状态
		if ("配送单".equals(fiPayment.getDocumentsSmalltype())) {
			this.oprStatusService.verificationCashStatusByFiPayment(fiPayment.getDocumentsNo());
		}
	}
	
	public void audit(Long id,User user) throws Exception{
		FiPayment fiPayment=this.fiPaymentDao.get(id);
		if(fiPayment==null){
			throw new ServiceException("单据不存在!");
		}
		if(fiPayment.getStatus()==0L){
			throw new ServiceException("单据已作废!");
		}
		if(fiPayment.getPaymentStatus()==2L){
			throw new ServiceException("单据已收款确认!");
		}
		if(fiPayment.getPaymentStatus()==5L){
			throw new ServiceException("单据已付款确认!");
		}
		fiPayment.setReviewStatus(1L);//已审核
		fiPayment.setReviewDate(new Date());
		fiPayment.setReviewUser(user.get("name")+"");
		this.fiPaymentDao.save(fiPayment);
	}
	
	public void revocationAudit(Long id,User user) throws Exception{
		FiPayment fiPayment=this.fiPaymentDao.get(id);
		if(fiPayment==null){
			throw new ServiceException("单据不存在!");
		}
		if(fiPayment.getStatus()==0L){
			throw new ServiceException("单据已作废!");
		}
		if(fiPayment.getPaymentStatus()==2L){
			throw new ServiceException("单据已收款确认!");
		}
		if(fiPayment.getPaymentStatus()==5L){
			throw new ServiceException("单据已付款确认!");
		}
		if(fiPayment.getReviewStatus()==0L){
			throw new ServiceException("单据还未审核!");
		}
		if(fiPayment.getReviewStatus()==1L){
			throw new ServiceException("单据已审核，不允许重复审核");
		}
		fiPayment.setReviewStatus(1L);//已审核
		fiPayment.setReviewDate(new Date());
		fiPayment.setReviewUser(user.get("name")+"");
		this.fiPaymentDao.save(fiPayment);
	}
}
