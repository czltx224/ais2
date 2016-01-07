package com.xbwl.oper.fax.service.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.springframework.beans.BeanUtils;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.orm.Page;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.cus.service.IConsigneeInfoService;
import com.xbwl.entity.BasCusService;
import com.xbwl.entity.ConsigneeInfo;
import com.xbwl.entity.Customer;
import com.xbwl.entity.OprChangeDetail;
import com.xbwl.entity.OprFaxIn;
import com.xbwl.entity.OprFaxMain;
import com.xbwl.entity.OprFaxOut;
import com.xbwl.entity.OprReceipt;
import com.xbwl.entity.OprRequestDo;
import com.xbwl.entity.OprStatus;
import com.xbwl.entity.OprValueAddFee;
import com.xbwl.entity.SysDepart;
import com.xbwl.finance.dto.IFiInterface;
import com.xbwl.finance.dto.impl.FiInterfaceProDto;
import com.xbwl.oper.edi.service.IOprFaxOutService;
import com.xbwl.oper.fax.dao.IOprFaxChangeDetailDao;
import com.xbwl.oper.fax.dao.IOprFaxInDao;
import com.xbwl.oper.fax.dao.IOprFaxMainDao;
import com.xbwl.oper.fax.service.IOprFaxChangeDetailService;
import com.xbwl.oper.fax.service.IOprFaxInService;
import com.xbwl.oper.receipt.dao.IOprReceiptDao;
import com.xbwl.oper.stock.service.IOprRequestDoService;
import com.xbwl.oper.stock.service.IOprStatusService;
import com.xbwl.oper.stock.service.IOprValueAddFeeService;
import com.xbwl.rbac.Service.IDepartService;
import com.xbwl.sys.service.IBasCusService;
import com.xbwl.sys.service.ICustomerService;

/**
 *@author LiuHao
 *@time Aug 25, 2011 3:03:18 PM
 */
