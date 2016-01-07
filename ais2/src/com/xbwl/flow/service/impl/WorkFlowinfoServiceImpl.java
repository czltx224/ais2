package com.xbwl.flow.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.entity.FlowWorkflowinfo;
import com.xbwl.flow.dao.IWorkFlowinfoDao;
import com.xbwl.flow.service.IWorkFlowinfoService;

/**
 * ������ת��Ϣ�����ʵ����
 *@author LiuHao
 *@time Feb 24, 2012 5:24:15 PM
 */
@Service("workFlowinfoServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class WorkFlowinfoServiceImpl extends BaseServiceImpl<FlowWorkflowinfo,Long> implements
		IWorkFlowinfoService {
	@Resource(name="workFlowinfoHibernateDaoImpl")
	private IWorkFlowinfoDao workFlowinfoDao;
	@Override
	public IBaseDAO getBaseDao() {
		return workFlowinfoDao;
	}
	public void saveCurFlowinfo(Long curStepid, Long workflowId, Long logType)
			throws Exception {
		List<FlowWorkflowinfo> infoList = workFlowinfoDao.find("from FlowWorkflowinfo fw where fw.workflowId=? and fw.curstepId=?", workflowId,curStepid);
		if(infoList.size()!=1){
			throw new ServiceException("���浱ǰ����������Ϣʧ�ܣ�û���ҵ����߷��ֶ�����ǰ�����������Ϣ��");
		}
		FlowWorkflowinfo info=infoList.get(0);
		//�鿴
		if(logType == 4){
			info.setIsReceived(1L);
		}else if(logType == 2){
			info.setIsSubmited(1L);
		}else if(logType == 3){
			info.setIsSubmited(1L);
			info.setIsPaused(1L);
			info.setIsAudit(1L);
		}else if(logType == 6){
			info.setIsSubmited(1L);
			info.setIsPaused(1L);
			info.setIsAudit(1L);
		}else if(logType == 7){
			//���̸�Ԥ
			info.setIsSubmited(1L);
			info.setIsPaused(1L);
			info.setIsAudit(1L);
			info.setIsReceived(1L);
		}
		workFlowinfoDao.save(info);
	}
}
