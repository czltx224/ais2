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

	// 应收应付Dao
	@Resource(name = "fiPaymentHibernateDaoImpl")
	private IFiPaymentDao fiPaymentDao;

	// 资金账号Dao
	@Resource(name = "fiCapitaaccountsetHibernateDaoImpl")
	private IFiCapitaaccountsetDao fiCapitaaccountsetDao;

	// 资金账号流水Dao
	@Resource(name = "fiCapitaaccountHibernateDaoImpl")
	private IFiCapitaaccountDao fiCapitaaccountDao;
	
	//出纳收款单Dao
	@Resource(name="fiCashiercollectionHibernateDaoImpl")
	private IFiCashiercollectionDao fiCashiercollectionDao;
	
	// 支票Dao
	@Resource(name = "fiCheckHibernateImpl")
	private IFiCheckDao fiCheckDao;
	
	//预付款结算Dao
	@Resource(name="fiAdvanceHibernateDaoImpl")
	private IFiAdvanceDao fiAdvanceDao;
	
	//预付款设置Dao
	@Resource(name = "fiAdvancesetHibernateDaoImpl")
	private IFiAdvancesetDao fiAdvancesetDao;
	
	//对账单
	@Resource(name = "fiReceivablestatementHibernateDaoImpl")
	private IFiReceivablestatementDao fiReceivablestatementDao;
	
	//欠款往来明细
	@Resource(name = "fiReceivabledetailHibernateDaoImpl")
	private IFiReceivabledetailDao fiReceivabledetailDao;
	
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
	
	//对账单
	@Resource(name = "fiReceivablestatementServiceImpl")
	private IFiReceivablestatementService fiReceivablestatementService;
	
	// 欠款明细Service
	@Resource(name = "fiReceivabledetailServiceImpl")
	private IFiReceivabledetailService fiReceivabledetailService;
	
	//传真状态表
	@Resource(name="oprStatusServiceImpl")
	private IOprStatusService oprStatusService;
	
	//问题账款
	@Resource(name="fiProblemreceivableServiceImpl")
	private IFiProblemreceivableService fiProblemreceivableService;
	
	//异常到付款
	@Resource(name = "fiPaymentabnormalServiceImpl")
	private IFiPaymentabnormalService fiPaymentabnormalService;
	
	//资金交接单
	@Resource(name="fiFundstransferServiceImpl")
	private IFiFundstransferService fiFundstransferService;
	
	//收入核算报表
	@Resource(name="fiIncomeAccountServiceImpl")
	private IFiIncomeAccountService fiIncomeAccountService;
	
	//日志
	@Resource(name = "oprHistoryServiceImpl")
	private IOprHistoryService oprHistoryService;
	
	@Resource(name="fiAdvancebpServiceImpl")
	private IFiAdvancebpService fiAdvancebpService;
	
	@ModuleName(value="撤销实收实付",logType=LogType.fi)
	public String revocation(Long fiPaidId) throws Exception {
		FiPaid fiPaid = this.fiPaidDao.get(fiPaidId);
		if (fiPaid==null){
			return "实收付款单未找到，无法撤销收付款！";
		}
		if(Long.valueOf(fiPaid.getVerificationStatus())==null||fiPaid.getVerificationStatus()==1L){
			return "实收付款单已核销，无法撤销收付款！";
		}
		if(fiPaid.getFiFundstransferStatus()==1L){
			return "实收付款单已上交，无法撤销收付款！";
		}
		
		FiPayment fiPayment = this.fiPaymentDao.get(fiPaid.getFiPaymentId());
		if (fiPaid==null){
			return "实收单对应的应收单号未找到，无法撤销收付款！";
		}
		
		String penyJenis = fiPaid.getPenyJenis();// 结算方式
		if (penyJenis.equals("现金") || penyJenis.equals("银行")|| penyJenis.equals("POS")) {
			this.accountRevocation(fiPaid);
		}else if (penyJenis.equals("支票")) {
			checkRevocation(fiPaid,fiPayment);
		}else if (penyJenis.equals("收付对冲")) {
			paymentInfoRevocation(fiPaid);
		}else if (penyJenis.equals("预付冲销")) {
			advanceInfoRevocation(fiPaid,fiPayment);
		}else if(penyJenis.equals("委托确收")){
			entrustInfoRevocation(fiPaid,fiPayment);
		}
		return "true";
	}
	
	//收付冲销撤销
	private void paymentInfoRevocation(FiPaid fiPaid) throws Exception{
		double amount=0.0;
		double fiPaymentSettlementAmount =0.0;
		String costType=null;
		List<FiPaid> list=this.fiPaidDao.find("from FiPaid f where f.paidId=? and f.status=1L",fiPaid.getPaidId());
		for(FiPaid fiPaid1:list){
			FiPayment fiPayment=this.fiPaymentDao.get(fiPaid1.getFiPaymentId());
			Double fiPaidsettlementAmount = fiPaid1.getSettlementAmount();// 实收付金额

			if(fiPaid1.getFiPaidWriteId()!=null||"".equals(fiPaid1.getFiPaidWriteId())){
				FiPaid fp=this.fiPaidDao.get(fiPaid1.getFiPaidWriteId());
				FiPayment fiPayment1=this.fiPaymentDao.get(fp.getFiPaymentId());
				costType=fiPayment1.getCostType();
				if("问题账款".equals(fiPayment1.getCostType())||"异常到付款".equals(fiPayment1.getCostType())){
					fiPaymentSettlementAmount = DoubleUtil.sub(fiPayment.getEliminationAmount(),fiPaidsettlementAmount);// 应收付款已结算金额=已结算金额-实收实付金额
					fiPayment.setEliminationAmount(fiPaymentSettlementAmount);
				}else{
					fiPaymentSettlementAmount = DoubleUtil.sub(fiPayment.getSettlementAmount(),fiPaidsettlementAmount);// 应收付款已结算金额=已结算金额-实收实付金额
					fiPayment.setSettlementAmount(fiPaymentSettlementAmount);
				}
			}else{
				fiPaymentSettlementAmount = DoubleUtil.sub(fiPayment.getSettlementAmount(),fiPaidsettlementAmount);// 应收付款已结算金额=已结算金额-实收实付金额
				fiPayment.setSettlementAmount(fiPaymentSettlementAmount);
			}
			amount=fiPayment.getAmount();//应收总额
			fiPaymentSettlementAmount=DoubleUtil.add(fiPayment.getSettlementAmount(),fiPayment.getEliminationAmount());
			if(fiPayment.getPaymentType()==1L){//撤销收款单
				//应收应付处理
				if (fiPaymentSettlementAmount != amount) {
					fiPayment.setPaymentStatus(Long.valueOf(1));// 未收款
				}
			}else{
				if (fiPaymentSettlementAmount != amount) {
					fiPayment.setPaymentStatus(Long.valueOf(4));// 未付款
				}
			}
			
			this.fiPaymentDao.save(fiPayment);
			
			//实收实付作废
			fiPaid1.setStatus(Long.valueOf(0));
			this.fiPaidDao.save(fiPaid1);
			
			//撤销其它模块核销数据
			if(fiPayment.getPaymentType()==1L&&!"问题账款".equals(costType)){//撤销收款单
				this.revocationReceiving(fiPayment, fiPaid);
			}

			if(fiPayment.getPaymentType()==2){
				this.revocationPayment(fiPayment, fiPaid);
			}
		}
	}

	
	//预付冲销撤销
	private void advanceInfoRevocation(FiPaid fiPaid, FiPayment fiPayment) throws Exception{
		Double fiPaidsettlementAmount = fiPaid.getSettlementAmount();// 实收付金额
		double fiPaymentSettlementAmount = fiPayment.getSettlementAmount()
				- fiPaidsettlementAmount;// 应收付款已结算金额=已结算金额-实收实付金额
		double amount=fiPayment.getAmount();
		
		if(fiPaid.getVerificationStatus()==1L) throw new ServiceException("单据已核销，不允许再撤销");
		
		//应收应付处理
		if(fiPayment.getPaymentType()==1L){//撤销收款单
			//应收应付处理
			if (fiPaymentSettlementAmount !=amount) {
				fiPayment.setPaymentStatus(Long.valueOf(1));// 未收款
			}
		}else{
			if (fiPaymentSettlementAmount !=amount) {
				fiPayment.setPaymentStatus(Long.valueOf(4));// 未付款
			}
		}
		fiPayment.setSettlementAmount(fiPaymentSettlementAmount);
		this.fiPaymentDao.save(fiPayment);
		
		//实收实付作废
		fiPaid.setStatus(Long.valueOf(0));
		this.fiPaidDao.save(fiPaid);
		
		//预付款清单作废
		FiAdvance fiAdvance=this.fiAdvanceDao.get(fiPaid.getFiAdvanceId());
		fiAdvance.setStatus(Long.valueOf(0));//作废
		this.fiAdvanceDao.save(fiAdvance);
		
		//预付款账号设置
		FiAdvanceset fiAdvanceset=this.fiAdvancesetDao.get(fiAdvance.getFiAdvanceId());
		fiAdvanceset.setOpeningBalance(fiAdvanceset.getOpeningBalance()+fiPaidsettlementAmount);
		this.fiAdvancesetDao.save(fiAdvanceset);
		
		//撤销其它模块核销数据..
		this.revocationReceiving(fiPayment, fiPaid);
		
		//操作日志
		oprHistoryService.saveFiLog(fiPayment.getDocumentsNo(), "实收单ID："+fiPaid.getId()+"撤销收银,金额："+fiPaid.getSettlementAmount() , 34L);
		
	}
	
	//支票收款撤销
	private void checkRevocation(FiPaid fiPaid, FiPayment fiPayment) throws Exception{
		double fiPaidsettlementAmount = fiPaid.getSettlementAmount();// 实收付金额
		double fiPaymentSettlementAmount = fiPayment.getSettlementAmount()
				- fiPaidsettlementAmount;// 应收付款已结算金额=已结算金额-实收实付金额
		double amount=fiPayment.getAmount();
		if(fiPaid.getVerificationStatus()==1L) throw new ServiceException("单据已核销，不允许再撤销");
		//应收应付处理
		if (fiPaymentSettlementAmount != amount) {
			fiPayment.setPaymentStatus(Long.valueOf(1));// 未收款
		}
		fiPayment.setSettlementAmount(fiPaymentSettlementAmount);
		this.fiPaymentDao.save(fiPayment);
		
		//实收实付作废
		fiPaid.setStatus(Long.valueOf(0));
		this.fiPaidDao.save(fiPaid);
		
		//减去支票金额
		FiCheck fiCheck=this.fiCheckDao.get(fiPaid.getFiCheckId());
		fiCheck.setConfirmDate(new Date());
		fiCheck.setConfirmStatus(0l);
		this.fiCheckDao.save(fiCheck);
		
		//撤销其它模块核销数据..
		this.revocationReceiving(fiPayment, fiPaid);
		
		//操作日志
		oprHistoryService.saveFiLog(fiPayment.getDocumentsNo(), "实收单ID："+fiPaid.getId()+"撤销收银,金额："+fiPaid.getSettlementAmount() , 34L);
		
	}
	
	//委托收款撤销
	private void entrustInfoRevocation(FiPaid fiPaid, FiPayment fiPayment) throws Exception{
		double fiPaidsettlementAmount = fiPaid.getSettlementAmount();// 实收付金额
		double amount=fiPayment.getAmount();
		double fiPaymentSettlementAmount = fiPayment.getSettlementAmount()
				- fiPaidsettlementAmount;// 应收付款已结算金额=已结算金额-实收实付金额
		
		if(fiPaid.getVerificationStatus()==1L) throw new ServiceException("单据已核销，不允许再撤销");
		//应收应付处理
		if (fiPaymentSettlementAmount != amount) {
			fiPayment.setPaymentStatus(Long.valueOf(1));// 未收款
		}
		fiPayment.setSettlementAmount(fiPaymentSettlementAmount);
		this.fiPaymentDao.save(fiPayment);
		
		//实收实付作废
		fiPaid.setStatus(Long.valueOf(0));
		this.fiPaidDao.save(fiPaid);
		
		//出纳收款单处理
		FiCashiercollection fiCashiercollection=this.fiCashiercollectionDao.get(fiPaid.getFiCashiercollectionId());
		fiCashiercollection.setEntrustAmount(fiCashiercollection.getEntrustAmount()-fiPaid.getSettlementAmount());//委托确收金额=已确收金额-减去收款单确收金额
		fiCashiercollection.setEntrustTime(new Date());
		
		this.fiCashiercollectionDao.save(fiCashiercollection);
		
		//撤销其它模块核销数据..
		this.revocationReceiving(fiPayment, fiPaid);
		
		//操作日志
		oprHistoryService.saveFiLog(fiPayment.getDocumentsNo(), "实收单ID："+fiPaid.getId()+"撤销收银,金额："+fiPaid.getSettlementAmount() , 34L);
	}
	
	//现金、银行、POS收银撤销
	private void accountRevocation(FiPaid fiPaid) throws Exception{
		List<FiPaid> list=this.fiPaidDao.find("from FiPaid f where f.paidId=? and f.status=1L",fiPaid.getPaidId());
		for(FiPaid fiPaid1:list){
			FiPayment fiPayment=this.fiPaymentDao.get(fiPaid1.getFiPaymentId());
			double fiPaidsettlementAmount = fiPaid1.getSettlementAmount();// 实收付金额
			double fiPaymentSettlementAmount = fiPayment.getSettlementAmount()- fiPaidsettlementAmount;// 应收付款已结算金额=已结算金额-实收实付金额
			double amount=fiPayment.getAmount();
			if(fiPayment.getPaymentType()==1L){//撤销收款单
				//应收应付处理
				if (fiPaymentSettlementAmount != amount) {
					fiPayment.setPaymentStatus(Long.valueOf(1));// 未收款
				}
			}else{
				if (fiPaymentSettlementAmount != amount) {
					fiPayment.setPaymentStatus(Long.valueOf(4));// 未付款
				}
			}
			fiPayment.setSettlementAmount(fiPaymentSettlementAmount);
			this.fiPaymentDao.save(fiPayment);
			
			//实收实付作废
			fiPaid1.setStatus(Long.valueOf(0));
			this.fiPaidDao.save(fiPaid1);
			
			FiCapitaaccount fiCapitaaccount1 = new FiCapitaaccount();
			
			//资金账号流水处理
			FiCapitaaccount fiCapitaaccount=this.fiCapitaaccountDao.get(fiPaid1.getFiCapitaaccountId());
			
			//资金账号余额处理
			FiCapitaaccountset fiCapitaaccountset=this.fiCapitaaccountsetDao.get(fiCapitaaccount.getFiCapitaaccountsetId());
			if(fiPayment.getPaymentType()==2L){
				fiCapitaaccountset.setOpeningBalance(fiCapitaaccountset.getOpeningBalance()+fiPaidsettlementAmount);
				fiCapitaaccount1.setBorrow(DoubleUtil.mul(fiCapitaaccount.getBorrow(), -1));// 贷
			}else{
				fiCapitaaccountset.setOpeningBalance(fiCapitaaccountset.getOpeningBalance()-fiPaidsettlementAmount);
				fiCapitaaccount1.setLoan(DoubleUtil.mul(fiCapitaaccount.getLoan(), -1));// 借
			}
			this.fiCapitaaccountsetDao.save(fiCapitaaccountset);
			
			fiCapitaaccount1.setFiCapitaaccountsetId(fiCapitaaccountset.getId());
			
			fiCapitaaccount1.setSourceData("实收实付");
			fiCapitaaccount1.setRemark("撤销收银");
			fiCapitaaccount1.setSourceNo(fiPaid1.getId());
			fiCapitaaccount1.setBalance(fiCapitaaccountset.getOpeningBalance());// 余额
			
			this.fiCapitaaccountDao.save(fiCapitaaccount1);

			//撤销其它模块核销数据
			if(fiPayment.getPaymentType()==1L){//撤销收款单
				this.revocationReceiving(fiPayment, fiPaid);
			}else{
				this.revocationPayment(fiPayment, fiPaid);
			}

			//操作日志
			oprHistoryService.saveFiLog(fiPayment.getDocumentsNo(), "实收单ID："+fiPaid.getId()+"撤销收银,金额："+fiPaid.getSettlementAmount() , 34L);
		}
	}
	
	//付款单撤销
	private void revocationPayment(FiPayment fiPayment,FiPaid fiPaid) throws Exception{
		//提货成本撤销收银
		if ("提货成本".equals(fiPayment.getSourceData())) {
			this.fiDeliverycostService.payConfirmationRegisterBybatchNo(fiPayment.getSourceNo());
		}
		
		//中转成本核销
		if ("中转成本".equals(fiPayment.getSourceData())) {
			this.fiTransitService.payConfirmationRegisterBybatchNo(fiPayment.getSourceNo());
		}
		
		//外发成本核销
		if ("外发成本".equals(fiPayment.getSourceData())) {
			this.fiOutCostService.payConfirmationRegisterBybatchNo(fiPayment.getSourceNo());
		}
		
		//车辆成本核销
		if ("车辆成本".equals(fiPayment.getSourceData())) {
			this.oprSignRouteService.payConfirmationRegisterById(fiPayment.getSourceNo());
		}
		
		if ("对账单".equals(fiPayment.getSourceData())) {
			this.fiReceivablestatementService.revocationFiPaid(fiPayment, fiPaid);
		}
		
		//问题账款核销
		if("问题账款".equals(fiPayment.getSourceData())){
			this.fiProblemreceivableService.verfiProblemreceivableRegister(fiPaid.getSettlementAmount(), fiPayment.getSourceNo());
		}
		
		//异常到付款核销
		if("异常到付款".equals(fiPayment.getSourceData())){
			this.fiPaymentabnormalService.verfiPaymentabnormalRegister(fiPaid.getSettlementAmount(), fiPayment.getSourceNo());
		}
		
		// 回写预存款单收银状态
		if ("预存款单".equals(fiPayment.getDocumentsSmalltype())) {
			this.fiAdvancebpService.verfiFiAdvancebpRegister(fiPayment.getDocumentsNo());
		}
	}
	
	//收款单撤销
	private void revocationReceiving(FiPayment fiPayment,FiPaid fiPaid) throws Exception{
		
		if ("对账单".equals(fiPayment.getSourceData())) {
			this.fiReceivablestatementService.revocationFiPaid(fiPayment, fiPaid);
		}
		
		//现结应收代收货款，撤销核销应付代收货款
		if("代收货款".equals(fiPayment.getCostType())){
			this.fiReceivabledetailService.revocationFiPaid(fiPayment);
		}
		
		//更新传真状态表收银状态
		if ("配送单".equals(fiPayment.getDocumentsSmalltype())) {
			this.oprStatusService.revocationCashStatus(fiPayment.getDocumentsNo());
		}
		
		// 回写预存款单收银状态
		if ("预存款单".equals(fiPayment.getDocumentsSmalltype())) {
			this.fiAdvancebpService.verfiFiAdvancebpRegister(fiPayment.getDocumentsNo());
		}
		
	}

	public void verificationById(Long id,User user) throws Exception {
		FiPaid fiPaid=this.fiPaidDao.get(id);
		if(fiPaid==null){
			throw new ServiceException("核销失败，实收实付单不存在！");
		}
		
		fiPaid.setFiFundstransferStatus(1L);//上交状态
		fiPaid.setVerificationAmount(fiPaid.getSettlementAmount());
		fiPaid.setVerificationUser(String.valueOf(user.get("name")));
		fiPaid.setVerificationDept(String.valueOf(user.get("departName")));
		fiPaid.setVerificationTime(new Date());
		fiPaid.setVerificationStatus(1L);
	}
	
	public void paymentVerification(String ids,User user) throws Exception {
		Long id=0L;
		if("null".equals(ids)||"".equals(ids)){
			throw new ServiceException("核销失败，实收实付单不存在！");
		}
		String[] idsp=ids.split(",");
		if(idsp.length==0){
			throw new ServiceException("核销失败，实收实付单不存在！");
		}
		for(int i=0;i<idsp.length;i++){
			id=Long.valueOf(idsp[i]);
			this.verificationById(id, user);
		}
	}
	
	
	public Map searchHandInAmount(Map map) throws Exception{
		String paidIds=this.tosqlids(map.get("paidIds")+"");//实收实付ID
		Long userId=0L;
		User user=WebRalasafe.getCurrentUser(Struts2Utils.getRequest()); 
		userId=Long.valueOf(user.get("id")+"");
		StringBuffer sb=new StringBuffer();
		sb.append("select nvl(sum(f.settlement_amount),0) as settlement_amount from fi_paid f left join fi_payment fp on f.fi_payment_id=fp.id where fp.payment_type=1 and f.verification_status=0 and f.status=1 and f.FI_FUNDSTRANSFER_STATUS=0 and f.PENY_JENIS='现金' and f.create_id=?");
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
			throw new ServiceException("您没有需要上交的金额!");
		}
		return mapReturn;
	}
	
	
	public void handInConfirmation(Map map,User user) throws Exception{
		String paidIds=this.tosqlids(map.get("paidIds")+"");
		Long receivablesaccountId=Long.valueOf(map.get("receivablesaccountId")+"");//收款账号ID
		Long receivablesaccountDeptid=Long.valueOf(map.get("receivablesaccountDeptid")+"");//收款部门ID
		String receivablesaccountDept=map.get("receivablesaccountDept")+"";//收款人部门
		Long paymentaccountId=Long.valueOf(map.get("paymentaccountId")+"");//付款账号ID
		String remark=map.get("remark")+"";//付款备注
		Double settlementAmountSum=0.0;//实收总金额
		Double settlementAmount=Double.valueOf(map.get("settlementAmount")+"");//实收总金额
		Double sumAmount=Double.valueOf(map.get("sumAmount")+"");//未上交总金额
		Long userId=0L;
	    userId=Long.valueOf(user.get("id")+"");
	    StringBuffer sb=new StringBuffer();
	    sb.append("select f.* from fi_paid f left join fi_payment fp on f.fi_payment_id=fp.id where fp.payment_type=1 and f.verification_status=0 and f.status=1 and f.FI_FUNDSTRANSFER_STATUS=0 and f.PENY_JENIS='现金' and f.create_id=");
	    sb.append(userId);
	    if(!"null".equals(paidIds)&&!"".equals(paidIds)){
	      sb.append(" and f.paid_id in (");
	      sb.append(paidIds);
	      sb.append(")");
	    }
	    Map mapReturn=this.searchHandInAmount(map);
	    //settlementAmount=Double.valueOf(mapReturn.get("SETTLEMENT_AMOUNT")+"");
	    //创建交接单
	    FiFundstransfer fiFundstransfer=new FiFundstransfer();
	    fiFundstransfer.setPaymentaccountId(paymentaccountId);
	    fiFundstransfer.setReceivablesaccountDeptid(receivablesaccountDeptid);
	    fiFundstransfer.setReceivablesaccountId(receivablesaccountId);
	    fiFundstransfer.setReceivablesaccountDept(receivablesaccountDept);
	    fiFundstransfer.setRemark(remark);
	    fiFundstransfer.setSourceData("实收实付单");
	    fiFundstransfer.setAmount(sumAmount);
	    this.fiFundstransferService.save(fiFundstransfer);
	    
	    List<FiPaid> list=this.fiPaidDao.getSession().createSQLQuery(sb.toString()).addEntity(FiPaid.class).list();
	    if(list==null||list.size()<=0){
	    	throw new ServiceException("未找到需要上交的实收单!");
	    }
	    
	    for(FiPaid fiPaid:list){
	    	//实收实付总金额
	    	settlementAmountSum=DoubleUtil.add(settlementAmountSum, fiPaid.getSettlementAmount());
	    	//核销实收实付
	    	/*fiPaid.setVerificationAmount(fiPaid.getSettlementAmount());
	    	fiPaid.setVerificationStatus(1L);
	    	fiPaid.setVerificationTime(new Date());
	    	fiPaid.setVerificationUser(user.get("name")+"");*/
	    	fiPaid.setFiFundstransferId(fiFundstransfer.getId());
	    	fiPaid.setFiFundstransferStatus(1L);
	    	this.fiPaidDao.save(fiPaid);
	    }
	    
	    if(!settlementAmount.equals(settlementAmountSum)){
	    	throw new ServerCloneException("数据异常，请重新再试!");
	    }
	    
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
	
	public void addAccountSingle(Long departId,String startTime,String endTime,Long seq) throws Exception{
		Double cashAmount=0.0;//现金
		Double posAmount=0.0;//POS
		Double checkAmount=0.0;//支票
		Double intecollectionAmount1=0.0;//内部代收余额(替别人收)
		Double intecollectionAmount2=0.0;//内部代收余额(别人替我收)
		Double intecollectionAmount=0.0;//内部代收余额(我替别人收-别人替我收的)
		Double eliminationAmount=0.0;//到付冲销
		Double collectionAmount=0.0;//代收货款
		Double consigneeAmount=0.0;//到付款
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date accountData = sdf.parse(startTime);
		String hql=null;
		String sql=null;
		
		//根据核销日期统计需交账收款单
		//本部门收银数据
		hql="update fi_paid f set f.account_id=? where exists(select 1 from fi_payment fp where f.fi_payment_id=fp.id and fp.payment_type=1 and f.VERIFICATION_TIME between to_date('"+startTime+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+endTime+"','yyyy-mm-dd hh24:mi:ss') and f.DEPART_ID=? and f.VERIFICATION_STATUS=1 and f.status=1 and (f.peny_Jenis='现金' or f.peny_Jenis='支票' or f.peny_Jenis='POS' or f.peny_Jenis='收付对冲'))";
		this.fiPaidDao.batchSQLExecute(hql, seq,departId);
		
		//别的部门代收银数据
		hql="update fi_paid f set f.account_ds_id=? where exists(select 1 from fi_payment fp where f.fi_payment_id=fp.id and fp.payment_type=1 and f.VERIFICATION_TIME between to_date('"+startTime+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+endTime+"','yyyy-mm-dd hh24:mi:ss') and f.DEPART_ID!=? and f.income_depart_id=? and f.VERIFICATION_STATUS=1 and f.status=1 and (f.peny_Jenis='现金' or f.peny_Jenis='支票' or f.peny_Jenis='POS' or f.peny_Jenis='收付对冲'))";
		this.fiPaidDao.batchSQLExecute(hql, seq,departId,departId);
		
		//获取本部门实际收银数据
		sql="select nvl(sum(case when fp.peny_jenis='现金' then fp.settlement_amount else 0 end),0) as cashAmount,nvl(sum(case when fp.peny_jenis='POS' then fp.settlement_amount else 0 end),0) as posAmount,nvl(sum(case when fp.peny_jenis='支票' then fp.settlement_amount else 0 end),0) as checkAmount,nvl(sum(case when fp.peny_jenis='收付对冲' then fp.settlement_amount else 0 end),0) as eliminationAmount from fi_paid fp where fp.account_id=? and fp.DEPART_ID=?";
		Map map =(Map) this.fiPaidDao.createSQLQuery(sql, seq,departId).list().get(0);
		cashAmount=Double.valueOf(map.get("CASHAMOUNT")+"");//现金合计
		posAmount=Double.valueOf(map.get("POSAMOUNT")+"");//POS合计
		checkAmount=Double.valueOf(map.get("CHECKAMOUNT")+"");//支票合计
		eliminationAmount=Double.valueOf(map.get("ELIMINATIONAMOUNT")+"");//到付冲销
		
		//业务类型(计算本部门的到付款与代收货款+别的部门代收到付款与代收货款)
		sql="select nvl(sum(case when fy.cost_type != '代收货款' then fp.settlement_amount else 0 end),0) as consigneeAmount,nvl(sum(case when fy.cost_type = '代收货款' then fp.settlement_amount else 0 end),0) as collectionAmount from fi_paid fp left join fi_payment fy on fp.fi_payment_id = fy.id and (fp.account_id=? or fp.account_ds_id=?) and ((fp.DEPART_ID=? and fp.income_depart_id=?) or (fp.DEPART_ID!=? and fp.income_depart_id=?))";
		Map map1 =(Map) this.fiPaidDao.createSQLQuery(sql, seq,seq,departId,departId,departId,departId).list().get(0);
		consigneeAmount=Double.valueOf(map1.get("CONSIGNEEAMOUNT")+"");
		collectionAmount=Double.valueOf(map1.get("COLLECTIONAMOUNT")+"");
		
		//内部代收(从收银中减代别部门收的)
		sql="select nvl(sum(fp.settlement_amount),0) as intecollectionAmount from fi_paid fp left join fi_payment fy on fp.fi_payment_id = fy.id where fp.account_id=? and fp.DEPART_ID=? and fp.income_depart_id!=?";
		Map map2 =(Map) this.fiPaidDao.createSQLQuery(sql, seq,departId,departId).list().get(0);
		intecollectionAmount1=Double.valueOf(map2.get("INTECOLLECTIONAMOUNT")+"");
		
		//内部代收(从别部门收银加入收入部门为本部门的)
		sql="select nvl(sum(fp.settlement_amount),0) as intecollectionAmount from fi_paid fp left join fi_payment fy on fp.fi_payment_id = fy.id where fp.account_ds_id=? and fp.DEPART_ID!=? and fy.income_depart_id=?";
		Map map3 =(Map) this.fiPaidDao.createSQLQuery(sql, seq,departId,departId).list().get(0);
		intecollectionAmount2=Double.valueOf(map3.get("INTECOLLECTIONAMOUNT")+"");
		
		//内部代收余额
		intecollectionAmount=DoubleUtil.sub(intecollectionAmount1,intecollectionAmount2);
		
		FiIncomeAccount fiIncomeAccount=new FiIncomeAccount();
		fiIncomeAccount.setTypeName("收银");
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
