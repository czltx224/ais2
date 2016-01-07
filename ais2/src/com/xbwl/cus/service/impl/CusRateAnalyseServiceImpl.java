package com.xbwl.cus.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.orm.Page;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.cus.dao.ICusRateAnalyseDao;
import com.xbwl.cus.service.ICusRateAnalyseService;
import com.xbwl.cus.service.IProAnalyseService;
import com.xbwl.cus.vo.ReportSerarchVo;
import com.xbwl.entity.CusRateAnalyse;
import com.xbwl.oper.reports.util.AppendConditions;

/**
 *@author LiuHao
 *@time May 21, 2012 3:45:42 PM
 */
@Service("cusRateAnalyseServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class CusRateAnalyseServiceImpl extends BaseServiceImpl<CusRateAnalyse,Long> implements
		ICusRateAnalyseService {
	@Resource(name="cusRateAnalyseHibernateDaoImpl")
	private ICusRateAnalyseDao cusRateAnalyseDao;
	@Resource(name="proAnalyseServiceImpl")
	private IProAnalyseService proAnalyseService;
	@Resource(name="appendConditions")
	private AppendConditions appendConditions;
	@Override
	public IBaseDAO getBaseDao() {
		return cusRateAnalyseDao;
	}
	public Page rateAnalyse(Page page, ReportSerarchVo searchVo)
			throws Exception {
		String startCount =searchVo.getStartCount();
		String endCount =searchVo.getEndCount();
		String countRange =searchVo.getCountRange();
		String countType=searchVo.getCountType();
		//统计价格或者统计费率
		if("价格".equals(countType)){
			countType = "pa.rate";
		}else{
			countType = "pa.rebate";
		}
		Map sqlMap = proAnalyseService.getSqlStr(searchVo);
		
		Map map=new HashMap();

		
		StringBuffer sql=new StringBuffer(sqlMap.get("sqlStr").toString());
		String termVal=sqlMap.get("termVal").toString();
		String groupByVal=sqlMap.get("groupByVal").toString();
		Map termMap = (Map)sqlMap.get("termMap");
		Iterator<String> termIter = termMap.keySet().iterator();
		while(termIter.hasNext()){
			String key = termIter.next();
			map.put(key, termMap.get(key));
		}
		
		int  count=1;
		int countNum=0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date1=new Date();
		Date date2=new Date();
		Calendar cal=Calendar.getInstance();
		String dateFormat="yyyy-MM-dd";
		//获得统计范围 年、月、周、日 两个时间之间相隔的时间
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
		//根据统计维度 年、月、周、日 拼接SQL
		if(countRange.equals("日")){
			cal.setTime(date1);
			for(int i=1;i<=count;i++){
				String yy = cal.get(Calendar.YEAR)+"";
				String dd = cal.get(Calendar.DATE)<10?"0"+cal.get(Calendar.DATE):cal.get(Calendar.DATE)+"";
				String mm = cal.get(Calendar.MONTH)+1<10?"0"+(cal.get(Calendar.MONTH)+1):(cal.get(Calendar.MONTH)+1)+"";
				if(i!=1){
					sql.append(",");
				}
				sql.append("round(sum(decode(to_date(to_char(pa.count_time,'"+dateFormat+"'),'"+dateFormat+"'),")
				  .append("to_date('"+sdf.format(cal.getTime())+"','"+dateFormat+"'),nvl(")
				  .append(countType)
				  .append(",0),0))/decode(sum(decode(to_date(to_char(pa.count_time,'"+dateFormat+"'),'"+dateFormat+"'),")
				  .append("to_date('"+sdf.format(cal.getTime())+"','"+dateFormat+"'),nvl(pa.ticket_num,0),0))")
				  .append(",0,1,")
				  .append("sum(decode(to_date(to_char(pa.count_time,'"+dateFormat+"'),'"+dateFormat+"'),")
				  .append("to_date('"+sdf.format(cal.getTime())+"','"+dateFormat+"'),nvl(pa.ticket_num,0),0))")
				  .append(")")
				  .append(",3) ")
				  .append("\"").append(yy).append("-").append(mm).append("-").append(dd).append("\"");
				cal.set(Calendar.DATE, cal.get(Calendar.DATE)+1);
			}
		}else if(countRange.equals("月")){
			cal.setTime(date1);
			for(int i=1;i<=count;i++){
				String yy = cal.get(Calendar.YEAR)+"";
				String mm = cal.get(Calendar.MONTH)+1<10?"0"+(cal.get(Calendar.MONTH)+1):(cal.get(Calendar.MONTH)+1)+"";
				if(i!=1){
					sql.append(",");
				}
				sql.append("round(sum(decode(to_date(to_char(pa.count_time,'"+dateFormat+"'),'"+dateFormat+"'),")
				  .append("to_date('"+sdf.format(cal.getTime())+"','"+dateFormat+"'),nvl(")
				  .append(countType)
				  .append(",0),0))/decode(sum(decode(to_date(to_char(pa.count_time,'"+dateFormat+"'),'"+dateFormat+"'),")
				  .append("to_date('"+sdf.format(cal.getTime())+"','"+dateFormat+"'),nvl(pa.ticket_num,0),0))")
				  .append(",0,1,")
				  .append("sum(decode(to_date(to_char(pa.count_time,'"+dateFormat+"'),'"+dateFormat+"'),")
				  .append("to_date('"+sdf.format(cal.getTime())+"','"+dateFormat+"'),nvl(pa.ticket_num,0),0))")
				  .append(")")
				  .append(",3) ")
				  .append("\"").append(yy).append("-").append(mm).append("\"");
				cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)+1);
			}
		}else if(countRange.equals("周") || countRange.equals("年")){
			countNum=Integer.parseInt(startCount);
			for(int i=1;i<=count;i++){
				if(i!=1){
					sql.append(",");
				}
				sql.append("sum(decode(trunc(to_char(pa.count_time,'"+dateFormat+"')),")
				  .append(countNum+",nvl(")
				  .append(countType)
				  .append(",0),0))/decode(sum(decode(trunc(to_char(pa.count_time,'"+dateFormat+"')),")
				  .append(countNum+",nvl(pa.ticket_num,0)))")
				  .append(",0,1,")
				  .append("sum(decode(trunc(to_char(pa.count_time,'"+dateFormat+"')),")
				  .append(countNum+",nvl(pa.ticket_num,0)))")
				  .append(",3)")
				  .append("\"").append(countNum).append(countRange).append("\"");
				countNum++;
			}
		}
		
		sql.append(" from cus_rate_analyse pa ");
		sql.append(termVal);
		if(groupByVal.length()>0){
			sql.append(" group by ");
		}
		int index = groupByVal.lastIndexOf(",");
		if(index > 0 && index == groupByVal.length()-1){
			groupByVal = groupByVal.substring(0,groupByVal.length()-1);
		}
		sql.append(groupByVal);
		return this.getPageBySqlMap(page, sql.toString(), map);
	}
}
