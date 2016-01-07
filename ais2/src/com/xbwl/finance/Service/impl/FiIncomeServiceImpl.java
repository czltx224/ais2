package com.xbwl.finance.Service.impl;


import java.rmi.ServerException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.orm.Page;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.common.utils.DoubleUtil;
import com.xbwl.entity.FiIncome;
import com.xbwl.entity.FiIncomeAccount;
import com.xbwl.finance.Service.IFiIncomeAccountService;
import com.xbwl.finance.Service.IFiIncomeService;
import com.xbwl.finance.dao.IFiIncomeDao;
import com.xbwl.finance.vo.FiIncomeBalanceVo;

/**
 *@author LiuHao
 *@time Aug 26, 2011 6:35:06 PM
 */
@Service("fiIncomeServiceImpl")
@Transactional(rollbackFor={Exception.class})
public class FiIncomeServiceImpl extends BaseServiceImpl<FiIncome,Long> implements
		IFiIncomeService {
	@Resource(name="fiIncomeHibernateDaoImpl")
	private IFiIncomeDao fiIncomeDao;
	
	//收入核算报表
	@Resource(name="fiIncomeAccountServiceImpl")
	private IFiIncomeAccountService fiIncomeAccountService;
	
	
	@Override
	public IBaseDAO getBaseDao() {
		return fiIncomeDao;
	}
	
	
	public String getTotalFiInIncome(Map map) throws Exception {
		Long departId = (Long)map.get("departId");
		String selectString = (String)map.get("selectString");
		Date startTime = (Date)map.get("startTime");
		Date endTime = (Date)map.get("endTime");
		Long dno =(Long) map.get("dno"); 
 		String certiNo = (String) map.get("certiNo");
 		String serviceCode=(String)map.get("serviceCode");
 		
		StringBuffer sb = new StringBuffer();

		sb.append("select  ");
					sb.append("sum(ther_amount) ther_amount, ");
					sb.append("sum(yfzz_amount) yfzz_amount, ");
					sb.append("sum(dfzz_amount) dfzz_amount, ");
					sb.append("sum(yfds_amount) yfds_amount, ");
					sb.append("sum(dfds_amount) dfds_amount, ");
					sb.append("sum(dfzc_amount) dfzc_amount, ");
					sb.append("sum(yfzc_amount) yfzc_amount, ");
					sb.append("sum(cqWeight) cqWeight, ");
					sb.append("sum(piece) piece ");
		  sb.append("From (select fi.d_no dno,");
		               sb.append("to_date(to_char(fi.create_time,'yyyy-mm-dd'),'yyyy-mm-dd') createTime, ");
		               sb.append("fi.source_data sourceData,");
		               sb.append(" min(fi.accounting) accounting,");    
		               sb.append(" min(fi.certi_no) certi_no ,");  
		               sb.append("decode(fi.amount_type, '其他收入', fi.source_no) as therNo,");
		               sb.append("max(fi.customer_name) customerName,");
		               sb.append("max(fi.customer_id) customerId,");
		               sb.append("max(NVL(ofi.piece, 0)) piece,");
		               sb.append("max(fi.id) id,");
		              sb.append(" max(fi.INCOME_DEPART_ID) departId,  ");
		               sb.append("max(fi.INCOME_DEPART) departName,");
		               sb.append("max(NVL(ofi.cus_weight, 0)) cqWeight,");
		               
		               sb.append("max(decode(fi.source_data, '更改',  ofi.flight_main_no,  '作废',ofi.flight_main_no,'传真录入', ofi.flight_main_no)) as flightMainNo,");
		               sb.append("sum(decode(fi.amount_type, '其他收入', fi.amount, 0)) as ther_amount,");
		               
		               sb.append(" sum(decode(fi.amount_type, '预付增值费', fi.amount, 0)) as yfzz_amount,");
		               sb.append("sum(decode(fi.amount_type, '到付增值费', fi.amount, 0)) as dfzz_amount,");
		               sb.append("sum(decode(fi.amount_type, '到付提送费', fi.amount, 0)) as dfds_amount,");
		               sb.append("sum(decode(fi.amount_type, '预付提送费', fi.amount, 0)) as yfds_amount,");
		               sb.append("sum(decode(fi.amount_type, '到付专车费', fi.amount, 0)) as dfzc_amount,");
		               sb.append("sum(decode(fi.amount_type, '预付专车费', fi.amount, 0)) as yfzc_amount,");
		               
		               sb.append("sum(decode(fi.amount_type, '其他收入', fi.amount, 0)) +          ");           
		               sb.append("sum(decode(fi.amount_type, '预付增值费', fi.amount, 0)) +");
		               sb.append("sum(decode(fi.amount_type, '到付增值费', fi.amount, 0)) +");
		               sb.append("sum(decode(fi.amount_type, '到付提送费', fi.amount, 0)) +");
		               sb.append("sum(decode(fi.amount_type, '预付提送费', fi.amount, 0)) +");
		               sb.append("sum(decode(fi.amount_type, '到付专车费', fi.amount, 0)) +");
		               sb.append("sum(decode(fi.amount_type, '预付专车费', fi.amount, 0)) total_amount,");
		               
		               sb.append("decode(max(ofi.in_depart),null,max(fi.depart_name),max(ofi.in_depart)) indepart,");
		               sb.append("decode(max(ofi.in_depart_id),null,max(fi.depart_id),max(ofi.in_depart_id)) inDepartId,");
		               sb.append("max(ofi.cus_depart_name) cusDepartName,");
		  
		       sb.append("min(ofi.cus_depart_code) cusDepartCode,");
		       sb.append("min(decode(fi.d_no, null,3,");
		                  sb.append("(select o.cash_status ");
		                     sb.append("from opr_status o ");
		                    sb.append("where o.d_no = fi.d_no))) cashStatus ");
		  sb.append("From fi_income fi, opr_fax_in ofi, sys_depart sd ");
		 sb.append("where fi.d_no = ofi.d_no(+) ");
		   sb.append("and fi.adm_depart_id = sd.depart_id(+) ");
		 sb.append("group by to_date(to_char(fi.create_time,'yyyy-mm-dd'),'yyyy-mm-dd'),fi.d_no, ");
		          sb.append("decode(fi.amount_type, '其他收入', fi.source_no), ");
		          sb.append("fi.source_data  ");
		  sb.append(" )    where  1=1    "); 

				if(dno!=null&&dno!=0l){
					sb.append("AND dno = :dno ");
				}
				if(serviceCode!=null&&!"".equals(serviceCode)){
					sb.append("AND cusDepartCode  like :serviceCode  ");
				}
				if(certiNo!=null&&!"".equals(certiNo)){
					sb.append("AND certi_no=:certiNo  ");
				}
				if(departId!=null&&departId!=0l){
					sb.append("AND departId=:departId  ");
				}
				if("会计日期".equals(selectString)){
					if(startTime==null&&endTime!=null){    
						sb.append("AND accounting <= to_date( :endTime ,'yyyy-mm-dd hh24:mi:ss') ");
					}else if(startTime!=null&&endTime==null){
						sb.append("AND accounting >=  to_date( :startTime ,'yyyy-mm-dd hh24:mi:ss')   ");
					}else if(startTime!=null&&endTime!=null){
						sb.append("AND accounting >=  to_date( :startTime ,'yyyy-mm-dd hh24:mi:ss')   ");
						sb.append("AND accounting <= to_date( :endTime ,'yyyy-mm-dd hh24:mi:ss')  ");
					}
				}
				if("创建日期".equals(selectString)){
					if(startTime==null&&endTime!=null){    
						sb.append("AND createTime <= to_date( :endTime ,'yyyy-mm-dd hh24:mi:ss') ");
					}else if(startTime!=null&&endTime==null){
						sb.append("AND createTime >=  to_date( :startTime ,'yyyy-mm-dd hh24:mi:ss')   ");
					}else if(startTime!=null&&endTime!=null){
						sb.append("AND createTime >=  to_date( :startTime ,'yyyy-mm-dd hh24:mi:ss')   ");
						sb.append("AND createTime <= to_date( :endTime ,'yyyy-mm-dd hh24:mi:ss')  ");
					}
				}
		return sb.toString();
	}


	/* 
	 * 盈亏平衡报表查询语句
	 */
	public String getAllincomeBalanceVo(FiIncomeBalanceVo vo) throws Exception {
		StringBuffer sb= new StringBuffer();
		sb.append("select  createTime,incomeAmount,doGoodCost,signDanCost,transitCost,outSideCost,therCost,totalCost,variableCostRate, ");
		sb.append(" round(breakEvenPoint,2 ) breakEvenPoint  ");  //保留两位小数
		sb.append(" from (");
			sb.append("select createTime,");
					sb.append("incomeAmount,");
					sb.append("doGoodCost,");  
					sb.append("signDanCost,");
					sb.append("transitCost,");
					sb.append("outSideCost,");
					sb.append("therCost,");
					sb.append("totalCost, ");
					sb.append("decode(incomeAmount,0,0,round(totalCost/incomeAmount, 2)) variableCostRate,");
					sb.append("decode(decode(incomeAmount,0,0,round(totalCost/incomeAmount, 2)),1,0,doGoodCost/(1-decode(incomeAmount,0,0,round(totalCost/incomeAmount, 2))))");
					sb.append("breakEvenPoint ");
	       sb.append("from ( ");
			        sb.append("select to_char(fi.create_time, 'yyyy-mm-dd') createTime,");
							sb.append("sum(fi.AMOUNT) incomeAmount,");
							sb.append("sum(decode(fc.cost_type, '提货成本', fc.cost_amount, 0)) as doGoodCost, ");
							sb.append("sum(decode(fc.cost_type, '签单成本', fc.cost_amount, 0)) as signDanCost,");
							sb.append("sum(decode(fc.cost_type, '中转成本', fc.cost_amount, 0)) as transitCost,");
							sb.append("sum(decode(fc.cost_type, '外发成本', fc.cost_amount, 0)) as outSideCost,");
							sb.append("sum(decode(fc.cost_type, '其他成本', fc.cost_amount, 0)) as therCost,");
							sb.append("sum(decode(fc.cost_type, '签单成本', fc.cost_amount, 0) +");
							//		sb.append("decode(fc.cost_type, '提货成本', fc.cost_amount, 0) +");
									sb.append("decode(fc.cost_type, '中转成本', fc.cost_amount, 0) +");
									sb.append("decode(fc.cost_type, '外发成本', fc.cost_amount, 0) +");
									sb.append("decode(fc.cost_type, '其他成本', fc.cost_amount, 0)) as totalCost   ");
				 sb.append("from fi_income fi, fi_cost fc ");
			     sb.append("where fi.d_no = fc.d_no(+) ");
								 sb.append("and fc.status(+) = 1 ");
								 sb.append(" and fi.create_time<to_date(to_char(:endTime1,'yyyy-mm-dd')||' 23:59:59','yyyy-mm-dd hh24:mi:ss') ");
								 sb.append(" and fi.create_time>to_date(to_char(:startTime1,'yyyy-mm-dd')||' 00:00:00','yyyy-mm-dd hh24:mi:ss') ");
				 sb.append(" group by to_char(fi.create_time, 'yyyy-mm-dd')) ");
       
		 sb.append("union all ");
				sb.append(" select createTime, ");
					 sb.append("incomeAmount,");
					 sb.append("doGoodCost,  ");
					 sb.append("signDanCost,");
					 sb.append("transitCost,");
					 sb.append("outSideCost,");
					 sb.append("therCost,");
					 sb.append(" totalCost,  ");
					 sb.append("decode(incomeAmount,0,0,round(totalCost/incomeAmount, 2)) variableCostRate,");
					 sb.append("decode(decode(incomeAmount,0,0,round(totalCost/incomeAmount, 2)),1,0,doGoodCost/(1-decode(incomeAmount,0,0,round(totalCost/incomeAmount, 2)))) ");
					 sb.append("breakEvenPoint ");
			sb.append(" from (  ");
					sb.append("select to_char(fc.create_time, 'yyyy-mm-dd') createTime,");
							sb.append("0 incomeAmount,");
							sb.append("sum(decode(fc.cost_type, '提货成本', fc.cost_amount, 0)) as doGoodCost,  ");
							sb.append("sum(decode(fc.cost_type, '签单成本', fc.cost_amount, 0)) as signDanCost,");
							sb.append("sum(decode(fc.cost_type, '中转成本', fc.cost_amount, 0)) as transitCost,");
							sb.append("sum(decode(fc.cost_type, '外发成本', fc.cost_amount, 0)) as outSideCost,");
							sb.append("sum(decode(fc.cost_type, '其他成本', fc.cost_amount, 0)) as therCost,");
							sb.append("sum(decode(fc.cost_type, '签单成本', fc.cost_amount, 0) + ");
						    	//		sb.append("decode(fc.cost_type, '提货成本', fc.cost_amount, 0) + ");
										sb.append("decode(fc.cost_type, '中转成本', fc.cost_amount, 0) + ");
										sb.append("decode(fc.cost_type, '外发成本', fc.cost_amount, 0) + ");
									 	sb.append("decode(fc.cost_type, '其他成本', fc.cost_amount, 0)) as totalCost  ");
					sb.append("from fi_cost fc ");
					sb.append("where ");
						   sb.append("        fc.create_time<to_date(to_char(:endTime2,'yyyy-mm-dd')||' 23:59:59','yyyy-mm-dd hh24:mi:ss') ");
						   sb.append("and fc.create_time>to_date(to_char(:startTime2,'yyyy-mm-dd')||' 00:00:00','yyyy-mm-dd hh24:mi:ss') ");
						   sb.append("and fc.status = 1 ");
						   sb.append("and to_char(fc.create_time, 'yyyy-mm-dd') not in ( ");
								   sb.append("select to_char(f.create_time, 'yyyy-mm-dd') ");
							       sb.append("from fi_income f  ");
									sb.append("where ");
									   sb.append("        f.create_time<to_date(to_char(:endTime3,'yyyy-mm-dd')||' 23:59:59','yyyy-mm-dd hh24:mi:ss') ");
									   sb.append("and f.create_time>to_date(to_char(:startTime3,'yyyy-mm-dd')||' 00:00:00','yyyy-mm-dd hh24:mi:ss') ");
								 sb.append(" )  ");
					sb.append("group by to_char(fc.create_time, 'yyyy-mm-dd')) ) order by createTime DESC ");
		return sb.toString();
	}
	
	//生成收入核算报表
	public void addAccountSingle(Long departId,String startTime,String endTime,Long seq) throws Exception{
		Double cpFee=0.0;//预付合计
		Double consigneeFee=0.0;//到付合计
		Double incomeAmount=0.0;//收入总额
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date accountData = sdf.parse(startTime);
		
		String hqlIncome="update FiIncome f set f.accountId=? where f.createTime between to_date('"+startTime+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+endTime+"','yyyy-mm-dd hh24:mi:ss') and f.incomeDepartId=?";
		this.fiIncomeAccountService.batchExecute(hqlIncome, seq,departId);
		
		String sql="select nvl(sum(case when f.who_cash='预付' then f.amount else 0 end),0) as cpFee, nvl(sum(case when f.who_cash='到付' then f.amount else 0 end),0) as consigneeFee from fi_income f where f.account_id=?";
		Map map =(Map) this.fiIncomeAccountService.createSQLQuery(sql, seq).list().get(0);
		cpFee=Double.valueOf(map.get("CPFEE")+"");//预付合计
		consigneeFee=Double.valueOf(map.get("CONSIGNEEFEE")+"");//到付合计
		incomeAmount=DoubleUtil.add(cpFee, consigneeFee);
		
		FiIncomeAccount fiIncomeAccount=new FiIncomeAccount();
		fiIncomeAccount.setTypeName("收入");
		fiIncomeAccount.setCpFee(cpFee);
		fiIncomeAccount.setConsigneeFee(consigneeFee);
		fiIncomeAccount.setIncomeAmount(incomeAmount);
		fiIncomeAccount.setAccountData(accountData);
		fiIncomeAccount.setDepartId(departId);
		fiIncomeAccount.setBatchNo(seq);
		this.fiIncomeAccountService.save(fiIncomeAccount);
	}
	
	public Page<FiIncome> findFiIncomeDetail(Page page,Map map) throws Exception{

		StringBuffer  hql=new StringBuffer();
		hql.append("from FiIncome f where f.accountId=:accountId");
		
		if(!"null".equals(map.get("costType")+"")&&!"".equals(map.get("costType")+"")){
			hql.append(" and f.amountType=:costType");
		}
		if(!"null".equals(map.get("dno")+"")){
			hql.append(" and f.dno=:dno");
		}
		
		Page<FiIncome> pageReturn=this.fiIncomeDao.findPage(page, hql.toString(), map);
		return pageReturn;
	}
	
	
	
	
	
	
}
