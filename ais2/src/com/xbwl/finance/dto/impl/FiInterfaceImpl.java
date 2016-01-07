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

	// 应收付款
	@Resource(name = "fiPaymentHibernateDaoImpl")
	private IFiPaymentDao fiPaymentDao;

	// 欠款明细
	@Resource(name = "fiReceivabledetailHibernateDaoImpl")
	private IFiReceivabledetailDao fiReceivabledetailDao;
	// 客商
	@Resource(name = "customerHibernateDaoImpl")
	private ICustomerDao customerDao;

	// 预存款设置
	@Resource(name = "fiAdvancesetHibernateDaoImpl")
	private IFiAdvancesetDao fiAdvancesetDao;

	// 预存款流水
	@Resource(name = "fiAdvanceHibernateDaoImpl")
	private IFiAdvanceDao fiAdvanceDao;
	
	//内部结算
	@Resource(name = "fiInternalDetailServiceImpl")
	private IFiInternalDetailService fiInternalDetailService;
	
	//航空主单
	@Resource(name = "oprFaxMainServiceImpl")
	private IOprFaxMainService oprFaxMainService;
	
	//提货成本
	@Resource(name = "fiDeliverycostServiceImpl")
	private IFiDeliverycostService fiDeliverycostService;
	
	//提货协议价
	@Resource(name = "fiDeliveryPriceServiceImpl")
	private IFiDeliveryPriceService fiDeliveryPriceService;

	//主单超重设置
	@Resource(name="cusOverManagerServiceImpl")
	private ICusOverManagerService cusOverManagerService;
	
	//主单超重
	@Resource(name="cusOverWeightServiceImpl")
	private ICusOverWeightService cusOverWeightService;
	
	//中转成本
	@Resource(name = "fiTransitcostServiceImpl")
	private IFiTransitcostService fiTransitService;
	
	//收入表
	@Resource(name="fiIncomeServiceImpl")
	private IFiIncomeService fiIncomeService;
	
	//传真录入
	@Resource(name = "oprFaxInServiceImpl")
	private IOprFaxInService oprFaxInService;
	
	//客商欠款设置
	@Resource(name="fiArrearsetServiceImpl")
	private IFiArrearsetService fiArrearsetService;
	
	//成本表
	@Resource(name="fiCostHibernateDaoImpl")
	private IFiCostDao fiCostDao;
	
	//外发成本
	@Resource(name="fiOutCostHibernateDaoImpl")
	private IFiOutCostDao fiOutCostDao;
	
	//外发成本
	@Resource(name="fiOutCostServiceImpl")
	private IFiOutCostService fiOutCostService;
	
	//成本审核节点
	@Value("${fiAuditCost.log_auditCost}")
	private Long log_auditCost ;
	
	//历史操作记录Service
	@Resource(name = "oprHistoryServiceImpl")
	private IOprHistoryService oprHistoryService;
	
	//内部协议价
	@Resource(name = "fiInternalRateServiceImpl")
	private IFiInternalRateService fiInternalRateService;
	
	//内部特殊协议价
	@Resource(name = "fiInternalSpecialRateServiceImpl")
	private IFiInternalSpecialRateService fiInternalSpecialRateService;
	
	
	@ModuleName(value="业务调用财务接口",logType=LogType.fi)
	public String addFinanceInfo(List<FiInterfaceProDto> listfiInterfaceDto)
			throws Exception {
		for (FiInterfaceProDto fiInterfaceProDto : listfiInterfaceDto) {
				if (fiInterfaceProDto.getCustomerId() == null||"".equals(fiInterfaceProDto.getCustomerId() == null))
					throw new ServiceException("请输入客商ID!");
				Customer cst = this.customerDao.get(fiInterfaceProDto.getCustomerId());
				//REVIEW 需要做非空判断
				//FIXED 已处理
				if(cst==null) {
					throw new ServiceException("客商在客商管理中不存在");
				}
				String settlement = cst.getSettlement();// 结算方式(现结\月结\预付)
				if("".equals(settlement)||settlement==null){
					throw new ServiceException("结算方式不存在，请联系管理员!");
				}
				if ("现结".equals(settlement)) {
					fiInterfaceProDto.setGocustomerId(cst.getId());
					fiInterfaceProDto.setGocustomerName(cst.getCusName());
					this.addFiPayment(fiInterfaceProDto);
				} else if ("月结".equals(settlement)) {
					this.addFiReceivabledetail(fiInterfaceProDto);
				} else if ("预付".equals(settlement)) {
					this.addFiAdvance(fiInterfaceProDto);
				} else {
					throw new ServiceException("结算方式不存在，请联系管理员!");
				}
		}
		return "保存成功!";
	}
	
	//REVIEW-ACCEPT 增加注释
	//FIXED
	/**
	 * 对账单生成应收应付接口
	 */
	public String reconciliationToFiPayment(List<FiInterfaceProDto> listfiInterfaceDto)throws Exception{
		for (FiInterfaceProDto fiInterfaceProDto : listfiInterfaceDto) {
			fiInterfaceProDto.setGocustomerId(fiInterfaceProDto.getCustomerId());
			fiInterfaceProDto.setGocustomerName(fiInterfaceProDto.getCustomerName());
			this.addFiPayment(fiInterfaceProDto);
		}
		return "保存成功!";
	}
	
	
	//REVIEW-ACCEPT 增加注释
	//FIXED 接口中已有注释
	@ModuleName(value="出库调用财务接口",logType=LogType.fi)
	public String outStockToFi(List<FiInterfaceProDto> listfiInterfaceDto)throws Exception{
		String outStockMode=null;
		for (FiInterfaceProDto fiInterfaceProDto : listfiInterfaceDto) {
			outStockMode=fiInterfaceProDto.getOutStockMode();
			if("中转".equals(outStockMode)||"外发".equals(outStockMode)){
				Customer cst = this.customerDao.get(fiInterfaceProDto.getGocustomerId());//收货人客商
				if (cst == null) throw new ServiceException("客商不存在，请联系管理员!");
				String settlement = cst.getSettlement();// 结算方式(现结\月结\预付)
				if ("现结".equals(settlement)) {
					fiInterfaceProDto.setPenyType("现结");
					this.addFiPayment(fiInterfaceProDto);
				} else if ("月结".equals(settlement)) {
					//REVIEW-ACCEPT 月结不需要设置setPenyType属性吗？
					//FIXED 不需要设置
					this.addFiReceivabledetail(fiInterfaceProDto);
				} else if ("预付".equals(settlement)) {
					fiInterfaceProDto.setPenyType("预付");
					this.addFiAdvance(fiInterfaceProDto);
				} else {
					throw new ServiceException("结算方式不存在，请联系管理员!");
				}
			}else if("市内送货".equals(outStockMode)||"专车".equals(outStockMode)||"市内自提".equals(outStockMode)||"机场自提".equals(outStockMode)){//市内自提
				fiInterfaceProDto.setPenyType("现结");
				this.addFiPayment(fiInterfaceProDto);
			}else if(!"部门交接".equals(outStockMode)) {
				throw new ServiceException("出库方式不存在，请联系管理员!");
			}
		}
		return "保存成功!";
	}
	
	
	/**
	 * 预存款记录
	 */
	private String addFiAdvance(FiInterfaceProDto fpd) throws Exception {
		List<FiAdvanceset> list = fiAdvancesetDao.find("from FiAdvanceset f where f.customerId=? and f.isdelete=1L",fpd.getCustomerId());
		if(list.size()==0){
			throw new ServiceException("此客商在预存款设置中不存在!");
		}
		this.addFiPayment(fpd);
		/*		FiAdvanceset fat = list.get(0);

		Double openingBalance = fat.getOpeningBalance();// 账号余额
		if (openingBalance < 0)
			throw new ServiceException("预存款余额不足，不能用预付款支付!");
		

		Long customerId = fpd.getCustomerId();// 客商ID(必输参数)
		String customerName = fpd.getCustomerName();// 客商名称(必输参数)
		Long settlementType = fpd.getSettlementType();// 收付类型(1:存款、2:取款)(必输参数)
		Double settlementAmount = fpd.getAmount(); // 结算金额(必输参数)
		String sourceData = fpd.getSourceData();// 数据来源(必输参数)
		Long sourceNo = fpd.getSourceNo();// 来源单号(必输参数)

		Customer cst = this.customerDao.get(fpd
				.getCustomerId());
		if (cst == null)
			throw new ServiceException("客商不存在，请联系管理员!");
		if (customerId == null) {
			throw new ServiceException("预付款实体中客商ID不能为空!");
		}

		if (settlementAmount <= 0.0||settlementAmount==null)
			throw new ServiceException("请输入收款金额!");
		
		if ("".equals(customerName)||customerName==null) {
			throw new ServiceException("客商名称不能为空");
		}
		if ("".equals(sourceData)||sourceData==null) {
			throw new ServiceException("请输入数据来源!");
		}
		if ("".equals(sourceNo)||sourceNo == null) {
			throw new ServiceException("请输入来源单号!");
		}
		if (settlementAmount > openingBalance) {
			throw new ServiceException("预存款余额不足，不能用预付款支付!");
		}

		if (settlementType != 1L || settlementType != 2L) {
			throw new ServiceException("预付款实体中输入正确收付类型!");
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
	 * 月结写入欠款明细
	 */
	private String addFiReceivabledetail(FiInterfaceProDto fpd)
			throws Exception {
		User user=WebRalasafe.getCurrentUser(Struts2Utils.getRequest());
		Long dno = fpd.getDno();// 配送单号(必输参数)
		Double amount = fpd.getAmount();// 金额(必输参数)
		String sourceData = fpd.getSourceData();// 数据来源
		Long sourceNo = fpd.getSourceNo();// 来源单号(必输参数)
		Long paymentType = fpd.getSettlementType(); // 收付类型(1:收款/2:付款)
		Long customerId = fpd.getCustomerId();// 客商ID(必输参数)
		String customerName = fpd.getCustomerName();// 客商名称(必输参数)
		String costType = fpd.getCostType();// 费用类型(必输参数)
		String flightmainno=fpd.getFlightMainNo();//主单号
		Long incomeDepartId=fpd.getIncomeDepartId();//收入部门

		Customer cst = this.customerDao.get(fpd.getCustomerId());
		
		if (cst == null)
			throw new ServiceException("客商不存在，请联系管理员!");
		
		if("发货代理".equals(cst.getCustprop())&&!"代收货款".equals(costType)){
			List<FiArrearset> list=this.fiArrearsetService.find("from FiArrearset f where f.customerId=? and f.departId=? and isDelete=0",cst.getId(),Long.valueOf(user.get("bussDepart")+""));
			//REVIEW-ACCEPT 检查非空
			//FIXED 已处理
			if(list==null|| list.size()==0) throw new ServiceException("客商<"+cst.getCusName()+">在<客商欠款设置>中不存在，请联系管理员!");
			if(list.size()>1) throw new ServiceException("客商<"+cst.getCusName()+">在<客商欠款设置>中存在存在多条记录，请联系管理员!");
		}
		
		if(paymentType==null){
			throw new ServiceException("请输入收付类型!");
		}
		if (paymentType != 1L&& paymentType != 2L) {
			throw new ServiceException("请输入收付类型!");
		}
		if(!"主单超重".equals(sourceData)){
			if (dno==null||"".equals(dno)){
				throw new ServiceException("请输入配送单号!");
			}
		}
		if (amount == 0.0||amount==null)
			throw new ServiceException("请输入欠款金额!");
		if ("".equals(sourceData))
			throw new ServiceException("请输入数据来源!");
		if (sourceNo==null)
			throw new ServiceException("请输入来源单号!");
		if (costType==null)
			throw new ServiceException("请输入费用类型!");

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
	 * 现结写入应收应付
	 */
	private String addFiPayment(FiInterfaceProDto fpd) throws Exception {
		Long paymentType = fpd.getSettlementType();// 收付类型(1:收款单/2:付款单)(必输参数)
		String costType = fpd.getCostType(); // 费用类型:代收货款/到付提送费/到付增值费/预付提送费/预付增值费/其他收入/对账(必输参数)
		String documentsType = fpd.getDocumentsType(); // 单据大类:收入\成本\对账\预存款\代收货款(必输参数)
		String documentsSmalltype = fpd.getDocumentsSmalltype(); // 单据小类：配送单/对账单/配送单/预存款单(必输参数)
		Long documentsNo = fpd.getDocumentsNo(); // 单据小类对应的单号(必输参数)
		String penyType=null ; // 结算类型(现结、月结)(必输参数)
		Double amount = fpd.getAmount();// 金额(必输参数)
		Long customerId = fpd.getGocustomerId();// 收货人客商ID(必输参数)
		String customerName = fpd.getGocustomerName(); // 收货人客商表名称(必输参数)
		String sourceData = fpd.getSourceData(); // 数据来源(必输参数)
		Long sourceNo = fpd.getSourceNo(); // 来源单号(必输参数)
		String collectionUser = fpd.getCollectionUser(); // 收款责任人:自提：创建人，送货：送货员，外发：外发员(必输参数)
		String createRemark=fpd.getCreateRemark(); //摘要
		String contacts=fpd.getContacts();//往来单位(如果没收货人客商，往来单位为收货人姓名)
		Long paymentStatus; // 收付状态：0作废、1未收款、2已收款、3部分收款、4未付款、5已付款、6部分付款、7到付转欠款、8异常
		Long disDepartId=fpd.getDisDepartId();//配送部门
		Long incomeDepartId=fpd.getIncomeDepartId();//收入部门
		Long departId = fpd.getDepartId();
		String departName = fpd.getDepartName();
		
		if (paymentType == 1L) {
			paymentStatus = 1L;
		} else if (paymentType == 2L) {
			paymentStatus = 4L;
		} else {
			throw new ServiceException("请输入收付类型!");
		}

		if (paymentType.equals("")||paymentType==null) {
			throw new ServiceException("请输入收付类型!");
		} else if (paymentType != 1L && paymentType != 2L) {
			throw new ServiceException("收付类型只能为收款单或付款单!");
		}
		
		if(("".equals(customerId)||customerId==null)&&("".equals(contacts)||contacts==null)){
			throw new ServiceException("生成收、付款单必须输入客商或往来单位!");
		}
		
			
		if ("".equals(costType)||costType==null)
			throw new ServiceException("请输入费用类型!");
		if ("".equals(documentsType)||documentsType==null)
			throw new ServiceException("请输入单据大类!");
		if ("".equals(documentsSmalltype)||documentsSmalltype==null)
			throw new ServiceException("请输入单据小类!");
		if ("".equals(documentsNo)||documentsNo==null)
			throw new ServiceException("请输入单据号!");

		if (amount==null)
			throw new ServiceException("请输入收款金额!");
		if ("".equals(sourceData)||sourceData==null)
			throw new ServiceException("请输入数据来源!!");
		if ("".equals(sourceNo)||sourceNo==null)
			throw new ServiceException("请输入来源单号!");
		
		
		if (paymentType == 1L) {
			if (("".equals(collectionUser)||collectionUser==null))
				throw new ServiceException("收款责任人不能为空!");
		}
		
		if((null!=customerId &&customerId!=0)&&(penyType==null||"".equals(penyType))){
			Customer cst = this.customerDao.get(customerId);
			penyType=cst.getSettlement();
		}else{
			penyType="现结";
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
	
	//REVIEW-ACCEPT 增加注释
	//FIXED 接口中已有注释
	public String currentToFiPayment(List<FiInterfaceProDto> listfiInterfaceDto)throws Exception{
		for (FiInterfaceProDto fiInterfaceProDto : listfiInterfaceDto) {
			fiInterfaceProDto.setGocustomerId(fiInterfaceProDto.getCustomerId());
			fiInterfaceProDto.setGocustomerName(fiInterfaceProDto.getCustomerName());
			this.addFiPayment(fiInterfaceProDto);
		}
		return "";
	}
	//REVIEW-ACCEPT 增加注释
	//FIXED 接口中已有注释
	public String internalCostToFi(List<FiInterfaceProDto> listfiInterfaceDto)throws Exception{
		for(FiInterfaceProDto fiInterfaceProDto:listfiInterfaceDto){
			Long startDepartId=fiInterfaceProDto.getStartDepartId();//始发部门ID
			String startDepartName=fiInterfaceProDto.getStartDepartName(); //始发部门
			Long endDepartId=fiInterfaceProDto.getEndDepartId(); //终端部门ID
			String endDepartName=fiInterfaceProDto.getEndDepart();//终端部门
			String sourceData=fiInterfaceProDto.getSourceData();//数据来源(到车确认、发车确认)
			Long sourceNo=fiInterfaceProDto.getSourceNo();//来源单号
			Long dno=fiInterfaceProDto.getDno();// 配送单号(必输参数)
			Double flightWeight=fiInterfaceProDto.getFlightWeight();//重量
			Long flightPiece=fiInterfaceProDto.getFlightPiece();//件数
			Double bulk=fiInterfaceProDto.getBulk();//体积
			Long customerId = fiInterfaceProDto.getCustomerId();// 客商ID
			String outStockMode = fiInterfaceProDto.getOutStockMode();//出库调用财务接口：出库方式(新邦自提/新邦市内送货/新邦郊区送货/中转自提/中转市内送货/中转郊区送货/外发自提/外发送货)
			String distributionMode=fiInterfaceProDto.getDistributionMode();// 配送方式(新邦/中转/外发)
			
			
			if("".equals(startDepartId)||"".equals(startDepartName)) throw new  ServiceException("计算内部结算失败,请输入始发部门!");
			if("".equals(endDepartId)||"".equals(startDepartName)) throw new  ServiceException("计算内部结算失败,请输入到达部门!");
			
			if("".equals(sourceData)) throw new  ServiceException("请输入数据来源");
			if("".equals(sourceNo)) throw new  ServiceException("请输入来源单号");
			if("".equals(customerId)) throw new  ServiceException("请输入客商ID");
			if("".equals(dno)) throw new  ServiceException("请输入配送单号");
			if(flightWeight<=0.0) throw new  ServiceException("请输入重量");
			if("".equals(flightPiece)) throw new  ServiceException("请输入件数");
			//if("".equals(bulk)||bulk<=0.0) throw new  ServiceException("请输入体积");
			
			//根据特殊协议价计划
			FiInternalDetail fiInternalDetail=this.fiInternalSpecialRateService.calculateCost(customerId, flightWeight, flightPiece, bulk);
			
			//如果特殊协议价不存在，则根据特殊协议价计算
			if(fiInternalDetail!=null){
				fiInternalDetail.setAgreementType(48251L);//特殊协议价
			}else{
				fiInternalDetail=fiInternalRateService.calculateCost(startDepartId, endDepartId, dno, flightWeight, outStockMode);
				if(fiInternalDetail!=null){
					fiInternalDetail.setAgreementType(48250L);//标准协议价
				}
			}
			
			if(fiInternalDetail!=null){
				if("发车确认".equals(sourceData)){
					fiInternalDetail.setDno(dno);
					fiInternalDetail.setStartDepartId(startDepartId);
					fiInternalDetail.setStartDepartName(startDepartName);
					fiInternalDetail.setEndDepartId(endDepartId);
					fiInternalDetail.setEndDepartName(endDepartName);
					fiInternalDetail.setSourceData(sourceData);
					fiInternalDetail.setSourceNo(sourceNo);
					fiInternalDetail.setSettlementType(2L);//成本
					fiInternalDetail.setBelongsDepartId(startDepartId);
					fiInternalDetail.setBelongsDepartName(startDepartName);
					fiInternalDetail.setDistributionMode(distributionMode);
					fiInternalDetail.setCustomerId(customerId);
				}else if("到车确认".equals(sourceData)){
					fiInternalDetail.setDno(dno);
					fiInternalDetail.setStartDepartId(startDepartId);
					fiInternalDetail.setStartDepartName(startDepartName);
					fiInternalDetail.setEndDepartId(endDepartId);
					fiInternalDetail.setEndDepartName(endDepartName);
					fiInternalDetail.setSourceData(sourceData);
					fiInternalDetail.setSourceNo(sourceNo);
					fiInternalDetail.setSettlementType(1L);//收入
					fiInternalDetail.setBelongsDepartId(endDepartId);
					fiInternalDetail.setBelongsDepartName(endDepartName);
					fiInternalDetail.setDistributionMode(distributionMode);
					fiInternalDetail.setCustomerId(customerId);
				}else{
					throw new  ServiceException("数据来源错误!");
				}
				this.fiInternalDetailService.save(fiInternalDetail);
			}else{
				//收款单操作日志
				oprHistoryService.saveFiLog(sourceNo, "实配单号"+sourceNo+"内部成本协议价不存在!" , 34L);
				throw new ServiceException("内部协议价不存在！");
				
			}
		}
		return "";
	}

	public String invalidInternalCostToFi(List<FiInterfaceProDto> listfiInterfaceDto)throws Exception{
		String sourceData=null;//数据来源(到车确认、发车确认)
		Long sourceNo=null;//来源单号
		String hql=null;
		for(FiInterfaceProDto fiInterfaceProDto:listfiInterfaceDto){
			sourceData=fiInterfaceProDto.getSourceData();
			sourceNo=fiInterfaceProDto.getSourceNo();
			if("".equals(sourceData)) throw new  ServiceException("请输入数据来源");
			if("".equals(sourceNo)) throw new  ServiceException("请输入来源单号");
			hql="update FiInternalDetail f set f.status=0 where f.sourceData=? and f.sourceNo=?";
			this.fiInternalDetailService.batchExecute(hql, sourceData,sourceNo);
		}
		return "";
	}
	
	//REVIEW-ANN 增加注释
	//FIXED 接口中已注释
	@ModuleName(value="到车确认调用财务提货成本接口",logType=LogType.fi)
	public String storageToFiDeliverCost(List<FiInterfaceProDto> listfiInterfaceDto)throws Exception{
		for(FiInterfaceProDto fiInterfaceProDto:listfiInterfaceDto){
			Long customerId=fiInterfaceProDto.getCustomerId();// 提货点ID(手工新增始发点)
			String customerName=fiInterfaceProDto.getCustomerName();// 提货点名称(手工新增始发点名称)
			String flightMainNo=fiInterfaceProDto.getFlightMainNo();//手动新增(主单号)
			double flightWeight=fiInterfaceProDto.getFlightWeight();//黄单重量
			Long flightPiece=fiInterfaceProDto.getFlightPiece();//黄单件数
			String startcity=fiInterfaceProDto.getStartcity();//始发站
			
//			String faxMainNo;//主单号(主单表)
			String goodsType =fiInterfaceProDto.getGoodsType();  //货物类型
			String sourceData=fiInterfaceProDto.getSourceData();
			Long matStatus;//匹配状态(1:自动匹配、2:手动匹配、0:未匹配)
			double faxWeight=0.0; //主单重量(主单表)
			double flightAmount; //黄单金额(黄单重量*单价)
			double boardAmount = 0.0;//板费
			double price;//单价
			Long isLowestStatus;//是否最低一票(1:是，0:不是)
			double diffWeight=0.0;//重量差异(黄单重量-主单重量)
			double diffAmount;//金额差异(黄单重量*单价-主单重量*单价)
			double lowest;//最低一票；
			
			if("".equals(customerId)) throw new  ServiceException("请输入始发点");
			if("".equals(flightMainNo)) throw new  ServiceException("请输入主单号");
			if(flightWeight<=0.0) throw new  ServiceException("请输入重量");
			if(flightPiece<=0L) throw new  ServiceException("请输入件数");
			
			if("".equals(goodsType)||goodsType==null){
				throw new  ServiceException("请输入货物类型");
			}
			OprFaxMain oprFaxMain=this.oprFaxMainService.findFiDeliveryByMatchStatus(flightMainNo);  //查找出未匹配的主单数据进行匹配，部门一致

			String returnMsg="";
			FiDeliverycost fiDeliverycost=new FiDeliverycost();
			fiDeliverycost.setFlightMainNo(flightMainNo);
			fiDeliverycost.setFlightWeight(flightWeight);
			fiDeliverycost.setFlightPiece(flightPiece);
			fiDeliverycost.setGoodsType(goodsType);
			fiDeliverycost.setStartcity(startcity);
	
			List<FiDeliveryPrice> fiDeliveryPricelist=this.fiDeliveryPriceService.getPriceByCustomerId(customerId,goodsType);
			if(fiDeliveryPricelist.size()!=1){
				throw new  ServiceException("财务取提货协议价的时候出问题了");
			}
			
			FiDeliveryPrice fiDeliveryPrice=fiDeliveryPricelist.get(0);//提货点协议价
			

			price=fiDeliveryPrice.getRates();//费率
			lowest=fiDeliveryPrice.getLowest();//最低一票
			flightAmount=DoubleUtil.mul(flightWeight, price);//黄单金额(黄单重量*单价)
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
				returnMsg="保存成功，黄单未能找对应的主单数据";
			}else{
				matStatus=1L;
				diffAmount=Math.ceil(DoubleUtil.mul(price,DoubleUtil.sub(fiDeliverycost.getFlightWeight(),oprFaxMain.getTotalWeight())));
				fiDeliverycost.setDiffAmount(diffAmount);
				fiDeliverycost.setFaxId(oprFaxMain.getId()+"");
				
				diffWeight=Math.ceil(DoubleUtil.sub(fiDeliverycost.getFlightWeight(),oprFaxMain.getTotalWeight()));
				fiDeliverycost.setDiffWeight(Math.ceil(DoubleUtil.sub(fiDeliverycost.getFlightWeight(),oprFaxMain.getTotalWeight())));
				if(diffWeight==0.0){
					returnMsg="保存成功，匹配成功,无重量差异";
				}else{
					returnMsg="保存成功，匹配成功,有重量差异";
				}
				
				fiDeliverycost.setFaxPiece(oprFaxMain.getTotalPiece());
				fiDeliverycost.setFaxMainNo(oprFaxMain.getFlightMainNo());
				fiDeliverycost.setFaxWeight(Math.ceil(oprFaxMain.getTotalWeight()));
				
				oprFaxMain.setMatchStatus(1l);   //修改匹配状态 已经匹配了
				oprFaxMainService.save(oprFaxMain);
			}
			
			fiDeliverycost.setMatStatus(matStatus);
			fiDeliverycost.setCustomerId(customerId);
			fiDeliverycost.setCustomerName(customerName);
			
			List<FiDeliverycost>list = fiDeliverycostService.find("from FiDeliverycost  fi where fi.flightMainNo=? and fi.customerId=? ", fiDeliverycost.getFlightMainNo(),fiDeliverycost.getCustomerId());
			//REVIEW-ACCEPT 做非空判断
			//FIXED
			if(list==null){
				throw new ServiceException("未找到提货成本数据");
			}
			if(list.size()>0){  // 判断一下，如果已录入黄单不能再录进去了。
				return "找到多条黄单数据，未能匹配成功";
			}
			this.fiDeliverycostService.save(fiDeliverycost);
			
			
			if(oprFaxMain!=null&&diffWeight>0.0){
				//主单超重处理   在匹配的时候做主单超重处理
				List<OprFaxIn> listMain = oprFaxInService.find("from OprFaxIn oi where oi.faxMainId=? and oi.status=1 ",oprFaxMain.getId());
				OprFaxIn oprFaxIn =listMain.get(0);
				
				CusOverweightManager cusMan=null;
				List<CusOverweightManager> cuslist=this.cusOverManagerService.findBy("cusId", oprFaxIn.getCusId());
				if(cuslist.size()==0){
					List<CusOverweightManager> listM =cusOverManagerService.find("from CusOverweightManager cs where cs.cusId is null or cs.cusId=0");
					if(listM.size()==0){
						throw new ServiceException("请在主单超重模块录入主单超重相关信息");
					}else{
						cusMan=listM.get(0);
					}
				}else{
					cusMan=cuslist.get(0);
				}
				
				if(diffWeight>cusMan.getLowWeight()){//写入主单超重表
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
		return "黄单数据和系统主单数据匹配成功";
	}
	
	
	//REVIEW-ACCEPT 增加注释
	//FIXED
	/**
	 * 更改申请调用财务接口
	 */
	@ModuleName(value="更改申请调用财务接口",logType=LogType.fi)
	public String changeToFi(List<FiInterfaceProDto> listfiInterfaceDto)throws Exception{
		for (FiInterfaceProDto fiInterfaceProDto : listfiInterfaceDto) {
			String costType=fiInterfaceProDto.getCostType();//原费用类型
			if("预付提送费".equals(costType)||"预付增值费".equals(costType)||"预付专车费".equals(costType)){
				fiInterfaceProDto.setWhoCash("预付");
				this.changePrepaid(fiInterfaceProDto);
			}else if("到付提送费".equals(costType)||"到付增值费".equals(costType)||"到付专车费".equals(costType)){
				fiInterfaceProDto.setWhoCash("到付");
				this.changeTopay(fiInterfaceProDto);
			}else if("中转费".equals(costType)){
				this.changeTotransit(fiInterfaceProDto);
			}else if("外发费".equals(costType)){//外发
				this.changeToOutCost(fiInterfaceProDto);
			}else if("代收货款".equals(costType)){
				this.inStockCollectionToFi(fiInterfaceProDto);
			}else{
				throw new ServiceException("更改费用类型财务未配置，请联系管理员!");
			}
		}
		return "保存成功!";
	}
	
	//出库前代收货款更改
	private void inStockCollectionToFi(FiInterfaceProDto fiInterfaceProDto) throws Exception{
		Long dno=fiInterfaceProDto.getDno();//配送单号
		String costType=fiInterfaceProDto.getCostType();//原费用类型
		Long customerId=fiInterfaceProDto.getCustomerId();//发货代理
		Long gocustomerId=fiInterfaceProDto.getGocustomerId();// 客商ID(中转、外发客商ID)
		Long stockStatus=fiInterfaceProDto.getStockStatus();//0未出库,1已出库
		Long incomeDepartId=fiInterfaceProDto.getIncomeDepartId();//收入部门(录单部门ID)
		//Long disDepartId=fiInterfaceProDto.getDisDepartId();//配送部门ID
		
		Customer precst=this.customerDao.get(fiInterfaceProDto.getPreCustomerId());//更改前发货代理
		Customer cst = this.customerDao.get(customerId);//更改后发货代理
		if (cst == null) throw new ServiceException("发货代理不存在，请联系管理员!");
		//Double amount=0.0;//更改前总金额
		Double amount=fiInterfaceProDto.getAmount();//更改后金额
		double beforeAmount=fiInterfaceProDto.getBeforeAmount();//更改前总金额
		double diffAmount=DoubleUtil.sub(amount,beforeAmount);//更改差额
		
		//代理代收货款处理(应付)
		if(precst.getId()==cst.getId()&&diffAmount!=0.0){//同一客商更改金额
			FiInterfaceProDto fpd=new FiInterfaceProDto();
			fpd.setCustomerId(cst.getId());
			fpd.setCustomerName(cst.getCusName());
			fpd.setDno(fiInterfaceProDto.getDno());
			fpd.setAmount(diffAmount);
			fpd.setSourceData(fiInterfaceProDto.getSourceData());
			fpd.setSourceNo(fiInterfaceProDto.getSourceNo());
			fpd.setCostType(costType);
			fpd.setSettlementType(2L);//由小改大，应付代理
			if(incomeDepartId!=null){
				fpd.setIncomeDepartId(incomeDepartId);
			}
			this.addFiReceivabledetail(fpd);
		}else{//不同客商
		      List<FiInterfaceProDto> listfiInterfaceDto=new ArrayList<FiInterfaceProDto>();
		      //原客商费用写入负记录
		      if(beforeAmount!=0.0){
					FiInterfaceProDto prefpd=new FiInterfaceProDto();
					prefpd.setCustomerId(precst.getId());
					prefpd.setCustomerName(precst.getCusName());
					prefpd.setDno(fiInterfaceProDto.getDno());
					prefpd.setAmount(beforeAmount*-1);
					prefpd.setSourceData(fiInterfaceProDto.getSourceData());
					prefpd.setSourceNo(fiInterfaceProDto.getSourceNo());
					prefpd.setCostType(costType);
					prefpd.setSettlementType(2L);//由小改大，应付代理
					if(incomeDepartId!=null){
						prefpd.setIncomeDepartId(incomeDepartId);
					}
					this.addFiReceivabledetail(prefpd);
				}
				
		      //新客商写入正记录
		      if(amount!=0.0){
		        FiInterfaceProDto fpd=new FiInterfaceProDto();
				fpd.setCustomerId(cst.getId());
				fpd.setCustomerName(cst.getCusName());
				fpd.setDno(fiInterfaceProDto.getDno());
				fpd.setAmount(amount);
				fpd.setSourceData(fiInterfaceProDto.getSourceData());
				fpd.setSourceNo(fiInterfaceProDto.getSourceNo());
				fpd.setCostType(costType);
				fpd.setSettlementType(2L);//由小改大，应付代理
				if(incomeDepartId!=null){
					fpd.setIncomeDepartId(incomeDepartId);
				}
		        this.addFiReceivabledetail(fpd);
		        }
		}
		
		//收货人、中转外发代收货款处理(应收)
		if(diffAmount!=0.0&&stockStatus==1L){
			fiInterfaceProDto.setCustomerId(gocustomerId);
			fiInterfaceProDto.setDocumentsType("代收货款");
			this.changeTopay(fiInterfaceProDto);
		}
	
	}
	
	//预付更改
	private void changePrepaid(FiInterfaceProDto fiInterfaceProDto) throws Exception{
		Long dno=fiInterfaceProDto.getDno();//配送单号
		String costType=fiInterfaceProDto.getCostType();//原费用类型
		Long stockStatus=fiInterfaceProDto.getStockStatus();//0未出库,1已出库
		Customer precst=this.customerDao.get(fiInterfaceProDto.getPreCustomerId());//更改前发货代理
		Customer cst = this.customerDao.get(fiInterfaceProDto.getCustomerId());//更改后发货代理
		if (cst == null) throw new ServiceException("客商不存在，请联系管理员!");
		double afamount=fiInterfaceProDto.getAmount();//更改后金额
		double beforeAmount=fiInterfaceProDto.getBeforeAmount();//更改前总金额
		double amount=DoubleUtil.sub(afamount,beforeAmount);//更改差额，新金额-原付金额

		if(precst.getId()==cst.getId()&&amount!=0.0){//同一客商更改金额
			//往来明细更改
			FiInterfaceProDto fpd=new FiInterfaceProDto();
			fpd.setCustomerId(cst.getId());
			fpd.setCustomerName(cst.getCusName());
			fpd.setDno(fiInterfaceProDto.getDno());
			fpd.setAmount(amount);
			fpd.setSourceData(fiInterfaceProDto.getSourceData());
			fpd.setSourceNo(fiInterfaceProDto.getSourceNo());
			fpd.setCostType(costType);
			fpd.setSettlementType(1L);
			fpd.setIncomeDepartId(fiInterfaceProDto.getIncomeDepartId());//收入/创建部门ID
			fpd.setIncomeDepart(fiInterfaceProDto.getIncomeDepart());
			fpd.setAdmDepartId(fiInterfaceProDto.getAdmDepartId());
			fpd.setAdmDepart(fiInterfaceProDto.getAdmDepart());
			fpd.setWhoCash(fiInterfaceProDto.getWhoCash());
			
			this.addFiReceivabledetail(fpd);
			
			//收入更改
			if(!"代收货款".equals(fpd.getCostType())){
				List<FiInterfaceProDto> listfiInterfaceDto=new ArrayList<FiInterfaceProDto>();
				//fiInterfaceProDto.setAmount(amount);//差额
				listfiInterfaceDto.add(fpd);
				this.currentToFiIncome(listfiInterfaceDto);
			}
			
		}else{//更改客商、更改金额
			List<FiInterfaceProDto> listfiInterfaceDto=new ArrayList<FiInterfaceProDto>();
			//原客商费用写入负记录
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
				prefpd.setIncomeDepartId(fiInterfaceProDto.getIncomeDepartId());//收入/创建部门ID
				prefpd.setIncomeDepart(fiInterfaceProDto.getIncomeDepart());
				prefpd.setAdmDepartId(fiInterfaceProDto.getAdmDepartId());
				prefpd.setAdmDepart(fiInterfaceProDto.getAdmDepart());
				prefpd.setWhoCash(fiInterfaceProDto.getWhoCash());
				this.addFiReceivabledetail(prefpd);
				//原客商收入计算
				if(!"代收货款".equals(prefpd.getCostType())){
					//fiInterfaceProDto.setAmount(beforeAmount*-1);//更改前金额
					//fiInterfaceProDto.setCustomerId(precst.getId());//更改前客商ID
					//fiInterfaceProDto.setCustomerName(precst.getCusName());
					listfiInterfaceDto.add(prefpd);
				}
			}
			
			//新客商写入正记录
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
				fpd.setIncomeDepartId(fiInterfaceProDto.getIncomeDepartId());//收入/创建部门ID
				fpd.setIncomeDepart(fiInterfaceProDto.getIncomeDepart());
				fpd.setAdmDepartId(fiInterfaceProDto.getAdmDepartId());
				fpd.setAdmDepart(fiInterfaceProDto.getAdmDepart());
				fpd.setWhoCash(fiInterfaceProDto.getWhoCash());
				this.addFiReceivabledetail(fpd);
				//新客商收入计算
				if(!"代收货款".equals(fpd.getCostType())){
					//fiInterfaceProDto.setAmount(afamount);//更改后金额
					//fiInterfaceProDto.setCustomerId(cst.getId());//更改后客商ID
					//fiInterfaceProDto.setCustomerName(cst.getCusName());
					listfiInterfaceDto.add(fpd);
				}
			}
			
			//保存收入
			if(listfiInterfaceDto.size()>0){
				this.currentToFiIncome(listfiInterfaceDto);
			}
		}

	}
	

	//到付更改
	private void changeTopay (FiInterfaceProDto fiInterfaceProDto) throws Exception{
		String sourceData=fiInterfaceProDto.getSourceData();// 数据来源:中转成本(必输参数)
		Long sourceNo=fiInterfaceProDto.getSourceNo();// 来源单号:中转成本ID(必输参数)
		Long dno=fiInterfaceProDto.getDno();//配送单号
		Long stockStatus=fiInterfaceProDto.getStockStatus();//0：未出库，1：已出库
		String costType=fiInterfaceProDto.getCostType();//原费用类型
		double beforeAmount=fiInterfaceProDto.getBeforeAmount();//更改前总金额
		double goamount=fiInterfaceProDto.getAmount();//更改后金额
		double diffAmount=DoubleUtil.sub(goamount,beforeAmount);//更改差额
		Long customerId=fiInterfaceProDto.getGocustomerId();//供应商
		Long paymentStatus=null;
		String settlement=null;
		String createRemark=null;
		
		Customer cst = null;
		if(!"".equals(customerId)&&customerId!=null){
			cst=this.customerDao.get(customerId);
		}
		
		if(cst!=null){
			settlement = cst.getSettlement();// 结算方式(现结\月结\预付)
		}
		//更改备注
		createRemark="更改申请,更改申请单号："+fiInterfaceProDto.getSourceNo();
		
		if(cst==null||"现结".equals(settlement)||"预付".equals(settlement)){
			if(diffAmount!=0.0){
				List<FiPayment> list=this.fiPaymentDao.find("from FiPayment f where f.documentsNo=? and f.documentsSmalltype=? and f.status=1 and f.paymentType=1",dno,"配送单");
				for(FiPayment fiPayment:list){
						paymentStatus=fiPayment.getPaymentStatus();
						if("实配单".equals(fiPayment.getSourceData())||"自提单".equals(fiPayment.getSourceData())){
							sourceData=fiPayment.getSourceData();
							sourceNo=fiPayment.getSourceNo();
						}
				}
				if("".equals(paymentStatus)||paymentStatus==null){
					paymentStatus=1L;
				}
				
				//出库后调用应收应付接口
				if(stockStatus==1L){
					if(paymentStatus==1L||diffAmount>0.0){
						fiInterfaceProDto.setDocumentsType(fiInterfaceProDto.getDocumentsType());
						fiInterfaceProDto.setDocumentsSmalltype("配送单");
						fiInterfaceProDto.setDocumentsNo(dno);
						fiInterfaceProDto.setCostType(costType);
						fiInterfaceProDto.setSettlementType(1L);
						fiInterfaceProDto.setAmount(diffAmount);
						fiInterfaceProDto.setCreateRemark(createRemark);
						fiInterfaceProDto.setSourceData(sourceData);
						fiInterfaceProDto.setSourceNo(sourceNo);
					}else{
						fiInterfaceProDto.setDocumentsType(fiInterfaceProDto.getDocumentsType());
						fiInterfaceProDto.setDocumentsSmalltype("配送单");
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
				
				//收入更改
				if(!"代收货款".equals(fiInterfaceProDto.getCostType())){
					List<FiInterfaceProDto> listfiInterfaceDto=new ArrayList<FiInterfaceProDto>();
					fiInterfaceProDto.setAmount(diffAmount);//差额
					listfiInterfaceDto.add(fiInterfaceProDto);
					this.currentToFiIncome(listfiInterfaceDto);
				}
			}
		}else{
			/*if (cst == null)
				throw new ServiceException("客商不存在，请联系管理员!");
			if("月结".equals(settlement)){
				fiInterfaceProDto.setIncomeDepartId(fiInterfaceProDto.getDisDepartId());
				this.changePrepaid(fiInterfaceProDto);
			}else{
				throw new ServiceException("客商对应的结算方式不存在，请联系管理员!");
			}*/
			throw new ServiceException("到付不允许月结!");
		}

	}
	
	//中转费更改
	private void changeTotransit(FiInterfaceProDto fiInterfaceProDto) throws Exception{
		User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		Long dno=fiInterfaceProDto.getDno();//配送单号
		String costType=fiInterfaceProDto.getCostType();//费用类型(中转成本)
		double beforeAmount=fiInterfaceProDto.getBeforeAmount();//更改前总金额
		Double goamount=fiInterfaceProDto.getAmount();//新金额
		double diffAmount=DoubleUtil.sub(goamount,beforeAmount);//更改差额
		Long disDepartId=fiInterfaceProDto.getDisDepartId();//配送部门ID
		Long customerId=fiInterfaceProDto.getGocustomerId();//中转客商ID
		if(dno==null||"".equals(dno)) throw new ServiceException("配送单号不存在!");
		

		//已审核中转成本
		List<FiTransitcost> fiTransitcostlist=this.fiTransitService.find("from FiTransitcost f where f.dno=? and f.sourceData=?  and  f.status=1",dno,"传真录入");
		//REVIEW-ACCEPT 做非空判断
		//FIXED
		if(fiTransitcostlist.size()!=0){
			Customer cst=null;
			if(customerId==null){
				throw new ServiceException("请输入对应的客商ID");
			}else{
				cst = this.customerDao.get(customerId);
				if(cst==null){
					throw new ServiceException("请客商ID对应的客商不存在");
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
			 fiTraNew.setReviewRemark("更改申请金额变化，写入差额");
			 fiTraNew.setReviewUser(user.get("name")+"");
			 fiTraNew.setSourceData("更改申请");
			 fiTraNew.setSourceNo(fiInterfaceProDto.getSourceNo());
			 fiTraNew.setStatus(0l);//审核状态
			 fiTransitService.save(fiTraNew);     //写入更改申请中转成本差额，并已审核
			
		}
		
	//	if(fiTransitcostlist.size()>0){
		//	throw new ServiceException("请撤销付款再更改！");
			/*Customer cst=null;
			if(customerId==null){
				throw new ServiceException("请输入对应的客商ID");
			}else{
				cst = this.customerDao.get(customerId);
				if(cst==null){
					throw new ServiceException("请客商ID对应的客商不存在");
				}
			}
			String settlement = cst.getSettlement();// 结算方式(现结\月结\预付)
			
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
			 fiTraNew.setReviewRemark("更改申请金额变化，写入差额");
			 fiTraNew.setReviewUser(user.get("name")+"");
			 fiTraNew.setSourceData("更改申请");
			 fiTraNew.setSourceNo(fiInterfaceProDto.getSourceNo());
			 fiTraNew.setStatus(1l);//审核状态
			 fiTransitService.save(fiTraNew);     //写入更改申请中转成本差额，并已审核
			  
			 oprHistoryService.saveLog(dno, "审核中转成本(更改申请)，审核金额："+diffAmount, log_auditCost);     //操作日志

			FiCost fiCostNew = new FiCost();
			fiCostNew.setCostType("中转成本");
			fiCostNew.setCostTypeDetail("更改申请");
			fiCostNew.setCostAmount(diffAmount);
			fiCostNew.setDataSource("中转成本");
			fiCostNew.setSourceSignNo(fiTraNew.getId()+"");
			fiCostNew.setDno(dno);
			fiCostNew.setStatus(1l);
			fiCostDao.save(fiCostNew);  //重新写入成本表
			
				if("现结".equals(settlement)||"预付".equals(settlement)){
					if(diffAmount>0.0){
						fiInterfaceProDto.setDocumentsType("成本");
						fiInterfaceProDto.setDocumentsSmalltype("配送单");
						fiInterfaceProDto.setDocumentsNo(dno);
						fiInterfaceProDto.setCostType(costType);
						fiInterfaceProDto.setSettlementType(2L);
						fiInterfaceProDto.setAmount(diffAmount);
						fiInterfaceProDto.setSourceNo(fiTraNew.getId());
						fiInterfaceProDto.setCreateRemark("更改申请");
					}else{
						fiInterfaceProDto.setDocumentsType("成本");
						fiInterfaceProDto.setDocumentsSmalltype("配送单");
						fiInterfaceProDto.setDocumentsNo(dno);
						fiInterfaceProDto.setCostType(costType);
						fiInterfaceProDto.setSourceNo(fiTraNew.getId());
						fiInterfaceProDto.setSettlementType(1L);
						fiInterfaceProDto.setAmount(Math.abs(diffAmount));
						fiInterfaceProDto.setCreateRemark("更改申请");
					}
					this.addFiPayment(fiInterfaceProDto);
				}else if("月结".equals(settlement)){
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
					throw new ServiceException("客商对应的结算方式不存在，请在基础数据维护");
				}*/
	//	}
	}
	//REVIEW-ACCEPT 增加注释
	//FIXED 接口中已有注释
	public String currentToFiFiCost(List<FiInterfaceProDto> listfiInterfaceDto)throws Exception{
		double amount=0.0;
		Long dno=0L;
		String sourceData=null;
		Long sourceNo=0L;
		String costType1;//成本大类
		String costTypeDetail;  //成本小类
		
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
			fiCostDao.save(fiCostNew);  //重新写入成本表
		}
		return "写入成本成功!";
	}
	
	//外发费更改
	private void changeToOutCost(FiInterfaceProDto fiInterfaceProDto) throws Exception{
		User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		Long dno=fiInterfaceProDto.getDno();//配送单号
		String costType=fiInterfaceProDto.getCostType();//费用类型(中转成本)
		double beforeAmount=fiInterfaceProDto.getBeforeAmount();//更改前总金额
		Double goamount=fiInterfaceProDto.getAmount();//新金额
		double diffAmount=DoubleUtil.sub(goamount,beforeAmount);
		Long paymentStatus=null;
		Long customerId=fiInterfaceProDto.getGocustomerId();
		StringBuffer sb=new StringBuffer();
		if(dno==null||"".equals(dno)) throw new ServiceException("配送单号不存在!");
		
		Customer cst=null;
		if(customerId==null){
			throw new ServiceException("请输入对应的客商ID");
		}else{
			cst = this.customerDao.get(customerId);
			if(cst==null){
				throw new ServiceException("请客商ID对应的客商不存在");
			}
		}
		String settlement = cst.getSettlement();// 结算方式(现结\月结\预付)
		//已审核外发成本
		List<FiOutcost> outList=this.fiOutCostDao.find("from FiOutcost fu where fu.dno=? and fu.sourceData=?  and  fu.status=1 and  fu.isdelete=1 ",dno,"传真录入");
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
			fiout.setSourceData("更改申请");
			fiout.setStatus(1l);
			fiOutCostDao.save(fiout);     //写入更改申请中转成本差额，并已审核

			FiCost fiCostNew = new FiCost();
			fiCostNew.setCostType("外发成本");
			fiCostNew.setCostTypeDetail("更改申请");
			fiCostNew.setCostAmount(diffAmount);
			fiCostNew.setDataSource("外发成本");
			fiCostNew.setDno(dno);
			fiCostNew.setSourceSignNo(fiInterfaceProDto.getSourceNo()+"");
			fiCostNew.setStatus(1l);
			fiCostDao.save(fiCostNew);  //重新写入成本表
			sb.append("通过更改申请将原金额:"+beforeAmount+"更改为:"+goamount);
			sb.append("更改申请ID为："+fiInterfaceProDto.getSourceNo());
			
			if("现结".equals(settlement)||"预付".equals(settlement)){
				if(diffAmount>0.0){
					fiInterfaceProDto.setDocumentsType("成本");
					fiInterfaceProDto.setDocumentsSmalltype("配送单");
					fiInterfaceProDto.setDocumentsNo(dno);
					fiInterfaceProDto.setCostType(costType);
					fiInterfaceProDto.setSettlementType(2L);
					fiInterfaceProDto.setAmount(diffAmount);
					fiCostNew.setDataSource("外发成本");
					fiInterfaceProDto.setSourceNo(batchNo);
					fiInterfaceProDto.setCreateRemark(sb.toString());
				}else{
					fiInterfaceProDto.setDocumentsType("收入");
					fiInterfaceProDto.setDocumentsSmalltype("配送单");
					fiInterfaceProDto.setDocumentsNo(dno);
					fiInterfaceProDto.setCostType(costType);
					fiInterfaceProDto.setSettlementType(1L);
					fiCostNew.setDataSource("外发成本");
					fiInterfaceProDto.setSourceNo(batchNo);
					fiInterfaceProDto.setAmount(Math.abs(diffAmount));
					fiInterfaceProDto.setCreateRemark(sb.toString());
				}
				this.addFiPayment(fiInterfaceProDto);

			}else if("月结".equals(settlement)){
				customerId=fiInterfaceProDto.getCustomerId();
				if("".equals(customerId)||customerId==null) throw new ServiceException("外发客商Id不能为空");

				if(goamount!=0.0){
					FiInterfaceProDto fpd=new FiInterfaceProDto();
					fpd.setCustomerId(cst.getId());
					fpd.setCustomerName(cst.getCusName());
					fpd.setDno(fiInterfaceProDto.getDno());
					fpd.setAmount(diffAmount);
					fpd.setSourceData("外发成本");
					fpd.setSourceNo(batchNo);
					fpd.setCreateRemark(sb.toString());
					fpd.setCostType(costType);
					fpd.setSettlementType(2L);
					this.addFiReceivabledetail(fpd);
				}
			}else{
				throw new ServiceException("客商对应的结算方式不存在，请在基础数据维护");
			}
			
			}else{
				throw new ServiceException("外发成本未审核，不需要更改");
			}
	}
	//REVIEW-ANN 增加注释
	public int invalidToFi(List<FiInterfaceProDto> listfiInterfaceDto) throws Exception {
		int size=0;
		for (FiInterfaceProDto fiInterfaceProDto : listfiInterfaceDto) {
			String settlement=null;
			Customer cst=null;
			if(fiInterfaceProDto.getCustomerId()!=null&&!"".equals(fiInterfaceProDto.getCustomerId())){
				cst = this.customerDao.get(fiInterfaceProDto.getCustomerId());
			}
				if(cst!=null){
					settlement = cst.getSettlement();// 结算方式(现结\月结\预付)
				}
				if (cst==null||"现结".equals(settlement)||"预付".equals(settlement)) {
					size=this.invalidToFiPayment(fiInterfaceProDto);
				} else if ("月结".equals(settlement)) {
					size=this.invalidToFiReceivabledetail(fiInterfaceProDto);
				} else if ("预付".equals(settlement)) {
					size=this.invalidToFiPayment(fiInterfaceProDto);
					//this.invalidToFiAdvance(fiInterfaceProDto);
				} else {
					throw new ServiceException("客商结算方式不存在，请联系管理员!");
				}
		}
		//return "保存成功!";
		return size;
	}

	//REVIEW-ANN 增加注释
	public int invalidToFiPayment(FiInterfaceProDto fiInterfaceProDto)throws Exception{
		String sourceData;// 数据来源
		Long sourceNo;// 来源单号
		Long status;
		
		sourceData=fiInterfaceProDto.getSourceData();
		sourceNo=fiInterfaceProDto.getSourceNo();
		List<FiPayment> list=null;
		if("外发成本".equals(sourceData)){
			String costType=fiInterfaceProDto.getCostType();
			Long documentsNo=fiInterfaceProDto.getDocumentsNo();
			Long customerId=fiInterfaceProDto.getCustomerId();
			if(customerId==null||"".equals(customerId)) throw new ServiceException("客商id不能为空！");
			Customer cst = this.customerDao.get(customerId);
			if (cst == null) throw new ServiceException("客商不存在，请联系管理员!");
			list=this.fiPaymentDao.find("from FiPayment f where f.costType=? and f.documentsNo=? and f.customerId=? and f.status=1",costType,documentsNo,customerId);
		}else{
			list=this.fiPaymentDao.find("from FiPayment f where f.sourceData=? and f.sourceNo=? and f.status=1",sourceData,sourceNo);
		}
		//REVIEW list使用前做非空判断
		if(list.size()==0){
				///throw new ServiceException("作废失败，没有找到对应的财务数据，不能作废！");
			return 0;
			}
		for(FiPayment fiPayment:list){
			status=fiPayment.getStatus();
			if(status==0L){
				throw new ServiceException("作废失败，应收付款单号["+fiPayment.getId()+"]已经作废，不能重复作废！");
			}
			if(fiPayment.getPaymentStatus()==1L||fiPayment.getPaymentStatus()==4L){//未收银
				fiPayment.setStatus(0L);
			}else{
				throw new ServiceException("作废失败，应收付单号已收银，不能作废！");
			}
			this.fiPaymentDao.save(fiPayment);
		}

		return list.size();
	}
	//REVIEW-ACCEPT 增加注释
	public String invalidToFiPaymentByDno(FiInterfaceProDto fiInterfaceProDto)throws Exception{
		String sourceData;// 数据来源
		Long sourceNo;// 来源单号
		Long dno;//配送单号
		String costType;//费用类型
		String createRemark;
		dno=fiInterfaceProDto.getDno();
		sourceData=fiInterfaceProDto.getSourceData();
		sourceNo=fiInterfaceProDto.getSourceNo();
		createRemark=fiInterfaceProDto.getCreateRemark();
		if(dno==null||"".equals(dno)) throw new ServiceException("配送单号不存在！");
		if(sourceData==null||"".equals(sourceData)) throw new ServiceException("数据来源不存在！");
		if(sourceNo==null||"".equals(sourceNo)) throw new ServiceException("来源单号不存在！");
		
		List<FiPayment> list=this.fiPaymentDao.find("from FiPayment f where f.documentsSmalltype='配送单' and f.documentsNo=? and f.status=1",dno);
		//REVIEW list做非空判断
		for(FiPayment fiPayment:list){
			costType=fiPayment.getCostType();
			if("代收货款".equals(costType)||"到付提送费".equals(costType)||"到付专车费".equals(costType)||"到付增值费".equals(costType)||"中转费".equals(costType)){
				if(fiPayment.getPaymentStatus()==1L||fiPayment.getPaymentStatus()==4L){//未收银
					fiPayment.setStatus(0L);
					fiPayment.setCreateRemark(createRemark);
					this.fiPaymentDao.save(fiPayment);
				}else{
					FiPayment fp=new FiPayment();
					fp.setPaymentType(fiPayment.getPaymentType()==1L?2L:1L);
					fp.setReviewStatus(fiPayment.getPaymentType()==1L?1L:0L);//默认付款单为未审核，收款单为已审核
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
				
		return "到付费用作废成功!";
	}
	
	//REVIEW-ANN 增加注释
	//FIXED 接口中已有注释
	public int invalidToFiReceivabledetail(FiInterfaceProDto fiInterfaceProDto)throws Exception{
		String sourceData;// 数据来源
		Long sourceNo;// 来源单号
		String costType;//费用类型
		
		sourceData=fiInterfaceProDto.getSourceData();
		sourceNo=fiInterfaceProDto.getSourceNo();
		costType=fiInterfaceProDto.getCostType();
		List<FiReceivabledetail> list=null;
		
		if("外发费".equals(costType)){
			Long customerId=fiInterfaceProDto.getCustomerId();
			Long dno=fiInterfaceProDto.getDno();
			if("".equals(dno)||dno==null)  throw new ServiceException("请输入配送单号！");
			if("".equals(dno)||dno==null)  throw new ServiceException("客商ID不能为空！");
			list=this.fiReceivabledetailDao.find("from FiReceivabledetail f where f.dno=? and f.customerId=?",dno,customerId);
		}else{
			list=this.fiReceivabledetailDao.find("from FiReceivabledetail f where f.sourceData=? and f.sourceNo=?",sourceData,sourceNo);
		}
		if(list.size()==0){
			//throw new ServiceException("作废失败，没有找到对应的财务数据，不能作废！");
			return 0;
		}
		for(FiReceivabledetail fiReceivabledetail:list){
			if(fiReceivabledetail.getReconciliationStatus()>1L){
				throw new ServiceException("作废失败，往来明细单号["+fiReceivabledetail.getId()+"]已经对账，不能作废！");
			}
			
			//作废写入负记录
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
	
	//REVIEW-ACCEPT 增加注释
	//FIXED 接口中已有注释
	public String invalidToFiReceivabledetailByDno(FiInterfaceProDto fiInterfaceProDto)throws Exception{
		String sourceData;// 数据来源
		Long sourceNo;// 来源单号
		Long dno;//配送单号
		String costType;//费用类型
		String createRemark;//备注
		dno=fiInterfaceProDto.getDno();
		sourceData=fiInterfaceProDto.getSourceData();
		sourceNo=fiInterfaceProDto.getSourceNo();
		createRemark=fiInterfaceProDto.getCreateRemark();
		if(dno==null||"".equals(dno)) throw new ServiceException("配送单号不存在！");
		if(sourceData==null||"".equals(sourceData)) throw new ServiceException("数据来源不存在！");
		if(sourceNo==null||"".equals(sourceNo)) throw new ServiceException("来源单号不存在！");
		
		List<FiReceivabledetail> list=this.fiReceivabledetailDao.find("from FiReceivabledetail f where f.dno=?",dno);
		for(FiReceivabledetail fiReceivabledetail:list){
			costType=fiReceivabledetail.getCostType();
			if("代收货款".equals(costType)||"到付提送费".equals(costType)||"到付增值费".equals(costType)||"到付专车费".equals(costType)||"外发费".equals(costType)){
				//作废写入负记录
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
	
	//REVIEW-ANN 增加注释
	//FIXED 接口中已有注释
	public String invalidToFiAdvance(FiInterfaceProDto fiInterfaceProDto)throws Exception{
		String sourceData;// 数据来源
		Long sourceNo;// 来源单号
		Long status;
		Long settlementType;
		sourceData=fiInterfaceProDto.getSourceData();
		sourceNo=fiInterfaceProDto.getSourceNo();
		List<FiAdvance> list=this.fiAdvanceDao.find("from FiAdvance f where f.sourceData=? and f.sourceNo=?",sourceData,sourceNo);
		if(list.size()==0){
			throw new ServiceException("作废失败，没有找到对应的财务数据，不能作废！");
		}
		for(FiAdvance fiAdvance:list){
			double settlementAmount=0.0;
			status=fiAdvance.getStatus();
			if(status==0L) throw new ServiceException("作废失败，预存款单号["+fiAdvance.getId()+"]已经作废，不能作废！");
			settlementType=fiAdvance.getSettlementType();
			if(settlementType == 1L){//存款
				settlementAmount=DoubleUtil.sub(fiAdvance.getSettlementAmount(),-1);
			}else{
				settlementAmount=fiAdvance.getSettlementAmount();
			}
			
			//更新预存款账号余额
			FiAdvanceset fat = this.fiAdvancesetDao.get(fiAdvance.getFiAdvanceId());
			if(fat==null)throw new ServiceException("作废失败，预存款账号不存在，不能作废！");
			fat.setOpeningBalance(DoubleUtil.add(fat.getOpeningBalance(), settlementAmount));
			this.fiAdvancesetDao.save(fat);
			
			//保存预存款明细
			FiAdvance fa = new FiAdvance();
			fa.setSettlementType(fiAdvance.getSettlementType());// 付款
			fa.setCustomerId(fiAdvance.getCustomerId());// 预付款结算设置：客商ID
			fa.setCustomerName(fiAdvance.getCustomerName());
			fa.setSettlementAmount(settlementAmount);// 本次结算金额
			fa.setSettlementBalance(fat.getOpeningBalance());// 余额
			fa.setSourceData(sourceData);
			fa.setSourceNo(sourceNo);
			fa.setFiAdvanceId(fa.getId());
			this.fiAdvanceDao.save(fa);
		}
		return "预存款单作废成功!";
	}
	
	//REVIEW-ANN 增加注释
	//FIXED 接口中已有注释
	public String changePaymentAmount(List<FiInterfaceProDto> listfiInterfaceDto)throws Exception{
		String sourceData;// 数据来源
		Long sourceNo;// 来源单号
		Double amount ;//撤销金额
		Long paymentStatus;
		
		for(FiInterfaceProDto fiInterfaceProDto:listfiInterfaceDto){
			sourceData=fiInterfaceProDto.getSourceData();
			sourceNo=fiInterfaceProDto.getSourceNo();
			amount=fiInterfaceProDto.getAmount();
			
			if(amount==null||amount<=0.0) throw new ServiceException("操作失败，修改金额必须大于零！");
			List<FiPayment> list=this.fiPaymentDao.find("from FiPayment f where f.sourceData=? and f.sourceNo=? and f.paymentType=2 and f.status=1",sourceData,sourceNo);
			if(list.size()==0){
				throw new ServiceException("操作失败，没有找到对应的财务数据！");
			}
			for(FiPayment fiPayment:list){
				paymentStatus=fiPayment.getPaymentStatus();
				if(paymentStatus!=4L){
					throw new ServiceException("操作失败，只能修改未付款单据金额！");
				}
				amount=DoubleUtil.sub(fiPayment.getAmount(), amount);
				if(amount<0.0) throw new ServiceException("操作失败，修改后金额必须大于零！");
				if(amount==0.0){
					fiPayment.setStatus(0L);
				};
				fiPayment.setAmount(amount);
				this.fiPaymentDao.save(fiPayment);
			}
		}

		return "付款单金额更改成功!";
	}
	
	//REVIEW-ACCEPT 增加注释
	//FIXED 接口中已有注释
	public String currentToFiIncome(List<FiInterfaceProDto> listfiInterfaceDto)throws Exception{
		Long dno;// 配送单号(必输参数)
		Long customerId;// 客商ID
		String customerName;// 客商名称
		Double amount;// 金额(必输参数)
		String costType;// 费用类型：专车费/增值服务费/代理应付费/代收货款/到付提送费/到付增值费/其他收入/对账/中转费(必输参数)
		String sourceData;// 数据来源:(必输参数)
		Long sourceNo;// 来源单号:(必输参数)
		String whoCash;//付款方
		String customerService;//客服员
		Date accounting;//会计日期
		OprFaxIn oprFaxIn=null;
		String incomeDepart;//收入部门
		Long incomeDepartId;//收入部门ID
		Long admDepartId;//客服员业务部门ID
		String admDepart;//客服员行政部门
		
		
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
			
			
			//其他收入时，客服员和客服部门记录入或录入或审核人部门
			if(!"".equals(dno)&&dno!=null){
				oprFaxIn=this.oprFaxInService.get(dno);
				admDepart=oprFaxIn.getCusDepartName();
				customerService=oprFaxIn.getCustomerService();
			}
			
			if(!"其他收入".equals(costType)){
				if(oprFaxIn==null) throw new ServiceException("传真表中配送单号不存在!");
				
				if (dno==null)
					throw new ServiceException("请输入配送单号!");
			}
			
			if (amount == 0.0||amount==null)
				throw new ServiceException("请输入收入金额!");
			if ("".equals(sourceData))
				throw new ServiceException("请输入数据来源!");
			if (sourceNo==null)
				throw new ServiceException("请输入来源单号!");
			if (!"预付提送费".equals(costType)&&!"预付专车费".equals(costType)&&!"到付专车费".equals(costType)&&!"到付提送费".equals(costType)&&!"预付增值费".equals(costType)&&!"到付增值费".equals(costType)&&!"其他收入".equals(costType)){
				throw new ServiceException("收入费用类型不存在，写入收入失败!");
			}
			if(admDepartId==null||"".equals(admDepartId)){
				throw new ServiceException("数据写入收入表时，必须指定收入客服部门ID!");
			}
			if(incomeDepartId==null||"".equals(incomeDepartId)){
				throw new ServiceException("数据写入收入表时，必须指定收入部门ID!");
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
		return "收入写入成功";
	}
	//REVIEW-ACCEPT 增加注释
	//FIXED 接口中已有注释
	public String oprReturnToFiTransitcost(List<FiInterfaceProDto> listfiInterfaceDto)throws Exception{
		Long dno;// 配送单号(必输参数)
		Double amount;// 金额(必输参数)
		String sourceData;// 数据来源:(必输参数)实配单
		Long sourceNo;// 来源单号:(必输参数)实配单号
		Long customerId;// 客商ID
		String distributionMode;// 配送方式(新邦/中转/外发)
		for(FiInterfaceProDto ipd:listfiInterfaceDto){
			dno=ipd.getDno();
			amount=ipd.getAmount();
			sourceData=ipd.getSourceData();
			sourceNo=ipd.getSourceNo();
			distributionMode=ipd.getDistributionMode();
			customerId=ipd.getCustomerId();
			Customer cst = this.customerDao.get(customerId);
			if(cst==null){
				throw new ServiceException("此客商ID的客商不存在或者已经删除，无法写入成本数据");
			}
//			if(amount<0.0){
//				throw new ServiceException("返货成本不能小于零");
//			}
			if (!"返货登记".equals(sourceData)){
				throw new ServiceException("请输入正确的数据来源!");
			}
			if (sourceNo==null){
				throw new ServiceException("请输入来源单号!");
			}
			if("中转".equals(distributionMode)){
				//List<FiTransitcost> listTransitcost=this.fiTransitService.find("from FiTransitcost f where f.dno=?",dno);
/*				if(amount<0.0){
					throw new ServiceException("返货成本不能小于零");
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
			
			}else if("外发".equals(distributionMode)){
				/*
				List<FiOutcost>list =fiOutCostService.find("from FiOutcost fo where fo.dno=? and fo.isdelete=1 and fo.sourceData=? and fo.status=1 ",dno,"传真录入");
				//如果已审核，则取消成本
				if(list.size()!=0){
					double totalMoney=0.0;
					for(FiOutcost fiocost:list){
						totalMoney=DoubleUtil.add(totalMoney, fiocost.getAmount());
						fiocost.setIsdelete(0l);
						fiOutCostService.save(fiocost);
					}
					
					FiCost fiCostNew = new FiCost();
					fiCostNew.setCostType("外发成本");
					fiCostNew.setCostTypeDetail("返货登记");
					fiCostNew.setCostAmount(-totalMoney);
					fiCostNew.setDataSource("外发成本");
					fiCostNew.setDno(dno);
					fiCostNew.setStatus(1l);
					fiCostDao.save(fiCostNew);  
				}*/
				
				//如果未审核，并已录入外发成本，作废
/*				List<FiOutcost>listO =fiOutCostService.find("from FiOutcost fo where fo.dno=? and fo.isdelete=1 and fo.sourceData=? and fo.status=0 and fo.returnStatus=0 ",dno,"传真录入");
				for(FiOutcost fiOutcost:listO){
		//			fiOutcost.setIsdelete(0l);
					fiOutcost.setReturnStatus(1l);
					fiOutCostService.save(fiOutcost);
				}
				FiCost fiCostNew2 = new FiCost();
				fiCostNew2.setCostType("外发成本");
				fiCostNew2.setCostTypeDetail("返货登记");
				fiCostNew2.setCostAmount(amount);
				fiCostNew2.setDataSource("返货登记");
				fiCostNew2.setDno(dno);
				fiCostNew2.setStatus(1l);
				fiCostDao.save(fiCostNew2);  */				
				User user= WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
				Long bussDepartId=Long.parseLong(user.get("bussDepart")+"");
				OprFaxIn oprFaxIn= oprFaxInService.get(dno);
				
				List<FiOutcost>list =fiOutCostDao.find("from FiOutcost fo where fo.dno=? and fo.isdelete=1 and fo.sourceData=? and fo.departId=? and fo.customerId=? and fo.amount>0 ",dno,"传真录入",bussDepartId,oprFaxIn.getGoWhereId());
				if(list.size()==0){
					if(amount!=0.0){
						throw new ServiceException("外发成本未录入，不允许录入返货成本");
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
				throw new ServiceException("不存在的类型，不能写入财务成本");
			}
		}
		return "写入成功";
	}

	public String oprReturnToFi(List<FiInterfaceProDto> listfiInterfaceDto)throws Exception{
		Long dno;// 配送单号(必输参数)
		Long customerId;// 客商ID(中转客商ID，外发客商ID，收货人客商ID，如果没有就为空)
		Customer customer=null;
		String settlement="";// 结算方式(现结\月结\预付)
		for(FiInterfaceProDto ipd:listfiInterfaceDto){
			dno=ipd.getDno();
			customerId=ipd.getCustomerId();
			if(!"".equals(customerId)&&customerId!=null){
				customer=this.customerDao.get(customerId);
				settlement = customer.getSettlement();
			}
			if ("".equals(settlement)||"现结".equals(settlement)||"预付".equals(settlement)) {
				this.invalidToFiPaymentByDno(ipd);
			} else if ("月结".equals(settlement)) {
				this.invalidToFiReceivabledetailByDno(ipd);
			}else {
				throw new ServiceException("结算方式不存在，请联系管理员!");
			}
			
		}
		
		return "撤销应收数据成功！";
	}
	
	//REVIEW-ACCEPT 增加注释
	//FIXED 接口中已有注释
	public String revocationFiDeliveryCost(List<FiInterfaceProDto> listfiInterfaceDto)throws Exception{
		String sourceData;// 数据来源
		Long sourceNo;// 来源单号
		double amount = 0.0;//撤销总金额
		for(FiInterfaceProDto fiInterfaceProDto:listfiInterfaceDto){
			sourceData=fiInterfaceProDto.getSourceData();
			sourceNo=fiInterfaceProDto.getSourceNo();
			amount=fiInterfaceProDto.getAmount();
			List<FiPayment> list=this.fiPaymentDao.find("from FiPayment f where f.sourceData=? and f.sourceNo=? and f.status=1",sourceData,sourceNo);
			if(list.size()==0){
				throw new ServiceException("有找到对应的财务数据！");
			}
			if(list.size()>1){
				throw new ServiceException("存在多个付款单！");
			}
			
			FiPayment fiPayment=list.get(0);
			if(fiPayment.getPaymentStatus()!=4L){
				throw new ServiceException("操作失败，只能修改未付款单据金额！");
			}
			if(fiPayment.getAmount()==0.0){
				fiPayment.setStatus(0L);
			}else{
				fiPayment.setAmount(DoubleUtil.sub(fiPayment.getAmount(), amount));
			}
			this.fiPaymentDao.save(fiPayment);
		}
		return "撤销提货成本成功!";
	}
}


