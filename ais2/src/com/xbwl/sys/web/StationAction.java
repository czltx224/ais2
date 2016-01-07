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

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.orm.Page;
import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.entity.SysDepart;
import com.xbwl.entity.SysStation;
import com.xbwl.rbac.Service.IDepartService;
import com.xbwl.sys.service.IStationService;

/**
 *author LiuHao
 *time Jun 21, 2011 10:09:50 AM
 */
@Controller
@Action("stationAction")
@Scope("prototype")
@Namespace("/sys")
@Results({
			@Result(name = "stationList", location = "/WEB-INF/xbwl/sys/station_list.jsp", type = "dispatcher"),
            @Result(name = "input", location = "/WEB-INF/xbwl/sys/sysStation.jsp", type = "dispatcher"),
            @Result(name = "tree", type = "json", params = {"root","stationList","excludeNullProperties","true"}),
            @Result(name = "reload", type = "json", params = {"root","validateInfo","excludeNullProperties","true"}),
            @Result(name = "list", type = "json", params = {"root","pages","includeProperties","*"})
         })
public class StationAction extends SimpleActionSupport {
	private SysStation sysStation;
	@Resource(name="stationServiceImpl")
	private IStationService stationService;
	@Resource(name="departServiceImpl")
	private IDepartService departService;
	private Long node;
	private Long parentId;
	private List<SysStation> stationList;

	@Override
	protected Object createNewInstance() {
		return new SysStation();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return stationService;
	}

	@Override
	public Object getModel() {
		return sysStation;
	}

	@Override
	public void setModel(Object obj) {
		sysStation=(SysStation)obj;
	}
	
	/**
	 * @return the node
	 */
	public Long getNode() {
		return node;
	}

	/**
	 * @param node the node to set
	 */
	public void setNode(Long node) {
		this.node = node;
	}
	
	/**
	 * @return the stationList
	 */
	public List<SysStation> getStationList() {
		return stationList;
	}

	/**
	 * @param stationList the stationList to set
	 */
	public void setStationList(List<SysStation> stationList) {
		this.stationList = stationList;
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
	 * 获得岗位数的子节点
	 * @return
	 */
	public String getStationTree(){
		try {
			stationList=stationService.findStationByParentId(node);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "tree";
	}
	public String findStationList(){
		return "stationList";
	}
	
	public void prepareDelete(){
			try {
				this.setPageConfig();
				boolean flag=true;
				List<Long> is=super.getPksByIds();
				for(Long id:is){
					Page<SysDepart> page = departService.findDepartBySationId(this.getPages(), id);
					if(page.getResultMap().size()>0){
						flag=false;
					}
				}
				if(!flag){
					throw new ServiceException("该岗位在部门中已引用，不能删除");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
}
