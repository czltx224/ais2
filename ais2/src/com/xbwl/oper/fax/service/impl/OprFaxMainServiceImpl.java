package com.xbwl.oper.fax.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sun.util.BuddhistCalendar;

import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.entity.OprFaxMain;
import com.xbwl.oper.fax.dao.IOprFaxMainDao;
import com.xbwl.oper.fax.service.IOprFaxMainService;

/**
 * @author CaoZhili time Aug 29, 2011 10:30:35 AM
 */
@Service("oprFaxMainServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class OprFaxMainServiceImpl extends BaseServiceImpl<OprFaxMain, Long>
		implements IOprFaxMainService {

	@Resource(name="oprFaxMainHibernateDaoImpl")
	private IOprFaxMainDao oprFaxMainDao;
	
	@Override
	public IBaseDAO<OprFaxMain, Long> getBaseDao() {

		return this.oprFaxMainDao;
	}

	// ÷π§∆•≈‰
	public String findFiDeliverycost(Map map) throws Exception{
		Date createTime_end = (Date)map.get("endDate");
		Date createTime_start = (Date)map.get("startDate");
		String flightMainNo=(String)map.get("flightMainNo");
		Long type=(Long)map.get("type");
		String flightNo=(String)map.get("flightNo");
		Long piece=(Long)map.get("piece");
		Double weight=(Double)map.get("weight");
		Long xvalue=(Long)map.get("xvalue");
		
		StringBuffer sb = new StringBuffer();
		sb.append("select  t0_FLIGHT_MAIN_NO,t1_FLIGHT_NO,t1_FLIGHT_DATE,t1_CP_NAME,t2_CUS_NAME,ID, ");
				sb.append("total_piece,total_weight,real_weight,ts,depart_name,create_time,startCity  ");
		sb.append("from ( ");
			sb.append("SELECT  min(t3.START_CITY)  startCity,     "); 
						sb.append("t0.FLIGHT_MAIN_NO  AS t0_FLIGHT_MAIN_NO ,  WMSYS.wm_concat(t1.FLIGHT_NO)  AS t1_FLIGHT_NO ,   ");
						sb.append(" WMSYS.wm_concat( to_char(t1.FLIGHT_DATE,'YYYY-MM-DD HH24:MI:SS') )   AS t1_FLIGHT_DATE , WMSYS.wm_concat( t1.CP_NAME)   AS t1_CP_NAME , ");
						sb.append(" WMSYS.wm_concat( t2.CUS_NAME)  AS t2_CUS_NAME     ");
			sb.append("FROM  OPR_FAX_MAIN t0 , OPR_FAX_IN t1 , CUSTOMER t2 ,   ");
					    sb.append("    BAS_FLIGHT t3 ");
			sb.append(" WHERE  (  t0.MATCH_STATUS =0 and t0.depart_id=:bussDepart and  t0.FLIGHT_MAIN_NO  =  t1.FLIGHT_MAIN_NO   AND   t1.FLIGHT_NO  =   ");
						sb.append("  t3.FLIGHT_NUMBER   AND   t3.CUS_ID  =  t2.ID   )    ");
						sb.append(" group by t0.FLIGHT_MAIN_NO ) f,opr_fax_main m ");
		sb.append("where f.t0_FLIGHT_MAIN_NO=m.flight_main_no ");
		
		if(type==null){
			if(piece==null&&weight==null){
				if(createTime_start==null&&createTime_end!=null){
					sb.append(" AND  create_time  < :endDate ");
				}else if(createTime_start!=null&&createTime_end==null){
					sb.append(" AND  create_time  < sysdate ");
					sb.append(" AND  create_time >  :startDate ");
				}else if(createTime_start!=null&&createTime_end!=null){
					sb.append(" AND  create_time  >  :startDate");
					sb.append(" AND  create_time <  :endDate ");
				}else{			
				}
				if(!"".equals(flightMainNo)&&null!=flightMainNo){
					sb.append(" AND  t0_FLIGHT_MAIN_NO  =  :flightMainNo  ");
				}
			}else{
				if (xvalue==null) {
					sb.append(" AND ( t0_FLIGHT_MAIN_NO = :flightMainNo ");
					sb.append(" OR total_piece = :piece ");
					sb.append(" OR  total_weight = :weight ) ");
				}else if(xvalue==0l){
					sb.append(" AND ( t0_FLIGHT_MAIN_NO like :flightMainNo ");
					sb.append(" OR total_piece = :piece ");
					sb.append(" OR  total_weight = :weight ) ");
				}else{
					sb.append(" AND ( t0_FLIGHT_MAIN_NO like :flightMainNo ");
					sb.append(" OR (:piece1<= total_piece  AND  total_piece <= :piece2) ");
					sb.append(" OR  (:weight1<=total_weight AND total_weight<= :weight2 ) ) ");
				}
			}
		}else{
			if(!"".equals(flightMainNo)&&null!=flightMainNo){
					sb.append(" AND t0_FLIGHT_MAIN_NO like :flightMainNo ");
			}
			if(!"".equals(flightNo)&&null!=flightNo){
				   sb.append(" AND t1_FLIGHT_NO like :flightNo ");
			}
			if(null!=piece&&piece!=0l){
				   sb.append(" AND   total_piece = :piece ");
			}
			if(null!=weight&&weight!=0l){
				   sb.append(" AND   total_weight = :weight ");
			}
		}
		return sb.toString();
	}
	
	public OprFaxMain getTotalByFlightMainNo(String flightMainNo)throws Exception{
		String sql="select new com.xbwl.entity.OprFaxMain(sum(f.piece) as totalPiece,sum(f.cusWeight) as totalWeight) from OprFaxIn f where f.flightMainNo=?";
		List<OprFaxMain> list = this.oprFaxMainDao.createQuery(sql, flightMainNo).list();
		
		if(null!=list && list.size()>0){
			
			return list.get(0);
		}else{
			return null;
		}
	}
	
	public OprFaxMain getOprFaxMainByFlightMainNo(String flightMainNo)throws Exception{
		String sql="from OprFaxMain ofm where ofm.flightMainNo=? order by ofm.createTime desc";
		List<OprFaxMain> list=this.oprFaxMainDao.find(sql,flightMainNo);
		if(null!=list && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

	public OprFaxMain findFiDeliveryByMatchStatus(String flightMainNo) throws Exception {
		User user= WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		Long bussDepartId=Long.parseLong(user.get("bussDepart")+"");
		List<OprFaxMain>list=oprFaxMainDao.find("from OprFaxMain fm where fm.flightMainNo=? and fm.departId=? and fm.matchStatus=0 ",flightMainNo,bussDepartId);

		if(null!=list && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

}
