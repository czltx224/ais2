package com.xbwl.flow.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.entity.FlowRalaGive;
import com.xbwl.flow.dao.IFlowRalaGiveDao;
import com.xbwl.flow.service.IFlowRalaGiveService;

/**
 *@author LiuHao
 *@time Apr 19, 2012 4:16:16 PM
 */
@Service("flowRalaGiveServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class FlowRalaGiveServiceImpl extends BaseServiceImpl<FlowRalaGive,Long> implements
		IFlowRalaGiveService {
	@Resource(name = "flowRalaGiveHibernateDaoImpl")
	private IFlowRalaGiveDao flowRalaGiveDao;
	@Override
	public IBaseDAO getBaseDao() {
		return flowRalaGiveDao;
	}
	public FlowRalaGive getRalaByPipeId(Long pipeId,Long userId) throws Exception {
		String sysDate=new SimpleDateFormat("yyyy-mm-dd,hh:mm").format(new Date());
		String strTime=sysDate.split(",")[1];
		String hql="from FlowRalaGive fr where (sysdate between nvl(fr.startDate,to_date('1970-01-01','yyyy-MM-dd')) and nvl(fr.endDate+1,to_date('3000-01-01','yyyy-mm-dd')))";
			   hql+="and ( ? between nvl(fr.startTime,'00:00') and nvl(fr.endTime,'23:59')) and fr.pipeId=? and fr.userId=?";
		List<FlowRalaGive> ralaList = flowRalaGiveDao.find(hql, strTime,pipeId,userId);
		if(ralaList.size()>0){
			return ralaList.get(0);
		}else{
			return null;
		}
	}
}
