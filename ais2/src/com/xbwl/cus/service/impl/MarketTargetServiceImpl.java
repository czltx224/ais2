package com.xbwl.cus.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.cus.dao.IMarketingTargetDao;
import com.xbwl.cus.service.IMarketTargetService;
import com.xbwl.entity.MarketingTarget;

/**
 * 销售、货量、票数指标分析服务层实现类
 *@author LiuHao
 *@time Dec 20, 2011 6:04:16 PM
 */
@Service("marketTargetServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class MarketTargetServiceImpl extends BaseServiceImpl<MarketingTarget,Long> implements
		IMarketTargetService {
	@Resource(name="marketTarHibernateDaoImpl")
	private IMarketingTargetDao marketingTargetDao;
	@Override
	public IBaseDAO getBaseDao() {
		return marketingTargetDao;
	}
	//REVIEW-ANN 增加注释
	//FIXED 接口中已有注释
	//REVIEW-ACCEPT 增加注释
	public List findMargetTargetMsg(String countArea, String targetType,
			Date countDate,Long isTarget) throws Exception {
		Map map=new HashMap();
		//REVIEW 使用前进行非空判断
		//FIXED LIUH
		if(countDate == null){
			throw new ServiceException("统计日期不能为空");
		}
		
		Calendar cal = Calendar.getInstance();      
		cal.setTime(countDate);
		cal.set(Calendar.DAY_OF_MONTH,   1);
		 //统计月份的第一天
		Date   firstDate = cal.getTime();
		cal.add(Calendar.MONTH,   1); 
		cal.add(Calendar.DAY_OF_MONTH,   -1); 
		//统计月份的最后一天
		Date lastDate = cal.getTime();
		
		map.put("firstDate", firstDate);
		map.put("lastDate", lastDate);
		
		String cDate=new SimpleDateFormat("yyyy").format(countDate);
		map.put("targetType", targetType);
		String groupName="";
		String term="";
		StringBuffer sql=new StringBuffer("select ");
		if("depart".equals(countArea)){
			term="t.cus_depart_code=o.cus_depart_code(+)";
			groupName="t.cus_depart_code,t.cus_depart_name";
		}else{
			term="t.depart_id=o.in_depart_id";
			groupName="t.depart_id,t.depart_name";
		}
		sql.append(groupName);
		sql.append(",");
		if(isTarget==1){
			for (int i = 1; i <= 12; i++) {
				//REVIEW 多次使用append来取代+运算
				//FIXED LIUH
				//sql.append("nvl(sum(case when to_date(to_char(t.target_time,'yyyy-mm'),'yyyy-mm')=to_date('"+cDate+"-"+i+"','yyyy-mm') and t.target_type=:targetType then t.target_num end),0) \""+i+"月\"");
				sql.append("nvl(sum(case when to_date(to_char(t.target_time,'yyyy-mm'),'yyyy-mm')=to_date('");
				sql.append(cDate);
				sql.append("-");
				sql.append(i);
				sql.append("','yyyy-mm') and t.target_type=:targetType then t.target_num end),0) \"");
				sql.append(i);
				sql.append("月\"");
				if(i!=12){
					sql.append(",");
				}
			}
			sql.append(" from marketing_target t group by ");
			sql.append(groupName);
		}else{
			for (int i = 1; i <=12; i++) {
				//REVIEW 多次使用append来取代+运算
				//FIXED LIUH
				//sql.append("(t.\""+i+"income\"/t.\""+i+"target\") \""+i+"月\"");
				sql.append("(t.\"");
				sql.append(i);
				sql.append("income\"/t.\"");
				sql.append(i);
				sql.append("target\") \"");
				sql.append(i);
				sql.append("月\"");
				if(i!=12){
					sql.append(",");
				}
			}
			sql.append(" from (select "+groupName+",");
			for (int i = 1; i <= 12; i++) {
				//REVIEW 多次使用append来取代+运算
				//FIXED LIUH
				//sql.append("nvl(sum(decode(to_date(to_char(o.create_time,'yyyy-mm'),'yyyy-mm'),to_date('"+cDate+"-"+i+"','yyyy-mm'),o.cp_fee+o.cp_value_add_fee+o.cp_sonderzug_price+o.cus_value_add_fee+o.consignee_fee)),0) \""+i+"income\",");
				sql.append("nvl(sum(decode(to_date(to_char(o.create_time,'yyyy-mm'),'yyyy-mm'),to_date('");
				sql.append(cDate);
				sql.append("-");
				sql.append(i);
				sql.append("','yyyy-mm'),o.cp_fee+o.cp_value_add_fee+o.cp_sonderzug_price+o.cus_value_add_fee+o.consignee_fee)),0) \"");
				sql.append(i);
				sql.append("income\",");
				//sql.append("nvl(sum(case when to_date(to_char(t.target_time,'yyyy-mm'),'yyyy-mm')=to_date('"+cDate+"-"+i+"','yyyy-mm') and t.target_type=:targetType then t.target_num end),1) \""+i+"target\"");
				sql.append("nvl(sum(case when to_date(to_char(t.target_time,'yyyy-mm'),'yyyy-mm')=to_date('");
				sql.append(cDate);
				sql.append("-");
				sql.append(i);
				sql.append("','yyyy-mm') and t.target_type=:targetType then t.target_num end),1) \"");
				sql.append(i);
				sql.append("target\"");
				if(i!=12){
					sql.append(",");
				}
			}
			//REVIEW 去掉1=1条件
			//FIXED LIUH
			sql.append(" from marketing_target t,opr_fax_in o ");
			sql.append(" where ");
			sql.append(term);
			sql.append(" group by ");
			sql.append(groupName);
			sql.append(")t");
		}
		return this.createSQLMapQuery(sql.toString(), map).list();
	}
	public List findTargetDepartCode(Long departId) throws Exception {
		Map map = new HashMap();
		StringBuffer sql = new StringBuffer("select distinct mt.cus_depart_code, mt.cus_depart_name from marketing_target mt ");
		if(departId != null){
			sql.append(" where mt.depart_id=:departId ");
			map.put("departId", departId);
		}
		sql.append(" group by mt.cus_depart_code, mt.cus_depart_name order by mt.cus_depart_code asc");
		return this.createSQLMapQuery(sql.toString(), map).list();
	}
}
