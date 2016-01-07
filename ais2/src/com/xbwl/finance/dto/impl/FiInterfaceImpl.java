package com.xbwl.finance.dto.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.log.anno.ModuleName;
import com.xbwl.common.utils.DoubleUtil;
import com.xbwl.common.utils.LogType;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.cus.service.ICusOverManagerService;
import com.xbwl.cus.service.ICusOverWeightService;
import com.xbwl.entity.CusOverweightManager;
import com.xbwl.entity.Customer;
import com.xbwl.entity.FiAdvance;
import com.xbwl.entity.FiAdvanceset;
import com.xbwl.entity.FiArrearset;
import com.xbwl.entity.FiCost;
import com.xbwl.entity.FiDeliveryPrice;
import com.xbwl.entity.FiDeliverycost;
import com.xbwl.entity.FiIncome;
import com.xbwl.entity.FiInternalDetail;
import com.xbwl.entity.FiOutcost;
import com.xbwl.entity.FiPayment;
import com.xbwl.entity.FiReceivabledetail;
import com.xbwl.entity.FiTransitcost;
import com.xbwl.entity.OprFaxIn;
import com.xbwl.entity.OprFaxMain;
import com.xbwl.entity.OprOverweight;
import com.xbwl.finance.Service.IFiArrearsetService;
import com.xbwl.finance.Service.IFiDeliveryPriceService;
import com.xbwl.finance.Service.IFiDeliverycostService;
import com.xbwl.finance.Service.IFiIncomeService;
import com.xbwl.finance.Service.IFiInternalDetailService;
import com.xbwl.finance.Service.IFiInternalRateService;
import com.xbwl.finance.Service.IFiInternalSpecialRateService;
import com.xbwl.finance.Service.IFiOutCostService;
import com.xbwl.finance.Service.IFiTransitcostService;
import com.xbwl.finance.dao.IFiAdvanceDao;
import com.xbwl.finance.dao.IFiAdvancesetDao;
import com.xbwl.finance.dao.IFiCostDao;
import com.xbwl.finance.dao.IFiOutCostDao;
import com.xbwl.finance.dao.IFiPaymentDao;
import com.xbwl.finance.dao.IFiReceivabledetailDao;
import com.xbwl.finance.dto.IFiInterface;
import com.xbwl.oper.fax.service.IOprFaxInService;
import com.xbwl.oper.fax.service.IOprFaxMainService;
import com.xbwl.oper.stock.service.IOprHistoryService;
import com.xbwl.sys.dao.ICustomerDao;

@Service("fiInterfaceImpl")
@Transactional(rollbackFor = { Exception.class })
public class FiInterfaceImpl implements IFiInterface {

	// Ӧ�ո���
	@Resource(name = "fiPaymentHibernateDaoImpl")
	private IFiPaymentDao fiPaymentDao;

	// Ƿ����ϸ
	@Resource(name = "fiReceivabledetailHibernateDaoImpl")
	private IFiReceivabledetailDao fiReceivabledetailDao;
	// ����
	@Resource(name = "customerHibernateDaoImpl")
	private ICustomerDao customerDao;

	// Ԥ�������
	@Resource(name = "fiAdvancesetHibernateDaoImpl")
	private IFiAdvancesetDao fiAdvancesetDao;

	// Ԥ�����ˮ
	@Resource(name = "fiAdvanceHibernateDaoImpl")
	private IFiAdvanceDao fiAdvanceDao;
	
	//�ڲ�����
	@Resource(name = "fiInternalDetailServiceImpl")
	private IFiInternalDetailService fiInternalDetailService;
	
	//��������
	@Resource(name = "oprFaxMainServiceImpl")
	private IOprFaxMainService oprFaxMainService;
	
	//����ɱ�
	@Resource(name = "fiDeliverycostServiceImpl")
	private IFiDeliverycostService fiDeliverycostService;
	
	//���Э���
	@Resource(name = "fiDeliveryPriceServiceImpl")
	private IFiDeliveryPriceService fiDeliveryPriceService;

	//������������
	@Resource(name="cusOverManagerServiceImpl")
	private ICusOverManagerService cusOverManagerService;
	
	//��������
	@Resource(name="cusOverWeightServiceImpl")
	private ICusOverWeightService cusOverWeightService;
	
	//��ת�ɱ�
	@Resource(name = "fiTransitcostServiceImpl")
	private IFiTransitcostService fiTransitService;
	
	//�����
	@Resource(name="fiIncomeServiceImpl")
	private IFiIncomeService fiIncomeService;
	
	//����¼��
	@Resource(name = "oprFaxInServiceImpl")
	private IOprFaxInService oprFaxInService;
	
	//����Ƿ������
	@Resource(name="fiArrearsetServiceImpl")
	private IFiArrearsetService fiArrearsetService;
	
	//�ɱ���
	@Resource(name="fiCostHibernateDaoImpl")
	private IFiCostDao fiCostDao;
	
	//�ⷢ�ɱ�
	@Resource(name="fiOutCostHibernateDaoImpl")
	private IFiOutCostDao fiOutCostDao;
	
	//�ⷢ�ɱ�
	@Resource(name="fiOutCostServiceImpl")
	private IFiOutCostService fiOutCostService;
	
	//�ɱ���˽ڵ�
	@Value("${fiAuditCost.log_auditCost}")
	private Long log_auditCost ;
	
	//��ʷ������¼Service
	@Resource(name = "oprHistoryServiceImpl")
	private IOprHistoryService oprHistoryService;
	
	//�ڲ�Э���
	@Resource(name = "fiInternalRateServiceImpl")
	private IFiInternalRateService fiInternalRateService;
	
	//�ڲ�����Э���
	@Resource(name = "fiInternalSpecialRateServiceImpl")
	private IFiInternalSpecialRateService fiInternalSpecialRateService;
	
	
	@ModuleName(value="ҵ����ò���ӿ�",logType=LogType.fi)
	public String addFinanceInfo(List<FiInterfaceProDto> listfiInterfaceDto)
			throws Exception {
		for (FiInterfaceProDto fiInterfaceProDto : listfiInterfaceDto) {
				if (fiInterfaceProDto.getCustomerId() == null||"".equals(fiInterfaceProDto.getCustomerId() == null))
					throw new ServiceException("���������ID!");
				Customer cst = this.customerDao.get(fiInterfaceProDto.getCustomerId());
				//REVIEW ��Ҫ���ǿ��ж�
				//FIXED �Ѵ���
				if(cst==null) {
					throw new ServiceException("�����ڿ��̹����в�����");
				}
				String settlement = cst.getSettlement();// ���㷽ʽ(�ֽ�\�½�\Ԥ��)
				if("".equals(settlement)||settlement==null){
					throw new ServiceException("���㷽ʽ�����ڣ�����ϵ����Ա!");
				}
				if ("�ֽ�".equals(settlement)) {
					fiInterfaceProDto.setGocustomerId(cst.getId());
					fiInterfaceProDto.setGocustomerName(cst.getCusName());
					this.addFiPayment(fiInterfaceProDto);
				} else if ("�½�".equals(settlement)) {
					this.addFiReceivabledetail(fiInterfaceProDto);
				} else if ("Ԥ��".equals(settlement)) {
					this.addFiAdvance(fiInterfaceProDto);
				} else {
					throw new ServiceException("���㷽ʽ�����ڣ�����ϵ����Ա!");
				}
		}
		return "����ɹ�!";
	}
	
	//REVIEW-ACCEPT ����ע��
	//FIXED
	/**
	 * ���˵�����Ӧ��Ӧ���ӿ�
	 */
	public String reconciliationToFiPayment(List<FiInterfaceProDto> listfiInterfaceDto)throws Exception{
		for (FiInterfaceProDto fiInterfaceProDto : listfiInterfaceDto) {
			fiInterfaceProDto.setGocustomerId(fiInterfaceProDto.getCustomerId());
			fiInterfaceProDto.setGocustomerName(fiInterfaceProDto.getCustomerName());
			this.addFiPayment(fiInterfaceProDto);
		}
		return "����ɹ�!";
	}
	
	
	//REVIEW-ACCEPT ����ע��
	//FIXED �ӿ�������ע��
	@ModuleName(value="������ò���ӿ�",logType=LogType.fi)
	public String outStockToFi(List<FiInterfaceProDto> listfiInterfaceDto)throws Exception{
		String outStockMode=null;
		for (FiInterfaceProDto fiInterfaceProDto : listfiInterfaceDto) {
			outStockMode=fiInterfaceProDto.getOutStockMode();
			if("��ת".equals(outStockMode)||"�ⷢ".equals(outStockMode)){
				Customer cst = this.customerDao.get(fiInterfaceProDto.getGocustomerId());//�ջ��˿���
				if (cst == null) throw new ServiceException("���̲����ڣ�����ϵ����Ա!");
				String settlement = cst.getSettlement();// ���㷽ʽ(�ֽ�\�½�\Ԥ��)
				if ("�ֽ�".equals(settlement)) {
					fiInterfaceProDto.setPenyType("�ֽ�");
					this.addFiPayment(fiInterfaceProDto);
				} else if ("�½�".equals(settlement)) {
					//REVIEW-ACCEPT �½᲻��Ҫ����setPenyType������
					//FIXED ����Ҫ����
					this.addFiReceivabledetail(fiInterfaceProDto);
				} else if ("Ԥ��".equals(settlement)) {
					fiInterfaceProDto.setPenyType("Ԥ��");
					this.addFiAdvance(fiInterfaceProDto);
				} else {
					throw new ServiceException("���㷽ʽ�����ڣ�����ϵ����Ա!");
				}
			}else if("�����ͻ�".equals(outStockMode)||"ר��".equals(outStockMode)||"��������".equals(outStockMode)||"��������".equals(outStockMode)){//��������
				fiInterfaceProDto.setPenyType("�ֽ�");
				this.addFiPayment(fiInterfaceProDto);
			}else if(!"���Ž���".equals(outStockMode)) {
				throw new ServiceException("���ⷽʽ�����ڣ�����ϵ����Ա!");
			}
		}
		return "����ɹ�!";
	}
	
	
	/**
	 * Ԥ����¼
	 */
	private String addFiAdvance(FiInterfaceProDto fpd) throws Exception {
		List<FiAdvanceset> list = fiAdvancesetDao.find("from FiAdvanceset f where f.customerId=? and f.isdelete=1L",fpd.getCustomerId());
		if(list.size()==0){
			throw new ServiceException("�˿�����Ԥ��������в�����!");
		}
		this.addFiPayment(fpd);
		/*		FiAdvanceset fat = list.get(0);

		Double openingBalance = fat.getOpeningBalance();// �˺����
		if (openingBalance < 0)
			throw new ServiceException("Ԥ������㣬������Ԥ����֧��!");
		

		Long customerId = fpd.getCustomerId();// ����ID(�������)
		String customerName = fpd.getCustomerName();// ��������(�������)
		Long settlementType = fpd.getSettlementType();// �ո�����(1:��2:ȡ��)(�������)
		Double settlementAmount = fpd.getAmount(); // ������(�������)
		String sourceData = fpd.getSourceData();// ������Դ(�������)
		Long sourceNo = fpd.getSourceNo();// ��Դ����(�������)

		Customer cst = this.customerDao.get(fpd
				.getCustomerId());
		if (cst == null)
			throw new ServiceException("���̲����ڣ�����ϵ����Ա!");
		if (customerId == null) {
			throw new ServiceException("Ԥ����ʵ���п���ID����Ϊ��!");
		}

		if (settlementAmount <= 0.0||settlementAmount==null)
			throw new ServiceException("�������տ���!");
		
		if ("".equals(customerName)||customerName==null) {
			throw new ServiceException("�������Ʋ���Ϊ��");
		}
		if ("".equals(sourceData)||sourceData==null) {
			throw new ServiceException("������������Դ!");
		}
		if ("".equals(sourceNo)||sourceNo == null) {
			throw new ServiceException("��������Դ����!");
		}
		if (settlementAmount > openingBalance) {
			throw new ServiceException("Ԥ������㣬������Ԥ����֧��!");
		}

		if (settlementType != 1L || settlementType != 2L) {
			throw new ServiceException("Ԥ����ʵ����������ȷ�ո�����!");
		}
		FiAdvance fa = new FiAdvance();
		fa.setFiAdvanceId(fat.getId());
		fa.setSettlementAmount(settlementAmount);
		if(settlementType == 1L){
			fa.setSettlementBalance(DoubleUtil.add(fat.getOpeningBalance(), settlementAmount));
			fat.setOpeningBalance(DoubleUtil.add(fat.getOpeningBalance(), fa.getSettlementAmount()));
		}else{
			fa.setSettlementBalance(DoubleUtil.sub(fat.getOpeningBalance(), settlementAmount));
			fat.setOpeningBalance(DoubleUtil.sub(fat.getOpeningBalance(), fa.getSettlementAmount()));
		}
		this.fiAdvancesetDao.save(fat);
		this.fiAdvanceDao.save(fa);
*/
		return "";
	}

