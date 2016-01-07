package com.xbwl.oper.reports.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.entity.OprEnterPortReport;
import com.xbwl.oper.reports.dao.IOprEnterPortReportDao;
import com.xbwl.oper.reports.service.IOprEnterPortReportService;
import com.xbwl.oper.reports.util.AppendConditions;

/**
 * author CaoZhili
 * time Nov 9, 2011 4:30:35 PM
 */
@Service("oprEnterPortReportServiceImpl")
@Transactional(rollbackFor=Exception.class)
public class OprEnterPortReportServiceImpl extends BaseServiceImpl<OprEnterPortReport, Long> implements IOprEnterPortReportService{

	@Resource(name="oprEnterPortReportHibernateDaoImpl")
	private IOprEnterPortReportDao enterPortReportDao;	
	
	@Resource(name="appendConditions")
	private AppendConditions appendCond;
	
	@Override
	public IBaseDAO<OprEnterPortReport, Long> getBaseDao() {
		
		return this.enterPortReportDao;
	}

	public String findListService(Map<String, String> map) throws Exception {
		StringBuffer sb = new StringBuffer();
		
		sb.append("SELECT  t0.ID  AS ID , to_char(t0.COUNT_DATE,'yyyy-MM-dd WW')  AS COUNTDATE , to_char(t0.FLIGHT_DATE,'yyyy-MM-dd') AS FLIGHTDATE,t0.FLIGHT_NO  AS  ")
			.append("FLIGHTNO , t0.CAR_CODE  AS CARCODE , t0.FLIGHT_LANDING_TIME  AS  ")
			.append("FLIGHTLANDINGTIME , t0.TAKE_USE_TIME  AS TAKEUSETIME ,  ")
			.append("t0.ENTER_PORT_LIMITATION  AS ENTERPORTLIMITATION , t0.DNO  AS DNO ,")  
			.append("t0.TAKE_IS_STANDARD  AS TAKEISSTANDARD , t0.ENTER_IS_STANDARD  AS  ")
			.append("ENTERISSTANDARD , t0.TAKE_DUTY_UNIT  AS TAKEDUTYUNIT ,  ")
			.append("t0.CAR_RUN_TIME  AS CARRUNTIME , t0.TAKE_CAR_STANDARD_TIME  AS")  
			.append(" TAKECARSTANDARDTIME , t0.CAR_RUN_IS_STANDARD  AS  ")
			.append("CARRUNISSTANDARD , t0.TAKE_STANDARD_TIME  AS TAKESTANDARDTIME ,")  
			.append("t0.ENTER_PORT_STANDARD_TIME  AS ENTERPORTSTANDARDTIME , t0.CP_NAME  AS ") 
			.append("CPNAME , t0.DEPT_NAME  AS DEPTNAME , t0.DEPT_ID  AS DEPTID ,  ")
			.append("to_char(t0.AIRPORT_GOCAR_TIME,'yyyy-MM-dd hh24:mi')  AS AIRPORTGOCARTIME , to_char(t0.ENTER_STOCK_TIME,'yyyy-MM-dd hh24:mi')  AS")  
			.append(" ENTERSTOCKTIME   ")
			//REVIEW 去掉1=1条件 -- 如果没有其他条件我不能加where
		    .append("FROM  aisuser.OPR_ENTER_PORT_REPORT t0  where 1=1");
		
		//添加日期条件
		sb.append(appendCond.appendCountDate(map));
		
		//添加查询条件
		sb.append(appendCond.appendConditions(map,null));
		
		return sb.toString();
	}

	public String findOutListService(Map<String, String> map) throws Exception {
		StringBuffer sb = new StringBuffer();
		
		sb.append("select t.id,to_char(t.count_date,'yyyy-MM-dd WW') as countDate,to_char(t.out_date,'yyyy-MM-dd') as outDate,t.dno,t.cp_name as cpName,t.operation_type as operationtype,  ")
			.append("to_char(t.out_stock_time,'yyyy-MM-dd hh24:mi') as outstocktime, to_char(t.out_standard_time,'yyyy-MM-dd hh24:mi')as outstandardtime,")
			.append("t.out_is_standart as outisstandart,t.out_defer_hour as outdeferhour,t.dept_id as deptId, ")
			.append("t.dept_name as deptName,to_char(t.reach_time,'yyyy-MM-dd hh24:mi') as reachTime,f.real_go_where")
			//REVIEW 去掉1=1条件 -- 如果没有其他条件我不能加where
			.append(" from opr_out_port_report t,opr_fax_in f  where t.dno=f.d_no(+)");
		
		//添加日期条件
		sb.append(appendCond.appendCountDate(map));
		
		//添加查询条件
		sb.append(appendCond.appendConditions(map,null));
		
		return sb.toString();
	}

