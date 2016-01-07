package com.xbwl.finance.web;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.ServletUtils;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.BasSpecialTrainLine;
import com.xbwl.finance.Service.ISpecialTrainLineService;

/**
 * @author CaoZhili time Aug 3, 2011 9:32:40 AM
 * 
 * 专车线路控制层操作类
 */

@Controller
@Action("basSpecialTrainLineAction")
@Scope("prototype")
@Namespace("/fi")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/fi/bas_specialTrainLine.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "tree", type = "json", params = { "root", "areaList",
				"excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"includeProperties", "*" }) })
public class BasSpecialTrainLineAction extends SimpleActionSupport {

	private BasSpecialTrainLine basSpecialTrainLine;
	
	@Resource(name="specialTrainLineServiceImpl")
	private ISpecialTrainLineService specialTrainLineService;
	
	@Override
	protected Object createNewInstance() {

		return new BasSpecialTrainLine();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {

		return this.specialTrainLineService;
	}

	@Override
	public Object getModel() {

		return this.basSpecialTrainLine;
	}

	@Override
	public void setModel(Object obj) {

		this.basSpecialTrainLine=(BasSpecialTrainLine)obj;
	}

	@Override
	public String delete() throws Exception {
		String strids=ServletActionContext.getRequest().getParameter("ids");
		try{
			if(this.specialTrainLineService.getIsNotDeleteService(strids.split(","))){
				return super.delete();
			}else{
				super.getValidateInfo().setSuccess(false);
				super.getValidateInfo().setMsg("该数据存在外键引用，删除失败！");
			}
		}catch(Exception e){
			e.printStackTrace();
			super.getValidateInfo().setSuccess(false);
			super.getValidateInfo().setMsg("删除失败！"+e.getLocalizedMessage());
		}
		
		return this.RELOAD;
	}
	
	/**
	 * 查询专车线路
	 * @return
	 */
	public String findList(){
		String sql=null;
		//设置分页参数
        setPageConfig();
		Map<String, String> map = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
		try{
			sql=this.specialTrainLineService.findListService(map);
			this.specialTrainLineService.getPageBySqlMap(this.getPages(), sql, map);
			
		}catch(Exception e){
			addError("专车线路查询失败！", e);
		}
		return this.LIST;
	}
	
}
