package com.xbwl.flow.service.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.FlowExport;
import com.xbwl.entity.FlowFormfield;
import com.xbwl.entity.FlowNodeinfo;
import com.xbwl.entity.FlowPipeinfo;
import com.xbwl.entity.FlowRalaGive;
import com.xbwl.entity.FlowRalarule;
import com.xbwl.entity.FlowWorkflowbase;
import com.xbwl.entity.FlowWorkflowinfo;
import com.xbwl.entity.FlowWorkflowlog;
import com.xbwl.entity.FlowWorkflowoperators;
import com.xbwl.entity.FlowWorkflowstep;
import com.xbwl.entity.OprChangeDetail;
import com.xbwl.entity.OprChangeMain;
import com.xbwl.entity.OprFaxIn;
import com.xbwl.entity.OprValueAddFee;
import com.xbwl.entity.SysDepart;
import com.xbwl.flow.dao.IWorkFlowbaseDao;
import com.xbwl.flow.service.IFlowExportService;
import com.xbwl.flow.service.IFlowNodeinfoService;
import com.xbwl.flow.service.IFlowPipeinfoService;
import com.xbwl.flow.service.IFlowRalaGiveService;
import com.xbwl.flow.service.IFlowRalaruleService;
import com.xbwl.flow.service.IFormfieldService;
import com.xbwl.flow.service.IForminfoService;
import com.xbwl.flow.service.IWorkFlowbaseService;
import com.xbwl.flow.service.IWorkFlowinfoService;
import com.xbwl.flow.service.IWorkFlowlogService;
import com.xbwl.flow.service.IWorkFlowoperService;
import com.xbwl.flow.service.IWorkFlowstepService;
import com.xbwl.flow.vo.FlowSaveVo;
import com.xbwl.oper.fax.service.IOprFaxChangeDetailService;
import com.xbwl.oper.fax.service.IOprFaxChangeService;
import com.xbwl.oper.fax.service.IOprFaxInService;
import com.xbwl.rbac.Service.IDepartService;
import com.xbwl.rbac.Service.IUserService;
import com.xbwl.rbac.entity.SysUser;

/**
 * 流程服务层实现类
 *@author LiuHao
 *@time Feb 24, 2012 4:33:17 PM
 */
