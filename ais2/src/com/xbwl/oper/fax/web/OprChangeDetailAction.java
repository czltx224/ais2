package com.xbwl.oper.fax.web;

import java.util.Map;
import javax.annotation.Resource;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.context.annotation.Scope;
import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.entity.OprChangeDetail;
import com.xbwl.oper.fax.service.IOprFaxChangeDetailService;
/**
 * @author Administrator
 * @createTime 10:52:24 AM
 * @updateName Administrator
 * @updateTime 10:52:24 AM
 * 
 */
@Action("oprChangeDetailAction")
@Scope("prototype")
@Namespace("/fax")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/fax/oprChange.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }
		) }
)
public class OprChangeDetailAction  extends SimpleActionSupport{
	private Long changeMainId;
	@Resource(name="oprFaxChangeDetailServiceImpl")
	private IOprFaxChangeDetailService oprFaxChangeDetailService;
	private OprChangeDetail oprChangeDetail;
	@Override
	protected Object createNewInstance() {
		return new OprChangeDetail();
	}
	@Override
	public Map getContextMap() {
		return null;
	}
	@Override
	protected IBaseService getManager() {
		return oprFaxChangeDetailService;
	}
	@Override
	public Object getModel() {
		return oprChangeDetail;
	}
	@Override
	public void setModel(Object obj) {
		oprChangeDetail=(OprChangeDetail)obj;
	}
	
	/**
	 * @return the changMainId
	 */
	public Long getChangeMainId() {
		return changeMainId;
	}
	/**
	 * @param changMainId the changMainId to set
	 */
	public void setChangeMainId(Long changeMainId) {
		this.changeMainId = changeMainId;
	}
	public String findDetail(){
		this.setPageConfig();
		try {
			oprFaxChangeDetailService.findDetailByMainId(this.getPages(), changeMainId);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("操作出错");
			super.getValidateInfo().setSuccess(false);
			super.getValidateInfo().setMsg("操作失败,失败原因:"+e.getLocalizedMessage());
			return "reload";
		}
		return LIST;
	}
	/**
	 * 保存更改申请
	 * @return
	 */
	/*
	public String updateRecord(){
		try {
			System.out.println(ofi.getDno());
			oprFaxChangeDetailService.updateChangeRecord(changeDetailList, ofi);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return RELOAD;	
	}
	*/
}
