package com.xbwl.flow.service;

import java.util.List;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FlowExport;

/**
 * 流程管理 出口管理 数据访问层接口
 *@author LiuHao
 *@time Feb 20, 2012 11:03:31 AM
 */
public interface IFlowExportService extends IBaseService<FlowExport,Long> {
	/**
	 * 批量保存/修改出口信息
	 * @author LiuHao
	 * @time Feb 20, 2012 3:01:44 PM 
	 * @param exports
	 * @throws Exception
	 */
	public void saveExports(List<FlowExport> exports)throws Exception;
	/**
	 * 根据流程ID查询所有出口信息
	 * @author LiuHao
	 * @time Feb 22, 2012 4:04:49 PM 
	 * @throws Exception
	 */
	public List<FlowExport> getExportByPipeid(Long pipeId) throws Exception;
}