	/**
	 * �½�д��Ƿ����ϸ
	 */
	private String addFiReceivabledetail(FiInterfaceProDto fpd)
			throws Exception {
		User user=WebRalasafe.getCurrentUser(Struts2Utils.getRequest());
		Long dno = fpd.getDno();// ���͵���(�������)
		Double amount = fpd.getAmount();// ���(�������)
		String sourceData = fpd.getSourceData();// ������Դ
		Long sourceNo = fpd.getSourceNo();// ��Դ����(�������)
		Long paymentType = fpd.getSettlementType(); // �ո�����(1:�տ�/2:����)
		Long customerId = fpd.getCustomerId();// ����ID(�������)
		String customerName = fpd.getCustomerName();// ��������(�������)
		String costType = fpd.getCostType();// ��������(�������)
		String flightmainno=fpd.getFlightMainNo();//������
		Long incomeDepartId=fpd.getIncomeDepartId();//���벿��

		Customer cst = this.customerDao.get(fpd.getCustomerId());
		
		if (cst == null)
			throw new ServiceException("���̲����ڣ�����ϵ����Ա!");
		
		if("��������".equals(cst.getCustprop())&&!"���ջ���".equals(costType)){
			List<FiArrearset> list=this.fiArrearsetService.find("from FiArrearset f where f.customerId=? and f.departId=? and isDelete=0",cst.getId(),Long.valueOf(user.get("bussDepart")+""));
			//REVIEW-ACCEPT ���ǿ�
			//FIXED �Ѵ���
			if(list==null|| list.size()==0) throw new ServiceException("����<"+cst.getCusName()+">��<����Ƿ������>�в����ڣ�����ϵ����Ա!");
			if(list.size()>1) throw new ServiceException("����<"+cst.getCusName()+">��<����Ƿ������>�д��ڴ��ڶ�����¼������ϵ����Ա!");
		}
		
		if(paymentType==null){
			throw new ServiceException("�������ո�����!");
		}
		if (paymentType != 1L&& paymentType != 2L) {
			throw new ServiceException("�������ո�����!");
		}
		if(!"��������".equals(sourceData)){
			if (dno==null||"".equals(dno)){
				throw new ServiceException("���������͵���!");
			}
		}
		if (amount == 0.0||amount==null)
			throw new ServiceException("������Ƿ����!");
		if ("".equals(sourceData))
			throw new ServiceException("������������Դ!");
		if (sourceNo==null)
			throw new ServiceException("��������Դ����!");
		if (costType==null)
			throw new ServiceException("�������������!");

		FiReceivabledetail frb = new FiReceivabledetail();
		frb.setDno(dno); 
		frb.setFlightmainno(flightmainno);
		frb.setAmount(amount);
		frb.setSourceData(sourceData);
		frb.setSourceNo(sourceNo);
		frb.setPaymentType(paymentType);
		frb.setCustomerId(customerId);
		frb.setCustomerName(customerName);
		frb.setCostType(costType);
		frb.setReviewStatus(1L);
		if(incomeDepartId!=null){
			frb.setDepartId(incomeDepartId);
		}
		this.fiReceivabledetailDao.save(frb);
		return "";
	};

	/**
	 * �ֽ�д��Ӧ��Ӧ��
	 */
	private String addFiPayment(FiInterfaceProDto fpd) throws Exception {
		Long paymentType = fpd.getSettlementType();// �ո�����(1:�տ/2:���)(�������)
		String costType = fpd.getCostType(); // ��������:���ջ���/�������ͷ�/������ֵ��/Ԥ�����ͷ�/Ԥ����ֵ��/��������/����(�������)
		String documentsType = fpd.getDocumentsType(); // ���ݴ���:����\�ɱ�\����\Ԥ���\���ջ���(�������)
		String documentsSmalltype = fpd.getDocumentsSmalltype(); // ����С�ࣺ���͵�/���˵�/���͵�/Ԥ��(�������)
		Long documentsNo = fpd.getDocumentsNo(); // ����С���Ӧ�ĵ���(�������)
		String penyType=null ; // ��������(�ֽᡢ�½�)(�������)
		Double amount = fpd.getAmount();// ���(�������)
		Long customerId = fpd.getGocustomerId();// �ջ��˿���ID(�������)
		String customerName = fpd.getGocustomerName(); // �ջ��˿��̱�����(�������)
		String sourceData = fpd.getSourceData(); // ������Դ(�������)
		Long sourceNo = fpd.getSourceNo(); // ��Դ����(�������)
		String collectionUser = fpd.getCollectionUser(); // �տ�������:���᣺�����ˣ��ͻ����ͻ�Ա���ⷢ���ⷢԱ(�������)
		String createRemark=fpd.getCreateRemark(); //ժҪ
		String contacts=fpd.getContacts();//������λ(���û�ջ��˿��̣�������λΪ�ջ�������)
		Long paymentStatus; // �ո�״̬��0���ϡ�1δ�տ2���տ3�����տ4δ���5�Ѹ��6���ָ��7����תǷ�8�쳣
		Long disDepartId=fpd.getDisDepartId();//���Ͳ���
		Long incomeDepartId=fpd.getIncomeDepartId();//���벿��
		Long departId = fpd.getDepartId();
		String departName = fpd.getDepartName();
		
		if (paymentType == 1L) {
			paymentStatus = 1L;
		} else if (paymentType == 2L) {
			paymentStatus = 4L;
		} else {
			throw new ServiceException("�������ո�����!");
		}

		if (paymentType.equals("")||paymentType==null) {
			throw new ServiceException("�������ո�����!");
		} else if (paymentType != 1L && paymentType != 2L) {
			throw new ServiceException("�ո�����ֻ��Ϊ�տ�򸶿!");
		}
		
		if(("".equals(customerId)||customerId==null)&&("".equals(contacts)||contacts==null)){
			throw new ServiceException("�����ա��������������̻�������λ!");
		}
		
			
		if ("".equals(costType)||costType==null)
			throw new ServiceException("�������������!");
		if ("".equals(documentsType)||documentsType==null)
			throw new ServiceException("�����뵥�ݴ���!");
		if ("".equals(documentsSmalltype)||documentsSmalltype==null)
			throw new ServiceException("�����뵥��С��!");
		if ("".equals(documentsNo)||documentsNo==null)
			throw new ServiceException("�����뵥�ݺ�!");

		if (amount==null)
			throw new ServiceException("�������տ���!");
		if ("".equals(sourceData)||sourceData==null)
			throw new ServiceException("������������Դ!!");
		if ("".equals(sourceNo)||sourceNo==null)
			throw new ServiceException("��������Դ����!");
		
		
		if (paymentType == 1L) {
			if (("".equals(collectionUser)||collectionUser==null))
				throw new ServiceException("�տ������˲���Ϊ��!");
		}
		
		if((null!=customerId &&customerId!=0)&&(penyType==null||"".equals(penyType))){
			Customer cst = this.customerDao.get(customerId);
			penyType=cst.getSettlement();
		}else{
			penyType="�ֽ�";
		}
		
		FiPayment fpt = new FiPayment();
		fpt.setPaymentType(paymentType);
		fpt.setCostType(costType);
		fpt.setDocumentsType(documentsType);
		fpt.setDocumentsSmalltype(documentsSmalltype);
		fpt.setDocumentsNo(documentsNo);
		fpt.setPenyType(penyType);
		fpt.setPaymentStatus(paymentStatus);
		fpt.setCustomerId(customerId);
		fpt.setCustomerName(customerName);
		fpt.setSourceData(sourceData);
		fpt.setSourceNo(sourceNo);
		fpt.setCollectionUser(collectionUser);
		fpt.setAmount(amount);
		fpt.setCreateRemark(createRemark);
		fpt.setContacts(contacts);
		fpt.setIncomeDepartId(incomeDepartId);
		fpt.setDepartId(departId);
		fpt.setDepartName(departName);
		if(disDepartId!=null){
			fpt.setDepartId(disDepartId);
		}
		this.fiPaymentDao.save(fpt);
		return "";
	}
	
