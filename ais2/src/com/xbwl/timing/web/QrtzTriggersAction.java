package com.xbwl.timing.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.hssf.record.formula.functions.T;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.util.Element;
import com.xbwl.common.bean.ValidateInfo;
import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.orm.Page;
import com.xbwl.common.web.ServletUtils;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.sys.service.IBasCarService;
import com.xbwl.timing.service.IQrtzTriggersService;
import com.xbwl.timing.vo.QrtzTriggersVo;

import dto.HessianResult;
import dto.JobConfigDTO;

@Controller
@Action("qrtzTriggersAction")
@Scope("prototype")
@Namespace("/timing")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/timing/qrtz_triggersManager.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root","validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages","excludeNullProperties", "true" }
		) }
)
public class QrtzTriggersAction extends ActionSupport{
	
	private int limit=50;
	private int start=0;
	
	private Page<T> page;
	
	private ValidateInfo validateInfo = new ValidateInfo();
	
	private JobConfigDTO job;
	
	@Element(value = QrtzTriggersVo.class)
	private List<QrtzTriggersVo> objList;
	
	@Resource(name="basCarServiceImpl")
	private IBasCarService basCarService;
	
	@Resource(name="qrtzTriggersServiceImpl")
	private IQrtzTriggersService qrtzTriggersService;
	
	/**任务查询
	 * @return
	 */
	public String findAll(){
		Page<T> page = new Page<T>();
		page.setLimit(this.limit);
		page.setStart(this.start);
		Map<String, String> map = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
		try{
			String sql = qrtzTriggersService.getPageSql(map);
			setPage(this.basCarService.getPageBySqlMap(page, sql, map));
			//System.out.println(getPage().getResultMap());
//			List<Map> list  = getPage().getResultMap();
//			for(Map mapist :list ){
//				SerializableClob clob=(SerializableClob)mapist.get("JOBDATA");
//				mapist.put("JOBDATA", OprFaxMainAction.clob2String(clob));
//			}
//			getPage().setResultMap(list);
			Struts2Utils.renderJson(getPage());
		}catch (Exception e) {
			validateInfo.setSuccess(false);
    		validateInfo.setMsg(e.getLocalizedMessage());
    		e.printStackTrace();
		}
		return null;
	}
	
	/**暂停任务
	 * @return
	 */
	public String stopTigger(){
		try{
			this.qrtzTriggersService.stopJobs(objList);
		}catch (Exception e) {
			validateInfo.setSuccess(false);
    		validateInfo.setMsg(e.getLocalizedMessage());
    		e.printStackTrace();
		}
		return "reload";
	}
	
	/**开始任务
	 * @return
	 */
	public String startTigger(){
		try{
			this.qrtzTriggersService.startTriggers(objList);
		}catch (Exception e) {
			validateInfo.setSuccess(false);
    		validateInfo.setMsg(e.getLocalizedMessage());
    		e.printStackTrace();
		}
		return "reload";
	}
	
	/**开始所有
	 * @return
	 */
	public String startAll(){
		try{
			this.qrtzTriggersService.startAll();
		}catch (Exception e) {
			validateInfo.setSuccess(false);
    		validateInfo.setMsg(e.getLocalizedMessage());
    		e.printStackTrace();
		}
		return "reload";
	}
	
	/**暂停所有
	 * @return
	 */
	public String stopAll(){
		try{
			this.qrtzTriggersService.stopAll();
		}catch (Exception e) {
			validateInfo.setSuccess(false);
    		validateInfo.setMsg(e.getLocalizedMessage());
    		e.printStackTrace();
		}
		return "reload";
	}
	
	/**开始任务组
	 * @return
	 */
	public String startGroup(){
		try{
			String groupName = ServletActionContext.getRequest().getParameter("groupName");
			this.qrtzTriggersService.startGroup(groupName);
		}catch (Exception e) {
			validateInfo.setSuccess(false);
    		validateInfo.setMsg(e.getLocalizedMessage());
    		e.printStackTrace();
		}
		return "reload";
	}
	
	/**暂停任务组
	 * @return
	 */
	public String stopGroup(){
		try{
			String groupName = ServletActionContext.getRequest().getParameter("groupName");
			this.qrtzTriggersService.stopGroup(groupName);
		}catch (Exception e) {
			validateInfo.setSuccess(false);
    		validateInfo.setMsg(e.getLocalizedMessage());
    		e.printStackTrace();
		}
		return "reload";
	}
	
	/**获取当前执行任务
	 * @return
	 */
	public String getExecutingJob(){
		
		return "reload";
	}
	
	/**定时任务修改方法
	 * @return
	 */
	public String save(){
		try{
			HessianResult result = this.qrtzTriggersService.save(job);
			if(!result.getCode().equals("0")){
				throw new ServiceException(result.getMessage());
			}
		}catch (Exception e) {
			validateInfo.setSuccess(false);
    		validateInfo.setMsg(e.getLocalizedMessage());
    		e.printStackTrace();
		}
		return "reload";
	}
	
	public String removeTrigger(){
		try{
			String jobName = ServletActionContext.getRequest().getParameter("jobName");
			String jobGroup = ServletActionContext.getRequest().getParameter("jobGroup");
			HessianResult result = this.qrtzTriggersService.removeTrigger(jobName, jobGroup);
			if(!result.getCode().equals("0")){
				throw new ServiceException(result.getMessage());
			}
		}catch (Exception e) {
			validateInfo.setSuccess(false);
    		validateInfo.setMsg(e.getLocalizedMessage());
    		e.printStackTrace();
		}
		return "reload";
	}
	public String input(){
		return "input";
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public ValidateInfo getValidateInfo() {
		return validateInfo;
	}

	public void setValidateInfo(ValidateInfo validateInfo) {
		this.validateInfo = validateInfo;
	}

	public Page<T> getPage() {
		return page;
	}

	public void setPage(Page<T> page) {
		this.page = page;
	}

	public List<QrtzTriggersVo> getObjList() {
		return objList;
	}

	public void setObjList(List<QrtzTriggersVo> objList) {
		this.objList = objList;
	}

	public JobConfigDTO getJob() {
		return job;
	}

	public void setJob(JobConfigDTO job) {
		this.job = job;
	}
}
