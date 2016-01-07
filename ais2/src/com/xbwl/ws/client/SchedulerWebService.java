package com.xbwl.ws.client;

import dto.HessianResult;
import dto.JobConfigDTO;

/**
 * @author Administrator
 * @createTime 3:38:54 PM
 * @updateName Administrator
 * @updateTime 3:38:54 PM
 * 
 */
public interface SchedulerWebService {
	
	/**
	 * @param job
	 * @return
	 */
	public HessianResult modifyScheduler(JobConfigDTO job);
	
	
	/**��ȡĿǰ����ִ�е�����
	 * @return
	 */
	public HessianResult getExecutingJob();
	
	/**��ͣ����
	 * @param triggerName
	 * @param groupName
	 * @return
	 */
	public HessianResult stopJob(String triggerName, String groupName);
	
	
	/**
	 * @param groupName
	 * @return
	 */
	public HessianResult stopGroup( String groupName);
	
	/**
	 * @return
	 */
	public HessianResult stopAll( );

	/**
	 * @return
	 */
	public HessianResult startAll( );
	
	/**
	 * @param groupName
	 * @return
	 */
	public HessianResult startGroup(String groupName );
	
	/**
	 * @param triggerName
	 * @param groupName
	 * @return
	 */
	public HessianResult startTrigger(String triggerName,String groupName );
	
	/**
	 * @param triggerName
	 * @param groupName
	 * @return
	 */
	public HessianResult removeTrigger(String jobName,String jobGroup );
}
