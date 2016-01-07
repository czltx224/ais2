package com.xbwl.cus.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.orm.Page;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.cus.dao.ICusAnalyseDao;
import com.xbwl.cus.service.ICusAnalyseService;
import com.xbwl.entity.CusAnalyse;
import com.xbwl.entity.SysDepart;
import com.xbwl.oper.reports.util.AppendConditions;
import com.xbwl.rbac.Service.IDepartService;

/**
 * 客服分析服务层实现类
 *@author LiuHao
 *@time Dec 17, 2011 11:11:32 AM
 */
@Service("cusAnalyseServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class CusAnalyseServiceImpl extends BaseServiceImpl<CusAnalyse,Long> implements
		ICusAnalyseService {
	@Resource(name="cusAnalyseHibernateDaoImpl")
	private ICusAnalyseDao cusAnalyseDao;
	@Value("${cusAnalyseServiceImpl.cusThanPageSize}")
	private Long cusThanPageSize;
	@Resource(name="appendConditions")
	private AppendConditions appendConditions;
	@Resource(name="departServiceImpl")
	private IDepartService departService;
	@Override
	public IBaseDAO getBaseDao() {
		return cusAnalyseDao;
	}
	public Page findCusRankMsg(Page page,Date startDate,Date endDate,String dateType) throws Exception {
		Map map=new HashMap();
		map.put("firstDate", startDate);
		map.put("lastDate", endDate);
		
		StringBuffer sql=new StringBuffer();
		sql.append("select t.cus_depart_code,t.cus_depart_name,t.large_cus_count,t.large_cus_income,t.imp_cus_count,t.imp_cus_income,");
		sql.append("t.offen_cus_count,t.offen_cus_income,t.dis_cus_count,t.dis_cus_income,");
		sql.append("(t.large_cus_count+t.imp_cus_count+t.offen_cus_count+t.dis_cus_count) sum_count,");
		sql.append("(t.large_cus_income+t.imp_cus_income+t.offen_cus_income+t.dis_cus_income) sum_income");
		sql.append(" from(select ca.cus_depart_code,ca.cus_depart_name,");
		sql.append("count(decode(ca.importance_level,'大客户',ca.cus_depart_code)) large_cus_count,");
		sql.append("nvl(sum(decode(ca.importance_level,'大客户',ca.cus_income)),0) large_cus_income,");
		sql.append("count(decode(ca.importance_level,'重点客户',ca.cus_depart_code)) imp_cus_count,");
		sql.append("nvl(sum(decode(ca.importance_level,'重点客户',ca.cus_income)),0) imp_cus_income,");
		sql.append("count(decode(ca.importance_level,'常客',ca.cus_depart_code)) offen_cus_count,");
		sql.append("nvl(sum(decode(ca.importance_level,'常客',ca.cus_income)),0) offen_cus_income,");
		sql.append("count(decode(ca.importance_level,'散客',ca.cus_depart_code)) dis_cus_count,");
		sql.append("nvl(sum(decode(ca.importance_level,'散客',ca.cus_income)),0) dis_cus_income");
		//REVIEW-ACCEPT 1=1条件去除
		//FIXED LIUH
		//sql.append(" from cus_analyse ca where 1=1");
		sql.append(" from cus_analyse ca,cus_record cr where ca.cus_record_id=cr.id ");
		//REVIEW-ACCEPT 两次转换没有必要
		//FIXED LIUH
		//sql.append(" and to_date(to_char(ca.count_time,'yyyy-mm'),'yyyy-mm')=to_date(:countDate,'yyyy-mm')");
		//sql.append(" where ca.count_time>=:firstDate and ca.count_time<=:lastDate ");
		if(dateType.equals("firstDate")){
			sql.append(" and cr.start_buss>=:firstDate and cr.start_buss<:lastDate+1");
		}else{
			sql.append(" and cr.last_buss>=:firstDate and cr.last_buss<:lastDate+1");
		}
		sql.append(" group by ca.cus_depart_code,ca.cus_depart_name)t");
		return this.getPageBySqlMap(page, sql.toString(), map);
	}
	public Page findCusMonRankMsg(Page page,String countType, String startCount,String endCount,
			Long departId,String countRange,Long cusRecordId) throws Exception {
		Map map=new HashMap();
		String groupBy = "";
		String columnVal = "";
		if(cusRecordId != null){
			columnVal = "t.cus_name";
		}else{
			columnVal = "t.importance_level";
			groupBy = "t.importance_level";
		}
		StringBuffer sql=new StringBuffer("select ");
		sql.append(columnVal).append(",");
		//String date=new SimpleDateFormat("yyyy").format(countDate);
		if(countType.equals("weight")){
			countType="t.cus_weight";
		}
		else if(countType.equals("income")){
			//如果是统计收入
			countType="t.cus_income";
		}else{
			//票数统计
			countType="t.cus_ticket";
		}
		int  count=1;
		int countNum=0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date1=new Date();
		Date date2=new Date();
		Calendar cal=Calendar.getInstance();
		String dateFormat="yyyy-MM-dd";
		
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
		if(countRange.equals("日")){
			cal.setTime(date1);
			for(int i=1;i<=count;i++){
				String yy = cal.get(Calendar.YEAR)+"";
				String dd = cal.get(Calendar.DATE)<10?"0"+cal.get(Calendar.DATE):cal.get(Calendar.DATE)+"";
				String mm = cal.get(Calendar.MONTH)+1<10?"0"+(cal.get(Calendar.MONTH)+1):(cal.get(Calendar.MONTH)+1)+"";
				if(i!=1){
					sql.append(",");
				}
				sql.append("sum(decode(to_date(to_char(t.count_time,'"+dateFormat+"'),'"+dateFormat+"'),")
				  .append("to_date('"+sdf.format(cal.getTime())+"','"+dateFormat+"'),round(nvl(")
				  .append(countType)
				  .append(",0),2),0)) ")
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
				sql.append("sum(decode(to_date(to_char(t.count_time,'"+dateFormat+"'),'"+dateFormat+"'),")
				  .append("to_date('"+sdf.format(cal.getTime())+"','"+dateFormat+"'),round(nvl(")
				  .append(countType)
				  .append(",0),2),0)) ")
				  .append("\"").append(yy).append("-").append(mm).append("\"");
				cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)+1);
			}
		}else if(countRange.equals("周") || countRange.equals("年")){
			countNum=Integer.parseInt(startCount);
			for(int i=1;i<=count;i++){
				if(i!=1){
					sql.append(",");
				}
				sql.append("sum(decode(trunc(to_char(t.count_time,'"+dateFormat+"')),")
				  .append(countNum+",round(nvl(")
				  .append(countType)
				  .append(",0),2),0)) ")
				  .append("\"").append(countNum).append(countRange).append("\"");
				countNum++;
			}
		}
		//REVIEW-ACCEPT 1=1条件去除
		//FIXED LIUH
		//sql.append(" from cus_analyse t where 1=1 ");
		sql.append(" from cus_analyse t where 1=1");
		if(departId!=null){
			SysDepart sd = departService.getAndInitEntity(departId);
			if(sd.getIsBussinessDepa()==1){
				sql.append(" and t.depart_id=:departId");
				map.put("departId", departId);
			}else{
				sql.append(" where t.cus_depart_code=:cusDepartCode");
				map.put("cusDepartCode", sd.getDepartNo());
			}
		}
		if(cusRecordId != null){
			sql.append(" and t.cus_record_id=:cusRecordId");
			map.put("cusRecordId", cusRecordId);
		}
		if(!"".equals(groupBy)){
			sql.append(" group by ").append(groupBy);
		}
		return this.getPageBySqlMap(page, sql.toString(), map);
	}
	public Page findCusRankThan(Page page,String countType, Date beforeDate, Date afterDate,
			String cusType) throws Exception {
		Map map=new HashMap();
		if(countType.equals("货量")){
			countType="t.cus_weight";
		}
		else if(countType.equals("收入")){
			//如果是统计收入
			countType="t.cus_income";
		}else{
			//票数统计
			countType="t.cus_ticket";
		}
		map.put("cusType", cusType);
		String bDate=new SimpleDateFormat("yyyy-MM").format(beforeDate);
		String aDate=new SimpleDateFormat("yyyy-MM").format(afterDate);
		map.put("afterDate", aDate);
		map.put("beforeDate", bDate);
		StringBuffer sql=new StringBuffer("select t.depart_name,t.cus_name,t.before_count,t.after_count,(t.after_count-t.before_count) d_value from(");
		sql.append("select t.depart_name,t.cus_name,");
		sql.append("nvl(sum(decode(to_date(to_char(t.count_time,'yyyy-mm'),'yyyy-mm'),to_date(:beforeDate,'yyyy-mm'),"+countType+")),0) before_Count,");
		sql.append("nvl(sum(decode(to_date(to_char(t.count_time,'yyyy-mm'),'yyyy-mm'),to_date(:afterDate,'yyyy-mm'),"+countType+")),0) after_Count");
		sql.append(" from cus_analyse t where 1=1");
		if(countType!=null){
			sql.append(" and t.importance_level=:cusType");
		}
		sql.append(" and rownum<="+cusThanPageSize);
		sql.append(" group by t.depart_id,t.depart_name,t.cus_record_id,t.cus_name)t");
		sql.append(" order by t.after_count desc");
		return this.getPageBySqlMap(page, sql.toString(), map);
	}
	public Page findCusVsCus(Page page,String countType, Date startDate, Date endDate,
			String cusService) throws Exception {
		Map map=new HashMap();
		if(countType.equals("货量")){
			countType="ca.cus_weight";
		}
		else if(countType.equals("收入")){
			//如果是统计收入
			countType="ca.cus_income";
		}else{
			//票数统计
			countType="ca.cus_ticket";
		}
		map.put("cusService", cusService);
		String bDate=new SimpleDateFormat("yyyy-MM").format(startDate);
		String aDate=new SimpleDateFormat("yyyy-MM").format(endDate);
		map.put("afterDate", aDate);
		map.put("beforeDate", bDate);
		
		StringBuffer sql=new StringBuffer("select cus_name,principal,before_count,after_count,(after_count-before_count) d_value from(");
		sql.append("select cr.cus_name,cr.principal,");
		sql.append("nvl(sum(decode(to_date(to_char(ca.count_time,'yyyy-mm'),'yyyy-mm'),to_date(:beforeDate,'yyyy-mm'),"+countType+")),0) before_Count,");
		sql.append("nvl(sum(decode(to_date(to_char(ca.count_time,'yyyy-mm'),'yyyy-mm'),to_date(:afterDate,'yyyy-mm'),"+countType+")),0) after_Count");
		sql.append(" from cus_record cr, cus_analyse ca where cr.id = ca.cus_record_id(+) and cr.is_cq = '发货代理' and cr.principal=:cusService");
		sql.append(" group by cr.cus_name,cr.principal)");
		return this.getPageBySqlMap(page, sql.toString(), map);
	}
}
