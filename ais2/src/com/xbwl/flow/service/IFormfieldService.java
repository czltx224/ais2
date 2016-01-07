package com.xbwl.flow.service;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FlowFormfield;
import com.xbwl.entity.FlowForminfo;

/**
 * 流程管理-表单字段服务层接口
 *@author LiuHao
 *@time Feb 14, 2012 4:20:24 PM
 */
public interface IFormfieldService extends IBaseService<FlowFormfield,Long> {
	/**
	 * 根据表单ID查询字段
	 * @author LiuHao
	 * @time Feb 15, 2012 3:04:08 PM 
	 * @param formId
	 * @return
	 * @throws Exception
	 */
	public int findFieldByFormId(Long formId)throws Exception;
	/**
	 * 保存表单字段信息
	 * @author LiuHao
	 * @time Feb 27, 2012 1:56:57 PM 
	 * @param flowFormField
	 * @throws Exception
	 */
	public void saveFormfield(FlowFormfield flowFormfield,String tableName)throws Exception;
	/**
	 * 根据表单ID，表单名称 查询表单信息
	 * @author LiuHao
	 * @time Apr 10, 2012 2:18:07 PM 
	 * @param formId
	 * @param labelName
	 * @return
	 * @throws Exception
	 */
	public FlowFormfield getInfoByLabelname(Long formId,String labelName)throws Exception;
}
