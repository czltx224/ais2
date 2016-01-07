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

import com.xbwl.common.orm.Page;
import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.BasArea;
import com.xbwl.entity.SysDepart;
import com.xbwl.sys.service.IBasAreaService;

/**
 *author LiuHao
 *time Jun 22, 2011 12:48:48 PM
 */
@Controller
@Action("basAreaAction")
@Scope("prototype")
@Namespace("/sys")
@Results({
			@Result(name = "areaList",location = "/WEB-INF/xbwl/sys/area_list.jsp", type = "dispatcher"),
            @Result(name = "input", location = "/WEB-INF/xbwl/sys/basArea.jsp", type = "dispatcher"),
            @Result(name = "reload", type = "json", params = {"root","validateInfo","excludeNullProperties","true"}),
            @Result(name = "tree", type = "json", params = {"root","areaList","excludeNullProperties","true"}),
            @Result(name = "list", type = "json", params = {"root","pages","includeProperties","*"})
         })
public class BasAreaAction extends SimpleActionSupport {
	@Resource(name="basAreaServiceImpl")
	private IBasAreaService basAreaService;
	private Long node;
	private BasArea baseArea;
	private List<BasArea> areaList;
	private Long parentId;
	private String city;
	private String town;
	private String street;
	@Override
	protected Object createNewInstance() {
		return new BasArea();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return basAreaService;
	}

	@Override
	public Object getModel() {
		return baseArea;
	}

	@Override
	public void setModel(Object obj) {
		baseArea=(BasArea)obj;
	}
	public Long getNode() {
		return node;
	}

	public void setNode(Long node) {
		this.node = node;
	}
	
	public List getAreaList() {
		return areaList;
	}

	public void setAreaList(List areaList) {
		this.areaList = areaList;
	}
	/**
	 * @return the parentId
	 */
	public Long getParentId() {
		return parentId;
	}

	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the town
	 */
	public String getTown() {
		return town;
	}

	/**
	 * @param town the town to set
	 */
	public void setTown(String town) {
		this.town = town;
	}

	/**
	 * @return the street
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * @param street the street to set
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * 获得数的子节点
	 * @return
	 */
	public String getAreaTree(){
		try {
			areaList=basAreaService.getBasAreaTreeByPrentId(node);
		} catch (Exception e) {
			//e.printStackTrace();
			logger.error("查询出错");
			super.getValidateInfo().setSuccess(false);
			super.getValidateInfo().setMsg("查询失败,失败原因:"+e.getLocalizedMessage());
			return "reload";
		}
		return "tree";
	}
	public String findAreaList(){
		return "areaList";
	}
	/**
	 * 获得选择的地区的子地区
	 * @return
	 */
	public String getAreaByParentId(){
		try {
			setPageConfig();
			basAreaService.findArea(this.getPages(), parentId);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询出错");
			super.getValidateInfo().setSuccess(false);
			super.getValidateInfo().setMsg(
					"查询失败,失败原因:" + e.getLocalizedMessage());
			return "reload";
		}
		return "list";
	}
	public String getAreaMsg(){
		this.setPageConfig();
		try {
			basAreaService.getAreaMsg(this.getPages(), city, town, street);
		} catch (Exception e) {
			addError("查询出错！", e);
		}
		return LIST;
	}
	
	public String delete(){
		this.setPageConfig();
		Long idLong = this.getId();
		try {
			Page<BasArea> dPage = basAreaService.findArea(this.getPages(), idLong);
			if(dPage.getResult().size()>0){
				this.getValidateInfo().setSuccess(false);
				this.getValidateInfo().setMsg("不能删除含有下级地区的地区名!");
			}else{
				basAreaService.delete(idLong);
				this.getValidateInfo().setSuccess(true);
				this.getValidateInfo().setMsg("删除成功!");
			}
		} catch (Exception e) {
			addError("删除出错", e);
		}
		return RELOAD;
	}
}
