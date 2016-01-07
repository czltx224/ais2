package com.xbwl.flow.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.entity.FlowWorkflowoperators;
import com.xbwl.entity.FlowWorkflowstep;
import com.xbwl.flow.dao.IWorkFlowoperDao;
import com.xbwl.flow.service.IWorkFlowoperService;

/**
 * 流程操作者信息服务层实现类
 *@author LiuHao
 *@time Feb 24, 2012 5:24:15 PM
 */
@Service("workFlowoperServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class WorkFlowoperServiceImpl extends BaseServiceImpl<FlowWorkflowoperators,Long> implements
		IWorkFlowoperService {
	@Resource(name="workFlowoperHibernateDaoImpl")
	private IWorkFlowoperDao workFlowoperDao;
	@Override
	public IBaseDAO getBaseDao() {
		return workFlowoperDao;
	}
	public void delCurOprMsg(Long curUserId, Long pipeId) throws Exception {
		Map map=new HashMap();
		map.put("workflowId", pipeId);
		
		StringBuffer hql=new StringBuffer("from FlowWorkflowoperators opr where opr.workflowId=:workflowId ");
		if(curUserId != null){
			hql.append("and opr.userId=:userId");
			map.put("userId", curUserId);
		}
		List<FlowWorkflowoperators> oprList=this.find(hql.toString(), map);
		for(FlowWorkflowoperators oper:oprList){
			workFlowoperDao.delete(oper);
		}
	}
	public boolean getByStep(Long workflowId, List<FlowWorkflowstep> stepList)
			throws Exception {
		boolean flag = false;
		StringBuffer sql = new StringBuffer("from FlowWorkflowoperators fo where fo.workflowId=? and (1=1");
		for(FlowWorkflowstep fw:stepList){
			sql.append(" or fo.stepId=");
			sql.append(fw.getId());
		}
		sql.append(")");
		List<FlowWorkflowoperators> list = this.find(sql.toString(),workflowId);
		for(FlowWorkflowoperators fo:list){
			if(fo.getOperateType() == 1){
				flag = true;
			}
		}
		return flag;
	}
}
