package com.xbwl.finance.Service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.ServerException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
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
import com.xbwl.common.utils.XbwlInt;
import com.xbwl.entity.Customer;
import com.xbwl.entity.FiArrearset;
import com.xbwl.entity.FiPaid;
import com.xbwl.entity.FiPayment;
import com.xbwl.entity.FiReceivabledetail;
import com.xbwl.entity.FiReceivablestatement;
import com.xbwl.entity.FiReconciliationAccount;
import com.xbwl.entity.FiRepayment;
import com.xbwl.finance.Service.IFiArrearsetService;
import com.xbwl.finance.Service.IFiPaymentService;
import com.xbwl.finance.Service.IFiReceivabledetailService;
import com.xbwl.finance.Service.IFiReceivablestatementService;
import com.xbwl.finance.Service.IFiReconciliationAccountService;
import com.xbwl.finance.Service.IFiRepaymentService;
import com.xbwl.finance.dao.IFiReceivabledetailDao;
import com.xbwl.finance.dao.IFiReceivablestatementDao;
import com.xbwl.finance.dto.IFiInterface;
import com.xbwl.finance.dto.impl.FiInterfaceProDto;
import com.xbwl.oper.stock.dao.IOprHistoryDao;
import com.xbwl.oper.stock.service.IOprHistoryService;
import com.xbwl.oper.stock.service.IOprStatusService;
import com.xbwl.report.print.bean.FiReconAccountBean;
import com.xbwl.sys.service.ICustomerService;

/**
 * @author Administrator
 *
 */