@Service("workFlowbaseServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class WorkFlowbaseServiceImpl extends BaseServiceImpl<FlowWorkflowbase,Long> implements
		IWorkFlowbaseService {
	@Resource(name = "workFlowbaseHibernateDaoImpl")
	private IWorkFlowbaseDao workFlowbaseDao;
	@Resource(name = "flowExportServiceImpl")
	private IFlowExportService flowExportService;
	@Resource(name = "flowRalaruleServiceImpl")
	private IFlowRalaruleService flowRalaruleService;
	@Resource(name = "flowNodeinfoServiceImpl")
	private IFlowNodeinfoService flowNodeinfoService;
	@Resource(name = "flowPipeinfoServiceImpl")
	private IFlowPipeinfoService flowPipeinfoService;
	@Resource(name = "workFlowstepServiceImpl")
	private IWorkFlowstepService workFlowstepService;
	@Resource(name = "workFlowoperServiceImpl")
	private IWorkFlowoperService workFlowoperService;
	@Resource(name = "workFlowinfoServiceImpl")
	private IWorkFlowinfoService workFlowinfoService;
	@Resource(name = "workFlowlogServiceImpl")
	private IWorkFlowlogService workFlowlogService;
	@Resource(name = "forminfoServiceImpl")
	private IForminfoService forminfoService;
	@Resource(name = "formfieldServiceImpl")
	private IFormfieldService formfieldService;
	@Resource(name = "oprFaxChangeServiceImpl")
	private IOprFaxChangeService oprFaxChangeService;
	@Resource(name="departServiceImpl")
	private IDepartService departService;
	//@Value("${workFlowbaseServiceImpl.mainTableName}")
	//private String mainTableName;
	@Value("${workFlowbaseServiceImpl.dnoField}")
	private String dnoField;
	@Value("${workFlowbaseServiceImpl.applymanField}")
	private String applymanField;
	@Value("${workFlowbaseServiceImpl.workIdField}")
	private String workIdField;
	@Value("${oprFaxChangeServiceImpl.changeFlow.pipeId}")
	private Long faxChangePipeId;

	@Resource(name="oprFaxInServiceImpl")
	private IOprFaxInService oprFaxInService;
	@Resource(name="oprFaxChangeDetailServiceImpl")
	private IOprFaxChangeDetailService oprFaxChangeDetailService;
	@Resource(name = "flowRalaGiveServiceImpl")
	private IFlowRalaGiveService flowRalaGiveService;
	@Resource(name="userServiceImpl")
	private IUserService userService;
	@Override
	public IBaseDAO getBaseDao() {
		return workFlowbaseDao;
	}
	public boolean isFinishOrDelete(Long id) throws Exception {
		boolean ret = false;
	    FlowWorkflowbase workflowbase = workFlowbaseDao.getAndInitEntity(id);
	    if ((((workflowbase.getIsDelete() == 0) || (workflowbase.getIsFinished() == 1)))){
	      ret = true;
	    }
	    return ret;
	}
	public Map<Long ,Long> getNextUsers(Long curNodeId,Long pipeId,Long workflowId) throws Exception {
		Long nodeId=this.getNodeId(curNodeId, pipeId,workflowId);
		return flowRalaruleService.getRalaByNodeId(nodeId,pipeId,workflowId);
	}
	/**
	 * 获得下个流转节点的节点ID
	 * @author LiuHao
	 * @time Mar 1, 2012 4:29:47 PM 
	 * @return
	 * @throws Exception
	 */
	private Long getNodeId(Long curNodeId,Long pipeId,Long workflowId)throws Exception{
		Long nodeId=null;
		List<FlowExport> exportList=flowExportService.find("from FlowExport fe where fe.startnodeId=? and fe.pipeId=? and fe.status=1", curNodeId,pipeId);
		//该节点有多个出口
		if(exportList.size()>1){
			boolean flag=false;//有没有获得到节点ID
			//条件为空的List
			List<FlowExport> nullCondition=new ArrayList<FlowExport>();
			//条件不为空的List
			List<FlowExport> condition=new ArrayList<FlowExport>();
			for(FlowExport fe:exportList){
				String cond=fe.getCondition();
				if(cond !=null && !"".equals(cond)){
					condition.add(fe);
				}else{
					nullCondition.add(fe);
				}
			}
			for(FlowExport fe:condition){
				List list=this.getListBycondition(fe.getCondition(),workflowId,pipeId);
				if(list.size()>0){
					nodeId=fe.getEndnodeId();
					flag=true;
					break;
				}
			}
			if(!flag && nullCondition.size()!=1){
				throw new ServiceException("该节点没有满足的出口条件，或者多个出口没有条件，请联系系统管理员！");
			}else if(!flag && nullCondition.size()==1){
				nodeId=nullCondition.get(0).getEndnodeId();
			}
		}else if(exportList.size() == 1){
			nodeId =  exportList.get(0).getEndnodeId();
		}else{
			throw new ServiceException("该流程当前节点没有在出口表中不存在，请联系系统管理员！");
		}
		return nodeId;
	}
	/**
	 * 根据出口条件查询数据
	 * @author LiuHao
	 * @time Mar 1, 2012 4:46:57 PM 
	 * 条件格式               表名                   字段名     值
	 * @param sqlQuery 例如:{table_name1:field1='1' and field2>= '1000' }
	 * @return
	 */
	private List getListBycondition(String sqlQuery,Long workflowId,Long pipeId)throws Exception{
		sqlQuery.replace("{", "");
		sqlQuery.replace("}", "");
		String[] splits=sqlQuery.split(":");
		if(splits.length>2){
			throw new ServiceException("出口条件格式与系统定义不符，请联系系统管理员！");
		}
		
		List<Map> mainList = forminfoService.getMainForm(pipeId);
		Map formMap=mainList.get(0);
		Long formId=Long.valueOf(formMap.get("ID")+"");
//		List<FlowFormfield> fieldList = forminfoService.find("from FlowFormField ff where ff.id=? and ff.labelName='流程号'", formId);
//		if(fieldList.size()!=1){
//			throw new ServiceException("流程回写数据失败，表单ID："+formId+"，对应的表单有多个或者没有'流程号'字段，请联系系统管理员！");
//		}
		FlowFormfield formField = formfieldService.getInfoByLabelname(formId, "流程号");
		String fieldName=formField.getFieldName();
		return workFlowbaseDao.createSQLQuery("select * from "+splits[0]+" where "+fieldName+"=? and "+splits[1]+"",workflowId).list();
	}
	public void flowSubmit(FlowSaveVo saveVo) throws Exception {
		Long workflowId;
		Long workId=saveVo.getWorkflowId();
		Long pipeId=saveVo.getPipeId();
		Long nodeId=saveVo.getNodeId();
		String oprType=saveVo.getOprType();
		Long logType=saveVo.getLogType();
		String auditRemark=saveVo.getAuditRemark();
		Long returnNodeid=saveVo.getReturnNodeId();
		Long dno=saveVo.getDno();
		String applyRemark=saveVo.getApplyRemark();
		String formDetailStr = saveVo.getFormDetailStr();
		String changeType = saveVo.getChangeType();
		//String manType = saveVo.getManType();
		
		User user=WebRalasafe.getCurrentUser(Struts2Utils.getRequest());
		FlowNodeinfo nodeinfo = flowNodeinfoService.getAndInitEntity(nodeId);
		
		if(nodeinfo == null){
			throw new ServiceException("节点ID："+nodeId+"对应的节点信息不存在！");
		}
		FlowPipeinfo pipeinfo = flowPipeinfoService.getAndInitEntity(pipeId);
		if(pipeinfo == null){
			throw new ServiceException("流程ID："+pipeId+"对应的节点信息不存在！");
		}
		if("开始节点".equals(nodeinfo.getNodeType()) && "submit".equals(oprType)){
			
			FlowWorkflowbase flowbase=new FlowWorkflowbase();
			flowbase.setCreaterId(Long.valueOf(user.get("id").toString()));
			flowbase.setCreateType(1L);
			flowbase.setPipeId(pipeinfo.getId());
			flowbase.setCurnodeids(nodeId);
			flowbase.setFormId(pipeinfo.getFormId());
			flowbase.setIsDelete(1L);
			flowbase.setIsFinished(1L);
			flowbase.setWorkflowName(pipeinfo.getObjName()+"-"+user.get("name")+"-"+new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			flowbase.setWorkflowLevel(1L);
			//保存流程基本信息
			workFlowbaseDao.save(flowbase);
			workflowId = flowbase.getId();
			
			FlowWorkflowstep flowstep=new FlowWorkflowstep();
			flowstep.setNodeId(nodeId);
			flowstep.setNodeType("开始节点");
			flowstep.setWorkflowId(workflowId);
			flowstep.setReceiverId(Long.valueOf(user.get("id").toString()));
			flowstep.setReceiveTime(new Date());
			flowstep.setSubmiterId(Long.valueOf(user.get("id").toString()));
			flowstep.setSubmitTime(new Date());
			workFlowstepService.save(flowstep);
			//保存操作者信息
			FlowWorkflowoperators operstors=new FlowWorkflowoperators();
			//operstors.setRuleId(userMap.get(uId));
			operstors.setUserId(Long.valueOf(user.get("id").toString()));
			operstors.setWorkflowId(workflowId);
			operstors.setStepId(flowstep.getId());
			operstors.setOperateType(1L);
			//保存操作信息
			workFlowoperService.save(operstors);
			
			FlowWorkflowinfo flowinfo=new FlowWorkflowinfo();
			flowinfo.setWorkflowId(workflowId);
			flowinfo.setCurstepId(flowstep.getId());
			flowinfo.setLaststepId(flowstep.getId());
			flowinfo.setIsReceived(1L);
			flowinfo.setIsSubmited(1L);
			flowinfo.setIsPaused(0L);
			flowinfo.setIsAudit(0L);
			workFlowinfoService.save(flowinfo);
			
			//写入表单信息
			saveFormInfo(workflowId,user,dno,applyRemark,formDetailStr,changeType);
		}else{
			workflowId = workId;
		}
		
		saveFlowDetial(workflowId,pipeId,nodeId,Long.valueOf(user.get("id").toString()),nodeinfo.getNodeType(), oprType, logType, auditRemark,returnNodeid);
	}
	/**
	 * 保存流程明细信息
	 * @author LiuHao
	 * @time Mar 2, 2012 2:43:07 PM 
	 * @param pipeId
	 * @param nodeId
	 * @param nodeType
	 * @param oprType 操作类型 submit/see/audit
	 */
	private void saveFlowDetial(Long workflowId,Long pipeId,Long nodeId,Long userId,String nodeType,String oprType,Long logType,String auditRemark,Long returnNodeid)
		throws Exception{
		User user=WebRalasafe.getCurrentUser(Struts2Utils.getRequest());
		//查看流程
		if("see".equals(oprType)){
			List<FlowWorkflowstep> stepList = workFlowstepService.getLastStep(workflowId, nodeId, userId);
			this.saveLastStep(stepList, oprType, userId);
			workFlowinfoService.saveCurFlowinfo(stepList.get(0).getId(), workflowId, 4L);
		}else{
			FlowWorkflowlog flowlog=new FlowWorkflowlog();
			flowlog.setRemark(auditRemark);
			flowlog.setNodeinfoId(nodeId);
			flowlog.setWorkflowId(workflowId);
			flowlog.setLogType(logType);
			flowlog.setReturnNodeid(returnNodeid);
			flowlog.setOperatorId(Long.valueOf(user.get("id").toString()));
			List<FlowWorkflowstep> stepList = workFlowstepService.getLastStep(workflowId, nodeId, userId);
			this.saveLastStep(stepList, oprType, userId);
			//审批、提交
			if(logType == 1 || logType == 2){
				if(!"结束节点".equals(nodeType)){
					//活动节点
//					if(!"开始节点".equals(nodeType)){
//						workFlowoperService.delCurOprMsg(userId, workflowId);
//					}
					List<FlowWorkflowstep> sList = workFlowstepService.getStepByNosubmit(workflowId);
					//当前审批人是该节点的最后一个审批人
					if(sList.size() <= 1){
						
						Long nextNodeId = this.getNodeId(nodeId, pipeId,workflowId);
						FlowNodeinfo nextNode = flowNodeinfoService.getAndInitEntity(nextNodeId);
						//下个节点是结束节点
						if("结束节点".equals(nextNode.getNodeType())){
							//Map <K,V>  K:userId,V:ralaId
							Map<Long,Long> ralaMap = flowRalaruleService.getRalaByNodeId(nextNodeId,pipeId,workflowId);
							//结束节点存在知会人
							if(ralaMap.size()>0){
								saveFlowMsg(ralaMap,nextNode.getId(),nextNode.getNodeType(),pipeId,workflowId,stepList,logType);
							}
							
							//回写流程状态
							FlowWorkflowbase flowbase = workFlowbaseDao.getAndInitEntity(workflowId);
							flowbase.setIsFinished(2L);
							workFlowbaseDao.save(flowbase);
							
							Map map = forminfoService.getAutoForm(workflowId,pipeId);
							List list = forminfoService.getFormValueByPipeId(pipeId, "detail", workflowId);
							if(pipeId.equals(faxChangePipeId) ){
								faxChangeSave(map,list,true);
							}
							
						}else{
							//Map <K,V>  K:userId,V:ralaId
							Map<Long, Long> userMap= this.getNextUsers(nodeId, pipeId,workflowId);
							Long curUserId=Long.valueOf(user.get("id").toString());
							//如果只有一个审批人 并且下个节点是自己审批，则跳过
							if(userMap.size() == 1){
								Set<Long> keySet = userMap.keySet();
								for(Long key :keySet){
									if(curUserId.equals(key)){
										Long nNodeId = this.getNodeId(nodeId, pipeId, workflowId);
										nodeId = nNodeId;
										userMap = this.getNextUsers(nNodeId, pipeId,workflowId);
									}
								}
							}else if(userMap.size()>1){
								Set<Long> keySet = userMap.keySet();
								for(Long key :keySet){
									if(curUserId.equals(key)){
										userMap.remove(key);
									}
								}
							}
							FlowNodeinfo node = flowNodeinfoService.getAndInitEntity(this.getNodeId(nodeId, pipeId,workflowId));
							saveFlowMsg(userMap,node.getId(),node.getNodeType(),pipeId,workflowId,stepList,logType);
						}
					}
				}else{
					throw new ServiceException("数据异常，结束节点不能存在审批人，请联系系统管理员！");
//					if(sList.size() == 0){
//						
//					}
				}
				if(logType == 2){
					workFlowinfoService.saveCurFlowinfo(stepList.get(0).getId(), workflowId, 2L);
				}
			}else if(logType == 3){
				//否决
				this.flowReject(workflowId,stepList.get(0).getId(),pipeId);
			}else if(logType == 4){
				//退回
				if(returnNodeid == null){
					throw new ServiceException("退回节点ID不能为空，请联系系统管理员！");
				}
				FlowNodeinfo nodeInfo = flowNodeinfoService.getAndInitEntity(returnNodeid);
				if(nodeInfo == null){
					throw new ServiceException("节点ID："+returnNodeid+"对应的节点信息不存在，请联系系统管理员！");
				}
				//如果退回开始节点 就等于否决
				if("开始节点".equals(nodeInfo.getNodeType())){
					this.flowReject(workflowId,stepList.get(0).getId(),pipeId);
				}else{
					//workFlowoperService.delCurOprMsg(null, workflowId);
					Map<Long,Long> curMap = flowRalaruleService.getRalaByNodeId(nodeInfo.getId(),pipeId,workflowId);
					saveFlowMsg(curMap,nodeInfo.getId(),nodeInfo.getNodeType(),pipeId,workflowId,stepList,logType);
					workFlowinfoService.saveCurFlowinfo(stepList.get(0).getId(), workflowId, 3L);
				}
			//批注
			}else if(logType == 5){
				workFlowinfoService.saveCurFlowinfo(stepList.get(0).getId(), workflowId, 2L);
			}else if(logType == 6){
				//审核
				workFlowinfoService.saveCurFlowinfo(stepList.get(0).getId(), workflowId, logType);
			}
			//保存日志信息
			workFlowlogService.save(flowlog);
		}
		
	}
	/**
	 * 保存上一个步骤信息
	 * @author LiuHao
	 * @time Mar 2, 2012 4:52:56 PM
	 */
	private void saveLastStep(List<FlowWorkflowstep> stepList,String type,Long userId){
		FlowWorkflowstep step=stepList.get(0);
		if("submit".equals(type)){
			step.setSubmiterId(userId);
			step.setSubmitTime(new Date());
		}else if("see".equals(type)){
			step.setReceiveTime(new Date());
		}else if("audit".equals(type)){
			step.setSubmiterId(userId);
			step.setSubmitTime(new Date());
		}
		workFlowstepService.save(step);
	}
	/**
	 * 保存流程流转信息
	 * @author LiuHao
	 * @time Mar 8, 2012 11:35:29 AM 
	 * @param nodeId
	 * @param pipeId
	 * @param workflowId
	 * @param stepList
	 * @throws Exception
	 */
	private void saveFlowMsg(Map<Long, Long> userMap,Long nodeId,String nodeType,Long pipeId,Long workflowId,List<FlowWorkflowstep> stepList,Long logType)throws Exception{
		if(userMap.size()<1){
			throw new ServiceException("没有找到下个节点的审批人，请联系系统管理员！");
		}
		Iterator<Long> iter = userMap.keySet().iterator();
		while(iter.hasNext()){
			Long uId=iter.next();
			
			SysUser su = userService.getAndInitEntity(uId);
			if(su == null || su.getStatus().equals("0") || su.getWorkstatus().equals(0)){
				throw new ServiceException("下个节点操作者，用户ID为:"+uId+"的员工已经离职或者从系统删除，请联系系统管理员!");
			}
			//为了获得权限审批类型
			FlowRalarule ralarule = flowRalaruleService.getAndInitEntity(userMap.get(uId));
			if(ralarule == null){
				throw new ServiceException("数据异常，规则ID:"+userMap.get(uId)+"对应的权限规则为空！");
			}
			if(ralarule.getWfoptType() != 2){
				FlowRalaGive frg = flowRalaGiveService.getRalaByPipeId(pipeId, uId);
				if(frg != null){
					if(frg.getGiveId() == null || "".equals(frg.getGiveId())){
						throw new ServiceException("数据异常，下个节点存在赋权人并且赋权人为空了，请联系系统管理员!");
					}
					uId = frg.getGiveId();
				}
			}
			FlowWorkflowstep step=new FlowWorkflowstep();
			step.setNodeId(nodeId);
			step.setNodeType(nodeType);
			step.setReceiverId(uId);
			step.setWorkflowId(workflowId);
			//保存步骤信息
			workFlowstepService.save(step);
			
			FlowWorkflowoperators operstors=new FlowWorkflowoperators();
			operstors.setRuleId(userMap.get(uId));
			operstors.setUserId(uId);
			operstors.setWorkflowId(workflowId);
			operstors.setStepId(step.getId());
			operstors.setOperateType(ralarule.getWfoptType());
			//保存操作信息
			workFlowoperService.save(operstors);
			
			
			//如果操作类型是审批
			if(ralarule.getWfoptType() == 1){
				FlowWorkflowinfo flowinfo=new FlowWorkflowinfo();
				flowinfo.setWorkflowId(workflowId);
				flowinfo.setCurstepId(step.getId());
				flowinfo.setLaststepId(stepList.get(0).getId());
				if(logType == 1 || logType == 2){
					flowinfo.setIsReceived(0L);
					flowinfo.setIsSubmited(0L);
					flowinfo.setIsPaused(0L);
				}else if(logType == 3){
					flowinfo.setIsSubmited(0L);
					flowinfo.setIsReceived(0L);
					flowinfo.setIsPaused(1L);
				}
				//保存流程信息
				workFlowinfoService.save(flowinfo);
			}else if(ralarule.getWfoptType() == 2 || ralarule.getWfoptType() == 3){
				//操作类型为知会
				//操作类型为审核  需要审核 此流程才算归档，但是此流程不影响数据的变更
				FlowWorkflowinfo flowinfo=new FlowWorkflowinfo();
				flowinfo.setWorkflowId(workflowId);
				flowinfo.setCurstepId(step.getId());
				flowinfo.setLaststepId(stepList.get(0).getId());
				flowinfo.setIsReceived(0L);
				flowinfo.setIsSubmited(0L);
				flowinfo.setIsAudit(0L);
				workFlowinfoService.save(flowinfo);
				
				if(!"结束节点".equals(nodeType)){
					//如果下个节点是结束节点，则直接修改数据
					Map<Long, Long> notifyUsers= this.getNextUsers(nodeId, pipeId,workflowId);
					FlowNodeinfo notifyNode = flowNodeinfoService.getAndInitEntity(this.getNodeId(nodeId, pipeId,workflowId));
					if("结束节点".equals(notifyNode.getNodeType())){
						//Map <K,V>  K:userId,V:ralaId
						Map<Long,Long> ralaMap = flowRalaruleService.getRalaByNodeId(notifyNode.getId(),pipeId,workflowId);
						//结束节点存在知会人
						if(ralaMap.size()>0){
							saveFlowMsg(ralaMap,notifyNode.getId(),notifyNode.getNodeType(),pipeId,workflowId,stepList,logType);
						}
						
						//回写流程状态
						FlowWorkflowbase flowbase = workFlowbaseDao.getAndInitEntity(workflowId);
						flowbase.setIsFinished(2L);
						workFlowbaseDao.save(flowbase);
						
						Map map = forminfoService.getAutoForm(workflowId,pipeId);
						List list = forminfoService.getFormValueByPipeId(pipeId, "detail", workflowId);
						if(pipeId.equals(faxChangePipeId) ){
							faxChangeSave(map,list,true);
						}
						
					}else{
						//如果不是结束节点 流程继续往下走
						List<FlowWorkflowstep> nextStepList = workFlowstepService.getLastStep(workflowId, notifyNode.getId(), uId);
						saveFlowMsg(notifyUsers,notifyNode.getId(),notifyNode.getNodeType(),pipeId,workflowId,nextStepList,logType);
					}
				}
			}
		}
		
		//设置流程的当前节点
		FlowWorkflowbase fwbase = workFlowbaseDao.getAndInitEntity(workflowId);
		fwbase.setCurnodeids(nodeId);
		workFlowbaseDao.save(fwbase);
	}
	/**
	 * 流程否决
	 * @author LiuHao
	 * @time Mar 8, 2012 3:08:09 PM 
	 * @param workflowId
	 * @param stepId
	 * @throws Exception
	 */
	private void flowReject(Long workflowId,Long stepId,Long pipeId)throws Exception{
		//知会申请人
		
		FlowWorkflowstep fstep = workFlowstepService.getStepByStartNode(workflowId);
		FlowWorkflowstep step=new FlowWorkflowstep();
		step.setNodeId(fstep.getNodeId());
		step.setNodeType("结束节点");
		step.setReceiverId(fstep.getSubmiterId());
		step.setWorkflowId(workflowId);
		//保存步骤信息
		workFlowstepService.save(step);
		
		FlowWorkflowoperators operstors=new FlowWorkflowoperators();
		operstors.setUserId(fstep.getSubmiterId());
		operstors.setWorkflowId(workflowId);
		operstors.setStepId(step.getId());
		operstors.setOperateType(2L);
		//保存操作信息
		workFlowoperService.save(operstors);
		
		FlowWorkflowinfo flowinfo=new FlowWorkflowinfo();
		flowinfo.setWorkflowId(workflowId);
		flowinfo.setCurstepId(step.getId());
		flowinfo.setLaststepId(stepId);
		flowinfo.setIsReceived(0L);
		flowinfo.setIsAudit(0L);
		flowinfo.setIsSubmited(0L);
		workFlowinfoService.save(flowinfo);
		
		workFlowinfoService.saveCurFlowinfo(stepId, workflowId, 3L);
		FlowWorkflowbase flowbase = workFlowbaseDao.getAndInitEntity(workflowId);
		flowbase.setIsFinished(3L);
		//更新流程状态为已否决
		workFlowbaseDao.save(flowbase);
		
		Map map = forminfoService.getAutoForm( workflowId,pipeId);
		List list = forminfoService.getFormValueByPipeId(pipeId, "detail", workflowId);
		if(pipeId.equals(faxChangePipeId)){
			faxChangeSave(map,list,false);
		}
	}
	/**
	 * 写入表单相关表
	 * @author LiuHao
	 * @time Mar 8, 2012 5:08:56 PM 
	 * @param workflowId
	 * @param user
	 * @param dno
	 * @param applyRemark
	 * @param formDetailStr
	 * @throws Exception
	 */
	private void saveFormInfo(Long workflowId,User user,Long dno,String applyRemark,String formDetailStr,String changeType)throws Exception{
		Connection con=null;
		try {
			OprFaxIn ofi = oprFaxInService.get(dno);
			List<SysDepart> sd=departService.findBy("departNo", user.get("departId").toString());
			if(sd.size()<1){
				throw new ServiceException("数据异常，部门编码："+user.get("departId").toString()+"对应的部门信息为空了.");
			}	
			con=SessionFactoryUtils.getDataSource(workFlowbaseDao.getSessionFactory()).getConnection();
			CallableStatement cs=con.prepareCall("{call pro_flow_add(?,?,?,?,?,?,?,?,?)}");
			cs.setLong(1, dno);
			cs.setString(2, user.get("name").toString());
			cs.setString(3, applyRemark);
			cs.setString(4, sd.get(0).getDepartName());
			cs.setString(5, formDetailStr);
			cs.setLong(6, workflowId);
			cs.setString(7, workIdField);
			cs.setString(8, ofi.showGoodMsg());
			cs.registerOutParameter(9, Types.VARCHAR);
			cs.executeUpdate();
			String returnMsg=cs.getString(9);
			if(!"true".equals(returnMsg)){
				throw new ServiceException(returnMsg);
			}
		} catch (Exception e) {
			throw new ServiceException(e);
		} finally{
			if(con != null){
				con.close();
			}
		}
	}
	/**
	 * 更改申请数据回写
	 * @author LiuHao
	 * @param formMap 表单主表列表
	 * @param formList 表单明细列表
	 * @param isPass 是否通过
	 * @time Mar 28, 2012 11:16:13 AM
	 */
	private void faxChangeSave(Map formMap,List formList,boolean isPass)throws Exception{
		List<OprValueAddFee> addFeeList = new ArrayList<OprValueAddFee>();
		Object objman = formMap.get(applymanField.toUpperCase());
		Object objdno = formMap.get(dnoField.toUpperCase());
		String addStr="";
		if(objman == null || "".equals(objman)){
			throw new ServiceException("数据异常，更改申请流程主表对应的申请人字段名"+applymanField+"对应的值为空,请联系系统管理员修改！");
		}
		if(objdno == null || "".equals(objdno)){
			throw new ServiceException("数据异常，更改申请流程主表对应的配送单号字段名"+dnoField+"对应的值为空,请联系系统管理员修改！");
		}
		Long dno = Long.valueOf(objdno+"");
		List<OprChangeDetail> changeList=new ArrayList<OprChangeDetail>();
		OprChangeMain ocm = oprFaxChangeService.getChangeByDno(dno);
		if(ocm == null){
			throw new ServiceException("更改申请数据回写失败，原因：配送单号："+dno+"对应的更改信息不存在，请联系系统管理员！");
		}
		if(isPass){
			
			OprFaxIn ofi = oprFaxInService.getAndInitEntity(dno);
			Class cls = ofi.getClass();
			//将流程表单里面的数据封装到实体类
			for (int i = 0; i < formList.size(); i++) {
				Map detailMap = (Map) formList.get(i);
				OprChangeDetail ocd=new OprChangeDetail();
				ocd.setChangeField(detailMap.get("FIELD4")+"");
				Field field = cls.getDeclaredField(detailMap.get("FIELD4").toString());
				if(field == null){
					throw new ServiceException("数据异常，修改字段的英文名为空了！");
				}
				String fieldName = field.getName();
				Method method = cls.getDeclaredMethod("set" + fieldName.substring(0,1).toUpperCase()+ fieldName.substring(1),field.getType());
				
				//field.setAccessible(true);//设置允许访问 
				String fieldType=field.getType().getSimpleName();
				Object value=detailMap.get("FIELD3");
				if ("String".equals(fieldType)) { 
					if(value == null || "".equals(value)){
						//field.set(ofi, "");
						method.invoke(ofi, "");
					}else{
						//field.set(ofi,value.toString() );
						method.invoke(ofi, value.toString());
					}
	            }else if ("Long".equalsIgnoreCase(fieldType)) {
	            	if(value == null || "".equals(value)){
						//field.set(ofi, 0L);
						method.invoke(ofi, 0L);
					}else{
						method.invoke(ofi, Long.parseLong(value.toString()));
						//field.set(ofi,Long.parseLong(value.toString()));
					}
	            }else if ("Double".equalsIgnoreCase(fieldType)) {
	            	if(value == null || "".equals(value)){
						//field.set(ofi, 0.0);
	            		method.invoke(ofi, 0.0);
					}else{
						//field.set(ofi,Double.parseDouble(value.toString()));
						method.invoke(ofi, Double.parseDouble(value.toString()));
					}
	            }
				Object changePre = detailMap.get("FIELD2");
				Object changePost = detailMap.get("FIELD3");
				if(changePre == null || "".equals(changePre)){
					ocd.setChangePre("");
				}else{
					ocd.setChangePre(changePre.toString());
				}
				
				if(changePost == null || "".equals(changePost)){
					ocd.setChangePost("");
				}else{
					ocd.setChangePost(changePost.toString());
				}
				ocd.setChangeFieldZh(detailMap.get("FIELD1").toString());
				if(i == 0){
					Object addObj = detailMap.get("FIELD8");
					if(addObj != null && !"".equals(addObj)){
						addStr = addObj.toString();
					}
				}
				changeList.add(ocd);
			}
			if(addStr != null && !"".equals(addStr)){
				String[] adds = addStr.split(";");
				for (int i = 0; i < adds.length; i++) {
					String add =adds[i];
					
					String[] vas=add.split(",");
					
					OprValueAddFee ova = new OprValueAddFee();
					ova.setId(Long.valueOf(vas[0]));
					ova.setFeeName(vas[1]);
					ova.setFeeCount(Double.valueOf(vas[2]));
					ova.setPayType(vas[3]);
					
					addFeeList.add(ova);
				}
			}
			oprFaxChangeDetailService.updateChangeRecord(addFeeList, changeList, ofi, dno,objman.toString());
			//修改状态为已审核
			ocm.setStatus(2L);
		}else{
			//修改状态为已否决
			ocm.setStatus(3L);
			ocm.setIsSystem(1L);
		}
		oprFaxChangeService.save(ocm);
		
	}
	public void flowSend(Long userId, Long workflowId,Long nodeId)
			throws Exception {
		List list = workFlowstepService.createSQLQuery("select * from flow_workflowstep fs,flow_workflowoperators fo where fs.id=fo.step_id and fo.operate_type=3 and fs.receiver_id=? and fs.node_id=? and fs.workflow_id=?", userId,nodeId,workflowId).list();
		if(list.size()>0){
			throw new ServiceException("同一个节点不能重复转发流程给同一个人!");
		}
		FlowNodeinfo  node = flowNodeinfoService.get(nodeId);
		FlowWorkflowstep step = new FlowWorkflowstep();
		step.setNodeId(nodeId);
		step.setNodeType(node.getNodeType());
		step.setReceiverId(userId);
		step.setWorkflowId(workflowId);
		workFlowstepService.save(step);
		
		FlowWorkflowinfo info = new FlowWorkflowinfo();
		info.setCurstepId(step.getId());
		info.setWorkflowId(workflowId);
		info.setIsPaused(0L);
		info.setIsReceived(0L);
		info.setIsSubmited(0L);
		info.setIsAudit(0L);
		workFlowinfoService.save(info);
		
		FlowWorkflowoperators oper = new FlowWorkflowoperators();
		oper.setOperateType(4L);
		oper.setStepId(step.getId());
		oper.setWorkflowId(workflowId);
		oper.setUserId(userId);
		workFlowoperService.save(oper);
		
	}
	public void flowControl(FlowSaveVo saveVo) throws Exception {
		User user=WebRalasafe.getCurrentUser(Struts2Utils.getRequest());
		Long workflowId = saveVo.getWorkflowId();
		Long nodeId  = saveVo.getNodeId();
		Long nextNodeId = saveVo.getNextNodeId();
		Long pipeId = saveVo.getPipeId();
		
		List<FlowWorkflowstep> stepList = workFlowstepService.find("from FlowWorkflowstep step where step.workflowId=? and step.nodeId=?",workflowId,nodeId);
		boolean flag = workFlowoperService.getByStep(workflowId, stepList);
		if(!flag){
			throw new ServiceException("该节点不存在审批人，不需要调整!");
		}
		for(FlowWorkflowstep step : stepList){
			workFlowinfoService.saveCurFlowinfo(step.getId(), workflowId, 3L);
			step.setSubmiterId(Long.valueOf(user.get("id").toString()));
			if(step.getReceiveTime()==null){
				step.setReceiveTime(new Date());
			}
			step.setSubmitTime(new Date());
			workFlowstepService.save(step);
		}
		FlowNodeinfo node = flowNodeinfoService.getAndInitEntity(nextNodeId);
		//如果是结束节点，则直接修改数据
		if("结束节点".equals(node.getNodeType())){
			//Map <K,V>  K:userId,V:ralaId
			Map<Long,Long> ralaMap = flowRalaruleService.getRalaByNodeId(nextNodeId,pipeId,workflowId);
			//结束节点存在知会人
			if(ralaMap.size()>0){
				this.saveFlowMsg(ralaMap,node.getId(),node.getNodeType(),pipeId,workflowId,stepList,2L);
			}
			
			//回写流程状态
			FlowWorkflowbase flowbase = workFlowbaseDao.getAndInitEntity(workflowId);
			flowbase.setIsFinished(2L);
			workFlowbaseDao.save(flowbase);
			
			Map map = forminfoService.getAutoForm(workflowId,pipeId);
			List list = forminfoService.getFormValueByPipeId(pipeId, "detail", workflowId);
			if(pipeId.equals(faxChangePipeId) ){
				faxChangeSave(map,list,true);
			}
		}else{
			//Map <K,V>  K:userId,V:ralaId
			Map<Long, Long> userMap= this.getNextUsers(node.getId(), pipeId,workflowId);
			this.saveFlowMsg(userMap,node.getId(),node.getNodeType(),pipeId,workflowId,stepList,1L);
		}
	}
}
