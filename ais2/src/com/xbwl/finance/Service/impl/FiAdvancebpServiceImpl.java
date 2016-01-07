package com.xbwl.finance.Service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.xbwl.entity.FiAdvance;
import com.xbwl.entity.FiAdvancebp;
import com.xbwl.entity.FiAdvanceset;
import com.xbwl.finance.Service.IFiAdvancebpService;
import com.xbwl.finance.Service.IFiPaymentService;
import com.xbwl.finance.dao.IFiAdvanceDao;
import com.xbwl.finance.dao.IFiAdvancebpDao;
import com.xbwl.finance.dao.IFiAdvancesetDao;
import com.xbwl.finance.dto.IFiInterface;
import com.xbwl.finance.dto.impl.FiInterfaceProDto;

@Service("fiAdvancebpServiceImpl")
@Transactional(rollbackFor={Exception.class})
public class FiAdvancebpServiceImpl extends BaseServiceImpl<FiAdvancebp,Long> implements
		IFiAdvancebpService {
	
	@Resource(name="fiAdvancebpHibernateDaoImpl")
	private IFiAdvancebpDao fiAdvancebpDao;
	
	// 预付款设置Dao
	@Resource(name = "fiAdvancesetHibernateDaoImpl")
	private IFiAdvancesetDao fiAdvancesetDao;

	// 预付款结算Dao
	@Resource(name = "fiAdvanceHibernateDaoImpl")
	private IFiAdvanceDao fiAdvanceDao;
	
	@Resource(name="fiInterfaceImpl")
	private IFiInterface fiInterface;
	
	
	@Override
	public IBaseDAO getBaseDao() {
		return fiAdvancebpDao;
	}
	
	public void reviewConfirmation(FiAdvancebp fiAdvancebp) throws Exception{
		if(fiAdvancebp==null) throw new ServiceException("预付款支付实体不存在!");
		if(fiAdvancebp.getStatus()==0L) throw new ServiceException("预付款已作废，不允许审核!");
		if(fiAdvancebp.getReviewStatus()==2L) throw new ServiceException("预付款已审核，不允许审核!");
		
		User user=WebRalasafe.getCurrentUser(Struts2Utils.getRequest());
		fiAdvancebp.setReviewStatus(2L);
		fiAdvancebp.setSettlementAmount(fiAdvancebp.getAmount());
		fiAdvancebp.setReviewDate(new Date());
		fiAdvancebp.setReviewUser(user.get("name").toString());
		this.fiAdvancebpDao.save(fiAdvancebp);
		
		FiAdvanceset fa=this.fiAdvancesetDao.get(fiAdvancebp.getFiAdvanceId());
		if(fa==null) throw new ServiceException("预付款账号不存在!");
		// 预付款设置余额
		fa.setOpeningBalance(DoubleUtil.add(fa.getOpeningBalance(), fiAdvancebp.getSettlementAmount()));
		this.fiAdvancesetDao.save(fa);
		
		FiAdvance fiAdvance = new FiAdvance();
		fiAdvance.setSettlementType(fiAdvancebp.getSettlementType());// 付款
		fiAdvance.setCustomerId(fa.getCustomerId());// 预付款结算设置：客商ID
		fiAdvance.setCustomerName(fa.getCustomerName());
		fiAdvance.setSettlementAmount(fiAdvancebp.getSettlementAmount());// 本次结算金额
		fiAdvance.setSettlementBalance(fa.getOpeningBalance());// 余额
		fiAdvance.setSourceData("预存款收支");
		fiAdvance.setSourceNo(fiAdvancebp.getId());
		fiAdvance.setFiAdvanceId(fa.getId());
		this.fiAdvanceDao.save(fiAdvance);
		
		
		//将数据写入应收应付表
		FiInterfaceProDto fpd=new FiInterfaceProDto();
		fpd.setCustomerId(fiAdvancebp.getCustomerId());
		fpd.setCustomerName(fiAdvancebp.getCustomerName());
		fpd.setDistributionMode("客商");
		fpd.setDocumentsType("预存款");
		fpd.setDocumentsSmalltype("预存款单");
		fpd.setDocumentsNo(fiAdvancebp.getId());
		fpd.setSettlementType(fiAdvancebp.getSettlementType()==1?1L:2L);//如类型为1收款单，其它为付款单
		fpd.setAmount(fiAdvancebp.getAmount());//应收余额
		fpd.setSourceData("预存款收支");
		fpd.setCostType("预存款");
		fpd.setCollectionUser(user.get("name").toString());
		fpd.setSourceNo(fiAdvancebp.getId());
		fpd.setDepartId(Long.valueOf(String.valueOf(user.get("bussDepart"))));
		List<FiInterfaceProDto> listfiInterfaceDto=new ArrayList<FiInterfaceProDto>();
		listfiInterfaceDto.add(fpd);
		this.fiInterface.reconciliationToFiPayment(listfiInterfaceDto);
	}
	
	public void reviewRegister(FiAdvancebp fiAdvancebp) throws Exception{
		if(fiAdvancebp==null) throw new ServiceException("预存款支付实体不存在!");
		if(fiAdvancebp.getStatus()==0L) throw new ServiceException("预存款已作废，不允许撤销审核!");
		if(fiAdvancebp.getReviewStatus()==1L) throw new ServiceException("预存款未审核，不允许撤销审核!");
		if(fiAdvancebp.getPayStatus()==2L) throw new ServiceException("预存款已支付，不允许撤销审核!");
		User user=WebRalasafe.getCurrentUser(Struts2Utils.getRequest());
		fiAdvancebp.setReviewStatus(1L);
		this.fiAdvancebpDao.save(fiAdvancebp);
		
		// 预付款账号撤销
		FiAdvanceset fa=this.fiAdvancesetDao.get(fiAdvancebp.getFiAdvanceId());
		if(fa==null) throw new ServiceException("预存款账号不存在!");
		fa.setOpeningBalance(DoubleUtil.sub(fa.getOpeningBalance(), fiAdvancebp.getSettlementAmount()));
		this.fiAdvancesetDao.save(fa);
		
		FiAdvance fiAdvance = new FiAdvance();
		fiAdvance.setSettlementType(fiAdvancebp.getSettlementType());// 付款
		fiAdvance.setCustomerId(fa.getCustomerId());// 预付款结算设置：客商ID
		fiAdvance.setCustomerName(fa.getCustomerName());
		fiAdvance.setSettlementAmount(DoubleUtil.mul(fiAdvancebp.getSettlementAmount(),-1));// 本次结算金额
		fiAdvance.setSettlementBalance(fa.getOpeningBalance());// 余额
		fiAdvance.setSourceData("预存款收支");
		fiAdvance.setSourceNo(fiAdvancebp.getId());
		fiAdvance.setRemark("被"+user.get("name").toString()+"撤销审核");
		fiAdvance.setFiAdvanceId(fa.getId());
		this.fiAdvanceDao.save(fiAdvance);
		
		//作废应收应付
		FiInterfaceProDto fiInterfaceProDto=new FiInterfaceProDto();
		fiInterfaceProDto.setSourceData("预存款收支");
		fiInterfaceProDto.setSourceNo(fiAdvancebp.getId());
		this.fiInterface.invalidToFiPayment(fiInterfaceProDto);
		
	}
	
	public void addRegister(FiAdvancebp fiAdvancebp) throws Exception{
		if(fiAdvancebp==null) throw new ServiceException("预付款支付实体不存在!");
		if(fiAdvancebp.getStatus()==0L) throw new ServiceException("预付款已作废，不允许撤销审核!");
		if(fiAdvancebp.getReviewStatus()==2L) throw new ServiceException("预付款已审核，不允许审核!");
		if(fiAdvancebp.getPayStatus()==2L) throw new ServiceException("预付款已支付，不允许撤销审核!");
		fiAdvancebp.setStatus(0L);
		this.fiAdvancebpDao.save(fiAdvancebp);
	}
	
	public void verfiFiAdvancebp(Double amount,Long fiAdvancebpId) throws Exception{
		FiAdvancebp fiAdvancebp=this.fiAdvancebpDao.get(fiAdvancebpId);
		if(fiAdvancebp==null) throw new ServiceException("预付款支付实体不存在!");
		if(amount>=fiAdvancebp.getSettlementAmount()){
			fiAdvancebp.setPayStatus(2L);
		}
		this.fiAdvancebpDao.save(fiAdvancebp);
		
	}
	
	public void verfiFiAdvancebpRegister(Long fiAdvancebpId) throws Exception{
		FiAdvancebp fiAdvancebp=this.fiAdvancebpDao.get(fiAdvancebpId);
		if(fiAdvancebp==null) throw new ServiceException("预付款支付实体不存在!");
		fiAdvancebp.setPayStatus(1L);
		this.fiAdvancebpDao.save(fiAdvancebp);
	}
	
}
