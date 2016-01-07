package com.xbwl.timing.service;

import java.util.List;
import java.util.Map;

import com.xbwl.timing.vo.QrtzTriggersVo;

import dto.HessianResult;
import dto.JobConfigDTO;

/**定时任务服务层接口
 * @author Administrator
 *
 */
public interface IQrtzTriggersService {

	/**定时任务查询语句获取方法
	 * @param map
	 * @return
	 */
	public String getPageSql(Map<String, String> map)throws Exception;
	
	/**获取目前正在执行的任务
	 * @return
	 */
	public HessianResult getExecutingJob()throws Exception;
	
	/**暂停任务
	 * @param objList
	 */
	public void stopJobs(List<QrtzTriggersVo> objList)throws Exception;

	/**暂停组
	 * @param groupName
	 */
	public void stopGroup(String groupName)throws Exception;
	
	/**暂停所有
	 * 
	 */
	public void stopAll()throws Exception;

	/**开始所有
	 * 
	 */
	public void startAll()throws Exception;
	
	/**开始组
	 * @param groupName
	 */
	public void startGroup(String groupName)throws Exception;

	/**开始任务
	 * @param objList
	 */
	public void startTriggers(List<QrtzTriggersVo> objList)throws Exception;
	
	/**定时任务保存
	 * @param job保存对象
	 * @return 保存结果
	 * @throws Exception
	 */
	public HessianResult save(JobConfigDTO job)throws Exception;
	
	/**删除任务
	 * @param triggerName
	 * @param groupName
	 * @return
	 */
	public HessianResult removeTrigger(String jobName,String jobGroup );
}
