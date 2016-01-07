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
	
	//资金账号设置
	@Resource(name = "fiCapitaaccountsetServiceImpl")
	private IFiCapitaaccountsetService fiCapitaaccountsetService;
	
	//资金账号流水
	@Resource(name="fiCapitaaccountServiceImpl")
	private IFiCapitaaccountService fiCapitaaccountService;
	
	//实收实付
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
		//拨付账号余额减少
		FiCapitaaccountset fiCapitaaccountsetPayment=this.fiCapitaaccountsetService.get(fiFundstransfer.getPaymentaccountId());//资金交接单账号
		if(fiCapitaaccountsetPayment==null) throw new ServiceException("资金交接单付款账号不存在!");
		fiCapitaaccountsetPayment.setOpeningBalance(DoubleUtil.sub(fiCapitaaccountsetPayment.getOpeningBalance(),fiFundstransfer.getAmount()));
		if(fiCapitaaccountsetPayment.getOpeningBalance()<0.0){
			throw new ServiceException("资金交接单付款账号余额不足！");
		}
		this.fiCapitaaccountsetService.save(fiCapitaaccountsetPayment);
		//资金流水处理
		FiCapitaaccount fiCapitaaccountPayment = new FiCapitaaccount();
		fiCapitaaccountPayment.setLoan(fiFundstransfer.getAmount());
		fiCapitaaccountPayment.setBalance(fiCapitaaccountsetPayment.getOpeningBalance());// 账号余额
		fiCapitaaccountPayment.setFiCapitaaccountsetId(fiCapitaaccountsetPayment.getId());
		fiCapitaaccountPayment.setSourceData(fiFundstransfer.getSourceData());
		fiCapitaaccountPayment.setRemark("资金申请单账号余额交接");
		fiCapitaaccountPayment.setSourceNo(fiFundstransfer.getSourceNo());
		this.fiCapitaaccountService.save(fiCapitaaccountPayment);// 保存流水账
		
		//申请账号余额增加
		FiCapitaaccountset fiCapitaaccountsetReceivables=this.fiCapitaaccountsetService.get(fiFundstransfer.getReceivablesaccountId());//资金交接单账号
		if(fiCapitaaccountsetReceivables==null) throw new ServiceException("资金交接单收款账号不存在!");
		fiCapitaaccountsetReceivables.setOpeningBalance(DoubleUtil.add(fiCapitaaccountsetReceivables.getOpeningBalance(),fiFundstransfer.getAmount()));
		this.fiCapitaaccountsetService.save(fiCapitaaccountsetReceivables);
		//资金流水处理
		FiCapitaaccount fiCapitaaccountReceivables = new FiCapitaaccount();
		fiCapitaaccountReceivables.setBorrow(fiFundstransfer.getAmount());
		fiCapitaaccountReceivables.setBalance(fiCapitaaccountsetReceivables.getOpeningBalance());// 账号余额
		fiCapitaaccountReceivables.setFiCapitaaccountsetId(fiCapitaaccountsetReceivables.getId());
		fiCapitaaccountReceivables.setSourceData(fiFundstransfer.getSourceData());
		fiCapitaaccountReceivables.setRemark("资金申请单账号余额交接");
		fiCapitaaccountReceivables.setSourceNo(fiFundstransfer.getSourceNo());
		this.fiCapitaaccountService.save(fiCapitaaccountReceivables);// 保存流水账
		
	}
	
	public void paymentConfirmation(Long id,User user) throws Exception{
		Long userId=Long.valueOf(user.get("id")+"");
		if(userId==null||"".equals(userId)){
			throw new ServiceException("获取用户ID失败!");
		}
		FiFundstransfer fiFundstransfer=this.fiFundstransferDao.get(id);
		if(fiFundstransfer==null){
			throw new ServiceException("交接单不存在!");
		}
		if(fiFundstransfer.getStatus()==0L){
			throw new ServiceException("交接单已作废!");
		}
		
		//拨付账号
		FiCapitaaccountset fiCapitaaccountsetPayment=this.fiCapitaaccountsetService.get(fiFundstransfer.getPaymentaccountId());
		
		//收款账号
		FiCapitaaccountset fiCapitaaccountsetReceivables=this.fiCapitaaccountsetService.get(fiFundstransfer.getReceivablesaccountId());
		
		if(fiCapitaaccountsetPayment==null){
			throw new ServiceException("资金交接单付款账号不存在!");
		}
		
		if(fiCapitaaccountsetReceivables==null){
			throw new ServiceException("资金交接单收款账号不存在!");
		}
		
		if(!userId.equals(fiCapitaaccountsetPayment.getResponsibleId())){
			throw new ServiceException("付款确认失败，只有付款人账号负责人才能付款确认!");
		}
		
		if(fiCapitaaccountsetPayment.getResponsibleId().equals(fiCapitaaccountsetReceivables.getResponsibleId())){
			throw new ServiceException("付款确认失败，付款账号负责人与收款账号负责人不能为同一人。");
		}
		
		if(fiFundstransfer.getPaymentStatus()==1L){
			throw new ServiceException("付款确认失败，当前付款状态已付款");
		}
		
		fiCapitaaccountsetPayment.setOpeningBalance(DoubleUtil.sub(fiCapitaaccountsetPayment.getOpeningBalance(),fiFundstransfer.getAmount()));
		if(fiCapitaaccountsetPayment.getOpeningBalance()<0.0){
			throw new ServiceException("资金交接单付款账号余额不足！");
		}
		this.fiCapitaaccountsetService.save(fiCapitaaccountsetPayment);
		//资金流水处理
		FiCapitaaccount fiCapitaaccountPayment = new FiCapitaaccount();
		fiCapitaaccountPayment.setBorrow(fiFundstransfer.getAmount());
		fiCapitaaccountPayment.setBalance(fiCapitaaccountsetPayment.getOpeningBalance());// 账号余额
		fiCapitaaccountPayment.setFiCapitaaccountsetId(fiCapitaaccountsetPayment.getId());
		fiCapitaaccountPayment.setSourceData("资金交接单");
		fiCapitaaccountPayment.setSourceNo(fiFundstransfer.getId());
		fiCapitaaccountPayment.setRemark("资金交接单付款确认");
		this.fiCapitaaccountService.save(fiCapitaaccountPayment);// 保存流水账
		
		//保存交单付款状态
		fiFundstransfer.setPaymentStatus(1L);
		fiFundstransfer.setPaymentTime(new Date());
		this.fiFundstransferDao.save(fiFundstransfer);
	}
	
	public void paymentRevoke(Long id,User user) throws Exception{
		Long userId=Long.valueOf(user.get("id")+"");
		if(userId==null||"".equals(userId)){
			throw new ServiceException("获取用户ID失败!");
		}
		FiFundstransfer fiFundstransfer=this.fiFundstransferDao.get(id);
		if(fiFundstransfer==null){
			throw new ServiceException("交接单不存在!");
		}
		if(fiFundstransfer.getStatus()==0L){
			throw new ServiceException("交接单已作废!");
		}
		
		if(fiFundstransfer.getPaymentStatus()==0L){
			throw new ServiceException("撤销收款确认失败，当前交接单还未付款确认");
		}
		
		if(fiFundstransfer.getReceivablesStatus()==1L){
			throw new ServiceException("撤销收款确认失败，当前收款状态已确收");
		}
		
		//拨付账号
		FiCapitaaccountset fiCapitaaccountsetPayment=this.fiCapitaaccountsetService.get(fiFundstransfer.getPaymentaccountId());
		if(fiCapitaaccountsetPayment==null){
			throw new ServiceException("资金交接单付款账号不存在!");
		}
		fiCapitaaccountsetPayment.setOpeningBalance(DoubleUtil.add(fiCapitaaccountsetPayment.getOpeningBalance(),fiFundstransfer.getAmount()));
		if(fiCapitaaccountsetPayment.getOpeningBalance()<0.0){
			throw new ServiceException("资金交接单付款账号余额不足！");
		}
		this.fiCapitaaccountsetService.save(fiCapitaaccountsetPayment);
		
		//资金流水处理
		FiCapitaaccount fiCapitaaccountPayment = new FiCapitaaccount();
		fiCapitaaccountPayment.setLoan(fiFundstransfer.getAmount());
		fiCapitaaccountPayment.setBalance(fiCapitaaccountsetPayment.getOpeningBalance());// 账号余额
		fiCapitaaccountPayment.setFiCapitaaccountsetId(fiCapitaaccountsetPayment.getId());
		fiCapitaaccountPayment.setSourceData("资金交接单");
		fiCapitaaccountPayment.setSourceNo(fiFundstransfer.getId());
		fiCapitaaccountPayment.setRemark("资金交接单撤销付款确认");
		this.fiCapitaaccountService.save(fiCapitaaccountPayment);// 保存流水账
		
		//保存交单付款状态
		fiFundstransfer.setPaymentStatus(0L);
		fiFundstransfer.setPaymentTime(new Date());
		this.fiFundstransferDao.save(fiFundstransfer);
		
	}
	
	
	public void receivablesConfirmation(Long id,User user) throws Exception{
		Long userId=Long.valueOf(user.get("id")+"");
		if(userId==null||"".equals(userId)){
			throw new ServiceException("获取用户ID失败!");
		}
		FiFundstransfer fiFundstransfer=this.fiFundstransferDao.get(id);
		if(fiFundstransfer==null){
			throw new ServiceException("交接单不存在!");
		}
		
		//收款账号
		FiCapitaaccountset fiCapitaaccountsetReceivables=this.fiCapitaaccountsetService.get(fiFundstransfer.getReceivablesaccountId());//资金交接单账号
		if(fiCapitaaccountsetReceivables==null) {
			throw new ServiceException("资金交接单收款账号不存在!");
		}
		if(!userId.equals(fiCapitaaccountsetReceivables.getResponsibleId())){
			throw new ServiceException("收款确认失败，只有收款人账号负责人才能收款确认");
		}
		if(fiFundstransfer.getPaymentStatus()!=1L){
			throw new ServiceException("收款确认失败，当前收款单还未付款确认！");
		}
		
		if(fiFundstransfer.getReceivablesStatus()==1L){
			throw new ServiceException("收款确认失败，当前收款状态已确收");
		}
		
		if(fiFundstransfer.getStatus()==0L){
			throw new ServiceException("交接单已作废!");
		}
		

		
		fiCapitaaccountsetReceivables.setOpeningBalance(DoubleUtil.add(fiCapitaaccountsetReceivables.getOpeningBalance(),fiFundstransfer.getAmount()));
		this.fiCapitaaccountsetService.save(fiCapitaaccountsetReceivables);
		//资金流水处理
		FiCapitaaccount fiCapitaaccountReceivables = new FiCapitaaccount();
		fiCapitaaccountReceivables.setLoan(fiFundstransfer.getAmount());
		fiCapitaaccountReceivables.setBalance(fiCapitaaccountsetReceivables.getOpeningBalance());// 账号余额
		fiCapitaaccountReceivables.setFiCapitaaccountsetId(fiCapitaaccountsetReceivables.getId());
		fiCapitaaccountReceivables.setSourceData(fiFundstransfer.getSourceData());
		fiCapitaaccountReceivables.setSourceData("资金交接单");
		fiCapitaaccountReceivables.setSourceNo(fiFundstransfer.getId());
		fiCapitaaccountReceivables.setRemark("资金交接单收款确认");
		this.fiCapitaaccountService.save(fiCapitaaccountReceivables);// 保存流水账
		
		//保存交单收款状态
		fiFundstransfer.setReceivablesStatus(1L);//已收款
		fiFundstransfer.setReceivablesTime(new Date());
		fiFundstransfer.setStatus(2L);//已核销
		this.fiFundstransferDao.save(fiFundstransfer);
		
		//核销数据
		this.verificationSourceData(fiFundstransfer);
	}
	
	/**
	 * 交接单收款确认核销其它模块数据
	 * @param fiFundstransfer
	 */
	private void verificationSourceData(FiFundstransfer fiFundstransfer){
		if("实收实付单".equals(fiFundstransfer.getSourceData())){
			User user=WebRalasafe.getCurrentUser(Struts2Utils.getRequest());
			this.fiPaidService.batchExecute("update FiPaid f set f.verificationAmount=f.settlementAmount,f.verificationStatus=1,f.verificationUser=?,f.verificationTime=? where f.fiFundstransferId=?", user.get("name"),new Date(),fiFundstransfer.getId());
		}
	}
	
	public void revocation(Long id) throws Exception{
		if("".equals(id)||id==null){
			throw new ServiceException("交接单ID不存在！");
		}
		FiFundstransfer fundstransfer=this.fiFundstransferDao.get(id);
		if(fundstransfer==null){
			throw new ServiceException("交接单不存在！");
		}
		if(fundstransfer.getPaymentStatus()==1L){
			throw new ServiceException("交接单号已付款确认，不能作废");
		}
		if(fundstransfer.getReceivablesStatus()==1L){
			throw new ServiceException("交接单号已付款确认，不能作废");
		}
		
		fundstransfer.setStatus(0L);
		this.fiFundstransferDao.save(fundstransfer);
		
		//核销其它模块数据
		this.revocationSourceData(fundstransfer);
	}
	
	/**
	 * 交接单收款确认核销其它模块数据
	 * @param fiFundstransfer
	 */
	private void revocationSourceData(FiFundstransfer fiFundstransfer){
		if("实收实付单".equals(fiFundstransfer.getSourceData())){
			User user=WebRalasafe.getCurrentUser(Struts2Utils.getRequest());
			this.fiPaidService.batchExecute("update FiPaid f set f.fiFundstransferId='',f.fiFundstransferStatus=0L where f.fiFundstransferId=?", fiFundstransfer.getId());
		}
	}
}
