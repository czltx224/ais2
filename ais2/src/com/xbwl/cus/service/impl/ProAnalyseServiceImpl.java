package com.xbwl.cus.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.orm.Page;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.cus.dao.IProAnalyseDao;
import com.xbwl.cus.service.ICusGoodsRankService;
import com.xbwl.cus.service.IProAnalyseService;
import com.xbwl.cus.vo.ReportSerarchVo;
import com.xbwl.entity.CusGoodsRank;
import com.xbwl.entity.ProductAnalyse;
import com.xbwl.entity.SysDepart;
import com.xbwl.oper.reports.util.AppendConditions;
import com.xbwl.rbac.Service.IDepartService;
@Service("proAnalyseServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class ProAnalyseServiceImpl extends BaseServiceImpl<ProductAnalyse,Long> implements
		IProAnalyseService {
	@Resource(name="proAnalyseHibernateDaoImpl")
	private IProAnalyseDao proAnalyseDao;
	@Resource(name="departServiceImpl")
	private IDepartService departService;
	@Resource(name="appendConditions")
	private AppendConditions appendConditions;
	@Resource(name="cusGoodsRankServiceImpl")
	private ICusGoodsRankService cusGoodsRankService;
	@Override
	public IBaseDAO getBaseDao() {
		return proAnalyseDao;
	}
	//REVIEW-ACCEPT 增加注释
	//FIXED 接口上已写入注释
	public Page findWholePro(Page page,ReportSerarchVo searchVo) throws Exception {
		Map sqlMap = getSqlStr(searchVo);
		Date startDate = searchVo.getStartDate();
		Date endDate = searchVo.getEndDate();
		Map map=new HashMap();
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		
		StringBuffer sql=new StringBuffer(sqlMap.get("sqlStr").toString());
		String termVal=sqlMap.get("termVal").toString();
		String groupByVal=sqlMap.get("groupByVal").toString();
		Map termMap = (Map)sqlMap.get("termMap");
		Iterator<String> termIter = termMap.keySet().iterator();
		while(termIter.hasNext()){
			String key = termIter.next();
			map.put(key, termMap.get(key));
		}
		
		sql.append(" sum(pa.product_ticket) sumTicket,round(sum(pa.product_ticket)/max(t.sumTicket),4) ticketRate,");
		sql.append("sum(pa.product_weight) sumWeight,round(sum(pa.product_weight)/max(t.sumWeight),4) weightRate,sum(pa.product_income) sumIncome,round(sum(pa.product_income)/max(t.sumIncome),4) incomeRate");
		sql.append(" from product_analyse pa,");
		sql.append("(select sum(t.product_ticket) sumTicket,sum(t.product_weight) sumWeight,sum(t.product_income) sumIncome from product_analyse t where t.count_time>=:startDate and t.count_time<:endDate+1) t");
		sql.append(" where pa.count_time>=:startDate and pa.count_time<:endDate+1");
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
	//REVIEW-ACCEPT 增加注释
	//FIXED 接口上已写明注释 
	public Page findIncomePro(Page page,ReportSerarchVo searchVo) throws Exception {
		Map map=new HashMap();
		
		String type = searchVo.getCountType();
		String startCount =searchVo.getStartCount();
		String endCount =searchVo.getEndCount();
		String countRange =searchVo.getCountRange();
		String countType="";
		
		Map sqlMap = getSqlStr(searchVo);
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
		//获得 年、月、日、周 统计时的间隔天数
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
		//如果是统计货量
		if(type.equals("weight")){
			countType="pa.product_weight";
		}
		else if(type.equals("income")){
			//如果是统计收入
			countType="pa.product_income";
		}else{
			//票数统计
			countType="pa.product_ticket";
		}
		//拼接 年、月、日、周 统计的SQL语句
		if(countRange.equals("日")){
			cal.setTime(date1);
			for(int i=1;i<=count;i++){
				String yy = cal.get(Calendar.YEAR)+"";
				String dd = cal.get(Calendar.DATE)<10?"0"+cal.get(Calendar.DATE):cal.get(Calendar.DATE)+"";
				String mm = cal.get(Calendar.MONTH)+1<10?"0"+(cal.get(Calendar.MONTH)+1):(cal.get(Calendar.MONTH)+1)+"";
				if(i!=1){
					sql.append(",");
				}
				sql.append("sum(decode(to_date(to_char(pa.count_time,'"+dateFormat+"'),'"+dateFormat+"'),")
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
				sql.append("sum(decode(to_date(to_char(pa.count_time,'"+dateFormat+"'),'"+dateFormat+"'),")
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
				sql.append("sum(decode(trunc(to_char(pa.count_time,'"+dateFormat+"')),")
				  .append(countNum+",round(nvl(")
				  .append(countType)
				  .append(",0),2),0)) ")
				  .append("\"").append(countNum).append(countRange).append("\"");
				countNum++;
			}
		}
		sql.append(" from product_analyse pa where 1=1");
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
	//REVIEW-ACCEPT 增加注释
	//FIXED 接口上已写注释 
	public Page findGoodsRank(Page page,ReportSerarchVo searchVo) throws Exception {
		Map map=new HashMap();
		Date startDate = searchVo.getStartDate();
		Date endDate = searchVo.getEndDate();

		map.put("startDate", startDate);
		map.put("endDate", endDate);
		
		Map sqlMap = getSqlStr(searchVo);
		StringBuffer sql=new StringBuffer(sqlMap.get("sqlStr").toString());
		String termVal=sqlMap.get("termVal").toString();
		String groupByVal=sqlMap.get("groupByVal").toString();
		Map termMap = (Map)sqlMap.get("termMap");
		Iterator<String> termIter = termMap.keySet().iterator();
		while(termIter.hasNext()){
			String key = termIter.next();
			map.put(key, termMap.get(key));
		}
		
		Page rankList = cusGoodsRankService.findAllRank(page);
		if(rankList.getResult().size()<1){
			throw new ServiceException("没有设置货量等级，请先设置再尝试统计!");
		}
		 List<CusGoodsRank> cusList = rankList.getResult(); 
		//获得所有的货量等级信息
		for(CusGoodsRank cgr : cusList){
			Long minVal = cgr.getMinVal();
			Long maxVal = cgr.getMaxVal();
			
			String columnName = minVal+"~"+maxVal+"kg";
			sql.append("nvl(sum(case when pa.product_weight between ");
			sql.append(minVal);
			sql.append(" and ");
			sql.append(maxVal);
			sql.append(" then pa.product_weight end),0) \"");
			sql.append(columnName);
			sql.append("\",");
			
		}
		sql.append("sum(pa.product_weight) sum_weight");
		sql.append(" from product_analyse pa where pa.count_time>=:startDate and pa.count_time<:endDate+1");
		sql.append(termVal);
		if(groupByVal.length()>0){
			sql.append(" group by ");
		}
		int index = groupByVal.lastIndexOf(",");
		//去除","
		if(index > 0 && index == groupByVal.length()-1){
			groupByVal = groupByVal.substring(0,groupByVal.length()-1);
		}
		sql.append(groupByVal);
		return this.getPageBySqlMap(page, sql.toString(), map);
	}
	/**
	 * 获得Sql及条件以及一些分组信息
	 */
	public Map getSqlStr(ReportSerarchVo searchVo){
		
		Map sqlMap = new HashMap();
		Map map = new HashMap();
		
		Long departId = searchVo.getDepartId();;//部门
		Long cusId = searchVo.getCusId();//代理ID
		String trafficMode = searchVo.getTrafficMode();//运输方式
		String productType = searchVo.getProductType();//产品类型
		String endCity = searchVo.getEndCity();//目的站
		String departScope = searchVo.getDepartScope();//部门范围 "all"/"one"/""
		String cusScope = searchVo.getCusScope();//客商范围
		String trafficScope = searchVo.getTrafficModeScope();//运输方式范围
		String productTypeScope = searchVo.getProductTypeScope();//产品类型范围
		String endCityScope = searchVo.getEndCityScope();//目的站范围
		
		
		StringBuffer groupByVal = new StringBuffer("");
		StringBuffer termVal = new StringBuffer("");
		StringBuffer sql=new StringBuffer("select");
		
		if(departScope.equals("one")){
			SysDepart sd = departService.getAndInitEntity(departId);
			if(sd == null){
				throw new ServiceException("数据异常，部门ID："+departId+"对应的部门信息为空了。");
			}
			if(sd.getIsBussinessDepa() == 1){
				map.put("departId", departId);
				termVal.append(" and pa.depart_id=:departId");
			}else{
				map.put("cusDepartCode", sd.getDepartNo());
				termVal.append(" and pa.cus_depart_code=:cusDepartCode");
			}
			sql.append(" pa.cus_depart_name,");
			groupByVal.append(" pa.cus_depart_name,");
		}else if(departScope.equals("all")){
			groupByVal.append(" pa.cus_depart_name,");
			sql.append(" pa.cus_depart_name,");
		}
		
		if(cusScope.equals("one")){
			if(cusId == null){
				throw new ServiceException("数据异常，代理ID不能为空!");
			}
			sql.append(" pa.cus_name,");
			map.put("cusId", cusId);
			termVal.append(" and pa.cus_id=:cusId");
			groupByVal.append(" pa.cus_name,");
		}else if(cusScope.equals("all")){
			groupByVal.append(" pa.cus_name,");
			sql.append(" pa.cus_name,");
		}
		
		if(trafficScope.equals("one")){
			if(trafficMode == null || "".equals(trafficMode)){
				throw new ServiceException("数据异常，运输方式不能为空！");
			}
			groupByVal.append(" pa.traffic_mode,");
			map.put("trafficMode", trafficMode);
			termVal.append(" and pa.traffic_mode=:trafficMode");
			sql.append(" pa.traffic_mode,");
		}else if(trafficScope.equals("all")){
			groupByVal.append(" pa.traffic_mode,");
			sql.append(" pa.traffic_mode,");
		}
		
		if(productTypeScope.equals("one")){
			if(productType == null || "".equals(productType)){
				throw new ServiceException("数据异常，产品类型不能为空！");
			}
			sql.append(" pa.product_type,");
			map.put("productType", productType);
			termVal.append(" and pa.product_type=:productType");
			groupByVal.append(" pa.product_type,");
		}else if(productTypeScope.equals("all")){
			groupByVal.append(" pa.product_type,");
			sql.append(" pa.product_type,");
		}
		
		if(endCityScope.equals("one")){
			if(endCity == null || "".equals(endCity)){
				throw new ServiceException("数据异常，目的站不能为空！");
			}
			sql.append(" pa.end_city,");
			map.put("endCity", endCity);
			termVal.append(" and pa.end_city=:endCity");
			groupByVal.append(" pa.end_city,");
		}else if(endCityScope.equals("all")){
			groupByVal.append(" pa.end_city,");
			sql.append(" pa.end_city,");
		}
		sqlMap.put("groupByVal", groupByVal);//分组列
		sqlMap.put("termVal", termVal);//条件
		sqlMap.put("termMap", map);//条件值MAP
		sqlMap.put("sqlStr", sql);//SQL 语句
		return sqlMap;
	}
}
