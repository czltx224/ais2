package com.xbwl.oper.exception.web;

import java.io.File;
import java.util.ArrayList;
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

import com.xbwl.common.orm.Page;
import com.xbwl.common.orm.PropertyFilter;
import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.OprException;
import com.xbwl.oper.exception.service.IOprExceptionService;

/**
 * author shuw
 * time Aug 22, 2011 4:35:40 PM  
 */
@Controller
@Action("oprExceptionAction")
@Scope("prototype")
@Namespace("/exception")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/exception/opr_exception_mange.jsp", type = "dispatcher"), 
		@Result(name = "showForm", location = "/WEB-INF/xbwl/exception/opr_exception_form.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root","validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages","excludeNullProperties", "true" }
		) }
)
public class OprExceptionAction extends SimpleActionSupport{

	private OprException oprException;
	
	@Resource(name="oprExceptionServiceImpl")
	private IOprExceptionService oprExceptionService;
	
	@Value("${exception_quality_management_station_id}")
	private String qualityManger;
	
	@Value("${exception_images_url}")
	private String exceptionImagesUrl;

	private Long dno;
	
	private String exceptionAddsFileName;
	private String exptionAddsFileName;
	private File exceptionAdds;
	private File exptionAdds;

	public String getAll(){
		try {
			 List <Long>stationIds = new ArrayList<Long>();
		     String[] idValue = qualityManger.split("\\,");
		     for (String delId : idValue) {
		       	stationIds.add(Long.valueOf(delId));
		     }
		    setPageConfig();
		    List<PropertyFilter> filters = getRequestFilter();
		    User user= WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
			Long stationId = Long.parseLong(user.get("stationId")+"");
			Long bussDepartId = Long.parseLong(user.get("bussDepart")+"");
			Long departId = Long.parseLong(user.get("departId")+"");
			Page page =oprExceptionService.getAllByStationId(getPages(), stationId, stationIds, bussDepartId, departId,filters);
			setPages(page);
			super.getValidateInfo().setSuccess(true);
			super.getValidateInfo().setMsg("查询操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("操作出错");
			super.getValidateInfo().setSuccess(false);
			super.getValidateInfo().setMsg("操作失败,失败原因:"+e.getLocalizedMessage());
			return "reload";
		}
		return LIST;
	}
	
	public String showForm(){
		return "showForm";
	}
	
	@Override
	public String save() throws Exception {
		  try {
	        	if(WebRalasafe.permit(Struts2Utils.getRequest(), getPrivilege(), getModel(), getContextMap())){
	        		
	        		oprExceptionService.saveExceptionOfNew(oprException, exceptionAdds,exceptionAddsFileName,exptionAdds,exptionAddsFileName);
	        		
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
	        
			Struts2Utils.render("text/html; charset=UTF-8",JSONObject.fromObject(getValidateInfo()).toString());
	        
		return null;
	}

	/**
	 * 保存异常信息，多个方法调用
	 * @return
	 */
	public String saveDoException(){
		  try {
			  	prepareModel();
	        	if(WebRalasafe.permit(Struts2Utils.getRequest(), getPrivilege(), getModel(), getContextMap())){
	        		OprException oprException=(OprException)getModel();
	        		
	        		oprExceptionService.saveExceptionDo(oprException);
	        		
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
	
	
	@Override
	public String delete() throws Exception {
		  try {
	            List pks = getPksByIds();
	            oprExceptionService.deleteByIdsOfStatus(pks);
	            addMessage("数据删除成功！");
	        } catch (Exception e) {
	            addError("数据删除失败！", e);
	        }
	        return RELOAD;
	}

	public Long getDno() {
		return dno;
	}

	public void setDno(Long dno) {
		this.dno = dno;
	}
	
	
	@Override
	protected Object createNewInstance() {
		return new OprException();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return oprExceptionService;
	}

	@Override
	public Object getModel() {
		return  oprException;
	}

	@Override
	public void setModel(Object obj) {
		oprException=(OprException)obj;
	}


	public String getExceptionAddsFileName() {
		return exceptionAddsFileName;
	}

	public void setExceptionAddsFileName(String exceptionAddsFileName) {
		this.exceptionAddsFileName = exceptionAddsFileName;
	}

	public String getExptionAddsFileName() {
		return exptionAddsFileName;
	}

	public void setExptionAddsFileName(String exptionAddsFileName) {
		this.exptionAddsFileName = exptionAddsFileName;
	}

	public File getExceptionAdds() {
		return exceptionAdds;
	}

	public void setExceptionAdds(File exceptionAdds) {
		this.exceptionAdds = exceptionAdds;
	}

	public File getExptionAdds() {
		return exptionAdds;
	}

	public void setExptionAdds(File exptionAdds) {
		this.exptionAdds = exptionAdds;
	}

	public String getQualityManger() {
		return qualityManger;
	}


	public void setQualityManger(String qualityManger) {
		this.qualityManger = qualityManger;
	}

	public OprException getOprException() {
		return oprException;
	}

	public void setOprException(OprException oprException) {
		this.oprException = oprException;
	}

	public IOprExceptionService getOprExceptionService() {
		return oprExceptionService;
	}

	public void setOprExceptionService(IOprExceptionService oprExceptionService) {
		this.oprExceptionService = oprExceptionService;
	}

	public String getExceptionImagesUrl() {
		return exceptionImagesUrl;
	}

	public void setExceptionImagesUrl(String exceptionImagesUrl) {
		this.exceptionImagesUrl = exceptionImagesUrl;
	}

}
