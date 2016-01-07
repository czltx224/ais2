package com.xbwl.flow.service;

import org.jdom.Document;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FlowPipeinfo;

/**
 * 流程管理 管道信息服务层接口
 *@author LiuHao
 *@time Feb 17, 2012 8:53:05 AM
 */
public interface IFlowPipeinfoService extends IBaseService<FlowPipeinfo,Long> {
	/**
	 * 查询流程图
	 * @author LiuHao
	 * @time Feb 22, 2012 4:33:34 PM 
	 * @param pipeId
	 * @return
	 * @throws Exception
	 */
	public Document getLayoutXml(Long pipeId) throws Exception;
	/**
	 * 设置并保存坐标为0的节点或者出口信息
	 * @author LiuHao
	 * @time Feb 22, 2012 6:20:02 PM 
	 * @param flowId
	 * @throws Exception
	 */
	public void setDrawpos(Long flowId)throws Exception;
	/**
	 * 保存用户拖动的流程图坐标
	 * @author LiuHao
	 * @time Feb 23, 2012 5:14:58 PM 
	 * @throws Exception
	 */
	public void saveLayoutXml(String strXml)throws Exception;
	/**
	 * 获得流程图xml的字符串格式
	 * @author LiuHao
	 * @time Feb 25, 2012 3:08:34 PM 
	 * @param pipeId
	 * @param workflowId
	 * @return
	 * @throws Exception
	 */
	public String getShowChartStr(Long pipeId,Long workflowId)throws Exception;
	/**
	 * 获得流程图的XML格式
	 * @author LiuHao
	 * @time Feb 25, 2012 3:09:47 PM 
	 * @return
	 * @throws Exception
	 */
	public Document getShowChartXml(Long pipeId,Long workflowId)throws Exception;

}
