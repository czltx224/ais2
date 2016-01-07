package com.xbwl.finance.Service.impl;

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
import com.xbwl.entity.BasSpecialTrainLine;
import com.xbwl.finance.Service.ISpecialTrainLineService;
import com.xbwl.finance.dao.IBasSpecialTrainLineDetailDao;
import com.xbwl.finance.dao.IBasStSpecialTrainRateDao;
import com.xbwl.finance.dao.ISpecialTrainLineDao;
import com.xbwl.oper.reports.util.AppendConditions;

/**
 *@author LiuHao
 *@time 2011-7-22 下午02:59:22
 */
@Service("specialTrainLineServiceImpl")
@Transactional(rollbackFor={Exception.class})
public class SpecialTrainLineServiceImpl extends BaseServiceImpl<BasSpecialTrainLine, Long>
		implements ISpecialTrainLineService {
	
	@Resource(name="specialTrainLineHibernateDaoImpl")
	private ISpecialTrainLineDao specialTrainLineDao;
	
	@Resource(name="basSpecialTrainLineDetailHibernateDaoImpl")
	private IBasSpecialTrainLineDetailDao basSpecialTrainLineDetailDao;
	
	@Resource(name="basStSpecialTrainRateHibernateDaoImpl")
	private IBasStSpecialTrainRateDao basStSpecialTrainRateDao;
	
	@Resource(name="appendConditions")
	private AppendConditions appendConditions;
	
	@Override
	public IBaseDAO<BasSpecialTrainLine, Long> getBaseDao() {
		return specialTrainLineDao;
	}
	
	@Override
	public void save(BasSpecialTrainLine entity) {
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		PropertyFilter filter = null;
		if(null!=entity.getId() && entity.getId()>0){
			filter = new PropertyFilter("NEL_id",entity.getId()+"");
			filters.add(filter);
		}
		if(null!=entity.getLineName() && !"".equals(entity.getLineName().trim())){
			filter = new PropertyFilter("EQS_lineName",entity.getLineName()+"");
			filters.add(filter);
		}
		List<BasSpecialTrainLine> list = this.specialTrainLineDao.find(filters);
		
		if(null!=list && list.size()>0){
			throw new ServiceException("该专车线路已经存在！");
		}else{
			super.save(entity);
		}
	}

	public boolean getIsNotDeleteService(String[] ids) throws Exception {
		List list=null;
		for (int i = 0; i < ids.length; i++) {
			list = this.basSpecialTrainLineDetailDao.findBy("specialTrainLineId", Long.valueOf(ids[i]));
			if(list.size()>0){
				return false;
			}
			
//			list=this.basStSpecialTrainRateDao.findBy("specialTrainLineId", Long.valueOf(ids[i]));
//			if(list.size()>0){
//				return false;
//			}
		}
		
		return true;
	}

	public String findListService(Map<String, String> map) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("select t.id,t.line_name,d.area_name,t.depart_id,t.depart_name,D.ID DETAIL_ID,")
		  .append("d.create_name,d.create_time,d.update_name,d.update_time,d.ts")
		  .append(" from bas_special_train_line t,bas_special_train_line_detail d")
		  .append(" where t.id=d.special_train_line_id(+)");
		
		//添加时间条件
		sb.append(this.appendConditions.appendCountDate(map));
		//添加其他条件
		sb.append(this.appendConditions.appendConditions(map, null));
		return sb.toString();
	}
}
