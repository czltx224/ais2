package com.xbwl.flow.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.orm.Page;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.entity.FlowWorkflowlog;
import com.xbwl.flow.dao.IWorkFlowlogDao;
import com.xbwl.flow.dao.IWorkFlowstepDao;
import com.xbwl.flow.service.IWorkFlowbaseService;
import com.xbwl.flow.service.IWorkFlowlogService;

/**
 * 流程日志信息服务层实现类
 *@author LiuHao
 *@time Feb 24, 2012 5:24:15 PM
 */
@Service("workFlowlogServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class WorkFlowlogServiceImpl extends BaseServiceImpl<FlowWorkflowlog,Long> implements
		IWorkFlowlogService {
	@Resource(name="workFlowlogHibernateDaoImpl")
	private IWorkFlowlogDao workFlowlogDao;
	
	@Resource(name="workFlowbaseServiceImpl")
	private IWorkFlowbaseService workFlowbaseService;
	@Resource(name="workFlowstepHibernateDaoImpl")
	private IWorkFlowstepDao workFlowstepDao;
	@Override
	public IBaseDAO getBaseDao() {
		return workFlowlogDao;
	}
	public List getOperatorListPost(Long nodeId, Long workflowId)
			throws Exception {
		String hql="select distinct ws.submiter_id,su.user_name from flow_workflowstep ws,sys_user su " +
				"where ws.submiter_id=su.id and ws.node_id=? and ws.workflow_id=? ";
	    if (this.logger.isDebugEnabled())
	      this.logger.debug("getOperatorListPost:" + hql.toString());
	    List operatorlist = workFlowstepDao.createSQLQuery(hql.toString(), nodeId,workflowId).list();
	    return operatorlist;
	}
	
	public List getOperatorListCur(Long nodeid, Long workflowid)throws Exception
	  {
	    List operatorlist = new ArrayList();

//	    List operatorlist1 = getOperatorCur(nodeid, workflowid);
//	    Long curstepids = null;
//	    for (int i = 0; i < operatorlist1.size(); ++i) {
//	      String _tmpstepid = StringHelper.null2String(operatorlist1.get(i));
//	      if (!(StringHelper.isEmpty(_tmpstepid)))
//	        curstepids = curstepids + "," + _tmpstepid;
//	    }
	    //curstepids = StringHelper.formatMutiIDs(curstepids);
	    operatorlist.add(getOperatorListNot(nodeid, workflowid));
	    operatorlist.add(getOperatorListEd(nodeid, workflowid));
	    operatorlist.add(getOperatorListSee(nodeid, workflowid));
	    return operatorlist;
	  }
	//获得当前的操作者
	private List getOperatorCur(Long nodeid, Long workflowid) throws Exception
	  {
	    String hql="select ws.id from flow_workflowinfo wi, flow_workflowstep ws " +
	    		"where wi.curstep_id = ws.id and (wi.is_received = 0 or wi.is_submited = 0)  and ws.node_id =? and wi.workflow_id =? and ws.workflow_id =?";

	    if (this.logger.isDebugEnabled())
	      this.logger.debug("getOperatorCur:" + hql.toString());
	    List operatorlist = workFlowstepDao.createSQLQuery(hql.toString(), nodeid,workflowid,workflowid).list();

	    return operatorlist;
	  }
	//当前流程节点的未操作者
	private List getOperatorListNot(Long nodeid, Long workflowid) throws Exception
	  {
//      String hql="select distinct h.id h.user_name, from flow_Workflowoperators wo, flow_Workflowstep ws,sys_user h " +
//      		"where wo.step_id = ws.id  and wo.user_id = h.id and ws.node_id = ? and wo.workflow_id = ?  and ws.workflow_id = ? ";
      String hql="select ws.receiver_id,su.user_name from flow_workflowstep ws,sys_user su " +
      		"where ws.receiver_id = su.id and ws.receive_time is null and ws.node_id = ? and ws.workflow_id = ?";
	    if (this.logger.isDebugEnabled())
	      this.logger.debug("getOperatorListNot_未操作者:" + hql.toString());
	    List operatorlist0 = workFlowstepDao.createSQLQuery(hql.toString(), nodeid,workflowid).list();
	    return operatorlist0;
	  }
	  //当前流程节点的已操作者
	  private List getOperatorListEd(Long nodeid, Long workflowid) throws Exception
	  {
		  String hql= "select ws.submiter_id,su.user_name from flow_workflowstep ws,sys_user su " +
    		"where ws.submiter_id = su.id and ws.submit_time is not null and ws.node_id = ? and ws.workflow_id = ?";

	    if (this.logger.isDebugEnabled())
	      this.logger.debug("getOperatorListEd_已操作者:" + hql.toString());
	    List operatorlist = workFlowstepDao.createSQLQuery(hql.toString(),nodeid, workflowid).list();
	    return operatorlist;
	  }
	//当前流程节点的已查看者
	  private List getOperatorListSee(Long nodeid, Long workflowid) throws Exception
	  {
		  String hql="select ws.receiver_id,su.user_name from flow_workflowstep ws,sys_user su " +
    		"where ws.receiver_id = su.id and ws.receive_time is not null and ws.node_id = ? and ws.workflow_id = ?";

	    if (this.logger.isDebugEnabled())
	      this.logger.debug("getOperatorList2_查看者:" + hql.toString());
	    List operatorlist = workFlowstepDao.createSQLQuery(hql.toString(),nodeid, workflowid).list();
	    return operatorlist;
	  }
	public List<FlowWorkflowlog> getFlowlog(Long workflowId, Long nodeId) throws Exception {
		return this.find("from FlowWorkflowlog fl where fl.workflowId=? and fl.nodeId=?", workflowId,nodeId);
	}
	public Page getFlowLogByWid(Page page, Long workflowId) throws Exception {
		return this.getPageBySql(page, "select fw.*,fn.obj_name node_name,fnr.obj_name return_node_name from flow_workflowlog fw,flow_nodeinfo fn,flow_nodeinfo fnr where fw.nodeinfo_id=fn.id and fw.return_nodeid=fnr.id(+) and fw.workflow_id=? order by fw.create_time desc", workflowId+"");
	}
}