	//REVIEW-ACCEPT ����ע��
	//FIXED �ӿ�������ע��
	public String currentToFiPayment(List<FiInterfaceProDto> listfiInterfaceDto)throws Exception{
		for (FiInterfaceProDto fiInterfaceProDto : listfiInterfaceDto) {
			fiInterfaceProDto.setGocustomerId(fiInterfaceProDto.getCustomerId());
			fiInterfaceProDto.setGocustomerName(fiInterfaceProDto.getCustomerName());
			this.addFiPayment(fiInterfaceProDto);
		}
		return "";
	}
	//REVIEW-ACCEPT ����ע��
	//FIXED �ӿ�������ע��
	public String internalCostToFi(List<FiInterfaceProDto> listfiInterfaceDto)throws Exception{
		for(FiInterfaceProDto fiInterfaceProDto:listfiInterfaceDto){
			Long startDepartId=fiInterfaceProDto.getStartDepartId();//ʼ������ID
			String startDepartName=fiInterfaceProDto.getStartDepartName(); //ʼ������
			Long endDepartId=fiInterfaceProDto.getEndDepartId(); //�ն˲���ID
			String endDepartName=fiInterfaceProDto.getEndDepart();//�ն˲���
			String sourceData=fiInterfaceProDto.getSourceData();//������Դ(����ȷ�ϡ�����ȷ��)
			Long sourceNo=fiInterfaceProDto.getSourceNo();//��Դ����
			Long dno=fiInterfaceProDto.getDno();// ���͵���(�������)
			Double flightWeight=fiInterfaceProDto.getFlightWeight();//����
			Long flightPiece=fiInterfaceProDto.getFlightPiece();//����
			Double bulk=fiInterfaceProDto.getBulk();//���
			Long customerId = fiInterfaceProDto.getCustomerId();// ����ID
			String outStockMode = fiInterfaceProDto.getOutStockMode();//������ò���ӿڣ����ⷽʽ(�°�����/�°������ͻ�/�°���ͻ�/��ת����/��ת�����ͻ�/��ת�����ͻ�/�ⷢ����/�ⷢ�ͻ�)
			String distributionMode=fiInterfaceProDto.getDistributionMode();// ���ͷ�ʽ(�°�/��ת/�ⷢ)
			
			
			if("".equals(startDepartId)||"".equals(startDepartName)) throw new  ServiceException("�����ڲ�����ʧ��,������ʼ������!");
			if("".equals(endDepartId)||"".equals(startDepartName)) throw new  ServiceException("�����ڲ�����ʧ��,�����뵽�ﲿ��!");
			
			if("".equals(sourceData)) throw new  ServiceException("������������Դ");
			if("".equals(sourceNo)) throw new  ServiceException("��������Դ����");
			if("".equals(customerId)) throw new  ServiceException("���������ID");
			if("".equals(dno)) throw new  ServiceException("���������͵���");
			if(flightWeight<=0.0) throw new  ServiceException("����������");
			if("".equals(flightPiece)) throw new  ServiceException("���������");
			//if("".equals(bulk)||bulk<=0.0) throw new  ServiceException("���������");
			
			//��������Э��ۼƻ�
			FiInternalDetail fiInternalDetail=this.fiInternalSpecialRateService.calculateCost(customerId, flightWeight, flightPiece, bulk);
			
			//�������Э��۲����ڣ����������Э��ۼ���
			if(fiInternalDetail!=null){
				fiInternalDetail.setAgreementType(48251L);//����Э���
			}else{
				fiInternalDetail=fiInternalRateService.calculateCost(startDepartId, endDepartId, dno, flightWeight, outStockMode);
				if(fiInternalDetail!=null){
					fiInternalDetail.setAgreementType(48250L);//��׼Э���
				}
			}
			
			if(fiInternalDetail!=null){
				if("����ȷ��".equals(sourceData)){
					fiInternalDetail.setDno(dno);
					fiInternalDetail.setStartDepartId(startDepartId);
					fiInternalDetail.setStartDepartName(startDepartName);
					fiInternalDetail.setEndDepartId(endDepartId);
					fiInternalDetail.setEndDepartName(endDepartName);
					fiInternalDetail.setSourceData(sourceData);
					fiInternalDetail.setSourceNo(sourceNo);
					fiInternalDetail.setSettlementType(2L);//�ɱ�
					fiInternalDetail.setBelongsDepartId(startDepartId);
					fiInternalDetail.setBelongsDepartName(startDepartName);
					fiInternalDetail.setDistributionMode(distributionMode);
					fiInternalDetail.setCustomerId(customerId);
				}else if("����ȷ��".equals(sourceData)){
					fiInternalDetail.setDno(dno);
					fiInternalDetail.setStartDepartId(startDepartId);
					fiInternalDetail.setStartDepartName(startDepartName);
					fiInternalDetail.setEndDepartId(endDepartId);
					fiInternalDetail.setEndDepartName(endDepartName);
					fiInternalDetail.setSourceData(sourceData);
					fiInternalDetail.setSourceNo(sourceNo);
					fiInternalDetail.setSettlementType(1L);//����
					fiInternalDetail.setBelongsDepartId(endDepartId);
					fiInternalDetail.setBelongsDepartName(endDepartName);
					fiInternalDetail.setDistributionMode(distributionMode);
					fiInternalDetail.setCustomerId(customerId);
				}else{
					throw new  ServiceException("������Դ����!");
				}
				this.fiInternalDetailService.save(fiInternalDetail);
			}else{
				//�տ������־
				oprHistoryService.saveFiLog(sourceNo, "ʵ�䵥��"+sourceNo+"�ڲ��ɱ�Э��۲�����!" , 34L);
				throw new ServiceException("�ڲ�Э��۲����ڣ�");
				
			}
		}
		return "";
	}

	public String invalidInternalCostToFi(List<FiInterfaceProDto> listfiInterfaceDto)throws Exception{
		String sourceData=null;//������Դ(����ȷ�ϡ�����ȷ��)
		Long sourceNo=null;//��Դ����
		String hql=null;
		for(FiInterfaceProDto fiInterfaceProDto:listfiInterfaceDto){
			sourceData=fiInterfaceProDto.getSourceData();
			sourceNo=fiInterfaceProDto.getSourceNo();
			if("".equals(sourceData)) throw new  ServiceException("������������Դ");
			if("".equals(sourceNo)) throw new  ServiceException("��������Դ����");
			hql="update FiInternalDetail f set f.status=0 where f.sourceData=? and f.sourceNo=?";
			this.fiInternalDetailService.batchExecute(hql, sourceData,sourceNo);
		}
		return "";
	}
	
	//REVIEW-ANN ����ע��
	//FIXED �ӿ�����ע��
	@ModuleName(value="����ȷ�ϵ��ò�������ɱ��ӿ�",logType=LogType.fi)
	public String storageToFiDeliverCost(List<FiInterfaceProDto> listfiInterfaceDto)throws Exception{
		for(FiInterfaceProDto fiInterfaceProDto:listfiInterfaceDto){
			Long customerId=fiInterfaceProDto.getCustomerId();// �����ID(�ֹ�����ʼ����)
			String customerName=fiInterfaceProDto.getCustomerName();// ���������(�ֹ�����ʼ��������)
			String flightMainNo=fiInterfaceProDto.getFlightMainNo();//�ֶ�����(������)
			double flightWeight=fiInterfaceProDto.getFlightWeight();//�Ƶ�����
			Long flightPiece=fiInterfaceProDto.getFlightPiece();//�Ƶ�����
			String startcity=fiInterfaceProDto.getStartcity();//ʼ��վ
			
//			String faxMainNo;//������(������)
			String goodsType =fiInterfaceProDto.getGoodsType();  //��������
			String sourceData=fiInterfaceProDto.getSourceData();
			Long matStatus;//ƥ��״̬(1:�Զ�ƥ�䡢2:�ֶ�ƥ�䡢0:δƥ��)
			double faxWeight=0.0; //��������(������)
			double flightAmount; //�Ƶ����(�Ƶ�����*����)
			double boardAmount = 0.0;//���
			double price;//����
			Long isLowestStatus;//�Ƿ����һƱ(1:�ǣ�0:����)
			double diffWeight=0.0;//��������(�Ƶ�����-��������)
			double diffAmount;//������(�Ƶ�����*����-��������*����)
			double lowest;//���һƱ��
			
			if("".equals(customerId)) throw new  ServiceException("������ʼ����");
			if("".equals(flightMainNo)) throw new  ServiceException("������������");
			if(flightWeight<=0.0) throw new  ServiceException("����������");
			if(flightPiece<=0L) throw new  ServiceException("���������");
			
			if("".equals(goodsType)||goodsType==null){
				throw new  ServiceException("�������������");
			}
			OprFaxMain oprFaxMain=this.oprFaxMainService.findFiDeliveryByMatchStatus(flightMainNo);  //���ҳ�δƥ����������ݽ���ƥ�䣬����һ��

			String returnMsg="";
			FiDeliverycost fiDeliverycost=new FiDeliverycost();
			fiDeliverycost.setFlightMainNo(flightMainNo);
			fiDeliverycost.setFlightWeight(flightWeight);
			fiDeliverycost.setFlightPiece(flightPiece);
			fiDeliverycost.setGoodsType(goodsType);
			fiDeliverycost.setStartcity(startcity);
	
			List<FiDeliveryPrice> fiDeliveryPricelist=this.fiDeliveryPriceService.getPriceByCustomerId(customerId,goodsType);
			if(fiDeliveryPricelist.size()!=1){
				throw new  ServiceException("����ȡ���Э��۵�ʱ���������");
			}
			
			FiDeliveryPrice fiDeliveryPrice=fiDeliveryPricelist.get(0);//�����Э���
			

			price=fiDeliveryPrice.getRates();//����
			lowest=fiDeliveryPrice.getLowest();//���һƱ
			flightAmount=DoubleUtil.mul(flightWeight, price);//�Ƶ����(�Ƶ�����*����)
			if(flightAmount<=lowest){
				isLowestStatus=1L;
				fiDeliverycost.setFlightAmount(lowest);
			}else{
				isLowestStatus=0L;
				fiDeliverycost.setFlightAmount(flightAmount);
			}
			
			if(fiDeliveryPrice.getIsBoardStatus()==1){
				if(flightWeight<=200.0&&flightWeight>100.0){
					boardAmount=fiDeliveryPrice.getBoard1();
				}else if(flightWeight<=300.0&&flightWeight>200.0){
					boardAmount=fiDeliveryPrice.getBoard2();
				}else if(flightWeight<=400.0&&flightWeight>300.0){
					boardAmount=fiDeliveryPrice.getBoard3();
				}else if(flightWeight<=500.0&&flightWeight>400.0){
					boardAmount=fiDeliveryPrice.getBoard4();
				}else if(flightWeight<=1000.0&&flightWeight>500.0){
					boardAmount=fiDeliveryPrice.getBoard5();
				}else if(flightWeight>1000.0){
					boardAmount=fiDeliveryPrice.getBoard6();
				}else{
					boardAmount=0.0;
				}
			}

			fiDeliverycost.setIsLowestStatus(isLowestStatus);
			fiDeliverycost.setPrice(price);
			fiDeliverycost.setStatus(0l);
			fiDeliverycost.setBoardAmount(boardAmount);
			fiDeliverycost.setAmount(DoubleUtil.add(fiDeliverycost.getFlightAmount(), boardAmount));
			
			if(oprFaxMain==null){
				matStatus=0L;
				returnMsg="����ɹ����Ƶ�δ���Ҷ�Ӧ����������";
			}else{
				matStatus=1L;
				diffAmount=Math.ceil(DoubleUtil.mul(price,DoubleUtil.sub(fiDeliverycost.getFlightWeight(),oprFaxMain.getTotalWeight())));
				fiDeliverycost.setDiffAmount(diffAmount);
				fiDeliverycost.setFaxId(oprFaxMain.getId()+"");
				
				diffWeight=Math.ceil(DoubleUtil.sub(fiDeliverycost.getFlightWeight(),oprFaxMain.getTotalWeight()));
				fiDeliverycost.setDiffWeight(Math.ceil(DoubleUtil.sub(fiDeliverycost.getFlightWeight(),oprFaxMain.getTotalWeight())));
				if(diffWeight==0.0){
					returnMsg="����ɹ���ƥ��ɹ�,����������";
				}else{
					returnMsg="����ɹ���ƥ��ɹ�,����������";
				}
				
				fiDeliverycost.setFaxPiece(oprFaxMain.getTotalPiece());
				fiDeliverycost.setFaxMainNo(oprFaxMain.getFlightMainNo());
				fiDeliverycost.setFaxWeight(Math.ceil(oprFaxMain.getTotalWeight()));
				
				oprFaxMain.setMatchStatus(1l);   //�޸�ƥ��״̬ �Ѿ�ƥ����
				oprFaxMainService.save(oprFaxMain);
			}
			
			fiDeliverycost.setMatStatus(matStatus);
			fiDeliverycost.setCustomerId(customerId);
			fiDeliverycost.setCustomerName(customerName);
			
			List<FiDeliverycost>list = fiDeliverycostService.find("from FiDeliverycost  fi where fi.flightMainNo=? and fi.customerId=? ", fiDeliverycost.getFlightMainNo(),fiDeliverycost.getCustomerId());
			//REVIEW-ACCEPT ���ǿ��ж�
			//FIXED
			if(list==null){
				throw new ServiceException("δ�ҵ�����ɱ�����");
			}
			if(list.size()>0){  // �ж�һ�£������¼��Ƶ�������¼��ȥ�ˡ�
				return "�ҵ������Ƶ����ݣ�δ��ƥ��ɹ�";
			}
			this.fiDeliverycostService.save(fiDeliverycost);
			
			
			if(oprFaxMain!=null&&diffWeight>0.0){
				//�������ش���   ��ƥ���ʱ�����������ش���
				List<OprFaxIn> listMain = oprFaxInService.find("from OprFaxIn oi where oi.faxMainId=? and oi.status=1 ",oprFaxMain.getId());
				OprFaxIn oprFaxIn =listMain.get(0);
				
				CusOverweightManager cusMan=null;
				List<CusOverweightManager> cuslist=this.cusOverManagerService.findBy("cusId", oprFaxIn.getCusId());
				if(cuslist.size()==0){
					List<CusOverweightManager> listM =cusOverManagerService.find("from CusOverweightManager cs where cs.cusId is null or cs.cusId=0");
					if(listM.size()==0){
						throw new ServiceException("������������ģ��¼���������������Ϣ");
					}else{
						cusMan=listM.get(0);
					}
				}else{
					cusMan=cuslist.get(0);
				}
				
				if(diffWeight>cusMan.getLowWeight()){//д���������ر�
					OprOverweight oprOverweight=new OprOverweight();
					oprOverweight.setCustomerId(oprFaxIn.getCusId());
					oprOverweight.setCustomerName(oprFaxIn.getCpName());
					oprOverweight.setWeight(diffWeight);
					oprOverweight.setFlightMainNo(fiDeliverycost.getFaxMainNo());
					oprOverweight.setDepartId(oprFaxMain.getDepartId());
					oprOverweight.setDepartName(oprFaxMain.getDepartName());
					oprOverweight.setFaxWeight(fiDeliverycost.getFaxWeight());
					oprOverweight.setFlightWeight(fiDeliverycost.getFlightWeight());
					oprOverweight.setAmount(Math.ceil(DoubleUtil.mul(diffWeight, cusMan.getOverweightRate())));
					oprOverweight.setRate(cusMan.getOverweightRate());
					oprOverweight.setStatus(1l);
					cusOverWeightService.save(oprOverweight);
				}
			}
		}
		return "�Ƶ����ݺ�ϵͳ��������ƥ��ɹ�";
	}
	
	
	//REVIEW-ACCEPT ����ע��
	//FIXED
	/**
	 * ����������ò���ӿ�
	 */
	@ModuleName(value="����������ò���ӿ�",logType=LogType.fi)
	public String changeToFi(List<FiInterfaceProDto> listfiInterfaceDto)throws Exception{
		for (FiInterfaceProDto fiInterfaceProDto : listfiInterfaceDto) {
			String costType=fiInterfaceProDto.getCostType();//ԭ��������
			if("Ԥ�����ͷ�".equals(costType)||"Ԥ����ֵ��".equals(costType)||"Ԥ��ר����".equals(costType)){
				fiInterfaceProDto.setWhoCash("Ԥ��");
				this.changePrepaid(fiInterfaceProDto);
			}else if("�������ͷ�".equals(costType)||"������ֵ��".equals(costType)||"����ר����".equals(costType)){
				fiInterfaceProDto.setWhoCash("����");
				this.changeTopay(fiInterfaceProDto);
			}else if("��ת��".equals(costType)){
				this.changeTotransit(fiInterfaceProDto);
			}else if("�ⷢ��".equals(costType)){//�ⷢ
				this.changeToOutCost(fiInterfaceProDto);
			}else if("���ջ���".equals(costType)){
				this.inStockCollectionToFi(fiInterfaceProDto);
			}else{
				throw new ServiceException("���ķ������Ͳ���δ���ã�����ϵ����Ա!");
			}
		}
		return "����ɹ�!";
	}
	
