package com.xbwl.cus.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.List;
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
import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.cus.service.ICusComplaintService;
import com.xbwl.cus.service.ICusDemandService;
import com.xbwl.entity.CusComplaint;
import com.xbwl.entity.CusDemand;
import com.xbwl.entity.CusLinkman;

/**
 * �ͻ�Ͷ���뽨����Ʋ�
 *@author LiuHao
 *@time Oct 8, 2011 1:53:31 PM
 */
@Controller
@Action("cusComplaintAction")
@Scope("prototype")
@Namespace("/cus")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/cus/cus_complaint.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "msg", type = "json", params = { "root", "validateInfo",
				"excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }
		) }
)
public class CusComplaintAction extends SimpleActionSupport {
	@Resource(name="cusComplaintServiceImpl")
	private ICusComplaintService cusComplaintService;
	private CusComplaint cusComplaint;
	private String dutyName;
	private Date dutyTime;
	private Double dealCost;
	private String dealResult;
	private Long comId;
	private File upFile;
	private String upFileFileName;
	@Value("${exception_images_url}")
	private String exceptionImagesUrl;
	@Override
	protected Object createNewInstance() {
		return new CusComplaint();
	}
	@Override
	public Map getContextMap() {
		return null;
	}
	@Override
	protected IBaseService getManager() {
		return cusComplaintService;
	}
	@Override
	public Object getModel() {
		return cusComplaint;
	}
	@Override
	public void setModel(Object obj) {
		this.cusComplaint=(CusComplaint)obj;
	}
	
	
	/**
	 * @return the upFile
	 */
	public File getUpFile() {
		return upFile;
	}
	/**
	 * @param upFile the upFile to set
	 */
	public void setUpFile(File upFile) {
		this.upFile = upFile;
	}
	
	/**
	 * @return the upFileFileName
	 */
	public String getUpFileFileName() {
		return upFileFileName;
	}
	/**
	 * @param upFileFileName the upFileFileName to set
	 */
	public void setUpFileFileName(String upFileFileName) {
		this.upFileFileName = upFileFileName;
	}
	
	/**
	 * @return the comId
	 */
	public Long getComId() {
		return comId;
	}
	/**
	 * @param comId the comId to set
	 */
	public void setComId(Long comId) {
		this.comId = comId;
	}
	/**
	 * @return the exceptionImagesUrl
	 */
	public String getExceptionImagesUrl() {
		return exceptionImagesUrl;
	}
	/**
	 * @param exceptionImagesUrl the exceptionImagesUrl to set
	 */
	public void setExceptionImagesUrl(String exceptionImagesUrl) {
		this.exceptionImagesUrl = exceptionImagesUrl;
	}
	
	/**
	 * @return the dutyName
	 */
	public String getDutyName() {
		return dutyName;
	}
	/**
	 * @param dutyName the dutyName to set
	 */
	public void setDutyName(String dutyName) {
		this.dutyName = dutyName;
	}
	/**
	 * @return the dutyTime
	 */
	public Date getDutyTime() {
		return dutyTime;
	}
	/**
	 * @param dutyTime the dutyTime to set
	 */
	public void setDutyTime(Date dutyTime) {
		this.dutyTime = dutyTime;
	}
	/**
	 * @return the dealCost
	 */
	public Double getDealCost() {
		return dealCost;
	}
	/**
	 * @param dealCost the dealCost to set
	 */
	public void setDealCost(Double dealCost) {
		this.dealCost = dealCost;
	}
	/**
	 * @return the dealResult
	 */
	public String getDealResult() {
		return dealResult;
	}
	/**
	 * @param dealResult the dealResult to set
	 */
	public void setDealResult(String dealResult) {
		this.dealResult = dealResult;
	}
	public String accept(){
		User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		FileInputStream fis=null;
		try {
			if(upFile!=null){
				fis=new FileInputStream(upFile);
				//REVIEW Ӧ�ý�������ֱ�ӷ��뵽�Ƚϵط�������ÿ���жϵļ��㣬����if��size��10584760��
				//FIXED liuh 
					//int size=fis.available()/1024/1024;
					//10485760=10*1024*1024 �ļ���СΪ10M
					if(fis.available()>10485760){
						getValidateInfo().setMsg("�ϴ��ļ���С���ܴ���10M��");
			    		addMessage("�ϴ��ļ���С���ܴ���10M��");
			    		Struts2Utils.render("text/html; charset=UTF-8",JSONObject.fromObject(getValidateInfo()).toString());
			    		return null;
					}
			}
			cusComplaintService.acceptComplaint(user.get("name").toString(), upFile, upFileFileName,comId);
			getValidateInfo().setMsg("���ݱ���ɹ���");
    		addMessage("���ݱ���ɹ���");
		} catch(FileNotFoundException e){
    		addError("�����ϴ����ļ���", e);
    		getValidateInfo().setMsg("�����ϴ����ļ���");
    		addMessage("�����ϴ����ļ���");
		}catch(Exception e){
			getValidateInfo().setSuccess(false);
        	getValidateInfo().setMsg("���ݱ���ʧ�ܣ�");
            addError("���ݱ���ʧ�ܣ�", e);
		}finally{
			try{
				if(fis!=null){
					fis.close();
				}
			}catch(Exception e){
				addError("���ݱ���ʧ�ܣ�", e);
			}
		}
		Struts2Utils.render("text/html; charset=UTF-8",JSONObject.fromObject(getValidateInfo()).toString());
		return null;
	}
	public void parpareDuty(){
		
	}
	public String duty(){
		try {
			CusComplaint cc=new CusComplaint();
			cc.setDealCost(dealCost);
			cc.setDealResult(dealResult);
			cc.setDutyName(dutyName);
			cc.setDutyTime(dutyTime);
			cusComplaintService.dutyComplaint(cc, comId);
		} catch (Exception e) {
			getValidateInfo().setSuccess(false);
        	getValidateInfo().setMsg("���ݱ���ʧ�ܣ�");
            addError("���ݱ���ʧ�ܣ�", e);
		}
		return RELOAD;
	}
	public String delete(){
		try {
			cusComplaintService.deleteComplaint(comId);
			getValidateInfo().setSuccess(true);
        	getValidateInfo().setMsg("����ɾ���ɹ���");
		} catch (Exception e) {
			getValidateInfo().setSuccess(false);
        	getValidateInfo().setMsg("����ɾ��ʧ�ܣ�");
        	addError("����ɾ��ʧ�ܣ�", e);
		}
		return RELOAD;
	}
}
