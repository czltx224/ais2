package com.xbwl.oper.reports.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.orm.PropertyFilter;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.entity.OprEnterPortKpi;
import com.xbwl.oper.reports.dao.IOprEnterPortKpiDao;
import com.xbwl.oper.reports.service.IOprEnterPortKpiService;
import com.xbwl.oper.reports.util.AppendConditions;

/**
 * author CaoZhili time Nov 10, 2011 1:47:58 PM
 */

@Service("oprEnterPortKpiServiceImpl")
@Transactional(rollbackFor=Exception.class)
public class OprEnterPortKpiServiceImpl extends
		BaseServiceImpl<OprEnterPortKpi, Long> implements IOprEnterPortKpiService {

	@Resource(name="oprEnterPortKpiHibernateDaoImpl")
	private IOprEnterPortKpiDao enterPortKpiDao;
	
	@Resource(name="appendConditions")
	private AppendConditions appendCond;
	
	@Override
	public IBaseDAO<OprEnterPortKpi, Long> getBaseDao() {
		return this.enterPortKpiDao;
	}

	@Override
	public void save(OprEnterPortKpi entity) {
		Long bussDepartId = entity.getDeptId();
		
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		PropertyFilter filter = null;
		if(null!=entity.getId() && entity.getId()>0){
			filter = new PropertyFilter("NEL_id",entity.getId()+"");
			filters.add(filter);
		}
		if(null!=entity.getDeptId() && entity.getDeptId()>0){
			filter = new PropertyFilter("EQL_deptId",entity.getDeptId()+"");
			filters.add(filter);
		}
		if(null!=entity.getKpiName() && !"".equals(entity.getKpiName())){
			filter = new PropertyFilter("EQS_kpiName",entity.getKpiName());
			filters.add(filter);
		}
		if(null!=entity.getKpiType() && !"".equals(entity.getKpiType())){
			filter = new PropertyFilter("EQS_kpiType",entity.getKpiType());
			filters.add(filter);
		}
		if(null!=entity.getOperateType() && !"".equals(entity.getOperateType())){
			filter = new PropertyFilter("EQS_operateType",entity.getOperateType());
			filters.add(filter);
		}
		if(null!=entity.getCountRange() && !"".equals(entity.getCountRange())){
			filter = new PropertyFilter("EQS_countRange",entity.getCountRange());
			filters.add(filter);
		}
		
		List<OprEnterPortKpi> list = this.enterPortKpiDao.find(filters);
		if(null==list || list.size()==0){
			super.save(entity);
		}else{
			throw new ServiceException("该KPI已经存在！");
		}
	}

	public String findKpiReportService(Map<String, String> map)
			throws Exception {
		StringBuffer sb = new StringBuffer();
		
		sb.append("select id,kpi_name kpiname,manager_target managertarget,true_finish truefinish,kpi_color kpicolor,kpi_year kpiyear,kpi_month kpimonth,")
		  .append("kpi_day kpiday,to_char(kpi_date,'yyyy-MM-dd WW') kpidate,warning_percent warningpercent,count_range countRange,")
		  .append("warning_rate warningrate,qualified_num qualifiednum,total_num totalnum,duty_depart_id dutydepartid,")
		  .append("duty_depart_name dutydepartname,parent_depart_id parentdepartid,parent_depart_name parentdepartname,")
		  .append("OPERATE_TYPE OPERATETYPE,to_char(opr_date,'yyyy-MM-dd') opr_date")
		  //REVIEW 去掉1=1条件 -- 可能没有带条件的
		  .append(" from opr_kpi_count_report where count_range=:countRange");
		//添加日期条件
		sb.append(this.appendCond.appendCountDate(map));
		//添加查询条件
		sb.append(this.appendCond.appendConditions(map,null));
		sb.append(" order by kpi_date ");
		return sb.toString();
	}
}
