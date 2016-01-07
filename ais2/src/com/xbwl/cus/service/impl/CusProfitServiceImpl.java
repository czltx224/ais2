package com.xbwl.cus.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.orm.Page;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.cus.dao.ICusProfitDao;
import com.xbwl.cus.service.ICusProfitService;
import com.xbwl.entity.CusProfit;
import com.xbwl.oper.reports.util.AppendConditions;
//REVIEW-ACCEPT ����ע��
//FIXED
/**
 * �ͻ�ӯ�����������ʵ����
 *@author LiuHao
 *@time Oct 20, 2011 2:05:51 PM
 */
@Service("cusProfitServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class CusProfitServiceImpl extends BaseServiceImpl<CusProfit,Long> implements
		ICusProfitService {
	@Resource(name="cusProfitHibernateDaoImpl")
	private ICusProfitDao cusProfitDao;
	@Resource(name="appendConditions")
	private AppendConditions appendConditions;
	@Override
	public IBaseDAO getBaseDao() {
		return cusProfitDao;
	}
	//REVIEW-ACCEPT ����ע��
	//FIXED �ӿ��Ѽ���ע�ͣ��˴��Ƿ���Բ�д��
	public Page findProfitMsg(Page page,String startCount,String endCount,String countRange, Long cusId) throws Exception {
		User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
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
		
		Map map=new HashMap();
		map.put("departId", user.get("bussDepart"));
		//����SQL
		StringBuffer inSql = new StringBuffer("select '����' counttype,");
		//ë��SQL
		StringBuffer profitSql = new StringBuffer("select 'ë�����' counttype,");
		//ë����SQL
		StringBuffer prorateSql = new StringBuffer("select 'ë����' counttype,");
		StringBuffer sql=new StringBuffer("");
		//ƴ�� �ꡢ�¡��ա��� ͳ�Ƶ�SQL���
		if(countRange.equals("��")){
			cal.setTime(date1);
			for(int i=1;i<=count;i++){
				String yy = cal.get(Calendar.YEAR)+"";
				String dd = cal.get(Calendar.DATE)<10?"0"+cal.get(Calendar.DATE):cal.get(Calendar.DATE)+"";
				String mm = cal.get(Calendar.MONTH)+1<10?"0"+(cal.get(Calendar.MONTH)+1):(cal.get(Calendar.MONTH)+1)+"";
				if(i!=1){
					inSql.append(",");
					profitSql.append(",");
					prorateSql.append(",");
				}
				inSql.append("sum(decode(to_date(to_char(cp.pro_time,'"+dateFormat+"'),'"+dateFormat+"'),")
				  .append("to_date('"+sdf.format(cal.getTime())+"','"+dateFormat+"'),round(nvl(")
				  .append("cp.fi_income")
				  .append(",0),2),0))||'' ")
				  .append("\"").append(yy).append("-").append(mm).append("-").append(dd).append("\"");
				profitSql.append("sum(decode(to_date(to_char(cp.pro_time,'"+dateFormat+"'),'"+dateFormat+"'),")
				  .append("to_date('"+sdf.format(cal.getTime())+"','"+dateFormat+"'),round(nvl(")
				  .append("cp.fi_income-cp.fi_cost")
				  .append(",0),2),0))||'' ")
				  .append("\"").append(yy).append("-").append(mm).append("-").append(dd).append("\"");
				prorateSql.append("rtrim(to_char((sum(decode(to_date(to_char(cp.pro_time,'"+dateFormat+"'),'"+dateFormat+"'),")
				  .append("to_date('"+sdf.format(cal.getTime())+"','"+dateFormat+"'),round(nvl(")
				  .append("cp.fi_income-cp.fi_cost")
				  .append(",0),2),0)) ")
				  .append("/")
				  .append("decode(sum(decode(to_date(to_char(cp.pro_time,'"+dateFormat+"'),'"+dateFormat+"'),")
				  .append("to_date('"+sdf.format(cal.getTime())+"','"+dateFormat+"'),round(nvl(")
				  .append("cp.fi_income")
				  .append(",0),2),0)),0,1,")
				  .append("sum(decode(to_date(to_char(cp.pro_time,'"+dateFormat+"'),'"+dateFormat+"'),")
				  .append("to_date('"+sdf.format(cal.getTime())+"','"+dateFormat+"'),round(nvl(")
				  .append("cp.fi_income")
				  .append(",0),2),0))")
				  .append("))*100,'fm9999990.99'),'.')||'%' ")
				  .append("\"").append(yy).append("-").append(mm).append("-").append(dd).append("\"");
				cal.set(Calendar.DATE, cal.get(Calendar.DATE)+1);
			}
		}else if(countRange.equals("��")){
			cal.setTime(date1);
			for(int i=1;i<=count;i++){
				String yy = cal.get(Calendar.YEAR)+"";
				String mm = cal.get(Calendar.MONTH)+1<10?"0"+(cal.get(Calendar.MONTH)+1):(cal.get(Calendar.MONTH)+1)+"";
				if(i!=1){
					inSql.append(",");
					profitSql.append(",");
					prorateSql.append(",");
				}
				inSql.append("sum(decode(to_date(to_char(cp.pro_time,'"+dateFormat+"'),'"+dateFormat+"'),")
				  .append("to_date('"+sdf.format(cal.getTime())+"','"+dateFormat+"'),round(nvl(")
				  .append("cp.fi_income")
				  .append(",0),2),0))||'' ")
				  .append("\"").append(yy).append("-").append(mm).append("\"");
				profitSql.append("sum(decode(to_date(to_char(cp.pro_time,'"+dateFormat+"'),'"+dateFormat+"'),")
				  .append("to_date('"+sdf.format(cal.getTime())+"','"+dateFormat+"'),round(nvl(")
				  .append("cp.fi_income-cp.fi_cost")
				  .append(",0),2),0))||'' ")
				  .append("\"").append(yy).append("-").append(mm).append("\"");
				prorateSql.append("rtrim(to_char((sum(decode(to_date(to_char(cp.pro_time,'"+dateFormat+"'),'"+dateFormat+"'),")
				  .append("to_date('"+sdf.format(cal.getTime())+"','"+dateFormat+"'),round(nvl(")
				  .append("cp.fi_income-cp.fi_cost")
				  .append(",0),2),0)) ")
				  .append("/")
				  .append("decode(sum(decode(to_date(to_char(cp.pro_time,'"+dateFormat+"'),'"+dateFormat+"'),")
				  .append("to_date('"+sdf.format(cal.getTime())+"','"+dateFormat+"'),round(nvl(")
				  .append("cp.fi_income")
				  .append(",0),2),0)),0,1, ")
				  .append("sum(decode(to_date(to_char(cp.pro_time,'"+dateFormat+"'),'"+dateFormat+"'),")
				  .append("to_date('"+sdf.format(cal.getTime())+"','"+dateFormat+"'),round(nvl(")
				  .append("cp.fi_income")
				  .append(",0),2),0))")
				  .append("))*100,'fm9999990.99'),'.')||'%' ")
				  .append("\"").append(yy).append("-").append(mm).append("\"");
				cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)+1);
			}
		}else if(countRange.equals("��") || countRange.equals("��")){
			countNum=Integer.parseInt(startCount);
			for(int i=1;i<=count;i++){
				if(i!=1){
					inSql.append(",");
					profitSql.append(",");
					prorateSql.append(",");
				}
				inSql.append("sum(decode(trunc(to_char(cp.pro_time,'"+dateFormat+"')),")
				  .append(countNum+",round(nvl(")
				  .append("cp.fi_income")
				  .append(",0),2),0))||'' ")
				  .append("\"").append(countNum).append(countRange).append("\"");
				profitSql.append("sum(decode(trunc(to_char(cp.pro_time,'"+dateFormat+"')),")
				  .append(countNum+",round(nvl(")
				  .append("cp.fi_income-cp.fi_cost")
				  .append(",0),2),0))||'' ")
				  .append("\"").append(countNum).append(countRange).append("\"");
				prorateSql.append("rtrim(to_char((sum(decode(trunc(to_char(cp.pro_time,'"+dateFormat+"')),")
				  .append(countNum+",round(nvl(")
				  .append("cp.fi_income-cp.fi_cost")
				  .append(",0),2),0)) ")
				  .append("/")
				  .append("decode(sum(decode(trunc(to_char(cp.pro_time,'"+dateFormat+"')),")
				  .append(countNum+",round(nvl(")
				  .append("cp.fi_income")
				  .append(",0),2),0)),0,1,")
				  .append("sum(decode(trunc(to_char(cp.pro_time,'"+dateFormat+"')),")
				  .append(countNum+",round(nvl(")
				  .append("cp.fi_income")
				  .append(",0),2),0))")
				  .append("))*100,'fm9999990.99'),'.')||'%' ")
				  .append("\"").append(countNum).append(countRange).append("\"");
				countNum++;
			}
		}

		sql.append(inSql);
		sql.append(" from cus_profit cp where cp.depart_Id=:departId ");
		if(cusId!=null){
			sql.append(" and cp.cus_id=:cusId");
			map.put("cusId", cusId);
		}
		sql.append(" union ");
		sql.append(profitSql);
		sql.append(" from cus_profit cp where cp.depart_Id=:departId");
		if(cusId!=null){
			sql.append(" and cp.cus_id=:cusId");
			map.put("cusId", cusId);
		}
		sql.append(" union ");
		sql.append(prorateSql);
		sql.append(" from cus_profit cp where cp.depart_Id=:departId ");
		if(cusId!=null){
			sql.append(" and cp.cus_id=:cusId");
			map.put("cusId", cusId);
		}
		return this.getPageBySqlMap(page, sql.toString(), map);
	}
	
}
