package com.xbwl.oper.stock.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.log.anno.ModuleName;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.orm.PropertyFilter;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.common.utils.DoubleUtil;
import com.xbwl.common.utils.LogType;
import com.xbwl.entity.OprFaxIn;
import com.xbwl.entity.OprLoadingbrigadTime;
import com.xbwl.entity.OprLoadingbrigadeWeight;
import com.xbwl.entity.OprRequestDo;
import com.xbwl.entity.OprSign;
import com.xbwl.entity.OprStatus;
import com.xbwl.entity.OprStock;
import com.xbwl.entity.OprValueAddFee;
import com.xbwl.entity.SysDepart;
import com.xbwl.finance.dto.IFiInterface;
import com.xbwl.finance.dto.impl.FiInterfaceProDto;
import com.xbwl.oper.fax.dao.IOprFaxInDao;
import com.xbwl.oper.stock.dao.IOprLoadingbrigadeTimeDao;
import com.xbwl.oper.stock.dao.IOprRequestDoDao;
import com.xbwl.oper.stock.dao.IOprSignDao;
import com.xbwl.oper.stock.dao.IOprStatusDao;
import com.xbwl.oper.stock.dao.IOprStockDao;
import com.xbwl.oper.stock.dao.IOprValueAddFeeDao;
import com.xbwl.oper.stock.service.IOprHistoryService;
import com.xbwl.oper.stock.service.IOprLoadingbrigadeWeightService;
import com.xbwl.oper.stock.service.IOprSignService;
import com.xbwl.oper.stock.service.IOprStatusService;
import com.xbwl.oper.stock.vo.LoadingbrigadeTypeEnum;
import com.xbwl.rbac.Service.IDepartService;

 

/**
 *功能描述：
 * @author shuw
 *@2011-7-18
 */