	//����ǰ���ջ������
	private void inStockCollectionToFi(FiInterfaceProDto fiInterfaceProDto) throws Exception{
		Long dno=fiInterfaceProDto.getDno();//���͵���
		String costType=fiInterfaceProDto.getCostType();//ԭ��������
		Long customerId=fiInterfaceProDto.getCustomerId();//��������
		Long gocustomerId=fiInterfaceProDto.getGocustomerId();// ����ID(��ת���ⷢ����ID)
		Long stockStatus=fiInterfaceProDto.getStockStatus();//0δ����,1�ѳ���
		Long incomeDepartId=fiInterfaceProDto.getIncomeDepartId();//���벿��(¼������ID)
		//Long disDepartId=fiInterfaceProDto.getDisDepartId();//���Ͳ���ID
		
		Customer precst=this.customerDao.get(fiInterfaceProDto.getPreCustomerId());//����ǰ��������
		Customer cst = this.customerDao.get(customerId);//���ĺ󷢻�����
		if (cst == null) throw new ServiceException("�����������ڣ�����ϵ����Ա!");
		//Double amount=0.0;//����ǰ�ܽ��
		Double amount=fiInterfaceProDto.getAmount();//���ĺ���
		double beforeAmount=fiInterfaceProDto.getBeforeAmount();//����ǰ�ܽ��
		double diffAmount=DoubleUtil.sub(amount,beforeAmount);//���Ĳ��
		
		//������ջ����(Ӧ��)
		if(precst.getId()==cst.getId()&&diffAmount!=0.0){//ͬһ���̸��Ľ��
			FiInterfaceProDto fpd=new FiInterfaceProDto();
			fpd.setCustomerId(cst.getId());
			fpd.setCustomerName(cst.getCusName());
			fpd.setDno(fiInterfaceProDto.getDno());
			fpd.setAmount(diffAmount);
			fpd.setSourceData(fiInterfaceProDto.getSourceData());
			fpd.setSourceNo(fiInterfaceProDto.getSourceNo());
			fpd.setCostType(costType);
			fpd.setSettlementType(2L);//��С�Ĵ�Ӧ������
			if(incomeDepartId!=null){
				fpd.setIncomeDepartId(incomeDepartId);
			}
			this.addFiReceivabledetail(fpd);
		}else{//��ͬ����
		      List<FiInterfaceProDto> listfiInterfaceDto=new ArrayList<FiInterfaceProDto>();
		      //ԭ���̷���д�븺��¼
		      if(beforeAmount!=0.0){
					FiInterfaceProDto prefpd=new FiInterfaceProDto();
					prefpd.setCustomerId(precst.getId());
					prefpd.setCustomerName(precst.getCusName());
					prefpd.setDno(fiInterfaceProDto.getDno());
					prefpd.setAmount(beforeAmount*-1);
					prefpd.setSourceData(fiInterfaceProDto.getSourceData());
					prefpd.setSourceNo(fiInterfaceProDto.getSourceNo());
					prefpd.setCostType(costType);
					prefpd.setSettlementType(2L);//��С�Ĵ�Ӧ������
					if(incomeDepartId!=null){
						prefpd.setIncomeDepartId(incomeDepartId);
					}
					this.addFiReceivabledetail(prefpd);
				}
				
		      //�¿���д������¼
		      if(amount!=0.0){
		        FiInterfaceProDto fpd=new FiInterfaceProDto();
				fpd.setCustomerId(cst.getId());
				fpd.setCustomerName(cst.getCusName());
				fpd.setDno(fiInterfaceProDto.getDno());
				fpd.setAmount(amount);
				fpd.setSourceData(fiInterfaceProDto.getSourceData());
				fpd.setSourceNo(fiInterfaceProDto.getSourceNo());
				fpd.setCostType(costType);
				fpd.setSettlementType(2L);//��С�Ĵ�Ӧ������
				if(incomeDepartId!=null){
					fpd.setIncomeDepartId(incomeDepartId);
				}
		        this.addFiReceivabledetail(fpd);
		        }
		}
		
		//�ջ��ˡ���ת�ⷢ���ջ����(Ӧ��)
		if(diffAmount!=0.0&&stockStatus==1L){
			fiInterfaceProDto.setCustomerId(gocustomerId);
			fiInterfaceProDto.setDocumentsType("���ջ���");
			this.changeTopay(fiInterfaceProDto);
		}
	
	}
	
	//Ԥ������
	private void changePrepaid(FiInterfaceProDto fiInterfaceProDto) throws Exception{
		Long dno=fiInterfaceProDto.getDno();//���͵���
		String costType=fiInterfaceProDto.getCostType();//ԭ��������
		Long stockStatus=fiInterfaceProDto.getStockStatus();//0δ����,1�ѳ���
		Customer precst=this.customerDao.get(fiInterfaceProDto.getPreCustomerId());//����ǰ��������
		Customer cst = this.customerDao.get(fiInterfaceProDto.getCustomerId());//���ĺ󷢻�����
		if (cst == null) throw new ServiceException("���̲����ڣ�����ϵ����Ա!");
		double afamount=fiInterfaceProDto.getAmount();//���ĺ���
		double beforeAmount=fiInterfaceProDto.getBeforeAmount();//����ǰ�ܽ��
		double amount=DoubleUtil.sub(afamount,beforeAmount);//���Ĳ��½��-ԭ�����

		if(precst.getId()==cst.getId()&&amount!=0.0){//ͬһ���̸��Ľ��
			//������ϸ����
			FiInterfaceProDto fpd=new FiInterfaceProDto();
			fpd.setCustomerId(cst.getId());
			fpd.setCustomerName(cst.getCusName());
			fpd.setDno(fiInterfaceProDto.getDno());
			fpd.setAmount(amount);
			fpd.setSourceData(fiInterfaceProDto.getSourceData());
			fpd.setSourceNo(fiInterfaceProDto.getSourceNo());
			fpd.setCostType(costType);
			fpd.setSettlementType(1L);
			fpd.setIncomeDepartId(fiInterfaceProDto.getIncomeDepartId());//����/��������ID
			fpd.setIncomeDepart(fiInterfaceProDto.getIncomeDepart());
			fpd.setAdmDepartId(fiInterfaceProDto.getAdmDepartId());
			fpd.setAdmDepart(fiInterfaceProDto.getAdmDepart());
			fpd.setWhoCash(fiInterfaceProDto.getWhoCash());
			
			this.addFiReceivabledetail(fpd);
			
			//�������
			if(!"���ջ���".equals(fpd.getCostType())){
				List<FiInterfaceProDto> listfiInterfaceDto=new ArrayList<FiInterfaceProDto>();
				//fiInterfaceProDto.setAmount(amount);//���
				listfiInterfaceDto.add(fpd);
				this.currentToFiIncome(listfiInterfaceDto);
			}
			
		}else{//���Ŀ��̡����Ľ��
			List<FiInterfaceProDto> listfiInterfaceDto=new ArrayList<FiInterfaceProDto>();
			//ԭ���̷���д�븺��¼
			if(beforeAmount!=0.0){
				FiInterfaceProDto prefpd=new FiInterfaceProDto();
				prefpd.setCustomerId(precst.getId());
				prefpd.setCustomerName(precst.getCusName());
				prefpd.setDno(fiInterfaceProDto.getDno());
				prefpd.setAmount(beforeAmount*-1);
				prefpd.setSourceData(fiInterfaceProDto.getSourceData());
				prefpd.setSourceNo(fiInterfaceProDto.getSourceNo());
				prefpd.setCostType(costType);
				prefpd.setSettlementType(1L);
				prefpd.setIncomeDepartId(fiInterfaceProDto.getIncomeDepartId());//����/��������ID
				prefpd.setIncomeDepart(fiInterfaceProDto.getIncomeDepart());
				prefpd.setAdmDepartId(fiInterfaceProDto.getAdmDepartId());
				prefpd.setAdmDepart(fiInterfaceProDto.getAdmDepart());
				prefpd.setWhoCash(fiInterfaceProDto.getWhoCash());
				this.addFiReceivabledetail(prefpd);
				//ԭ�����������
				if(!"���ջ���".equals(prefpd.getCostType())){
					//fiInterfaceProDto.setAmount(beforeAmount*-1);//����ǰ���
					//fiInterfaceProDto.setCustomerId(precst.getId());//����ǰ����ID
					//fiInterfaceProDto.setCustomerName(precst.getCusName());
					listfiInterfaceDto.add(prefpd);
				}
			}
			
			//�¿���д������¼
			if(afamount!=0.0){
				FiInterfaceProDto fpd=new FiInterfaceProDto();
				fpd.setCustomerId(cst.getId());
				fpd.setCustomerName(cst.getCusName());
				fpd.setDno(fiInterfaceProDto.getDno());
				fpd.setAmount(afamount);
				fpd.setSourceData(fiInterfaceProDto.getSourceData());
				fpd.setSourceNo(fiInterfaceProDto.getSourceNo());
				fpd.setCostType(costType);
				fpd.setSettlementType(1L);
				fpd.setIncomeDepartId(fiInterfaceProDto.getIncomeDepartId());//����/��������ID
				fpd.setIncomeDepart(fiInterfaceProDto.getIncomeDepart());
				fpd.setAdmDepartId(fiInterfaceProDto.getAdmDepartId());
				fpd.setAdmDepart(fiInterfaceProDto.getAdmDepart());
				fpd.setWhoCash(fiInterfaceProDto.getWhoCash());
				this.addFiReceivabledetail(fpd);
				//�¿����������
				if(!"���ջ���".equals(fpd.getCostType())){
					//fiInterfaceProDto.setAmount(afamount);//���ĺ���
					//fiInterfaceProDto.setCustomerId(cst.getId());//���ĺ����ID
					//fiInterfaceProDto.setCustomerName(cst.getCusName());
					listfiInterfaceDto.add(fpd);
				}
			}
			
			//��������
			if(listfiInterfaceDto.size()>0){
				this.currentToFiIncome(listfiInterfaceDto);
			}
		}

	}
	

