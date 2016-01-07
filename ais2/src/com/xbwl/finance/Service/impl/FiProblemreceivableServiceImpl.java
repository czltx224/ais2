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
	
	//对账单
	@Resource(name = "fiReceivablestatementServiceImpl")
	private IFiReceivablestatementService fiReceivablestatementService;
	
	//日志
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
				throw new ServiceException("撤销失败，当前问题账款已作废！");
			}else if(fiProblemreceivable.getStatus()==2L){
				throw new ServiceException("撤销失败，当前问题账款已审核！");
			}else{
				throw new ServiceException("问题账款状态不存在，请联系系统管理员！");
			}
		}

		//问题账款记录表状态更新为已取消 
		fiProblemreceivable.setStatus(Long.valueOf(0));
		this.fiProblemreceivableDao.save(fiProblemreceivable);
		
		//更新供应商往来明细问题账款相关信息
		FiReceivabledetail fiReceivabledetail=this.fiReceivabledetailDao.get(fiProblemreceivable.getSourceNo());
		fiReceivabledetail.setProblemStatus(Long.valueOf(0));
		this.fiReceivabledetailDao.save(fiReceivabledetail);
		
		//更新对账单的问题账款金额与应收金额(整票按原应收金额仍参与对账)
		FiReceivablestatement frt=this.fiReceivablestatementService.get(fiReceivabledetail.getReconciliationNo());
		//frt.setAccountsBalance(frt.getAccountsBalance()+fiProblemreceivable.getProblemAmount());// 应收余额
		frt.setProblemAmount(fiReceivabledetail.getProblemAmount()-fiProblemreceivable.getProblemAmount());  // 问题账款金额
		this.fiReceivablestatementService.save(frt);
		
		//操作日志
		oprHistoryService.saveFiLog(fiReceivabledetail.getDno(), "对账单号："+frt.getId()+",客商往来明细单号："+fiReceivabledetail.getId()+",撤销问题账款金额："+fiProblemreceivable.getProblemAmount() ,54L);
	}
	
	@ModuleName(value="问题账款审核",logType=LogType.fi)
	public void audit(FiProblemreceivable fiProblemreceivable) throws Exception{
		if(fiProblemreceivable.getStatus()==0L){
			throw new ServiceException("操作失败，当前单据已作废！");
		}else if(fiProblemreceivable.getStatus()==2L){
			throw new ServiceException("操作失败，当前单据已审核！");
		}else if(fiProblemreceivable.getStatus()==1L){
			User user= WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
			//问题账款状态
			fiProblemreceivable.setStatus(2L);
			fiProblemreceivable.setVerRemark(fiProblemreceivable.getVerRemark());
			
			//更新供应商往来明细问题账款相关信息
			FiReceivabledetail fiReceivabledetail=this.fiReceivabledetailDao.get(fiProblemreceivable.getSourceNo());
			if(fiReceivabledetail==null)  throw new ServiceException("操作失败，问题账款对应的客商往来明细记录不存在！");
			fiReceivabledetail.setProblemStatus(Long.valueOf(0));
			fiReceivabledetail.setProblemAmount(DoubleUtil.add(fiReceivabledetail.getProblemAmount(),fiProblemreceivable.getProblemAmount()));
			this.fiReceivabledetailDao.save(fiReceivabledetail);
			
			//更新对账单的问题账款金额与应收金额(整票按原应收金额仍参与对账)
			FiReceivablestatement frt=this.fiReceivablestatementService.get(fiReceivabledetail.getReconciliationNo());
			if(frt==null)  throw new ServiceException("操作失败，问题账款对应的对账单记录不存在！");
			frt.setProblemAmount(fiProblemreceivable.getProblemAmount());  // 问题账款金额
			this.fiReceivablestatementService.save(frt);
			
			
			//将数据写入应收应付表
			FiInterfaceProDto fpd=new FiInterfaceProDto();
			List<FiInterfaceProDto> listfiInterfaceDto=new ArrayList<FiInterfaceProDto>();
			fpd.setAmount(fiProblemreceivable.getProblemAmount());
			fpd.setDocumentsType("问题账款");
			fpd.setDocumentsSmalltype("配送单");
			fpd.setDocumentsNo(fiProblemreceivable.getDno());
			fpd.setCustomerId(fiProblemreceivable.getCustomerId());
			fpd.setCustomerName(fiProblemreceivable.getCustomerName());
			fpd.setCostType("问题账款");
			fpd.setSourceData("问题账款");
			fpd.setSourceNo(fiProblemreceivable.getId());
			fpd.setDno(fiProblemreceivable.getDno());
			fpd.setSettlementType(2L);//付款单
			fpd.setDepartId(Long.valueOf(user.get("bussDepart")+""));
			fpd.setDepartName(user.get("rightDepart")+"");
			listfiInterfaceDto.add(fpd);
			
			this.fiProblemreceivableDao.save(fiProblemreceivable);//问题账款表
			this.fiInterface.currentToFiPayment(listfiInterfaceDto);//应付款单表
			
			//操作日志
			oprHistoryService.saveFiLog(fiReceivabledetail.getDno(), "问题账款ID："+fiProblemreceivable.getId()+",审核问题账款金额："+fiProblemreceivable.getProblemAmount() ,55L);
			
		}else{
			throw new ServiceException("操作失败，当前单据状态不存在！");
		}
	}
	
	public void problemreceivableRegister(FiProblemreceivable fiProblemreceivable) throws Exception{
		if(fiProblemreceivable.getVerificationStatus()==1L){
			//问题账款状态
			fiProblemreceivable.setStatus(1L);
			this.fiProblemreceivableDao.save(fiProblemreceivable);//问题账款表
			
			FiInterfaceProDto fpd=new FiInterfaceProDto();
			fpd.setSourceData("问题账款");
			fpd.setSourceNo(fiProblemreceivable.getId());
			this.fiInterface.invalidToFiPayment(fpd);
			
			//操作日志
			oprHistoryService.saveFiLog(fiProblemreceivable.getDno(), "问题账款ID："+fiProblemreceivable.getId()+",撤销金额："+fiProblemreceivable.getProblemAmount() ,55L);
		}else if(fiProblemreceivable.getVerificationStatus()==2L||fiProblemreceivable.getVerificationStatus()==3L){
			throw new ServiceException("操作失败，当前单据已核销,不允许再撤销审核！");
		}else{
			throw new ServiceException("操作失败，当前单据核销状态不存在,请联系系统管理员！");
		}
	}
	
	@ModuleName(value="问题账款核销",logType=LogType.fi)
	public void verfiProblemreceivable(Double amount,Long fiProblemreceivableId) throws Exception{
		Double verificationAmountNo=0.0;//未核销金额
		Double problemAmount=0.0; // 问题账款金额
		Double verificationAmount=0.0; //核销金额
		
		FiProblemreceivable fiProblemreceivable=this.fiProblemreceivableDao.get(fiProblemreceivableId);
		if(fiProblemreceivable==null) throw new ServiceException("问题账款记录不存在!");
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
		
		//更新供应商往来明细问题账款相关信息
		FiReceivabledetail fiReceivabledetail=this.fiReceivabledetailDao.get(fiProblemreceivable.getSourceNo());
		if(fiReceivabledetail==null)  throw new ServiceException("操作失败，问题账款对应的客商往来明细记录不存在！");
		fiReceivabledetail.setEliminationAmount(fiProblemreceivable.getVerificationAmount());
		//已核销金额+已冲销金额>=应收金额
		if(DoubleUtil.add(fiReceivabledetail.getVerificationAmount(),fiReceivabledetail.getEliminationAmount())>=fiReceivabledetail.getAmount()){
			fiReceivabledetail.setVerificationStatus(3L);//已核销
		}else{
			fiReceivabledetail.setVerificationStatus(1L);//未核销
		}
		this.fiReceivabledetailDao.save(fiReceivabledetail);
		
		//更新对账单的问题账款金额与应收金额(整票按原应收金额仍参与对账)
		FiReceivablestatement frt=this.fiReceivablestatementService.get(fiReceivabledetail.getReconciliationNo());
		if(frt==null)  throw new ServiceException("操作失败，问题账款对应的对账单记录不存在！");
		frt.setEliminationAmount(fiProblemreceivable.getVerificationAmount());  // 问题账款金额
		this.fiReceivablestatementService.save(frt);
		
	}
	
	@ModuleName(value="问题账款撤销核销",logType=LogType.fi)
	public void verfiProblemreceivableRegister(Double amount,Long fiProblemreceivableId) throws Exception{
		Double verificationAmountNo=0.0;//未核销金额
		Double verificationAmount=0.0; //核销金额
		
		FiProblemreceivable fiProblemreceivable=this.fiProblemreceivableDao.get(fiProblemreceivableId);
		if(fiProblemreceivable==null) throw new ServiceException("问题账款记录不存在!");
		verificationAmount=fiProblemreceivable.getVerificationAmount();
		verificationAmountNo=DoubleUtil.sub(verificationAmount,amount);
		fiProblemreceivable.setVerificationAmount(verificationAmountNo);
		fiProblemreceivable.setVerificationTime(null);
		this.fiProblemreceivableDao.save(fiProblemreceivable);
		
		//更新供应商往来明细问题账款相关信息
		FiReceivabledetail fiReceivabledetail=this.fiReceivabledetailDao.get(fiProblemreceivable.getSourceNo());
		if(fiReceivabledetail==null)  throw new ServiceException("操作失败，问题账款对应的客商往来明细记录不存在！");
		fiReceivabledetail.setEliminationAmount(DoubleUtil.sub(fiReceivabledetail.getEliminationAmount(),amount));
		//已核销金额+已冲销金额>=应收金额
		if(DoubleUtil.add(fiReceivabledetail.getVerificationAmount(),fiReceivabledetail.getEliminationAmount())>=fiReceivabledetail.getAmount()){
			fiReceivabledetail.setVerificationStatus(3L);//已核销
		}else{
			fiReceivabledetail.setVerificationStatus(1L);//未核销
		}
		this.fiReceivabledetailDao.save(fiReceivabledetail);
		
		//更新对账单的问题账款金额与应收金额(整票按原应收金额仍参与对账)
		FiReceivablestatement frt=this.fiReceivablestatementService.get(fiReceivabledetail.getReconciliationNo());
		if(frt==null)  throw new ServiceException("操作失败，问题账款对应的对账单记录不存在！");
		frt.setEliminationAmount(DoubleUtil.sub(frt.getEliminationAmount(), amount));  // 问题账款金额
		this.fiReceivablestatementService.save(frt);
		
		//操作日志
		oprHistoryService.saveFiLog(fiProblemreceivable.getDno(), "问题账款ID："+fiProblemreceivable.getId()+",核销金额："+amount ,57L);
	}

}
