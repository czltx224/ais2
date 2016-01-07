package com.xbwl.sys.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.util.Element;
import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.orm.Page;
import com.xbwl.common.orm.PropertyFilter;
import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.SysCommonMenu;
import com.xbwl.sys.service.ISysCommonMenuService;

/**
 * author shuw
 * time Mar 1, 2012 3:31:04 PM
 */
@Controller
@Action("sysCommonMenuAction")
@Scope("prototype")
@Namespace("/sys")
@Results({
            @Result(name = "input", location = "/WEB-INF/xbwl/sys/sys_common_menu.jsp", type = "dispatcher"),
            @Result(name = "reload", type = "json", params = {"root","validateInfo","excludeNullProperties","true"}),
            @Result(name = "list", type = "json", params = {"root","pages","includeProperties","*"})
         })
public class SysCommonMenuAction extends SimpleActionSupport {

	private SysCommonMenu sysCommonMenu;
	
	@Resource(name="sysCommonMenuServiceImpl")
	private ISysCommonMenuService sysCommonMenuService;
	
	@Element(value =SysCommonMenu .class)
	private List<SysCommonMenu> sysCmMuList;
	
	private String sort;  //排序字段 up向上; down向下。

	/**
	 *  排序保存
	 * @return
	 */
	public String changeSort(){
		 try {
			 	sysCommonMenu=sysCommonMenuService.get(getId());
				if(sort.equals("up")){
					sysCommonMenu.setSortNum(sysCommonMenu.getSortNum()-1);
				}else if(sort.equals("down")){
					sysCommonMenu.setSortNum(sysCommonMenu.getSortNum()+1);
				}else{
					new ServiceException("排序失败");
				}
				sysCommonMenuService.save(sysCommonMenu);
	     		getValidateInfo().setMsg("数据保存成功！");
	     		addMessage("数据保存成功！");
	     } catch (Exception e) {
	     	getValidateInfo().setSuccess(false);
	     	getValidateInfo().setMsg(e.getMessage());
	         addError("数据保存失败！", e);
	     }
		return RELOAD;
	}
	
	public String save() throws Exception {
		   try {
			   		sysCommonMenuService.saveList(sysCmMuList);
	        		getValidateInfo().setMsg("数据保存成功！");
	        		addMessage("数据保存成功！");
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
	        	List<PropertyFilter> pList=getFilters();
	       // 	Page  pages = getManager().findPage(getPages(), pList);
	        	Page  pages = getPages();
	        	pages.setOrder(Page.ASC);
	        	pages.setOrderBy("sortNum");
	        
	        	pages =sysCommonMenuService.queryList(pages, pList);
	            if(isXml()){
	            	Struts2Utils.renderXml(pages);
	            	return null;
	            }
	        } catch (Exception e) {
	            addError("数据查询失败！", e);
	        }
	        return LIST;
	}

	protected Object createNewInstance() {
		return new SysCommonMenu();
	}

	public Map getContextMap() {
		return null;
	}

	protected IBaseService getManager() {
		return sysCommonMenuService;
	}

	public Object getModel() {
		return sysCommonMenu;
	}

	public void setModel(Object obj) {
		sysCommonMenu=(SysCommonMenu)obj;
	}

	public List<SysCommonMenu> getSysCmMuList() {
		return sysCmMuList;
	}

	public void setSysCmMuList(List<SysCommonMenu> sysCmMuList) {
		this.sysCmMuList = sysCmMuList;
	}

	
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
}
