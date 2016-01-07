package com.xbwl.cus.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.cus.dao.ICusSearchDao;
import com.xbwl.cus.service.ICusSearchService;
import com.xbwl.entity.CusSearch;
import com.xbwl.entity.SysDepart;
import com.xbwl.oper.reports.util.AppendConditions;
import com.xbwl.rbac.Service.IDepartService;

/**
 * author CaoZhili time Oct 17, 2011 10:09:27 AM
 */
@Service("cusSearchServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class CusSearchServiceImpl extends BaseServiceImpl<CusSearch, Long>
		implements ICusSearchService {

	@Resource(name="cusSearchHibernateDaoImpl")
	private ICusSearchDao cusSearchDao;
	
	@Resource(name="departServiceImpl")
	private IDepartService departService;
	
	@Resource(name="appendConditions")
	private AppendConditions appendConditions;
	
	@Override
	public IBaseDAO<CusSearch, Long> getBaseDao() {
		return this.cusSearchDao;
	}

	public String findCusSearchService(Map<String, String> map) throws Exception {
		//FIXED 使用前进行非空判断
		StringBuffer sb = new StringBuffer();
		String createName = map.get("createName");
		String departCode = map.get("departCode");
		
		if(null==createName){
			throw new ServiceException("创建人不允许为空！");
		}
		if(null==departCode){
			throw new ServiceException("部门编码不允许为空！");
		}
		//FIXED 去掉1=1条件
		sb.append("select t.* from cus_search t ");
		sb.append(" WHERE (t.create_name =:createName or t.depart_code like :departCode)");
		//FIXED 根据主键排序是否意义，是否必要，能否去除？
		//sb.append(" order by id desc");
		return sb.toString();
	}

	public void authorizedService(String[] idStrings, Long departId)
			throws Exception {
		//FIXED 使用前进行非空判断
		SysDepart  depart = this.departService.get(departId);
		if(null==depart){
			throw new ServiceException("部门编号不允许为空！");
		}
		CusSearch entity = null;
		for (int i = 0; i < idStrings.length; i++) {
			entity=this.cusSearchDao.get(Long.valueOf(idStrings[i]));
			entity.setDepartCode(depart.getDepartNo());
			
			this.cusSearchDao.save(entity);
		}
	}

	public String findSearchListService(Map<String, String> map)
			throws Exception {
		String departCode = map.get("departCode");
		String createName = map.get("createName");
		
		if(null==departCode || "".equals(departCode.trim())){
			throw new ServiceException("部门编码不能为空！");
		}
		if(null==createName || "".equals(createName.trim())){
			throw new ServiceException("创建人不能为空！");
		}
		String[] sts = new String[]{"departCode","createName"};
		StringBuffer sb = new StringBuffer();
		sb.append("select t.id,t.table_ch tableCh,t.table_en tableEn,t.search_statement searchStatement,")
		  .append("t.create_name createName,t.create_time createTime,t.update_name updateName,t.update_time updateTime,t.ts,")
		  .append("t.title,t.search_chinese searchChinese,d.depart_Name departName");
		sb.append(" from cus_search t,sys_depart d")
		  .append(" where t.DEPART_CODE=d.depart_no");
		sb.append(" and (t.create_name=:createName or t.depart_code like :departCode||'%')");
		
		//添加日期条件
		sb.append(appendConditions.appendCountDate(map));
		
		//添加条件
		sb.append(appendConditions.appendConditions(map,sts));
		return sb.toString();
	}

}