	public String findArteryCarListService(Map<String, String> map)
			throws Exception {
		StringBuffer sb = new StringBuffer();
		
		sb.append("select t.id,t.start_depart_id as startdepartid,t.start_depart_name as startdepartname,  ")
			.append("t.end_depart_id as endDepartId,t.end_depart_name as endDepartName, t.car_code as carCode,")
			.append("t.car_number as carNumber,to_char(t.count_date,'yyyy-MM-dd hh24:mi') as countDate,")
			.append("to_char(t.standard_reachcar_time,'yyyy-MM-dd hh24:mi')  as standardreachcartime,")
			.append(" to_char(t.true_reachcar_time,'yyyy-MM-dd hh24:mi') as truereachcartime,to_char(t.standard_out_time ,'yyyy-MM-dd hh24:mi')  as standardouttime,")  
			.append("to_char(t.true_out_time ,'yyyy-MM-dd hh24:mi') as trueoutTime,t.standard_use_hour as standardusehour, ")
			.append("t.true_use_hour trueUseHour,to_char(t.standard_reachstock_time ,'yyyy-MM-dd hh24:mi') as standardreachstocktime, ")
			.append("to_char(t.true_reachstock_time  ,'yyyy-MM-dd hh24:mi') as truereachstocktime,t.reachcar_is_standard as reachcarisstandard,")  
			.append("t.outcar_is_standard as outcarisstandard,t.run_is_standard as runisstandard, t.reach_is_standard as REACHSTOCKISSTANDARD, ")
			.append("t.driver_name as driverName,t.cargo_name as cargoName,t.reach_name reachName,t.loading_unloading_group as loadingunloadinggroup,")  
			.append("t.duty_unit as dutyUnit ") 
			//REVIEW 去掉1=1条件 -- 如果没有其他条件我不能加where
			.append("from opr_arterycar_report t where 1=1");
		
		//添加日期条件
		sb.append(appendCond.appendCountDate(map));
		
		//添加查询条件
		sb.append(appendCond.appendConditions(map,null));
		
		return sb.toString();
	}

	public String findSignListService(Map<String, String> map) throws Exception {
		StringBuffer sb = new StringBuffer();
		
		sb.append("select t.id,to_char(t.count_date,'yyyy-MM-dd WW') as countDate,to_char(t.sign_date,'yyyy-MM-dd hh24:mi') as signDate,")
		  .append("t.dno,to_char(t.reach_time ,'yyyy-MM-dd hh24:mi') as OUTTIME, ")
		  .append("t.operation_type as operationtype,t.out_car_time as outCarTime,t.sign_is_standard  as signIsStandard,")
		  .append("t.sign_defer_hour as signDeferHour,t.dept_id as deptId,t.dept_name as deptName,")
		  .append("to_char(t.sign_standard_time,'yyyy-MM-dd hh24:mi') as signStandardTime,")
		  .append("f.real_go_where,f.addr,f.cp_name")
		  //REVIEW 去掉1=1条件 -- 如果没有其他条件我不能加where
		  .append(" from opr_sign_report t,opr_fax_in f where t.dno=f.d_no(+)") ;
		
		//添加日期条件
		sb.append(appendCond.appendCountDate(map));
		
		//添加查询条件
		sb.append(appendCond.appendConditions(map,null));
		
		return sb.toString();
	}

	public String findReceiptComfirmListService(Map<String, String> map)
			throws Exception {
		StringBuffer sb = new StringBuffer();
		
		sb.append("select t.id,to_char(t.count_date,'yyyy-MM-dd WW') as countDate,t.dno,t.website,t.operation_type as operationtype,")
		    .append("t.out_time_point as OUTTIMEPOINT,to_char(t.sign_time,'yyyy-MM-dd hh24:mi') signTime,to_char(t.comfirm_time,'yyyy-MM-dd hh24:mi') as comfirmTime,")
		    .append("to_char(t.comfirm_standard_time,'yyyy-MM-dd hh24:mi') as comfirmstandardtime,t.comfirm_is_standard as comfirmisstandard,")
		    .append("t.defer_hour as deferHour,t.dept_id as deptId,t.dept_name as deptName,")
		    .append("f.real_go_where,to_char(s.out_time,'yyyy-MM-dd hh24:mi') out_time")
		    //REVIEW 去掉1=1条件 -- 如果没有其他条件我不能加where
		    .append(" from opr_receipt_comfirm_report t,opr_fax_in f,opr_status s where t.dno=f.d_no and f.d_no=s.d_no");
		
		//添加日期条件
		sb.append(appendCond.appendCountDate(map));
		
		//添加查询条件
		sb.append(appendCond.appendConditions(map,null));
		
		return sb.toString();
	}

