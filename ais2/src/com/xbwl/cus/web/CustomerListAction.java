package com.xbwl.cus.web;

import java.util.Map;

import javax.annotation.Resource;

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
import com.xbwl.common.web.ServletUtils;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.cus.service.ICusRecordService;
import com.xbwl.cus.service.ICusSearchService;
import com.xbwl.entity.CusRecord;
import com.xbwl.entity.CusSearch;
import com.xbwl.entity.SysDepart;
import com.xbwl.rbac.Service.IDepartService;
import com.xbwl.rbac.Service.IUserService;
import com.xbwl.rbac.entity.SysUser;

/**
 * author CaoZhili time Oct 9, 2011 2:27:41 PM
 */
@Controller
@Action("customerListAction")
@Scope("prototype")
@Namespace("/cus")
@Results( {
		@Result(name="distributionCustomer" , location = "/WEB-INF/xbwl/cus/cus_distributionCustomer.jsp", type = "dispatcher"),
		@Result(name = "input", location = "/WEB-INF/xbwl/cus/cus_customerList.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "msg", type = "json", params = { "root", "validateInfo",
				"excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }
		) }
)
public class CustomerListAction extends SimpleActionSupport {

	@Resource(name = "cusRecordServiceImpl")
	private ICusRecordService cusRecordService;

	@Resource(name="userServiceImpl")
	private IUserService userService;
	
	@Resource(name="cusSearchServiceImpl")
	private ICusSearchService cusSearchService;
	
	@Resource(name="departServiceImpl")
	private IDepartService departService;
	
	private CusRecord cusRecord;
	
	private String searchStatement;
	private String searchChinese;
	
	@Value("${userRight.manager.level}")
	private Long   managerLevel;

	@Override
	protected Object createNewInstance() {
		return new CusRecord();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return this.cusRecordService;
	}

	@Override
	public Object getModel() {
		return this.cusRecord;
	}

	@Override
	public void setModel(Object obj) {
		this.cusRecord = (CusRecord) obj;
	}

	
	public String getSearchStatement() {
		return searchStatement;
	}

	public void setSearchStatement(String searchStatement) {
		this.searchStatement = searchStatement;
	}

	public String getSearchChinese() {
		return searchChinese;
	}

	public void setSearchChinese(String searchChinese) {
		this.searchChinese = searchChinese;
	}

	/**
	 * 客户列表查询
	 * @return
	 */
	public String findCustomerList(){
		try{
			String sql="";
			setPageConfig();
			User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
			Map<String, String> map = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
			
			String bussDepart = map.get("authorityDepartId");
			if(null==bussDepart || "".equals(bussDepart.trim())){
				bussDepart=user.get("bussDepart")+"";
			}
			if(null==map.get("status") || "".equals(map.get("status"))){
				map.put("status","1");
			}
			map.put("searchStatement", getSearchStatement());
			map.put("bussDepart", bussDepart);
			
			SysDepart depart = this.departService.get(Long.valueOf(bussDepart));
			
			map.put("departCode", depart.getDepartNo());//通过部门ID设置部门编码
			
			map.put("managerLevel", managerLevel+"");
			SysUser sysUser = this.userService.get(Long.valueOf(user.get("id").toString()));
			map.put("userId", sysUser.getId()+"");
			map.put("userCode", sysUser.getUserCode());
			map.put("userLevel", sysUser.getUserLevel()==null?"":sysUser.getUserLevel()+"");
			
			sql  = this.cusRecordService.findCustomerListService(map);
			this.cusRecordService.getPageBySqlMap(this.getPages(), sql, map);
				
		}catch (Exception e) {
			addError("客户列表查询失败！", e);
			return this.RELOAD;
		}
	
		return this.LIST;
	}
	
	/**
	 * 停用客户
	 * @return
	 */
	public String stopCustomer(){
		
		try{
			String[] strIds = ServletActionContext.getRequest().getParameter("ids").split(",");
			this.cusRecordService.stopCustomerService(strIds);
		}catch (Exception e) {
			addError("客户停用失败！", e);
		}
		return this.RELOAD;
	}
	
