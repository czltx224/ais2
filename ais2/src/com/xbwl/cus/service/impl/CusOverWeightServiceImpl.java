package com.xbwl.cus.service.impl;

import java.text.SimpleDateFormat;
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
import com.xbwl.cus.dao.ICusOverWeightDao;
import com.xbwl.cus.service.ICusOverWeightService;
import com.xbwl.entity.BasCusService;
import com.xbwl.entity.OprOverweight;
import com.xbwl.entity.SysDepart;
import com.xbwl.finance.dto.IFiInterface;
import com.xbwl.finance.dto.impl.FiInterfaceProDto;
import com.xbwl.rbac.Service.IDepartService;
import com.xbwl.sys.service.IBasCusService;

/**
 * �������ش�������ʵ����
 *@author LiuHao
 *@time Oct 25, 2011 10:57:23 AM
 */
@Service("cusOverWeightServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class CusOverWeightServiceImpl extends BaseServiceImpl<OprOverweight,Long> implements
		ICusOverWeightService {
	@Resource(name="cusOverWeightHibernateDaoImpl")
	private ICusOverWeightDao cusOverWeightDao;
	@Resource(name="fiInterfaceImpl")
	private IFiInterface fiInterface;
	@Resource(name="basCusServiceImpl")
	private IBasCusService basCusService;
	@Resource(name="departServiceImpl")
	private IDepartService departService;
	@Override
	public IBaseDAO getBaseDao() {
		return cusOverWeightDao;
	}
	//REVIEW-ACCEPT ����ע��
	//FIXED �ӿ���дע�� �˴��Ƿ���Բ�д��
	public void aduitOverWeight(Long ooId,String aduitRemark) throws Exception {
		OprOverweight oprOverweight = cusOverWeightDao.get(ooId);
		User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		if(oprOverweight.getAuditStatus()!=1L){
			new ServiceException("ֻ�����δ��˵ĳ�����Ϣ��");
		}else{
			
			oprOverweight.setAuditRemark(aduitRemark);
			oprOverweight.setAuditName(user.get("name").toString());
			oprOverweight.setAuditStatus(2L);
			oprOverweight.setAuditTime(new Date());
			
			//����ӿ�����List
			List<FiInterfaceProDto> incomeList=new ArrayList<FiInterfaceProDto>(0);
			//REVIEW-ACCEPT ���listֻ��һ��Ԫ�أ�Ӧ���ڳ�ʼ��ʱָ�����ȱ����ڴ��˷�
			//FIXED LIUH
			List<FiInterfaceProDto> list=new ArrayList<FiInterfaceProDto>(0); 
			FiInterfaceProDto fpd=new FiInterfaceProDto();
			fpd.setFlightMainNo(oprOverweight.getFlightMainNo());
			fpd.setCustomerId(oprOverweight.getCustomerId());
			fpd.setCustomerName(oprOverweight.getCustomerName());
			fpd.setCollectionUser(user.get("name").toString());
			fpd.setSettlementType(1L);
			fpd.setCreateRemark("��_"+oprOverweight.getCustomerName()+"_"+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"_������("+oprOverweight.getFlightMainNo()+")_����("+oprOverweight.getWeight()+")__����("+oprOverweight.getRate()+")_Ԥ�����ͷ�");
			fpd.setAmount(oprOverweight.getAmount());
			fpd.setDocumentsType("����");
			fpd.setDocumentsSmalltype("���ؽ��㵥");
			fpd.setDocumentsNo(ooId);
			fpd.setCostType("Ԥ�����ͷ�");
			fpd.setSourceData("��������");
			fpd.setSourceNo(ooId);
			list.add(fpd);
			
			//����
			BasCusService cusService=basCusService.getCusServiceByCusId(oprOverweight.getCustomerId(), Long.valueOf(user.get("bussDepart").toString()));
			if(cusService == null){
				throw new ServiceException("�����쳣������IDΪ:"+oprOverweight.getCustomerId()+"�Ŀ��̶�Ӧ�Ŀͷ�ԱΪ�գ�");
			}
			SysDepart sd=departService.getDepartByDepartNo(cusService.getServiceDepartCode());
			if(sd == null){
				throw new ServiceException("���ű���:"+cusService.getServiceDepartCode()+"��Ӧ�Ĳ�����ϢΪ���ˣ�����ϵϵͳ����Ա��");
			}
			FiInterfaceProDto incomeFpd=new FiInterfaceProDto();
			incomeFpd.setCustomerId(oprOverweight.getCustomerId());
			incomeFpd.setCustomerName(oprOverweight.getCustomerName());
			incomeFpd.setAmount(oprOverweight.getAmount());
			incomeFpd.setSourceData("��������");
			incomeFpd.setCostType("��������");
			incomeFpd.setCustomerService(cusService.getServiceName());
			incomeFpd.setAdmDepart(cusService.getServiceDepart());
			incomeFpd.setAdmDepartId(sd.getDepartId());
			incomeFpd.setSourceNo(oprOverweight.getId());
			incomeFpd.setIncomeDepartId(oprOverweight.getDepartId());
			incomeFpd.setIncomeDepart(oprOverweight.getDepartName());
			incomeFpd.setDepartId(Long.valueOf(user.get("bussDepart")+""));
			incomeFpd.setDepartName(user.get("rightDepart")+"");
			incomeList.add(incomeFpd);
			fiInterface.addFinanceInfo(list);
			//����ӿ�
			fiInterface.currentToFiIncome(incomeList);
			cusOverWeightDao.save(oprOverweight);
		}

	}
}
