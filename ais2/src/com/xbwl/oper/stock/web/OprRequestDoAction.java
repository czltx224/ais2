package com.xbwl.oper.stock.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

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
import com.xbwl.entity.OprRequestDo;
import com.xbwl.oper.stock.service.IOprRequestDoService;

/**
 * author CaoZhili time Jul 19, 2011 6:16:28 PM
 * 
 * 个性化要求控制层操作类
 */

@Controller
@Action("oprRequestDoAction")
@Scope("prototype")
@Namespace("/stock")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/stock/opr_requestDo.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }

		) }

)
public class OprRequestDoAction extends SimpleActionSupport {

	@Resource(name = "oprRequestDoServiceImpl")
	private IOprRequestDoService oprRequestDoService;

	private OprRequestDo oprRequestDo;

	@Override
	protected Object createNewInstance() {

		return new OprRequestDo();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {

		return this.oprRequestDoService;
	}

	@Override
	public Object getModel() {

		return this.oprRequestDo;
	}

	@Override
	public void setModel(Object obj) {

		this.oprRequestDo = (OprRequestDo) obj;
	}

	@Override
	public String save() throws Exception {
		Boolean flag=true;
		List<OprRequestDo> list = this.oprRequestDoService.findBy("dno", this.oprRequestDo.getDno());
	 
//		for (int i = 0; i <list.size(); i++) {
//			if(null==this.oprRequestDo.getId() || this.oprRequestDo.getId()==0){
//				if(list.get(i).getRequestStage().equals(this.oprRequestDo.getRequestStage())){
//						flag=false;
//				}
//			}else{
//				if(!list.get(i).getId().equals(this.getId()) && list.get(i).getRequestStage().equals(this.oprRequestDo.getRequestStage())){
//					flag=false;
//				}
//			}
//		}
//		if(flag){
//			return super.save();
//		}else{
//			super.getValidateInfo().setSuccess(false);
//			super.getValidateInfo().setMsg("该阶段个性化要求已经存在，请勿重复添加！");
//			return this.RELOAD;
//		}
		 //只允许添加出库，送货阶段的个性化要求
		if(null==this.oprRequestDo.getId()){
			if(this.oprRequestDo.getRequestStage().equals("出库") || this.oprRequestDo.getRequestStage().equals("送货")){
				try{
					this.oprRequestDoService.save(this.oprRequestDo);
					getValidateInfo().setMsg("数据保存成功！");
					addMessage("数据保存成功！");
				}catch (Exception e) {
					addError("数据保存失败！", e);
				}
			}else{
				super.getValidateInfo().setSuccess(false);
				super.getValidateInfo().setMsg("现在不允许添加出库和送货外的个性化要求！");
			}
		}else{
			if(this.oprRequestDo.getRequestStage().equals("出库") || this.oprRequestDo.getRequestStage().equals("送货")){
				try{
					this.oprRequestDoService.save(this.oprRequestDo);
					getValidateInfo().setMsg("数据保存成功！");
					addMessage("数据保存成功！");
				}catch (Exception e) {
					addError("数据保存失败！", e);
				}
				}else{
					super.getValidateInfo().setSuccess(false);
					super.getValidateInfo().setMsg("现在不允许修改出库和送货外的个性化要求！");
				}
		}
		return this.RELOAD;
	}
	
	public String findRequestDoList(){
		String sql=null;
		//设置分页参数
        setPageConfig();
		Map<String, String> filterParamMap = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
		if(null!=getCheckItems() && null!= getItemsValue()){
			filterParamMap.put("checkItems", getCheckItems().substring(getCheckItems().indexOf("_")+1));
			filterParamMap.put("itemsValue", "%"+getItemsValue()+"%");
		}
		try{
			sql=this.oprRequestDoService.getSqlRalaListService(filterParamMap);
			this.oprRequestDoService.getPageBySqlMap(this.getPages(), sql, filterParamMap);
			
		}catch(Exception e){
			addError("查询失败！", e);
			
		}
		
		return this.LIST;
	}
	
}