	//��������
	private void changeTopay (FiInterfaceProDto fiInterfaceProDto) throws Exception{
		String sourceData=fiInterfaceProDto.getSourceData();// ������Դ:��ת�ɱ�(�������)
		Long sourceNo=fiInterfaceProDto.getSourceNo();// ��Դ����:��ת�ɱ�ID(�������)
		Long dno=fiInterfaceProDto.getDno();//���͵���
		Long stockStatus=fiInterfaceProDto.getStockStatus();//0��δ���⣬1���ѳ���
		String costType=fiInterfaceProDto.getCostType();//ԭ��������
		double beforeAmount=fiInterfaceProDto.getBeforeAmount();//����ǰ�ܽ��
		double goamount=fiInterfaceProDto.getAmount();//���ĺ���
		double diffAmount=DoubleUtil.sub(goamount,beforeAmount);//���Ĳ��
		Long customerId=fiInterfaceProDto.getGocustomerId();//��Ӧ��
		Long paymentStatus=null;
		String settlement=null;
		String createRemark=null;
		
		Customer cst = null;
		if(!"".equals(customerId)&&customerId!=null){
			cst=this.customerDao.get(customerId);
		}
		
		if(cst!=null){
			settlement = cst.getSettlement();// ���㷽ʽ(�ֽ�\�½�\Ԥ��)
		}
		//���ı�ע
		createRemark="��������,�������뵥�ţ�"+fiInterfaceProDto.getSourceNo();
		
		if(cst==null||"�ֽ�".equals(settlement)||"Ԥ��".equals(settlement)){
			if(diffAmount!=0.0){
				List<FiPayment> list=this.fiPaymentDao.find("from FiPayment f where f.documentsNo=? and f.documentsSmalltype=? and f.status=1 and f.paymentType=1",dno,"���͵�");
				for(FiPayment fiPayment:list){
						paymentStatus=fiPayment.getPaymentStatus();
						if("ʵ�䵥".equals(fiPayment.getSourceData())||"���ᵥ".equals(fiPayment.getSourceData())){
							sourceData=fiPayment.getSourceData();
							sourceNo=fiPayment.getSourceNo();
						}
				}
				if("".equals(paymentStatus)||paymentStatus==null){
					paymentStatus=1L;
				}
				
				//��������Ӧ��Ӧ���ӿ�
				if(stockStatus==1L){
					if(paymentStatus==1L||diffAmount>0.0){
						fiInterfaceProDto.setDocumentsType(fiInterfaceProDto.getDocumentsType());
						fiInterfaceProDto.setDocumentsSmalltype("���͵�");
						fiInterfaceProDto.setDocumentsNo(dno);
						fiInterfaceProDto.setCostType(costType);
						fiInterfaceProDto.setSettlementType(1L);
						fiInterfaceProDto.setAmount(diffAmount);
						fiInterfaceProDto.setCreateRemark(createRemark);
						fiInterfaceProDto.setSourceData(sourceData);
						fiInterfaceProDto.setSourceNo(sourceNo);
					}else{
						fiInterfaceProDto.setDocumentsType(fiInterfaceProDto.getDocumentsType());
						fiInterfaceProDto.setDocumentsSmalltype("���͵�");
						fiInterfaceProDto.setDocumentsNo(dno);
						fiInterfaceProDto.setCostType(costType);
						fiInterfaceProDto.setSettlementType(2L);
						fiInterfaceProDto.setAmount(Math.abs(diffAmount));
						fiInterfaceProDto.setCreateRemark(createRemark);
						fiInterfaceProDto.setSourceData(sourceData);
						fiInterfaceProDto.setSourceNo(sourceNo);
					}
					this.addFiPayment(fiInterfaceProDto);
				}
				
				//�������
				if(!"���ջ���".equals(fiInterfaceProDto.getCostType())){
					List<FiInterfaceProDto> listfiInterfaceDto=new ArrayList<FiInterfaceProDto>();
					fiInterfaceProDto.setAmount(diffAmount);//���
					listfiInterfaceDto.add(fiInterfaceProDto);
					this.currentToFiIncome(listfiInterfaceDto);
				}
			}
		}else{
			/*if (cst == null)
				throw new ServiceException("���̲����ڣ�����ϵ����Ա!");
			if("�½�".equals(settlement)){
				fiInterfaceProDto.setIncomeDepartId(fiInterfaceProDto.getDisDepartId());
				this.changePrepaid(fiInterfaceProDto);
			}else{
				throw new ServiceException("���̶�Ӧ�Ľ��㷽ʽ�����ڣ�����ϵ����Ա!");
			}*/
			throw new ServiceException("�����������½�!");
		}

	}
	
	//��ת�Ѹ���
	private void changeTotransit(FiInterfaceProDto fiInterfaceProDto) throws Exception{
		User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		Long dno=fiInterfaceProDto.getDno();//���͵���
		String costType=fiInterfaceProDto.getCostType();//��������(��ת�ɱ�)
		double beforeAmount=fiInterfaceProDto.getBeforeAmount();//����ǰ�ܽ��
		Double goamount=fiInterfaceProDto.getAmount();//�½��
		double diffAmount=DoubleUtil.sub(goamount,beforeAmount);//���Ĳ��
		Long disDepartId=fiInterfaceProDto.getDisDepartId();//���Ͳ���ID
		Long customerId=fiInterfaceProDto.getGocustomerId();//��ת����ID
		if(dno==null||"".equals(dno)) throw new ServiceException("���͵��Ų�����!");
		

		//�������ת�ɱ�
		List<FiTransitcost> fiTransitcostlist=this.fiTransitService.find("from FiTransitcost f where f.dno=? and f.sourceData=?  and  f.status=1",dno,"����¼��");
		//REVIEW-ACCEPT ���ǿ��ж�
		//FIXED
		if(fiTransitcostlist.size()!=0){
			Customer cst=null;
			if(customerId==null){
				throw new ServiceException("�������Ӧ�Ŀ���ID");
			}else{
				cst = this.customerDao.get(customerId);
				if(cst==null){
					throw new ServiceException("�����ID��Ӧ�Ŀ��̲�����");
				}
			}
			
			Long batchNo=fiTransitService.getBatchNo();
			 FiTransitcost fiTraNew= new FiTransitcost();
			 fiTraNew.setDno(dno);
			 fiTraNew.setAmount(diffAmount);
			 fiTraNew.setCreateName(user.get("name")+"");
			 fiTraNew.setCreateTime(new Date());
			 fiTraNew.setCustomerId(customerId);
			 fiTraNew.setCustomerName(cst.getCusName());
			 fiTraNew.setPayStatus(0l);
			 fiTraNew.setBatchNo(batchNo);
			 fiTraNew.setReviewDate(new Date());
			 fiTraNew.setReviewRemark("����������仯��д����");
			 fiTraNew.setReviewUser(user.get("name")+"");
			 fiTraNew.setSourceData("��������");
			 fiTraNew.setSourceNo(fiInterfaceProDto.getSourceNo());
			 fiTraNew.setStatus(0l);//���״̬
			 fiTransitService.save(fiTraNew);     //д�����������ת�ɱ����������
			
		}
		
	//	if(fiTransitcostlist.size()>0){
		//	throw new ServiceException("�볷�������ٸ��ģ�");
			/*Customer cst=null;
			if(customerId==null){
				throw new ServiceException("�������Ӧ�Ŀ���ID");
			}else{
				cst = this.customerDao.get(customerId);
				if(cst==null){
					throw new ServiceException("�����ID��Ӧ�Ŀ��̲�����");
				}
			}
			String settlement = cst.getSettlement();// ���㷽ʽ(�ֽ�\�½�\Ԥ��)
			
			Long batchNo=fiTransitService.getBatchNo();
			 FiTransitcost fiTraNew= new FiTransitcost();
			 fiTraNew.setDno(dno);
			 fiTraNew.setAmount(diffAmount);
			 fiTraNew.setCreateName(user.get("name")+"");
			 fiTraNew.setCreateTime(new Date());
			 fiTraNew.setCustomerId(customerId);
			 fiTraNew.setCustomerName(cst.getCusName());
			 fiTraNew.setPayStatus(0l);
			 fiTraNew.setBatchNo(batchNo);
			 fiTraNew.setReviewDate(new Date());
			 fiTraNew.setReviewRemark("����������仯��д����");
			 fiTraNew.setReviewUser(user.get("name")+"");
			 fiTraNew.setSourceData("��������");
			 fiTraNew.setSourceNo(fiInterfaceProDto.getSourceNo());
			 fiTraNew.setStatus(1l);//���״̬
			 fiTransitService.save(fiTraNew);     //д�����������ת�ɱ����������
			  
			 oprHistoryService.saveLog(dno, "�����ת�ɱ�(��������)����˽�"+diffAmount, log_auditCost);     //������־

			FiCost fiCostNew = new FiCost();
			fiCostNew.setCostType("��ת�ɱ�");
			fiCostNew.setCostTypeDetail("��������");
			fiCostNew.setCostAmount(diffAmount);
			fiCostNew.setDataSource("��ת�ɱ�");
			fiCostNew.setSourceSignNo(fiTraNew.getId()+"");
			fiCostNew.setDno(dno);
			fiCostNew.setStatus(1l);
			fiCostDao.save(fiCostNew);  //����д��ɱ���
			
				if("�ֽ�".equals(settlement)||"Ԥ��".equals(settlement)){
					if(diffAmount>0.0){
						fiInterfaceProDto.setDocumentsType("�ɱ�");
						fiInterfaceProDto.setDocumentsSmalltype("���͵�");
						fiInterfaceProDto.setDocumentsNo(dno);
						fiInterfaceProDto.setCostType(costType);
						fiInterfaceProDto.setSettlementType(2L);
						fiInterfaceProDto.setAmount(diffAmount);
						fiInterfaceProDto.setSourceNo(fiTraNew.getId());
						fiInterfaceProDto.setCreateRemark("��������");
					}else{
						fiInterfaceProDto.setDocumentsType("�ɱ�");
						fiInterfaceProDto.setDocumentsSmalltype("���͵�");
						fiInterfaceProDto.setDocumentsNo(dno);
						fiInterfaceProDto.setCostType(costType);
						fiInterfaceProDto.setSourceNo(fiTraNew.getId());
						fiInterfaceProDto.setSettlementType(1L);
						fiInterfaceProDto.setAmount(Math.abs(diffAmount));
						fiInterfaceProDto.setCreateRemark("��������");
					}
					this.addFiPayment(fiInterfaceProDto);
				}else if("�½�".equals(settlement)){
					if(goamount!=0.0){
						FiInterfaceProDto fpd=new FiInterfaceProDto();
						fpd.setCustomerId(cst.getId());
						fpd.setCustomerName(cst.getCusName());
						fpd.setDno(fiInterfaceProDto.getDno());
						fpd.setAmount(diffAmount);
						fpd.setSourceData(fiInterfaceProDto.getSourceData());
						fpd.setSourceNo(fiInterfaceProDto.getSourceNo());
						fpd.setCostType(costType);
						fpd.setSettlementType(2L);
						fpd.setDisDepartId(disDepartId);
						this.addFiReceivabledetail(fpd);
					}
				}else{
					throw new ServiceException("���̶�Ӧ�Ľ��㷽ʽ�����ڣ����ڻ�������ά��");
				}*/
	//	}
	}
	//REVIEW-ACCEPT ����ע��
	//FIXED �ӿ�������ע��
	public String currentToFiFiCost(List<FiInterfaceProDto> listfiInterfaceDto)throws Exception{
		double amount=0.0;
		Long dno=0L;
		String sourceData=null;
		Long sourceNo=0L;
		String costType1;//�ɱ�����
		String costTypeDetail;  //�ɱ�С��
		
		for (FiInterfaceProDto fiInterfaceProDto : listfiInterfaceDto) {
			amount=fiInterfaceProDto.getAmount();
			dno=fiInterfaceProDto.getDno();
			sourceData=fiInterfaceProDto.getSourceData();
			sourceNo=fiInterfaceProDto.getSourceNo();
			costType1=fiInterfaceProDto.getCostType1();
			costTypeDetail=fiInterfaceProDto.getCostTypeDetail();
			
			FiCost fiCostNew = new FiCost();
			fiCostNew.setCostType(costType1);
			fiCostNew.setCostTypeDetail(costTypeDetail);
			fiCostNew.setCostAmount(amount);
			fiCostNew.setDataSource(sourceData);
			fiCostNew.setDno(dno);
			fiCostNew.setStatus(1l);
			fiCostDao.save(fiCostNew);  //����д��ɱ���
		}
		return "д��ɱ��ɹ�!";
	}
	
