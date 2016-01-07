package com.xbwl.flow.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.jdom.Document;
import org.jdom.Element;
import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.orm.Page;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.FlowExport;
import com.xbwl.entity.FlowNodeinfo;
import com.xbwl.entity.FlowWorkflowstep;
import com.xbwl.flow.dao.IWorkFlowstepDao;
import com.xbwl.flow.service.IFlowExportService;
import com.xbwl.flow.service.IFlowNodeinfoService;
import com.xbwl.flow.service.IFlowPipeinfoService;
import com.xbwl.flow.service.IWorkFlowbaseService;
import com.xbwl.flow.service.IWorkFlowlogService;
import com.xbwl.flow.service.IWorkFlowstepService;

/**
 * 流程步骤信息服务层实现类
 *@author LiuHao
 *@time Feb 24, 2012 5:24:15 PM
 */
@Service("workFlowstepServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class WorkFlowstepServiceImpl extends BaseServiceImpl<FlowWorkflowstep,Long> implements
		IWorkFlowstepService {
	@Resource(name="workFlowstepHibernateDaoImpl")
	private IWorkFlowstepDao workFlowstepDao;
	@Resource(name="flowNodeinfoServiceImpl")
	private IFlowNodeinfoService flowNodeinfoService;
	@Resource(name="flowExportServiceImpl")
	private IFlowExportService flowExportService;
	@Resource(name="workFlowlogServiceImpl")
	private IWorkFlowlogService workFlowlogService;
	@Resource(name="workFlowbaseServiceImpl")
	private IWorkFlowbaseService workFlowbaseService;
	@Override
	public IBaseDAO getBaseDao() {
		return workFlowstepDao;
	}
	public Document getStepXml(Long pipeId, Long workflowId) throws Exception {
		Map map=null;
	    Long nodeid;
	    Element node=null;
	    List<FlowExport> exportList = this.flowExportService.getExportByPipeid(pipeId);
	   // boolean isFinished=workFlowbaseService.isFinishOrDelete(workflowId);
	    //查询 当前流转到了哪个节点的节点信息
	    String curNodeSql = "select ws.node_id, wi.laststep_id, wi.curstep_id from flow_workflowinfo wi, flow_workflowstep ws " +
	    		"where wi.workflow_id = ? and ws.workflow_id = ? and wi.curstep_id = ws.id " +
	    		"and (wi.is_received = 0 or wi.is_submited = 0)";
	    
	    //查询 已经提交的节点的信息
	    String postNodeSql = "select ws.node_id, wi.laststep_id, wi.curstep_id from flow_workflowinfo wi, flow_workflowstep ws,flow_workflowoperators wo " +
				"where wi.workflow_id = ? and ws.workflow_id = ? and wi.curstep_id = ws.id and ws.id=wo.step_id " +
				"and ((wo.operate_type =1 and wi.is_received = 1 and wi.is_submited = 1) or (wo.operate_type =2 and wi.is_received = 1))";
	    List curNode =workFlowstepDao.createSQLQuery(curNodeSql, workflowId,workflowId).list();
	    List stepList = workFlowstepDao.createSQLQuery(postNodeSql, workflowId,workflowId).list();
	    List<Long> curNodeIds = new ArrayList<Long>();
	    List<Long> stepNodeIds = new ArrayList<Long>();
	    for (int i = 0; i < curNode.size(); ++i) {
	      map = (Map)curNode.get(i); 
	      nodeid = Long.valueOf(map.get("NODE_ID").toString());
	      if (!(curNodeIds.contains(nodeid)))
	        curNodeIds.add(nodeid);
	    }

	    for (int i = 0; i < stepList.size(); ++i) {
	      map = (Map)stepList.get(i);
	      nodeid = Long.valueOf(map.get("NODE_ID").toString());
	      if (!(stepNodeIds.contains(nodeid)))
	        stepNodeIds.add(nodeid);
	    }

	    Element nodes = new Element("nodes");
	    Element edges = new Element("edges");
	    Element graph = new Element("graph");
	    graph.setAttribute("id", pipeId.toString());
	    graph.setAttribute("flowid", workflowId.toString());
	    //生成显示流程图的XML文档  包括 已操作者、未操作者、已查看者
	    Document document = new Document(graph);
	    //已经提交的节点的信息
	    for (int j = 0; j < stepNodeIds.size(); ++j) {
	      Long postNodeId = stepNodeIds.get(j);
	      node = new Element("node");
	      node.setAttribute("id", postNodeId.toString());
	      node.setAttribute("nodeType", "2");
	      List postDoList = workFlowlogService.getOperatorListPost(postNodeId, workflowId);
	      Element postDoUsers = new Element("doUsers");
	      for (int k = 0; k < postDoList.size(); ++k) {
	        Map hum = (Map)postDoList.get(k);
	        Element doUser = new Element("doUser");
	        doUser.setAttribute("id", hum.get("SUBMITER_ID").toString());
	        doUser.setAttribute("rtx", hum.get("SUBMITER_ID").toString());
	        doUser.setAttribute("name", hum.get("USER_NAME").toString());
	        postDoUsers.addContent(doUser);
	      }
	      node.addContent(postDoUsers);
	      nodes.addContent(node);
	    }
	    //当前操作节点的信息
	    for (int i = 0; i < curNodeIds.size(); ++i) {
	      Map hum;
	      Long curNodeId = curNodeIds.get(i);
	      node = new Element("node");
	      node.setAttribute("id", curNodeId.toString());
	      node.setAttribute("nodeType", "1");
	      List operatorList = workFlowlogService.getOperatorListCur(curNodeId, workflowId);
	      List undoList = (List)operatorList.get(0);
	      List doList = (List)operatorList.get(1);
	      List viewList = (List)operatorList.get(2);
	      Element doUsers = new Element("doUsers");
	      Element undoUsers = new Element("undoUsers");
	      Element viewUsers = new Element("viewUsers");
	      for (int j = 0; j < doList.size(); j++) {
	        hum = (Map)doList.get(j);
	        //已操作者
	        Element doUser = new Element("doUser");
	        doUser.setAttribute("id", hum.get("SUBMITER_ID").toString());
	        doUser.setAttribute("rtx", hum.get("SUBMITER_ID").toString());
	        doUser.setAttribute("name", hum.get("USER_NAME").toString());
	        doUsers.addContent(doUser);
	      }
	      for (int j = 0; j < undoList.size(); j++) {
	        hum = (Map)undoList.get(j);
	        //未操作者
	        Element undoUser = new Element("undoUser");
	        undoUser.setAttribute("id", hum.get("RECEIVER_ID").toString());
	        undoUser.setAttribute("rtx", hum.get("RECEIVER_ID").toString());
	        undoUser.setAttribute("name", hum.get("USER_NAME").toString());
	        undoUsers.addContent(undoUser);
	      }
	      for (int j = 0; j < viewList.size(); j++) {
	        hum = (Map)viewList.get(j);
	        //已查看者
	        Element viewUser = new Element("viewUser");
	        viewUser.setAttribute("id", hum.get("RECEIVER_ID").toString());
	        viewUser.setAttribute("rtx", hum.get("RECEIVER_ID").toString());
	        viewUser.setAttribute("name", hum.get("USER_NAME").toString());
	        viewUsers.addContent(viewUser);
	      }
	      node.addContent(undoUsers);
	      node.addContent(doUsers);
	      node.addContent(viewUsers);
	      nodes.addContent(node);
	    }
	    //绘制出口信息
	    FlowNodeinfo endNodeInfo =  flowNodeinfoService.getSEnodeinfoByPipeId(pipeId, "end");
	    FlowNodeinfo startNodeInfo =  flowNodeinfoService.getSEnodeinfoByPipeId(pipeId, "start");
	    for (int m = 0; m < exportList.size(); ++m) {
	      FlowExport export = (FlowExport)exportList.get(m);
	      Long startId = export.getStartnodeId();
	      Long endId = export.getEndnodeId();
	      //去除由开始直接到结束节点的实体线
	      if ((stepNodeIds.contains(startId)) && (((stepNodeIds.contains(endId)) || (curNodeIds.contains(endId))))) {
	    	  if(stepNodeIds.size()>1){
	    		  if(!startId.equals(startNodeInfo.getId()) || !endId.equals(endNodeInfo.getId())){
	    			  Element edge = new Element("edge");
	    	    	  edge.setAttribute("id", export.getId().toString());
	    	    	  edges.addContent(edge);
	    		  }
	    	  }else{
	    		  Element edge = new Element("edge");
		    	  edge.setAttribute("id", export.getId().toString());
		    	  edges.addContent(edge);
	    	  }
	      }
	      
	    }
	    graph.addContent(nodes);
	    graph.addContent(edges);
	    return document;
	}
	public List<FlowWorkflowstep> getStepByNodeId(Long nodeId,Long workflowId) throws Exception {
		return workFlowstepDao.find("from FlowWorkflowstep fw where fw.nodeId=? and fw.workflowId=?", nodeId,workflowId);
	}
	public List<FlowWorkflowstep> getLastStep(Long pipeId, Long nodeId, Long userId)
			throws Exception {
		String hql="from FlowWorkflowstep step where step.workflowId=? and step.nodeId=? and step.receiverId=?";
		List<FlowWorkflowstep> stepList  = workFlowstepDao.find("from FlowWorkflowstep step where step.workflowId=? and step.nodeId=? and step.receiverId=? and (step.receiveTime is null or step.submiterId is null)", pipeId,nodeId,userId);
		if(stepList.size()<1){
			stepList = workFlowstepDao.find(hql, pipeId,nodeId,userId);
			if(stepList.size()<1){
				throw new ServiceException("数据出错，您不是该流程的审批人，请联系系统管理员！");
			}else{
				return stepList;
			}
		}else{
			return stepList;
		}
	}
	public Page getstayAuditFlow(Page page,Long userId, Date startTime, Date endTime,String isAlert,Long operType)
			throws Exception {
		Map map=new HashMap();
		map.put("userId", userId);
		map.put("operType", operType);
		StringBuffer sql=new StringBuffer("select fs.id,fs.workflow_id, fs.step_id, fs.node_id, fs.node_type, fs.receiver_id, fs.receive_time, fs.submiter_id, fs.submit_time, fs.create_time, fs.create_name, fs.update_name, fs.update_time, fs.ts,fb.workflow_name,fb.is_finished ,wo.operate_type ");
		sql.append(" from flow_workflowstep fs,flow_workflowbase fb,flow_workflowoperators wo where fs.workflow_id=fb.id and fs.id = wo.step_id and fs.receiver_id=:userId  ");
		if(operType == null){
			sql.append(" and ((wo.operate_type=1 and fs.submiter_id is null) or (wo.operate_type = 3 and fs.receive_time is null) or (wo.operate_type = 4 and fs.submiter_id is null)");
			if("false".equals(isAlert)){
				sql.append(" or (wo.operate_type = 2 and fs.receive_time is null)");
			}
			sql.append(")");
		}else{
			if(operType == 1 || operType == 4 ){
				sql.append(" and ((wo.operate_type=:operType and fs.submiter_id is null))");
			}else {
				sql.append(" and ((wo.operate_type=:operType and fs.receive_time is null))");
			}
		}
		if(startTime != null){
			sql.append(" and fs.create_time>=:startTime");
			map.put("startTime", startTime);
		}
		if(endTime != null){
			sql.append(" and fs.create_time<=:endTime");
			map.put("endTime", startTime);
		}
		sql.append(" order by fs.create_time desc");
		return this.getPageBySqlMap(page, sql.toString(), map);
	}
//	public List<FlowWorkflowstep> getStepByUserid(Long workflowId, Long userId)
//			throws Exception {
//		return this.find("from FlowWorkflowstep fs where fs.workflowId=? and fs.receiverId=?", workflowId,userId);
//	}
	public List<FlowWorkflowstep> getStepByNosubmit(Long workflowId) throws Exception {
		return workFlowstepDao.createSQLQuery("select * from flow_workflowstep fs,flow_workflowoperators fo where fs.id=fo.step_id and fo.operate_type=1 and fs.submiter_id is null and fs.workflow_id=?", workflowId).list();
	}
	public FlowWorkflowstep getStepByStartNode(Long workflowId)
			throws Exception {
		List<FlowWorkflowstep> fwList = this.find("from FlowWorkflowstep fw where fw.nodeType='开始节点' and fw.workflowId=?", workflowId);
		if(fwList.size()!=1){
			throw new ServiceException("流程号:"+workflowId+",对应的开始节点步骤信息存在多条，知会申请人失败。");
		}else{
			return fwList.get(0);
		}
	}
	public List getStepTime(Date startDate, Date endDate,
			int pageSize,Long pipeId,String countType)throws Exception {
		Map map = new HashMap();
		map.put("nodeType", "活动节点");
		StringBuffer sql=new StringBuffer();
		if("node".equals(countType)){
			sql.append("select fp.obj_name pipe_name,fs.node_id,fn.obj_name node_name,");
		}else{
			sql.append("select fp.obj_name pipe_name,fs.update_name,");
		}
		sql.append("count(fs.node_id) audit_count,");
		sql.append("round(sum(fs.update_time - fs.create_time)*24/ count(fs.node_id),1) avg_time,");
		sql.append("floor(sum(fs.update_time - fs.create_time) / count(fs.node_id))||'天'||");
		sql.append("mod(floor((sum(fs.update_time - fs.create_time) / count(fs.node_id))*24),24)||'小时'||");
		sql.append("mod(floor((sum(fs.update_time - fs.create_time) / count(fs.node_id))*24*60),60)||'分' avg_time_str");
		sql.append(" from flow_workflowstep fs,flow_nodeinfo fn,flow_workflowbase fb,flow_pipeinfo fp,flow_workflowoperators fo  ");
		sql.append(" where fs.node_id = fn.id and fs.workflow_id = fb.id and fb.pipe_id = fp.id and fs.id = fo.step_id and fo.operate_type=1 and fs.node_type =:nodeType and (fb.is_finished=2 or fb.is_finished=3)");
		if(startDate != null){
			sql.append(" and fb.create_time>=:startDate");
			map.put("startDate", startDate);
		}
		if(endDate != null){
			sql.append(" and fb.create_time<=:endDate+1");
			map.put("endDate", endDate);
		}
		if(pipeId != null){
			sql.append(" and fb.pipe_id=:pipeId");
			map.put("pipeId", pipeId);
		}
		if("node".equals(countType)){
			sql.append(" group by fs.node_id, fn.obj_name, fp.obj_name");
		}else{
			sql.append(" group by fp.obj_name,fs.update_name");
		}
		sql.append(" order by sum(fs.update_time - fs.create_time)/ count(fs.node_id) desc");
		return this.createSQLMapQuery(sql.toString(), map).setMaxResults(pageSize).list();
	}
	public Page getYetAuditFlow(Page page, Long workId, String workName,
			Date startTime, Date endTime) throws Exception {
		User user=WebRalasafe.getCurrentUser(Struts2Utils.getRequest());
		StringBuffer sql = new StringBuffer();
		Map map=new HashMap();
		sql.append("select fs.workflow_id,fs.id,fs.node_id,fn.node_type,fn.obj_name node_name,fs.receiver_id,fs.receive_time,fs.submiter_id,fs.submit_time,fs.create_name,fs.create_time,fb.is_finished,fb.workflow_name");
		sql.append(" from flow_workflowstep fs, flow_workflowbase fb, flow_nodeinfo fn,flow_workflowoperators wo");
		sql.append(" where fs.workflow_id = fb.id and fs.node_id = fn.id and fs.id= wo.step_id ");
		sql.append(" and (fs.submiter_id=:userId or (fs.receiver_id=:userId and fs.receive_time is not null and wo.operate_type=3))");
		map.put("userId", user.get("id").toString());
		if(workId!=null){
			sql.append(" and fs.workflow_id=:workflowId");
			map.put("workflowId", workId);
		}
		if(workName !=null && !"".equals(workName)){
			sql.append(" and fb.workflow_name like :workflowName");
			map.put("workflowName", "%"+workName+"%");
		}
		if(startTime != null){
			sql.append(" and fs.create_time>=:startTime");
			map.put("startTime", startTime);
		}
		if(endTime != null){
			sql.append(" and fs.create_time <=:endTime+1");
			map.put("endTime", endTime);
		}
		return this.getPageBySqlMap(page, sql.toString(), map);
	}
}
