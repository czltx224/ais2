package com.xbwl.oper.stock.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.entity.BasDictionaryDetail;
import com.xbwl.entity.OprValueAddFee;
import com.xbwl.oper.stock.dao.IOprValueAddFeeDao;
import com.xbwl.oper.stock.service.IOprValueAddFeeService;
import com.xbwl.sys.dao.IBasDictionaryDetailDao;

/**
 *@author LiuHao
 *@time Sep 29, 2011 2:16:29 PM
 */
@Service("oprValueAddFeeServiceImpl")
@Transactional(rollbackFor={Exception.class})
public class OprValueAddFeeServiceImpl extends BaseServiceImpl<OprValueAddFee,Long> implements
		IOprValueAddFeeService {
	@Resource(name="oprValueAddFeeHibernateDaoImpl")
	private IOprValueAddFeeDao oprValueAddFeeDao;
	@Resource(name="basDictionaryDetailDaoImpl")
	private IBasDictionaryDetailDao basDictionaryDetailDao;
	@Override
	public IBaseDAO getBaseDao() {
		return oprValueAddFeeDao;
	}
	public List<OprValueAddFee> findChangeAddFee(List<OprValueAddFee> list,Long dno)
			throws Exception {
		StringBuffer sql=new StringBuffer("from OprValueAddFee ovaf where ovaf.id not in(");
		for (int i = 0; i < list.size(); i++) {
			sql.append(list.get(i).getId());
			if(i!=list.size()-1){
				sql.append(",");
			}
		}
		sql.append(") and ovaf.dno=? and ovaf.status=0");
		return this.find(sql.toString(),dno);
	}
	public List findAddFeeMsg(Date date,String dateType,String departCode) throws Exception {
		Map map=new HashMap();
		String countDate=new SimpleDateFormat("yyyy").format(date);
		StringBuffer sql=new StringBuffer();
		sql.append("select t.fee_name,");
		if(dateType.equals("月")){
			for (int i = 1; i <=12; i++) {
				sql.append("nvl(sum(decode(to_date(to_char(t.create_time,'yyyy-mm'),'yyyy-mm'),to_date('"+countDate+"-"+i+"','yyyy-mm'),t.fee_count)),0) \""+i+"月\"");
				if(i!=12){
					sql.append(",");
				}
			}
		}else if(dateType.equals("日")){
			countDate=new SimpleDateFormat("yyyy-MM").format(date);
			Calendar   c   =   Calendar.getInstance(); 
			c.setTime(date);
			int day=c.getActualMaximum(Calendar.DATE);  
			for (int i = 1; i <=day; i++) {
				sql.append("nvl(sum(decode(to_date(to_char(t.create_time,'yyyy-mm-dd'),'yyyy-mm-dd'),to_date('"+countDate+"-"+i+"','yyyy-mm-dd'),t.fee_count)),0) \""+i+"日\"");
				if(i!=day){
					sql.append(",");
				}
			}
		}
		sql.append(" from opr_value_add_fee t");
		if(departCode!=null&&!"".equals(departCode)){
			sql.append(",opr_fax_in ofi and t.d_no=ofi.d_no and ofi.cusDepartCode=:departCode ");
			map.put("departCode", departCode);
		}
		sql.append(" group by t.fee_name");
		return this.createSQLMapQuery(sql.toString(), map).list();
	}
	public List findDepartFeeMsg(Date date,String dateType) throws Exception {
		//根据数据库字段自动生成列
		List<BasDictionaryDetail> list=basDictionaryDetailDao.findBy("basDictionaryId", 145L);
		Map map=new HashMap();
		String countDate=null;
		String dateFromat=null;
		if(dateType.equals("月")){
			countDate=new SimpleDateFormat("yyyy-MM").format(date);
			dateFromat="yyyy-mm";
		}else if(dateType.equals("日")){
			dateFromat="yyyy-mm-dd";
			countDate=new SimpleDateFormat("yyyy-MM-dd").format(date);
		}
		StringBuffer sql=new StringBuffer("select t.cus_depart_code,t.cus_depart_name,(");
		for(int i=0;i<list.size();i++){
			BasDictionaryDetail bdd=list.get(i);
			String typeName=bdd.getTypeName();
			sql.append("t.\""+typeName+"\"");
			if(i!=list.size()-1){
				sql.append("+");
			}
		}
		sql.append(") addsum_fee,t.income,concat(to_char(round((");
		for(int i=0;i<list.size();i++){
			BasDictionaryDetail bdd=list.get(i);
			String typeName=bdd.getTypeName();
			sql.append("t.\""+typeName+"\"");
			if(i!=list.size()-1){
				sql.append("+");
			}
		}
		sql.append(")/decode(t.income,0,1,t.income),4)*100,'990.99'),'%') fee_rate,");
		for(int i=0;i<list.size();i++){
			BasDictionaryDetail bdd=list.get(i);
			String typeName=bdd.getTypeName();
			Long bId=bdd.getId();
			sql.append("t.\""+typeName+"\" m"+bId+"");
			if(i!=list.size()-1){
				sql.append(",");
			}
		}
		sql.append(" from (select tf.cus_depart_code,tf.cus_depart_name,");
		for(BasDictionaryDetail bdd:list){
			String typeName=bdd.getTypeName();
			sql.append("sum(decode(t.fee_name,'"+typeName+"',t.fee_count,0)) \""+typeName+"\",");
		}
		sql.append("sum(tf.cp_fee+tf.consignee_fee+tf.sonderzug_price+tf.cp_value_add_fee+tf.cus_value_add_fee+tf.cp_sonderzug_price) income");
		sql.append(" from opr_value_add_fee t,opr_fax_in tf where t.d_no(+)=tf.d_no");
		sql.append(" and to_date(to_char(t.create_time,'"+dateFromat+"'),'"+dateFromat+"')=to_date(:countDate,'"+dateFromat+"') ");
		map.put("countDate", countDate);
		sql.append(" group by tf.cus_depart_code,tf.cus_depart_name)t");
		return this.createSQLMapQuery(sql.toString(), map).list();
	}
	public List findFeeThan(Date beforeDate, Date afterDate) throws Exception {
		Map map=new HashMap();
		String bDate=new SimpleDateFormat("yyyy-MM").format(beforeDate);
		String aDate=new SimpleDateFormat("yyyy-MM").format(afterDate);
		map.put("beforeDate", bDate);
		map.put("afterDate", aDate);
		StringBuffer sql=new StringBuffer("select t.fee_name,t.before_date,t.after_date,(t.after_date-t.before_date) income_add from");
		sql.append("(select t.fee_name,");
		sql.append("sum(decode(to_date(to_char(t.create_time,'yyyy-mm'),'yyyy-mm'),to_date(:beforeDate,'yyyy-mm'),t.fee_count,0)) before_date,");
		sql.append("sum(decode(to_date(to_char(t.create_time,'yyyy-mm'),'yyyy-mm'),to_date(:afterDate,'yyyy-mm'),t.fee_count,0)) after_date");
		sql.append(" from opr_value_add_fee t group by t.fee_name)t");
		return this.createSQLMapQuery(sql.toString(), map).list();
	}

}
