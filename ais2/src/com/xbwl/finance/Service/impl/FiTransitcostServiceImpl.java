package com.xbwl.finance.Service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.log.anno.ModuleName;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.common.utils.DoubleUtil;
import com.xbwl.common.utils.LogType;
import com.xbwl.entity.FiCost;
import com.xbwl.entity.FiOutcost;
import com.xbwl.entity.FiTransitcost;
import com.xbwl.entity.OprFaxIn;
import com.xbwl.entity.OprStatus;
import com.xbwl.finance.Service.IFiTransitcostService;
import com.xbwl.finance.dao.IFiCostDao;
import com.xbwl.finance.dao.IFiTransicostDao;
import com.xbwl.finance.dto.IFiInterface;
import com.xbwl.finance.dto.impl.FiInterfaceProDto;
import com.xbwl.oper.fax.dao.IOprFaxInDao;
import com.xbwl.oper.stock.dao.IOprStatusDao;
import com.xbwl.oper.stock.service.IOprHistoryService;
import com.xbwl.oper.stock.service.IOprStatusService;

/**
 * author shuw
 * time Oct 7, 2011 11:47:14 AM
 */
@Service("fiTransitcostServiceImpl")
@Transactional(rollbackFor={Exception.class})
public class FiTransitcostServiceImpl extends BaseServiceImpl<FiTransitcost, Long> implements
	IFiTransitcostService {

	@Resource(name = "fiTransitcostHibernateDaoImpl")
	private IFiTransicostDao fiTransicostDao;

	@Resource(name = "oprStatusHibernateDaoImpl")
	private IOprStatusDao oprStatusDao;

	@Resource(name="fiCostHibernateDaoImpl")
	private IFiCostDao fiCostDao;
	
	@Resource(name = "oprFaxInHibernateDaoImpl")
	private IOprFaxInDao oprFaxInDao;	
	
	@Resource(name = "fiInterfaceImpl")
	private IFiInterface fiInterfaceImpl;
	@Override
	public IBaseDAO<FiTransitcost, Long> getBaseDao() {
		return fiTransicostDao;
	}

	@Value("${fiAuditCost.log_auditCost}")
	private Long log_auditCost ;

	@Value("${fiAuditCost.log_qxAuditCost}")
	private Long log_qxAuditCost ;

	
	@Resource(name = "oprHistoryServiceImpl")
	private IOprHistoryService oprHistoryService;
	
	//传真状态表
	@Resource(name = "oprStatusServiceImpl")
	private IOprStatusService oprStatusService;
	
	/* 
	 *返货成本审核
	 */
	@ModuleName(value="中转返货成本付款",logType=LogType.fi)
	public String saveFiTransitcostAndFicost(User user,String ts,Long id) throws Exception {
		//Long bussDepartId = Long.parseLong(user.get("bussDepart").toString());
		Long batchNo= getBatchNo();
		FiTransitcost fiTransitcost= fiTransicostDao.get(id);
		OprFaxIn oprFaxIn =oprFaxInDao.get(fiTransitcost.getDno());
		
		fiTransitcost.setBatchNo(batchNo);
		fiTransitcost.setStatus(1l);
		fiTransitcost.setReviewUser(user.get("name").toString());
		fiTransitcost.setReviewDate(new Date());
		fiTransitcost.setReviewRemark("支付中转返货成本"+fiTransitcost.getAmount()+"元");
		oprHistoryService.saveLog(oprFaxIn.getDno(), "付款中转成本(返货登记)，付款金额："+fiTransitcost.getAmount() , log_auditCost);     //操作日志
		
		//保存到成本表中
		FiCost fiCost = new FiCost();
		fiCost.setCostType("中转成本");
		fiCost.setDataSource("中转成本");
		fiCost.setCostAmount(fiTransitcost.getAmount());
		fiCost.setCostTypeDetail("返货登记");
		fiCost.setDataSource("中转成本");
		fiCost.setCostTypeDetail("传真录入");
		fiCost.setCostAmount(fiTransitcost.getAmount());
		fiCost.setStatus(1l);
		fiCost.setDno(fiTransitcost.getDno());
		fiCostDao.save(fiCost);
		
		//customerDao.get(oprFaxIn.getGoWhereId());

		//保存到应收应付表中
		FiInterfaceProDto fiInterfaceProDto = new FiInterfaceProDto()	;
		List<FiInterfaceProDto> listPro = new ArrayList<FiInterfaceProDto>();
		fiInterfaceProDto.setCustomerId(oprFaxIn.getGoWhereId());
		fiInterfaceProDto.setCustomerName(oprFaxIn.getGowhere());
		fiInterfaceProDto.setDistributionMode("客商");
		fiInterfaceProDto.setDno(fiTransitcost.getDno());
		fiInterfaceProDto.setSettlementType(2l);
		fiInterfaceProDto.setDocumentsType("成本");
		fiInterfaceProDto.setAmount(fiTransitcost.getAmount());
		fiInterfaceProDto.setDocumentsSmalltype("配送单");
		fiInterfaceProDto.setDocumentsNo(fiTransitcost.getDno());
		fiInterfaceProDto.setCollectionUser(fiTransitcost.getReviewUser());
		fiInterfaceProDto.setCostType("中转费");
		fiInterfaceProDto.setDepartId(Long.parseLong(user.get("departId").toString()));
		fiInterfaceProDto.setDepartName(user.get("rightDepart")+"");
		fiInterfaceProDto.setSourceData("中转成本");
		fiInterfaceProDto.setSourceNo(fiTransitcost.getId());
		listPro.add(fiInterfaceProDto);

		fiInterfaceImpl.addFinanceInfo(listPro);
		 return id+"";
	}

	/* 
	 * 传真录入 的中转成本审核
	 * @see com.xbwl.finance.Service.IFiTransitcostService#saveFiTransitcostAndFicost(org.ralasafe.user.User, java.util.List)
	 */
	@ModuleName(value="中转成本付款",logType=LogType.fi)
	public String saveFiTransitcostAndFicost(User user, List<FiTransitcost> aa) throws Exception {
		List<FiInterfaceProDto> listPro = new ArrayList<FiInterfaceProDto>();
		StringBuffer ids=new StringBuffer(500);
		Long batchNo= getBatchNo();
		for(FiTransitcost fiTransitcost2 : aa){
		
			 OprFaxIn oprFaxIn =oprFaxInDao.get(fiTransitcost2.getDno());
		/*	
			if(!oprFaxIn.getInDepartId().equals(Long.parseLong(user.get("bussDepart")+""))){
				throw new ServiceException("请勿审核非本业务部门的数据，谢谢！");
			}*/
			
			FiTransitcost fiTransitcost = new FiTransitcost();
			fiTransitcost.setAmount(oprFaxIn.getTraFee());
			fiTransitcost.setDno(oprFaxIn.getDno());
			fiTransitcost.setCustomerId(oprFaxIn.getGoWhereId());
			fiTransitcost.setCustomerName(oprFaxIn.getGowhere());
			fiTransitcost.setPayStatus(0l);
			fiTransitcost.setSourceData("传真录入");
			fiTransitcost.setBatchNo(batchNo);
			fiTransitcost.setStatus(1l);
			fiTransitcost.setReviewUser(user.get("name").toString());
			fiTransitcost.setReviewDate(new Date());
			fiTransitcost.setReviewRemark("支付中转成本"+fiTransitcost.getAmount()+"元");
			save(fiTransitcost);
			
			ids.append(fiTransitcost.getId()).append(",");
			oprHistoryService.saveLog(oprFaxIn.getDno(), "付款中转成本，付款金额："+fiTransitcost.getAmount() , log_auditCost);     //操作日志
			
			List<OprStatus> listu =oprStatusDao.find(" from OprStatus o where o.dno=?  ",oprFaxIn.getDno());
			if(listu.size()==1){
				
				boolean flag=oprFaxIn.getConsigneeFee()==0.0&&oprFaxIn.getPaymentCollection()==0.0&&oprFaxIn.getCusValueAddFee()==0.0;
				//判断收银状态
				if(!flag){
					if(listu.get(0).getCashStatus()!=1l){
						throw new ServiceException("配送单号"+oprFaxIn.getDno()+"的货物没有收银确认，不能进行付款");
					}
				}
				
				if(listu.get(0).getFeeAuditStatus()==1l){
					throw new ServiceException("配送单号"+oprFaxIn.getDno()+"的货物中转成本已付款");
				}else{
					listu.get(0).setFeeAuditStatus(1l);
					listu.get(0).setFeeAuditTime(new Date());
					oprStatusDao.save(listu.get(0));
				}
			}else{
				throw new ServiceException("查询配送单号"+oprFaxIn.getDno()+"的货物中转成本付款状态错误");
			}
			
			//保存到成本表中
			FiCost fiCost = new FiCost();
			fiCost.setCostType("中转成本");
			fiCost.setDataSource("中转成本");
			fiCost.setCostAmount(fiTransitcost.getAmount());
			fiCost.setCostTypeDetail("返货登记");
			fiCost.setDataSource("中转成本");
			fiCost.setCostTypeDetail("传真录入");
			fiCost.setCostAmount(oprFaxIn.getTraFee());
			fiCost.setStatus(1l);
			fiCost.setDno(fiTransitcost.getDno());
			fiCostDao.save(fiCost);
			
			FiInterfaceProDto fiInterfaceProDto = new FiInterfaceProDto()	;
			fiInterfaceProDto.setCustomerId(oprFaxIn.getGoWhereId());
			fiInterfaceProDto.setCustomerName(oprFaxIn.getGowhere());
			fiInterfaceProDto.setDistributionMode("客商");
			fiInterfaceProDto.setDno(fiTransitcost.getDno());
			fiInterfaceProDto.setAmount(fiTransitcost.getAmount());
			fiInterfaceProDto.setSettlementType(2l);
			fiInterfaceProDto.setDocumentsType("成本");
			fiInterfaceProDto.setDocumentsSmalltype("配送单");
			fiInterfaceProDto.setDocumentsNo(fiTransitcost.getDno());
			fiInterfaceProDto.setCollectionUser(fiTransitcost.getReviewUser());
			fiInterfaceProDto.setCostType("中转费");
			fiInterfaceProDto.setDepartId(Long.parseLong(user.get("bussDepart").toString()));
			fiInterfaceProDto.setDepartName(user.get("rightDepart")+"");
			fiInterfaceProDto.setSourceData("中转成本");
			fiInterfaceProDto.setSourceNo(fiTransitcost.getId());
			listPro.add(fiInterfaceProDto);
		}
		String  s =fiInterfaceImpl.addFinanceInfo(listPro);
		 return ids.toString();
	}
	
	/* 
	 * 撤销审核
	 */
	@ModuleName(value="撤销中转成本付款",logType=LogType.fi)
	public void qxFiAudit(Long id, String ts,String sourceData) throws Exception {
		FiTransitcost fiTransitcost = getAndInitEntity(id);
		User user= WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		Long bussDepartId=Long.parseLong(user.get("bussDepart")+"");
	
	/*	if(bussDepartId-fiTransitcost.getDepartId()!=0){
			throw new ServiceException("请勿操作其他业务部门的数据");
		}*/
		
		List<FiInterfaceProDto> listPro = new ArrayList<FiInterfaceProDto>();
		fiTransitcost.setTs(ts);
		if("返货登记".equals(sourceData)){
			fiTransitcost.setStatus(0l);
			fiTransitcost.setReviewRemark(null);
			fiTransitcost.setReviewUser(null);
			save(fiTransitcost);
			
			FiInterfaceProDto fiInterfaceProDto = new FiInterfaceProDto()	;
			fiInterfaceProDto.setSourceData("中转成本");
			fiInterfaceProDto.setCustomerId(fiTransitcost.getCustomerId());
			fiInterfaceProDto.setSourceNo(fiTransitcost.getId());
			listPro.add(fiInterfaceProDto);     //调用财务接口，处理应收应付数据
			
			FiCost fiCostNew = new FiCost();
			fiCostNew.setCostType("中转成本");
			fiCostNew.setCostTypeDetail("返货登记");
			fiCostNew.setCostAmount(- fiTransitcost.getAmount());
			fiCostNew.setDataSource("返货登记");
			fiCostNew.setDno(fiTransitcost.getDno());
			fiCostNew.setStatus(1l);
			fiCostDao.save(fiCostNew);  //减去返货成本
			
			oprHistoryService.saveLog(fiTransitcost.getDno(), "中转返货成本付款撤销，撤销金额："+fiTransitcost.getAmount() , log_auditCost);     //操作日志
		}else{
			String hql="from FiTransitcost fo where fo.dno=?  and fo.status=1 and fo.departId=? and  (fo.sourceData=? or fo.sourceData=? ) and fo.customerId=? ";
			List<FiTransitcost>list =find(hql,fiTransitcost.getDno(),fiTransitcost.getDepartId(),"传真录入","更改申请",fiTransitcost.getCustomerId());  
			if(list.size()==0){
				//REVIEW-ACCEPT 提示要准确
				//FIXED
				throw new ServiceException("查找不到中转成本数据");
			}
			
			double costDouble=0.0;
			 for(FiTransitcost fiTransitcost2:list){
				 if(fiTransitcost2.getPayStatus()==1l){
					 throw new ServiceException("存在中转成本已支付的数据");
				 }
				 costDouble=DoubleUtil.add(costDouble, fiTransitcost2.getAmount());   //金额汇总
				 
				FiInterfaceProDto fiInterfaceProDto = new FiInterfaceProDto()	;
				if("更改申请".equals(fiTransitcost2.getSourceData())){
					fiInterfaceProDto.setSourceData(fiTransitcost2.getSourceData());
				}else{
					fiInterfaceProDto.setSourceData("中转成本");
				}
				fiInterfaceProDto.setCustomerId(fiTransitcost.getCustomerId());
				fiInterfaceProDto.setSourceNo(fiTransitcost2.getId());
				listPro.add(fiInterfaceProDto);     //调用财务接口，处理应收应付数据
				 
				 delete(fiTransitcost2.getId());  //删除中转成本数据
			 }
			 
			 List<OprStatus> listu =oprStatusDao.find(" from OprStatus o where o.dno=?  ",fiTransitcost.getDno());
			 if(listu.size()==1){
					listu.get(0).setFeeAuditStatus(0l);
					listu.get(0).setFeeAuditTime(new Date());
					oprStatusDao.save(listu.get(0));  //状态表的中转成本审核状态更改
			 }else{
				 throw new ServiceException("取该数据的状态记录出错！");
			 }
			 
			FiCost fiCostNew = new FiCost();
			fiCostNew.setCostType("中转成本");
			fiCostNew.setCostTypeDetail("传真录入");
			fiCostNew.setCostAmount(-costDouble);
			fiCostNew.setDataSource("中转成本");
			fiCostNew.setDno(fiTransitcost.getDno());
			fiCostNew.setStatus(1l);
			fiCostDao.save(fiCostNew);  //对冲成本表中的数据
		
			oprHistoryService.saveLog(fiTransitcost.getDno(), "中转成本付款撤销，撤销金额："+costDouble, log_qxAuditCost);     //操作日志
		}
		fiInterfaceImpl.invalidToFi(listPro);  //财务接口
	}

	/* 
	 * 获取批次号
	 */
	public Long getBatchNo() throws Exception {
		String carString ;
		Map  times = (Map)fiTransicostDao.createSQLQuery("  select SEQ_FI_TRANSI_BATCH_NO.nextval cartimes from  dual  ").list().get(0);
		Long s =Long.valueOf(times.get("CARTIMES")+"");
		return s;
	}

	/* 
	 * 中转成本管理查询Sql拼接
	 */
	@ModuleName(value="中转成本查询SQL拼接",logType=LogType.fi)
	public String getSelectSql(Map map, Long type) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("select * from ( ");
				if(type==null){
					throw new ServiceException("请选择审核状态进行查询");
				}
				if(type==-1l||type==0l){  //查询全部或者未审核的数据
						 sb=addSqlBuffer(sb);
						 sb.append("WHERE  (  ");
				   		     			 sb.append("t0.DISTRIBUTION_MODE  =  '中转' ");  
							   sb.append("AND t0.D_NO = t2.D_NO  ");
							   sb.append("AND t0.D_NO = t3.D_NO(+)   ");
							   sb.append("AND t0.D_NO = t1.D_NO ");
							   sb.append("AND t0.tra_fee >0  ");
							   sb.append("AND t2.fee_audit_status = 0  ");
							   sb.append("AND t3.source_data(+) <> '返货登记'  ");  //查询未审核的数据，自动流过来的，并且未审核的，不包括返货登记的数据
							   sb.append("AND t0.D_NO = t1.D_NO ");
								   sb.append(")  ");
							   
					sb.append("UNION ALL  ");
						sb=addSqlBuffer(sb);
						sb.append("WHERE  (  ");
								   		     sb.append("t0.DISTRIBUTION_MODE  =  '中转' ");  
								   sb.append("AND t0.D_NO = t2.D_NO  ");
								   sb.append("AND t0.D_NO = t3.D_NO  ");
								   sb.append("AND t0.D_NO = t1.D_NO ");
								   sb.append("AND t3.source_data <> '传真录入'  ");   //查询返货登记的未审核的数据
								   sb.append("AND t3.status=0  ");
						sb.append(")  ");
	
				}
				
				if(type==0l){  
					sb.append("UNION ALL  ");
				}
				if(type==1l||type==0l){  //查询已审核或全部的数据
						sb=addSqlBuffer(sb);
						sb.append("WHERE  (  ");
								   		     sb.append("t0.DISTRIBUTION_MODE  =  '中转' ");  
								   sb.append("AND t0.D_NO = t2.D_NO  ");
								   sb.append("AND t0.D_NO = t3.D_NO  ");
								   sb.append("AND t0.D_NO = t1.D_NO ");
								   sb.append("AND t3.status=1  ");
						sb.append(")  ");
				}
		sb.append(")  where  (t0_IN_DEPART_ID =:departId  OR t3_DEPART_ID = :depart2 )  ");  //查询已审核的数据
		String sql=addSelectCondition(sb,map);   //加上查询条件
		return sql;
	}
	
	//SQL拼接
	public StringBuffer addSqlBuffer(StringBuffer sb){
		sb.append("SELECT  ");
				sb.append("t0.D_NO  AS t0_D_NO , ");
				sb.append("t0.DISTRIBUTION_MODE  AS t0_DISTRIBUTION_MODE ,   ");
			//	sb.append("t0.GOWHERE  AS t0_GOWHERE , ");
				sb.append("t0.TAKE_MODE  AS t0_TAKE_MODE , ");
				sb.append("t0.ADDR  AS  t0_ADDR ,  ");
				sb.append("t0.PIECE  AS t0_PIECE, ");
				sb.append("t0.CQ_WEIGHT  AS t0_CQ_WEIGHT, ");
				 sb.append(" case when t3.amount is null then t0.TRA_FEE  else t3.amount end as t0_TRA_FEE ,"); 
				sb.append("t0.AREA_RANK  AS t0_AREA_RANK, ");
				sb.append("t0.CREATE_TIME  AS t0_CREATE_TIME , ");
			//	sb.append("t0.GOWHERE_ID  AS t0_GOWHERE_ID ,   ");
				
				sb.append(" case when t3.source_data  is null then  t0.GOWHERE_ID else t3.customer_id end as t0_GOWHERE_ID ,"); 
				sb.append(" case when t3.source_data  is null then  t0.GOWHERE else t3.customer_name  end as t0_GOWHERE , "); 
				
				sb.append("t0.DISTRIBUTION_DEPART  AS t0_IN_DEPART ,   ");
				sb.append("t0.DISTRIBUTION_DEPART_ID  AS t0_IN_DEPART_ID , ");
				sb.append("t0.CP_NAME  AS t0_CP_NAME ,   ");
				sb.append("t0.FLIGHT_MAIN_NO  AS t0_FLIGHT_MAIN_NO , ");
				sb.append("t0.SUB_NO  AS t0_SUB_NO ,   ");
				sb.append("t0.CONSIGNEE  AS t0_CONSIGNEE , "); 
				sb.append("t0.CP_FEE  AS t0_CP_FEE , ");
				sb.append("t0.CONSIGNEE_FEE AS t0_CONSIGNEE_FEE, ");
				sb.append(" t0.CP_FEE+t0.CONSIGNEE_FEE totalFee, ");
				sb.append("t0.CP_FEE+t0.CONSIGNEE_FEE-t0.TRA_FEE deficitFee, ");
				sb.append("t1.CONFIRM_STATUS AS t1_CONFIRM_STATUS , ");
				sb.append("t2.FEE_AUDIT_STATUS  AS t2_FEE_AUDIT_STATUS , "); 
				sb.append("decode(t2.CASH_STATUS,null,0,t2.CASH_STATUS)  AS t2_CASH_STATUS, ");
				sb.append("t3.id t3_ID, ");
				sb.append("t3.STATUS  AS t3_STATUS,  ");
				sb.append("t3.REVIEW_USER AS t3_REVIEW_USER, ");
				sb.append("t3.REVIEW_DATE  AS t3_REVIEW_DATE, ");
				sb.append("t3.REVIEW_REMARK  AS t3_REVIEW_REMARK, ");
				sb.append("decode(t3.PAY_STATUS,null,0,t3.PAY_STATUS)  AS t3_PAY_STATUS, ");
				sb.append("t3.TS AS t3_TS, ");
				sb.append("t3.DEPART_ID  AS t3_DEPART_ID , "); 
				sb.append("t3.DEPART_NAME  AS t3_DEPART_NAME , ");   
				sb.append("t3.BATCH_NO batchNo, ");
				sb.append("0 t3_amount, ");
		        sb.append("t3.source_data  AS t3_source_data , ");
				sb.append("t3.CREATE_TIME t3_CREATE_TIME , ");
				sb.append("t3.source_no t3_source_no, ");
				sb.append("t3.customer_name  t3_customer_name, ");
				sb.append("t3.customer_id  t3_customer_id, ");
				sb.append("t0.cus_Depart_Code  t0_cus_Depart_Code, ");
				sb.append("t0.cus_Depart_Name  t0_cus_Depart_Name ");
		sb.append("FROM  OPR_FAX_IN t0, ");
			   sb.append("OPR_RECEIPT t1, ");
			   sb.append("OPR_STATUS t2, ");
			   sb.append("FI_TRANSITCOST t3 ");    
	   return sb;
	}
	
	//加上查询条件的方法
	public  String  addSelectCondition(StringBuffer sb,Map map ) throws Exception{
		String itemsValue = (String)map.get("itemsValue");
		Date startDate = (Date)map.get("startDate");
		Date endDate = (Date)map.get("endDate");
		Long confirmStatus =(Long) map.get("confirmStatus"); 
 		String checkItems = (String) map.get("checkItems");
 		String serviceDepartCode=(String )map.get("serviceDepartCode");
 		String dateType=(String)map.get("dateType");
		
 		if("传真日期".equals(dateType)){
			if(startDate!=null){
				sb.append("AND( t0_CREATE_TIME >= to_date( :startDate ,'yyyy-mm-dd hh24:mi:ss') OR   t3_CREATE_TIME >= to_date( :startDate2 ,'yyyy-mm-dd hh24:mi:ss') ) ");
			}
			if(endDate!=null){
				sb.append("AND( t0_CREATE_TIME <= to_date( :endDate ,'yyyy-mm-dd hh24:mi:ss')  OR   t3_CREATE_TIME <= to_date( :endDate2 ,'yyyy-mm-dd hh24:mi:ss')  ) ");
			}
 		}else{
 			if(startDate!=null){
				sb.append("AND( t3_REVIEW_DATE >= to_date( :startDate ,'yyyy-mm-dd hh24:mi:ss') OR   t3_REVIEW_DATE >= to_date( :startDate2 ,'yyyy-mm-dd hh24:mi:ss') ) ");
			}
			if(endDate!=null){
				sb.append("AND( t3_REVIEW_DATE <= to_date( :endDate ,'yyyy-mm-dd hh24:mi:ss')  OR   t3_REVIEW_DATE <= to_date( :endDate2 ,'yyyy-mm-dd hh24:mi:ss')  ) ");
			}
 			
 		}
		if(confirmStatus!=null){
			sb.append("AND t1_CONFIRM_STATUS = :confirmStatus ");
		}
		
		if(serviceDepartCode!=null){
			sb.append("AND t0_cus_Depart_Code  like :serviceDepartCode ");
		}
		if(itemsValue!=null&&checkItems!=null&&!"".equals(itemsValue)){
			if("cashStatus".equals(checkItems)){
				sb.append("AND t2_CASH_STATUS = :itemsValue ");
			}else if("dno".equals(checkItems)){
				sb.append("AND t0_D_NO = :itemsValue ");
			}else if("batchNo".equals(checkItems)){
				sb.append("AND batchNo = :itemsValue ");
			}else if("goWhere".equals(checkItems)){
				sb.append("AND t0_GOWHERE_ID = :itemsValue ");
			}else if("payStatus".equals(checkItems)){
				sb.append("AND t3_PAY_STATUS = :itemsValue ");
			}
			
		}
		return sb.toString();
	}
	
	//撤销审核信息提示
	@ModuleName(value="中转成本撤销审核时信息提示",logType=LogType.fi)
	public String qxAmountCheck(Long id) throws Exception {
		FiTransitcost fiTransitcost =get(id);
		String hql="from FiTransitcost fo where fo.dno=?  and fo.status=1 and fo.departId=? and ( fo.sourceData=? or fo.sourceData=? ) and fo.customerId=? ";
		List<FiTransitcost>list =find(hql,fiTransitcost.getDno(),fiTransitcost.getDepartId(),"传真录入","更改申请",fiTransitcost.getCustomerId());  
		double totaAmount=0.0;
		for (FiTransitcost fiTransitcost2 : list) {
			totaAmount=DoubleUtil.add(fiTransitcost2.getAmount(), totaAmount);
		}
		StringBuffer sb=new StringBuffer();
		sb.append("撤销中转成本总计<span  style='color:red'> ").append(list.size()).append(" </span>行,")
			.append("金额<span  style='color:red'> ").append(totaAmount).append(" </span>元");
		return sb.toString();
	}
	
	public void payConfirmationBybatchNo(Long batchNo) throws Exception{
		//FiTransitcost fiTransicost=this.fiTransicostDao.get(id);
		List<FiTransitcost> list=this.fiTransicostDao.findBy("batchNo", batchNo);
		if(list==null) throw new ServiceException("中转成本中根据批次号:"+batchNo+"未找到数据！");
		User user= WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		for(FiTransitcost fiTransicost:list){
			if(fiTransicost==null) throw new ServiceException("核销中转成本失败！");
			fiTransicost.setPayStatus(1L);
			fiTransicost.setPayTime(new Date());
			fiTransicost.setPayUser(user.get("name").toString());
			this.fiTransicostDao.save(fiTransicost);
			
			//成本支付状态
			OprStatus oprStatus=this.oprStatusService.findStatusByDno(fiTransicost.getDno());
			if(oprStatus!=null){
				oprStatus.setPayTra(1L);
				oprStatus.setPayTraTime(new Date());
				this.oprStatusService.save(oprStatus);
			}
		}
	}
	
	public void payConfirmationRegisterBybatchNo(Long batchNo) throws Exception{
		List<FiTransitcost> list=this.fiTransicostDao.findBy("batchNo", batchNo);
		if(list==null) throw new ServiceException("中转成本中根据批次号:"+batchNo+"未找到数据！");
		for(FiTransitcost fiTransicost:list){
			if(fiTransicost==null) throw new ServiceException("核销中转成本失败！");
			fiTransicost.setPayStatus(0L);
			fiTransicost.setPayTime(null);
			fiTransicost.setPayUser(null);
			this.fiTransicostDao.save(fiTransicost);
			
			//撤销成本支付状态
			OprStatus oprStatus=this.oprStatusService.findStatusByDno(fiTransicost.getDno());
			if(oprStatus!=null){
				oprStatus.setPayTra(0L);
				oprStatus.setPayTraTime(null);
				this.oprStatusService.save(oprStatus);
			}
		}
	}
}