	//�ⷢ�Ѹ���
	private void changeToOutCost(FiInterfaceProDto fiInterfaceProDto) throws Exception{
		User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		Long dno=fiInterfaceProDto.getDno();//���͵���
		String costType=fiInterfaceProDto.getCostType();//��������(��ת�ɱ�)
		double beforeAmount=fiInterfaceProDto.getBeforeAmount();//����ǰ�ܽ��
		Double goamount=fiInterfaceProDto.getAmount();//�½��
		double diffAmount=DoubleUtil.sub(goamount,beforeAmount);
		Long paymentStatus=null;
		Long customerId=fiInterfaceProDto.getGocustomerId();
		StringBuffer sb=new StringBuffer();
		if(dno==null||"".equals(dno)) throw new ServiceException("���͵��Ų�����!");
		
		Customer cst=null;
		if(customerId==null){
			throw new ServiceException("�������Ӧ�Ŀ���ID");
		}else{
			cst = this.customerDao.get(customerId);
			if(cst==null){
				throw new ServiceException("�����ID��Ӧ�Ŀ��̲�����");
			}
		}
		String settlement = cst.getSettlement();// ���㷽ʽ(�ֽ�\�½�\Ԥ��)
		//������ⷢ�ɱ�
		List<FiOutcost> outList=this.fiOutCostDao.find("from FiOutcost fu where fu.dno=? and fu.sourceData=?  and  fu.status=1 and  fu.isdelete=1 ",dno,"����¼��");
		if(outList.size()>0){
			Long batchNo=fiOutCostService.getOutcostBatchNo();
			FiOutcost fiout= new FiOutcost();
			fiout.setDno(dno);
			fiout.setAmount(diffAmount);
			fiout.setCreateName(user.get("name")+"");
			fiout.setCreateTime(new Date());
			fiout.setCustomerId(customerId);
			fiout.setCustomerName(cst.getCusName());
			fiout.setPayStatus(0l);
			fiout.setIsdelete(1l);
			fiout.setBatchNo(batchNo);
			fiout.setSourceNo(fiInterfaceProDto.getSourceNo());
			fiout.setSourceData("��������");
			fiout.setStatus(1l);
			fiOutCostDao.save(fiout);     //д�����������ת�ɱ����������

			FiCost fiCostNew = new FiCost();
			fiCostNew.setCostType("�ⷢ�ɱ�");
			fiCostNew.setCostTypeDetail("��������");
			fiCostNew.setCostAmount(diffAmount);
			fiCostNew.setDataSource("�ⷢ�ɱ�");
			fiCostNew.setDno(dno);
			fiCostNew.setSourceSignNo(fiInterfaceProDto.getSourceNo()+"");
			fiCostNew.setStatus(1l);
			fiCostDao.save(fiCostNew);  //����д��ɱ���
			sb.append("ͨ���������뽫ԭ���:"+beforeAmount+"����Ϊ:"+goamount);
			sb.append("��������IDΪ��"+fiInterfaceProDto.getSourceNo());
			
			if("�ֽ�".equals(settlement)||"Ԥ��".equals(settlement)){
				if(diffAmount>0.0){
					fiInterfaceProDto.setDocumentsType("�ɱ�");
					fiInterfaceProDto.setDocumentsSmalltype("���͵�");
					fiInterfaceProDto.setDocumentsNo(dno);
					fiInterfaceProDto.setCostType(costType);
					fiInterfaceProDto.setSettlementType(2L);
					fiInterfaceProDto.setAmount(diffAmount);
					fiCostNew.setDataSource("�ⷢ�ɱ�");
					fiInterfaceProDto.setSourceNo(batchNo);
					fiInterfaceProDto.setCreateRemark(sb.toString());
				}else{
					fiInterfaceProDto.setDocumentsType("����");
					fiInterfaceProDto.setDocumentsSmalltype("���͵�");
					fiInterfaceProDto.setDocumentsNo(dno);
					fiInterfaceProDto.setCostType(costType);
					fiInterfaceProDto.setSettlementType(1L);
					fiCostNew.setDataSource("�ⷢ�ɱ�");
					fiInterfaceProDto.setSourceNo(batchNo);
					fiInterfaceProDto.setAmount(Math.abs(diffAmount));
					fiInterfaceProDto.setCreateRemark(sb.toString());
				}
				this.addFiPayment(fiInterfaceProDto);

			}else if("�½�".equals(settlement)){
				customerId=fiInterfaceProDto.getCustomerId();
				if("".equals(customerId)||customerId==null) throw new ServiceException("�ⷢ����Id����Ϊ��");

				if(goamount!=0.0){
					FiInterfaceProDto fpd=new FiInterfaceProDto();
					fpd.setCustomerId(cst.getId());
					fpd.setCustomerName(cst.getCusName());
					fpd.setDno(fiInterfaceProDto.getDno());
					fpd.setAmount(diffAmount);
					fpd.setSourceData("�ⷢ�ɱ�");
					fpd.setSourceNo(batchNo);
					fpd.setCreateRemark(sb.toString());
					fpd.setCostType(costType);
					fpd.setSettlementType(2L);
					this.addFiReceivabledetail(fpd);
				}
			}else{
				throw new ServiceException("���̶�Ӧ�Ľ��㷽ʽ�����ڣ����ڻ�������ά��");
			}
			
			}else{
				throw new ServiceException("�ⷢ�ɱ�δ��ˣ�����Ҫ����");
			}
	}
	//REVIEW-ANN ����ע��
	public int invalidToFi(List<FiInterfaceProDto> listfiInterfaceDto) throws Exception {
		int size=0;
		for (FiInterfaceProDto fiInterfaceProDto : listfiInterfaceDto) {
			String settlement=null;
			Customer cst=null;
			if(fiInterfaceProDto.getCustomerId()!=null&&!"".equals(fiInterfaceProDto.getCustomerId())){
				cst = this.customerDao.get(fiInterfaceProDto.getCustomerId());
			}
				if(cst!=null){
					settlement = cst.getSettlement();// ���㷽ʽ(�ֽ�\�½�\Ԥ��)
				}
				if (cst==null||"�ֽ�".equals(settlement)||"Ԥ��".equals(settlement)) {
					size=this.invalidToFiPayment(fiInterfaceProDto);
				} else if ("�½�".equals(settlement)) {
					size=this.invalidToFiReceivabledetail(fiInterfaceProDto);
				} else if ("Ԥ��".equals(settlement)) {
					size=this.invalidToFiPayment(fiInterfaceProDto);
					//this.invalidToFiAdvance(fiInterfaceProDto);
				} else {
					throw new ServiceException("���̽��㷽ʽ�����ڣ�����ϵ����Ա!");
				}
		}
		//return "����ɹ�!";
		return size;
	}

	//REVIEW-ANN ����ע��
	public int invalidToFiPayment(FiInterfaceProDto fiInterfaceProDto)throws Exception{
		String sourceData;// ������Դ
		Long sourceNo;// ��Դ����
		Long status;
		
		sourceData=fiInterfaceProDto.getSourceData();
		sourceNo=fiInterfaceProDto.getSourceNo();
		List<FiPayment> list=null;
		if("�ⷢ�ɱ�".equals(sourceData)){
			String costType=fiInterfaceProDto.getCostType();
			Long documentsNo=fiInterfaceProDto.getDocumentsNo();
			Long customerId=fiInterfaceProDto.getCustomerId();
			if(customerId==null||"".equals(customerId)) throw new ServiceException("����id����Ϊ�գ�");
			Customer cst = this.customerDao.get(customerId);
			if (cst == null) throw new ServiceException("���̲����ڣ�����ϵ����Ա!");
			list=this.fiPaymentDao.find("from FiPayment f where f.costType=? and f.documentsNo=? and f.customerId=? and f.status=1",costType,documentsNo,customerId);
		}else{
			list=this.fiPaymentDao.find("from FiPayment f where f.sourceData=? and f.sourceNo=? and f.status=1",sourceData,sourceNo);
		}
		//REVIEW listʹ��ǰ���ǿ��ж�
		if(list.size()==0){
				///throw new ServiceException("����ʧ�ܣ�û���ҵ���Ӧ�Ĳ������ݣ��������ϣ�");
			return 0;
			}
		for(FiPayment fiPayment:list){
			status=fiPayment.getStatus();
			if(status==0L){
				throw new ServiceException("����ʧ�ܣ�Ӧ�ո����["+fiPayment.getId()+"]�Ѿ����ϣ������ظ����ϣ�");
			}
			if(fiPayment.getPaymentStatus()==1L||fiPayment.getPaymentStatus()==4L){//δ����
				fiPayment.setStatus(0L);
			}else{
				throw new ServiceException("����ʧ�ܣ�Ӧ�ո��������������������ϣ�");
			}
			this.fiPaymentDao.save(fiPayment);
		}

		return list.size();
	}
	//REVIEW-ACCEPT ����ע��
	public String invalidToFiPaymentByDno(FiInterfaceProDto fiInterfaceProDto)throws Exception{
		String sourceData;// ������Դ
		Long sourceNo;// ��Դ����
		Long dno;//���͵���
		String costType;//��������
		String createRemark;
		dno=fiInterfaceProDto.getDno();
		sourceData=fiInterfaceProDto.getSourceData();
		sourceNo=fiInterfaceProDto.getSourceNo();
		createRemark=fiInterfaceProDto.getCreateRemark();
		if(dno==null||"".equals(dno)) throw new ServiceException("���͵��Ų����ڣ�");
		if(sourceData==null||"".equals(sourceData)) throw new ServiceException("������Դ�����ڣ�");
		if(sourceNo==null||"".equals(sourceNo)) throw new ServiceException("��Դ���Ų����ڣ�");
		
		List<FiPayment> list=this.fiPaymentDao.find("from FiPayment f where f.documentsSmalltype='���͵�' and f.documentsNo=? and f.status=1",dno);
		//REVIEW list���ǿ��ж�
		for(FiPayment fiPayment:list){
			costType=fiPayment.getCostType();
			if("���ջ���".equals(costType)||"�������ͷ�".equals(costType)||"����ר����".equals(costType)||"������ֵ��".equals(costType)||"��ת��".equals(costType)){
				if(fiPayment.getPaymentStatus()==1L||fiPayment.getPaymentStatus()==4L){//δ����
					fiPayment.setStatus(0L);
					fiPayment.setCreateRemark(createRemark);
					this.fiPaymentDao.save(fiPayment);
				}else{
					FiPayment fp=new FiPayment();
					fp.setPaymentType(fiPayment.getPaymentType()==1L?2L:1L);
					fp.setReviewStatus(fiPayment.getPaymentType()==1L?1L:0L);//Ĭ�ϸ��Ϊδ��ˣ��տΪ�����
					fp.setPaymentStatus(fp.getPaymentType()==1L?1L:4L);
					fp.setCostType(fiPayment.getCostType());
					fp.setAmount(DoubleUtil.sub(fiPayment.getAmount(),fiPayment.getSettlementAmount()));
					fp.setContacts(fiPayment.getContacts());
					fp.setDocumentsType(fiPayment.getDocumentsType());
					fp.setDocumentsSmalltype(fiPayment.getDocumentsSmalltype());
					fp.setDocumentsNo(fiPayment.getDocumentsNo());
					fp.setSourceData(sourceData);
					fp.setSourceNo(sourceNo);
					fp.setDepartName(fiPayment.getDepartName());
					fp.setDepartId(fiPayment.getDepartId());
					this.fiPaymentDao.save(fp);
				}
			}
		}
				
		return "�����������ϳɹ�!";
	}
	
