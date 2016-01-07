package com.xbwl.timing.service;

import java.util.List;
import java.util.Map;

import com.xbwl.timing.vo.QrtzTriggersVo;

import dto.HessianResult;
import dto.JobConfigDTO;

/**��ʱ��������ӿ�
 * @author Administrator
 *
 */
public interface IQrtzTriggersService {

	/**��ʱ�����ѯ����ȡ����
	 * @param map
	 * @return
	 */
	public String getPageSql(Map<String, String> map)throws Exception;
	
	/**��ȡĿǰ����ִ�е�����
	 * @return
	 */
	public HessianResult getExecutingJob()throws Exception;
	
	/**��ͣ����
	 * @param objList
	 */
	public void stopJobs(List<QrtzTriggersVo> objList)throws Exception;

	/**��ͣ��
	 * @param groupName
	 */
	public void stopGroup(String groupName)throws Exception;
	
	/**��ͣ����
	 * 
	 */
	public void stopAll()throws Exception;

	/**��ʼ����
	 * 
	 */
	public void startAll()throws Exception;
	
	/**��ʼ��
	 * @param groupName
	 */
	public void startGroup(String groupName)throws Exception;

	/**��ʼ����
	 * @param objList
	 */
	public void startTriggers(List<QrtzTriggersVo> objList)throws Exception;
	
	/**��ʱ���񱣴�
	 * @param job�������
	 * @return ������
	 * @throws Exception
	 */
	public HessianResult save(JobConfigDTO job)throws Exception;
	
	/**ɾ������
	 * @param triggerName
	 * @param groupName
	 * @return
	 */
	public HessianResult removeTrigger(String jobName,String jobGroup );
}