	/**
	 * 启用客户
	 * @return
	 */
	public String startCustomer(){
		
		try{
			String[] strIds = ServletActionContext.getRequest().getParameter("ids").split(",");
			this.cusRecordService.startCustomerService(strIds);
		}catch (Exception e) {
			addError("客户停用失败！", e);
		}
		
		return this.RELOAD;
	}
	
	/**
	 * 客户分配
	 * @return
	 */
	public String distributionCustomer(){
		
		return "distributionCustomer";
	}
	
	/**
	 * 分配客户查询方法
	 * @return
	 */
	public String findDistributionCustomer(){
		String sql="";
		try{
			setPageConfig();
			User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
			Map<String, String> map = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
			SysUser sysUser = this.userService.get(Long.valueOf(user.get("id").toString()));
			String departId = user.get("bussDepart").toString();
			SysDepart depart = this.departService.get(Long.valueOf(departId));
			map.put("status","1");
			map.put("departCode", depart.getDepartNo());//通过部门ID设置部门编码
			map.put("managerLevel", managerLevel+"");
			map.put("userLevel", sysUser.getUserLevel()==null?"":sysUser.getUserLevel()+"");
			map.put("bussDepart",user.get("bussDepart")+"");
			if(null!=map.get("userId") && !"".equals(map.get("userId"))){
				map.put("userId", map.get("userId")+"");
			}
		
			sql  = this.cusRecordService.findDistributionCustomerService(map);
			this.cusRecordService.getPageBySqlMap(this.getPages(), sql, map);
		}catch (Exception e) {
			addError("客户列表查询失败！", e);
		}

		return this.LIST;
	}
	
	/**分配客户到部门
	 * @return
	 */
	public String referToDepart(){
		
		try{
			String idString =ServletActionContext.getRequest().getParameter("ids");
			String departId = ServletActionContext.getRequest().getParameter("departId");
			this.cusRecordService.referToDepartService(idString.split(","),Long.valueOf(departId));
		}catch (Exception e) {
			addError("分配客户到部门失败！", e);
		}
		return this.RELOAD;
	}
	
	/**自定义查询保存方法
	 * @return
	 */
	public String customSearchSave(){
		
		try{
			String flagString =  ServletActionContext.getRequest().getParameter("flag");
			String tableCh = "客商档案表";
			String tableEn = "CUS_RECORD";
			User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
			this.searchStatement=ServletActionContext.getRequest().getParameter("searchStatement");
			String title=ServletActionContext.getRequest().getParameter("searchTitle");
			CusSearch cusSearch  =  new CusSearch();
			cusSearch.setSearchStatement(this.searchStatement);
			cusSearch.setSearchChinese(this.searchChinese);
			cusSearch.setTitle(title);
			cusSearch.setTableCh(tableCh);
			cusSearch.setTableEn(tableEn);
			cusSearch.setDepartCode(user.get("departId")+"");
			if(Boolean.valueOf(flagString)){
				this.cusSearchService.save(cusSearch);
			}else{
				return findCustomerList();
			}
		}catch (Exception e) {
			addError("自定义查询保存失败！", e);
		}
		return this.RELOAD;
	}
	
	/**自定义查询客户列表信息
	 * @return
	 */
	public String customSearch(){
		String sql="";
		try{
			setPageConfig();
			User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
			Map<String, String> map = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
			String departCode = user.get("departId")+"";
			
			map.put("status","1");
			map.put("userCode", user.get("userCode").toString());
			map.put("departCode", departCode+"");
			map.put("managerLevel", managerLevel+"");
			sql = this.cusRecordService.customSearchService(map);
			this.cusRecordService.getPageBySqlMap(this.getPages(), sql, map);
		}catch (Exception e) {
			addError("自定义查询失败！", e);
		}
		
		return LIST;
	}
}
