package com.xbwl.finance.Service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.entity.FiCost;
import com.xbwl.finance.Service.IFiCostService;
import com.xbwl.finance.dao.IFiCostDao;

/**
 * author shuw
 * time Sep 21, 2011 3:52:03 PM
 */
@Service("fiCostServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class FiCostServiceImpl extends BaseServiceImpl<FiCost,Long> implements
												IFiCostService {

	@Resource(name="fiCostHibernateDaoImpl")
	private IFiCostDao fiCostDao;
	
	@Override
	public IBaseDAO<FiCost, Long> getBaseDao() {
		return fiCostDao;
	}

	public double sumCostBySourceDataId(String sourceData, Long dno)
			throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append( " select sum(f.cost_amount)  sumAmount  from fi_cost f where f.d_no=? and f.data_source=? ");
		double sum=0.0;
		List list2 = fiCostDao.createSQLQuery(sb.toString(), dno,sourceData).list();
		if(list2.size()==0){
			return sum;
		}else{
			Map listMap  =(Map) list2.get(0);
			sum =Double.valueOf(listMap.get("SUMAMOUNT")+"");
			return sum;
		}
	}

	//代理汇总统计分析SQL拼接
	public String getAllCqList(Map<String, String> map) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb=getAllSql(sb);
		sb=getQueryCondition(map, sb);
		return sb.toString();
	}
	
	//拼接查询条件
	public StringBuffer getQueryCondition(Map<String,String> map ,StringBuffer sb) throws Exception{
		String startDate = map.get("GED_createTime");
		String endDate = map.get("LED_createTime");
		String cusId = map.get("EQL_cusId");
		String departId = map.get("EQL_departId");
		if(null!=startDate && !"".equals(startDate)){
			sb.append(" and ofi.create_time >=to_date( :LED_createTime||' 00:00:00' ,'yyyy-mm-dd hh24:mi:ss') ");
		}
		if(null!=endDate && !"".equals(endDate)){
			sb.append(" and ofi.create_time <=to_date( :GED_createTime||' 23:59:59' ,'yyyy-mm-dd hh24:mi:ss') ");
		}
		if(null!=cusId && !"".equals(cusId)){
			sb.append(" and ofi.cus_id = :EQL_cusId ");
		}
		if(null!=departId && !"".equals(departId)){
			sb.append(" and ofi.in_depart_id =:EQL_departId  ");
		}
		sb.append("  group by ofi.cus_id,ofi.cp_name ");
		return sb;
	}
	
	//拼接显示数据条件
	public StringBuffer getAllSql(StringBuffer sb){
		sb.append("select ");
				sb.append("count(*)  countdno, ");
				sb.append(" NVL(sum(ofi.piece), 0) piece, ");
				sb.append(" NVL(sum(ofi.cus_weight), 0) cusWeight, ");
				sb.append(" NVL(sum(f.signDanCost), 0) signDanCostFee,");
				sb.append(" NVL(sum(f.doGoodCost), 0) doGoodCostFee, ");
				sb.append(" NVL(sum(f.transitCost), 0) transitCostFee, ");
				sb.append(" NVL(sum(f.outSideCost), 0) outSideCostFee, ");
				sb.append(" NVL(sum(f.therCost), 0) therCostFee,");
				sb.append(" NVL(sum(f.totalCostFee), 0) totalCostFee, ");
				sb.append(" NVL(sum(fn.therAddFee), 0) therAddFee, ");
	         	sb.append(" NVL(sum(fn.cusValueAddFee), 0)  cusValueAddFee, ");
				sb.append(" NVL(sum(fn.sonderzugPrice), 0) sonderzugPrice, ");
				sb.append(" NVL(sum(fn.cpValueAddFee), 0) cpValueAddFee, ");
				sb.append(" NVL(sum(fn.consigneeFee), 0)  consigneeFee, ");
				sb.append(" NVL(sum(fn.cpFee), 0) cpFee, ");
				sb.append(" NVL(sum(fn.totalIncomeFee), 0) totalIncomeFee, "); 
			    sb.append(" NVL(sum(fn.yfSonderzugPrice), 0)  yfSonderzugPrice, ");
				sb.append(" ofi.cp_name cusName, ");
				sb.append(" ofi.cus_id cusId, ");
				sb.append(" NVL(sum(ofi.payment_collection ), 0) paymentcollection, ");
				sb.append(" NVL(sum(fn.totalIncomeFee), 0) - NVL(sum(f.totalCostFee), 0) grossProfitFee, ");
				sb.append(" (case NVl(sum(fn.totalIncomeFee), 0) ");
				sb.append(" when 0 then 0 ");
				sb.append(" else round((NVL(sum(fn.totalIncomeFee), 0) - NVL(sum(f.totalCostFee), 0)) / NVL(sum(fn.totalIncomeFee), 0),  2) ");
				sb.append(" end) maorate  ");
		sb.append(" from (select sum(decode(f.cost_type, '签单成本', f.cost_amount, 0)) as signDanCost, ");
				sb.append(" sum(decode(f.cost_type, '提货成本', f.cost_amount, 0)) as doGoodCost, ");
				sb.append(" sum(decode(f.cost_type, '中转成本', f.cost_amount, 0)) as transitCost, ");
				sb.append(" sum(decode(f.cost_type, '外发成本', f.cost_amount, 0)) as outSideCost, ");
				sb.append(" sum(decode(f.cost_type, '其他成本', f.cost_amount, 0)) as therCost, ");
				sb.append(" sum(decode(f.cost_type, '签单成本', f.cost_amount, 0) + ");
				sb.append("      decode(f.cost_type, '提货成本', f.cost_amount, 0) + ");
				sb.append("      decode(f.cost_type, '中转成本', f.cost_amount, 0) + ");
				sb.append("      decode(f.cost_type, '外发成本', f.cost_amount, 0) + ");
				sb.append("      decode(f.cost_type, '其他成本', f.cost_amount, 0)) as totalCostFee, ");
				sb.append("     f.d_no dno ");
				sb.append("  From fi_cost f where f.status=1 ");
   			    sb.append("  group by f.d_no) f, "); 
				sb.append(" (select fi.d_no dno, ");
				sb.append("   sum(decode(fi.amount_type, '其他收入', fi.amount, 0)) as therAddFee, ");
				sb.append("   sum(decode(fi.amount_type, '预付增值费', fi.amount, 0)) as cpValueAddFee, ");
				sb.append("   sum(decode(fi.amount_type, '到付专车费', fi.amount, 0)) as sonderzugPrice, ");
				sb.append("   sum(decode(fi.amount_type, '预付专车费', fi.amount, 0)) as yfSonderzugPrice, ");
				sb.append("   sum(decode(fi.amount_type, '到付增值费', fi.amount, 0)) as cusValueAddFee, ");
				sb.append("   sum(decode(fi.amount_type, '到付提送费', fi.amount, 0)) as consigneeFee, ");
		    	sb.append("   sum(decode(fi.amount_type, '预付提送费', fi.amount, 0)) as cpFee, ");
   			    sb.append("   sum(decode(fi.amount_type, '预付提送费', fi.amount, 0) + ");
				sb.append("      decode(fi.amount_type, '到付提送费', fi.amount, 0) + ");
				sb.append("      decode(fi.amount_type, '到付增值费', fi.amount, 0) + ");
				sb.append("      decode(fi.amount_type, '到付专车费', fi.amount, 0) + ");
				sb.append("      decode(fi.amount_type, '预付增值费', fi.amount, 0) + ");
				sb.append("      decode(fi.amount_type, '预付专车费', fi.amount, 0) +  ");
				sb.append("      decode(fi.amount_type, '其他收入', fi.amount, 0)) as totalIncomeFee ");
				sb.append("  From fi_income fi ");
				sb.append("    group by fi.d_no) fn, ");
				sb.append("    opr_fax_in ofi ");
				sb.append(" where ofi.d_no = f.dno(+) ");
						sb.append("   and ofi.status=1   AND ofi.d_no = fn.dno(+) ");
		return sb;
	}

}
