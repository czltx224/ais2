package com.xbwl.flow.service;

import java.util.List;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FlowNodeinfo;

/**
 * 节点管理 服务层接口
 *@author LiuHao
 *@time Feb 17, 2012 4:09:59 PM
 */
public interface IFlowNodeinfoService extends IBaseService<FlowNodeinfo,Long> {
	/**
	 * 根据流程ID查询节点信息
	 * @author LiuHao
	 * @time Feb 22, 2012 4:07:18 PM 
	 * @param pipeId
	 * @return
	 * @throws Exception
	 */
	public List<FlowNodeinfo> getNodeByPipeid(Long pipeId)throws Exception;
	/**
	 * 查询开始或者结束节点
	 * @author LiuHao
	 * @time Feb 22, 2012 5:46:34 PM 
	 * @param pipeId
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public FlowNodeinfo getSEnodeinfoByPipeId(Long pipeId,String type) throws Exception;
}
