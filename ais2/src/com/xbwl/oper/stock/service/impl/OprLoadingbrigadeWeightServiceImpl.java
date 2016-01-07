package com.xbwl.oper.stock.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.entity.OprLoadingbrigadeWeight;
import com.xbwl.oper.reports.util.AppendConditions;
import com.xbwl.oper.stock.dao.IOprLoadingbrigadeWeightDao;
import com.xbwl.oper.stock.service.IOprLoadingbrigadeWeightService;
import com.xbwl.oper.stock.vo.LoadingbrigadeTypeEnum;

/**
 * author CaoZhili time Jul 8, 2011 10:09:00 AM
 * 
 * 装卸组货量表服务层实现类
 */
@Service("oprLoadingbrigadeWeightServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class OprLoadingbrigadeWeightServiceImpl extends
		BaseServiceImpl<OprLoadingbrigadeWeight, Long> implements
		IOprLoadingbrigadeWeightService {

	@Resource(name = "oprLoadingbrigadeWeightHibernateDaoImpl")
	private IOprLoadingbrigadeWeightDao oprLoadingbrigadeWeightDao;

	@Resource(name="appendConditions")
	private AppendConditions appendConditions;
	
	@Override
	public IBaseDAO<OprLoadingbrigadeWeight, Long> getBaseDao() {

		return this.oprLoadingbrigadeWeightDao;
	}
	
	public String findTakeGoods(Map<String, String> map) throws Exception {
		StringBuffer sb = new StringBuffer();
		String countRange =map.get("countRange");
		String startCount =map.get("startCount");
		String endCount =map.get("endCount");
		String[] sts = new String[]{"cusId"};
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
		
		sb.append("select d.depart_name range,c.cus_name areatakegoods,");
		sb.append("sum(nvl(t.total_weight,0)) SUMCOL");
		if(countRange.equals("日")){
			cal.setTime(date1);
			for(int i=1;i<=count;i++){
				String yy = cal.get(Calendar.YEAR)+"";
				String dd = cal.get(Calendar.DATE)<10?"0"+cal.get(Calendar.DATE):cal.get(Calendar.DATE)+"";
				String mm = cal.get(Calendar.MONTH)+1<10?"0"+(cal.get(Calendar.MONTH)+1):(cal.get(Calendar.MONTH)+1)+"";
				sb.append(",sum(decode(to_date(to_char(t.create_time,'"+dateFormat+"'),'"+dateFormat+"'),to_date('"+sdf.format(cal.getTime())+"','"+dateFormat+"'),t.total_weight,0))")
				  .append(" \"").append(yy).append("-").append(mm).append("-").append(dd).append("\"");
				cal.set(Calendar.DATE, cal.get(Calendar.DATE)+1);
			}
		}else if(countRange.equals("月")){
			cal.setTime(date1);
			for(int i=1;i<=count;i++){
				String yy = cal.get(Calendar.YEAR)+"";
				String mm = cal.get(Calendar.MONTH)+1<10?"0"+(cal.get(Calendar.MONTH)+1):(cal.get(Calendar.MONTH)+1)+"";
				sb.append(",sum(decode(to_date(to_char(t.create_time,'"+dateFormat+"'),'"+dateFormat+"'),to_date('"+sdf.format(cal.getTime())+"','"+dateFormat+"'),t.total_weight,0))")
				  .append(" \"").append(yy).append("-").append(mm).append("\"");
				cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)+1);
			}
		}else if(countRange.equals("周") || countRange.equals("年")){
			countNum=Integer.parseInt(startCount);
			for(int i=1;i<=count;i++){
				sb.append(",sum(decode(round(to_char(t.create_time,'"+dateFormat+"')),"+countNum+", nvl(t.total_weight,0),0))")
				  .append(" \"").append(countNum).append(countRange).append("\"");
				countNum++;
			}
		}
		sb.append(" from opr_overmemo t,customer c,sys_depart d where t.start_depart_id=c.id and t.end_depart_id=d.depart_id");
		sb.append(this.appendConditions.appendCountDate(map));
		sb.append(this.appendConditions.appendConditions(map, sts));
		
		sb.append(" group by d.depart_name ,c.cus_name");
		return sb.toString();
	}

	public void saveLoadingWeight(List<OprLoadingbrigadeWeight> list,LoadingbrigadeTypeEnum enumType)throws Exception{
		
		for (OprLoadingbrigadeWeight entity:list) {
			entity.setLoadingbrigadeType(enumType.getValue());
			
			this.oprLoadingbrigadeWeightDao.save(entity);
		}
	}

	public String findSqlListService(Map<String, String> map) throws Exception {

		StringBuffer sb = new StringBuffer();
		String dno = map.get("t_dNo");
		sb.append("select t.id,t.overmemo_no,t.d_no,t.goods,t.weight,t.bulk,t.piece,")
		  .append("t.create_name,to_char(t.create_time,'yyyy-MM-dd HH24:mi') create_time,")
		  .append("t.update_name,to_char(t.update_time,'yyyy-MM-dd HH24:mi') update_time,")
		  .append("t.ts,t.loadingbrigade_id,t.dispatch_id,t.loadingbrigade_type,t.depart_id,")
		  .append("l.loading_name dispatchName,m.loading_name loadingbrigadeName")
		  .append(" from opr_loadingbrigade_weight t,bas_loadingbrigade l,bas_loadingbrigade m")
		  .append(" where t.dispatch_id=l.id(+) and t.loadingbrigade_id=m.id(+)");
		
		if(null!=dno && !"".equals(dno.trim())){
			sb.append(" and t.d_no=:t_dNo and t.depart_id=:t_departId");
		}else{
			sb.append(this.appendConditions.appendConditions(map, null));
			sb.append(this.appendConditions.appendCountDate(map));
		}
		
		return sb.toString();
	}
}
