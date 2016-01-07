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

	@ModuleName(value="��ֵ����ѹ���ͷ����",logType=LogType.fi)
	public void saveService(List<Long> list, User user) throws Exception{
		for(Long idLong : list ){
			FiAppreciationService fiAppreciationService = fiAppreciationServiceDao.get(idLong);
			if(fiAppreciationService.getServiceAuditStatus()!=null){
				if(fiAppreciationService.getServiceAuditStatus()==2l){
					throw new ServiceException("�ͷ��Ѿ���ˣ������ظ����");
				}
			}
			
			if(!fiAppreciationService.getDepartId().equals(Long.parseLong(user.get("bussDepart")+""))){
				throw new ServiceException("�Ǳ�ҵ������Ա�������������");
			}
			fiAppreciationService.setServiceAuditDate(new Date());
			fiAppreciationService.setServiceAuditStatus(2l);
			fiAppreciationService.setServiceAuditUser(user.get("name")+"");
			fiAppreciationServiceDao.save(fiAppreciationService);
			OprFaxIn oprfaxin = oprFaxInDao.get(fiAppreciationService.getDno());
			if(fiAppreciationService.getIncomeAmount()>0){
				FiIncome fiIncome = new FiIncome();
				fiIncome.setSourceNo(fiAppreciationService.getId());
				fiIncome.setWhoCash("����");  //�����⡣
				fiIncome.setAmountType("������ֵ��");
				
				fiIncome.setAdmDepart(oprfaxin.getCusDepartName());
				
				List<SysDepart>list2 =departService.find("from SysDepart t where t.departNo=? ", oprfaxin.getCusDepartCode());
				if(list2.size()==0){  //ȡ�����Ŀͷ�Ա����ID
					throw new ServiceException("û���ҵ��ͷ�Ա�Ĳ��ţ����汻ȡ��");
				}
				fiIncome.setAdmDepartId(list2.get(0).getDepartId());
				
				fiIncome.setCustomerService(oprfaxin.getCustomerService());
				fiIncome.setAmount(fiAppreciationService.getIncomeAmount());
				fiIncome.setSourceData("��ֵ�����");
				fiIncome.setIncomeDepart(fiAppreciationService.getDepartName());
				fiIncome.setIncomeDepartId(fiAppreciationService.getDepartId());
				fiIncome.setCustomerId(fiAppreciationService.getCustomerId());
				fiIncome.setCustomerName(fiAppreciationService.getCustomerName());
				fiIncome.setDno(fiAppreciationService.getDno());
				fiIncomeDao.save(fiIncome);           //�����
				
				OprValueAddFee entity= new OprValueAddFee();
				entity.setDno(fiAppreciationService.getDno());
				entity.setFeeCount(fiAppreciationService.getIncomeAmount());
				entity.setFeeName(fiAppreciationService.getAppreciationType());
				oprValueAddFeeDao.save(entity);                 //��ֵ����ѱ�

				if("�½�".equals(fiAppreciationService.getPaymentMode())){					
					FiReceivabledetail fiRece = new FiReceivabledetail();
					fiRece.setDno(fiAppreciationService.getDno());
					fiRece.setFlightmainno(oprfaxin.getFlightMainNo());
					fiRece.setAmount(fiAppreciationService.getIncomeAmount());
					fiRece.setSourceData("��ֵ�����");
					fiRece.setSourceNo(fiAppreciationService.getId());
					fiRece.setCustomerId(fiAppreciationService.getCustomerId());
					fiRece.setCustomerName(fiAppreciationService.getCustomerName());
					fiRece.setCostType(fiAppreciationService.getAppreciationType());
					fiRece.setPaymentType(1l);
					fiReceivabledetailDao.save(fiRece);            // д�����������ϸ
					
					oprHistoryService.saveLog(fiAppreciationService.getDno(), "�������(��ֵ�����)�ɱ�����������"+fiAppreciationService.getIncomeAmount() , log_repayment);     //������־
				}else{
					FiPayment fiPayment = new FiPayment();
					fiPayment.setPaymentType(1l);
					fiPayment.setPaymentStatus(1l);
					fiPayment.setCostType(fiAppreciationService.getAppreciationType());
					fiPayment.setDocumentsType("����");
					fiPayment.setDocumentsSmalltype("���͵�");
					fiPayment.setDocumentsNo(fiAppreciationService.getDno());
					fiPayment.setPenyType("�ֽ�");
					fiPayment.setIncomeDepartId(oprfaxin.getInDepartId());
					fiPayment.setAmount(fiAppreciationService.getIncomeAmount());
					if("�ջ���".equals(fiAppreciationService.getPaymentType())){
						fiPayment.setContacts(oprfaxin.getConsignee()+"/"+oprfaxin.getConsigneeTel());
					}else {
						fiPayment.setCustomerId(fiAppreciationService.getCustomerId());
						fiPayment.setCustomerName(fiAppreciationService.getCustomerName());
					}
					fiPayment.setSourceData("��ֵ�����");
					fiPayment.setSourceNo(fiAppreciationService.getId());
					fiPayment.setCollectionUser(fiAppreciationService.getCreateName());  //д��Ӧ�տ�
					fiPaymentDao.save(fiPayment);
					
					oprHistoryService.saveLog(fiAppreciationService.getDno(), "�������(��ֵ�����)�ɱ������֧����"+fiAppreciationService.getIncomeAmount() , log_doMoney);     //������־
				}
			}
		}
	}

	@ModuleName(value="��ֵ����ѹ��������",logType=LogType.fi)
	public void saveFiAudit(List<Long> list, User user) throws Exception {
		for(Long idLong : list ){
			FiAppreciationService fiAppreciationService = fiAppreciationServiceDao.get(idLong);
			if(fiAppreciationService.getServiceAuditStatus()==null||fiAppreciationService.getServiceAuditStatus()==1l){
				throw new ServiceException("�ͷ�δ��ˣ����ܽ��л�����");
			}
			if(fiAppreciationService.getAccountAuditStatus()!=null){
				if(fiAppreciationService.getAccountAuditStatus()==2l){
					throw new ServiceException("����Ѿ���ˣ������ظ����");
				}
			}
			
			if(!fiAppreciationService.getDepartId().equals(Long.parseLong(user.get("bussDepart")+""))){
				throw new ServiceException("�Ǳ�ҵ������Ա�������������");
			}
			
			fiAppreciationService.setAccountAuditDate(new Date());
			fiAppreciationService.setAccountAuditStatus(2l);
			fiAppreciationService.setAccountAuditUser(user.get("name")+"");
			fiAppreciationServiceDao.save(fiAppreciationService);
			
			 if (fiAppreciationService.getCostAmount()>0) {
				 FiCost fiCost = new FiCost();
				 fiCost.setCostType("�����ɱ�");
				 fiCost.setCostTypeDetail(fiAppreciationService.getAppreciationType());
				 fiCost.setCostAmount(fiAppreciationService.getCostAmount());
				 fiCost.setDataSource("��ֵ�����");
				 fiCost.setSourceSignNo(fiAppreciationService.getId()+"");
				 fiCost.setDno(fiAppreciationService.getDno());
				 fiCostDao.save(fiCost);
				 
				oprHistoryService.saveLog(fiCost.getDno(), "�������(��ֵ�����)�ɱ������֧����"+fiCost.getCostAmount() , log_auditCost);     //������־
				 
				 FiPayment fiPayment = new FiPayment();
				 fiPayment.setPaymentType(2l);
				 fiPayment.setPaymentStatus(4l);
				 fiPayment.setCostType(fiAppreciationService.getAppreciationType());
				 fiPayment.setDocumentsType("�ɱ�");
				 fiPayment.setDocumentsSmalltype("���͵�");
				 fiPayment.setDocumentsNo(fiAppreciationService.getDno());
				 fiPayment.setPenyType("�ֽ�");
				 fiPayment.setAmount(fiAppreciationService.getCostAmount());
				 fiPayment.setContacts(fiAppreciationService.getRecipientsUser()+"/"+fiAppreciationService.getPhome());
				 fiPayment.setCreateRemark("֧��"+fiAppreciationService.getRecipientsUser()+fiAppreciationService.getAppreciationType()+fiAppreciationService.getCostAmount()+"Ԫ");
				 fiPayment.setSourceData("��ֵ�����");
				 fiPayment.setSourceNo(fiAppreciationService.getId());
				 fiPayment.setCollectionUser(fiAppreciationService.getCreateName());  //д��Ӧ����
				 fiPaymentDao.save(fiPayment);
			}
		}
	}
}
