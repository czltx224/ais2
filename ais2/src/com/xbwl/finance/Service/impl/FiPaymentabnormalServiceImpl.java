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
	
	//日志
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
		
		//回写收款单异常到付款字段
		fp.setAbnormalAmount(fiPaymentabnormal.getAmount());
		this.fiPaymentDao.save(fp);
		
		//操作日志
		oprHistoryService.saveFiLog(fp.getDocumentsNo(), "异常到付款ID："+fiPaymentabnormal.getId()+",登记金额:"+fiPaymentabnormal.getAmount()+",异常备注："+fiPaymentabnormal.getRemark() ,59L);
		return "true";
	}

	public void revocationException(FiPaymentabnormal fiPaymentabnormal) throws Exception{
		if(fiPaymentabnormal.getStatus()!=1L){
			if(fiPaymentabnormal.getStatus()==0L){
				throw new ServiceException("撤销失败，当前异常到付款已作废！");
			}else if(fiPaymentabnormal.getStatus()==2L){
				throw new ServiceException("撤销失败，当前异常到付款已审核！");
			}else{
				throw new ServiceException("异常到付款状态不存在，请联系系统管理员！");
			}
		}

		fiPaymentabnormal.setStatus(Long.valueOf(0));
		this.fiPaymentabnormalDao.save(fiPaymentabnormal);
		
		//撤销应收款单异常到付款金额
		FiPayment fp = this.fiPaymentDao.get(fiPaymentabnormal.getFiPaymentId());
		fp.setAbnormalAmount(0.0);
		this.fiPaymentDao.save(fp);
		
		//操作日志
		oprHistoryService.saveFiLog(fp.getDocumentsNo(), "异常到付款ID:"+ fiPaymentabnormal.getId()+",流程号:"+fiPaymentabnormal.getWorkflowNo(),60L);
	}
	
	public void verificationException(FiPaymentabnormal fiPaymentabnormal) throws Exception{
		if(fiPaymentabnormal.getStatus()==0L){
			throw new ServiceException("操作失败，当前单据已作废！");
		}else if(fiPaymentabnormal.getStatus()==2L){
			throw new ServiceException("操作失败，当前单据已审核！");
		}else if(fiPaymentabnormal.getStatus()==1L){
			User user= WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
			FiPayment fp = this.fiPaymentDao.get(fiPaymentabnormal.getFiPaymentId());
			if(fp==null) throw new ServiceException("异常到付款对应的应收单号不存在，不允许审核!");
			double eliminationAmount=fiPaymentabnormal.getAmount();
			double settlementAmount=DoubleUtil.add(fp.getSettlementAmount(),eliminationAmount);
			
			//问题账款状态
			fiPaymentabnormal.setStatus(2L);
			
			//将数据写入应收应付表
			FiInterfaceProDto fpd=new FiInterfaceProDto();
			List<FiInterfaceProDto> listfiInterfaceDto=new ArrayList<FiInterfaceProDto>();
			fpd.setAmount(fiPaymentabnormal.getAmount());
			fpd.setDocumentsType("异常到付款");
			fpd.setDocumentsSmalltype("配送单");
			fpd.setDocumentsNo(fp.getDocumentsNo());
			fpd.setCustomerId(fp.getCustomerId());
			fpd.setCustomerName(fp.getCustomerName());
			fpd.setCostType("异常到付款");
			fpd.setSourceData("异常到付款");
			fpd.setSourceNo(fiPaymentabnormal.getId());
			fpd.setSettlementType(2L);//付款单
			fpd.setContacts(fp.getContacts());
			fpd.setDepartId(Long.valueOf(user.get("bussDepart")+""));
			fpd.setDepartName(user.get("rightDepart")+"");
			listfiInterfaceDto.add(fpd);
			
			//冲销金额
			fp.setWorkflowNo(fiPaymentabnormal.getWorkflowNo());
			/*if (fp.getAmount()<=settlementAmount) {
				fp.setPaymentStatus(2L);// 已收款
			}else if(settlementAmount==0.0) {
				fp.setPaymentStatus(1L);// 未收款
			}else {
				fp.setPaymentStatus(3L);// 部分收款
			}
			this.fiPaymentDao.save(fp);*/
			
			this.fiPaymentabnormalDao.save(fiPaymentabnormal);//异常到付款表
			this.fiInterface.currentToFiPayment(listfiInterfaceDto);//应付款单表
			
			//操作日志
			oprHistoryService.saveFiLog(fp.getDocumentsNo(), "问题账款ID："+fiPaymentabnormal.getId()+",审核金额:"+fiPaymentabnormal.getAmount(),61L);
			
		}else{
			throw new ServiceException("操作失败，当前单据状态不存在！");
		}
	}
	
	public void verificationRegister(FiPaymentabnormal fiPaymentabnormal) throws Exception{
		if(fiPaymentabnormal.getVerificationStatus()==1L){
			fiPaymentabnormal.setStatus(1L);
			this.fiPaymentabnormalDao.save(fiPaymentabnormal);//异常到付款表
			
			FiInterfaceProDto fpd=new FiInterfaceProDto();
			fpd.setSourceData("异常到付款");
			fpd.setSourceNo(fiPaymentabnormal.getId());
			this.fiInterface.invalidToFiPayment(fpd);
			
			FiPayment fp = this.fiPaymentDao.get(fiPaymentabnormal.getFiPaymentId());
			//double eliminationAmount=DoubleUtil.sub(fp.getEliminationAmount(), fiPaymentabnormal.getAmount());
			//fp.setEliminationAmount(eliminationAmount);
			
			if(fp.getPaymentStatus()==2L){
				throw new ServiceException("收款单已收银，不允许再撤销审核");
			}
			/*double settlementAmount=DoubleUtil.add(fp.getSettlementAmount(),eliminationAmount);
			if (fp.getAmount()<=settlementAmount) {
				fp.setPaymentStatus(2L);// 已收款
			}else if(settlementAmount==0.0) {
				fp.setPaymentStatus(1L);// 未收款
			}else {
				fp.setPaymentStatus(3L);// 部分收款
			}*/
			
			//操作日志
			oprHistoryService.saveFiLog(fp.getDocumentsNo(), "问题账款ID："+fiPaymentabnormal.getId(),62L);
			
		}else if(fiPaymentabnormal.getVerificationStatus()==2L){
			throw new ServiceException("操作失败，当前单据已核销,不允许再撤销审核！");
		}else{
			throw new ServiceException("操作失败，当前单据核销状态不存在,请联系系统管理员！");
		}
	}

	@XbwlInt(isCheck=false)
	public void verfiPaymentabnormal(FiPayment fiPayment) throws Exception{
		FiPaymentabnormal fiPaymentabnormal=this.fiPaymentabnormalDao.get(fiPayment.getSourceNo());
		if(fiPaymentabnormal==null) throw new ServiceException("异常到付款未找到，无法操作");
		fiPaymentabnormal.setVerificationTime(new Date());
		fiPaymentabnormal.setVerificationAmount(DoubleUtil.add(fiPaymentabnormal.getVerificationAmount(),fiPayment.getAmount()));
		if(fiPaymentabnormal.getAmount().equals(fiPaymentabnormal.getVerificationAmount())){
			fiPaymentabnormal.setVerificationStatus(2L);
		}
		this.fiPaymentabnormalDao.save(fiPaymentabnormal);
		
	}
	
	public void verfiPaymentabnormalRegister(Double amount,Long fiPaymentabnormalId) throws Exception{
	FiPaymentabnormal fiPaymentabnormal=this.fiPaymentabnormalDao.get(fiPaymentabnormalId);
	if(fiPaymentabnormal==null) throw new ServiceException("异常到付款未找到，无法操作");
	fiPaymentabnormal.setVerificationTime(null);
	fiPaymentabnormal.setVerificationAmount(DoubleUtil.sub(fiPaymentabnormal.getVerificationAmount(), amount));
	fiPaymentabnormal.setVerificationStatus(1L);
	this.fiPaymentabnormalDao.save(fiPaymentabnormal);
	
}

}
