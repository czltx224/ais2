package com.xbwl.finance.Service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.hibernate.Query;
import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.orm.Page;
import com.xbwl.common.orm.PropertyFilter;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.entity.BasProjCusTransitRate;
import com.xbwl.finance.Service.IBasProjCusTransitRateService;
import com.xbwl.finance.dao.IBasProjCusTransitRateDao;

/**
 * author CaoZhili time Nov 29, 2011 9:34:36 AM
 */
@Service("basProjCusTransitRateServiceImpl")
@Transactional(rollbackFor=Exception.class)
public class BasProjCusTransitRateServiceImpl extends
		BaseServiceImpl<BasProjCusTransitRate, Long> implements
		IBasProjCusTransitRateService {

	@Resource(name="basProjCusTransitRateHibernateDaoImpl")
	private IBasProjCusTransitRateDao basProjCusTransitRateDao;
	
	@Override
	public IBaseDAO<BasProjCusTransitRate, Long> getBaseDao() {
		return this.basProjCusTransitRateDao;
	}

	@Override
	public void save(BasProjCusTransitRate entity) {
		User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		Long bussDepartId = Long.parseLong(user.get("bussDepart") + "");
		entity.setDepartId(bussDepartId);
		
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		PropertyFilter filter = null;
		if(null!=entity.getId() && entity.getId()>0){
			filter = new PropertyFilter("NEL_id",entity.getId()+"");
			filters.add(filter);
		}
		if(null!=entity.getCusId() && entity.getCusId()>0){
			filter = new PropertyFilter("EQL_cusId",entity.getCusId()+"");
			filters.add(filter);
		}
		if(null!=entity.getCusName() && !"".equals(entity.getCusName())){
			filter = new PropertyFilter("EQS_cusName",entity.getCusName());
			filters.add(filter);
		}
		if(null!=entity.getTrafficMode() && !"".equals(entity.getTrafficMode())){
			filter = new PropertyFilter("EQS_trafficMode",entity.getTrafficMode());
			filters.add(filter);
		}
		if(null!=entity.getTakeMode() && !"".equals(entity.getTakeMode())){
			filter = new PropertyFilter("EQS_takeMode",entity.getTakeMode());
			filters.add(filter);
		}
		if(null!=entity.getAreaType() && !"".equals(entity.getAreaType())){
			filter = new PropertyFilter("EQS_areaType",entity.getAreaType());
			filters.add(filter);
		}
		if(null!=entity.getValuationType() && !"".equals(entity.getValuationType())){
			filter = new PropertyFilter("EQS_valuationType",entity.getValuationType());
			filters.add(filter);
		}
		if(null!=entity.getConditionUnit() && !"".equals(entity.getConditionUnit())){
			filter = new PropertyFilter("EQS_conditionUnit",entity.getConditionUnit());
			filters.add(filter);
		}
		if(null!=entity.getDepartId() && !"".equals(entity.getDepartId())){
			filter = new PropertyFilter("EQL_departId",entity.getDepartId()+"");
			filters.add(filter);
		}
		if(null!=entity.getCpId() && entity.getCpId()>0){
			filter = new PropertyFilter("EQL_cpId",entity.getCpId()+"");
			filters.add(filter);
		}
		if(null!=entity.getSpeTown() && !"".equals(entity.getSpeTown())){
			filter = new PropertyFilter("EQS_speTown",entity.getSpeTown());
			filters.add(filter);
		}
		List<BasProjCusTransitRate> list = this.basProjCusTransitRateDao.find(filters);
		
		if(null!=list && list.size()>0){
			throw new ServiceException("该项目客户中转协议价已经存在！");
		}else{
			super.save(entity);
		}
	}

	public Page<BasProjCusTransitRate> findProTraRate(Page page, Long piece,
			Double weight, Double bulk, Long cusId, Long cpId, String areaType,
			String takeMode, String trafficMode,Long disDepartId,String town) throws Exception {
		Page<BasProjCusTransitRate> page1=null;
		Map map=new HashMap();
		
		//map.put("piece", piece);
		//map.put("weight", weight);
		//map.put("bulk", bulk);
		map.put("cusId", cusId);
		map.put("takeMode", takeMode);
		map.put("trafficMode", trafficMode);
		map.put("disDepartId", disDepartId);
		map.put("town", town);
		map.put("valaType", "体积");
		map.put("valaVal", bulk);
		StringBuffer sql = new StringBuffer("select id, condition_unit, min_value, max_value, VALUATION_TYPE, rate, low_fee, cus_id, cus_name, depart_name, depart_id, create_time, create_name, update_name, update_time, ts from BAS_PROJ_CUS_TRANSIT_RATE bpr where (bpr.condition_Unit=:valaType and :valaVal between bpr.min_Value and bpr.max_Value)  and bpr.cus_Id=:cusId and bpr.spe_town=:town and bpr.take_mode=:takeMode and bpr.traffic_mode=:trafficMode and bpr.depart_id=:disDepartId ");
		if(cpId!=null){
			map.put("cpId", cpId);
			sql.append("and bpr.cp_id=:cpId ");
		}
		page1 = this.getPageBySqlMap(page, sql.toString(), map);
		if(page1.getResultMap().size()<1){
			map.put("valaType", "件数");
			map.put("valaVal", piece);
			page1 = this.getPageBySqlMap(page, sql.toString(), map);
			if(page1.getResultMap().size()<1){
				map.put("valaType", "重量");
				map.put("valaVal", weight);
				page1 = this.getPageBySqlMap(page, sql.toString(), map);
			}
		}
		if(page1.getResultMap().size()<1){
			map.put("areaType", areaType);
			map.put("valaType", "体积");
			map.put("valaVal", bulk);
			sql= new StringBuffer("select id, condition_unit, min_value, max_value, VALUATION_TYPE, rate, low_fee, cus_id, cus_name, depart_name, depart_id, create_time, create_name, update_name, update_time, ts from BAS_PROJ_CUS_TRANSIT_RATE bpr where (bpr.condition_Unit=:valaType and :valaVal between bpr.min_Value and bpr.max_Value)  and bpr.cus_Id=:cusId and bpr.area_type=:areaType and bpr.take_mode=:takeMode and bpr.traffic_mode=:trafficMode and bpr.depart_id=:disDepartId ");
			if(cpId!=null){
				map.put("cpId", cpId);
				sql.append("and bpr.cp_id=:cpId ");
			}
			page1 = this.getPageBySqlMap(page, sql.toString(), map);
			if(page1.getResultMap().size()<1){
				map.put("valaType", "件数");
				map.put("valaVal", piece);
				page1 = this.getPageBySqlMap(page, sql.toString(), map);
				if(page1.getResultMap().size()<1){
					map.put("valaType", "重量");
					map.put("valaVal", weight);
					page1 = this.getPageBySqlMap(page, sql.toString(), map);
				}
			}
		}
		
//		page1=this.getPageBySqlMap(page, sql.toString(), map);
//		if(page1.getResultMap().size()<1){
//			map.put("weight", weight);
//			sql=new StringBuffer("select id, condition_unit, min_value, max_value, VALUATION_TYPE, rate, low_fee, cus_id, cus_name, depart_name, depart_id, create_time, create_name, update_name, update_time, ts from BAS_PROJ_CUS_TRANSIT_RATE bpr where (bpr.condition_Unit='重量' and :weight between bpr.min_Value and bpr.max_Value)  and bpr.cus_Id=:cusId and bpr.area_type=:areaType and bpr.take_mode=:takeMode and bpr.traffic_mode=:trafficMode and bpr.depart_id=:disDepartId ");
//			if(cpId!=null){
//				map.put("cpId", cpId);
//				sql.append("and bpr.cp_id=:cpId ");
//			}
//			page1=this.getPageBySqlMap(page,sql.toString(),map);
//			if(page1.getResultMap().size()<1){
//				map.put("bulk", bulk);
//				sql=new StringBuffer("select id, condition_unit, min_value, max_value, VALUATION_TYPE, rate, low_fee, cus_id, cus_name, depart_name, depart_id, create_time, create_name, update_name, update_time, ts from BAS_PROJ_CUS_TRANSIT_RATE bpr where (bpr.condition_Unit='体积' and :bulk between bpr.min_Value and bpr.max_Value)  and bpr.cus_Id=:cusId and bpr.area_type=:areaType and bpr.take_mode=:takeMode and bpr.traffic_mode=:trafficMode and bpr.depart_id=:disDepartId ");
//				if(cpId!=null){
//					map.put("cpId", cpId);
//					sql.append("and bpr.cp_id=:cpId ");
//				}
//				page1=this.getPageBySqlMap(page, sql.toString(), map);
//			}
//		}
		return page1;
	}

	
}
