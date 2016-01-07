package com.xbwl.oper.stock.service.impl;

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

import com.xbwl.common.log.anno.ModuleName;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.common.utils.LogType;
import com.xbwl.entity.OprFaxIn;
import com.xbwl.entity.OprInformAppointment;
import com.xbwl.entity.OprInformAppointmentDetail;
import com.xbwl.entity.OprStatus;
import com.xbwl.oper.fax.service.IOprFaxInService;
import com.xbwl.oper.stock.dao.IOprInformAppointmentDao;
import com.xbwl.oper.stock.service.IOprHistoryService;
import com.xbwl.oper.stock.service.IOprInformAppointmentDetailService;
import com.xbwl.oper.stock.service.IOprInformAppointmentService;
import com.xbwl.oper.stock.service.IOprStatusService;

/**
 * author CaoZhili time Jul 18, 2011 2:19:21 PM
 * 
 * 通知预约主表服务层实现类
 */
@Service("oprInformAppointmentServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class OprInformAppointmentServiceImpl extends
		BaseServiceImpl<OprInformAppointment, Long> implements
		IOprInformAppointmentService {

	@Resource(name = "oprInformAppointmentHibernateDaoImpl")
	private IOprInformAppointmentDao oprInformAppointmentDao;
	
	@Resource(name = "oprInformAppointmentDetailServiceImpl")
	private IOprInformAppointmentDetailService oprInformAppointmentDetailService;

	@Resource(name = "oprFaxInServiceImpl")
	private IOprFaxInService oprFaxInService;
	
	@Resource(name = "oprStatusServiceImpl")
	private IOprStatusService oprStatusService;
	
	@Resource(name = "oprHistoryServiceImpl")
	private IOprHistoryService oprHistoryService;
	
	@Value("${oprInformAppointmentServiceImpl.log_informSuccess}")
	private Long   log_informSuccess;//通知成功
	
	@Value("${oprInformAppointmentServiceImpl.log_informFailure}")
	private Long   log_informFailure;//通知失败
	@Override
	public IBaseDAO<OprInformAppointment, Long> getBaseDao() {

		return this.oprInformAppointmentDao;
	}
	
	@ModuleName(value="通知客户",logType=LogType.buss)
	public void saveInformAppointmentService(Map map) throws Exception{
		Long dno=Long.parseLong(map.get("dno").toString());
		Long informResult=Long.parseLong(map.get("informResult").toString());
		String informType=map.get("informType").toString();
		String ts=map.get("ts").toString();
		User user = WebRalasafe.getCurrentUser(ServletActionContext
				.getRequest());
		
		OprFaxIn faxin=oprFaxInService.findBy("dno", dno).get(0);
		Date dt=new Date();
		List<OprInformAppointment> oprmentlist=this.oprInformAppointmentDao.findBy("dno", dno);
		OprInformAppointment oprment =null;
		if(null==oprmentlist || oprmentlist.size()==0){
			oprment=new OprInformAppointment();
			oprment.setDno(faxin.getDno());
			oprment.setBulk(faxin.getBulk());
			oprment.setFlightNo(faxin.getFlightNo());
			oprment.setInformNum(new Long(1));
			oprment.setLastInformCus(faxin.getConsignee());
			oprment.setLastInformResult(informResult);
			oprment.setLastInformTime(dt);
			oprment.setLastServiceName(user.get("name").toString());
			oprment.setOprInformAppointmentDetails(null);
			oprment.setPiece(faxin.getPiece());
			//oprment.setRealpiece(faxin.get);
			oprment.setWeight(faxin.getCusWeight());
			
			this.oprInformAppointmentDao.save(oprment);//save
		}else{
			oprment= oprmentlist.get(0);
			
			oprment.setLastInformCus(faxin.getConsignee());
			oprment.setLastInformResult(informResult);
			oprment.setLastInformTime(dt);
			oprment.setLastServiceName(user.get("name").toString());
			oprment.setInformNum(oprment.getInformNum()+1);
			oprment.setTs(ts);
			//把修改后的信息保存到通知预约主表
			this.oprInformAppointmentDao.save(oprment);
			
		}
		
	
		oprment=this.oprInformAppointmentDao.findBy("dno", dno).get(0);
		
		
		OprInformAppointmentDetail oprmentDetail=new OprInformAppointmentDetail();
		
		oprmentDetail.setCpFee(faxin.getCpFee());
		oprmentDetail.setCusAddr(faxin.getAddr());
		oprmentDetail.setCusTel(faxin.getConsigneeTel());
		oprmentDetail.setCusMobile(faxin.getConsigneeTel());
		oprmentDetail.setCusOptions("");
		oprmentDetail.setServiceName(user.get("name").toString());
		oprmentDetail.setRemark("");
		oprmentDetail.setOprInformAppointment(new OprInformAppointment(oprment.getId(),oprment.getDno()));
		oprmentDetail.setInpaymentcollection(faxin.getPaymentCollection());
		oprmentDetail.setInformType(informType);
		oprmentDetail.setInformTime(dt);
		oprmentDetail.setInformResult(informResult);
		oprmentDetail.setDeliveryFee(faxin.getConsigneeFee());
		oprmentDetail.setCusRequest(faxin.getRemark());
		oprmentDetail.setCusName(faxin.getConsignee());
		
		this.oprHistoryService.saveLog(dno, informType+" 通知，通知"+(informResult==1l?"成功":"失败"), (informResult==1l?this.log_informSuccess:this.log_informFailure));
		
		//向通知预约明细表中添加一条记录
		this.oprInformAppointmentDetailService.save(oprmentDetail);//save
		
		
		OprStatus oprstatus=this.oprStatusService.findBy("dno", dno).get(0);
		
		if(informResult==1){//通知成功
			oprstatus.setNotifyStatus(new Long(1));
		}else{
			oprstatus.setNotifyStatus(new Long(2));
		}
		oprstatus.setNotifyTime(dt);
		//修改状态表中的通知状态
		this.oprStatusService.save(oprstatus);
		
	}

	@ModuleName(value="通知预约查询语句拼接",logType=LogType.buss)
	public String getResultMapQueryService(Map<String, String> map) throws Exception{
		StringBuffer sb=new StringBuffer();
		String reachStatus= map.get("reachStatus");
		String EQS_customerService=map.get("EQS_customerService");
		String EQS_distributionMode=map.get("EQS_distributionMode");
		String EQS_takeMode=map.get("EQS_takeMode");
		String LIKES_consignee=map.get("LIKES_consignee");
		
		String LIKES_consigneeTel=map.get("LIKES_consigneeTel");
		String checkItems=map.get("checkItems");
		String itemsValue=map.get("itemsValue");
		//String requestStage=map.get("requestStage");
		String EQS_notifyStatus=map.get("EQS_notifyStatus");
		
		String GED_flightDate=map.get("GED_flightDate");
		String LED_flightDate=map.get("LED_flightDate");
		String EQL_dno=map.get("EQL_dno");
		String printNum = map.get("printNum");
		//FIXED SQL需要优化 -- 修改了一些小地方
		sb.append("SELECT  f.CUS_ID  AS CUSID , f.D_NO  AS DNO , f.CP_NAME  AS ")
		.append(" CPNAME , f.FLIGHT_NO  AS FLIGHTNO , to_char(f.FLIGHT_DATE,'yyyy-MM-dd ')||f.FLIGHT_TIME  AS")
		.append(" FLIGHTDATE , f.FLIGHT_TIME  AS FLIGHTTIME , f.TRAFFIC_MODE  AS  ")
		.append(" TRAFFICMODE , f.FLIGHT_MAIN_NO  AS FLIGHTMAINNO , f.SUB_NO  AS ")
		.append(" SUBNO , f.DISTRIBUTION_MODE  AS DISTRIBUTIONMODE , f.TAKE_MODE  AS ")
		.append(" TAKEMODE , f.RECEIPT_TYPE  AS RECEIPTTYPE , f.CONSIGNEE  AS")
		.append(" CONSIGNEE , f.CONSIGNEE_TEL  AS CONSIGNEETEL , f.CITY  AS CITY ,")
		.append(" f.TOWN  AS TOWN , f.ADDR  AS ADDR , f.PIECE  AS PIECE ,")
		.append(" f.CUS_WEIGHT  AS CUSWEIGHT , f.BULK  AS BULK , f.CP_RATE  AS")
		.append(" CPRATE , f.CP_FEE  AS CPFEE , f.CONSIGNEE_RATE  AS")
		.append(" CONSIGNEERATE , nvl(f.CONSIGNEE_FEE,0)  AS CONSIGNEEFEE,nvl(f.CP_VALUE_ADD_FEE,0) as CPVALUEADDFEE,")
		.append(" f.CUSTOMER_SERVICE  AS CUSTOMERSERVICE , f.AREA_TYPE  AS AREATYPE ,")
		.append(" t2.INFORM_NUM  AS INFORMNUM , t4.REQUEST,")
		.append(" f.GOODS_STATUS  AS GOODSSTATUS , t2.LAST_INFORM_RESULT  AS ")
		.append(" LASTINFORMRESULT , t2.LAST_INFORM_CUS  AS LASTINFORMCUS ,")
		.append(" (nvl(f.CUS_VALUE_ADD_FEE,0)+nvl(f.PAYMENT_COLLECTION,0)+nvl(f.CONSIGNEE_FEE,0)) as countFee,")
		.append(" t2.LAST_SERVICE_NAME  AS LASTSERVICENAME , t2.LAST_INFORM_TIME  AS")
		.append(" LASTINFORMTIME , t6.NOTIFY_STATUS  AS NOTIFYSTATUS ,")
		.append(" t6.REACH_STATUS  AS REACHSTATUS , nvl(f.PAYMENT_COLLECTION,0)  AS")
		.append(" PAYMENTCOLLECTION ,t5.PIECE  AS")
		.append(" STOCKPIECE , t2.ID  AS ID , t2.TS  AS TS , f.END_DEPART_ID  AS")
		.append(" ENDDEPARTID , t6.OUT_STATUS  AS OUTSTATUS , f.CUS_VALUE_ADD_FEE AS CUSVALUEADDFEE,")
		.append(" f.remark as faxRemark,od.node_color as nodeColor,r.print_num ")
		.append(" FROM  opr_node od, OPR_FAX_IN f , OPR_INFORM_APPOINTMENT t2 ,")
		.append(" (select t.d_no,WMSYS.wm_concat(t.REQUEST) REQUEST from aisuser.OPR_REQUEST_DO t where t.request_stage=:requestStage group by t.d_no) t4,")
		.append(" OPR_STOCK t5 , OPR_STATUS t6,opr_receipt r")
		.append(" WHERE f.D_NO  =  t2.D_NO(+)   AND   f.D_NO  =  t4.D_NO(+) and f.d_no=r.d_no")
		.append(" and f.D_NO = t5.D_NO(+) AND f.D_NO  =  t6.D_NO  and f.GOODS_STATUS=od.node_name(+) ");
		
		sb.append(" and f.END_DEPART_ID =:bussDepart and t5.DEPART_ID(+)=:bussDepart");
		sb.append(" and f.status=1 ");//未传真作废
		sb.append(" AND (t6.OUT_STATUS = 0 or  t6.OUT_STATUS=3) ");//未出库的货
		//sb.append(" and reachStatus = :reachStatus");//已点到
		if(null!=EQL_dno && EQL_dno.length()>0){
			sb.append(" and f.d_no=:EQL_dno");
		}else{
			if(null!=itemsValue && !"".equals(itemsValue)){
				if(null!=checkItems && ("dno".equals(checkItems))){
					sb.append(" AND "+ checkItems+" =  :itemsValue");
				}else if(null!=checkItems && !"".equals(checkItems)){
					sb.append(" AND "+ checkItems+" like  '%'||:itemsValue").append("||'%'");
				}
			}
			if(null!=printNum && !"".equals(printNum)){
				if(Long.valueOf(printNum)==0l){
					sb.append(" AND  (r.PRINT_NUM =0 or r.PRINT_NUM is null)");
				}else if(Long.valueOf(printNum)>0l){
					sb.append(" AND  r.PRINT_NUM  >=:printNum");
				}
			}
			if(null!=reachStatus && !"".equals(reachStatus.trim())){
				if(Long.valueOf(reachStatus)==0l){
					sb.append(" AND  (t6.reach_status =0 or t6.reach_status is null)");
				}else if(Long.valueOf(reachStatus)==1l){ 
					sb.append(" AND  t6.reach_status >=:reachStatus");
				}
			}
			if(null!=EQS_customerService && !"".equals(EQS_customerService)){
				sb.append(" AND   f.CUSTOMER_SERVICE  =  :EQS_customerService ");
			}
			if(null!=EQS_distributionMode && !"".equals(EQS_distributionMode)){
				sb.append(" AND   f.DISTRIBUTION_MODE  =  :EQS_distributionMode ");
			}
			if(null!=EQS_takeMode && !"".equals(EQS_takeMode)){
				sb.append(" AND   f.take_Mode  =  :EQS_takeMode ");
			}
			if(null!=LIKES_consignee && !"".equals(LIKES_consignee.trim())){
				sb.append(" AND   f.consignee  like  '%' ||:LIKES_consignee ||'%'");
			}
			if(null!=LIKES_consigneeTel && !"".equals(LIKES_consigneeTel.trim())){
				sb.append(" AND   f.consignee_Tel  like '%' ||:LIKES_consigneeTel || '%' ");
			}
			if(null!=EQS_notifyStatus && !"".equals(EQS_notifyStatus)){
				sb.append(" AND   t6.NOTIFY_STATUS  =  :EQS_notifyStatus ");
			}

			if(null!=GED_flightDate && null!=LED_flightDate){
				if(GED_flightDate.length()>8 && LED_flightDate.length()==0){
					sb.append(" AND  f.FLIGHT_DATE  >= to_date(:GED_flightDate,'yyyy-MM-dd')");
				}else if(GED_flightDate.length()>8 && LED_flightDate.length()>8){
					sb.append(" AND  f.FLIGHT_DATE  >= to_date(:GED_flightDate,'yyyy-MM-dd')");
					sb.append(" AND  f.FLIGHT_DATE <= to_date(:LED_flightDate,'yyyy-MM-dd')");
				}else if(GED_flightDate.length()==0 && LED_flightDate.length()>8){
					sb.append(" AND  f.FLIGHT_DATE  <= to_date(:LED_flightDate,'yyyy-MM-dd')+1-0.0001");
				} 
			}
		}
		// System.out.println(sb.toString());
		return sb.toString();
	}

}
