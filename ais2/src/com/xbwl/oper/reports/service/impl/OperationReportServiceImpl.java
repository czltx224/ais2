package com.xbwl.oper.reports.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.log.anno.ModuleName;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.common.utils.LogType;
import com.xbwl.entity.OprLoadingbrigadeWeight;
import com.xbwl.oper.reports.service.IOperationReportService;
import com.xbwl.oper.reports.util.AppendConditions;
import com.xbwl.oper.stock.dao.IOprLoadingbrigadeWeightDao;

/**
 * 运作报表统一服务层实现类
 * author CaoZhili time Sep 30, 2011 9:41:27 AM
 */
@Service("operationReportServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class OperationReportServiceImpl extends
		BaseServiceImpl<OprLoadingbrigadeWeight, Long> implements
		IOperationReportService {

	@Resource(name="oprLoadingbrigadeWeightHibernateDaoImpl")
	private IOprLoadingbrigadeWeightDao oprLoadingbrigadeWeightDao;
	
	final int firstWeightNum=200;
	final int weightCountNum=100;
	
	@Resource(name="appendConditions")
	private AppendConditions appendConditions;
	
	@Override
	public IBaseDAO<OprLoadingbrigadeWeight, Long> getBaseDao() {
		
		return this.oprLoadingbrigadeWeightDao;
	}

	/**送货货量拼接查询语句方法
	 * @param map
	 * @param countName
	 * @param countSum
	 * @param countCol
	 * @param sign
	 * @return
	 * @throws Exception
	 */
	private String addSendGoods(Map<String, String> map,String countName,String countSum,String countCol,String sign)throws Exception{
		Calendar cal=Calendar.getInstance();
		int count = Integer.parseInt(map.get("count"));
		int countNum = Integer.parseInt(map.get("countNum"));
		String countRange =map.get("countRange");
		String startCount =map.get("startCount");
		String dateFormat = map.get("dateFormat");
		String[] sts = new String[]{"takeMode","countNum","count","dateFormat"};
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		StringBuffer sb = new StringBuffer();
		sb.append("select DEPART_ID,fname,depart_name,sum(sumcol) sumcol");//,sum(col1)col1 from (
		
		if(countRange.equals("日")){
			cal.setTime(sdf.parse(startCount));
			for(int i=1;i<=count;i++){
				String yy = cal.get(Calendar.YEAR)+"";
				String dd = cal.get(Calendar.DATE)<10?"0"+cal.get(Calendar.DATE):cal.get(Calendar.DATE)+"";
				String mm = cal.get(Calendar.MONTH)+1<10?"0"+(cal.get(Calendar.MONTH)+1):(cal.get(Calendar.MONTH)+1)+"";
				sb.append(",sum(col"+i+")")
				  .append(" \"").append(yy).append("-").append(mm).append("-").append(dd).append("\"");
				
				cal.set(Calendar.DATE, cal.get(Calendar.DATE)+1);
			}
		}else if(countRange.equals("月")){
			sdf = new SimpleDateFormat("yyyy-MM");
			cal.setTime(sdf.parse(startCount));
			for(int i=1;i<=count;i++){
				String yy = cal.get(Calendar.YEAR)+"";
				String mm = cal.get(Calendar.MONTH)+1<10?"0"+(cal.get(Calendar.MONTH)+1):(cal.get(Calendar.MONTH)+1)+"";
				sb.append(",sum(col"+i+")")
				  .append(" \"").append(yy).append("-").append(mm).append("\"");
				cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)+1);
			}
		}else if(countRange.equals("周") || countRange.equals("年")){
			countNum=Integer.parseInt(startCount);
			for(int i=1;i<=count;i++){
				sb.append(",sum(col"+i+")")
				  .append(" \"").append(countNum).append(countRange).append("\"");
				countNum++;
			}
		}
//		for(int i=1;i<=count;i++){
//			sb.append(",sum(col"+i+") col").append(i);
//		}
		sb.append(" from (");
		sb.append(" select d.DEPART_ID,d.depart_name,decode(1,1,'"+countName+"','"+countName+"') fname,")
		    .append(countSum+" sumcol");
//		for(int i=1;i<=count;i++){
//			sb.append(",sum(decode(to_date(to_char(t.create_time,'"+dateFormat+"'),'"+dateFormat+"'),")
//			  .append("to_date('"+strdate+"-"+i+"','"+dateFormat+"'),"+countCol+", 0))"+sign+" col").append(i);
//		}
		
		if(countRange.equals("日")){
			sdf = new SimpleDateFormat("yyyy-MM-dd");
			cal.setTime(sdf.parse(startCount));
			 for(int i=1;i<=count;i++){
				 sb.append(",trunc(sum(decode(to_date(to_char(t.create_time,'"+dateFormat+"'),'"+dateFormat+"'),")
				  .append("to_date('"+sdf.format(cal.getTime())+"','"+dateFormat+"'),"+countCol+", 0))"+sign+",2) col").append(i);
				cal.set(Calendar.DATE, cal.get(Calendar.DATE)+1);
			 }
		}else if(countRange.equals("月")){
			sdf = new SimpleDateFormat("yyyy-MM");
			cal.setTime(sdf.parse(startCount));
			 for(int i=1;i<=count;i++){
				 sb.append(",trunc(sum(decode(to_date(to_char(t.create_time,'"+dateFormat+"'),'"+dateFormat+"'),")
				  .append("to_date('"+sdf.format(cal.getTime())+"','"+dateFormat+"'),"+countCol+", 0))"+sign+",2) col").append(i);
				
				cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)+1);
			 }
		}else if(countRange.equals("周") || countRange.equals("年")){
			countNum=Integer.parseInt(startCount);
			for(int i=1;i<=count;i++){
				sb.append(",trunc(sum(decode(round(to_char(t.create_time,'"+dateFormat+"')),"+countNum+", "+countCol+",0))"+sign+",2) col").append(i);
				countNum++;
			}
		}
		
		sb.append(" from opr_overmemo t,opr_overmemo_detail t2,sys_depart d,opr_fax_in f")
		  .append(" where t.start_depart_id=d.depart_id")
		  .append(" and t.id =t2.overmemo")
		  .append(" and t2.d_no=f.d_no(+)");
		
		sb.append(this.appendConditions.appendCountDate(map));
		sb.append(this.appendConditions.appendConditions(map, sts));
		
		sb.append(" group by d.DEPART_ID,d.depart_name,to_char(t.create_time,'"+dateFormat+"')")
		    .append(" ) group by DEPART_ID,depart_name,fname");
		
		return sb.toString();
	}
	
	@ModuleName(value="送货货量查询SQL获取",logType=LogType.buss)
	public String getSendGoodsService(Map<String, String> map)
			throws Exception {
		StringBuffer sb = new StringBuffer();
		String countRange =map.get("countRange");
		String startCount =map.get("startCount");
		String endCount =map.get("endCount");
		
		int  count=1;
		int countNum=0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date1=new Date();
		Date date2=new Date();
		Calendar cal=Calendar.getInstance();
		String dateFormat="";
		
		if(countRange.equals("日")){
			dateFormat="yyyy-MM-dd";
			if(null==startCount || "".equals(startCount)){
				date1=sdf.parse(cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH))+"-"+cal.get(Calendar.DATE));
			}else{
				date1 = sdf.parse(startCount);	
			}
			if(null!=endCount || !"".equals(endCount)){
				date2 = sdf.parse(endCount);
			}
			long l=date2.getTime()-date1.getTime(); 
			
			count=this.appendConditions.panduan(date1, date2, countRange, 60);
		}else if(countRange.equals("月")){
			dateFormat="yyyy-MM";
			sdf = new SimpleDateFormat("yyyy-MM");
			if(null==startCount || "".equals(startCount)){
				date1=sdf.parse(cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)));
			}else{
				date1 = sdf.parse(startCount);	
			}
			if(null!=endCount || !"".equals(endCount)){
				date2 = sdf.parse(endCount);
			}
			count=this.appendConditions.panduan(date1, date2, countRange, 12);
		}else if(countRange.equals("周")){
			dateFormat="WW";
			try{
				count=Integer.parseInt(endCount)+1-Integer.parseInt(startCount);
				if(count<0){
					throw new ServiceException("周数输入异常！");
				}
			}catch (Exception e) {
				throw new ServiceException("周数输入异常！");
			}
			if(count>20){
				throw new ServiceException("按周统计不能超过20周！");
			}
			
			countNum=Integer.parseInt(startCount);
		}else if(countRange.equals("年")){
			dateFormat="yyyy";
			count=Integer.parseInt(endCount)+1-Integer.parseInt(startCount);
			if(count>10){
				throw new ServiceException("按年统计不能超过10个年！");
			}else if(count<1){
				throw new ServiceException("年份不存在负数！");
			}
			countNum=Integer.parseInt(startCount);
		}
		
		map.put("countNum", countNum+"");
		map.put("count", count+"");
		map.put("dateFormat", dateFormat);
		
		sb.append("select *from (");
		
		String countName="总重量";
		String countSum="sum(nvl(t2.weight,0))";
		String countCol="nvl(t2.weight,0)";
		String signString="";
		//统计总重量
		sb.append(this.addSendGoods(map, countName,countSum,countCol,signString));
		
		sb.append(" union all ");
		countName="总票数";
		countSum="count(t.TOTAL_TICKET)";
		countCol="t.TOTAL_TICKET";
		signString="";
		//统计总票数
		sb.append(this.addSendGoods(map, countName,countSum,countCol,signString));

		sb.append(" union all ");
		countName="总送货收入";
		countSum="sum(nvl(f.cp_value_add_fee,0)+nvl(f.cp_fee,0)+nvl(f.consignee_fee,0)+nvl(f.cus_value_add_fee,0))";
		countCol="nvl(f.cp_value_add_fee,0)+nvl(f.cp_fee,0)+nvl(f.consignee_fee,0)+nvl(f.cus_value_add_fee,0)";
		signString="";
		//统计总收入
		sb.append(this.addSendGoods(map, countName,countSum,countCol,signString));
		
	    sb.append(" union all ");
	    countName="折合票数";
	    countCol="case when t2.weight <= "+firstWeightNum+" then 1 when t2.weight > "+firstWeightNum+" " +
	    		"then (case when substr((t2.weight - "+firstWeightNum+") / "+weightCountNum+" - floor((t2.weight - "+firstWeightNum+") / "+weightCountNum+"),2,1) > 5" +
	    		" then (trunc((t2.weight - "+firstWeightNum+") / "+weightCountNum+") + 2) when substr((t2.weight - "+firstWeightNum+") / "+weightCountNum+" - floor((t2.weight - "+firstWeightNum+") / "+weightCountNum+")," +
	    		"2,1) < 5 then (trunc((t2.weight - "+firstWeightNum+") / "+weightCountNum+") + 1.5)  else 1 end) else 1 end";
	    countSum ="sum("+countCol+")";
	  //统计折合票数
	    sb.append(this.addSendGoods(map, countName,countSum,countCol,signString));
	    
		sb.append(" union all ");
		countName="送货人次";
		countSum="count(*)";
		countCol="1";
		signString="";//有待调整，先滞后
		//统计送货人次
		sb.append(this.addSendGoods(map, countName,countSum,countCol,signString));
		
		sb.append(" union all ");
		countName="人均送货票数";
		countSum="trunc(sum(TOTAL_TICKET)/count(distinct t.end_depart_name),2)";
		countCol="1";
		signString="/count(distinct t.end_depart_name)";
		//统计人均送货票数
		sb.append(this.addSendGoods(map, countName,countSum,countCol,signString));
		
		sb.append(" union all ");
		countName="人均折合票数";
		countCol="case when t2.weight <= "+firstWeightNum+" then 1 when t2.weight > "+firstWeightNum+" " +
	 		"then (case when substr((t2.weight - "+firstWeightNum+") / "+weightCountNum+" - floor((t2.weight - "+firstWeightNum+") / "+weightCountNum+"),2,1) > 5" +
	 		" then (trunc((t2.weight - "+firstWeightNum+") / "+weightCountNum+") + 2) when substr((t2.weight - "+firstWeightNum+") / "+weightCountNum+" - floor((t2.weight - "+firstWeightNum+") / "+weightCountNum+")," +
	 		"2,1) < 5 then (trunc((t2.weight - "+firstWeightNum+") / "+weightCountNum+") + 1.5)  else 1 end) else 1 end";
		countSum="trunc(sum("+countCol+")/count(*),2)";
		signString="/count(*)";
		//统计人均折合票数
		sb.append(this.addSendGoods(map, countName,countSum,countCol,signString));
		
		sb.append(" union all ");
		countName="人均送货重量";
		countSum="trunc(sum(t.TOTAL_WEIGHT)/count(*),2)";
		countCol="t.TOTAL_WEIGHT";
		signString="/count(*)";
		//统计人均送货重量
		sb.append(this.addSendGoods(map, countName,countSum,countCol,signString));
		
		sb.append(") where 1=1");//添加打印条件
		
		//System.out.println(sb.length());
		return sb.toString();
	}

	@ModuleName(value="卸货时效查询SQL获取",logType=LogType.buss)
	public String getUnloadingTimeListService(Map<String, String> map)
			throws Exception {
		//appendConditions
		StringBuffer sb = new StringBuffer();
		String countRange =map.get("countRange");
		String startCount =map.get("startCount");
		String endCount =map.get("endCount");

		int  count=1;
		int countNum=0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date1=new Date();
		Date date2=new Date();
		Calendar cal=Calendar.getInstance();
		String dateFormat="";
		
		if(countRange.equals("日")){
			dateFormat="yyyy-MM-dd";
			if(null==startCount || "".equals(startCount)){
				date1=sdf.parse(cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH))+"-"+cal.get(Calendar.DATE));
			}else{
				date1 = sdf.parse(startCount);	
			}
			if(null!=endCount || !"".equals(endCount)){
				date2 = sdf.parse(endCount);
			}
			long l=date2.getTime()-date1.getTime(); 
			
			count=this.appendConditions.panduan(date1, date2, countRange, 60);
		}else if(countRange.equals("月")){
			dateFormat="yyyy-MM";
			sdf = new SimpleDateFormat("yyyy-MM");
			if(null==startCount || "".equals(startCount)){
				date1=sdf.parse(cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)));
			}else{
				date1 = sdf.parse(startCount);	
			}
			if(null!=endCount || !"".equals(endCount)){
				date2 = sdf.parse(endCount);
			}
			count=this.appendConditions.panduan(date1, date2, countRange, 12);
		}else if(countRange.equals("周")){
			dateFormat="WW";
			try{
				count=Integer.parseInt(endCount)+1-Integer.parseInt(startCount);
				if(count<0){
					throw new ServiceException("周数输入异常！");
				}
			}catch (Exception e) {
				throw new ServiceException("周数输入异常！");
			}
			if(count>20){
				throw new ServiceException("按周统计不能超过20周！");
			}
			
			countNum=Integer.parseInt(startCount);
		}else if(countRange.equals("年")){
			dateFormat="yyyy";
			count=Integer.parseInt(endCount)+1-Integer.parseInt(startCount);
			if(count>10){
				throw new ServiceException("按年统计不能超过10个年！");
			}else if(count<1){
				throw new ServiceException("年份不存在负数！");
			}
			countNum=Integer.parseInt(startCount);
		}
		map.put("countNum", countNum+"");
		map.put("count", count+"");
		map.put("dateFormat", dateFormat);
		String[] sts = new String[]{"loadingName","countNum","count","dateFormat"};
		sb.append("select *from (");
		String caseString=" case when t.status>1 and t.unload_end_time-t.unload_start_time<(select u.unloading_standard_time"
			+" from opr_unloading_standard u where u.car_type=(select TYPE_CODE from bas_car where id= t.car_id) and u.depart_id=:endDepartId) then '卸车及时台数' else '不及时台数' end";
		
		sb.append(appendUnLoading(map, caseString));
		sb.append(" union all ");
		caseString =" case when  t.status>=1 then '到达车辆台数' else '其他' end";
		sb.append(appendUnLoading(map, caseString));
		
		sb.append(" )where 1=1");
		sb.append(this.appendConditions.appendConditions(map, sts));
	   return sb.toString();
	}
	
	private String appendUnLoading(Map<String, String> map,String caseString)throws Exception{
		StringBuffer sb = new StringBuffer();
		Calendar cal=Calendar.getInstance();
		int count = Integer.parseInt(map.get("count"));
		int countNum = Integer.parseInt(map.get("countNum"));
		String countRange =map.get("countRange");
		String startCount =map.get("startCount");
		String dateFormat = map.get("dateFormat");
		String loadingName = map.get("loadingName");//装卸组
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		sb.append("select end_depart_id,depart_name,oname,sum(taishu) taishu");//sum(col1) col1 from (")
		if(countRange.equals("日")){
			sdf = new SimpleDateFormat("yyyy-MM-dd");
			cal.setTime(sdf.parse(startCount));
			for(int i=1;i<=count;i++){
				String yy = cal.get(Calendar.YEAR)+"";
				String dd = cal.get(Calendar.DATE)<10?"0"+cal.get(Calendar.DATE):cal.get(Calendar.DATE)+"";
				String mm = cal.get(Calendar.MONTH)+1<10?"0"+(cal.get(Calendar.MONTH)+1):(cal.get(Calendar.MONTH)+1)+"";
				sb.append(",sum(col"+i+")")
				  .append(" \"").append(yy).append("-").append(mm).append("-").append(dd).append("\"");
				cal.set(Calendar.DATE, cal.get(Calendar.DATE)+1);
			}
		}else if(countRange.equals("月")){
			sdf = new SimpleDateFormat("yyyy-MM");
			cal.setTime(sdf.parse(startCount));
			for(int i=1;i<=count;i++){
				String yy = cal.get(Calendar.YEAR)+"";
				String mm = cal.get(Calendar.MONTH)+1<10?"0"+(cal.get(Calendar.MONTH)+1):(cal.get(Calendar.MONTH)+1)+"";
				sb.append(",sum(col"+i+")")
				  .append(" \"").append(yy).append("-").append(mm).append("\"");
				cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)+1);
			}
		}else if(countRange.equals("周") || countRange.equals("年")){
			countNum=Integer.parseInt(startCount);
			for(int i=1;i<=count;i++){
				sb.append(",sum(col"+i+")")
				  .append(" \"").append(countNum).append(countRange).append("\"");
				countNum++;
			}
		}
			
		sb.append(" from (");
		sb.append(" select t.end_depart_id,depart_name,loading_name,")
		  .append(caseString)
		  .append(" as oname ,count(*) as taishu");
		if(countRange.equals("日")){
			cal.setTime(sdf.parse(startCount));
			 for(int i=1;i<=count;i++){
				sb.append(",decode(to_char(to_date(to_char(t.end_time,'"+dateFormat+"'),'"+dateFormat+"'),'"+dateFormat+"'),");
				sb.append(" to_char(to_date('"+sdf.format(cal.getTime())+"','"+dateFormat+"'),'"+dateFormat+"'),count(*), 0) col").append(i);
				
				cal.set(Calendar.DATE, cal.get(Calendar.DATE)+1);
			 }
		}else if(countRange.equals("月")){
			cal.setTime(sdf.parse(startCount));
			 for(int i=1;i<=count;i++){
				sb.append(",decode(to_date(to_char(t.end_time,'"+dateFormat+"'),'"+dateFormat+"'),");
				sb.append(" to_date('"+sdf.format(cal.getTime())+"','"+dateFormat+"'),count(*), 0) col").append(i);
				
				cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)+1);
			 }
		}else if(countRange.equals("周") || countRange.equals("年")){
			countNum=Integer.parseInt(startCount);
			for(int i=1;i<=count;i++){
				sb.append(",decode(round(to_char(t.end_time,'"+dateFormat+"')),"+countNum+", count(*),0) col").append(i);
				countNum++;
			}
		}
	   
	   sb .append("  from opr_overmemo t,sys_depart d,")
	      .append(" bas_loadingbrigade l,opr_loadingbrigade_weight w")
	      .append(" where 1=1 and t.end_depart_id=d.depart_id  and t.id=w.overmemo_no(+) and w.loadingbrigade_id=l.id(+)");
	   sb.append(" and t.status>=1");
	   sb.append(this.appendConditions.appendCountDate(map));
	   if(null!=loadingName && !"".equals(loadingName)){
		   sb.append(" and loading_Name =:loadingName");
	   }
	   sb.append("  group by t.end_depart_id,depart_name,car_id,t.status,t.unload_end_time-t.unload_start_time,to_char(t.end_time,'"+dateFormat+"')");
	   sb.append(" ,loading_name) where 1=1");
	   
	   sb.append(" group by oname,end_depart_id,depart_name");
	
	   return sb.toString();
	}
	
	@ModuleName(value="卸货时效明细查询SQL获取",logType=LogType.buss)
	public String getUnloadingTimeDetailListService(Map<String, String> map)
			throws Exception {
		String EQL_endDepartId = map.get("EQL_endDepartId");
		String loadingName = map.get("loadingName");
		String flag = map.get("flag");
		StringBuffer sb = new StringBuffer();
		sb.append("select to_char(t.unload_start_time,'yyyy-MM-dd') as create_Time,t.car_id,c.car_code,t.total_weight,to_char(t.unload_start_time,'hh24:mi')  as unload_start_time,")
		    .append("to_char(t.unload_end_time,'hh24:mi')  as unload_end_time,trunc((t.unload_end_time-t.unload_start_time)*24*60,2) as trueTime,u.unloading_standard_time,")
		    .append("unloading_standard_time-(t.unload_end_time-t.unload_start_time) as flagTime,case when unloading_standard_time-(t.unload_end_time-t.unload_start_time)>0 then 1 else 0 end as flag, l.loadingbrigade_id loadingbrigadeid,bd.loading_name loadingname")
		    .append(" from opr_overmemo t,opr_unloading_standard u,bas_car c,")
		    .append(" opr_loadingbrigade_weight l,bas_loadingbrigade bd")
		    .append(" where u.car_type(+)=c.type_code and c.id(+)=t.car_id")
		    .append(" and t.id=l.overmemo_no(+)  and l.loadingbrigade_id=bd.id(+)")
		    .append(" and t.status>=1");
		    
		    if(null!=EQL_endDepartId && !"".equals(EQL_endDepartId)){
				   sb.append(" and  t.end_depart_id=:EQL_endDepartId");
				   sb.append(" and u.depart_id(+)=:EQL_endDepartId");
			  }
		    if(null!=loadingName && !"".equals(loadingName)){
				   sb.append(" and  loading_Name=:loadingName");
			  }
		    if(null!=flag && !"".equals(flag)){
		    	if(flag.equals("1")){
				    sb.append(" and  unloading_standard_time-(t.unload_end_time-t.unload_start_time)>0");
		    	}else {
		    		sb.append(" and  nvl(unloading_standard_time-(t.unload_end_time-t.unload_start_time),0)<=0");
		    	}
			 }
		sb.append(this.appendConditions.appendCountDate(map));
		sb.append(" order by t.create_time desc ,t.unload_start_time desc");
		return sb.toString();
	}

	@ModuleName(value="送货货量明细SQL获取",logType=LogType.buss)
	public String getSendGoodsDetailService(Map<String, String> map)
			throws Exception {
		String[] sts = new String[]{"countDate"};
		StringBuffer sb =new StringBuffer();
		sb.append("select riqi,end_depart_name,sum(ampiece)ampiece,sum(pmpiece)pmpiece,sum(amcountpiece)amcountpiece,sum(pmcountpiece)pmcountpiece,")
		    .append(" sum(amweight)amweight,sum(pmweight)pmweight,unload_start_time,unload_end_time,")
		    .append(" sum(qianshou) qianshou,sum(amqiandan)amqiandan,sum(pmqiandan)pmqiandan,")
		    .append(" sum(amshouru)amshouru,sum(pmshouru)pmshouru,sum(return_num) returnNum,sum(stop_fee) STOP_FEE,use_car_type from (")
		    .append(" select  t2.id,to_char(t.create_time, 'yyyy-MM-dd') as riqi,t.end_depart_name,")
		    .append(" case when to_char(t.create_time,'hh24')<=12 then count(*) end ampiece,")
		    .append(" case when to_char(t.create_time,'hh24')>12 then count(*) end pmpiece,")
		    .append(" case when to_char(t.create_time,'hh24')<=12 then ")
		    .append(" case  when t2.weight<="+firstWeightNum+" then count(*) when t2.weight>"+firstWeightNum+" then  ")
		    .append(" case ")
		    //计算折合票数(200KG以内包括200算是一票，往后每一百公斤算是一票，不足一百但是多余50的算是一票，少于50的算是半票)
		    .append(" when ((t2.weight-"+firstWeightNum+")/"+weightCountNum+" +1)-substr(to_char(((t2.weight-"+firstWeightNum+")/"+weightCountNum+" +1)),0, instr(to_char(((t2.weight-"+firstWeightNum+")/"+weightCountNum+" +1)),'.')-1)>0.5 then substr(to_char(((t2.weight-"+firstWeightNum+")/"+weightCountNum+" +1)),0, instr(to_char(((t2.weight-"+firstWeightNum+")/"+weightCountNum+" +1)),'.')-1)+1")
		    .append(" when ((t2.weight-"+firstWeightNum+")/"+weightCountNum+" +1)-substr(to_char(((t2.weight-"+firstWeightNum+")/"+weightCountNum+" +1)),0, instr(to_char(((t2.weight-"+firstWeightNum+")/"+weightCountNum+" +1)),'.')-1)<0.5 then substr(to_char(((t2.weight-"+firstWeightNum+")/"+weightCountNum+" +1)),0, instr(to_char(((t2.weight-"+firstWeightNum+")/"+weightCountNum+" +1)),'.')-1)+0.5")
		    .append(" else ((t2.weight-"+firstWeightNum+")/"+weightCountNum+" +1)")
		    .append(" end else count(*)")
		    .append(" end end amcountpiece,")
		    .append(" case when to_char(t.create_time,'hh24')>12 then ")
		    .append(" case  when t2.weight<="+firstWeightNum+" then count(*) when t2.weight>"+firstWeightNum+" then  ")
		    .append(" case  ")
		    .append(" when ((t2.weight-"+firstWeightNum+")/"+weightCountNum+" +1)-substr(to_char(((t2.weight-"+firstWeightNum+")/"+weightCountNum+" +1)),0, instr(to_char(((t2.weight-"+firstWeightNum+")/"+weightCountNum+" +1)),'.')-1)>0.5 then substr(to_char(((t2.weight-"+firstWeightNum+")/"+weightCountNum+" +1)),0, instr(to_char(((t2.weight-"+firstWeightNum+")/"+weightCountNum+" +1)),'.')-1)+1")
		    .append(" when ((t2.weight-"+firstWeightNum+")/"+weightCountNum+" +1)-substr(to_char(((t2.weight-"+firstWeightNum+")/"+weightCountNum+" +1)),0, instr(to_char(((t2.weight-"+firstWeightNum+")/"+weightCountNum+" +1)),'.')-1)<0.5 then substr(to_char(((t2.weight-"+firstWeightNum+")/"+weightCountNum+" +1)),0, instr(to_char(((t2.weight-"+firstWeightNum+")/"+weightCountNum+" +1)),'.')-1)+0.5")
		    .append(" else ((t2.weight-"+firstWeightNum+")/"+weightCountNum+" +1)")
		    .append(" end else count(*) end")
		    .append("  end pmcountpiece,")
		    .append(" case when to_char(t.create_time,'hh24')<=12 then sum(t2.weight) end amweight,")
		    .append(" case when to_char(t.create_time,'hh24')>12 then sum(t2.weight) end pmweight,")
		    .append(" to_char(t.unload_start_time,'hh24:mi') unload_start_time,")
		    .append(" to_char(t.unload_end_time,'hh24:mi') unload_end_time,")
		    .append(" case when s.sign_status=1 then  count(*) end qianshou,")   
		    .append(" case when  to_char(t.create_time,'hh24')<=12  then  sum(SIGN_FEE) end amqiandan,")
            .append(" case when   to_char(t.create_time,'hh24')>12  then  sum(SIGN_FEE) end pmqiandan,")
		    .append(" case when to_char(t.create_time,'hh24')<=12 then (f.cp_value_add_fee+f.cp_fee+f.consignee_fee+f.cus_value_add_fee) end amshouru,")//计算收入
		    .append(" case when to_char(t.create_time,'hh24')>12 then (f.cp_value_add_fee+f.cp_fee+f.consignee_fee+f.cus_value_add_fee) end pmshouru,")//计算收入
		    .append(" sum(r.return_num) return_num,si.stop_fee,si.use_car_type")
		    .append(" from opr_overmemo t,opr_overmemo_detail t2,opr_fax_in f,opr_return_goods r,opr_status s,opr_sign_route si")
		    .append(" where t2.overmemo=t.id(+) ")
		    .append(" and t2.d_no=f.d_no(+)")
		    .append(" and t2.d_no=r.d_no(+)")
		    .append(" and t2.d_no=s.d_no(+)")
		    .append(" and t.car_id=si.car_id(+)");
		
			sb.append(this.appendConditions.appendConditions(map, sts));
			
			sb.append(this.appendConditions.appendCountDate(map));
		   sb.append(" group by t2.id,end_depart_name,unload_start_time,unload_end_time,stop_fee,si.use_car_type,end_depart_name,")
		    .append(" f.cp_value_add_fee+f.cp_fee+f.consignee_fee+f.cus_value_add_fee,sign_status,")
		    .append(" t.create_time,")
		    .append(" t2.weight,to_char(t.create_time, 'yyyy-MM-dd')")
		    .append(" ) where 1=1");
		
		
		
		sb.append(" group by riqi,end_depart_name,unload_start_time,unload_end_time,stop_fee,use_car_type");
		
		return sb.toString();
	}

	@ModuleName(value="送货货量明细汇总SQL获取",logType=LogType.buss)
	public String findSendGoodsDetailCount(Map<String, String> map)
			throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("select sum(ampiece)ampiece,sum(pmpiece)pmpiece,sum(amcountpiece)amcountpiece,sum(pmcountpiece)pmcountpiece,")
		  .append(" sum(amweight)amweight,sum(pmweight)pmweight,")
		  .append(" sum(qianshou) qianshou,sum(amqiandan)amqiandan,sum(pmqiandan)pmqiandan,")
		  .append(" sum(amshouru)amshouru,sum(pmshouru)pmshouru,sum(returnNum) returnNum,sum(stop_fee) stop_fee from (");
		sb.append(getSendGoodsDetailService(map));
		sb.append(" ) where 1=1");
		
		return sb.toString();
	}

	@ModuleName(value="送货盈利查询SQL获取",logType=LogType.buss)
	public String findSendGoodsProfitsService(Map<String, String> map)
			throws Exception {
		StringBuffer sb = new StringBuffer();
		String countRange =map.get("countRange");
		String startCount =map.get("startCount");
		String endCount =map.get("endCount");
		String[] sts = new String[]{"countDate","f.takeMode","feeName","overmemoType"};
		
		String takeMode = map.get("f.takeMode");
		String feeName = map.get("feeName");
		String overmemoType = map.get("overmemoType");
		
		int  count=1;
		int countNum=0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date1=new Date();
		Date date2=new Date();
		Calendar cal=Calendar.getInstance();
		String dateFormat="";
		
		if(countRange.equals("日")){
			dateFormat="yyyy-MM-dd";
			if(null==startCount || "".equals(startCount)){
				date1=sdf.parse(cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH))+"-"+cal.get(Calendar.DATE));
			}else{
				date1 = sdf.parse(startCount);	
			}
			if(null!=endCount || !"".equals(endCount)){
				date2 = sdf.parse(endCount);
			}
			long l=date2.getTime()-date1.getTime(); 
			
			count=this.appendConditions.panduan(date1, date2, countRange, 60);
		}else if(countRange.equals("月")){
			dateFormat="yyyy-MM";
			sdf = new SimpleDateFormat("yyyy-MM");
			if(null==startCount || "".equals(startCount)){
				date1=sdf.parse(cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)));
			}else{
				date1 = sdf.parse(startCount);	
			}
			if(null!=endCount || !"".equals(endCount)){
				date2 = sdf.parse(endCount);
			}
			count=this.appendConditions.panduan(date1, date2, countRange, 12);
		}else if(countRange.equals("周")){
			dateFormat="WW";
			try{
				count=Integer.parseInt(endCount)+1-Integer.parseInt(startCount);
				if(count<0){
					throw new ServiceException("周数输入异常！");
				}
			}catch (Exception e) {
				throw new ServiceException("周数输入异常！");
			}
			if(count>20){
				throw new ServiceException("按周统计不能超过20周！");
			}
			
			countNum=Integer.parseInt(startCount);
		}else if(countRange.equals("年")){
			dateFormat="yyyy";
			count=Integer.parseInt(endCount)+1-Integer.parseInt(startCount);
			if(count>10){
				throw new ServiceException("按年统计不能超过10个年！");
			}else if(count<1){
				throw new ServiceException("年份不存在负数！");
			}
			countNum=Integer.parseInt(startCount);
		}
		
		sb.append("select fname, sum(nvl(sumcol,0)) sumcol,endDepart,endDepartId");
		
		if(countRange.equals("日")){
			sdf = new SimpleDateFormat("yyyy-MM-dd");
			cal.setTime(sdf.parse(startCount));
			for(int i=1;i<=count;i++){
				String yy = cal.get(Calendar.YEAR)+"";
				String dd = cal.get(Calendar.DATE)<10?"0"+cal.get(Calendar.DATE):cal.get(Calendar.DATE)+"";
				String mm = cal.get(Calendar.MONTH)+1<10?"0"+(cal.get(Calendar.MONTH)+1):(cal.get(Calendar.MONTH)+1)+"";
				sb.append(",sum(nvl(col"+i+",0))")
				  .append(" \"").append(yy).append("-").append(mm).append("-").append(dd).append("\"");
				cal.set(Calendar.DATE, cal.get(Calendar.DATE)+1);
			}
		}else if(countRange.equals("月")){
			sdf = new SimpleDateFormat("yyyy-MM");
			cal.setTime(sdf.parse(startCount));
			for(int i=1;i<=count;i++){
				String yy = cal.get(Calendar.YEAR)+"";
				String mm = cal.get(Calendar.MONTH)+1<10?"0"+(cal.get(Calendar.MONTH)+1):(cal.get(Calendar.MONTH)+1)+"";
				sb.append(",sum(col"+i+")")
				  .append(" \"").append(yy).append("-").append(mm).append("\"");
				cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)+1);
			}
		}else if(countRange.equals("周") || countRange.equals("年")){
			countNum=Integer.parseInt(startCount);
			for(int i=1;i<=count;i++){
				sb.append(",sum(col"+i+")")
				  .append(" \"").append(countNum).append(countRange).append("\"");
				countNum++;
			}
		}
		sb.append(" from (");
		sb.append("select decode(1, 1, '送货费', '送货费') fname,f.end_depart endDepart,f.end_depart_id endDepartId,")
		  .append("trunc(sum(nvl(f.consignee_fee,0) + nvl(f.payment_collection,0)), 2) sumcol");
       
		if(countRange.equals("日")){
			sdf = new SimpleDateFormat("yyyy-MM-dd");
			cal.setTime(sdf.parse(startCount));
			 for(int i=1;i<=count;i++){
				 sb.append(",trunc(sum(decode(to_date(to_char(o.create_time,'"+dateFormat+"'),'"+dateFormat+"'),")
				  .append("to_date('"+sdf.format(cal.getTime())+"','"+dateFormat+"'),nvl(f.consignee_fee,0) + nvl(f.payment_collection,0), 0)),2) col").append(i);
				cal.set(Calendar.DATE, cal.get(Calendar.DATE)+1);
			 }
		}else if(countRange.equals("月")){
			sdf = new SimpleDateFormat("yyyy-MM");
			cal.setTime(sdf.parse(startCount));
			 for(int i=1;i<=count;i++){
				 sb.append(",trunc(sum(decode(to_date(to_char(o.create_time,'"+dateFormat+"'),'"+dateFormat+"'),")
				  .append("to_date('"+sdf.format(cal.getTime())+"','"+dateFormat+"'),nvl(f.consignee_fee,0) + nvl(f.payment_collection,0), 0)),2) col").append(i);
				
				cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)+1);
			 }
		}else if(countRange.equals("周") || countRange.equals("年")){
			countNum=Integer.parseInt(startCount);
			for(int i=1;i<=count;i++){
				sb.append(",trunc(sum(decode(round(to_char(o.create_time,'"+dateFormat+"')),"+countNum+",nvl(f.consignee_fee,0) + nvl(f.payment_collection,0),0)),2) col").append(i);
				countNum++;
			}
		}
		sb.append(" from opr_fax_in f,opr_overmemo o,opr_overmemo_detail od,opr_sign_route t");
		sb.append(" where od.d_no=f.d_no and od.overmemo=o.id and o.car_code=t.car_no(+)");
		if(null!=takeMode && !"".equals(takeMode)){
			sb.append(" and f.take_mode=:f.takeMode");
		}
		if(null!=overmemoType && !"".equals(overmemoType)){
			sb.append(" and o.overmemo_type=:overmemoType");
		}
		map.put("countCheckItems", "o.create_time");
		sb.append(this.appendConditions.appendCountDate(map));
		sb.append(" group by f.end_depart,f.end_depart_id");
		
		sb.append(" union all ");
		sb.append("select decode(1, 1, '签单费', '签单费') fname,t.depart_name endDepart,t.depart_id endDepartId,")
		  .append("sum(decode(t.use_car_type,'临时外租车',nvl(t.sign_fee,0)+nvl(t.toll_charge_total,0),nvl(t.toll_charge_total,0))) sumcol");
		if(countRange.equals("日")){
			sdf = new SimpleDateFormat("yyyy-MM-dd");
			cal.setTime(sdf.parse(startCount));
			 for(int i=1;i<=count;i++){
				 sb.append(",sum(decode(to_date(to_char(t.create_time,'"+dateFormat+"'),'"+dateFormat+"'),")
				  .append("to_date('"+sdf.format(cal.getTime())+"','"+dateFormat+"'),decode(t.use_car_type,'临时外租车',nvl(t.sign_fee,0)+nvl(t.toll_charge_total,0),nvl(t.toll_charge_total,0)), 0)) col").append(i);
				cal.set(Calendar.DATE, cal.get(Calendar.DATE)+1);
			 }
		}else if(countRange.equals("月")){
			sdf = new SimpleDateFormat("yyyy-MM");
			cal.setTime(sdf.parse(startCount));
			 for(int i=1;i<=count;i++){
				 sb.append(",sum(decode(to_date(to_char(t.create_time,'"+dateFormat+"'),'"+dateFormat+"'),")
				  .append("to_date('"+sdf.format(cal.getTime())+"','"+dateFormat+"'),decode(t.use_car_type,'临时外租车',nvl(t.sign_fee,0)+nvl(t.toll_charge_total,0),nvl(t.toll_charge_total,0)), 0)) col").append(i);
				
				cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)+1);
			 }
		}else if(countRange.equals("周") || countRange.equals("年")){
			countNum=Integer.parseInt(startCount);
			for(int i=1;i<=count;i++){
				sb.append(",sum(decode(round(to_char(t.create_time,'"+dateFormat+"')),")
				  .append(countNum)
				  .append(",decode(t.use_car_type,'临时外租车',nvl(t.sign_fee,0)+nvl(t.toll_charge_total,0),nvl(t.toll_charge_total,0)),0)) col")
				  .append(i);
				countNum++;
			}
		}
		sb.append(" from opr_sign_route t where 1=1");
		map.put("countCheckItems", "t.create_time");
		sb.append(this.appendConditions.appendCountDate(map));
		sb.append(" group by t.depart_id,t.depart_name");
		
		sb.append(" union all ");
		sb.append("select decode(1, 1, '劳务费', '劳务费') fname,f.end_depart,f.end_depart_id,")
		  .append("sum(nvl(v.fee_count,0)) sumcol");
		if(countRange.equals("日")){
			sdf = new SimpleDateFormat("yyyy-MM-dd");
			cal.setTime(sdf.parse(startCount));
			 for(int i=1;i<=count;i++){
				 sb.append(",sum(decode(to_date(to_char(o.create_time,'"+dateFormat+"'),'"+dateFormat+"'),")
				  .append("to_date('"+sdf.format(cal.getTime())+"','"+dateFormat+"'),nvl(v.fee_count,0),0)) col").append(i);
				cal.set(Calendar.DATE, cal.get(Calendar.DATE)+1);
			 }
		}else if(countRange.equals("月")){
			sdf = new SimpleDateFormat("yyyy-MM");
			cal.setTime(sdf.parse(startCount));
			 for(int i=1;i<=count;i++){
				 sb.append(",sum(decode(to_date(to_char(o.create_time,'"+dateFormat+"'),'"+dateFormat+"'),")
				  .append("to_date('"+sdf.format(cal.getTime())+"','"+dateFormat+"'),nvl(v.fee_count,0), 0)) col").append(i);
				
				cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)+1);
			 }
		}else if(countRange.equals("周") || countRange.equals("年")){
			countNum=Integer.parseInt(startCount);
			for(int i=1;i<=count;i++){
				sb.append(",sum(decode(round(to_char(o.create_time,'"+dateFormat+"')),")
				  .append(countNum)
				  .append(",nvl(v.fee_count,0),0)) col")
				  .append(i);
				countNum++;
			}
		}
		sb.append(" from opr_fax_in f,opr_overmemo o,opr_overmemo_detail od,opr_sign_route t,opr_value_add_fee v");
		sb.append(" where od.d_no = f.d_no and od.overmemo = o.id and o.car_code = t.car_no(+) and od.d_no=v.d_no(+)");
		if(null!=takeMode && !"".equals(takeMode)){
			sb.append(" and f.take_mode=:f.takeMode");
		}
		if(null!=overmemoType && !"".equals(overmemoType)){
			sb.append(" and o.overmemo_type=:overmemoType");
		}
		if(null!=feeName && !"".equals(feeName)){
			sb.append(" and v.fee_name(+)=:feeName");
		}
		sb.append(this.appendConditions.appendCountDate(map));
		sb.append(" group by f.end_depart,f.end_depart_id");
		
		sb.append(" union all ");
		sb.append("select decode(1, 1, '送货盈利', '送货盈利') fname,f.end_depart,f.end_depart_id,")
		  .append("trunc(sum(nvl(f.consignee_fee,0) + nvl(f.payment_collection,0)-decode(t.use_car_type, '临时外租车', nvl(t.sign_fee,0) + nvl(t.toll_charge_total,0), nvl(t.toll_charge_total,0))-nvl(v.fee_count,0)), 2) sumcol");
		if(countRange.equals("日")){
			sdf = new SimpleDateFormat("yyyy-MM-dd");
			cal.setTime(sdf.parse(startCount));
			 for(int i=1;i<=count;i++){
				 sb.append(",sum(decode(to_date(to_char(o.create_time,'"+dateFormat+"'),'"+dateFormat+"'),")
				  .append("to_date('"+sdf.format(cal.getTime())+"','"+dateFormat+"'),nvl(f.consignee_fee,0) + nvl(f.payment_collection,0)-decode(t.use_car_type, '临时外租车', nvl(t.sign_fee,0) + nvl(t.toll_charge_total,0), nvl(t.toll_charge_total,0))-nvl(v.fee_count,0),0)) col").append(i);
				cal.set(Calendar.DATE, cal.get(Calendar.DATE)+1);
			 }
		}else if(countRange.equals("月")){
			sdf = new SimpleDateFormat("yyyy-MM");
			cal.setTime(sdf.parse(startCount));
			 for(int i=1;i<=count;i++){
				 sb.append(",sum(decode(to_date(to_char(o.create_time,'"+dateFormat+"'),'"+dateFormat+"'),")
				  .append("to_date('"+sdf.format(cal.getTime())+"','"+dateFormat+"'),nvl(f.consignee_fee,0) + nvl(f.payment_collection,0)-decode(t.use_car_type, '临时外租车', nvl(t.sign_fee,0) + nvl(t.toll_charge_total,0), nvl(t.toll_charge_total,0))-nvl(v.fee_count,0), 0)) col").append(i);
				
				cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)+1);
			 }
		}else if(countRange.equals("周") || countRange.equals("年")){
			countNum=Integer.parseInt(startCount);
			for(int i=1;i<=count;i++){
				sb.append(",sum(decode(round(to_char(o.create_time,'"+dateFormat+"')),")
				  .append(countNum)
				  .append(",nvl(f.consignee_fee,0) + nvl(f.payment_collection,0)-decode(t.use_car_type, '临时外租车', nvl(t.sign_fee,0) + nvl(t.toll_charge_total,0), nvl(t.toll_charge_total,0))-nvl(v.fee_count,0),0)) col")
				  .append(i);
				countNum++;
			}
		}
		sb.append(" from opr_fax_in f,opr_overmemo o,opr_overmemo_detail od,opr_sign_route t,opr_value_add_fee v");
		sb.append(" where od.d_no = f.d_no and od.overmemo = o.id and o.car_code = t.car_no(+) and od.d_no=v.d_no(+)");
		if(null!=takeMode && !"".equals(takeMode)){
			sb.append(" and f.take_mode=:f.takeMode");
		}
		if(null!=overmemoType && !"".equals(overmemoType)){
			sb.append(" and o.overmemo_type=:overmemoType");
		}
		if(null!=feeName && !"".equals(feeName)){
			sb.append(" and v.fee_name(+)=:feeName");
		}
		map.put("countCheckItems", "o.create_time");
		sb.append(this.appendConditions.appendCountDate(map));
		sb.append(" group by f.end_depart,f.end_depart_id");
		
		sb.append(" ) where 1=1");
		sb.append(this.appendConditions.appendConditions(map, sts));
		sb.append(" group by fname,enddepart,enddepartid");
		return sb.toString();
	}

	@ModuleName(value="中转汇总统计查询SQL获取",logType=LogType.buss)
	public String findTransitCountService(Map<String, String> map) throws Exception {
		
		String[] sts = new String[]{"distributionMode","faxStatus","fiStatus"};
		StringBuffer sb = new StringBuffer();
		
		sb.append("select f.gowhere,f.gowhere_id,count(*) totalTicket,sum(f.piece) totalPiece,")
		  .append("sum(f.cus_weight) totalWeight,sum(f.cp_fee+nvl(cp_value_add_fee,0)) yufu,sum(f.consignee_fee+nvl(f.cus_value_add_fee,0)) daofu,")
		  .append("sum(f.payment_collection) payment_collection,sum(f.tra_fee) tra_fee,")
		  .append("round(sum(f.tra_fee)/sum(f.cus_weight),2) preKgCost,sum(nvl(notConfirmNum,0)) notConfirmNum,")
		  .append("sum(notSettleAmount) notSettleAmount,")
		  .append("sum(notPayAmount) notPayAmount")
		  .append(" from aisuser.opr_fax_in f,aisuser.opr_status s,")
		  .append("(select t.d_no,decode(t.d_no,null,0,1) notConfirmNum from aisuser.opr_receipt t,aisuser.opr_fax_in f")
		  .append(" where f.d_no=t.d_no(+) and f.status=:faxStatus and (t.confirm_status=0 or t.confirm_status is null) ) r,")
		  .append("(select a.customer_id,a.documents_no,")
		  .append("sum(decode(a.cost_type,'到付提送费',(nvl(a.amount,0)-(nvl(a.settlement_amount,0)+nvl(a.abnormal_amount,0))),'到付增值费',")
		  .append("(nvl(a.amount,0)-(nvl(a.settlement_amount,0)+nvl(a.abnormal_amount,0))),'代收货款',(nvl(a.amount,0)-(nvl(a.settlement_amount,0)+nvl(a.abnormal_amount,0))),0)) notSettleAmount,")
		  .append("sum(decode(a.cost_type,'中转费',(nvl(a.amount,0)-(nvl(a.settlement_amount,0)+nvl(a.abnormal_amount,0))),0)) notPayAmount")
		  .append(" from aisuser.fi_payment a where a.status=:fiStatus group by a.customer_id,a.documents_no) p")
		  .append(" where f.d_no=s.d_no and f.d_no=r.d_no(+)")
		  .append(" and f.gowhere_id=p.customer_id(+) and f.d_no=p.documents_no(+) and f.distribution_mode=:distributionMode")
		  .append(" and f.status=:faxStatus and s.out_status in (1,2)");
		
		sb.append(this.appendConditions.appendCountDate(map));
		
		sb.append(this.appendConditions.appendConditions(map, sts));
		
		sb.append(" group by f.gowhere,f.gowhere_id");
		
		return sb.toString();
	}

	@ModuleName(value="中转明细查询SQL获取",logType=LogType.buss)
	public String findTransitDetailFindService(Map<String, String> map)
			throws Exception {
		String[] sts = new String[]{"distributionMode","faxStatus","fiStatus","overmemoType"};
		StringBuffer sb = new StringBuffer();
		String dno = map.get("f_dNo");
		String f_inDepartId = map.get("f_inDepartId");
		
		sb.append("select f.d_no,f.gowhere,f.gowhere_id,f.piece,")
		  .append("f.cus_weight,(f.cp_fee+nvl(f.cp_value_add_fee,0)) yufu,(f.consignee_fee+nvl(f.cus_value_add_fee,0)) daofu,")
		  .append("f.payment_collection,f.tra_fee,")
		  .append("round(f.tra_fee/f.cus_weight,2) preKgCost,nvl(notConfirmNum,0) notConfirmNum,")
		  .append("p.notSettleAmount,p.notPayAmount,")
		  .append("c.overmemo,c.create_name,TO_CHAR(c.create_time,'yyyy-MM-dd HH24:mi') CREATE_TIME,F.CP_NAME")
		  .append(" from aisuser.opr_fax_in f,aisuser.opr_status s,")
		  .append("(select t.d_no,decode(t.d_no,null,0,1) notConfirmNum from aisuser.opr_receipt t,aisuser.opr_fax_in f")
		  .append(" where f.d_no=t.d_no(+) and f.status=:faxStatus and (t.confirm_status=0 or t.confirm_status is null) ) r,")
		  .append("(select a.customer_id,a.documents_no,")
		  .append("sum(decode(a.cost_type,'到付提送费',(nvl(a.amount,0)-(nvl(a.settlement_amount,0)+nvl(a.abnormal_amount,0))),'到付增值费',")
		  .append("(nvl(a.amount,0)-(nvl(a.settlement_amount,0)+nvl(a.abnormal_amount,0))),'代收货款',(nvl(a.amount,0)-(nvl(a.settlement_amount,0)+nvl(a.abnormal_amount,0))),0)) notSettleAmount,")
		  .append("sum(decode(a.cost_type,'中转费',(nvl(a.amount,0)-(nvl(a.settlement_amount,0)+nvl(a.abnormal_amount,0))),0)) notPayAmount")
		  .append(" from aisuser.fi_payment a where a.status=:fiStatus group by a.customer_id,a.documents_no) p,")
		  .append("(select e.d_no,e.overmemo,q.create_name,q.create_time from (")
          .append("select d.d_no,min(d.overmemo) overmemo from")
          .append(" aisuser.opr_overmemo o,aisuser.opr_overmemo_detail d")
          .append(" where o.id=d.overmemo and o.OVERMEMO_TYPE=:overmemoType group by d.d_No) e,aisuser.opr_overmemo q where e.overmemo=q.id) c")
		  .append(" where f.d_no=s.d_no and f.d_no=r.d_no(+)")
		  .append(" and f.d_no=c.d_no")
		  .append(" and f.gowhere_id=p.customer_id(+) and f.d_no=p.documents_no(+) and f.distribution_mode=:distributionMode")
		  .append(" and f.status=:faxStatus and s.out_status in (1,2)");
		
		if(null!=dno && !"".equals(dno.trim())){
			sb.append(" and f.d_no=:f_dNo");
			if(null!=f_inDepartId && !"".equals(f_inDepartId.trim())){
				sb.append(" and f.in_Depart_Id=:f_inDepartId");
			}
		}else{
			sb.append(this.appendConditions.appendCountDate(map));
		
			sb.append(this.appendConditions.appendConditions(map, sts));
		}
		return sb.toString();
	}

	@ModuleName(value="中转汇总统计查询合计SQL获取",logType=LogType.buss)
	public String findTransitCountTotalService(Map<String, String> map)
			throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("select sum(totalTicket) totalTicket,sum(totalPiece) totalPiece,")
		  .append("sum(totalWeight) totalWeight,sum(yufu) yufu,sum(daofu) daofu,")
		  .append("sum(payment_collection) payment_collection,sum(tra_fee) tra_fee,")
		  .append("sum(notConfirmNum) notConfirmNum,sum(notSettleAmount) notSettleAmount,")
		  .append("sum(notPayAmount) notPayAmount,round(sum(tra_fee)/sum(totalWeight),2) preKgCost")
		  .append(" from (");
		sb.append(this.findTransitCountService(map));
		sb.append(") tt");
		
		return sb.toString();
	}

	@ModuleName(value="中转明细查询合计SQL获取",logType=LogType.buss)
	public String findTransitDetailTotalService(Map<String, String> map)
			throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("select sum(piece) totalPiece,count(*) totalTicket,")
		  .append("sum(cus_weight) totalWeight,sum(yufu) yufu,sum(daofu) daofu,")
		  .append("sum(payment_collection) payment_collection,sum(tra_fee) tra_fee,")
		  .append("sum(notConfirmNum) notConfirmNum,sum(notSettleAmount) notSettleAmount,")
		  .append("sum(notPayAmount) notPayAmount,round(sum(tra_fee)/sum(cus_weight),2) preKgCost")
		  .append(" from (");
		sb.append(this.findTransitDetailFindService(map));
		sb.append(") tt");
		
		return sb.toString();
	}

	@ModuleName(value="EDI货物时效汇总统计查询SQL获取",logType=LogType.buss)
	public String findEdiAgingCountService(Map<String, String> map)
			throws Exception {
		String[] sts = new String[]{"distributionMode","faxStatus"};
		StringBuffer sb = new StringBuffer();
		String dno = map.get("f_dNo");
		String f_inDepartId = map.get("f_inDepartId");
		
		sb.append("select f.gowhere,f.gowhere_id,f.in_depart_id,f.in_depart,count(*) totalTicket,")
		  .append("sum(f.piece) totalPiece,sum(f.cus_weight)totalWeight,sum(f.bulk) totalBulk,")
		  .append("sum(decode(d.ok_flag,1,1,0)) reachNum,")
		  .append("sum(decode(o.create_time,null,0,1)) outNum,")
		  .append("sum(decode(n.create_time,null,0,1)) signNum")
		  .append(" from aisuser.opr_fax_in f,aisuser.opr_status s,lxy.ct_tm_d d,lxy.ct_out_detail o,lxy.ct_sign_in n")
		  .append(" where f.d_no=s.d_no")
		  .append(" and f.status=:faxStatus")
		  .append(" and f.distribution_mode=:distributionMode")
		  .append(" and s.out_status in (1,2)")
		  .append(" and f.d_no||'' = d.d_no(+)")
		  .append(" and  f.d_no||''=o.d_no(+)")
		  .append(" and f.d_no||''=n.d_no(+)");
		
		sb.append(this.appendConditions.appendCountDate(map));
		
		sb.append(this.appendConditions.appendConditions(map, sts));
		
		sb.append(" group by f.gowhere,f.gowhere_id,f.in_depart,f.in_depart_id");
		return sb.toString();
	}

	@ModuleName(value="EDI货物时效查询SQL获取",logType=LogType.buss)
	public String findEdiAgingQueryService(Map<String, String> map)
			throws Exception {
		String[] sts = new String[]{"distributionMode","faxStatus","okFlag","transitStandard","carOutStandard","signStandard"};
		StringBuffer sb = new StringBuffer();
		String dno = map.get("f_dNo");
		String f_inDepartId = map.get("f_inDepartId");
		String transitStandard = map.get("transitStandard");
		String carOutStandard = map.get("carOutStandard");
		String signStandard = map.get("signStandard");
		
		sb.append("select *from (");
		sb.append("select g.*,e.transit_hour,e.carout_hour,e.signhour sign_hour,")
		  .append("case when nvl(e.transit_hour,0)-g.transitHour>=0 then '是' else '否' end transitStandard,")
		  .append("case when nvl(e.carout_hour,0)-g.outStockHour>=0 then '是' else '否' end carOutStandard,")
		  .append("case when nvl(e.signhour,0)-g.signHour>=0 then '是' else '否' end signStandard")
		  .append(" from (");
		sb.append("select d.ok_flag,f.d_no,f.in_depart_id,f.in_depart,")
		  .append("to_char(s.out_time,'yyyy-MM-dd HH24:mi') out_time,")
		  .append("to_char(d.ok_flag_createtime,'yyyy-MM-dd HH24:mi') reach_time,")
		  .append("to_char(o.create_time,'yyyy-MM-dd HH24:mi') carout_time,")
		  .append("to_char(n.create_time,'yyyy-MM-dd HH24:mi') sign_time,")
		  .append("f.gowhere,f.gowhere_id,f.piece,f.cus_weight,f.bulk,f.cp_name,")
		  .append("round((d.ok_flag_createtime-s.out_time)*24,2) transitHour,")
		  .append("round((o.create_time-d.ok_flag_createtime)*24,2) outStockHour,")
		  .append("round((n.create_time-o.create_time)*24,2) signHour")
		  .append(" from aisuser.opr_fax_in f,aisuser.opr_status s,lxy.ct_tm_d d,lxy.ct_out_detail o,lxy.ct_sign_in n")
		  .append(" where f.d_no=s.d_no")
		  .append(" and f.status=:faxStatus")
		  .append(" and f.distribution_mode=:distributionMode")
		  .append(" and s.out_status in (1,2)")
		  .append(" and f.d_no||'' = d.d_no(+)")
		  .append(" and  f.d_no||''=o.d_no(+)")
		  .append(" and f.d_no||''=n.d_no(+)")
		  .append(" and d.ok_flag(+)=:okFlag ");
		
		if(null!=dno && !"".equals(dno.trim())){
			sb.append(" and f.d_no=:f_dNo");
			if(null!=f_inDepartId && !"".equals(f_inDepartId.trim())){
				sb.append(" and f.in_Depart_Id=:f_inDepartId");
			}
		}else{
			sb.append(this.appendConditions.appendCountDate(map));
		
			sb.append(this.appendConditions.appendConditions(map, sts));
		}
		sb.append(") g,aisuser.opr_edi_aging_standard e")
		  .append(" where g.in_depart_id=e.dept_id(+)");
		sb.append(" ) where 1=1");
		
		if(null==dno || "".equals(dno.trim())){
			
			if(null!=transitStandard && !"".equals(transitStandard.trim())){
				sb.append(" and transitStandard=:transitStandard");
			}
			if(null!=carOutStandard && !"".equals(carOutStandard.trim())){
				sb.append(" and carOutStandard=:carOutStandard");
			}
			if(null!=signStandard && !"".equals(signStandard.trim())){
				sb.append(" and signStandard=:signStandard");
			}
		}
		return sb.toString();
	}

	@ModuleName(value="EDI货物时效查询合计SQL获取",logType=LogType.buss)
	public String findEdiAgingQueryTotalService(Map<String, String> map)
			throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("select sum(piece) totalPiece,count(*) totalTicket,")
		  .append("sum(cus_weight) totalWeight,sum(decode(transitStandard,'是',1,0)) transitStandardNum,")
		  .append("sum(decode(carOutStandard,'是',1,0)) carOutStandardNum,sum(decode(signStandard,'是',1,0)) signStandardNum")
		  .append(" from (");
		sb.append(this.findEdiAgingQueryService(map));
		sb.append(") tt");
		
		return sb.toString();
	}

	@ModuleName(value="EDI货物时效汇总合计SQL获取",logType=LogType.buss)
	public String findEdiAgingCountTotalService(Map<String, String> map)
			throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("select sum(totalPiece) totalPiece,sum(totalTicket) totalTicket,")
		  .append("sum(totalWeight) totalWeight,sum(reachNum) reachNum,")
		  .append("sum(outNum) outNum,sum(signNum) signNum")
		  .append(" from (");
		sb.append(this.findEdiAgingCountService(map));
		sb.append(") tt");
		
		return sb.toString();
	}

}