	//REVIEW-ANN ����ע��
	//FIXED �ӿ�������ע��
	public int invalidToFiReceivabledetail(FiInterfaceProDto fiInterfaceProDto)throws Exception{
		String sourceData;// ������Դ
		Long sourceNo;// ��Դ����
		String costType;//��������
		
		sourceData=fiInterfaceProDto.getSourceData();
		sourceNo=fiInterfaceProDto.getSourceNo();
		costType=fiInterfaceProDto.getCostType();
		List<FiReceivabledetail> list=null;
		
		if("�ⷢ��".equals(costType)){
			Long customerId=fiInterfaceProDto.getCustomerId();
			Long dno=fiInterfaceProDto.getDno();
			if("".equals(dno)||dno==null)  throw new ServiceException("���������͵��ţ�");
			if("".equals(dno)||dno==null)  throw new ServiceException("����ID����Ϊ�գ�");
			list=this.fiReceivabledetailDao.find("from FiReceivabledetail f where f.dno=? and f.customerId=?",dno,customerId);
		}else{
			list=this.fiReceivabledetailDao.find("from FiReceivabledetail f where f.sourceData=? and f.sourceNo=?",sourceData,sourceNo);
		}
		if(list.size()==0){
			//throw new ServiceException("����ʧ�ܣ�û���ҵ���Ӧ�Ĳ������ݣ��������ϣ�");
			return 0;
		}
		for(FiReceivabledetail fiReceivabledetail:list){
			if(fiReceivabledetail.getReconciliationStatus()>1L){
				throw new ServiceException("����ʧ�ܣ�������ϸ����["+fiReceivabledetail.getId()+"]�Ѿ����ˣ��������ϣ�");
			}
			
			//����д�븺��¼
			FiInterfaceProDto fpd=new FiInterfaceProDto();
			fpd.setCustomerId(fiReceivabledetail.getCustomerId());
			fpd.setCustomerName(fiReceivabledetail.getCustomerName());
			fpd.setDno(fiReceivabledetail.getDno());
			fpd.setAmount(fiReceivabledetail.getAmount()*-1);
			fpd.setSourceData(fiInterfaceProDto.getSourceData());
			fpd.setSourceNo(fiInterfaceProDto.getSourceNo());
			fpd.setCostType(fiReceivabledetail.getCostType());
			fpd.setSettlementType(fiReceivabledetail.getPaymentType());
			this.addFiReceivabledetail(fpd);
		}
		return list.size();
	}
	
	//REVIEW-ACCEPT ����ע��
	//FIXED �ӿ�������ע��
	public String invalidToFiReceivabledetailByDno(FiInterfaceProDto fiInterfaceProDto)throws Exception{
		String sourceData;// ������Դ
		Long sourceNo;// ��Դ����
		Long dno;//���͵���
		String costType;//��������
		String createRemark;//��ע
		dno=fiInterfaceProDto.getDno();
		sourceData=fiInterfaceProDto.getSourceData();
		sourceNo=fiInterfaceProDto.getSourceNo();
		createRemark=fiInterfaceProDto.getCreateRemark();
		if(dno==null||"".equals(dno)) throw new ServiceException("���͵��Ų����ڣ�");
		if(sourceData==null||"".equals(sourceData)) throw new ServiceException("������Դ�����ڣ�");
		if(sourceNo==null||"".equals(sourceNo)) throw new ServiceException("��Դ���Ų����ڣ�");
		
		List<FiReceivabledetail> list=this.fiReceivabledetailDao.find("from FiReceivabledetail f where f.dno=?",dno);
		for(FiReceivabledetail fiReceivabledetail:list){
			costType=fiReceivabledetail.getCostType();
			if("���ջ���".equals(costType)||"�������ͷ�".equals(costType)||"������ֵ��".equals(costType)||"����ר����".equals(costType)||"�ⷢ��".equals(costType)){
				//����д�븺��¼
				FiInterfaceProDto fpd=new FiInterfaceProDto();
				fpd.setCustomerId(fiReceivabledetail.getCustomerId());
				fpd.setCustomerName(fiReceivabledetail.getCustomerName());
				fpd.setDno(fiReceivabledetail.getDno());
				fpd.setAmount(fiReceivabledetail.getAmount()*-1);
				fpd.setSourceData(fiInterfaceProDto.getSourceData());
				fpd.setSourceNo(fiInterfaceProDto.getSourceNo());
				fpd.setCostType(fiReceivabledetail.getCostType());
				fpd.setSettlementType(1L);
				fpd.setCreateRemark(createRemark);
				this.addFiReceivabledetail(fpd);
			}
		}
				
		return "";
	}
	
	//REVIEW-ANN ����ע��
	//FIXED �ӿ�������ע��
	public String invalidToFiAdvance(FiInterfaceProDto fiInterfaceProDto)throws Exception{
		String sourceData;// ������Դ
		Long sourceNo;// ��Դ����
		Long status;
		Long settlementType;
		sourceData=fiInterfaceProDto.getSourceData();
		sourceNo=fiInterfaceProDto.getSourceNo();
		List<FiAdvance> list=this.fiAdvanceDao.find("from FiAdvance f where f.sourceData=? and f.sourceNo=?",sourceData,sourceNo);
		if(list.size()==0){
			throw new ServiceException("����ʧ�ܣ�û���ҵ���Ӧ�Ĳ������ݣ��������ϣ�");
		}
		for(FiAdvance fiAdvance:list){
			double settlementAmount=0.0;
			status=fiAdvance.getStatus();
			if(status==0L) throw new ServiceException("����ʧ�ܣ�Ԥ����["+fiAdvance.getId()+"]�Ѿ����ϣ��������ϣ�");
			settlementType=fiAdvance.getSettlementType();
			if(settlementType == 1L){//���
				settlementAmount=DoubleUtil.sub(fiAdvance.getSettlementAmount(),-1);
			}else{
				settlementAmount=fiAdvance.getSettlementAmount();
			}
			
			//����Ԥ����˺����
			FiAdvanceset fat = this.fiAdvancesetDao.get(fiAdvance.getFiAdvanceId());
			if(fat==null)throw new ServiceException("����ʧ�ܣ�Ԥ����˺Ų����ڣ��������ϣ�");
			fat.setOpeningBalance(DoubleUtil.add(fat.getOpeningBalance(), settlementAmount));
			this.fiAdvancesetDao.save(fat);
			
			//����Ԥ�����ϸ
			FiAdvance fa = new FiAdvance();
			fa.setSettlementType(fiAdvance.getSettlementType());// ����
			fa.setCustomerId(fiAdvance.getCustomerId());// Ԥ����������ã�����ID
			fa.setCustomerName(fiAdvance.getCustomerName());
			fa.setSettlementAmount(settlementAmount);// ���ν�����
			fa.setSettlementBalance(fat.getOpeningBalance());// ���
			fa.setSourceData(sourceData);
			fa.setSourceNo(sourceNo);
			fa.setFiAdvanceId(fa.getId());
			this.fiAdvanceDao.save(fa);
		}
		return "Ԥ�����ϳɹ�!";
	}
	
	//REVIEW-ANN ����ע��
	//FIXED �ӿ�������ע��
	public String changePaymentAmount(List<FiInterfaceProDto> listfiInterfaceDto)throws Exception{
		String sourceData;// ������Դ
		Long sourceNo;// ��Դ����
		Double amount ;//�������
		Long paymentStatus;
		
		for(FiInterfaceProDto fiInterfaceProDto:listfiInterfaceDto){
			sourceData=fiInterfaceProDto.getSourceData();
			sourceNo=fiInterfaceProDto.getSourceNo();
			amount=fiInterfaceProDto.getAmount();
			
			if(amount==null||amount<=0.0) throw new ServiceException("����ʧ�ܣ��޸Ľ���������㣡");
			List<FiPayment> list=this.fiPaymentDao.find("from FiPayment f where f.sourceData=? and f.sourceNo=? and f.paymentType=2 and f.status=1",sourceData,sourceNo);
			if(list.size()==0){
				throw new ServiceException("����ʧ�ܣ�û���ҵ���Ӧ�Ĳ������ݣ�");
			}
			for(FiPayment fiPayment:list){
				paymentStatus=fiPayment.getPaymentStatus();
				if(paymentStatus!=4L){
					throw new ServiceException("����ʧ�ܣ�ֻ���޸�δ����ݽ�");
				}
				amount=DoubleUtil.sub(fiPayment.getAmount(), amount);
				if(amount<0.0) throw new ServiceException("����ʧ�ܣ��޸ĺ����������㣡");
				if(amount==0.0){
					fiPayment.setStatus(0L);
				};
				fiPayment.setAmount(amount);
				this.fiPaymentDao.save(fiPayment);
			}
		}

		return "��������ĳɹ�!";
	}
	
