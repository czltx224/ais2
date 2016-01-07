package com.xbwl.flow.service;

import java.util.List;
import java.util.Map;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FlowForminfo;

/**
 * 流程管理-表单信息服务层接口
 *@author LiuHao
 *@time Feb 14, 2012 4:29:00 PM
 */
public interface IForminfoService extends IBaseService<FlowForminfo,Long> {
	/**
	 * 保存表单信息
	 * @author LiuHao
	 * @time Feb 27, 2012 11:16:52 AM 
	 * @param formInfo
	 * @throws Exception
	 */
	public void saveFormInfo(FlowForminfo flowForminfo)throws Exception;
	/**
	 * 根据流程ID 查询表单布局信息
	 * @author LiuHao
	 * @time Feb 27, 2012 6:04:53 PM 
	 * @param pipeId
	 * @param formType main or detail
	 * @return
	 * @throws Exception
	 */
	public List getFormByPipeId(Long pipeId,String formType)throws Exception;
	/**
	 * 根据流程ID查询表单对应的值
	 * @author LiuHao
	 * @time Feb 28, 2012 1:55:51 PM 
	 * @param pipeId
	 * @param formType 表单类型
	 * @return
	 * @throws Exception
	 */
	public List getFormValueByPipeId(Long pipeId,String formType,Long workflowId)throws Exception;
	/**
	 * 查询主表信息
	 * @author LiuHao
	 * @time Mar 8, 2012 2:41:34 PM 
	 * @param tableName
	 * @param workflowId
	 * @return
	 * @throws Exception
	 */
	public  Map getAutoForm(Long workflowId,Long pipeId)throws Exception;
	/**
	 * 查询表单主表的信息
	 * @author LiuHao
	 * @time Apr 10, 2012 1:58:19 PM 
	 * @param pipeId
	 * @return
	 * @throws Exception
	 */
	public List<Map> getMainForm(Long pipeId)throws Exception;
	
	
}
