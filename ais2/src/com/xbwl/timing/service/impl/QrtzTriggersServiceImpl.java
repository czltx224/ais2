package com.xbwl.timing.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.oper.reports.util.AppendConditions;
import com.xbwl.timing.service.IQrtzTriggersService;
import com.xbwl.timing.vo.QrtzTriggersVo;
import com.xbwl.ws.client.SchedulerWebService;

import dto.HessianResult;
import dto.JobConfigDTO;

/**定时任务服务层实现类
 * @author Administrator
 *
 */
@Service("qrtzTriggersServiceImpl")
@Transactional(rollbackFor=Exception.class)
public class QrtzTriggersServiceImpl implements IQrtzTriggersService {

	@Resource(name="schedulerWebService")
	private SchedulerWebService schedulerWebService;
	
	@Resource(name="appendConditions")
	private AppendConditions appendConditions;
	
	public String getPageSql(Map<String, String> map) throws Exception{
		
		StringBuffer sb = new StringBuffer();
		//TO_NUMBER(SUBSTR(TZ_OFFSET(sessiontimezone),1,3)) 获取当前时区
		sb.append("select t.trigger_name triggername ,t.trigger_group triggergroup,t.job_name jobname,t.job_group jobgroup,")
		  .append("t.is_volatile isvolatile,t.description,")
		  //FIXED 计算好再放入sql，以下5行都是
		  .append("to_char(TO_DATE('19700101','yyyymmdd') + t.next_fire_time/86400000 +TO_NUMBER(SUBSTR(TZ_OFFSET(sessiontimezone),1,3))/24,'yyyy-mm-dd hh24:mi:ss') nextfiretime,")
		  .append("to_char(TO_DATE('19700101','yyyymmdd') + t.prev_fire_time/86400000 +TO_NUMBER(SUBSTR(TZ_OFFSET(sessiontimezone),1,3))/24,'yyyy-mm-dd hh24:mi:ss') prevfiretime,")
		  .append("t.priority,t.trigger_state triggerstate,t.trigger_type triggertype,")
		  .append("to_char(TO_DATE('19700101','yyyymmdd') + t.start_time/86400000 +TO_NUMBER(SUBSTR(TZ_OFFSET(sessiontimezone),1,3))/24,'yyyy-mm-dd hh24:mi:ss') starttime,")
		  .append("to_char(TO_DATE('19700101','yyyymmdd') + t.end_time/86400000 +TO_NUMBER(SUBSTR(TZ_OFFSET(sessiontimezone),1,3))/24,'yyyy-mm-dd hh24:mi:ss') endtime,")
		  .append("t.calendar_name calendarname,")
		  .append("t.misfire_instr misfireinstr,")
		  .append(" c.job_bean jobbean,c.job_sql jobsql,c.job_jar jobjar,c.trigger_script triggerscript,c.hessian_url hessianurl ")
		  .append(" from qrtz_triggers t,qutz_job_config c where t.trigger_name=c.trigger_name(+) ");
		
		sb.append(appendConditions.appendConditions(map,null));
		
		return sb.toString();
	}

	public HessianResult getExecutingJob() throws Exception{
		return this.schedulerWebService.getExecutingJob();
	}

	public void startAll()throws Exception {
		this.schedulerWebService.startAll();
	}

	public void startGroup(String groupName)throws Exception {
		this.schedulerWebService.startGroup(groupName);
	}

	public void startTriggers(List<QrtzTriggersVo> objList)throws Exception {
		QrtzTriggersVo vo = null;
		for (int i = 0; i < objList.size(); i++) {
			vo = objList.get(i);
			this.schedulerWebService.startTrigger(vo.getJobName(), vo.getJobGroup());
		}
	}

	public void stopAll()throws Exception {
		this.schedulerWebService.stopAll();
	}

	public void stopGroup(String groupName) throws Exception{
		this.schedulerWebService.stopGroup(groupName);
	}

	public void stopJobs(List<QrtzTriggersVo> objList) throws Exception{
		QrtzTriggersVo vo = null;
		for (int i = 0; i < objList.size(); i++) {
			vo = objList.get(i);
			HessianResult result = this.schedulerWebService.stopJob(vo.getJobName(), vo.getJobGroup());
			//System.out.println(result.getCode()+" "+result.getMessage());
		}
	}

	public HessianResult  save(JobConfigDTO job) throws Exception {
		return this.schedulerWebService.modifyScheduler(job);
	}

	public HessianResult removeTrigger(String jobName, String jobGroup) {
		return this.schedulerWebService.removeTrigger(jobName, jobGroup);
	}
}
