package com.xbwl.oper.fax.web;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.context.annotation.Scope;
import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.entity.OprFaxIn;
import com.xbwl.oper.fax.service.IOprFaxInService;

/**
 * @author Administrator
 * @createTime 10:52:24 AM
 * @updateName Administrator
 * @updateTime 10:52:24 AM
 * 
 */
@Action("oprFaxCancelAction")
@Scope("prototype")
@Namespace("/fax")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/fax/opr_faxcancel.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }

		) }

)
public class OprFaxCancelAction  extends SimpleActionSupport{
	@Resource(name="oprFaxInServiceImpl")
	private IOprFaxInService oprFaxInService;
	private OprFaxIn oprFaxIn;
	
	@Override
	protected Object createNewInstance() {
		return new OprFaxIn();
	}
	@Override
	public Map getContextMap() {
		return null;
	}
	@Override
	protected IBaseService getManager() {
		return oprFaxInService;
	}
	@Override
	public Object getModel() {
		return oprFaxIn;
	}
	@Override
	public void setModel(Object obj) {
		oprFaxIn=(OprFaxIn)obj;
	}
	public String delete(){
		List pks = getPksByIds();
		try {
			oprFaxInService.faxCancel(pks);
			super.getValidateInfo().setSuccess(true);
			super.getValidateInfo().setMsg("作废成功！");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("操作出错");
			super.getValidateInfo().setSuccess(false);
			super.getValidateInfo().setMsg("操作失败,失败原因:"+e.getLocalizedMessage());
			return RELOAD;
		}
		return RELOAD;
	}
	
}
