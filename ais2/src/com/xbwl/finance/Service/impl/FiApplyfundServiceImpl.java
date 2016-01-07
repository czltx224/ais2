package com.xbwl.finance.Service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.common.utils.DoubleUtil;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.FiApplyfund;
import com.xbwl.entity.FiCapitaaccount;
import com.xbwl.entity.FiCapitaaccountset;
import com.xbwl.entity.FiFundstransfer;
import com.xbwl.finance.Service.IFiApplyfundService;
import com.xbwl.finance.Service.IFiCapitaaccountService;
import com.xbwl.finance.Service.IFiCapitaaccountsetService;
import com.xbwl.finance.Service.IFiFundstransferService;
import com.xbwl.finance.dao.IFiApplyfundDao;

@Service("fiApplyfundServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class FiApplyfundServiceImpl extends BaseServiceImpl<FiApplyfund,Long> implements
		IFiApplyfundService {

	@Resource(name="fiApplyfundHibernateDaoImpl")
	private IFiApplyfundDao fiApplyfundDao;
	
	//资金交接单
	@Resource(name="fiFundstransferServiceImpl")
	private IFiFundstransferService fiFundstransferService;
	
	//资金账号设置
	@Resource(name = "fiCapitaaccountsetServiceImpl")
	private IFiCapitaaccountsetService fiCapitaaccountsetService;
	
	//资金账号流水
	@Resource(name="fiCapitaaccountServiceImpl")
	private IFiCapitaaccountService fiCapitaaccountService;
	
	@Override
	public IBaseDAO getBaseDao() {
		return this.fiApplyfundDao;
	}
	
	public void saveApplyfund(FiApplyfund fiApplyfund) throws Exception{
		if(fiApplyfund==null) throw new ServiceException("资金申请实体不存在！");
		this.fiApplyfundDao.save(fiApplyfund);
	}
	
	public void invalidApplyfund(FiApplyfund fiApplyfund) throws Exception{
		if(fiApplyfund==null) throw new ServiceException("资金申请实体不存在！");
		if(fiApplyfund.getId()==null) throw new ServiceException("资金申请单ID不存在！");
		FiApplyfund fa=this.fiApplyfundDao.get(fiApplyfund.getId());
		if(fa.getStatus()==0L)  throw new ServiceException("已作废，不允许重复作废！");
		if(fa.getStatus()==1L)  throw new ServiceException("已否决，不允许作废！");
		if(fa.getAuditStatus()==2L)  throw new ServiceException("已审核，不允作废核！");
		fa.setStatus(0L);
		this.fiApplyfundDao.save(fa);
	}
	
	public void auditApplyfund(FiApplyfund fiApplyfund) throws Exception{
		if(fiApplyfund==null) throw new ServiceException("资金申请实体不存在！");
		if(fiApplyfund.getId()==null) throw new ServiceException("资金申请单ID不存在！");
		if(fiApplyfund.getStatus()==0L)  throw new ServiceException("已作废，不允许审核！");
		if(fiApplyfund.getStatus()==1L)  throw new ServiceException("已否决，不允许审核！");
		if(fiApplyfund.getAuditStatus()==2L)  throw new ServiceException("已审核，不允许审核！");
		
		//直接拨付
		if(fiApplyfund.getIsSit()==2L){
			//拨付账号
			FiCapitaaccountset fiCapitaaccountsetPayment=this.fiCapitaaccountsetService.get(fiApplyfund.getPaymentAccountId());
			//申请账号
			FiCapitaaccountset fiCapitaaccountsetReceivables=this.fiCapitaaccountsetService.get(fiApplyfund.getAppAccountId());
			
			//拨付账号验证
			if(fiCapitaaccountsetPayment==null){
				throw new ServiceException("拨付账号不存在!");
			}
			if(fiCapitaaccountsetPayment.getResponsibleId().equals(fiCapitaaccountsetReceivables.getResponsibleId())){
				throw new ServiceException("申请人账号负责人与拨付账号负责人不能为同一人。");
			}
			//申请账号验证
			if(fiCapitaaccountsetReceivables==null){
				throw new ServiceException("申请账号不存在!");
			}
			
			//拨付账号余额减少
			fiCapitaaccountsetPayment.setOpeningBalance(DoubleUtil.sub(fiCapitaaccountsetPayment.getOpeningBalance(),fiApplyfund.getPaymentAmount()));
			if(fiCapitaaccountsetPayment.getOpeningBalance()<0.0){
				throw new ServiceException("资金交接单付款账号余额不足！");
			}
			this.fiCapitaaccountsetService.save(fiCapitaaccountsetPayment);
			
			//资金流水处理
			FiCapitaaccount fiCapitaaccountPayment = new FiCapitaaccount();
			fiCapitaaccountPayment.setBorrow(fiApplyfund.getPaymentAmount());
			fiCapitaaccountPayment.setBalance(fiCapitaaccountsetPayment.getOpeningBalance());// 账号余额
			fiCapitaaccountPayment.setFiCapitaaccountsetId(fiCapitaaccountsetPayment.getId());
			fiCapitaaccountPayment.setSourceData("资金申请单");
			fiCapitaaccountPayment.setSourceNo(fiApplyfund.getId());
			fiCapitaaccountPayment.setRemark("审核,确认直接拨付");
			this.fiCapitaaccountService.save(fiCapitaaccountPayment);// 保存流水账
		}
		
		fiApplyfund.setAuditStatus(2L);//已审核
		fiApplyfund.setAuditTime(new Date());
		this.fiApplyfundDao.save(fiApplyfund);
	}
	
	public void fundstransferSit(FiApplyfund fiApplyfund) throws Exception{
		if(fiApplyfund==null) throw new ServiceException("资金申请实体不存在！");
		if(fiApplyfund.getId()==null) throw new ServiceException("资金申请单ID不存在！");
		if(fiApplyfund.getStatus()==0L)  throw new ServiceException("已作废，不允许坐支！");
		if(fiApplyfund.getStatus()==1L)  throw new ServiceException("已否决，不允许坐支！");
		if(fiApplyfund.getAuditStatus()!=2L)  throw new ServiceException("还未审核，不允许坐支！");
		if(fiApplyfund.getIsSit()!=1L)  throw new ServiceException("不允许坐支！");
		
		//拨付账号
		FiCapitaaccountset fiCapitaaccountsetPayment=this.fiCapitaaccountsetService.get(fiApplyfund.getPaymentAccountId());
		//申请账号
		FiCapitaaccountset fiCapitaaccountsetReceivables=this.fiCapitaaccountsetService.get(fiApplyfund.getAppAccountId());
		//拨付账号验证
		if(fiCapitaaccountsetPayment==null){
			throw new ServiceException("拨付账号不存在!");
		}
		//申请账号验证
		if(fiCapitaaccountsetReceivables==null){
			throw new ServiceException("申请账号不存在!");
		}
		
		//拨付账号余额减少
		fiCapitaaccountsetPayment.setOpeningBalance(DoubleUtil.sub(fiCapitaaccountsetPayment.getOpeningBalance(),fiApplyfund.getPaymentAmount()));
		if(fiCapitaaccountsetPayment.getOpeningBalance()<0.0){
			throw new ServiceException("付款账号余额不足！");
		}
		this.fiCapitaaccountsetService.save(fiCapitaaccountsetPayment);
		//资金流水处理
		FiCapitaaccount fiCapitaaccountPayment = new FiCapitaaccount();
		fiCapitaaccountPayment.setBorrow(fiApplyfund.getPaymentAmount());
		fiCapitaaccountPayment.setBalance(fiCapitaaccountsetPayment.getOpeningBalance());// 账号余额
		fiCapitaaccountPayment.setFiCapitaaccountsetId(fiCapitaaccountsetPayment.getId());
		fiCapitaaccountPayment.setSourceData("资金申请单");
		fiCapitaaccountPayment.setSourceNo(fiApplyfund.getId());
		fiCapitaaccountPayment.setRemark("生成坐支单，自动减少付款账号余额");
		this.fiCapitaaccountService.save(fiCapitaaccountPayment);// 保存流水账
		
		//申请账号余额增加
		fiCapitaaccountsetReceivables.setOpeningBalance(DoubleUtil.add(fiCapitaaccountsetReceivables.getOpeningBalance(),fiApplyfund.getPaymentAmount()));
		this.fiCapitaaccountsetService.save(fiCapitaaccountsetReceivables);
		//资金流水处理
		FiCapitaaccount fiCapitaaccount = new FiCapitaaccount();
		fiCapitaaccount.setLoan(fiApplyfund.getPaymentAmount());
		fiCapitaaccount.setBalance(fiCapitaaccountsetReceivables.getOpeningBalance());// 账号余额
		fiCapitaaccount.setFiCapitaaccountsetId(fiCapitaaccountsetReceivables.getId());
		fiCapitaaccount.setSourceData("资金申请单");
		fiCapitaaccount.setSourceNo(fiApplyfund.getId());
		fiCapitaaccount.setRemark("生成坐支单，自动增加收款账号余额");
		this.fiCapitaaccountService.save(fiCapitaaccount);// 保存流水账
		
		fiApplyfund.setReceivablesStatus(2L);
		fiApplyfund.setReceivablesTime(new Date());
		fiApplyfund.setStatus(4L);
		this.fiApplyfundDao.save(fiApplyfund);
		
		/*if(fiApplyfund.getAppAccountId()==fiApplyfund.getPaymentAccountId()) throw new ServiceException("收款账号与付款账号不能为同一账号");
		User user=WebRalasafe.getCurrentUser(Struts2Utils.getRequest());
		
		//生成资金交接单
		FiFundstransfer fiFundstransfer=new FiFundstransfer();
		fiFundstransfer.setReceivablesaccountId(fiApplyfund.getAppAccountId());//收款账号
		fiFundstransfer.setReceivablesaccountDeptid(fiApplyfund.getDepartId());
		fiFundstransfer.setReceivablesaccountDept(fiApplyfund.getDepartName());
		fiFundstransfer.setPaymentaccountId(fiApplyfund.getPaymentAccountId());//付款账号
		fiFundstransfer.setAmount(fiApplyfund.getPaymentAmount());
		fiFundstransfer.setCapitalTypeId(fiApplyfund.getCapitalTypeId());
		fiFundstransfer.setRemark(fiApplyfund.getPaymentRemark());
		fiFundstransfer.setSourceData("资金申请单");
		fiFundstransfer.setSourceNo(fiApplyfund.getId());
		fiFundstransfer.setStatus(2L);//自动核销
		this.fiFundstransferService.save(fiFundstransfer);
		
		//资金交接单付款账号余额减少,自动付款确认
		this.fiFundstransferService.paymentConfirmation(fiFundstransfer.getId(), user);
		//this.fiFundstransferService.accountHandover(fiFundstransfer);
		
		this.fiFundstransferService.save(fiFundstransfer);*/
	}
	
	public void receivablesConfirmation(Long id,User user) throws Exception{
		Long userId=Long.valueOf(user.get("id")+"");
		if(userId==null||"".equals(userId)){
			throw new ServiceException("获取用户ID失败!");
		}
		FiApplyfund fiApplyfund=this.fiApplyfundDao.get(id);
		if(fiApplyfund==null){
			throw new ServiceException("资金申请单不存在!");
		}
		
		//申请人收款账号
		FiCapitaaccountset fiCapitaaccountsetReceivables=this.fiCapitaaccountsetService.get(fiApplyfund.getAppAccountId());
		if(fiCapitaaccountsetReceivables==null) {
			throw new ServiceException("资金交接单收款账号不存在!");
		}
		if(!userId.equals(fiCapitaaccountsetReceivables.getResponsibleId())){
			throw new ServiceException("只有收款人账号负责人才能收款确认");
		}
		if(fiApplyfund.getAuditStatus()==null||fiApplyfund.getAuditStatus()!=2L){
			throw new ServiceException("当前资金申请单还未审核！");
		}
		
		if(fiApplyfund.getStatus()==0L||fiApplyfund.getStatus()==1L){
			throw new ServiceException("交接单已作废!");
		}
		

		
		fiCapitaaccountsetReceivables.setOpeningBalance(DoubleUtil.add(fiCapitaaccountsetReceivables.getOpeningBalance(),fiApplyfund.getPaymentAmount()));
		this.fiCapitaaccountsetService.save(fiCapitaaccountsetReceivables);
		//资金流水处理
		FiCapitaaccount fiCapitaaccountReceivables = new FiCapitaaccount();
		fiCapitaaccountReceivables.setLoan(fiApplyfund.getPaymentAmount());
		fiCapitaaccountReceivables.setBalance(fiCapitaaccountsetReceivables.getOpeningBalance());// 账号余额
		fiCapitaaccountReceivables.setFiCapitaaccountsetId(fiCapitaaccountsetReceivables.getId());
		fiCapitaaccountReceivables.setSourceData("资金申请单");
		fiCapitaaccountReceivables.setSourceNo(fiApplyfund.getId());
		fiCapitaaccountReceivables.setRemark("资金申请单收款确认");
		this.fiCapitaaccountService.save(fiCapitaaccountReceivables);// 保存流水账
		
		//保存确收状态
		fiApplyfund.setReceivablesStatus(2L);
		fiApplyfund.setStatus(3L);
		fiApplyfund.setReceivablesTime(new Date());
		this.fiApplyfundDao.save(fiApplyfund);
	}
}
