package com.xbwl.finance.Service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.ralasafe.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.log.anno.ModuleName;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.common.utils.LogType;
import com.xbwl.entity.FiAppreciationService;
import com.xbwl.entity.FiCost;
import com.xbwl.entity.FiIncome;
import com.xbwl.entity.FiPayment;
import com.xbwl.entity.FiReceivabledetail;
import com.xbwl.entity.OprFaxIn;
import com.xbwl.entity.OprValueAddFee;
import com.xbwl.entity.SysDepart;
import com.xbwl.finance.Service.IFiAppreciationServiceService;
import com.xbwl.finance.dao.IFiAppreciationServiceDao;
import com.xbwl.finance.dao.IFiCostDao;
import com.xbwl.finance.dao.IFiIncomeDao;
import com.xbwl.finance.dao.IFiPaymentDao;
import com.xbwl.finance.dao.IFiReceivabledetailDao;
import com.xbwl.finance.dto.IFiInterface;
import com.xbwl.oper.fax.dao.IOprFaxInDao;
import com.xbwl.oper.stock.dao.IOprValueAddFeeDao;
import com.xbwl.oper.stock.service.IOprHistoryService;
import com.xbwl.rbac.Service.IDepartService;

/**
 * author shuw
 * time Oct 26, 2011 1:47:58 PM
 */