@Service("oprSignServiceImpl")
@Transactional(rollbackFor={Exception.class})
public class OprSignServiceImpl  extends BaseServiceImpl<OprSign, Long>
			implements IOprSignService{
	private static final String SIGN_REQUEST_STAGE="签收";
	@Resource(name="oprValueAddFeeHibernateDaoImpl")
	private IOprValueAddFeeDao oprValueAddFeeDao;
	
	@Resource(name="oprSignHibernateDaoImpl")
	private IOprSignDao oprSignDao;
	
	@Resource(name="oprStatusHibernateDaoImpl")
	private IOprStatusDao oprStatusDao;

	@Value("${oprSignServiceImpl.log_selfOutStock}")
	private Long log_selfOutStock ;
	
	@Value("${oprSignAction.log_inputSignReturn}")
	private Long log_inputSignReturn ;
	
	@Resource(name = "oprLoadingbrigadeTimeHibernateDaoImpl")
	private IOprLoadingbrigadeTimeDao oprLoadingbrigadeTimeDao;
	
	@Resource(name = "oprLoadingbrigadeWeightServiceImpl")
	private IOprLoadingbrigadeWeightService oprLoadingbrigadeWeightService;
	
	@Resource(name = "oprFaxInHibernateDaoImpl")
	private IOprFaxInDao oprFaxInDao;
	
	@Resource(name = "oprHistoryServiceImpl")
	private IOprHistoryService oprHistoryService;
	
	@Value("${oprSignAction.log_inputSign}")
	private Long log_inputSign;//签收录入
	
	@Value("${oprSignAction.log_signException}")
	private Long log_signException;//异常签收
	
	@Resource(name="oprStatusServiceImpl")
	private IOprStatusService oprStatusService;
	
	@Resource(name = "oprStockDaoImpl")
	private IOprStockDao oprStockDao;
	
	@Resource(name = "fiInterfaceImpl")
	private IFiInterface iFiInterface;
	@Resource(name = "oprRequestDoHibernateDaoImpl")
	private IOprRequestDoDao oprRequestDoDao;
	
	@Override
	public IBaseDAO<OprSign, Long> getBaseDao() {
		return oprSignDao;
	}
	
	@Resource(name="departServiceImpl")
	private IDepartService departService;
	
	@Value("${oprSignServiceImpl.takeMode}")
	private String takeMode;
	
	@Override
	public void save(OprSign entity) {
		try{
			User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
				OprStatus os=oprStatusService.findStatusByDno(entity.getDno());
				if(os==null){
					throw new ServiceException("数据异常，状态表不存在该配送单的货物！");
				}
				if(os.getOutStatus()==0l){
					throw new ServiceException("这票货还没有出库，不允许签收！");
				}else if(os.getOutStatus()==3l){
					throw new ServiceException("这票货还在预配阶段，不允许签收！");
				}
				List<OprSign> signList= this.oprSignDao.findBy("dno",entity.getDno());
				if(signList.size()>0){
					throw new ServiceException("配送单号为"+entity.getDno()+"的货物已经签收！");
				}
				String signMan = entity.getSignMan();
				if(null==entity.getSignMan() || "".equals(entity.getSignMan().trim())){
					signMan = entity.getReplaceSign();//代签人
				}
				os.setSignStatus(1L);
				os.setSignTime(new Date());
				os.setSignName(signMan);
				oprStatusService.save(os);
				
				if(entity.getIsSignException()==1l){
					//异常签收要做异常记录
					oprHistoryService.saveLog(entity.getDno(), signMan+"异常签收了配送单号为"+entity.getDno()+"的货物，备注:"+entity.getRemark(), this.log_signException);
				}
				oprHistoryService.saveLog(entity.getDno(), signMan+"签收了配送单号为"+entity.getDno()+"的货物", log_inputSign);
				Date dt = new Date();
				if(null==entity.getId()){
					entity.setCreateName(user.get("name")+"");
					entity.setCreateTime(dt);
				}
				entity.setUpdateName(user.get("name")+"");
				entity.setUpdateTime(dt);
				entity.setTs(dt.getTime()+"");
				entity.setDepartId(Long.valueOf(user.get("bussDepart")+""));
				super.save(entity);
				
			}catch(Exception e){
				throw new ServiceException(e.getLocalizedMessage());
			}
		
	}



	/**
	 * 自提出库数据保存，保存到三张表中
	 */
	@ModuleName(value="自提出库签收",logType=LogType.buss)
	public void saveSignStatusByFaxIn(OprSign oprSign,Double storeFee,
			Double  consigneeRate,Double  consigneeFee,Double totalCusValueAddFee)  throws Exception{
			User user= WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
			String userName=user.get("name")+"";
			oprSign.setIsSignException(0l);
			oprSign.setCreateName(userName);
			oprSign.setCreateTime(new Date());
			oprSign.setUpdateName(userName);
			oprSign.setUpdateTime(new Date());
			oprSignDao.save(oprSign);
			
			List<OprStatus> listsu =oprStatusDao.findBy("dno",oprSign.getDno());
		  	OprStatus oprStatus=null;
			if(listsu.size()==0){
				throw new ServiceException("找不到这票货的状态记录");
			}else {
				oprStatus=listsu.get(0);
			}
			
			if(oprStatus.getSignStatus()!=null&&oprStatus.getSignStatus()==1l){
				throw new ServiceException("货物已签收，不能重复签收");
			}
			
			oprStatus.setSignStatus(1l);
			oprStatus.setOutTime(new Date());
			oprStatus.setOutStatus(1l);
			oprStatus.setSignTime(new Date());
			oprStatusDao.save(oprStatus);
			
			List<PropertyFilter>filters= new ArrayList<PropertyFilter>();
			DateFormat df=new SimpleDateFormat("HH:mm:ss");
			String dateString =df.format(new Date());
			String nowDate ="1970-01-01 ".concat(dateString);
		//	Date foDate=dfnow.parse(nowDate);
			filters.add(new PropertyFilter("LED_startDate",nowDate));
			filters.add(new PropertyFilter("GED_endDate",nowDate));
			filters.add(new PropertyFilter("EQL_departId",oprSign.getDepartId()+""));
			List<OprLoadingbrigadTime> list =oprLoadingbrigadeTimeDao.find(filters);
			
			StringBuffer sb = new StringBuffer(" ");
			OprFaxIn fax=oprFaxInDao.get(oprSign.getDno());   
			if(DoubleUtil.sub(fax.getConsigneeRate(), consigneeRate)!=0.0){
				sb.append("   到付提送费率").append(fax.getConsigneeRate()).append("改成").append(consigneeRate);
			}
			if(DoubleUtil.sub(fax.getConsigneeFee(), consigneeFee)!=0.0){
				sb.append("   到付提送费").append(fax.getConsigneeFee()).append("改成").append(consigneeFee);
			}
			
			List<FiInterfaceProDto> listfiIneDto=new ArrayList<FiInterfaceProDto>();

			List<SysDepart>  list2= departService.find("from SysDepart o where o.departName=? ",fax.getCusDepartName());
			if(list2.size()!=1){
				throw  new ServiceException("找不到客服部门");
			}
			
			if(DoubleUtil.sub(fax.getConsigneeFee(),consigneeFee)!=0.0){  //修改过的到会提送费 差额写入收入表
				FiInterfaceProDto fidoDto = new FiInterfaceProDto();
				fidoDto.setDno(fax.getDno());// 配送单号(必输参数)
				fidoDto.setCustomerId(fax.getCusId());
				fidoDto.setCustomerName(fax.getCpName());
				fidoDto.setAmount(DoubleUtil.sub(consigneeFee,fax.getConsigneeFee()));
				fidoDto.setCostType("到付提送费");
				fidoDto.setSourceData("自提单");
				fidoDto.setSourceNo(fax.getDno());
				fidoDto.setWhoCash("到付");
				fidoDto.setCustomerName(fax.getCustomerService());
				fidoDto.setIncomeDepart(fax.getInDepart());
				fidoDto.setIncomeDepartId(fax.getInDepartId());
				fidoDto.setAdmDepart(fax.getCusDepartName());
				fidoDto.setAdmDepartId(list2.get(0).getDepartId()	);
				listfiIneDto.add(fidoDto);
			}
			
			if(DoubleUtil.sub(fax.getCusValueAddFee(),totalCusValueAddFee)!=0.0){  //修改过的到会增值费 差额写入收入表
				FiInterfaceProDto fidoDto = new FiInterfaceProDto();
				fidoDto.setDno(fax.getDno());// 配送单号(必输参数)
				fidoDto.setCustomerId(fax.getCusId());
				fidoDto.setCustomerName(fax.getCpName());
				fidoDto.setAmount(DoubleUtil.sub(totalCusValueAddFee,fax.getCusValueAddFee()));
				fidoDto.setCostType("到付增值费");
				fidoDto.setSourceData("自提单");
				fidoDto.setSourceNo(fax.getDno());
				fidoDto.setWhoCash("到付");
				fidoDto.setCustomerName(fax.getCustomerService());
				fidoDto.setIncomeDepart(fax.getInDepart());
				fidoDto.setIncomeDepartId(fax.getInDepartId());
				fidoDto.setAdmDepart(fax.getCusDepartName());
				fidoDto.setAdmDepartId(list2.get(0).getDepartId()	);
				listfiIneDto.add(fidoDto);
			}
			
			if(listfiIneDto.size()!=0){
				iFiInterface.currentToFiIncome(listfiIneDto);
			}

			fax.setConsigneeFee(consigneeFee);             //更新传真录入表
			fax.setConsigneeRate(consigneeRate);
			fax.setCusValueAddFee(totalCusValueAddFee);
			
			if(storeFee>0.0){
				OprValueAddFee oprValueAddFee = new OprValueAddFee();
				oprValueAddFee.setDno(oprSign.getDno());
				oprValueAddFee.setFeeCount(storeFee);
				oprValueAddFee.setFeeName("仓储费");
				oprValueAddFee.setStatus(0l);
				oprValueAddFee.setPayType("到付");
				oprValueAddFeeDao.save(oprValueAddFee);
				
			}
			List<OprLoadingbrigadeWeight> weightList = new ArrayList<OprLoadingbrigadeWeight>();
			if(list.size()>0 && fax.getTakeMode().equals(this.takeMode)){//如果是市内自提则向装卸组货量表中添加提货记录
				 Long wid =list.get(0).getLoadingbrigadId();
				 Long wid2 =list.get(0).getGroupId();
				  OprLoadingbrigadeWeight olbWeight  = new OprLoadingbrigadeWeight();
				  olbWeight.setDno(fax.getDno());
				  olbWeight.setLoadingbrigadeId(wid);
				  olbWeight.setPiece(fax.getPiece());
				  olbWeight.setBulk(fax.getBulk());
				  olbWeight.setWeight(fax.getCqWeight());
				  olbWeight.setGoods(fax.getGoods());
				  olbWeight.setDispatchId(wid2);
				  olbWeight.setDepartId(oprSign.getDepartId());
				  
				  weightList.add(olbWeight);
				  oprLoadingbrigadeWeightService.saveLoadingWeight(weightList,LoadingbrigadeTypeEnum.TIHUO);
			}
			
			if(oprStatus.getIsCreateFi()==null||oprStatus.getIsCreateFi()==0l){
				
					List<FiInterfaceProDto> listfiInterfaceDto =new ArrayList<FiInterfaceProDto>();
					FiInterfaceProDto fiInterfaceProDto = new FiInterfaceProDto();
					fiInterfaceProDto.setOutStockMode(fax.getTakeMode());
					fiInterfaceProDto.setDno(oprSign.getDno());
					fiInterfaceProDto.setSettlementType(1l);
					fiInterfaceProDto.setDocumentsType("收入");
					fiInterfaceProDto.setDocumentsSmalltype("配送单");
					fiInterfaceProDto.setDocumentsNo(oprSign.getDno());
					fiInterfaceProDto.setAmount(fax.getConsigneeFee());
					fiInterfaceProDto.setCostType("到付提送费");
					fiInterfaceProDto.setIncomeDepartId(fax.getInDepartId());
					fiInterfaceProDto.setDepartId(oprSign.getDepartId());
					fiInterfaceProDto.setSourceData("自提单");
					fiInterfaceProDto.setSourceNo(oprSign.getDno());
					fiInterfaceProDto.setCollectionUser(userName);
					fiInterfaceProDto.setContacts(userName);
					fiInterfaceProDto.setDepartId(Long.valueOf(user.get("bussDepart")+""));
					fiInterfaceProDto.setDepartName(user.get("rightDepart")+"");
					listfiInterfaceDto.add(fiInterfaceProDto);
					
					if(fax.getCusValueAddFee()!=null&&fax.getCusValueAddFee()!=0){
						FiInterfaceProDto fiInterfaceProDto2 = new FiInterfaceProDto();
						fiInterfaceProDto2.setOutStockMode(fax.getTakeMode());
						fiInterfaceProDto2.setDno(oprSign.getDno());
						fiInterfaceProDto2.setSettlementType(1l);
						fiInterfaceProDto2.setDocumentsType("收入");
						fiInterfaceProDto2.setDocumentsSmalltype("配送单");
						fiInterfaceProDto2.setDocumentsNo(oprSign.getDno());
						fiInterfaceProDto2.setAmount(fax.getCusValueAddFee());
						fiInterfaceProDto2.setCostType("到付增值费");
						fiInterfaceProDto2.setIncomeDepartId(fax.getInDepartId());
						fiInterfaceProDto2.setDepartId(oprSign.getDepartId());
						fiInterfaceProDto2.setSourceData("自提单");
						fiInterfaceProDto2.setSourceNo(oprSign.getDno());
						fiInterfaceProDto2.setCollectionUser(userName);
						fiInterfaceProDto2.setContacts(userName);
						fiInterfaceProDto2.setDepartId(Long.valueOf(user.get("bussDepart")+""));
						fiInterfaceProDto2.setDepartName(user.get("rightDepart")+"");
						
						listfiInterfaceDto.add(fiInterfaceProDto2);
					}
				
					if(fax.getPaymentCollection()!=null&&fax.getPaymentCollection()!=0){
						FiInterfaceProDto fiInterfaceProDto3 = new FiInterfaceProDto();
						fiInterfaceProDto3.setOutStockMode(fax.getTakeMode());
						fiInterfaceProDto3.setDno(oprSign.getDno());
						fiInterfaceProDto3.setSettlementType(1l);
						fiInterfaceProDto3.setIncomeDepartId(fax.getInDepartId());
						fiInterfaceProDto3.setDocumentsType("代收货款");
						fiInterfaceProDto3.setDocumentsSmalltype("配送单");
						fiInterfaceProDto3.setDocumentsNo(oprSign.getDno());
						fiInterfaceProDto3.setAmount(fax.getPaymentCollection());
						fiInterfaceProDto3.setCostType("代收货款");
						fiInterfaceProDto3.setDepartId(oprSign.getDepartId());
						fiInterfaceProDto3.setSourceData("自提单");
						fiInterfaceProDto3.setSourceNo(oprSign.getDno());
						fiInterfaceProDto3.setCollectionUser(userName);
						fiInterfaceProDto3.setContacts(userName);
						fiInterfaceProDto3.setDepartId(Long.valueOf(user.get("bussDepart")+""));
						fiInterfaceProDto3.setDepartName(user.get("rightDepart")+"");
						listfiInterfaceDto.add(fiInterfaceProDto3);
					}
					iFiInterface.outStockToFi(listfiInterfaceDto);
					oprStatus.setIsCreateFi(1l);
			}
			
			if("".equals(oprSign.getReplaceSign()) || oprSign.getReplaceSign()==null){
				fax.setRealGoWhere("自提出库："+oprSign.getSignMan());
				oprStatus.setSignName(oprSign.getSignMan());
			}else{
				fax.setRealGoWhere("自提出库："+oprSign.getReplaceSign());
				oprStatus.setSignName(oprSign.getReplaceSign());
			}
			
			List<OprStock> listo = oprStockDao.find(" from OprStock o where o.dno=? and o.departId=? ",oprSign.getDno(),oprSign.getDepartId());
			OprStock oStock= null;
			if(listo.size()==0){
				if("机场自提".equals(fax.getTakeMode())){
					oStock=new OprStock(fax.getDno(),fax.getFlightMainNo(),fax.getSubNo(),fax.getConsignee(),fax.getAddr(),fax.getCusWeight(),0l);
				}else{
					throw new ServiceException("此票货物库存件数为0，不能进行提货签收");
				}
		  	}else{
		  		oStock=listo.get(0);
		  		if(!"机场自提".equals(fax.getTakeMode())&&oStock.getPiece()==0l){
					throw new ServiceException("此票货物库存件数为0，不能进行提货签收");
				}
		  	}
		  //**********************************************
		  	oStock.setPiece(oStock.getPiece()-fax.getPiece());
		  	if(null!=oStock.getId() && oStock.getPiece().equals(0l)){
				//如果没有库存则删除
				oprStockDao.delete(oStock);
			}else{
				oprStockDao.save(oStock);
			}
			oprFaxInDao.save(fax);
			
			if(oprSign.getReplaceSign()==null||"".equals(oprSign.getReplaceSign())){
				oprHistoryService.saveLog(oprSign.getDno(), "自提出库已签收，签收人："+oprSign.getSignMan()+sb.toString(), log_selfOutStock);
			}else{
				oprHistoryService.saveLog(oprSign.getDno(), "自提出库已签收，收货人："+oprSign.getSignMan()+"，签收人："+oprSign.getReplaceSign()+sb.toString(), log_selfOutStock);
			}
	}

	public void delSign(String[] dnos) throws Exception {
		
		if(null==dnos || "".equals(dnos) || dnos.length<1){
			throw new ServiceException("请输入配送单号!");
		}
		for (int i = 0; i < dnos.length; i++) {
			
			Long dno = Long.valueOf(dnos[i]);
			OprStatus status=oprStatusDao.findStatusByDno(dno);
			if(status.getCashStatus()==1l){
				throw new ServiceException("配送单号为"+dno+"的货物已经收银,不允许撤销!");
			}
			if(status.getSignStatus()!=1l){
				throw new ServiceException("配送单号为"+dno+"还没有签收!");
			}
			List<OprRequestDo> list=oprRequestDoDao.find("from OprRequestDo ord where ord.dno=? and ord.requestStage=?", dno,SIGN_REQUEST_STAGE);
			//如果该票货物存在签收阶段的个性化要求，则将执行状态修改为未执行
			if(null!= list && list.size()>0){
				OprRequestDo ord=list.get(0);
				ord.setIsOpr(0L);
				ord.setOprMan("");
				oprRequestDoDao.save(ord);
			}
			status.setSignStatus(Long.valueOf(0));
			status.setSignName("");
			status.setSignTime(null);
			oprStatusDao.save(status);
			
			List<OprSign> signList = this.oprSignDao.findBy("dno",dno);
			for(OprSign sign : signList){
				this.delete(sign);
			}
			oprHistoryService.saveLog(dno, "配送单号为："+dno+"的货物做签收录入撤销", log_inputSignReturn);
		
		}
		
	}


	public Long getLog_selfOutStock() {
		return log_selfOutStock;
	}


	public void setLog_selfOutStock(Long log_selfOutStock) {
		this.log_selfOutStock = log_selfOutStock;
	}



	public void saveTask(OprSign sign) throws Exception {
		try{
				List<OprSign> signList= this.oprSignDao.findBy("dno",sign.getDno());
				OprFaxIn fax = this.oprFaxInDao.findUniqueBy("dno",sign.getDno());
				
				if(null==fax){
					return;
				}
				OprStatus status=oprStatusService.findStatusByDno(sign.getDno());
				
				if(null==status){
					throw new ServiceException("数据异常，不存在此货物！");
				}
				OprSign entity = null;
				if(signList.size()>0){
					entity = signList.get(0);
				}else{
					entity = sign;
				}
				String signMan = entity.getSignMan();
				if(null!=entity.getReplaceSign() && !"".equals(entity.getReplaceSign().trim())){
					signMan = entity.getReplaceSign();//代签人
				}
				
				
				status.setSignStatus(1L);
				status.setSignTime(new Date());
				status.setSignName(signMan);
				oprStatusService.save(status);
				
				BeanUtils.copyProperties(sign, entity,new String[]{"id"});
				entity.setDepartId(fax.getInDepartId());//把录单部门作为签收的部门
				this.oprSignDao.save(entity);
				
			}catch(Exception e){
				e.printStackTrace();
				throw new ServiceException(e.getLocalizedMessage());
			}
	}
}
