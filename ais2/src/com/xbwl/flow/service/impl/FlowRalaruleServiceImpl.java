package com.xbwl.flow.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.entity.BasDictionaryDetail;
import com.xbwl.entity.FlowNodeinfo;
import com.xbwl.entity.FlowRalarule;
import com.xbwl.entity.FlowWorkflowstep;
import com.xbwl.entity.SysDepart;
import com.xbwl.entity.SysStation;
import com.xbwl.flow.dao.IFlowNodeinfoDao;
import com.xbwl.flow.dao.IFlowRalaruleDao;
import com.xbwl.flow.service.IFlowRalaruleService;
import com.xbwl.flow.service.IWorkFlowbaseService;
import com.xbwl.flow.service.IWorkFlowstepService;
import com.xbwl.rbac.Service.IDepartService;
import com.xbwl.rbac.dao.IUserDao;
import com.xbwl.rbac.entity.SysUser;
import com.xbwl.sys.dao.IStationDao;
import com.xbwl.sys.service.IBasDictionaryDetailService;

/**
 * 流程管理  权限规则服务层实现类
 *@author LiuHao
 *@time Feb 21, 2012 8:57:01 AM
 */
@Service("flowRalaruleServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class FlowRalaruleServiceImpl extends BaseServiceImpl<FlowRalarule,Long> implements
		IFlowRalaruleService {
	private final static Long FORM_USER_FIELD=288L;
	private final static Long FORM_STATION_FIELD=289L;
	@Resource(name="flowRalaruleHibernateDaoImpl")
	private IFlowRalaruleDao flowRalaruleDao;
	@Resource(name="flowNodeinfoHibernateDaoImpl")
	private IFlowNodeinfoDao flowNodeinfoDao;
	@Resource(name="workFlowbaseServiceImpl")
	private IWorkFlowbaseService workFlowbaseService;
	@Resource(name="userHibernateDaoImpl")
	private IUserDao userDao;
	@Resource(name="stationHibernateDaoImpl")
	private IStationDao stationDao;
	@Resource(name="basDictionaryDetailServiceImpl")
	private IBasDictionaryDetailService basDictionaryDetailService;
	@Resource(name="workFlowstepServiceImpl")
	private IWorkFlowstepService workFlowstepService;
	@Resource(name="departServiceImpl")
	private IDepartService departService;
	
	
	@Override
	public IBaseDAO getBaseDao() {
		return flowRalaruleDao;
	}

	public Map<Long,Long> getRalaByNodeId(Long nodeId,Long pipeId,Long workflowId) throws Exception {
		//List<SysUser> userList=new ArrayList<SysUser>();
		Map<Long,Long> userMap=new HashMap<Long, Long>();
		List<FlowRalarule> ralaList=flowRalaruleDao.find("from FlowRalarule fr where fr.nodeId=? and fr.status=1", nodeId);
		if(ralaList.size()<1){
			FlowNodeinfo nodeInfo = flowNodeinfoDao.getAndInitEntity(nodeId);
			if(!"结束节点".equals(nodeInfo.getNodeType())){
				if(!"开始节点".equals(nodeInfo.getNodeType()) && nodeInfo.getIsAutoflow() == 0){
					throw new ServiceException("节点id:"+nodeId+"对应的节点不存在节点操作者并且不能自动流转，解决办法可能是：<br>1.将节点修改为可以自动流转。<br>2.为节点设置正确的操作者。<br>请联系系统管理员");
				}else{
					userMap = workFlowbaseService.getNextUsers(nodeId, pipeId,workflowId);
					if(userMap.size()>0){
						return userMap;
					}
				}
			}
		}else{
			for(FlowRalarule fr:ralaList){
				//人员类型
				if(fr.getShareType() == 1){
					//指定人员
					if(fr.getUserobjType() == 1){
						if(fr.getUserIds() == null){
							throw new ServiceException("权限指定人员ID为空，请联系系统管理员！");
						}
						//SysUser su = userDao.get(fr.getUserIds());
						userMap.put(fr.getUserIds(), fr.getId());
						//userList.add(su);
						//流程操作者
					}else{
						List<Long> userIds=getUsersByType(fr,fr.getUserobjType(),false,false,workflowId);
						for(Long userId:userIds){
							userMap.put(userId, fr.getId());
						}
					}
				}else if(fr.getShareType() ==2){
					//指定岗位
					if(fr.getStationobjType() == 1){
						if(fr.getStationId() == null){
							throw new ServiceException("权限岗位类型为指定岗位并且岗位ID为空，请联系系统管理员!");
						}
						List<SysUser> list=userDao.findBy("stationId", fr.getStationId());
						for(SysUser su:list){
							userMap.put(su.getId(), fr.getId());
						}
						//指定流程操作者
					}else {
						List<Long> userIds = getUsersByType(fr, fr.getStationobjType(),true,false,workflowId);
						for(Long userId:userIds){
							userMap.put(userId, fr.getId());
						}
					}
					//特定矩阵下的岗位体系
				}else if(fr.getShareType() == 3){
					//岗位类型(1指定岗位、2流程操作者字段、3表单人力资源字段、4表单岗位字段)
					if(fr.getStationobjType() ==1){
						SysStation station = stationDao.get(fr.getStationId());
						List<SysUser> dList=userDao.findBy("stationId",station.getParentStationId());
						for(SysUser u:dList){
							userMap.put(u.getId(), fr.getId());
						}
					}else{
						List<Long> userIds = getUsersByType(fr,fr.getStationobjType(),true,true,workflowId);
						for(Long userId:userIds){
							userMap.put(userId, fr.getId());
						}
					}
				}
			}
		}
		return userMap;
	}
	/**
	 * 获得权限规则为表单人力资源字段、表单岗位字段、流程操作者的用户信息
	 * @author LiuHao
	 * @time Mar 2, 2012 10:33:40 AM 
	 * @param fr
	 * @param type
	 * @param isStation 是否为岗位类型
	 * @param isArray 是否为矩阵类型
	 * @return
	 * @throws Exception
	 */
	private List<Long> getUsersByType(FlowRalarule fr,Long type,boolean isStation,boolean isArray,Long workflowId) throws Exception{
		List<Long> list=new ArrayList<Long>();
		//流程操作者的用户信息
		if(type == 2){
			if(fr.getWfoperatornodeId() == null){
				throw new ServiceException("流程操作者字段为空，请联系系统管理员！");
			}
			List<FlowWorkflowstep> stepList=workFlowstepService.getStepByNodeId(fr.getWfoperatornodeId(),workflowId);
			for(FlowWorkflowstep fw:stepList){
				if(fw.getSubmiterId() == null){
					throw new ServiceException("权限规则指定的流程操作者对应的节点的提交人为空，可能原因有：<br>1.该节点还没有人进行提交。请联系系统管理员！");
				}				
				if(isStation){
					List<SysUser> sus = getStationArrayUser(fr,fw.getSubmiterId(),isArray);
					for(SysUser u:sus){
						list.add(u.getId());
					}
				}else{
					list.add(fw.getSubmiterId());
				}
			}
			//表单人力资源字段
		}else if(type == 3){
			List<BasDictionaryDetail> dicList=basDictionaryDetailService.getDicDetail(FORM_USER_FIELD);
			for(BasDictionaryDetail bdd:dicList){
				Long userId = Long.valueOf(bdd.getTypeName());
				if(isStation){
					List<SysUser> sus = getStationArrayUser(fr,userId,isArray);
					for(SysUser u:sus){
						list.add(u.getId());
					}
				}else{
					list.add(userId);
				}
			}
			//表单岗位字段
		}else if(type == 4){
			List<BasDictionaryDetail> dicList=basDictionaryDetailService.getDicDetail(FORM_STATION_FIELD);
			for(BasDictionaryDetail bdd:dicList){
				Long stationId = Long.valueOf(bdd.getTypeName());
				SysStation station = stationDao.get(stationId);
				List<SysUser> dList=userDao.findBy("stationId",station.getParentStationId());
				for(SysUser u:dList){
					list.add(u.getId());
				}
			}
		}
		return list;
	}
	/**
	 * 查询岗位类型和矩阵类型的用户
	 * @author LiuHao
	 * @time Apr 7, 2012 1:55:26 PM 
	 * @param fr
	 * @param isArray  是否为矩阵
	 * @return
	 */
	private List<SysUser> getStationArrayUser(FlowRalarule fr,Long userId,boolean isArray){
		List<SysUser> userList = new ArrayList<SysUser>();
		SysUser su = userDao.get(userId);
		if(su == null){
			throw new ServiceException("用户ID："+userId+"对应的用户信息不存在，请联系系统管理员！");
		}
		if(isArray){
			//特定矩阵下的岗位体系(1直接上级岗位\2所有上级岗位\3岗位负责人)
			if(fr.getUsershareType() == 1){
				
				SysStation station = stationDao.get(su.getStationId());
				if(station.getParentStationId() == null){
					throw new ServiceException("岗位："+station.getStationName()+"的直接上级岗位为空，可能原因是：<br>1.指定了不正确的岗位权限规则。<br>2.岗位基础资料有误。<br>请联系系统管理员！");
				}
				userList = userDao.findBy("stationId", station.getParentStationId());
			}else if(fr.getUsershareType() ==2){
				
			}else if(fr.getUsershareType() == 3){
				SysDepart sd = departService.getAndInitEntity(su.getDepartId());
				if(sd == null){
					throw new ServiceException("活动下个节点操作者失败，部门ID："+su.getDepartId()+"对应的部门信息为空了！");
				}
				userList = userDao.findBy("stationId", sd.getSysStation().getStationId());
			}
		}else{
			userList = userDao.getUsersByStationId(su.getStationId());
		}
		return userList;
	}
}