	//REVIEW-ACCEPT ����ע��
	//FIXED �ӿ�������ע��
	public String currentToFiIncome(List<FiInterfaceProDto> listfiInterfaceDto)throws Exception{
		Long dno;// ���͵���(�������)
		Long customerId;// ����ID
		String customerName;// ��������
		Double amount;// ���(�������)
		String costType;// �������ͣ�ר����/��ֵ�����/����Ӧ����/���ջ���/�������ͷ�/������ֵ��/��������/����/��ת��(�������)
		String sourceData;// ������Դ:(�������)
		Long sourceNo;// ��Դ����:(�������)
		String whoCash;//���
		String customerService;//�ͷ�Ա
		Date accounting;//�������
		OprFaxIn oprFaxIn=null;
		String incomeDepart;//���벿��
		Long incomeDepartId;//���벿��ID
		Long admDepartId;//�ͷ�Աҵ����ID
		String admDepart;//�ͷ�Ա��������
		
		
		for(FiInterfaceProDto fiInterfaceProDto:listfiInterfaceDto){
			dno=fiInterfaceProDto.getDno();
			customerId=fiInterfaceProDto.getCustomerId();
			customerName=fiInterfaceProDto.getCustomerName();
			amount=fiInterfaceProDto.getAmount();
			costType=fiInterfaceProDto.getCostType();
			sourceData=fiInterfaceProDto.getSourceData();
			sourceNo=fiInterfaceProDto.getSourceNo();
			whoCash=fiInterfaceProDto.getWhoCash();
			customerService=fiInterfaceProDto.getCustomerService();
			admDepart=fiInterfaceProDto.getAdmDepart();
			admDepartId=fiInterfaceProDto.getAdmDepartId();
			incomeDepart=fiInterfaceProDto.getIncomeDepart();
			incomeDepartId=fiInterfaceProDto.getIncomeDepartId();
			
			
			//��������ʱ���ͷ�Ա�Ϳͷ����ż�¼���¼�������˲���
			if(!"".equals(dno)&&dno!=null){
				oprFaxIn=this.oprFaxInService.get(dno);
				admDepart=oprFaxIn.getCusDepartName();
				customerService=oprFaxIn.getCustomerService();
			}
			
			if(!"��������".equals(costType)){
				if(oprFaxIn==null) throw new ServiceException("����������͵��Ų�����!");
				
				if (dno==null)
					throw new ServiceException("���������͵���!");
			}
			
			if (amount == 0.0||amount==null)
				throw new ServiceException("������������!");
			if ("".equals(sourceData))
				throw new ServiceException("������������Դ!");
			if (sourceNo==null)
				throw new ServiceException("��������Դ����!");
			if (!"Ԥ�����ͷ�".equals(costType)&&!"Ԥ��ר����".equals(costType)&&!"����ר����".equals(costType)&&!"�������ͷ�".equals(costType)&&!"Ԥ����ֵ��".equals(costType)&&!"������ֵ��".equals(costType)&&!"��������".equals(costType)){
				throw new ServiceException("����������Ͳ����ڣ�д������ʧ��!");
			}
			if(admDepartId==null||"".equals(admDepartId)){
				throw new ServiceException("����д�������ʱ������ָ������ͷ�����ID!");
			}
			if(incomeDepartId==null||"".equals(incomeDepartId)){
				throw new ServiceException("����д�������ʱ������ָ�����벿��ID!");
			}
			
			
			
			FiIncome fiIncome=new FiIncome();
			fiIncome.setDno(dno);
			fiIncome.setCustomerId(customerId);
			fiIncome.setCustomerName(customerName);
			fiIncome.setAmount(amount);
			fiIncome.setAmountType(costType);
			fiIncome.setSourceData(sourceData);
			fiIncome.setSourceNo(sourceNo);
			fiIncome.setWhoCash(whoCash);
			fiIncome.setCustomerService(customerService);
			//SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			fiIncome.setIncomeDepart(incomeDepart);
			fiIncome.setAdmDepart(admDepart);
			fiIncome.setAdmDepartId(admDepartId);
			fiIncome.setIncomeDepartId(incomeDepartId);
			Calendar c=Calendar.getInstance();
			if(c.get(Calendar.HOUR_OF_DAY)>19){
				c.add(Calendar.DATE, 1);
			}
			accounting=c.getTime();
			fiIncome.setAccounting(accounting);
			this.fiIncomeService.save(fiIncome);
		}
		return "����д��ɹ�";
	}
	//REVIEW-ACCEPT ����ע��
	//FIXED �ӿ�������ע��
	public String oprReturnToFiTransitcost(List<FiInterfaceProDto> listfiInterfaceDto)throws Exception{
		Long dno;// ���͵���(�������)
		Double amount;// ���(�������)
		String sourceData;// ������Դ:(�������)ʵ�䵥
		Long sourceNo;// ��Դ����:(�������)ʵ�䵥��
		Long customerId;// ����ID
		String distributionMode;// ���ͷ�ʽ(�°�/��ת/�ⷢ)
		for(FiInterfaceProDto ipd:listfiInterfaceDto){
			dno=ipd.getDno();
			amount=ipd.getAmount();
			sourceData=ipd.getSourceData();
			sourceNo=ipd.getSourceNo();
			distributionMode=ipd.getDistributionMode();
			customerId=ipd.getCustomerId();
			Customer cst = this.customerDao.get(customerId);
			if(cst==null){
				throw new ServiceException("�˿���ID�Ŀ��̲����ڻ����Ѿ�ɾ�����޷�д��ɱ�����");
			}
//			if(amount<0.0){
//				throw new ServiceException("�����ɱ�����С����");
//			}
			if (!"�����Ǽ�".equals(sourceData)){
				throw new ServiceException("��������ȷ��������Դ!");
			}
			if (sourceNo==null){
				throw new ServiceException("��������Դ����!");
			}
			if("��ת".equals(distributionMode)){
				//List<FiTransitcost> listTransitcost=this.fiTransitService.find("from FiTransitcost f where f.dno=?",dno);
/*				if(amount<0.0){
					throw new ServiceException("�����ɱ�����С����");
				}*/
				
				if(amount!=0.0){
					FiTransitcost ft=new FiTransitcost();
					ft.setDno(dno);
					ft.setAmount(amount);
					ft.setSourceData(sourceData);
					ft.setStatus(0l);
					ft.setSourceNo(sourceNo);
					ft.setCustomerId(customerId);
					ft.setCustomerName(cst.getCusName());
					this.fiTransitService.save(ft);
				}
			
			}else if("�ⷢ".equals(distributionMode)){
				/*
				List<FiOutcost>list =fiOutCostService.find("from FiOutcost fo where fo.dno=? and fo.isdelete=1 and fo.sourceData=? and fo.status=1 ",dno,"����¼��");
				//�������ˣ���ȡ���ɱ�
				if(list.size()!=0){
					double totalMoney=0.0;
					for(FiOutcost fiocost:list){
						totalMoney=DoubleUtil.add(totalMoney, fiocost.getAmount());
						fiocost.setIsdelete(0l);
						fiOutCostService.save(fiocost);
					}
					
					FiCost fiCostNew = new FiCost();
					fiCostNew.setCostType("�ⷢ�ɱ�");
					fiCostNew.setCostTypeDetail("�����Ǽ�");
					fiCostNew.setCostAmount(-totalMoney);
					fiCostNew.setDataSource("�ⷢ�ɱ�");
					fiCostNew.setDno(dno);
					fiCostNew.setStatus(1l);
					fiCostDao.save(fiCostNew);  
				}*/
				
				//���δ��ˣ�����¼���ⷢ�ɱ�������
/*				List<FiOutcost>listO =fiOutCostService.find("from FiOutcost fo where fo.dno=? and fo.isdelete=1 and fo.sourceData=? and fo.status=0 and fo.returnStatus=0 ",dno,"����¼��");
				for(FiOutcost fiOutcost:listO){
		//			fiOutcost.setIsdelete(0l);
					fiOutcost.setReturnStatus(1l);
					fiOutCostService.save(fiOutcost);
				}
				FiCost fiCostNew2 = new FiCost();
				fiCostNew2.setCostType("�ⷢ�ɱ�");
				fiCostNew2.setCostTypeDetail("�����Ǽ�");
				fiCostNew2.setCostAmount(amount);
				fiCostNew2.setDataSource("�����Ǽ�");
				fiCostNew2.setDno(dno);
				fiCostNew2.setStatus(1l);
				fiCostDao.save(fiCostNew2);  */				
				User user= WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
				Long bussDepartId=Long.parseLong(user.get("bussDepart")+"");
				OprFaxIn oprFaxIn= oprFaxInService.get(dno);
				
				List<FiOutcost>list =fiOutCostDao.find("from FiOutcost fo where fo.dno=? and fo.isdelete=1 and fo.sourceData=? and fo.departId=? and fo.customerId=? and fo.amount>0 ",dno,"����¼��",bussDepartId,oprFaxIn.getGoWhereId());
				if(list.size()==0){
					if(amount!=0.0){
						throw new ServiceException("�ⷢ�ɱ�δ¼�룬������¼�뷵���ɱ�");
					}
				}
				if(amount!=0.0){
					FiOutcost fiOutcost= new FiOutcost();
					fiOutcost.setDno(dno);
					fiOutcost.setAmount(amount);
					fiOutcost.setSourceData(sourceData);
					fiOutcost.setSourceNo(sourceNo);
					fiOutcost.setIsdelete(1l);
					fiOutcost.setCustomerId(customerId);
					fiOutcost.setCustomerName(cst.getCusName());
					fiOutCostDao.save(fiOutcost);
				}
				
			}else{
				throw new ServiceException("�����ڵ����ͣ�����д�����ɱ�");
			}
		}
		return "д��ɹ�";
	}

	public String oprReturnToFi(List<FiInterfaceProDto> listfiInterfaceDto)throws Exception{
		Long dno;// ���͵���(�������)
		Long customerId;// ����ID(��ת����ID���ⷢ����ID���ջ��˿���ID�����û�о�Ϊ��)
		Customer customer=null;
		String settlement="";// ���㷽ʽ(�ֽ�\�½�\Ԥ��)
		for(FiInterfaceProDto ipd:listfiInterfaceDto){
			dno=ipd.getDno();
			customerId=ipd.getCustomerId();
			if(!"".equals(customerId)&&customerId!=null){
				customer=this.customerDao.get(customerId);
				settlement = customer.getSettlement();
			}
			if ("".equals(settlement)||"�ֽ�".equals(settlement)||"Ԥ��".equals(settlement)) {
				this.invalidToFiPaymentByDno(ipd);
			} else if ("�½�".equals(settlement)) {
				this.invalidToFiReceivabledetailByDno(ipd);
			}else {
				throw new ServiceException("���㷽ʽ�����ڣ�����ϵ����Ա!");
			}
			
		}
		
		return "����Ӧ�����ݳɹ���";
	}
	
	//REVIEW-ACCEPT ����ע��
	//FIXED �ӿ�������ע��
	public String revocationFiDeliveryCost(List<FiInterfaceProDto> listfiInterfaceDto)throws Exception{
		String sourceData;// ������Դ
		Long sourceNo;// ��Դ����
		double amount = 0.0;//�����ܽ��
		for(FiInterfaceProDto fiInterfaceProDto:listfiInterfaceDto){
			sourceData=fiInterfaceProDto.getSourceData();
			sourceNo=fiInterfaceProDto.getSourceNo();
			amount=fiInterfaceProDto.getAmount();
			List<FiPayment> list=this.fiPaymentDao.find("from FiPayment f where f.sourceData=? and f.sourceNo=? and f.status=1",sourceData,sourceNo);
			if(list.size()==0){
				throw new ServiceException("���ҵ���Ӧ�Ĳ������ݣ�");
			}
			if(list.size()>1){
				throw new ServiceException("���ڶ�������");
			}
			
			FiPayment fiPayment=list.get(0);
			if(fiPayment.getPaymentStatus()!=4L){
				throw new ServiceException("����ʧ�ܣ�ֻ���޸�δ����ݽ�");
			}
			if(fiPayment.getAmount()==0.0){
				fiPayment.setStatus(0L);
			}else{
				fiPayment.setAmount(DoubleUtil.sub(fiPayment.getAmount(), amount));
			}
			this.fiPaymentDao.save(fiPayment);
		}
		return "��������ɱ��ɹ�!";
	}
}


