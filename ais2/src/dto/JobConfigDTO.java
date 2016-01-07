package dto;

import java.io.Serializable;

/**
 * @author Administrator
 * @createTime 4:15:13 PM
 * @updateName Administrator
 * @updateTime 4:15:13 PM
 * 
 */
public class JobConfigDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String triggerName;
	String triggerGroup;
	String triggerScript;
	String jobName;
	String jobBean;
	String jobSql;
	String jobJar;
	String jobGroup;
	String hessianUrl;
	String description;
	
	public String getHessianUrl() {
		return hessianUrl;
	}
	public void setHessianUrl(String hessianUrl) {
		this.hessianUrl = hessianUrl;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTriggerName() {
		return triggerName;
	}
	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
	}
	public String getTriggerGroup() {
		return triggerGroup;
	}
	public void setTriggerGroup(String triggerGroup) {
		this.triggerGroup = triggerGroup;
	}
	public String getTriggerScript() {
		return triggerScript;
	}
	public void setTriggerScript(String triggerScript) {
		this.triggerScript = triggerScript;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobBean() {
		return jobBean;
	}
	public void setJobBean(String jobBean) {
		this.jobBean = jobBean;
	}
	public String getJobSql() {
		return jobSql;
	}
	public void setJobSql(String jobSql) {
		this.jobSql = jobSql;
	}
	public String getJobJar() {
		return jobJar;
	}
	public void setJobJar(String jobJar) {
		this.jobJar = jobJar;
	}
	public String getJobGroup() {
		return jobGroup;
	}
	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

}
