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
 * ���̹���  Ȩ�޹�������ʵ����
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
			if(!"�����ڵ�".equals(nodeInfo.getNodeType())){
				if(!"��ʼ�ڵ�".equals(nodeInfo.getNodeType()) && nodeInfo.getIsAutoflow() == 0){
					throw new ServiceException("�ڵ�id:"+nodeId+"��Ӧ�Ľڵ㲻���ڽڵ�����߲��Ҳ����Զ���ת������취�����ǣ�<br>1.���ڵ��޸�Ϊ�����Զ���ת��<br>2.Ϊ�ڵ�������ȷ�Ĳ����ߡ�<br>����ϵϵͳ����Ա");
				}else{
					userMap = workFlowbaseService.getNextUsers(nodeId, pipeId,workflowId);
					if(userMap.size()>0){
						return userMap;
					}
				}
			}
		}else{
			for(FlowRalarule fr:ralaList){
				//��Ա����
				if(fr.getShareType() == 1){
					//ָ����Ա
					if(fr.getUserobjType() == 1){
						if(fr.getUserIds() == null){
							throw new ServiceException("Ȩ��ָ����ԱIDΪ�գ�����ϵϵͳ����Ա��");
						}
						//SysUser su = userDao.get(fr.getUserIds());
						userMap.put(fr.getUserIds(), fr.getId());
						//userList.add(su);
						//���̲�����
					}else{
						List<Long> userIds=getUsersByType(fr,fr.getUserobjType(),false,false,workflowId);
						for(Long userId:userIds){
							userMap.put(userId, fr.getId());
						}
					}
				}else if(fr.getShareType() ==2){
					//ָ����λ
					if(fr.getStationobjType() == 1){
						if(fr.getStationId() == null){
							throw new ServiceException("Ȩ�޸�λ����Ϊָ����λ���Ҹ�λIDΪ�գ�����ϵϵͳ����Ա!");
						}
						List<SysUser> list=userDao.findBy("stationId", fr.getStationId());
						for(SysUser su:list){
							userMap.put(su.getId(), fr.getId());
						}
						//ָ�����̲�����
					}else {
						List<Long> userIds = getUsersByType(fr, fr.getStationobjType(),true,false,workflowId);
						for(Long userId:userIds){
							userMap.put(userId, fr.getId());
						}
					}
					//�ض������µĸ�λ��ϵ
				}else if(fr.getShareType() == 3){
					//��λ����(1ָ����λ��2���̲������ֶΡ�3��������Դ�ֶΡ�4����λ�ֶ�)
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
	 * ���Ȩ�޹���Ϊ��������Դ�ֶΡ�����λ�ֶΡ����̲����ߵ��û���Ϣ
	 * @author LiuHao
	 * @time Mar 2, 2012 10:33:40 AM 
	 * @param fr
	 * @param type
	 * @param isStation �Ƿ�Ϊ��λ����
	 * @param isArray �Ƿ�Ϊ��������
	 * @return
	 * @throws Exception
	 */
	private List<Long> getUsersByType(FlowRalarule fr,Long type,boolean isStation,boolean isArray,Long workflowId) throws Exception{
		List<Long> list=new ArrayList<Long>();
		//���̲����ߵ��û���Ϣ
		if(type == 2){
			if(fr.getWfoperatornodeId() == null){
				throw new ServiceException("���̲������ֶ�Ϊ�գ�����ϵϵͳ����Ա��");
			}
			List<FlowWorkflowstep> stepList=workFlowstepService.getStepByNodeId(fr.getWfoperatornodeId(),workflowId);
			for(FlowWorkflowstep fw:stepList){
				if(fw.getSubmiterId() == null){
					throw new ServiceException("Ȩ�޹���ָ�������̲����߶�Ӧ�Ľڵ���ύ��Ϊ�գ�����ԭ���У�<br>1.�ýڵ㻹û���˽����ύ������ϵϵͳ����Ա��");
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
			//��������Դ�ֶ�
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
			//����λ�ֶ�
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
	 * ��ѯ��λ���ͺ;������͵��û�
	 * @author LiuHao
	 * @time Apr 7, 2012 1:55:26 PM 
	 * @param fr
	 * @param isArray  �Ƿ�Ϊ����
	 * @return
	 */
	private List<SysUser> getStationArrayUser(FlowRalarule fr,Long userId,boolean isArray){
		List<SysUser> userList = new ArrayList<SysUser>();
		SysUser su = userDao.get(userId);
		if(su == null){
			throw new ServiceException("�û�ID��"+userId+"��Ӧ���û���Ϣ�����ڣ�����ϵϵͳ����Ա��");
		}
		if(isArray){
			//�ض������µĸ�λ��ϵ(1ֱ���ϼ���λ\2�����ϼ���λ\3��λ������)
			if(fr.getUsershareType() == 1){
				
				SysStation station = stationDao.get(su.getStationId());
				if(station.getParentStationId() == null){
					throw new ServiceException("��λ��"+station.getStationName()+"��ֱ���ϼ���λΪ�գ�����ԭ���ǣ�<br>1.ָ���˲���ȷ�ĸ�λȨ�޹���<br>2.��λ������������<br>����ϵϵͳ����Ա��");
				}
				userList = userDao.findBy("stationId", station.getParentStationId());
			}else if(fr.getUsershareType() ==2){
				
			}else if(fr.getUsershareType() == 3){
				SysDepart sd = departService.getAndInitEntity(su.getDepartId());
				if(sd == null){
					throw new ServiceException("��¸��ڵ������ʧ�ܣ�����ID��"+su.getDepartId()+"��Ӧ�Ĳ�����ϢΪ���ˣ�");
				}
				userList = userDao.findBy("stationId", sd.getSysStation().getStationId());
			}
		}else{
			userList = userDao.getUsersByStationId(su.getStationId());
		}
		return userList;
	}
}
