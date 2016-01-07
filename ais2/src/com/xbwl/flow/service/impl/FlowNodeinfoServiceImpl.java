package com.xbwl.flow.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.entity.FlowNodeinfo;
import com.xbwl.flow.dao.IFlowNodeinfoDao;
import com.xbwl.flow.service.IFlowNodeinfoService;

/**
 * �ڵ���� �����ʵ����
 *@author LiuHao
 *@time Feb 17, 2012 4:11:01 PM
 */
@Service("flowNodeinfoServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class FlowNodeinfoServiceImpl extends BaseServiceImpl<FlowNodeinfo,Long> implements
		IFlowNodeinfoService {
	@Resource(name="flowNodeinfoHibernateDaoImpl")
	private IFlowNodeinfoDao flowNodeinfoDao;
	@Override
	public IBaseDAO getBaseDao() {
		return flowNodeinfoDao;
	}
	public List<FlowNodeinfo> getNodeByPipeid(Long pipeId) throws Exception {
		return this.find("from FlowNodeinfo fn where fn.pipeId=? and fn.status=1", pipeId);
	}
	public FlowNodeinfo getSEnodeinfoByPipeId(Long pipeId, String type)
			throws Exception {
		String nodeType="";
		if("start".equals(type)){
			nodeType="��ʼ�ڵ�";
		}else{
			nodeType="�����ڵ�";
		}
		List<FlowNodeinfo> nodeList=this.find("from FlowNodeinfo fn where fn.nodeType=? and fn.pipeId=? and fn.status=1", nodeType,pipeId);
		if(nodeList.size()<1){
			logger.error("�����̲�����"+nodeType);
			return new FlowNodeinfo();
		}else if(nodeList.size()>1){
			logger.error("�������ж��"+nodeType);
			return nodeList.get(0);
		}else{
			return nodeList.get(0);
		}
	}

}
