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
	
	// Ԥ��������Dao
	@Resource(name = "fiAdvancesetHibernateDaoImpl")
	private IFiAdvancesetDao fiAdvancesetDao;

	// Ԥ�������Dao
	@Resource(name = "fiAdvanceHibernateDaoImpl")
	private IFiAdvanceDao fiAdvanceDao;
	
	@Resource(name="fiInterfaceImpl")
	private IFiInterface fiInterface;
	
	
	@Override
	public IBaseDAO getBaseDao() {
		return fiAdvancebpDao;
	}
	
	public void reviewConfirmation(FiAdvancebp fiAdvancebp) throws Exception{
		if(fiAdvancebp==null) throw new ServiceException("Ԥ����֧��ʵ�岻����!");
		if(fiAdvancebp.getStatus()==0L) throw new ServiceException("Ԥ���������ϣ����������!");
		if(fiAdvancebp.getReviewStatus()==2L) throw new ServiceException("Ԥ��������ˣ����������!");
		
		User user=WebRalasafe.getCurrentUser(Struts2Utils.getRequest());
		fiAdvancebp.setReviewStatus(2L);
		fiAdvancebp.setSettlementAmount(fiAdvancebp.getAmount());
		fiAdvancebp.setReviewDate(new Date());
		fiAdvancebp.setReviewUser(user.get("name").toString());
		this.fiAdvancebpDao.save(fiAdvancebp);
		
		FiAdvanceset fa=this.fiAdvancesetDao.get(fiAdvancebp.getFiAdvanceId());
		if(fa==null) throw new ServiceException("Ԥ�����˺Ų�����!");
		// Ԥ�����������
		fa.setOpeningBalance(DoubleUtil.add(fa.getOpeningBalance(), fiAdvancebp.getSettlementAmount()));
		this.fiAdvancesetDao.save(fa);
		
		FiAdvance fiAdvance = new FiAdvance();
		fiAdvance.setSettlementType(fiAdvancebp.getSettlementType());// ����
		fiAdvance.setCustomerId(fa.getCustomerId());// Ԥ����������ã�����ID
		fiAdvance.setCustomerName(fa.getCustomerName());
		fiAdvance.setSettlementAmount(fiAdvancebp.getSettlementAmount());// ���ν�����
		fiAdvance.setSettlementBalance(fa.getOpeningBalance());// ���
		fiAdvance.setSourceData("Ԥ�����֧");
		fiAdvance.setSourceNo(fiAdvancebp.getId());
		fiAdvance.setFiAdvanceId(fa.getId());
		this.fiAdvanceDao.save(fiAdvance);
		
		
		//������д��Ӧ��Ӧ����
		FiInterfaceProDto fpd=new FiInterfaceProDto();
		fpd.setCustomerId(fiAdvancebp.getCustomerId());
		fpd.setCustomerName(fiAdvancebp.getCustomerName());
		fpd.setDistributionMode("����");
		fpd.setDocumentsType("Ԥ���");
		fpd.setDocumentsSmalltype("Ԥ��");
		fpd.setDocumentsNo(fiAdvancebp.getId());
		fpd.setSettlementType(fiAdvancebp.getSettlementType()==1?1L:2L);//������Ϊ1�տ������Ϊ���
		fpd.setAmount(fiAdvancebp.getAmount());//Ӧ�����
		fpd.setSourceData("Ԥ�����֧");
		fpd.setCostType("Ԥ���");
		fpd.setCollectionUser(user.get("name").toString());
		fpd.setSourceNo(fiAdvancebp.getId());
		fpd.setDepartId(Long.valueOf(String.valueOf(user.get("bussDepart"))));
		List<FiInterfaceProDto> listfiInterfaceDto=new ArrayList<FiInterfaceProDto>();
		listfiInterfaceDto.add(fpd);
		this.fiInterface.reconciliationToFiPayment(listfiInterfaceDto);
	}
	
	public void reviewRegister(FiAdvancebp fiAdvancebp) throws Exception{
		if(fiAdvancebp==null) throw new ServiceException("Ԥ���֧��ʵ�岻����!");
		if(fiAdvancebp.getStatus()==0L) throw new ServiceException("Ԥ��������ϣ������������!");
		if(fiAdvancebp.getReviewStatus()==1L) throw new ServiceException("Ԥ���δ��ˣ������������!");
		if(fiAdvancebp.getPayStatus()==2L) throw new ServiceException("Ԥ�����֧���������������!");
		User user=WebRalasafe.getCurrentUser(Struts2Utils.getRequest());
		fiAdvancebp.setReviewStatus(1L);
		this.fiAdvancebpDao.save(fiAdvancebp);
		
		// Ԥ�����˺ų���
		FiAdvanceset fa=this.fiAdvancesetDao.get(fiAdvancebp.getFiAdvanceId());
		if(fa==null) throw new ServiceException("Ԥ����˺Ų�����!");
		fa.setOpeningBalance(DoubleUtil.sub(fa.getOpeningBalance(), fiAdvancebp.getSettlementAmount()));
		this.fiAdvancesetDao.save(fa);
		
		FiAdvance fiAdvance = new FiAdvance();
		fiAdvance.setSettlementType(fiAdvancebp.getSettlementType());// ����
		fiAdvance.setCustomerId(fa.getCustomerId());// Ԥ����������ã�����ID
		fiAdvance.setCustomerName(fa.getCustomerName());
		fiAdvance.setSettlementAmount(DoubleUtil.mul(fiAdvancebp.getSettlementAmount(),-1));// ���ν�����
		fiAdvance.setSettlementBalance(fa.getOpeningBalance());// ���
		fiAdvance.setSourceData("Ԥ�����֧");
		fiAdvance.setSourceNo(fiAdvancebp.getId());
		fiAdvance.setRemark("��"+user.get("name").toString()+"�������");
		fiAdvance.setFiAdvanceId(fa.getId());
		this.fiAdvanceDao.save(fiAdvance);
		
		//����Ӧ��Ӧ��
		FiInterfaceProDto fiInterfaceProDto=new FiInterfaceProDto();
		fiInterfaceProDto.setSourceData("Ԥ�����֧");
		fiInterfaceProDto.setSourceNo(fiAdvancebp.getId());
		this.fiInterface.invalidToFiPayment(fiInterfaceProDto);
		
	}
	
	public void addRegister(FiAdvancebp fiAdvancebp) throws Exception{
		if(fiAdvancebp==null) throw new ServiceException("Ԥ����֧��ʵ�岻����!");
		if(fiAdvancebp.getStatus()==0L) throw new ServiceException("Ԥ���������ϣ������������!");
		if(fiAdvancebp.getReviewStatus()==2L) throw new ServiceException("Ԥ��������ˣ����������!");
		if(fiAdvancebp.getPayStatus()==2L) throw new ServiceException("Ԥ������֧���������������!");
		fiAdvancebp.setStatus(0L);
		this.fiAdvancebpDao.save(fiAdvancebp);
	}
	
	public void verfiFiAdvancebp(Double amount,Long fiAdvancebpId) throws Exception{
		FiAdvancebp fiAdvancebp=this.fiAdvancebpDao.get(fiAdvancebpId);
		if(fiAdvancebp==null) throw new ServiceException("Ԥ����֧��ʵ�岻����!");
		if(amount>=fiAdvancebp.getSettlementAmount()){
			fiAdvancebp.setPayStatus(2L);
		}
		this.fiAdvancebpDao.save(fiAdvancebp);
		
	}
	
	public void verfiFiAdvancebpRegister(Long fiAdvancebpId) throws Exception{
		FiAdvancebp fiAdvancebp=this.fiAdvancebpDao.get(fiAdvancebpId);
		if(fiAdvancebp==null) throw new ServiceException("Ԥ����֧��ʵ�岻����!");
		fiAdvancebp.setPayStatus(1L);
		this.fiAdvancebpDao.save(fiAdvancebp);
	}
	
}
