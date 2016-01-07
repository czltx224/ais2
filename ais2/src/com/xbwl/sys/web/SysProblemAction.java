package com.xbwl.sys.web;

import java.io.File;
import java.text.DateFormat;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.xbwl.common.orm.Page;
import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.OprException;
import com.xbwl.entity.SysProblem;
import com.xbwl.sys.service.ISysProblemService;

/**
 * author shuw
 * time Feb 11, 2012 2:46:18 PM
 */
@Controller
@Action("sysProblemAction")
@Scope("prototype")
@Namespace("/sys")
@Results( {
	@Result(name = "reload", type = "json", params = { "root","validateInfo", "excludeNullProperties", "true" }),
	@Result(name = "input", location = "/WEB-INF/xbwl/sys/sys_Problem.jsp", type = "dispatcher"),
	@Result(name = "list", type = "json", params = { "root", "pages","includeProperties", "*" }) })
public class SysProblemAction extends SimpleActionSupport {
	
	private SysProblem sysProblem;

	private String doProblemResultString;  //临时保存处理结果
	private String ts;
	
	@Resource(name="sysProblemServiceImpl")
	private ISysProblemService sysProblemService;

	private String oprProblemPhotoAddrFileName;
	private File oprProblemPhotoAddr;   // 问题图片
	
	@Value("${exception_images_url}")
	private String exceptionImagesUrl;

	/**
	 * 新建问题，保存图片
	 * @return
	 */
	public String savePhoto(){
		  try {
		//	sysProblem=new SysProblem();
//			sysProblem.setProblemContent(content);
			sysProblemService.savePhoto(sysProblem,oprProblemPhotoAddrFileName, oprProblemPhotoAddr);
			getValidateInfo().setMsg("数据保存成功！");
			addMessage("数据保存成功！");
		} catch (Exception e) {
			getValidateInfo().setSuccess(false);
			getValidateInfo().setMsg("数据保存失败！");
			addError("数据保存失败！", e);
		}
		Struts2Utils.render("text/html; charset=UTF-8", JSONObject.fromObject(
				getValidateInfo()).toString());
		return null;
	}
	

	public String saveHandle() {
        try {
        	prepareModel();
        	if(WebRalasafe.permit(Struts2Utils.getRequest(), getPrivilege(), getModel(), getContextMap())){
        		User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
        		sysProblem.setTs(ts);
        		sysProblem.setProblemStatus(3l);
        		sysProblem.setDoProblemName(user.get("name").toString());
        		sysProblem.setDoProblemResult(doProblemResultString);
        		
        		if(sysProblem.getDoProblemMaxTime()!=null){
        			if(new Date().after(sysProblem.getDoProblemMaxTime())){  //判断时间，如果处理时间比最大时间还晚，不及时
        				sysProblem.setDoProblemStatus(0l);
        			}else{
        				sysProblem.setDoProblemStatus(1l);
        			}
        		}
        		sysProblem.setDoProblemTime(new Date());
        		getManager().save(sysProblem);
        		getValidateInfo().setMsg("数据保存成功！");
        		addMessage("数据保存成功！");
        	}else{
        		getValidateInfo().setSuccess(false);
        		getValidateInfo().setMsg(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        		addMessage(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        	}
        } catch (Exception e) {
        	getValidateInfo().setSuccess(false);
        	getValidateInfo().setMsg("数据保存失败！");
            addError("数据保存失败！", e);
        }
		return RELOAD;
	}

	
	public String list() throws Exception {
		   try {
	            //在list前会默认调用prepareList
	            //分页查询
	        	Struts2Utils.getSession().setAttribute("gotoPage",Struts2Utils.getRequest().getRequestURI() );
	        	Page  pages = getPages();
	        	pages.setOrder(Page.DESC+","+Page.ASC);
	        	pages.setOrderBy("createTime,problemStatus");
	            setPages(getManager().findPage(pages, getFilters()));
	            if(isXml()){
	            	Struts2Utils.renderXml(pages);
	            	return null;
	            }
	        } catch (Exception e) {
	            addError("数据查询失败！", e);
	        }
	        return LIST;
	}


	/**
	 * 展示图片，返回图片地址
	 * @return
	 */
	public String showPhoto(){
		Struts2Utils.renderJson(exceptionImagesUrl);
		return null;
	}
	
	protected Object createNewInstance() {
		return new SysProblem();
	}

	public Map getContextMap() {
		return null;
	}

	protected IBaseService getManager() {
		return sysProblemService;
	}

	public Object getModel() {
		return sysProblem;
	}

	public void setModel(Object obj) {
		sysProblem=(SysProblem)obj;
	}


	public String getOprProblemPhotoAddrFileName() {
		return oprProblemPhotoAddrFileName;
	}


	public void setOprProblemPhotoAddrFileName(String oprProblemPhotoAddrFileName) {
		this.oprProblemPhotoAddrFileName = oprProblemPhotoAddrFileName;
	}


	public File getOprProblemPhotoAddr() {
		return oprProblemPhotoAddr;
	}


	public void setOprProblemPhotoAddr(File oprProblemPhotoAddr) {
		this.oprProblemPhotoAddr = oprProblemPhotoAddr;
	}


	public SysProblem getSysProblem() {
		return sysProblem;
	}


	public void setSysProblem(SysProblem sysProblem) {
		this.sysProblem = sysProblem;
	}

	
	public String getExceptionImagesUrl() {
		return exceptionImagesUrl;
	}

	public void setExceptionImagesUrl(String exceptionImagesUrl) {
		this.exceptionImagesUrl = exceptionImagesUrl;
	}



	public String getTs() {
		return ts;
	}


	public void setTs(String ts) {
		this.ts = ts;
	}


	public String getDoProblemResultString() {
		return doProblemResultString;
	}


	public void setDoProblemResultString(String doProblemResultString) {
		this.doProblemResultString = doProblemResultString;
	}
}
