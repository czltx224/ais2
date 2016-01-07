package com.xbwl.finance.Service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.orm.Page;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.entity.BasProjectRate;
import com.xbwl.finance.Service.IBasProjectRateService;
import com.xbwl.finance.Service.IBasTreatyChangeListService;
import com.xbwl.finance.dao.IBasProjectRateDao;

/**
 * @author CaoZhili time Aug 10, 2011 10:44:16 AM
 * 
 * ��Ŀ�ͻ�Э��۷����ʵ����
 */
@Service("basProjectRateServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class BasProjectRateServiceImpl extends
		BaseServiceImpl<BasProjectRate, Long> implements IBasProjectRateService {

	@Resource(name="basProjectRateHibernateDaoImpl")
	private IBasProjectRateDao basProjectRateDao;
	
	@Resource(name="basTreatyChangeListServiceImpl")
	private IBasTreatyChangeListService basTreatyChangeListService;
	
	@Override
	public IBaseDAO<BasProjectRate, Long> getBaseDao() {

		return this.basProjectRateDao;
	}

	public void saveProjectRate(BasProjectRate entity,String chinaName)throws Exception {
		this.basTreatyChangeListService.saveRecord(entity, chinaName,false);
		this.save(entity);
	}

	public Page<BasProjectRate> findProRate(Page page,Long piece, Double weight, Double bulk,Long cusId,String city,String town)
			throws Exception {
		User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		Long bussDepartId = Long.parseLong(user.get("bussDepart") + "");
		
		Map map = new HashMap();
		map.put("valuType", bulk);
		map.put("departId",bussDepartId );
		map.put("cusId", cusId);
		map.put("unit", "���");
		
		Page<BasProjectRate> page1=null;
		StringBuffer sql=new StringBuffer("select id, condition_unit, min_value, max_value, count_way, end_add, rate, low_fee, cus_id, cus_name, depart_name, depart_id, create_time, create_name, update_name, update_time, ts,add_fee from BAS_PROJECT_RATE bpr where (bpr.condition_Unit=:unit and :valuType between bpr.min_Value and bpr.max_Value)  and bpr.cus_Id=:cusId and bpr.depart_id=:departId ");
		if((city !=null && !"".equals(city)) || (town !=null && !"".equals(town))){
			sql.append("and bpr.end_add=:endCity");
			map.put("endCity", town);
			
			page1=this.getPageBySqlMap(page, sql.toString(), map);
			if(page1.getResultMap().size()<1){
				map.put("endCity", city);
				page1 = this.getPageBySqlMap(page, sql.toString(), map);
				if(page1.getResultMap().size()<1){
					map.put("valuType", piece);
					map.put("unit", "����");
					map.put("endCity", town);
					page1 = this.getPageBySqlMap(page, sql.toString(), map);
					if(page1.getResultMap().size()<1){
						map.put("endCity", city);
						page1 = this.getPageBySqlMap(page, sql.toString(), map);
						if(page1.getResultMap().size()<1){
							map.put("valuType", weight);
							map.put("unit", "����");
							map.put("endCity", town);
							page1 = this.getPageBySqlMap(page, sql.toString(), map);
							if(page1.getResultMap().size()<1){
								map.put("endCity", city);
								page1 = this.getPageBySqlMap(page, sql.toString(), map);
								//���Ŀ��վ��Э���û���ҵ� ��ȥ��Ŀ��վ
								sql = new StringBuffer("select id, condition_unit, min_value, max_value, count_way, end_add, rate, low_fee, cus_id, cus_name, depart_name, depart_id, create_time, create_name, update_name, update_time, ts from BAS_PROJECT_RATE bpr where (bpr.condition_Unit=:unit and :valuType between bpr.min_Value and bpr.max_Value)  and bpr.cus_Id=:cusId and bpr.depart_id=:departId and bpr.end_add is null ");
								if(page1.getResultMap().size()<1){
									map.put("valuType", bulk);
									map.put("unit", "���");
									page1=this.getPageBySqlMap(page, sql.toString(), map);
									if(page1.getResultMap().size()<1){
										map.put("valuType", piece);
										map.put("unit", "����");
										page1=this.getPageBySqlMap(page, sql.toString(), map);
										if(page1.getResultMap().size()<1){
											map.put("valuType", weight);
											map.put("unit", "����");
											page1=this.getPageBySqlMap(page, sql.toString(), map);
										}
									}
								}
							}
						}
					}
				}
			}
		}else{
			page1=this.getPageBySqlMap(page, sql.toString(), map);
			if(page1.getResultMap().size()<1){
				map.put("valuType", weight);
				map.put("unit", "����");
				page1=this.getPageBySqlMap(page, sql.toString(), map);
				if(page1.getResultMap().size()<1){
					map.put("valuType", bulk);
					map.put("unit", "���");
					page1=this.getPageBySqlMap(page, sql.toString(), map);
				}
			}
		}
		
		return page1;
	}
}