@Service("fiReceivablestatementServiceImpl")
@Transactional(rollbackFor={Exception.class})
public class FiReceivablestatementServiceImpl extends
		BaseServiceImpl<FiReceivablestatement, Long> implements
		IFiReceivablestatementService {

	@Resource(name = "fiReceivablestatementHibernateDaoImpl")
	private IFiReceivablestatementDao fiReceivablestatementDao;
	
	@Resource(name = "fiReceivabledetailServiceImpl")
	private IFiReceivabledetailService fiReceivabledetailService;
	
	@Resource(name = "fiReceivabledetailHibernateDaoImpl")
	private IFiReceivabledetailDao fiReceivabledetailDao;
 
	@Resource(name = "fiReceivablestatementServiceImpl")
	private IFiReceivablestatementService fiReceivablestatementService;
	
	@Resource(name="fiReconciliationAccountServiceImpl")
	private IFiReconciliationAccountService fiReconciliationAccountService;
	
	@Resource(name = "customerServiceImpl")
	private ICustomerService customerService;
	
	//应收应付Service
	@Resource(name = "fiPaymentServiceImpl")
	private IFiPaymentService fiPaymentService;
	
	//传真状态表
	@Resource(name="oprStatusServiceImpl")
	private IOprStatusService oprStatusService;
	
	//日志
	@Resource(name="oprHistoryHibernateDaoImpl")
	private IOprHistoryDao oprHistoryDao;
	
	//日志
	@Resource(name = "oprHistoryServiceImpl")
	private IOprHistoryService oprHistoryService;
	
	//核算交账
	@Resource(name="fiRepaymentServiceImpl")
	private IFiRepaymentService fiRepaymentService;
	
	//客商欠款设置
	@Resource(name="fiArrearsetServiceImpl")
	private IFiArrearsetService fiArrearsetService;
	
	@Resource(name="fiInterfaceImpl")
	private IFiInterface fiInterface;
	@Override
	public IBaseDAO getBaseDao() {
		return fiReceivablestatementDao;
	}

	
	public boolean isConfirmReview(String reconciliationNos) {
		boolean flag=true;
		String[] creconciliationNos=reconciliationNos.split(",");
		for(int i=0;i<creconciliationNos.length;i++){
			FiReceivablestatement frs=this.get(Long.valueOf(creconciliationNos[i]));
			Long status=frs.getReconciliationStatus();
			if(status!=2L){//未审核
				flag=false;
				break;
			}
		}
		       
		return flag;
	}
	
 
	@ModuleName(value="对账单审核",logType=LogType.fi)
	public void confirmReview(String reconciliationNos,User user) throws Exception {
		String[] creconciliationNos=reconciliationNos.split(",");
		double collectionAmount=0.0;
		String dno="";
		for(int i=0;i<creconciliationNos.length;i++){
			//对账单收银状态(未收银不允许对账审核)
			List<FiReceivabledetail> list=this.fiReceivabledetailService.findCollectionByreconciliationNo(Long.valueOf(creconciliationNos[i]));
			for(FiReceivabledetail fiReceivabledetail:list){
				if(2L!=fiReceivabledetail.getCollectionStatus()){
					collectionAmount=DoubleUtil.add(collectionAmount, fiReceivabledetail.getAmount());
					if("".equals(dno)){
						dno=fiReceivabledetail.getDno()+"";
					}else{
						dno=dno+","+fiReceivabledetail.getDno();
					}
				}
			}
			if(collectionAmount>0.0){
				throw new ServiceException("配送单号<"+dno+">的应收代收货款未收银,不允许对账审核!");
			}
			
			//更新对账单状态
			FiReceivablestatement frs=this.get(Long.valueOf(creconciliationNos[i]));
			Double accountsBalance=frs.getAccountsBalance();// 应收余额
			frs.setReconciliationStatus(Long.valueOf(3));
			frs.setReviewUser(String.valueOf(user.get("name")));
			frs.setReviewDate(new Date());
			frs.setReviewDept(String.valueOf(user.get("departName")));
			this.save(frs);
			
			//将数据写入应收应付表
			FiInterfaceProDto fpd=new FiInterfaceProDto();
			fpd.setCustomerId(frs.getCustomerId());
			fpd.setCustomerName(frs.getCustomerName());
			fpd.setDistributionMode("客商");
			fpd.setDocumentsType("对账");
			fpd.setDocumentsSmalltype("对账单");
			fpd.setDocumentsNo(frs.getId());
			fpd.setSettlementType(accountsBalance>0?1L:2L);//如果应收余额大于0为收款单，如果小于0为付款单
			fpd.setAmount(Math.abs(accountsBalance));//应收余额
			fpd.setSourceData("对账单");
			fpd.setCostType("对账");
			fpd.setCollectionUser(frs.getCreateName());
			fpd.setSourceNo(frs.getId());
			fpd.setDepartId(Long.valueOf(String.valueOf(user.get("bussDepart"))));
			fpd.setDepartName(user.get("rightDepart")+"");
			List<FiInterfaceProDto> listfiInterfaceDto=new ArrayList<FiInterfaceProDto>();
			listfiInterfaceDto.add(fpd);
			this.fiInterface.reconciliationToFiPayment(listfiInterfaceDto);
			
			//回写欠款明细对账状态
			fiReceivabledetailService.updateStatusByreconciliationNo(Long.valueOf(creconciliationNos[i]),frs.getReconciliationStatus());
			
			//写入日志表
			StringBuffer logsql=new StringBuffer();
			logsql.append("insert into opr_history (id,opr_name,opr_node,opr_comment,opr_time,opr_man,opr_depart,d_no,opr_type) ");
			logsql.append("select Seq_Opr_History.Nextval,'对账单审核',48,'对账单号:"+Long.valueOf(creconciliationNos[i])+"',sysdate,'"+user.get("name").toString()+"','"+user.get("rightDepart").toString()+"',d_no,6 from fi_receivabledetail ");
			logsql.append("where reconciliation_no=?");
			this.oprHistoryDao.batchSQLExecute(logsql.toString(),Long.valueOf(creconciliationNos[i]));
			
		}
		
	}
	
	public boolean isRevocationReview(String reconciliationNos){
		boolean flag=true;
		String[] creconciliationNos=reconciliationNos.split(",");
		for(int i=0;i<creconciliationNos.length;i++){
			FiReceivablestatement frs=this.get(Long.valueOf(creconciliationNos[i]));
			Long status=frs.getReconciliationStatus();
			if(status<=2L){//未审核
				flag=false;
				break;
			}
		}
		       
		return flag;
	}
	
	@ModuleName(value="对账单撤销审核",logType=LogType.fi)
	public void revocationReview(String reconciliationNos,User user) throws Exception{
		String[] creconciliationNos=reconciliationNos.split(",");
		for(int i=0;i<creconciliationNos.length;i++){

			//更新对账单状态
			FiReceivablestatement frs=this.get(Long.valueOf(creconciliationNos[i]));
			//是否为已审核
			if(frs.getReconciliationStatus()<=2L)  throw new ServiceException("对账单未审核,不能再撤销审核!"); 
			
			frs.setReconciliationStatus(Long.valueOf(2));
			frs.setReviewUser(String.valueOf(user.get("name")));
			frs.setReviewDate(new Date());
			frs.setReviewDept(String.valueOf(user.get("departName")));
			this.save(frs);
			
			//将数据回写应收应付表
			List<FiPayment> fiPaymentlist=this.fiPaymentService.find("from FiPayment where sourceData='对账单' and sourceNo=?", frs.getId());
			for(FiPayment fiPayment:fiPaymentlist){
				if(fiPayment.getPaymentStatus()==2L||fiPayment.getPaymentStatus()==3L){
					throw new ServiceException("对账单已收银,不能再撤销审核!");
				}
				if(fiPayment.getPaymentStatus()==5L||fiPayment.getPaymentStatus()==6L){
					throw new ServiceException("对账单已付款,不能再撤销审核!");
				}
				if(fiPayment.getPaymentStatus()==8L){
					throw new ServiceException("对账单已登记异常,不能再撤销审核!");
				}
				if(fiPayment.getPaymentStatus()==7L){
					throw new ServiceException("对账单已转欠款,不能再撤销审核!");
				}
				fiPayment.setStatus(0L);//作废
				this.fiPaymentService.save(fiPayment);
			}
			
			//写入日志表
			StringBuffer logsql=new StringBuffer();
			logsql.append("insert into opr_history (id,opr_name,opr_node,opr_comment,opr_time,opr_man,opr_depart,d_no,opr_type) ");
			logsql.append("select Seq_Opr_History.Nextval,'对账单撤消审核',49,'对账单号:"+Long.valueOf(creconciliationNos[i])+"',sysdate,'"+user.get("name").toString()+"','"+user.get("rightDepart").toString()+"',d_no,6 from fi_receivabledetail ");
			logsql.append("where reconciliation_no=?");
			this.oprHistoryDao.batchSQLExecute(logsql.toString(),Long.valueOf(creconciliationNos[i]));
			
			//回写欠款明细对账状态
			fiReceivabledetailService.updateStatusByreconciliationNo(Long.valueOf(creconciliationNos[i]),frs.getReconciliationStatus());
		}
			
	}


	@ModuleName(value="对账单作废",logType=LogType.fi)
	public void invalid(String reconciliationNos) throws Exception {
		String[] creconciliationNos=reconciliationNos.split(",");
		for(int i=0;i<creconciliationNos.length;i++){
			//是否为已审核
			FiReceivablestatement frs=this.get(Long.valueOf(creconciliationNos[i]));
			if(frs.getReconciliationStatus()>2L)  throw new ServiceException("对账单["+frs.getId()+"]已审核,不能再作废对账单!"); 
			frs.setReconciliationStatus(Long.valueOf(0L));
			this.save(frs);
			
			//期初余额处理
			if(frs.getOpeningBalance()>0.0){
				String hql="from FiArrearset f where f.customerId=? and f.departId=?";
				List<FiArrearset> list=this.fiArrearsetService.find(hql, frs.getCustomerId(),frs.getDepartId());
				if(list.size()==1){
					FiArrearset fiArrearset=list.get(0);
					fiArrearset.setOpeningBalance(DoubleUtil.add(fiArrearset.getOpeningBalance(), frs.getOpeningBalance()));//原初期+作废对账单中包含的初期
					this.fiArrearsetService.save(fiArrearset);
				}else if(list.size()>1){
					throw new ServerException("此客商在客商欠款设置存在多条记录！");
				}
			}
			
			//回写欠款明细对账状态
			this.fiReceivabledetailService.invalid(Long.valueOf(creconciliationNos[i]));
			
			
		}
		
	}


	public Page findCusReceistatment(Page page) throws Exception {
		this.getPageBySql(page, "", new String[]{});
		return null;
	}
	
	@ModuleName(value="对账单核销",logType=LogType.buss)
	public void verificationReceistatment(Double amount,Long reconciliationNo) throws Exception{
		double accountsBalance=0.0; // 应收余额
		
		FiReceivablestatement frs=this.fiReceivablestatementDao.get(reconciliationNo);
		if(frs==null) throw new ServiceException("对账单不存在。");
		accountsBalance=frs.getAccountsBalance();
		if(accountsBalance>0){
			this.verificationReceistatmentReceivable(frs, amount, reconciliationNo);
		}else{
			this.verificationReceistatmentPayment(frs, amount, reconciliationNo);
		}
	}
	
	
	//核销收款单
	public void verificationReceistatmentReceivable(FiReceivablestatement frs,Double amount,Long reconciliationNo) throws Exception{
		double verificationAmount=0.0;//已核销金额
		double verificationAmountNo=0.0;//本次核销总金额
		double fiReceivabledetailAmount=0.0;//欠款明细金额
		
		if(frs==null) throw new ServiceException("对账单不存在。");
		//User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		Long verificationStatus=frs.getVerificationStatus();//核销状态(0:空,1:未核销,2:部分核销,3:已核销)
		double accountsBalance=frs.getAccountsBalance(); // 应收余额
		double eliminationAmount=frs.getEliminationAmount();//冲销金额
		double copeAmount=frs.getCopeAmount(); // 应付金额
		double openingBalance=frs.getOpeningBalance(); // 期初余额
		
		double thiseliminationAccounts=0.0;//本次冲销应收总额，入核数报表
		double thiseliminationCope=0.0;//本次冲销应付总额，入核数报表
		
			
		if(amount>DoubleUtil.sub(accountsBalance, frs.getEliminationAmount())){
			throw new ServiceException("核销失败，收款金额不能大于未核销金额");
		}

		if(verificationStatus==2L){
			verificationAmountNo=amount;
		}else{
			verificationAmountNo=amount-openingBalance+copeAmount;//本次核销总金额=本次核销金额-期初余额+应付金额
		}
		
		
		if(verificationStatus==0L||verificationStatus==1L||verificationStatus==2L){//从未核销
			accountsBalance=DoubleUtil.sub(accountsBalance, eliminationAmount);//应收余额=应收余额-冲销金额
			if(accountsBalance==amount){//全额核销
				verificationAmount=frs.getVerificationAmount();//对账单已核销金额
				this.fiReceivabledetailService.updateVerificationStatus(reconciliationNo);
				frs.setVerificationAmount(amount);
				frs.setVerificationStatus(DoubleUtil.add(frs.getVerificationAmount(),frs.getEliminationAmount())>=accountsBalance?3L:2L);
				
				//核销代收货款收银状态
				List<FiReceivabledetail> list=this.fiReceivabledetailService.find("from FiReceivabledetail f where f.reconciliationNo=? and f.verificationStatus=1 order by id",reconciliationNo);
				for(FiReceivabledetail fiReceivabledetail:list){
					this.verificationFiReceivabledetail(fiReceivabledetail);
				}
				if(verificationAmount==0.0){
					thiseliminationAccounts=verificationAmountNo;
					thiseliminationCope=copeAmount;
				}else{
					thiseliminationAccounts=amount;
					thiseliminationCope=0.0;
				}
				

			}else{
				//部分核销
				List<FiReceivabledetail> list=this.fiReceivabledetailService.find("from FiReceivabledetail f where f.reconciliationNo=? and f.verificationStatus=1 order by id",reconciliationNo);
				for(FiReceivabledetail fiReceivabledetail:list){
					if(fiReceivabledetail.getPaymentType()==1L){//收款单
						if(verificationAmountNo<=0.0){
							continue;
						}
						fiReceivabledetailAmount=DoubleUtil.sub(fiReceivabledetail.getAmount(),fiReceivabledetail.getVerificationAmount());
						fiReceivabledetailAmount=DoubleUtil.sub(fiReceivabledetailAmount, fiReceivabledetail.getProblemAmount());
						//核销剩于总金额>=本次核销金额
						if(verificationAmountNo>=fiReceivabledetailAmount){
							verificationAmount=fiReceivabledetailAmount;
						}else{
							verificationAmount=verificationAmountNo;
						}
						fiReceivabledetail.setVerificationAmount(DoubleUtil.add(fiReceivabledetail.getVerificationAmount(),verificationAmount));
						//已核销金额+已冲销金额>=应收金额
						if(DoubleUtil.add(fiReceivabledetail.getVerificationAmount(),fiReceivabledetail.getEliminationAmount())>=fiReceivabledetail.getAmount()){
							fiReceivabledetail.setVerificationStatus(3L);//已核销
						}else{
							fiReceivabledetail.setVerificationStatus(1L);//未核销
						}
						fiReceivabledetail.setVerificationTime(new Date());

						this.fiReceivabledetailService.save(fiReceivabledetail);
						verificationAmountNo=DoubleUtil.sub(verificationAmountNo, verificationAmount);
						thiseliminationAccounts=DoubleUtil.add(thiseliminationAccounts, verificationAmount);//本次冲销应收总额
					}else{
						fiReceivabledetail.setVerificationAmount(DoubleUtil.sub(fiReceivabledetail.getAmount(),fiReceivabledetail.getProblemAmount()));
						//已核销金额+已冲销金额>=应收金额
						if(DoubleUtil.add(fiReceivabledetail.getVerificationAmount(),fiReceivabledetail.getEliminationAmount())>=fiReceivabledetail.getAmount()){
							fiReceivabledetail.setVerificationStatus(3L);//已核销
						}else{
							fiReceivabledetail.setVerificationStatus(1L);//未核销
						}
						fiReceivabledetail.setVerificationTime(new Date());
						this.fiReceivabledetailService.save(fiReceivabledetail);
						verificationAmount=fiReceivabledetail.getVerificationAmount();
						thiseliminationCope=DoubleUtil.add(thiseliminationCope, verificationAmount);//本次冲销应付总额
					}

					//核销往明细数据
					this.verificationFiReceivabledetail(fiReceivabledetail);
					//操作日志
					oprHistoryService.saveFiLog(fiReceivabledetail.getDno(), "客商往来明细ID："+fiReceivabledetail.getId()+",核销金额："+verificationAmount,37L);
				}
				frs.setVerificationAmount(DoubleUtil.add(frs.getVerificationAmount(),amount));
				frs.setVerificationStatus(DoubleUtil.add(frs.getVerificationAmount(),frs.getEliminationAmount())>=accountsBalance?3L:2L);
			}
		}else if(verificationStatus==3L){
			throw new ServiceException("对账单已核销");
		}else{
			throw new ServerException("对账单核销状态不存在");
		}
		this.fiReceivablestatementDao.save(frs);
		//入核算数据
		FiRepayment fiRepayment=new FiRepayment();
		fiRepayment.setAccountData(new Date());
		fiRepayment.setCustomerId(frs.getCustomerId());
		fiRepayment.setCustomerName(frs.getCustomerName());
		fiRepayment.setAccountsBalance(amount);//本次还款金额
		fiRepayment.setEliminationAccounts(thiseliminationAccounts);//应收
		fiRepayment.setEliminationCope(thiseliminationCope);//应付
		fiRepayment.setSourceData("对账单");
		fiRepayment.setSourceNo(frs.getId());
		this.fiRepaymentService.save(fiRepayment);
		
	}
	
	//核销付款单
	private void verificationReceistatmentPayment(FiReceivablestatement frs,Double amount,Long reconciliationNo) throws Exception{
		double verificationAmount=0.0;//已核销金额
		double verificationAmountNo=0.0;//本次核销总金额
		double fiReceivabledetailAmount=0.0;//欠款明细金额
		
		if(frs==null) throw new ServiceException("对账单不存在。");
		Long verificationStatus=frs.getVerificationStatus();//核销状态(0:空,1:未核销,2:部分核销,3:已核销)
		double accountsBalance=frs.getAccountsBalance(); // 应收余额
		double eliminationAmount=frs.getEliminationAmount();//冲销金额
		double accountsAmount=frs.getAccountsAmount(); // 应收金额
		double copeAmount=frs.getCopeAmount(); // 应付金额
		double openingBalance=frs.getOpeningBalance();// 期初余额
		accountsBalance=DoubleUtil.mul(accountsBalance, -1);//负数变正数
		
		double thiseliminationAccounts=0.0;//本次冲销应收总额，入核数报表
		double thiseliminationCope=0.0;//本次冲销应付总额，入核数报表
		
		if(amount>DoubleUtil.sub(accountsBalance, frs.getEliminationAmount())){
			throw new ServiceException("核销失败，收款金额不能大于未核销金额");
		}
		
		accountsBalance=DoubleUtil.sub(accountsBalance, eliminationAmount);//应收余额=应收余额-冲销金额
		verificationAmountNo=amount-openingBalance+accountsAmount;//本次核销总金额=本次核销金额-期初余额+ 应收金额
		
		if(verificationStatus==0L||verificationStatus==1L||verificationStatus==2L){//从未核销
			if(accountsBalance==amount){//全额核销
				verificationAmount=frs.getVerificationAmount();
				this.fiReceivabledetailService.updateVerificationStatus(reconciliationNo);
				frs.setVerificationAmount(amount);
				frs.setVerificationStatus(DoubleUtil.add(frs.getVerificationAmount(),frs.getEliminationAmount())>=accountsBalance?3L:2L);
				
				//核销代收货款收银状态
				List<FiReceivabledetail> list=this.fiReceivabledetailService.find("from FiReceivabledetail f where f.reconciliationNo=? and f.verificationStatus=1 order by id",reconciliationNo);
				for(FiReceivabledetail fiReceivabledetail:list){
					this.verificationFiReceivabledetail(fiReceivabledetail);
				}
				
				if(verificationAmount==0.0){
					thiseliminationAccounts=accountsAmount;//收
					thiseliminationCope=verificationAmountNo;//付
				}else{
					thiseliminationAccounts=0.0;
					thiseliminationCope=amount;
				}
				
			}else{
				//部分核销
				List<FiReceivabledetail> list=this.fiReceivabledetailService.find("from FiReceivabledetail f where f.reconciliationNo=? and f.verificationStatus=1 order by id",reconciliationNo);
				for(FiReceivabledetail fiReceivabledetail:list){
					if(fiReceivabledetail.getPaymentType()==1L){//收款单
						fiReceivabledetail.setVerificationAmount(DoubleUtil.sub(fiReceivabledetail.getAmount(),fiReceivabledetail.getProblemAmount()));
						//已核销金额+已冲销金额>=应收金额
						if(DoubleUtil.add(fiReceivabledetail.getVerificationAmount(),fiReceivabledetail.getEliminationAmount())>=fiReceivabledetail.getAmount()){
							fiReceivabledetail.setVerificationStatus(3L);//已核销
						}else{
							fiReceivabledetail.setVerificationStatus(1L);//未核销
						}
						fiReceivabledetail.setVerificationTime(new Date());
						this.fiReceivabledetailService.save(fiReceivabledetail);
						verificationAmount=fiReceivabledetail.getVerificationAmount();
						thiseliminationAccounts=DoubleUtil.add(thiseliminationAccounts, verificationAmount);
					}else{
						if(verificationAmountNo<=0.0){
							continue;
						}
						fiReceivabledetailAmount=DoubleUtil.sub(fiReceivabledetail.getAmount(),fiReceivabledetail.getVerificationAmount());
						fiReceivabledetailAmount=DoubleUtil.sub(fiReceivabledetailAmount, fiReceivabledetail.getProblemAmount());
						//核销剩于总金额>=本次核销金额
						if(verificationAmountNo>=fiReceivabledetailAmount){
							verificationAmount=fiReceivabledetailAmount;
						}else{
							verificationAmount=verificationAmountNo;
						}
						fiReceivabledetail.setVerificationAmount(DoubleUtil.add(fiReceivabledetail.getVerificationAmount(),verificationAmount));
						//已核销金额+已冲销金额>=应收金额
						if(DoubleUtil.add(fiReceivabledetail.getVerificationAmount(),fiReceivabledetail.getEliminationAmount())>=fiReceivabledetail.getAmount()){
							fiReceivabledetail.setVerificationStatus(3L);//已核销
						}else{
							fiReceivabledetail.setVerificationStatus(1L);//未核销
						}
						
						fiReceivabledetail.setVerificationTime(new Date());
						
						this.fiReceivabledetailService.save(fiReceivabledetail);
						
						verificationAmountNo=DoubleUtil.sub(verificationAmountNo, verificationAmount);
						thiseliminationCope=DoubleUtil.add(thiseliminationCope, verificationAmount);
					}
					//核销往明细数据
					this.verificationFiReceivabledetail(fiReceivabledetail);
					
					//操作日志
					oprHistoryService.saveFiLog(fiReceivabledetail.getDno(), "客商往来明细ID："+fiReceivabledetail.getId()+",核销金额："+verificationAmount,37L);
				}
				frs.setVerificationAmount(DoubleUtil.add(frs.getVerificationAmount(),amount));
				frs.setVerificationStatus(DoubleUtil.add(frs.getVerificationAmount(),frs.getEliminationAmount())>=accountsBalance?3L:2L);
			}
		}else if(verificationStatus==3L){
			throw new ServiceException("对账单已核销");
		}else{
			throw new ServerException("对账单核销状态不存在");
		}
		this.fiReceivablestatementDao.save(frs);
		
		//入核算数据
		FiRepayment fiRepayment=new FiRepayment();
		fiRepayment.setAccountData(new Date());
		fiRepayment.setCustomerId(frs.getCustomerId());
		fiRepayment.setCustomerName(frs.getCustomerName());
		fiRepayment.setAccountsBalance(amount);//本次还款金额
		fiRepayment.setEliminationAccounts(thiseliminationAccounts);//应收
		fiRepayment.setEliminationCope(thiseliminationCope);//应付
		fiRepayment.setSourceData("对账单");
		fiRepayment.setSourceNo(frs.getId());
		this.fiRepaymentService.save(fiRepayment);
	}
	
	@XbwlInt(isCheck=false)
	@ModuleName(value="对账单撤销",logType=LogType.fi)
	public  void revocationFiPaid(FiPayment fiPayment,FiPaid fiPaid)  throws Exception{
		//撤销对账单
		FiReceivablestatement fiReceivablestatement=this.fiReceivablestatementDao.get(fiPayment.getSourceNo());
		if(fiReceivablestatement==null) throw new ServiceException("对账单不存在!");
		fiReceivablestatement.setVerificationAmount(DoubleUtil.sub(fiReceivablestatement.getVerificationAmount(), fiPaid.getSettlementAmount()));
		if(fiReceivablestatement.getVerificationAmount()==0.0){
			fiReceivablestatement.setVerificationStatus(1L);
		}else if(fiReceivablestatement.getVerificationAmount()>0.0){
			fiReceivablestatement.setVerificationStatus(2L);
		}
		this.fiReceivablestatementDao.save(fiReceivablestatement);
		
		//撤销欠款往来明细
		if(fiPayment.getPaymentStatus()==1L){//全额撤销
			String sqlupdate="update Fi_Receivabledetail set verification_Status=1,verification_Amount=0 where reconciliation_No=? ";
			this.fiReceivabledetailDao.batchSQLExecute(sqlupdate,fiPayment.getSourceNo());
		}else{
			List<FiReceivabledetail> list1=this.fiReceivabledetailDao.find("from FiReceivabledetail f where f.reconciliationNo=? and f.paymentType=1 and f.verificationAmount>0 order by id desc",fiPayment.getSourceNo());
			if(list1.size()==0) throw new ServiceException("对账单对应客商欠款明细不存在!");
			Double settlementAmount=fiPaid.getSettlementAmount();//应收付金额
			for(FiReceivabledetail fiReceivabledetail:list1){
				if(settlementAmount>=fiReceivabledetail.getVerificationAmount()){
					settlementAmount=settlementAmount-fiReceivabledetail.getVerificationAmount();
					fiReceivabledetail.setVerificationAmount(0.0);
					fiReceivabledetail.setVerificationStatus(1L);
					this.fiReceivabledetailDao.save(fiReceivabledetail);
				}else if(settlementAmount<fiReceivabledetail.getVerificationAmount()){
					fiReceivabledetail.setVerificationAmount(fiReceivabledetail.getVerificationAmount()-settlementAmount);
					fiReceivabledetail.setVerificationStatus(1L);
					this.fiReceivabledetailDao.save(fiReceivabledetail);
					break;
				}
				
				//撤销应付代收货款
				if(fiReceivabledetail.getPaymentType()==1L||"代收货款".equals(fiReceivabledetail.getCostType())){//收款单且代收货款
					this.fiReceivabledetailService.revocationCollectionStatus(fiReceivabledetail);
				}
			}
		}
	}
	
	//核销往明细数据
	private void verificationFiReceivabledetail(FiReceivabledetail fiReceivabledetail) throws Exception{
		//核销代收货款收银状态
		if(fiReceivabledetail.getPaymentType()==1L&&"代收货款".equals(fiReceivabledetail.getCostType())){//收款单且代收货款
			this.fiReceivabledetailService.verificationCollectionStatus(fiReceivabledetail);
		}
		//核销传真录入表收银状态
		if(fiReceivabledetail.getPaymentType()==1L&&("代收货款".equals(fiReceivabledetail.getCostType())||"到付专车费".equals(fiReceivabledetail.getCostType())||"到付增值费".equals(fiReceivabledetail.getCostType())||"到付提送费".equals(fiReceivabledetail.getCostType()))){
			this.oprStatusService.verificationCashStatusByFiReceivabledetail(fiReceivabledetail.getDno());
		}
		
	}


	//导出Excel 需要模版（特殊表头）
	public void exporterExcel(Long id) throws Exception   {
		ServletOutputStream os=null;
		InputStream myxls=null;
		try {
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
		String filename = new String("客商对账明细.xls".getBytes("GBK"),"ISO-8859-1");
		response.setHeader("Content-Disposition", "attachment; filename=" + filename + ";");
		
		String path=request.getSession().getServletContext().getRealPath(""); 
		myxls = new FileInputStream(new StringBuffer(path).append(File.separator)
						.append("excel_template").append( File.separator)
						.append("excle_tem.xls").toString());
		HSSFWorkbook wb     = new HSSFWorkbook(myxls);
		HSSFSheet sheet = wb.getSheetAt(0);       // 第一个工作表
	
		User user= WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		Long bussDepartId=Long.parseLong(user.get("bussDepart")+"");
	//	List<FiReconAccountBean> list = new ArrayList<FiReconAccountBean>();
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
		FiReceivablestatement fiReceivablestatement=fiReceivablestatementService.get(id);
		Customer customer=customerService.get(fiReceivablestatement.getCustomerId());
		
		List<FiReconciliationAccount>listFr =fiReconciliationAccountService.find("from FiReconciliationAccount fc where fc.departId=? and fc.isDelete=1 ",bussDepartId);
		FiReconciliationAccount firAccount=null;
		if(listFr.size()==0){
			throw new ServiceException("请设置对账打印账号");
		}else{
				firAccount=listFr.get(listFr.size()-1);
		}
	
		if(customer==null){
			throw new ServiceException("对账客商已不存在！");
		}
		
		StringBuffer sd= new StringBuffer();
		sd.append(firAccount.getNature()).append("-").append(customer.getCusName()).append("对账单(").
		append(simpleDateFormat.format(fiReceivablestatement.getStateDate())).append("至").
		append(simpleDateFormat.format(fiReceivablestatement.getEndDate())).append(")");
	
		StringBuffer sb=fiReceivabledetailService.getAllReceivabledetailSql();
		Map map=new HashMap<String , Object>();
		map.put("fid", id);
		List listMap =fiReceivabledetailService.createSQLMapQuery(sb.toString(), map).list();

		HSSFRow row     = sheet.getRow(0);        // 第一行
		HSSFCell cell   = row.getCell(0); // 第一个单元格
		cell.setCellValue(sd.toString());  // 写入表头
		
		HSSFRow row2     = sheet.getRow(1);        //
		HSSFCell cell2   = row2.getCell(1); // 
		cell2.setCellValue(customer.getLinkman1());  // 写入代理联系人

		HSSFCell cell25   = row2.getCell(5); // 
		cell25.setCellValue(customer.getPhone1());
		
		HSSFCell cell29   = row2.getCell(10); // 
		cell29.setCellValue(customer.getLegalbody());  //负责人
		
		Long totalPiece=0l;
		double  totalWeight=0.0;
		double totalPayment=0.0;
		double totalCpFee=0.0;
		double totalBulk=0.0;
		double  totalCpValueAddFee=0.0; 
		
		insertRow(wb, sheet, 2, listMap.size());
		
		for(int i=0;i<listMap.size();i++){
			Map  iMap=(Map)listMap.get(i);

			HSSFRow rowc =sheet.getRow(3+i);
			HSSFCell cellh=rowc.getCell(0);
			cellh.setCellValue(iMap.get("CPNAME")+"");
			
			HSSFCell cellh2=rowc.getCell(1);
			cellh2.setCellValue(iMap.get("FLIGHTMAINNO")+"");
			
			HSSFCell cellh3=rowc.getCell(2);
			cellh3.setCellValue(iMap.get("SUBNO")==null?" ":iMap.get("SUBNO")+"");
			
			HSSFCell cellh4=rowc.getCell(3);
			cellh4.setCellValue(Long.valueOf(iMap.get("DNO")+""));
			
			long piece=Long.valueOf(iMap.get("PIECE")==null?"0":iMap.get("PIECE")+"");
			HSSFCell cellh5=rowc.getCell(4);
			cellh5.setCellValue(piece);
			totalPiece=totalPiece+piece;
			
			double weight=Double.valueOf(iMap.get("CUSWEIGHT")==null?"0":iMap.get("CUSWEIGHT")+"");
			HSSFCell cellh6=rowc.getCell(5);
			cellh6.setCellValue(weight);
			totalWeight=DoubleUtil.add(weight, totalWeight);
			
			
			double bulk=Double.valueOf(iMap.get("BULK")==null?"0":iMap.get("BULK")+"");
			HSSFCell cellh7=rowc.getCell(6);
			cellh7.setCellValue(bulk);
			totalBulk=DoubleUtil.add(bulk, totalBulk);
			
			double paymentAmount=Double.valueOf(iMap.get("PAYMENTAMOUNT")==null?"0":iMap.get("PAYMENTAMOUNT")+"");
			HSSFCell cellh8=rowc.getCell(7);
			cellh8.setCellValue(paymentAmount);
			totalPayment=DoubleUtil.add(totalPayment, paymentAmount);
			
			double cpFee=Double.valueOf(iMap.get("CPFEE")==null?"0":iMap.get("CPFEE")+"");
			HSSFCell cellh9=rowc.getCell(8);
			cellh9.setCellValue(cpFee);
			totalCpFee=DoubleUtil.add(totalCpFee, cpFee);
			
			double cpValueAddFee=Double.valueOf(iMap.get("CPVALUEADDFEE")==null?"0":iMap.get("CPVALUEADDFEE")+"");
			HSSFCell cellh10=rowc.getCell(9);
			cellh10.setCellValue(cpValueAddFee);
			totalCpValueAddFee=DoubleUtil.add(totalCpValueAddFee,cpValueAddFee);
			
			HSSFCell cellh11=rowc.getCell(10);
			cellh11.setCellValue(iMap.get("CONSIGNEE")+"/"+iMap.get("ADDR")+"");
			
			Date date =(Date)iMap.get("CREATETIME");
			HSSFCell cellh12=rowc.getCell(11);
			cellh12.setCellValue(simpleDateFormat.format(date));
		}

		int size=listMap.size();
		HSSFRow row5     = sheet.getRow(5+size);        //
		HSSFCell cell55   = row5.getCell(4); // 
		cell55.setCellValue(totalPiece);
		HSSFCell cell56   = row5.getCell(5); // 
		cell56.setCellValue(totalWeight);
		HSSFCell cell57   = row5.getCell(6); // 
		cell57.setCellValue(totalBulk);
		
		HSSFCell cell58   = row5.getCell(7); // 
		cell58.setCellValue(totalPayment);
		HSSFCell cell59   = row5.getCell(8); // 
		cell59.setCellValue(totalCpFee);
		HSSFCell cell510   = row5.getCell(9); // 
		cell510.setCellValue(totalCpValueAddFee);
		HSSFCell cell512   = row5.getCell(11); // 
		cell512.setCellValue(DoubleUtil.sub(DoubleUtil.add(totalCpFee, totalCpValueAddFee),totalPayment));
		
		HSSFRow row9     = sheet.getRow(8+size);        // 户名
		HSSFCell cell92   = row9.getCell(1); // 
		cell92.setCellValue(firAccount.getAccountName()==null?"":firAccount.getAccountName());
		
		HSSFCell cell97   = row9.getCell(8); // 
		cell97.setCellValue(firAccount.getAccountName2()==null?"":firAccount.getAccountName2());  
		
		HSSFRow row10     = sheet.getRow(9+size);        // 账号
		HSSFCell cell102   = row10.getCell(1); // 
		cell102.setCellValue(firAccount.getAccountNum()==null?"":firAccount.getAccountNum());
		
		HSSFCell cell107   = row10.getCell(8); // 
		cell107.setCellValue(firAccount.getAccountNum2()==null?"":firAccount.getAccountNum2());
		
		HSSFRow row11     = sheet.getRow(10+size);        // 开户行
		HSSFCell cell112   = row11.getCell(1); // 
		cell112.setCellValue(firAccount.getBank()==null?"":firAccount.getBank());
		
		HSSFCell cell117   = row11.getCell(8); // 
		cell117.setCellValue(firAccount.getBank2()==null?"":firAccount.getBank2());
		
		HSSFRow row12     = sheet.getRow(12+size);
		HSSFRow row13     = sheet.getRow(13+size);        // 对账号
		HSSFRow row14     = sheet.getRow(14+size);        // 联系方式
		HSSFCell cell132   = row13.getCell(1); // 
		HSSFCell cell122= row12.getCell(1);
		cell122.setCellValue(id);
		cell132.setCellValue(fiReceivablestatement.getReconciliationUser()==null?"":fiReceivablestatement.getReconciliationUser());
		HSSFCell cell142   = row14.getCell(1); // 
		cell142.setCellValue(firAccount.getPhone()==null?"":firAccount.getPhone());
		
		os = response.getOutputStream();
		wb.write(os);
		} catch (Exception e) {
			e.getStackTrace();
			throw e;
		}finally{
			if(os!=null){
				try {
					os.close();
					os=null;
				} catch (IOException e) {
					e.printStackTrace();
					throw e;
				}finally{
					os=null;
				}
			}
			if(myxls!=null){
				try {
					myxls.close();
				} catch (IOException e) {
					e.printStackTrace();
					throw e;
				}finally{
					myxls=null;
				}
			}
		}
	}

	public void insertRow(HSSFWorkbook wb, HSSFSheet sheet, int starRow,
			int rows) throws IOException {
		// 选择一个区域，从startRow+1直到最后一行
		sheet.shiftRows(starRow + 1, sheet.getLastRowNum(), rows, true, false);
		starRow = starRow - 1;
		
		short num=1;
		HSSFFont font = wb.createFont();   
		font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);     //正常字体
		HSSFCellStyle cellStyle= wb.createCellStyle();      //创建样式
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); //居中
		cellStyle.setFont(font);                         //创建 和设置Excle 样式
		cellStyle.setBorderBottom(num);                               //边框
		cellStyle.setBorderLeft(num);
		cellStyle.setBorderRight(num);
		cellStyle.setBorderTop(num);
		
		for (int i = 0; i < rows; i++) {
			HSSFRow sourceRow = null;
			HSSFRow targetRow = null;
			HSSFCell sourceCell = null;
			HSSFCell targetCell = null;
			int m;
			starRow = starRow + 1;
			sourceRow = sheet.getRow(starRow);
			if (sourceRow == null) {
				sourceRow = sheet.createRow(starRow);
			}
			// 從start創建新的一行
			targetRow = sheet.createRow(starRow + 1);
			targetRow.setHeight(sourceRow.getHeight());

			// 处理刚刚创建的一行
			for (m = sourceRow.getFirstCellNum(); m < sourceRow
					.getLastCellNum(); m++) {
				sourceCell = sourceRow.getCell(m);
				targetCell = targetRow.createCell(m);

				// 风格一样
//				HSSFCellStyle  fCellStyle=sourceCell.getCellStyle();    跟创建的那行前面一行风格一样
				targetCell.setCellStyle(cellStyle);
				targetCell.setCellType(sourceCell.getCellType());
				targetCell.setCellValue("");// 設置值
			}
		}
	}
}
