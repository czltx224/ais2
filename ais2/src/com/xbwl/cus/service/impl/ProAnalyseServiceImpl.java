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
	//REVIEW-ACCEPT ����ע��
	//FIXED �ӿ�����д��ע��
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
	//REVIEW-ACCEPT ����ע��
	//FIXED �ӿ�����д��ע�� 
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
		//��� �ꡢ�¡��ա��� ͳ��ʱ�ļ������
		if(countRange.equals("��")){
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
		}else if(countRange.equals("��")){
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
		}else if(countRange.equals("��")){
			dateFormat="WW";
			try{
				count=Integer.parseInt(endCount)+1-Integer.parseInt(startCount);
				if(count<0){
					throw new ServiceException("���������쳣��");
				}
			}catch (Exception e) {
				throw new ServiceException("���������쳣��");
			}
			if(count>20){
				throw new ServiceException("����ͳ�Ʋ��ܳ���20�ܣ�");
			}
			
			countNum=Integer.parseInt(startCount);
		}else if(countRange.equals("��")){
			dateFormat="yyyy";
			count=Integer.parseInt(endCount)+1-Integer.parseInt(startCount);
			if(count>10){
				throw new ServiceException("����ͳ�Ʋ��ܳ���10���꣡");
			}else if(count<1){
				throw new ServiceException("��ݲ����ڸ�����");
			}
			countNum=Integer.parseInt(startCount);
		}
		//�����ͳ�ƻ���
		if(type.equals("weight")){
			countType="pa.product_weight";
		}
		else if(type.equals("income")){
			//�����ͳ������
			countType="pa.product_income";
		}else{
			//Ʊ��ͳ��
			countType="pa.product_ticket";
		}
		//ƴ�� �ꡢ�¡��ա��� ͳ�Ƶ�SQL���
		if(countRange.equals("��")){
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
		}else if(countRange.equals("��")){
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
		}else if(countRange.equals("��") || countRange.equals("��")){
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
	//REVIEW-ACCEPT ����ע��
	//FIXED �ӿ�����дע�� 
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
			throw new ServiceException("û�����û����ȼ������������ٳ���ͳ��!");
		}
		 List<CusGoodsRank> cusList = rankList.getResult(); 
		//������еĻ����ȼ���Ϣ
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
		//ȥ��","
		if(index > 0 && index == groupByVal.length()-1){
			groupByVal = groupByVal.substring(0,groupByVal.length()-1);
		}
		sql.append(groupByVal);
		return this.getPageBySqlMap(page, sql.toString(), map);
	}
	/**
	 * ���Sql�������Լ�һЩ������Ϣ
	 */
	public Map getSqlStr(ReportSerarchVo searchVo){
		
		Map sqlMap = new HashMap();
		Map map = new HashMap();
		
		Long departId = searchVo.getDepartId();;//����
		Long cusId = searchVo.getCusId();//����ID
		String trafficMode = searchVo.getTrafficMode();//���䷽ʽ
		String productType = searchVo.getProductType();//��Ʒ����
		String endCity = searchVo.getEndCity();//Ŀ��վ
		String departScope = searchVo.getDepartScope();//���ŷ�Χ "all"/"one"/""
		String cusScope = searchVo.getCusScope();//���̷�Χ
		String trafficScope = searchVo.getTrafficModeScope();//���䷽ʽ��Χ
		String productTypeScope = searchVo.getProductTypeScope();//��Ʒ���ͷ�Χ
		String endCityScope = searchVo.getEndCityScope();//Ŀ��վ��Χ
		
		
		StringBuffer groupByVal = new StringBuffer("");
		StringBuffer termVal = new StringBuffer("");
		StringBuffer sql=new StringBuffer("select");
		
		if(departScope.equals("one")){
			SysDepart sd = departService.getAndInitEntity(departId);
			if(sd == null){
				throw new ServiceException("�����쳣������ID��"+departId+"��Ӧ�Ĳ�����ϢΪ���ˡ�");
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
				throw new ServiceException("�����쳣������ID����Ϊ��!");
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
				throw new ServiceException("�����쳣�����䷽ʽ����Ϊ�գ�");
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
				throw new ServiceException("�����쳣����Ʒ���Ͳ���Ϊ�գ�");
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
				throw new ServiceException("�����쳣��Ŀ��վ����Ϊ�գ�");
			}
			sql.append(" pa.end_city,");
			map.put("endCity", endCity);
			termVal.append(" and pa.end_city=:endCity");
			groupByVal.append(" pa.end_city,");
		}else if(endCityScope.equals("all")){
			groupByVal.append(" pa.end_city,");
			sql.append(" pa.end_city,");
		}
		sqlMap.put("groupByVal", groupByVal);//������
		sqlMap.put("termVal", termVal);//����
		sqlMap.put("termMap", map);//����ֵMAP
		sqlMap.put("sqlStr", sql);//SQL ���
		return sqlMap;
	}
}