	public String findScanningListService(Map<String, String> map)
			throws Exception {
		StringBuffer sb = new StringBuffer();
		
		sb.append("select t.id,to_char(t.count_date,'yyyy-MM-dd WW') as countDate,to_char(t.SCANNING_DATE,'yyyy-MM-dd') as SCANNINGDATE,t.dno,t.operation_type operationtype,")
			.append("to_char(t.sign_time,'yyyy-MM-dd hh24:mi') as signTime,to_char(t.scanning_time,'yyyy-MM-dd hh24:mi') as scanningtime,")
			.append("to_char(t.Scanning_Standard_Time,'yyyy-MM-dd hh24:mi') as scanningStandardTime,")
		    .append("t.scanning_limitation scanninglimitation,t.scanning_is_standard scanningisstandard,t.receipt_is_comfirm as receiptIsComfirm,")
		    .append("t.defer_hour as deferHour,t.dept_id as deptId,t.dept_name as deptName")
		    //REVIEW 去掉1=1条件 -- 如果没有其他条件我不能加where
		    .append(" from opr_scanning_report t where 1=1 ");
		
		//添加日期条件
		sb.append(appendCond.appendCountDate(map));
		
		//添加查询条件
		sb.append(appendCond.appendConditions(map,null));
		
		return sb.toString();
	}

	public String findCarLoadingRateListService(Map<String, String> map)
			throws Exception {
		
		StringBuffer sb = new StringBuffer();
		sb.append("select route_number routenumber,car_code carcode,type_code typeCode,nvl(maxload_weight, 0) || 'KG' maxloadweight,")
          .append("nvl(weight, 0) || 'KG' AS WEIGHT,loadingRate,deptId,deptName,to_char(start_time, 'yyyy-MM-dd hh24:mi') as startTime,")
          .append("use_car_type as usecartype,rent_car_result as rentcarresult,loading_name as loadingname")
          .append(" from (select t1.route_number ,t1.car_code ,c.type_code ,maxload_weight,WEIGHT,nvl(to_char((t1.weight / c.maxload_weight) * 100,")
          .append("'fm9999999999999999.00'),0) + 0 loadingRate,deptId,deptName,start_Time,use_car_type ,rent_car_result ,loading_name")
		  .append(" from (select o.route_number,o.car_id,o.car_code,o.start_time,")
	      .append("sum(o.total_weight) as weight,o.start_depart_id as deptId,d.depart_name as deptName,o.use_car_type,o.rent_car_result,l.loading_name ")
	      .append(" from opr_overmemo o,sys_depart d, ")
	      .append("(select tt.overmemo_no, ll.loading_name from opr_loadingbrigade_weight tt, bas_loadingbrigade ll")
          .append(" where tt.loadingbrigade_id = ll.id group by tt.overmemo_no, ll.loading_name) l")
	      .append(" where o.start_depart_id = d.depart_id and o.id = l.overmemo_no(+)")
	      .append(" group by o.route_number,o.car_id,o.car_code,o.start_depart_id,d.depart_name,o.start_time,o.use_car_type,o.rent_car_result,l.loading_name")
	      .append(" ) t1,bas_car c")
	      .append(" where t1.car_code=c.car_code(+)");
		//REVIEW 去掉1=1条件 -- 如果没有其他条件我不能加where
		sb.append(") where 1=1 ");
		    
		//添加日期条件
		sb.append(appendCond.appendCountDate(map));
		
		//添加查询条件
		sb.append(appendCond.appendConditions(map,null));
		
		//sb.append(" group by routenumber,carcode,typecode,maxload_weight,weight,deptId,deptName,startTime");
		//sb.append(" order by route_number desc");
		return sb.toString();
	}

	public String findArteryCarCountService(Map<String, String> map)
			throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("select t.id,t.manager_target managertarget,t.true_finish truefinish,t.kpi_color kpicolor,")
		  .append("t.kpi_year kpiyear,t.kpi_month kpimonth,t.kpi_day kpiday,to_char(t.kpi_date,'yyyy-MM-dd WW') kpidate,t.warning_percent warningpercent,")
		  .append("t.warning_rate warningrate, t.qualified_num qualifiednum,t.total_num totalnum,t.duty_depart_id dutydepartid,")
		  .append("t.duty_depart_name dutydepartname,t.parent_depart_id parentdepartid,t.parent_depart_name parentdepartname,")
		  .append("t.count_rate_name countratename,to_char(t.opr_date,'yyyy-MM-dd') opr_date ")
		  //REVIEW 去掉1=1条件 -- 如果没有其他条件我不能加where
		  .append(" from opr_arterycar_count_report t where 1=1");
		    
	    //添加日期条件
		sb.append(appendCond.appendCountDate(map));
		
		//添加查询条件
		sb.append(appendCond.appendConditions(map,null));
		
		return sb.toString();
	}
}