@Service("oprFaxChangeDetailServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class OprFaxChangeDetailServiceImpl extends BaseServiceImpl<OprChangeDetail,Long> implements
		IOprFaxChangeDetailService {
	@Resource(name="oprFaxChangeDetailHibernateDaoImpl")
	private IOprFaxChangeDetailDao oprFaxChangeDetailDao;
	
	@Resource(name="oprFaxInHibernateDaoImpl")
	private IOprFaxInDao oprFaxInDao;
	@Resource(name="oprFaxInServiceImpl")
	private IOprFaxInService  oprFaxInService;
	@Resource(name="oprStatusServiceImpl")
	private IOprStatusService oprStatusService;
	@Resource(name="fiInterfaceImpl")
	private IFiInterface fiInterface;
	@Resource(name="oprValueAddFeeServiceImpl")
	private IOprValueAddFeeService oprValueAddFeeService;
	@Resource(name="oprReceiptHibernateDaoImpl")
	private IOprReceiptDao oprReceiptDao;
	@Resource(name="oprFaxMainHibernateDaoImpl")
	private IOprFaxMainDao oprFaxMainDao;
	
	@Resource(name="basCusServiceImpl")
	private IBasCusService basCusService;
	@Resource(name="departServiceImpl")
	private IDepartService departService;
	@Resource(name = "consigneeInfoServiceImpl")
	private IConsigneeInfoService consigneeInfoService;
	@Resource(name = "customerServiceImpl")
	private ICustomerService customerService;
	@Resource(name = "oprRequestDoServiceImpl")
	private IOprRequestDoService oprRequestDoService;
	@Resource(name="oprFaxOutServiceImpl")
	private IOprFaxOutService oprFaxOutService;
	@Override
	public IBaseDAO getBaseDao() {
		return oprFaxChangeDetailDao;
	}
	public Page<OprChangeDetail> findDetailByMainId(Page page, Long id)
			throws Exception {
		Page<OprChangeDetail> page1=this.getPageBySql(page,"select ocd.change_field_zh,ocd.change_pre,ocd.change_post from opr_change_detail ocd where ocd.opr_change_main_id=?", id+"");
		return page1;
	}
	public String updateChangeRecord(List<OprValueAddFee> addFeeList,List<OprChangeDetail> list, OprFaxIn ofi,Long oprDetailId,String oprMan)
			throws Exception {
		OprStatus os=oprStatusService.findStatusByDno(ofi.getDno());
		oprFaxInDao.save(ofi);
		updateAddFeeDetial(addFeeList, ofi,oprMan);
		updateMainMsg(list,ofi,oprMan);
		String returnMsg="";
		if(os.getOutStatus()==0 || os.getOutStatus() ==3){
			returnMsg=notOutStock(list, ofi,oprDetailId,oprMan,false);
		}else{
			returnMsg=notOutStock(list, ofi,oprDetailId,oprMan,true);
		}
		return returnMsg;
		
	}
	/**
	 * 如果没有出库
	 * @param changeDetailList
	 * @param ofi
	 * @param oprDetailId
	 * @return
	 * @throws Exception
	 */
	private String notOutStock(List<OprChangeDetail> changeDetailList,OprFaxIn ofi,Long oprDetailId,String oprMan,boolean isOutStore)throws Exception{
//		Double sonderP=0.0;
//		Double cpFee=0.0;
//		Double consigneeFee=0.0;
//		Double chsonderP=0.0;
//		Double chcpFee=0.0;
//		Double chconsigneeFee=0.0;
		String returnMsg="true";
		Long outStockStatus=0L;
		if(isOutStore){
			outStockStatus=1L;
		}
		//代收货款、预付款更改所需List
		List<FiInterfaceProDto> list=new ArrayList<FiInterfaceProDto>();
		//收入接口所需List
		//List<FiInterfaceProDto> incomeList=new ArrayList<FiInterfaceProDto>();
		//User user=WebRalasafe.getCurrentUser(Struts2Utils.getRequest());
		
		BasCusService cusService=basCusService.getCusServiceByCusId(ofi.getCusId(), ofi.getInDepartId());
		if(cusService == null){
			throw new ServiceException("数据异常，代理ID为:"+ofi.getCusId()+"的客商对应的客服员为空！");
		}
		SysDepart sd=departService.getDepartByDepartNo(cusService.getServiceDepartCode());
		if(sd == null){
			throw new ServiceException("部门编码:"+cusService.getServiceDepartCode()+"对应的部门信息为空了，请联系系统管理员！");
		}
		//是否更改了代理
		boolean isChangeCus=false;
		//是否更改预付提送费
		boolean isChangeCpFee=false;
		//是否更改了预付增值费
		boolean isChangeCpAdd=false;
		//是否更改了代收货款
		boolean isChangePay=false;
		//是否更改了预付专车费
		boolean isChangeCpSon=false;
		//到付提送费
		boolean isChangeConFee = false;
		//到付增值费
		boolean isChangeConAdd = false;
		//到付专车费
		boolean isChangeSon = false;
		Long preCusId = ofi.getCusId();
//		String preCusName = ofi.getCpName();
		for(OprChangeDetail ocd : changeDetailList){
			if(ocd.getChangeField().equals("cusId")){
				preCusId = Long.valueOf(ocd.getChangePre());
				isChangeCus = true;
			}
//			if(ocd.getChangeField().equals("cusName")){
//				preCusName = ocd.getChangePre();
//			}
		}
		
		for (OprChangeDetail ocd : changeDetailList) {
			//费用名称
			String feeName="";
			//费用类型
			String feeType="";
			//是否构造财务VO
			boolean isFiInterface=false;
			if(ocd.getChangeField().equals("whoCash")&&ocd.getChangePost().equals("预付")){
				decideDebt(ofi,changeDetailList);
			}
			if(ocd.getChangeField().equals("cpFee")){
				feeName="预付提送费";
				feeType="收入";
				isFiInterface = true;
				isChangeCpFee = true;
				//isChangeCpFee = true;
//				if(isChangeCus){
//					if(ofi.getCpFee()!=null && !ofi.getCpFee().equals(0)){
//						ocd.setChangePre(ofi.getCpFee().toString());
//						ocd.setChangePost(ofi.getCpFee().toString());
//					}
//				}
//				FiInterfaceProDto fip = this.getFiList(ofi, ocd, oprMan, oprDetailId, "预付提送费",preCusId,sd,cusService,outStockStatus,"收入");
//				list.add(fip);
			}
			if(ocd.getChangeField().equals("cpValueAddFee")){
				feeName="预付增值费";
				feeType="收入";
				isFiInterface = true;
				isChangeCpAdd = true;
				//isChangeCpAdd = true;
//				if(isChangeCus){
//					if(ofi.getCpValueAddFee()!=null && !ofi.getCpValueAddFee().equals(0)){
//						ocd.setChangePre(ofi.getCpValueAddFee().toString());
//						ocd.setChangePost(ofi.getCpValueAddFee().toString());
//					}
//				}
//				FiInterfaceProDto fip = this.getFiList(ofi, ocd, oprMan, oprDetailId, "预付增值费",preCusId,sd,cusService,outStockStatus,"收入");
//				list.add(fip);
			}
			if(ocd.getChangeField().equals("cpSonderzugPrice")){
				feeName="预付专车费";
				feeType="收入";
				isFiInterface = true;
				isChangeCpSon = true;
				//isChangeCpSon = true;
//				if(isChangeCus){
//					if(ofi.getCpSonderzugPrice()!=null && !ofi.getCpSonderzugPrice().equals(0)){
//						ocd.setChangePre(ofi.getCpSonderzugPrice().toString());
//						ocd.setChangePost(ofi.getCpSonderzugPrice().toString());
//					}
//				}
//				FiInterfaceProDto fip = this.getFiList(ofi, ocd, oprMan, oprDetailId, "预付专车费",preCusId,sd,cusService,outStockStatus,"收入");
//				list.add(fip);
			}
			if(ocd.getChangeField().equals("paymentCollection")){
				feeName="代收货款";
				feeType="代收货款";
				isFiInterface = true;
				isChangePay = true;
				//isChangePay = true;
//				if(isChangeCus){
//					if(ofi.getPaymentCollection()!=null && !ofi.getPaymentCollection().equals(0)){
//						ocd.setChangePre(ofi.getPaymentCollection().toString());
//						ocd.setChangePost(ofi.getPaymentCollection().toString());
//					}
//				}
//				FiInterfaceProDto fip = this.getFiList(ofi, ocd, oprMan, oprDetailId, "代收货款",preCusId,sd,cusService,outStockStatus,"代收货款");
//				list.add(fip);
			}
			if(ocd.getChangeField().equals("consigneeFee")){
				feeName="到付提送费";
				feeType="收入";
				isFiInterface = true;
				isChangeConFee = true;
				//isc
				//FiInterfaceProDto fip = this.getFiList(ofi, ocd, oprMan, oprDetailId, "到付提送费",preCusId,sd,cusService,outStockStatus,"收入");
//				if(isChangeCus){
//					if(ofi.getConsigneeFee()!=null && !ofi.getConsigneeFee().equals(0)){
//						ocd.setChangePre(ofi.getConsigneeFee().toString());
//						ocd.setChangePost(ofi.getConsigneeFee().toString());
//					}
//				}
//				if(!isOutStore){
//					Double sonFee=Double.valueOf(ocd.getChangePre());
//					Double chSonFee=Double.valueOf(ocd.getChangePost());
//					fip.setAmount(chSonFee-sonFee);
//					fip.setWhoCash("到付");
//					incomeList.add(fip);
//				}else{
					//list.add(fip);
				//}
			}
			if(ocd.getChangeField().equals("cusValueAddFee")){
				feeName="到付增值费";
				feeType="收入";
				isFiInterface = true;
				isChangeConAdd = true;
				//FiInterfaceProDto fip = this.getFiList(ofi, ocd, oprMan, oprDetailId, "到付增值费",preCusId,sd,cusService,outStockStatus,"收入");
//				if(isChangeCus){
//					if(ofi.getCusValueAddFee()!=null && !ofi.getCusValueAddFee().equals(0)){
//						ocd.setChangePre(ofi.getCusValueAddFee().toString());
//						ocd.setChangePost(ofi.getCusValueAddFee().toString());
//					}
//				}
//				if(!isOutStore){
//					Double sonFee=Double.valueOf(ocd.getChangePre());
//					Double chSonFee=Double.valueOf(ocd.getChangePost());
//					fip.setAmount(chSonFee-sonFee);
//					fip.setWhoCash("到付");
//					incomeList.add(fip);
//				}else{
					//list.add(fip);
				//}
			}
			if(ocd.getChangeField().equals("sonderzugPrice")){
				feeName="到付专车费";
				feeType="收入";
				isFiInterface = true;
				isChangeSon = true;
				//FiInterfaceProDto fip = this.getFiList(ofi, ocd, oprMan, oprDetailId, "到付专车费",preCusId,sd,cusService,outStockStatus,"收入");
//				if(isChangeCus){
//					if(ofi.getSonderzugPrice()!=null && !ofi.getSonderzugPrice().equals(0)){
//						ocd.setChangePre(ofi.getSonderzugPrice().toString());
//						ocd.setChangePost(ofi.getSonderzugPrice().toString());
//					}
//				}
//				if(!isOutStore){
//					Double sonFee=Double.valueOf(ocd.getChangePre());
//					Double chSonFee=Double.valueOf(ocd.getChangePost());
//					fip.setAmount(chSonFee-sonFee);
//					fip.setWhoCash("到付");
//					incomeList.add(fip);
//				}else{
					//list.add(fip);
				//}
			}
			if(ocd.getChangeField().equals("receiptType")){
				String recpType=ocd.getChangePost();
				List<OprReceipt> oprList=oprReceiptDao.findBy("dno", ofi.getDno());
				if(oprList.size()>0){
					OprReceipt or=oprList.get(0);
					or.setReceiptType(recpType);
					oprReceiptDao.save(or);
				}
			}
			if(isOutStore){
				//更改中转费
				if(ocd.getChangeField().equals("traFee")){
					Double pricePre=Double.valueOf(ocd.getChangePre());
					Double pricePost=Double.valueOf(ocd.getChangePost());
					FiInterfaceProDto fipd=new FiInterfaceProDto();
					fipd.setDistributionMode(ofi.getDistributionMode());
					if(fipd.getDistributionMode().equals("外发")){
						fipd.setCostType("外发费");
					}else{
						fipd.setCostType("中转费");
					}
					fipd.setGocustomerId(ofi.getGoWhereId());
					fipd.setGocustomerName(ofi.getGowhere());
					fipd.setPreCustomerId(preCusId);
					fipd.setCustomerId(ofi.getCusId());
					fipd.setCustomerName(ofi.getCpName());
					fipd.setDocumentsType("成本");
					fipd.setDocumentsSmalltype("配送单");
					fipd.setDocumentsNo(ofi.getDno());
					fipd.setDno(ofi.getDno());
					fipd.setBeforeAmount(pricePre);
					fipd.setAmount(pricePost);
					fipd.setSourceData("更改申请");
					fipd.setStockStatus(1L);
					fipd.setSourceNo(oprDetailId);
					fipd.setCollectionUser(oprMan);
					fipd.setIncomeDepart(ofi.getInDepart());
					fipd.setIncomeDepartId(ofi.getInDepartId());
					fipd.setDepartId(ofi.getDistributionDepartId());
					list.add(fipd);
				}
				//如果是中转并且已经出库 写入EDI依赖的表
				if(ofi.getDistributionMode().equals("中转")){
					//如果存在记录则 先删除 然后保存一条最新的
					List<OprFaxOut> outList = oprFaxOutService.findBy("dno", ofi.getDno());
					OprStatus os = oprStatusService.findStatusByDno(ofi.getDno());
					if(os == null){
						throw new ServiceException("数据异常，配送单号为:"+ofi.getDno()+"对应的货物状态信息为空了，写入EDI表失败!");
					}
					OprFaxOut ofo= new OprFaxOut();
					for(OprFaxOut fout:outList){
						ofo = fout;
					}
					List<ConsigneeInfo> conList = consigneeInfoService.findBy("consigneeTel", ofi.getConsigneeTel());
					StringBuffer request = new StringBuffer("");
					BeanUtils.copyProperties(ofi, ofo);
					if(conList.size()>0){
						ofo.setConsignId(conList.get(0).getId());
					}
					ofo.setChangeName(oprMan);
					ofo.setChangeTime(new Date());
					Customer cus = customerService.getAndInitEntity(ofi.getGoWhereId());
					if(cus != null){
						ofo.setEdiUserId(cus.getEdiUserId());
					}
					List<OprRequestDo> reqList = oprRequestDoService.getRequestByDno(ofi.getDno());
					for(int i=0;i<reqList.size();i++){
						OprRequestDo ord=reqList.get(i);
						request.append(ord.getRequestStage());
						request.append(":");
						request.append(ord.getRequest());
						if(i != reqList.size()-1){
							request.append(",");
						}
					}
					ofo.setOutTime(os.getOutTime());
					ofo.setRequest(request.toString());
					oprFaxOutService.save(ofo);
				}
			}
			
			if(isFiInterface){
				FiInterfaceProDto fip = this.getFiList(ofi, ocd, oprMan, oprDetailId, feeName,preCusId,sd,cusService,outStockStatus,feeType,false,0.0,0.0);
				list.add(fip);
			}
		}
		//如果更改了代理
		if(isChangeCus){
//			OprChangeDetail ocd=new OprChangeDetail();
//			if(!isChangeCpFee){
//				if(ofi.getCpFee()!=null && !ofi.getCpFee().equals(0)){
//					ocd.setChangePre(ofi.getCpFee().toString());
//					ocd.setChangePost(ofi.getCpFee().toString());
//					FiInterfaceProDto fip = this.getFiList(ofi, ocd, oprMan, oprDetailId, "预付提送费",preCusId,sd,cusService,outStockStatus,"收入");
//					list.add(fip);
//				}
//			}
//			if(!isChangeCpAdd){
//				if(ofi.getCpValueAddFee()!=null && !ofi.getCpValueAddFee().equals(0)){
//					ocd.setChangePre(ofi.getCpValueAddFee().toString());
//					ocd.setChangePost(ofi.getCpValueAddFee().toString());
//					FiInterfaceProDto fip = this.getFiList(ofi, ocd, oprMan, oprDetailId, "预付增值费",preCusId,sd,cusService,outStockStatus,"收入");
//					list.add(fip);
//				}
//			}
//			if(!isChangeCpSon){
//				if(ofi.getCpSonderzugPrice()!=null && !ofi.getCpSonderzugPrice().equals(0)){
//					ocd.setChangePre(ofi.getCpSonderzugPrice().toString());
//					ocd.setChangePost(ofi.getCpSonderzugPrice().toString());
//					FiInterfaceProDto fip = this.getFiList(ofi, ocd, oprMan, oprDetailId, "预付专车费",preCusId,sd,cusService,outStockStatus,"收入");
//					list.add(fip);
//				}
//			}
//			if(!isChangePay){
//				if(ofi.getPaymentCollection()!=null && !ofi.getPaymentCollection().equals(0)){
//					ocd.setChangePre(ofi.getPaymentCollection().toString());
//					ocd.setChangePost(ofi.getPaymentCollection().toString());
//					FiInterfaceProDto fip = this.getFiList(ofi, ocd, oprMan, oprDetailId, "代收货款",preCusId,sd,cusService,outStockStatus,"代收货款");
//					list.add(fip);
//				}
//			}
			//如果没有改费用
			if(!isChangeConAdd && !isChangeConFee && !isChangeCpAdd && !isChangeCpFee && !isChangeCpSon  && !isChangePay && !isChangeSon){
				if(ofi.getCpFee() != null && ofi.getCpFee() != 0.0){
					FiInterfaceProDto fiCon = this.getFiList(ofi, null, oprMan, oprDetailId, "预付提送费", preCusId, sd, cusService, outStockStatus, "收入",true,ofi.getCpFee(),ofi.getCpFee());
					list.add(fiCon);
				}
				if(ofi.getConsigneeFee() != null && ofi.getConsigneeFee() != 0.0 ){
					FiInterfaceProDto fiCon = this.getFiList(ofi, null, oprMan, oprDetailId, "到付提送费", preCusId, sd, cusService, outStockStatus, "收入",true,ofi.getConsigneeFee(),ofi.getConsigneeFee());
					list.add(fiCon);
				}
				if(ofi.getCpValueAddFee() != null && ofi.getCpValueAddFee() != 0.0 ){
					FiInterfaceProDto fiCon = this.getFiList(ofi, null, oprMan, oprDetailId, "预付增值费", preCusId, sd, cusService, outStockStatus, "收入",true,ofi.getCpValueAddFee(),ofi.getCpValueAddFee());
					list.add(fiCon);
				}
				if(ofi.getCusValueAddFee() != null && ofi.getCusValueAddFee() != 0.0 ){
					FiInterfaceProDto fiCon = this.getFiList(ofi, null, oprMan, oprDetailId, "到付增值费", preCusId, sd, cusService, outStockStatus, "收入",true,ofi.getCusValueAddFee(),ofi.getCusValueAddFee());
					list.add(fiCon);
				}
				if(ofi.getCpSonderzugPrice() != null && ofi.getCpSonderzugPrice() != 0.0 ){
					FiInterfaceProDto fiCon = this.getFiList(ofi, null, oprMan, oprDetailId, "预付专车费", preCusId, sd, cusService, outStockStatus, "收入",true,ofi.getCpSonderzugPrice(),ofi.getCpSonderzugPrice());
					list.add(fiCon);
				}
				if(ofi.getSonderzugPrice() != null && ofi.getSonderzugPrice() != 0.0 ){
					FiInterfaceProDto fiCon = this.getFiList(ofi, null, oprMan, oprDetailId, "到付专车费", preCusId, sd, cusService, outStockStatus, "收入",true,ofi.getSonderzugPrice(),ofi.getSonderzugPrice());
					list.add(fiCon);
				}
				if(ofi.getPaymentCollection() != null && ofi.getPaymentCollection() != 0.0 ){
					FiInterfaceProDto fiCon = this.getFiList(ofi, null, oprMan, oprDetailId, "代收货款", preCusId, sd, cusService, outStockStatus, "代收货款",true,ofi.getPaymentCollection(),ofi.getPaymentCollection());
					list.add(fiCon);
				}
				//调整客服员
				oprFaxInService.customerServiceAdjust(ofi.getDno().toString());
			}
		}
		//收入接口
//		if(incomeList.size()>0){
//			fiInterface.currentToFiIncome(incomeList);
//		}
		//更改申请财务总接口
		if(list.size()>0){
			fiInterface.changeToFi(list);
		}
		return returnMsg;
	}
	/**
	 * 如果已经出库
	 * @param changeDetailList
	 * @param ofi
	 * @param oprDetailId
	 * @return
	 * @throws Exception
	 */
	/*
	private String outStock(List<OprChangeDetail> changeDetailList,OprFaxIn ofi,Long oprDetailId,String oprMan)throws Exception{
		String returnMsg="true";
		User user=WebRalasafe.getCurrentUser(Struts2Utils.getRequest());
		List<FiInterfaceProDto> list=new ArrayList<FiInterfaceProDto>();
		//收入接口所需List
		List<FiInterfaceProDto> incomeList=new ArrayList<FiInterfaceProDto>();
		
		BasCusService cusService=basCusService.getCusServiceByCusId(ofi.getCusId(), Long.valueOf(user.get("bussDepart").toString()));
		if(cusService == null){
			throw new ServiceException("数据异常，代理ID为:"+ofi.getCusId()+"的客商对应的客服员为空！");
		}
		SysDepart sd=departService.getDepartByDepartNo(cusService.getServiceDepartCode());
		if(sd == null){
			throw new ServiceException("部门编码:"+cusService.getServiceDepartCode()+"对应的部门信息为空了，请联系系统管理员！");
		}
		
		for(OprChangeDetail ocd:changeDetailList){
			if(ocd.getChangeField().equals("whoCash")&&ocd.getChangePost().equals("预付")){
				decideDebt(ofi,changeDetailList);
			}
			//更改预付提送费
			if(ocd.getChangeField().equals("cpFee")){
				Double pricePre=Double.valueOf(ocd.getChangePre());
				Double pricePost=Double.valueOf(ocd.getChangePost());
				FiInterfaceProDto fipd=new FiInterfaceProDto();
				fipd.setDno(ofi.getDno());
				fipd.setCollectionUser(oprMan);
				fipd.setSourceData("更改申请");
				fipd.setSourceNo(oprDetailId);
				fipd.setCustomerId(ofi.getCusId());
				fipd.setCustomerName(ofi.getCpName());
				fipd.setCostType("预付提送费");
				fipd.setStockStatus(1L);
				fipd.setBeforeAmount(pricePre);
				fipd.setAmount(pricePost);
				fipd.setIncomeDepart(ofi.getInDepart());
				fipd.setIncomeDepartId(ofi.getInDepartId());
				fipd.setDepartId(ofi.getDistributionDepartId());
				list.add(fipd);
				
				//收入
				FiInterfaceProDto incomeFpd=new FiInterfaceProDto();
				incomeFpd.setDno(ofi.getDno());
				incomeFpd.setCustomerId(ofi.getCusId());
				incomeFpd.setCustomerName(ofi.getCpName());
				incomeFpd.setAmount(pricePost-pricePre);
				incomeFpd.setSourceData("更改申请");
				incomeFpd.setWhoCash(ofi.getWhoCash());
				incomeFpd.setIncomeDepartId(ofi.getInDepartId());
				incomeFpd.setIncomeDepart(ofi.getInDepart());
				incomeFpd.setCostType("预付提送费");
				incomeFpd.setSourceNo(oprDetailId);
				incomeFpd.setAdmDepart(cusService.getServiceDepart());
				incomeFpd.setAdmDepartId(sd.getDepartId());
				incomeList.add(incomeFpd);
			}
			//更改预付专车费
			if(ocd.getChangeField().equals("cpSonderzugPrice")){
				Double pricePre=Double.valueOf(ocd.getChangePre());
				Double pricePost=Double.valueOf(ocd.getChangePost());
				FiInterfaceProDto fipd=new FiInterfaceProDto();
				fipd.setDno(ofi.getDno());
				fipd.setCollectionUser(user.get("name").toString());
				fipd.setSourceData("更改申请");
				fipd.setSourceNo(oprDetailId);
				fipd.setCustomerId(ofi.getCusId());
				fipd.setCustomerName(ofi.getCpName());
				fipd.setCostType("预付专车费");
				fipd.setStockStatus(1L);
				fipd.setBeforeAmount(pricePre);
				fipd.setIncomeDepart(ofi.getInDepart());
				fipd.setIncomeDepartId(ofi.getInDepartId());
				fipd.setDepartId(ofi.getDistributionDepartId());
				fipd.setAmount(pricePost);
				list.add(fipd);
				
				//收入
				FiInterfaceProDto incomeFpd=new FiInterfaceProDto();
				incomeFpd.setDno(ofi.getDno());
				incomeFpd.setCustomerId(ofi.getCusId());
				incomeFpd.setCustomerName(ofi.getCpName());
				incomeFpd.setAmount(pricePost-pricePre);
				incomeFpd.setWhoCash(ofi.getWhoCash());
				incomeFpd.setSourceData("更改申请");
				incomeFpd.setCostType("预付专车费");
				incomeFpd.setSourceNo(oprDetailId);
				incomeFpd.setAdmDepart(cusService.getServiceDepart());
				incomeFpd.setAdmDepartId(sd.getDepartId());
				incomeFpd.setIncomeDepartId(ofi.getInDepartId());
				incomeFpd.setIncomeDepart(ofi.getInDepart());
				incomeList.add(incomeFpd);
			}
			//更改到付专车费 
			if(ocd.getChangeField().equals("sonderzugPrice")){
				Double pricePre=Double.valueOf(ocd.getChangePre());
				Double pricePost=Double.valueOf(ocd.getChangePost());
				FiInterfaceProDto fipd=new FiInterfaceProDto();
				fipd.setDno(ofi.getDno());
				fipd.setCollectionUser(oprMan);
				fipd.setSourceData("更改申请");
				fipd.setSourceNo(oprDetailId);
				fipd.setCustomerId(ofi.getGoWhereId());
				fipd.setCustomerName(ofi.getGowhere());
				fipd.setContacts(ofi.getConsignee());
				fipd.setCostType("到付专车费");
				fipd.setStockStatus(1L);
				fipd.setBeforeAmount(pricePre);
				fipd.setAmount(pricePost);
				fipd.setIncomeDepart(ofi.getInDepart());
				fipd.setIncomeDepartId(ofi.getInDepartId());
				fipd.setDepartId(ofi.getDistributionDepartId());
				list.add(fipd);
				
				//收入
				FiInterfaceProDto incomeFpd=new FiInterfaceProDto();
				incomeFpd.setDno(ofi.getDno());
				incomeFpd.setCustomerId(ofi.getCusId());
				incomeFpd.setCustomerName(ofi.getCpName());
				incomeFpd.setAmount(pricePost-pricePre);
				incomeFpd.setWhoCash(ofi.getWhoCash());
				incomeFpd.setSourceData("更改申请");
				incomeFpd.setCostType("到付专车费");
				incomeFpd.setSourceNo(oprDetailId);
				incomeFpd.setIncomeDepartId(ofi.getInDepartId());
				incomeFpd.setIncomeDepart(ofi.getInDepart());
				incomeFpd.setAdmDepart(cusService.getServiceDepart());
				incomeFpd.setAdmDepartId(sd.getDepartId());
				incomeList.add(incomeFpd);
			}
			if(ocd.getChangeField().equals("cpValueAddFee")){
				Double cpAddFee=Double.valueOf(ocd.getChangePre());
				Double chcpAddFee=Double.valueOf(ocd.getChangePost());

				FiInterfaceProDto fip=new FiInterfaceProDto();
				fip.setDno(ofi.getDno());
				fip.setCollectionUser(oprMan);
				fip.setSourceData("更改申请");
				fip.setCustomerId(ofi.getCusId());
				fip.setCustomerName(ofi.getCpName());
				fip.setBeforeAmount(cpAddFee);
				fip.setAmount(chcpAddFee);
				fip.setCostType("预付增值费");
				fip.setContacts(ofi.getConsignee());
				fip.setGocustomerId(ofi.getGoWhereId());
				fip.setGocustomerName(ofi.getGowhere());
				fip.setStockStatus(1L);
				fip.setSourceNo(oprDetailId);
				fip.setIncomeDepart(ofi.getInDepart());
				fip.setIncomeDepartId(ofi.getInDepartId());
				fip.setDepartId(ofi.getDistributionDepartId());
				list.add(fip);
				
				//收入
				FiInterfaceProDto incomeFpd=new FiInterfaceProDto();
				incomeFpd.setDno(ofi.getDno());
				incomeFpd.setCustomerId(ofi.getCusId());
				incomeFpd.setCustomerName(ofi.getCpName());
				incomeFpd.setAmount(chcpAddFee-cpAddFee);
				incomeFpd.setSourceData("更改申请");
				incomeFpd.setWhoCash(ofi.getWhoCash());
				incomeFpd.setCostType("预付增值费");
				incomeFpd.setSourceNo(oprDetailId);
				incomeFpd.setIncomeDepartId(ofi.getInDepartId());
				incomeFpd.setIncomeDepart(ofi.getInDepart());
				incomeFpd.setAdmDepart(cusService.getServiceDepart());
				incomeFpd.setAdmDepartId(sd.getDepartId());
				incomeList.add(incomeFpd);
			}
			//更改到付提送费
			if(ocd.getChangeField().equals("consigneeFee")){
				Double pricePre=Double.valueOf(ocd.getChangePre());
				Double pricePost=Double.valueOf(ocd.getChangePost());
				FiInterfaceProDto fipd=new FiInterfaceProDto();
				fipd.setDno(ofi.getDno());
				fipd.setCollectionUser(oprMan);
				fipd.setSourceData("更改申请");
				fipd.setSourceNo(oprDetailId);
				fipd.setCustomerId(ofi.getGoWhereId());
				fipd.setCustomerName(ofi.getGowhere());
				fipd.setContacts(ofi.getConsignee());
				fipd.setCostType("到付提送费");
				fipd.setStockStatus(1L);
				fipd.setBeforeAmount(pricePre);
				fipd.setAmount(pricePost);
				fipd.setIncomeDepart(ofi.getInDepart());
				fipd.setIncomeDepartId(ofi.getInDepartId());
				fipd.setDepartId(ofi.getDistributionDepartId());
				list.add(fipd);
				
				//收入
				FiInterfaceProDto incomeFpd=new FiInterfaceProDto();
				incomeFpd.setDno(ofi.getDno());
				incomeFpd.setCustomerId(ofi.getCusId());
				incomeFpd.setCustomerName(ofi.getCpName());
				incomeFpd.setAmount(pricePost-pricePre);
				incomeFpd.setWhoCash(ofi.getWhoCash());
				incomeFpd.setSourceData("更改申请");
				incomeFpd.setCostType("到付提送费");
				incomeFpd.setSourceNo(oprDetailId);
				incomeFpd.setIncomeDepartId(ofi.getInDepartId());
				incomeFpd.setIncomeDepart(ofi.getInDepart());
				incomeFpd.setAdmDepart(cusService.getServiceDepart());
				incomeFpd.setAdmDepartId(sd.getDepartId());
				incomeList.add(incomeFpd);
			}
			//更改到付增值费
			if(ocd.getChangeField().equals("cusValueAddFee")){
				Double pricePre=Double.valueOf(ocd.getChangePre());
				Double pricePost=Double.valueOf(ocd.getChangePost());
				FiInterfaceProDto fipd=new FiInterfaceProDto();
				fipd.setDno(ofi.getDno());
				fipd.setCollectionUser(oprMan);
				fipd.setSourceData("更改申请");
				fipd.setSourceNo(oprDetailId);
				fipd.setCustomerId(ofi.getGoWhereId());
				fipd.setCustomerName(ofi.getGowhere());
				fipd.setContacts(ofi.getConsignee());
				fipd.setCostType("到付增值费");
				fipd.setStockStatus(1L);
				fipd.setBeforeAmount(pricePre);
				fipd.setAmount(pricePost);
				fipd.setIncomeDepart(ofi.getInDepart());
				fipd.setIncomeDepartId(ofi.getInDepartId());
				fipd.setDepartId(ofi.getDistributionDepartId());
				list.add(fipd);
				
				//收入
				FiInterfaceProDto incomeFpd=new FiInterfaceProDto();
				incomeFpd.setDno(ofi.getDno());
				incomeFpd.setCustomerId(ofi.getCusId());
				incomeFpd.setCustomerName(ofi.getCpName());
				incomeFpd.setAmount(pricePost-pricePre);
				incomeFpd.setSourceData("更改申请");
				incomeFpd.setWhoCash(ofi.getWhoCash());
				incomeFpd.setCostType("到付增值费");
				incomeFpd.setSourceNo(oprDetailId);
				incomeFpd.setIncomeDepartId(ofi.getInDepartId());
				incomeFpd.setIncomeDepart(ofi.getInDepart());
				incomeFpd.setAdmDepart(cusService.getServiceDepart());
				incomeFpd.setAdmDepartId(sd.getDepartId());
				incomeList.add(incomeFpd);
			}
			//更改中转费
			if(ocd.getChangeField().equals("traFee")){
				Double pricePre=Double.valueOf(ocd.getChangePre());
				Double pricePost=Double.valueOf(ocd.getChangePost());
				FiInterfaceProDto fipd=new FiInterfaceProDto();
				fipd.setDistributionMode(ofi.getDistributionMode());
				if(fipd.getDistributionMode().equals("外发")){
					fipd.setCostType("外发费");
				}else{
					fipd.setCostType("中转费");
				}
				fipd.setCustomerId(ofi.getGoWhereId());
				fipd.setCustomerName(ofi.getGowhere());
				fipd.setDocumentsType("成本");
				fipd.setDocumentsSmalltype("配送单");
				fipd.setDocumentsNo(ofi.getDno());
				fipd.setDno(ofi.getDno());
				fipd.setBeforeAmount(pricePre);
				fipd.setAmount(pricePost);
				fipd.setSourceData("更改申请");
				fipd.setStockStatus(1L);
				fipd.setSourceNo(oprDetailId);
				fipd.setCollectionUser(oprMan);
				fipd.setIncomeDepart(ofi.getInDepart());
				fipd.setIncomeDepartId(ofi.getInDepartId());
				fipd.setDepartId(ofi.getDistributionDepartId());
				list.add(fipd);
			}
			//更改代收货款
			if(ocd.getChangeField().equals("paymentCollection")){
				Double payFee=Double.valueOf(ocd.getChangePre());
				Double chPayFee=Double.valueOf(ocd.getChangePost());
				FiInterfaceProDto fip=new FiInterfaceProDto();
				fip.setDno(ofi.getDno());
				fip.setCollectionUser(oprMan);
				fip.setSourceData("更改申请");
				fip.setCustomerId(ofi.getCusId());
				fip.setCustomerName(ofi.getCpName());
				fip.setBeforeAmount(payFee);
				fip.setAmount(chPayFee);
				fip.setCostType("代收货款");
				fip.setContacts(ofi.getConsignee());
				fip.setGocustomerId(ofi.getGoWhereId());
				fip.setGocustomerName(ofi.getGowhere());
				fip.setStockStatus(1L);
				fip.setSourceNo(oprDetailId);
				fip.setIncomeDepart(ofi.getInDepart());
				fip.setIncomeDepartId(ofi.getInDepartId());
				fip.setDepartId(ofi.getDistributionDepartId());
				list.add(fip);
			}
			
		}
		//如果是中转并且已经出库 写入EDI依赖的表
		if(ofi.getDistributionMode().equals("中转")){
			//如果存在记录则 先删除 然后保存一条最新的
			List<OprFaxOut> outList = oprFaxOutService.findBy("dno", ofi.getDno());
			OprStatus os = oprStatusService.findStatusByDno(ofi.getDno());
			if(os == null){
				throw new ServiceException("数据异常，配送单号为:"+ofi.getDno()+"对应的货物状态信息为空了，写入EDI表失败!");
			}
			OprFaxOut ofo= new OprFaxOut();
			for(OprFaxOut fout:outList){
				ofo = fout;
			}
			List<ConsigneeInfo> conList = consigneeInfoService.findBy("consigneeTel", ofi.getConsigneeTel());
			StringBuffer request = new StringBuffer("");
			BeanUtils.copyProperties(ofi, ofo);
			if(conList.size()>0){
				ofo.setConsignId(conList.get(0).getId());
			}
			ofo.setChangeName(oprMan);
			ofo.setChangeTime(new Date());
			Customer cus = customerService.getAndInitEntity(ofi.getGoWhereId());
			if(cus != null){
				ofo.setEdiUserId(cus.getEdiUserId());
			}
			List<OprRequestDo> reqList = oprRequestDoService.getRequestByDno(ofi.getDno());
			for(int i=0;i<reqList.size();i++){
				OprRequestDo ord=reqList.get(i);
				request.append(ord.getRequestStage());
				request.append(":");
				request.append(ord.getRequest());
				if(i != reqList.size()-1){
					request.append(",");
				}
			}
			ofo.setOutTime(os.getOutTime());
			ofo.setRequest(request.toString());
			oprFaxOutService.save(ofo);
		}
		
		//收入接口
		fiInterface.currentToFiIncome(incomeList);
		//外发成本接口
		//fiInterface.changeToOutCost();
		fiInterface.changeToFi(list);
		return returnMsg;
	}
	*/
	/**
	 * 判断是否能转欠款
	 * @param ofi
	 * @param ocd
	 * @return
	 * @throws Exception
	 */
	public String decideDebt(OprFaxIn ofi,List<OprChangeDetail> ocdList) throws Exception{
		String returnMsg="true";
		Connection con=null;
		Double sumPrice=0.0;
		try {
			for(OprChangeDetail ocd:ocdList){
				if(ocd.getChangeField().equals("cpSonderzugPrice")){
					Double pricePre=Double.valueOf(ocd.getChangePre());
					Double pricePost=Double.valueOf(ocd.getChangePost());
					sumPrice+=pricePost-pricePre;
				}
				if(ocd.getChangeField().equals("cpFee")){
					Double pricePre=Double.valueOf(ocd.getChangePre());
					Double pricePost=Double.valueOf(ocd.getChangePost());
					sumPrice+=pricePost-pricePre;
				}
				if(ocd.getChangeField().equals("traFee")){
					Double pricePre=Double.valueOf(ocd.getChangePre());
					Double pricePost=Double.valueOf(ocd.getChangePost());
					sumPrice+=pricePost-pricePre;
				}
				if(ocd.getChangeField().equals("cpValueAddFee")){
					Double pricePre=Double.valueOf(ocd.getChangePre());
					Double pricePost=Double.valueOf(ocd.getChangePost());
					sumPrice+=pricePost-pricePre;
				}
				/*
				if(ocd.getChangeField().equals("whoCash")||ocd.getChangePost().equals("预付")){
					Connection con=SessionFactoryUtils.getDataSource(oprFaxInDao.getSessionFactory()).getConnection();
					CallableStatement cs=con.prepareCall("{call pro_debt(?,?,?)}");
					cs.setLong(1, ofi.getCusId());
					if(ofi.getSonderzugPrice()!=null){
						cs.setDouble(2, ofi.getSonderzugPrice()+pricePost-pricePre);
					}else{
						cs.setDouble(2, pricePost-pricePre);
					}
					cs.registerOutParameter(3, Types.VARCHAR);
					returnMsg=cs.getString(3);
				}*/
			}
			con=SessionFactoryUtils.getDataSource(oprFaxInDao.getSessionFactory()).getConnection();
			CallableStatement cs=con.prepareCall("{call pro_debt(?,?,?,?)}");
			cs.setLong(1, ofi.getCusId());
			cs.setDouble(2, sumPrice);
			cs.setLong(3, ofi.getCurDepartId());
			cs.registerOutParameter(4, Types.VARCHAR);
			cs.executeUpdate();
			returnMsg=cs.getString(4);
			if(returnMsg.equals("true")){
				return returnMsg;
			}else{
				throw new ServiceException(returnMsg);
			}
		} catch (Exception e) {
			throw new ServiceException(returnMsg);
		}finally{
			if(con != null){
				con.close();
			}
		}
		
	}
	/**
	 * 修改传真主表
	 * @param changeDetailList
	 * @param ofi
	 * @param user
	 * @throws Exception
	 */
	private void updateMainMsg(List<OprChangeDetail> changeDetailList,OprFaxIn ofi,String oprMan)throws Exception{
		OprFaxMain faxMain=oprFaxMainDao.get(ofi.getFaxMainId());
		if(faxMain==null){
			throw new ServiceException("数据异常，该主单号在主单表中不存在！");
		}
		for(OprChangeDetail ocd:changeDetailList){
			//修改计费重量
			if(ocd.getChangeField().equals("cusWeight")){
				Double cusPre=Double.valueOf(ocd.getChangePre());
				Double cusPost=Double.valueOf(ocd.getChangePost());
				faxMain.setTotalWeight(faxMain.getTotalWeight()+(cusPost-cusPre));
				faxMain.setUpdateName(oprMan);
				faxMain.setUpdateTime(new Date());
			}
			//修改代理重量
			if(ocd.getChangeField().equals("cqWeight")){
				Double cqPre=Double.valueOf(ocd.getChangePre());
				Double cqPost=Double.valueOf(ocd.getChangePost());
				faxMain.setRealWeight(faxMain.getRealWeight()+(cqPost-cqPre));
				faxMain.setUpdateName(oprMan);
				faxMain.setUpdateTime(new Date());
			}
			//修改件数
			if(ocd.getChangeField().equals("piece")){
				int piecePre=Integer.valueOf(ocd.getChangePre());
				int piecePost=Integer.valueOf(ocd.getChangePost());
				faxMain.setTotalPiece(faxMain.getTotalPiece()+(piecePost-piecePre));
				faxMain.setUpdateName(oprMan);
				faxMain.setUpdateTime(new Date());
			}
		}
		oprFaxMainDao.save(faxMain);
	}
	/**
	 * 修改增值服务费明细
	 * @param addFeeList
	 * @param ofi
	 * @param user
	 * @throws Exception
	 */
	private void updateAddFeeDetial(List<OprValueAddFee> addFeeList,OprFaxIn ofi,String oprMan)throws Exception{
		List<OprValueAddFee> oldList=oprValueAddFeeService.findBy("dno", ofi.getDno());
		if(addFeeList!=null&&addFeeList.size()>0){
			//新增一条负记录
			List<OprValueAddFee> delFee=oprValueAddFeeService.findChangeAddFee(addFeeList,ofi.getDno());
			OprValueAddFee o=null;
			for(OprValueAddFee ovaf:delFee){
				o=new OprValueAddFee();
				o.setFeeCount(-(ovaf.getFeeCount()));
				o.setStatus(1L);
				o.setDno(ofi.getDno());
				o.setPayType(ovaf.getPayType());
				o.setFeeName(ovaf.getFeeName());
				o.setCreateName(oprMan);
				o.setCreateTime(new Date());
				oprValueAddFeeService.save(o);
				
				ovaf.setStatus(1L);
				oprValueAddFeeService.save(ovaf);
			}
			
			for (int i = 0; i < addFeeList.size(); i++) {
				boolean flag=true;
				OprValueAddFee ova=addFeeList.get(i);
				for(int j=0;j<oldList.size();j++){
					OprValueAddFee oldFee=oldList.get(j);
					if(ova.getId().equals(oldFee.getId())&&ova.getFeeName().equals(oldFee.getFeeName())){
						flag=false;
					}
				}
				if(flag){
					OprValueAddFee addFee=new OprValueAddFee();
					addFee.setDno(ofi.getDno());
					addFee.setFeeName(ova.getFeeName());
					addFee.setPayType(ova.getPayType());
					addFee.setCreateName(oprMan);
					addFee.setCreateTime(new Date());
					addFee.setFeeCount(ova.getFeeCount());
					addFee.setStatus(1L);
					oprValueAddFeeService.save(addFee);
				}else{
					//只改变了付款方
					if(!addFeeList.get(i).getPayType().equals(oldList.get(i).getPayType())){
						for(int j=0;j<oldList.size();j++){
							OprValueAddFee oldFee=oldList.get(j);
							oldFee.setPayType(ova.getPayType());
							oprValueAddFeeService.save(oldFee);
						}
					}
				}
			}
		}else{
			List<OprValueAddFee> addList=new ArrayList<OprValueAddFee>();
			OprValueAddFee ov=new OprValueAddFee();
			ov.setId(0L);
			addList.add(ov);
			List<OprValueAddFee> delFee=oprValueAddFeeService.findChangeAddFee(addList,ofi.getDno());
			OprValueAddFee o=null;
			for(OprValueAddFee ovaf:delFee){
				o=new OprValueAddFee();
				o.setFeeCount(-(ovaf.getFeeCount()));
				o.setStatus(1L);
				o.setDno(ofi.getDno());
				o.setFeeName(ovaf.getFeeName());
				o.setCreateName(oprMan);
				o.setCreateTime(new Date());
				oprValueAddFeeService.save(o);
				
				ovaf.setStatus(1L);
				oprValueAddFeeService.save(ovaf);
			}
		}
	}
	//获得财务接口所需VO 
	private FiInterfaceProDto getFiList(OprFaxIn ofi,OprChangeDetail ocd,String oprMan,Long oprDetailId,String costType,Long preCusId,SysDepart sd,BasCusService cusService,Long outStock,String docType,boolean isChCusNoFee,Double prePrice,Double postPrice){
		Double pricePre=0.0;
		Double pricePost=0.0;
		
		if(isChCusNoFee){
			pricePre = prePrice;
			pricePost = postPrice;
		}else{
			pricePre = Double.valueOf(ocd.getChangePre());
			pricePost = Double.valueOf(ocd.getChangePost());
		}
		
		FiInterfaceProDto fipd=new FiInterfaceProDto();
		fipd.setDno(ofi.getDno());
		fipd.setCollectionUser(oprMan);
		fipd.setWhoCash(ofi.getWhoCash());
		fipd.setAdmDepart(cusService.getServiceDepart());
		fipd.setAdmDepartId(sd.getDepartId());
		fipd.setSourceData("更改申请");
		fipd.setSourceNo(oprDetailId);
		fipd.setDocumentsType(docType);
		fipd.setPreCustomerId(preCusId);
		fipd.setCustomerId(ofi.getCusId());
		fipd.setCustomerName(ofi.getCpName());
		fipd.setCostType(costType);
		fipd.setStockStatus(outStock);
		fipd.setBeforeAmount(pricePre);
		fipd.setAmount(pricePost);
		fipd.setIncomeDepart(ofi.getInDepart());
		fipd.setIncomeDepartId(ofi.getInDepartId());
		fipd.setGocustomerId(ofi.getGoWhereId());
		fipd.setGocustomerName(ofi.getGowhere());
		fipd.setDepartId(ofi.getDistributionDepartId());
		fipd.setDisDepartId(ofi.getDistributionDepartId());
		return fipd;
	}
}