package com.xbwl.cus.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
import com.xbwl.cus.dao.ICusProductTypeDao;
import com.xbwl.cus.service.ICusProductTypeService;
import com.xbwl.entity.CusProducttype;
import com.xbwl.oper.reports.util.AppendConditions;
//REVIEW-ACCEPT ����ע��
//FIXED LIUH
/**
 *@author LiuHao
 *ͳ�Ʋ�Ʒ�ṹ���͵ķ����ʵ����
 *@time Oct 19, 2011 5:15:41 PM
 */
@Service("cusProductTypeServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class CusProductTypeServiceImpl extends BaseServiceImpl<CusProducttype,Long> implements
		ICusProductTypeService {
	@Resource(name="cusProductTypeHibernateDaoImpl")
	private ICusProductTypeDao cusProductTypeDao;
	@Resource(name="appendConditions")
	private AppendConditions appendConditions;
	@Override
	public IBaseDAO getBaseDao() {
		return cusProductTypeDao;
	}
	//REVIEW-ACCEPT ����ע��
	//FIXED �ӿ�����дע��
	public Page findCusProductMsg(Page page,String startCount,String endCount,String countRange,Long cusId) throws Exception {
		
//		if(date==null){
//			throw new ServiceException("ͳ�����ڲ���Ϊ��!");
//		}
//		String dateYear=new SimpleDateFormat("yyyy").format(date);
//		
//		Map map=new HashMap();
		StringBuffer sql=new StringBuffer("select t.product_type,");
//		for (int i = 1; i <= 12; i++) {
//			/*
//			 *
//			sql.append("concat(to_char(nvl(sum(decode(to_date(to_char(t.procude_time,'yyyy-MM'),'yyyy-MM'),to_date('"+dateYear+"-"+i+"','yyyy-mm'),t.pro_weight,0))/decode(sum(decode(to_date(to_char(t.procude_time,'yyyy'),'yyyy'),to_date('"+dateYear+"','yyyy'),t.pro_weight,0)),0,1,sum(decode(to_date(to_char(t.procude_time,'yyyy'),'yyyy'),to_date('"+dateYear+"','yyyy'),t.pro_weight,0)))*100,0),990.99),'%') \""+i+"��\"");
//			*/
//			//REVIEW-ACCEPT �����+���㻹�ǻ���ɴ�����String��������
//			//FIXED LIUH
//			//sql.append("sum(decode(to_date(to_char(t.procude_time,'yyyy-MM'),'yyyy-MM'),to_date('" +dateYear+"-"+i+"','yyyy-mm'),t.pro_weight,0)) \""+i+"��\"");
//			sql.append("sum(decode(to_date(to_char(t.procude_time,'yyyy-MM'),'yyyy-MM'),to_date('");
//			sql.append(dateYear);
//			sql.append("-");
//			sql.append(i);
//			sql.append("','yyyy-mm'),t.pro_weight,0)) ");
//			sql.append("\"");
//			sql.append(i);
//			sql.append("��\"");
//			if(i!=12){
//				sql.append(",");
//			}
//		}
		User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		Map map = new HashMap();
		int  count=1;
		int countNum=0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date1=new Date();
		Date date2=new Date();
		Calendar cal=Calendar.getInstance();
		String dateFormat="yyyy-MM-dd";
		//���ͳ�Ʒ�Χ �ꡢ�¡��ܡ��� ����ʱ��֮�������ʱ��
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
		//����ͳ��ά�� �ꡢ�¡��ܡ��� ƴ��SQL
		if(countRange.equals("��")){
			cal.setTime(date1);
			for(int i=1;i<=count;i++){
				String yy = cal.get(Calendar.YEAR)+"";
				String dd = cal.get(Calendar.DATE)<10?"0"+cal.get(Calendar.DATE):cal.get(Calendar.DATE)+"";
				String mm = cal.get(Calendar.MONTH)+1<10?"0"+(cal.get(Calendar.MONTH)+1):(cal.get(Calendar.MONTH)+1)+"";
				if(i!=1){
					sql.append(",");
				}
				sql.append("sum(decode(to_date(to_char(t.procude_time,'"+dateFormat+"'),'"+dateFormat+"'),")
				  .append("to_date('"+sdf.format(cal.getTime())+"','"+dateFormat+"'),round(nvl(")
				  .append("t.pro_weight")
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
				sql.append("sum(decode(to_date(to_char(t.procude_time,'"+dateFormat+"'),'"+dateFormat+"'),")
				  .append("to_date('"+sdf.format(cal.getTime())+"','"+dateFormat+"'),round(nvl(")
				  .append("t.pro_weight")
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
				sql.append("sum(decode(trunc(to_char(t.procude_time,'"+dateFormat+"')),")
				  .append(countNum+",round(nvl(")
				  .append("t.pro_weight")
				  .append(",0),2),0)) ")
				  .append("\"").append(countNum).append(countRange).append("\"");
				countNum++;
			}
		}
		sql.append(" from cus_producttype t where t.depart_id=:departId");
		map.put("departId", user.get("bussDepart"));
		if(cusId!=null){
			sql.append(" and t.cus_id=:cusId");
			map.put("cusId", cusId);
		}
		sql.append(" group by t.product_type");
		return this.getPageBySqlMap(page, sql.toString(), map);
	}
}
