package com.xbwl.rbac.web;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.dozer.DozerBeanMapper;
import org.ralasafe.WebRalasafe;
import org.ralasafe.privilege.Privilege;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.gdcn.bpaf.script.json.DateJsonValueProcessor;
import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.ServletUtils;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.rbac.Service.IUserService;
import com.xbwl.rbac.entity.SysUser;
import com.xbwl.rbac.vo.CommonMenuTree;
import com.xbwl.rbac.vo.MenuTree;

@Controller
@Action("loginAction")
@Scope("prototype")
@Namespace("/login")
@Results( {
            @Result(name = "success", location = "/aisIndex.jsp", type = "dispatcher"),
            @Result(name = "input", location = "/index.jsp", type = "dispatcher"),
            @Result(name = "loginout", location = "/login.jsp", type = "dispatcher"),
            @Result(name = "reload", location = "productInfo!list.action", type = "redirect"),
            @Result(name = "menu", type = "json", params = {"root","businessPrivilege","excludeProperties","parent"})
         })
public class LoginAction extends SimpleActionSupport {

	@Resource
	private IUserService userService;
	
	@Resource(name="dozer")
	private  DozerBeanMapper dozer;
	
	private SysUser user;
	
	private String userName;
	private String passWord;
	
	private Privilege businessPrivilege;

	protected Object createNewInstance() {
		return new SysUser();
	}
	
	protected IBaseService getManager() {
		return userService;
	}

	@SuppressWarnings({ "unused", "static-access" })
	public String login(){
		return this.SUCCESS;
	}
	
	public String test(){
		return this.INPUT;
	}
	
	public String getMenu(){
		try {
			 businessPrivilege= WebRalasafe.getBusinessPrivilegeTree(Struts2Utils.getRequest());
			 MenuTree menuTree= dozer.map(businessPrivilege,MenuTree.class);
			
			 JsonConfig config = new JsonConfig(); 
			 config.setIgnoreDefaultExcludes(false); 
			 config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT); 
			 config.registerJsonValueProcessor(Date.class,new DateJsonValueProcessor("yyyy-MM-dd")); //date processor register 
			 config.setExcludes(new String[]{//只要设置这个数组，指定过滤哪些字段。 
			 "parent"
			 }); 

			 String jSonString=JSONSerializer.toJSON(menuTree.getChildren(),config).toString();
			 Struts2Utils.renderJson(jSonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return null;
	}
	
	/**
	 * 用户设置常用菜单查询菜单树
	 * @return
	 */
	public String getCommonMenu(){
		try {
			 businessPrivilege= WebRalasafe.getBusinessPrivilegeTree(Struts2Utils.getRequest());
			 CommonMenuTree commonMenuTree= dozer.map(businessPrivilege,CommonMenuTree.class);
			
			 JsonConfig config = new JsonConfig(); 
			 config.setIgnoreDefaultExcludes(false); 
			 config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT); 
			 config.registerJsonValueProcessor(Date.class,new DateJsonValueProcessor("yyyy-MM-dd")); //date processor register 
			 config.setExcludes(new String[]{//只要设置这个数组，指定过滤哪些字段。 
			 "parent"
			 }); 

			 String jSonString=JSONSerializer.toJSON(commonMenuTree.getChildren(),config).toString();
			 Struts2Utils.renderJson(jSonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return null;
	}
	
	public String loginOut(){
		
		WebRalasafe.setCurrentUser(Struts2Utils.getRequest(), null);
		
		Struts2Utils.getSession().invalidate();

		return "loginout";
		
	}

	@Override
	public SysUser getModel() {
		return user;
	}

	@Override
	public void setModel(Object obj) {
		setUser((SysUser)obj);		
	}

	
	/**get set **/

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}


	public SysUser getUser() {
		return user;
	}

	public void setUser(SysUser user) {
		this.user = user;
	}

	public Privilege getBusinessPrivilege() {
		return businessPrivilege;
	}

	public void setBusinessPrivilege(Privilege businessPrivilege) {
		this.businessPrivilege = businessPrivilege;
	}

	@Override
	public Map getContextMap() {
		Map<String, String> filterParamMap = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "privilege");
		return filterParamMap;
	}


}
