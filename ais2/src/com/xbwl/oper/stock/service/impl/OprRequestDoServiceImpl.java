package com.xbwl.oper.stock.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.entity.OprRequestDo;
import com.xbwl.oper.stock.dao.IOprRequestDoDao;
import com.xbwl.oper.stock.service.IOprRequestDoService;

/**
 * author CaoZhili time Jul 12, 2011 10:23:47 AM
 * 
 * 个性化要求服务层实现类
 */
@Service("oprRequestDoServiceImpl")
@Transactional(rollbackFor={Exception.class})
public class OprRequestDoServiceImpl extends
		BaseServiceImpl<OprRequestDo, Long> implements IOprRequestDoService {

	@Resource(name = "oprRequestDoHibernateDaoImpl")
	private IOprRequestDoDao oprRequestDoDao;

	@Override
	public IBaseDAO<OprRequestDo, Long> getBaseDao() {
		return this.oprRequestDoDao;
	}

	public String getSqlRalaListService(Map<String, String> filterParamMap)
			throws Exception {
		String dno = filterParamMap.get("dno");
		String flightMainNo =  filterParamMap.get("flightMainNo");
		String isOpr =  filterParamMap.get("isOpr");
		String checkItems = filterParamMap.get("checkItems");
		String itemsValue  = filterParamMap.get("itemsValue");
		
		String startTime="";
 		String endTime="";
		Iterator itr =filterParamMap.keySet().iterator();
 		String time=null;
 		String searchTime=null;
 		while(itr.hasNext()){
 			time=itr.next().toString();
 			if(time.indexOf("time")>0 || time.indexOf("date")>0){
 				time=time.substring(time.indexOf("_")+1);
 				searchTime=time;
 				startTime="GED_"+time;
 				endTime="LED_"+time;
 			}
 		}
		StringBuffer sb = new StringBuffer();
		//REVIEW SQL优化，嵌套查询导致性能问题 -- 不知从何着手--找有经验的人着手。
 		sb.append("select * from (select f.cus_id ,f.cp_name,f.flight_main_no,f.flight_no,f.sub_no,f.tra_fee,f.cp_fee,")
 		    .append("f.consignee_fee,f.cus_value_add_fee,f.cp_value_add_fee,f.SONDERZUG_PRICE,")
 		    .append(" to_char(f.flight_date,'yyyy-MM-dd hh24:mm:ss'),to_char(f.create_time,'yyyy-MM-dd hh24:mm:ss')")
 		    .append(" as fax_create_time,f.consignee,f.consignee_tel,")
 		    .append("f.piece,f.cus_weight,f.bulk,")
 		    .append(" r.id,r.request_stage,r.request,r.opr_man,r.is_opr,r.request_type,r.create_name,")
 		    .append(" r.remark,to_char(r.create_time,'yyyy-MM-dd hh24:mm:ss') as create_time,r.update_name,")
 		    .append("to_char(r.update_time,'yyyy-MM-dd hh24:mm:ss') as update_time,r.ts,r.d_no,r.is_exception")
 		    .append(" from opr_request_do r,");
 		
 	   sb.append("opr_fax_in f where r.d_no=f.d_no(+) ");
 	   
 	    if(null!=dno && !"".equals(dno)){
			sb.append(" and r.d_no =:dno");
		}
		if(null!=isOpr && !"".equals(isOpr)){
			sb.append(" and r.is_opr =:isOpr");
		}

		if(null!=flightMainNo && !"".equals(flightMainNo)){
			sb.append(" and flight_Main_No =:flightMainNo");
		}
		
		if(null!=checkItems && !"".equals(checkItems) && !"%%".equals(checkItems)){
			if(null!=itemsValue && !"%%".equals(itemsValue)){
					sb.append(" AND "+ checkItems+" like  :itemsValue");
			}
		}
		sb.append(") where 1=1") ;
		if(startTime.trim().length()>5 && endTime.trim().length()>5){
				
				String time1 = filterParamMap.get(startTime).toString();
				String time2=filterParamMap.get(endTime).toString();
				
				if(time1.length()>8 && time2.length()==0){
					sb.append(" AND  "+searchTime+"  >=  to_date(:"+startTime+",'yyyy-MM-dd')");
				}else if(time1.length()>8 && time2.length()>8){
					sb.append(" AND  "+searchTime+"  >=  to_date(:"+startTime+",'yyyy-MM-dd')");
					sb.append(" AND  "+searchTime+" <=  to_date(:"+endTime+",'yyyy-MM-dd')");
				}else if(time1.length()==0 && time2.length()>8){
					sb.append(" AND  "+searchTime+"  <= to_date(:"+endTime+",'yyyy-MM-dd')");
				} 
			}
		sb.append(" order by d_no desc,create_Time desc");
		return sb.toString();
	}

	public List<OprRequestDo> getRequestByDno(Long dno) throws Exception {
		return this.findBy("dno", dno);
	}
}
