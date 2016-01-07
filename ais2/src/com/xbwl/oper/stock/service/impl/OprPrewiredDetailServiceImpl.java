package com.xbwl.oper.stock.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.log.anno.ModuleName;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.common.utils.LogType;
import com.xbwl.entity.OprFaxIn;
import com.xbwl.entity.OprPrewired;
import com.xbwl.entity.OprPrewiredDetail;
import com.xbwl.entity.OprRequestDo;
import com.xbwl.entity.OprStatus;
import com.xbwl.oper.fax.dao.IOprFaxInDao;
import com.xbwl.oper.reports.util.AppendConditions;
import com.xbwl.oper.stock.dao.IOprPrewiredDao;
import com.xbwl.oper.stock.dao.IOprPrewiredDetailDao;
import com.xbwl.oper.stock.dao.IOprRequestDoDao;
import com.xbwl.oper.stock.dao.IOprStatusDao;
import com.xbwl.oper.stock.service.IOprHistoryService;
import com.xbwl.oper.stock.service.IOprPrewiredDetailService;

/**
 * author shuw
 * time 2011-7-19 上午11:21:05
 */                
@Service("oprPrewiredDetailServiceImpl")
@Transactional(rollbackFor={Exception.class})
public class OprPrewiredDetailServiceImpl  extends BaseServiceImpl<OprPrewiredDetail, Long> 
					implements IOprPrewiredDetailService{

	@Resource(name="oprPrewiredDetailHibernateDaoImpl")
	private IOprPrewiredDetailDao oprPrewiredDetailDao;

	@Resource(name = "oprFaxInHibernateDaoImpl")
	private IOprFaxInDao oprFaxInDao;

	@Resource(name="oprPrewiredHibernateDaoImpl")
	private IOprPrewiredDao oprPrewiredDao;
	
	@Resource(name = "oprRequestDoHibernateDaoImpl")
	private IOprRequestDoDao oprRequestDoDao;
	
	@Resource(name = "oprStatusHibernateDaoImpl")
	private IOprStatusDao oprStatusDao;
	
	@Value("${oprPrewiredDetailServiceImpl.log_beforeCargo}")
	private Long log_beforeCargo ;
	
	@Resource(name = "oprHistoryServiceImpl")
	private IOprHistoryService oprHistoryService;
	
	@Resource(name="appendConditions")
	private AppendConditions appendConditions;
	
	@Override
	public IBaseDAO<OprPrewiredDetail, Long> getBaseDao() {
		return oprPrewiredDetailDao;
	}
	
	@ModuleName(value="货物预配",logType=LogType.buss)
	public Long saveOprPrewiredByids(OprPrewired oprPrewired, List<Long> ids,String s,String orderFields) throws Exception {
			Set<OprPrewiredDetail> oprPSet  =   oprPrewired.getOprPrewiredDetails();
			for(Long id : ids){
				OprFaxIn oprFaxIn = oprFaxInDao.get(id);
				if(oprFaxIn==null||(oprFaxIn.getStatus()!=null&&oprFaxIn.getStatus()==0)){
						throw new ServiceException("配送单号<"+id+">的货物不存在！");
				}
				OprRequestDo  oprRequestDo =oprRequestDoDao.getOprRequestDoByDnoAndStage(s, id);
				
				OprPrewiredDetail oprPrewiredDetail = new OprPrewiredDetail();
				oprPrewiredDetail.setDNo(oprFaxIn.getDno());
				
				List<OprStatus> list =oprStatusDao.find("from OprStatus o where o.dno=? ", oprFaxIn.getDno());
				if(list.size()==0||list.size()>1){
					throw new ServiceException("查不到或者查到多条货物的状态数据");
				}
				if("部门交接".equals(oprPrewired.getAutostowMode())){
					OprStatus entity = list.get(0);
					if(entity.getDepartOvermemoStatus()!=null&&entity.getDepartOvermemoStatus()==3){
						throw new ServiceException("货物已预配，不能多次预配");
					}
					entity.setDepartOvermemoStatus(3l);
					oprStatusDao.save(entity);   //部门交接时，修改部门交接状态，不修改其他状态。
				}else{
					OprStatus entity = list.get(0);
					if(entity.getOutStatus()!=null){
						if(entity.getOutStatus()==3l||entity.getOutStatus()==1l){
							throw new ServiceException("货物出库状态或预配状态已改变，无法预配");
						}
					}
					entity.setOutStatus(3l);
					oprStatusDao.save(entity);        // 出库状态变成3，表示已经预配
				}
				
				oprPrewiredDetail.setPiece(oprFaxIn.getPiece());
				oprPrewiredDetail.setWeight(oprFaxIn.getCusWeight());
				oprPrewiredDetail.setConsignee(oprFaxIn.getConsignee());
				oprPrewiredDetail.setAddr(oprFaxIn.getAddr());
				oprPrewiredDetail.setGowhere(oprFaxIn.getGowhere());
				oprPrewiredDetail.setFlightNo(oprFaxIn.getFlightNo());
				oprPrewiredDetail.setRequest(oprRequestDo.getRequest());
				oprPrewiredDetail.setOprPrewired(oprPrewired);
				oprPSet.add(oprPrewiredDetail);
				oprPrewiredDetail.setOprPrewired(oprPrewired);
			}
			oprPrewired.setOprPrewiredDetails(oprPSet);
			oprPrewired.setStatus(1l);
			oprPrewired.setOrderField(orderFields);
			oprPrewiredDao.save(oprPrewired);  

			for(OprPrewiredDetail oprPrewiredDetail:oprPrewired.getOprPrewiredDetails()){
				oprHistoryService.saveLog(oprPrewiredDetail.getDNo(), oprPrewired.getAutostowMode()+"预配，预配单号："+oprPrewired.getId() , log_beforeCargo);
			}
			
			return oprPrewired.getId();
	}

	@ModuleName(value="信息汇总",logType=LogType.buss)
	public String getAjaxTotalSum(String  ids) throws Exception {
		StringBuffer sb = new StringBuffer();
		if(!"".equals(ids)&&ids!=null){
			if(ids.endsWith(",")){
				ids = ids.substring(0,ids.length()-1);
			}
			sb.append("select     count(t0.D_NO) as t0_D_NO,sum(t0.PIECE) as t0_PIECE,sum(t1.PIECE) as t1_PIECE ,sum(t0.CUS_WEIGHT) as t0_CUS_WEIGHT  " );
			sb.append("FROM  aisuser.OPR_FAX_IN t0 , aisuser.OPR_STOCK t1 	");
			sb.append("WHERE  t0.D_NO  =  t1.D_NO(+)    	");
			sb.append(" AND   t0.status  =  1 	");
			sb.append("  and  t1.DEPART_ID(+)  = :departId    	");
			sb.append("and  t0.D_NO in (  "+ids+"  ) 	");
		}
		return sb.toString();
	}
	
	@ModuleName(value="预配SQL拼接",logType=LogType.buss)
	public String getListSqlAll(Map map)  throws Exception{
		String area = (String)map.get("f_area");
 		String ids = (String)map.get("ids");
 		String distributionMode = (String)map.get("distributionMode");
 		String[] sts = new String[]{"f_area","ids","requestStage","bussDepart","distributionMode"};
		StringBuffer sb = new StringBuffer();
		sb.append("  SELECT f.TAKE_MODE t0_take_mode, f.D_NO  AS t0_D_NO ,f.wait ,f.SONDERZUG AS t0_SONDERZUG , f.CONSIGNEE_TEL  AS t0_CONSIGNEE_TEL ,f.GOWHERE AS  t0_GOWHERE ,f.CUR_DEPART  AS  t0_CUR_DEPART,f.CITY  AS t0_CITY ,f.CREATE_TIME  AS t0_CREATE_TIME ,  	");
				sb.append(" f.IN_DEPART AS IN_DEPART, f.GOODS_STATUS  AS t0_GOODS_STATUS ,  f.FLIGHT_MAIN_NO  AS  FLIGHT_MAIN_NO,	f.cur_depart as T0_CUR_DEPART_NAME,  ");
				sb.append(" f.CONSIGNEE  AS t0_CONSIGNEE , f.ADDR  AS t0_ADDR , f.FLIGHT_DATE  AS  t0_FLIGHT_DATE ,   f.remark as t0_remark,f.cp_name as t0_cpname,	");
				sb.append(" f.FLIGHT_NO,f.IN_DEPART_ID as IN_DEPART_ID, f.PIECE  AS t0_PIECE , f.GOODS  AS t0_GOODS ,f.end_depart  AS t0_end_depart , f.end_depart_id as t0_end_depart_id,	");  
				sb.append(" f.CQ_WEIGHT  AS t0_CUS_WEIGHT , s.PIECE  AS t1_PIECE , f.TOWN  AS   	");
				sb.append(" t0_TOWN , t4.REQUEST  AS t4_REQUEST,t4.request_stage as t4_REQUEST_STAGE 	 ");
			    sb.append(" FROM  aisuser.OPR_FAX_IN f , aisuser.OPR_STOCK s ,  (   	");
				sb.append(" select ai.d_no,WMSYS.wm_concat(ai.request) AS request ,");
				sb.append(" ai.request_stage from   aisuser.OPR_REQUEST_DO ai");
				sb.append(" where ai.request_stage = :requestStage group by   ai.d_no,ai.request_stage )  t4 ,aisuser.OPR_Status  t5 	");
			    sb.append(" WHERE f.D_NO  =  s.D_NO(+) and s.depart_id(+)=:bussDepart");
				sb.append(" AND f.D_NO  =  t4.D_NO(+)");
				sb.append(" AND f.status =1");
				sb.append(" AND f.D_NO  = t5.D_No ");
				sb.append(" AND (t5.OUT_STATUS =0 or t5.OUT_STATUS=2)");
			
		if(null!=distributionMode && "部门交接".equals(distributionMode.trim())){
			sb.append(" and t5.DEPART_OVERMEMO_STATUS=0 ");
			sb.append(" AND (f.CUR_DEPART_ID = :bussDepart or s.PIECE >0)");
			sb.append(" AND f.end_depart_id <> :bussDepart");
		}
		sb.append(this.appendConditions.appendConditions(map, sts));
		sb.append(this.appendConditions.appendCountDate(map));
		if(!"".equals(area)&&null!=area){
			sb.append(" AND f.CITY||f.TOWN  like '%'||:f_area||'%'");
		}
		if(!"".equals(ids)&&ids!=null){
			if(ids.endsWith(",")){
				ids = ids.substring(0,ids.length()-1);
			}
			sb.append(" AND   f.D_NO  not in  ("+ids+") ");
		}
		//按照点到时间倒序排序
		sb.append(" order by t5.REACH_TIME desc");
		
		return sb.toString();
	}

	public String findOutCarList(Map<String, String> map) throws Exception {
		
		StringBuffer sb = new StringBuffer();
		String checkItems = map.get("checkItems");
		String itemsValue = map.get("itemsValue");
		String GED_createTime = map.get("GED_createTime");
		String LED_createTime = map.get("LED_createTime");
		String EQL_id = map.get("EQL_id");
		
		sb.append("select t.id,t.start_depart_id,t.end_depart_id,t.start_time,t.end_time,t.unload_start_time,t.unload_end_time,")
		  .append("t.overmemo_type,t.car_id,t.car_code,t.status,t.remark,t.create_time,t.create_name,t.update_time,t.update_name,")
		  .append("t.ts,t.orderfields,t.total_ticket,t.total_piece,t.total_weight,t.end_depart_name From opr_overmemo t");
		sb.append(" where t.start_time is null");
		
		if(null!=EQL_id && !"".equals(EQL_id.trim())){
			sb.append(" and id=:EQL_id");
		}else{
			if(null!=checkItems && !"".equals(checkItems.trim())){
				if(null!=itemsValue && !"".equals(itemsValue.trim())){
					sb.append(" and ").append(checkItems).append(" LIKE '%'||:itemsValue||'%'");
				}
			}
			if(null!=GED_createTime && !"".equals(GED_createTime) && AppendConditions.isDateType(GED_createTime)){
				sb.append(" and t.create_time >= to_date(:GED_createTime,'yyyy-MM-dd')");
			}
			if(null!=LED_createTime && !"".equals(LED_createTime) && AppendConditions.isDateType(LED_createTime)){
				sb.append(" and t.create_time <= to_date(:LED_createTime,'yyyy-MM-dd')+1-0.0001");
			}
		}
		return sb.toString();
	}
}