@Service("fiAppreciationServiceServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class FiAppreciationServiceServiceImpl  extends
								BaseServiceImpl<FiAppreciationService, Long> implements IFiAppreciationServiceService{

	@Resource(name = "fiAppreciationServicesHibernateDaoImpl")
	private IFiAppreciationServiceDao  fiAppreciationServiceDao;
	
	@Resource(name = "oprFaxInHibernateDaoImpl")
	private IOprFaxInDao oprFaxInDao;
	
	@Resource(name="fiIncomeHibernateDaoImpl")
	private IFiIncomeDao fiIncomeDao;
	
	@Resource(name = "fiReceivabledetailHibernateDaoImpl")
	private IFiReceivabledetailDao fiReceivabledetailDao;
	
	@Resource(name="fiCostHibernateDaoImpl")
	private IFiCostDao fiCostDao;
	
	@Resource(name="oprValueAddFeeHibernateDaoImpl")
	private IOprValueAddFeeDao oprValueAddFeeDao;

	@Resource(name="fiInterfaceImpl")
	private IFiInterface fiInterface;
	
	@Resource(name="departServiceImpl")
	private IDepartService departService;
	
	@Resource(name = "fiPaymentHibernateDaoImpl")
	private IFiPaymentDao fiPaymentDao;
	
	@Value("${fiAuditCost.log_auditCost}")
	private Long log_auditCost ;
	
	@Value("${fiDoMoney.log_doMoney}")
	private Long log_doMoney ;
	
	@Value("${fiRepayment.log_repayment}")
	private Long log_repayment ;
	
	@Resource(name = "oprHistoryServiceImpl")
	private IOprHistoryService oprHistoryService;
	
	
	@Override
	public IBaseDAO<FiAppreciationService, Long> getBaseDao() {
		return fiAppreciationServiceDao;
	}

	@ModuleName(value="增值服务费管理客服审核",logType=LogType.fi)
	public void saveService(List<Long> list, User user) throws Exception{
		for(Long idLong : list ){
			FiAppreciationService fiAppreciationService = fiAppreciationServiceDao.get(idLong);
			if(fiAppreciationService.getServiceAuditStatus()!=null){
				if(fiAppreciationService.getServiceAuditStatus()==2l){
					throw new ServiceException("客服已经审核，不能重复审核");
				}
			}
			
			if(!fiAppreciationService.getDepartId().equals(Long.parseLong(user.get("bussDepart")+""))){
				throw new ServiceException("非本业务部门人员不允许操作数据");
			}
			fiAppreciationService.setServiceAuditDate(new Date());
			fiAppreciationService.setServiceAuditStatus(2l);
			fiAppreciationService.setServiceAuditUser(user.get("name")+"");
			fiAppreciationServiceDao.save(fiAppreciationService);
			OprFaxIn oprfaxin = oprFaxInDao.get(fiAppreciationService.getDno());
			if(fiAppreciationService.getIncomeAmount()>0){
				FiIncome fiIncome = new FiIncome();
				fiIncome.setSourceNo(fiAppreciationService.getId());
				fiIncome.setWhoCash("到付");  //有问题。
				fiIncome.setAmountType("到付增值费");
				
				fiIncome.setAdmDepart(oprfaxin.getCusDepartName());
				
				List<SysDepart>list2 =departService.find("from SysDepart t where t.departNo=? ", oprfaxin.getCusDepartCode());
				if(list2.size()==0){  //取传真表的客服员部门ID
					throw new ServiceException("没有找到客服员的部门，保存被取消");
				}
				fiIncome.setAdmDepartId(list2.get(0).getDepartId());
				
				fiIncome.setCustomerService(oprfaxin.getCustomerService());
				fiIncome.setAmount(fiAppreciationService.getIncomeAmount());
				fiIncome.setSourceData("增值服务费");
				fiIncome.setIncomeDepart(fiAppreciationService.getDepartName());
				fiIncome.setIncomeDepartId(fiAppreciationService.getDepartId());
				fiIncome.setCustomerId(fiAppreciationService.getCustomerId());
				fiIncome.setCustomerName(fiAppreciationService.getCustomerName());
				fiIncome.setDno(fiAppreciationService.getDno());
				fiIncomeDao.save(fiIncome);           //收入表
				
				OprValueAddFee entity= new OprValueAddFee();
				entity.setDno(fiAppreciationService.getDno());
				entity.setFeeCount(fiAppreciationService.getIncomeAmount());
				entity.setFeeName(fiAppreciationService.getAppreciationType());
				oprValueAddFeeDao.save(entity);                 //增值服务费表

				if("月结".equals(fiAppreciationService.getPaymentMode())){					
					FiReceivabledetail fiRece = new FiReceivabledetail();
					fiRece.setDno(fiAppreciationService.getDno());
					fiRece.setFlightmainno(oprfaxin.getFlightMainNo());
					fiRece.setAmount(fiAppreciationService.getIncomeAmount());
					fiRece.setSourceData("增值服务费");
					fiRece.setSourceNo(fiAppreciationService.getId());
					fiRece.setCustomerId(fiAppreciationService.getCustomerId());
					fiRece.setCustomerName(fiAppreciationService.getCustomerName());
					fiRece.setCostType(fiAppreciationService.getAppreciationType());
					fiRece.setPaymentType(1l);
					fiReceivabledetailDao.save(fiRece);            // 写入客商往来明细
					
					oprHistoryService.saveLog(fiAppreciationService.getDno(), "审核其他(增值服务费)成本，审核收入金额："+fiAppreciationService.getIncomeAmount() , log_repayment);     //操作日志
				}else{
					FiPayment fiPayment = new FiPayment();
					fiPayment.setPaymentType(1l);
					fiPayment.setPaymentStatus(1l);
					fiPayment.setCostType(fiAppreciationService.getAppreciationType());
					fiPayment.setDocumentsType("收入");
					fiPayment.setDocumentsSmalltype("配送单");
					fiPayment.setDocumentsNo(fiAppreciationService.getDno());
					fiPayment.setPenyType("现结");
					fiPayment.setIncomeDepartId(oprfaxin.getInDepartId());
					fiPayment.setAmount(fiAppreciationService.getIncomeAmount());
					if("收货人".equals(fiAppreciationService.getPaymentType())){
						fiPayment.setContacts(oprfaxin.getConsignee()+"/"+oprfaxin.getConsigneeTel());
					}else {
						fiPayment.setCustomerId(fiAppreciationService.getCustomerId());
						fiPayment.setCustomerName(fiAppreciationService.getCustomerName());
					}
					fiPayment.setSourceData("增值服务费");
					fiPayment.setSourceNo(fiAppreciationService.getId());
					fiPayment.setCollectionUser(fiAppreciationService.getCreateName());  //写入应收款
					fiPaymentDao.save(fiPayment);
					
					oprHistoryService.saveLog(fiAppreciationService.getDno(), "审核其他(增值服务费)成本，审核支付金额："+fiAppreciationService.getIncomeAmount() , log_doMoney);     //操作日志
				}
			}
		}
	}

	@ModuleName(value="增值服务费管理会计审核",logType=LogType.fi)
	public void saveFiAudit(List<Long> list, User user) throws Exception {
		for(Long idLong : list ){
			FiAppreciationService fiAppreciationService = fiAppreciationServiceDao.get(idLong);
			if(fiAppreciationService.getServiceAuditStatus()==null||fiAppreciationService.getServiceAuditStatus()==1l){
				throw new ServiceException("客服未审核，不能进行会计审核");
			}
			if(fiAppreciationService.getAccountAuditStatus()!=null){
				if(fiAppreciationService.getAccountAuditStatus()==2l){
					throw new ServiceException("会计已经审核，不能重复审核");
				}
			}
			
			if(!fiAppreciationService.getDepartId().equals(Long.parseLong(user.get("bussDepart")+""))){
				throw new ServiceException("非本业务部门人员不允许操作数据");
			}
			
			fiAppreciationService.setAccountAuditDate(new Date());
			fiAppreciationService.setAccountAuditStatus(2l);
			fiAppreciationService.setAccountAuditUser(user.get("name")+"");
			fiAppreciationServiceDao.save(fiAppreciationService);
			
			 if (fiAppreciationService.getCostAmount()>0) {
				 FiCost fiCost = new FiCost();
				 fiCost.setCostType("其他成本");
				 fiCost.setCostTypeDetail(fiAppreciationService.getAppreciationType());
				 fiCost.setCostAmount(fiAppreciationService.getCostAmount());
				 fiCost.setDataSource("增值服务费");
				 fiCost.setSourceSignNo(fiAppreciationService.getId()+"");
				 fiCost.setDno(fiAppreciationService.getDno());
				 fiCostDao.save(fiCost);
				 
				oprHistoryService.saveLog(fiCost.getDno(), "审核其他(增值服务费)成本，审核支付金额："+fiCost.getCostAmount() , log_auditCost);     //操作日志
				 
				 FiPayment fiPayment = new FiPayment();
				 fiPayment.setPaymentType(2l);
				 fiPayment.setPaymentStatus(4l);
				 fiPayment.setCostType(fiAppreciationService.getAppreciationType());
				 fiPayment.setDocumentsType("成本");
				 fiPayment.setDocumentsSmalltype("配送单");
				 fiPayment.setDocumentsNo(fiAppreciationService.getDno());
				 fiPayment.setPenyType("现结");
				 fiPayment.setAmount(fiAppreciationService.getCostAmount());
				 fiPayment.setContacts(fiAppreciationService.getRecipientsUser()+"/"+fiAppreciationService.getPhome());
				 fiPayment.setCreateRemark("支付"+fiAppreciationService.getRecipientsUser()+fiAppreciationService.getAppreciationType()+fiAppreciationService.getCostAmount()+"元");
				 fiPayment.setSourceData("增值服务费");
				 fiPayment.setSourceNo(fiAppreciationService.getId());
				 fiPayment.setCollectionUser(fiAppreciationService.getCreateName());  //写入应付款
				 fiPaymentDao.save(fiPayment);
			}
		}
	}
}
