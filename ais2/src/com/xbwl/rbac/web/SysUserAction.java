package com.xbwl.rbac.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.ralasafe.WebRalasafe;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.service.IBaseService;
import com.xbwl.common.utils.MD5Utils;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.rbac.Service.IUserService;
import com.xbwl.rbac.entity.SysUser;

@Controller
@Action("userAction")
@Scope("prototype")
@Namespace("/user")
@Results( {
            @Result(name = "input", location = "/WEB-INF/xbwl/rbac/sys_user.jsp", type = "dispatcher"),
            @Result(name = "view", location = "/drp/product/ProductInfoView.jsp", type = "dispatcher"),
            @Result(name = "CPList", location = "/drp/product/CPProductInfoList.jsp", type = "dispatcher"),
            @Result(name = "authority", location = "/WEB-INF/xbwl/rbac/super_authority.jsp", type = "dispatcher"),
            @Result(name = "CPView", location = "/drp/product/CPProductInfoView.jsp", type = "dispatcher"),
            @Result(name = "reload", type = "json", params = {"root","validateInfo","excludeNullProperties","true"}),
            @Result(name = "list", type = "json", params = {"root","pages","excludeNullProperties","true"})
         })
public class SysUserAction extends SimpleActionSupport {
	
	@Resource
	private IUserService userService;

	private String password1;  //用户输入的旧密码
	private String password2;  //新密码
	private String password3;  //确认新密码
	
	private String tts;  //时间戳
	private SysUser sysuser;
	private Long userLevel;

	@Value("${sysUser.password}")
	private String userStartPassword ;
	
	
	
	/**
	 * 切换用户
	 * @return
	 */
	public String changeUser(){
		try {
            userService.changeUser(getId());
            addMessage("数据查询成功！");
	    } catch (Exception e) {
	    	getValidateInfo().setMsg(e.getMessage());
	    	addMessage("数据查询成功！");
	    	addError("数据查询失败！", e);
	    }
	    return null;
	}
	
	/**
	 * 用户多账号查询
	 * @return
	 */
	public String 	getUserlist(){
		    try {
	            List<SysUser>list  = userService.getUserlist(getId());
	            for(SysUser sysUser:list){
	            	sysUser.setPassword(null);
	            }
	            Struts2Utils.renderJson(list);
	            addMessage("数据查询成功！");
		    } catch (Exception e) {
		    	getValidateInfo().setMsg(e.getMessage());
		    	addMessage("数据查询成功！");
		    	addError("数据查询失败！", e);
		    }
		return null;
	}
	
	/**
	 * 修改用户密码
	 * @return
	 */
	public String savePassword(){
		    try {
	            String  msg = userService.savePassword(getId(),password1,password2,password3,tts);
	            getValidateInfo().setMsg(msg);
	            addMessage("数据修改成功！");
		    } catch (Exception e) {
		    	getValidateInfo().setMsg(e.getMessage());
		    	addMessage("数据删除成功！");
		    	addError("数据删除失败！", e);
		    }
		return RELOAD;
	}

	/**
	 * 用户表软删方法
	 */
	public String deleteStatusById(){
		if(WebRalasafe.permit(Struts2Utils.getRequest(), getPrivilege(), null, getContextMap())){
	    try {
            List<Long> pks = getPksByIds();
            userService.deleteStatusById(pks);
            getValidateInfo().setMsg("数据删除成功！");
            addMessage("数据删除成功！");
	    } catch (Exception e) {
	    	addMessage("数据删除成功！");
	    	addError("数据删除失败！", e);
	    }
		}else{
			getValidateInfo().setSuccess(false);
			getValidateInfo().setMsg(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
			addMessage(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
		}
		return RELOAD;
	}

    /**
     * 保存用户等级设置
     * @return
     * @throws Exception
     */
    public String startPassword2() throws Exception {
        try {
        		List<Long>list =getPksByIds();
        		for(Long uId:list){
        			sysuser=userService.get(uId);		
        			sysuser.setPassword(MD5Utils.strToMd5(userStartPassword));
        			userService.save(sysuser);
        		}
        		getValidateInfo().setMsg("数据保存成功！");
        		addMessage("数据保存成功！");
        } catch (Exception e) {
        	getValidateInfo().setSuccess(false);
        	getValidateInfo().setMsg("数据保存失败！");
            addError("数据保存失败！", e);
        }

        return RELOAD;
    }
	
    /**
     * 保存用户等级设置
     * @return
     * @throws Exception
     */
    public String saveLevel() throws Exception {
        try {
        	if(WebRalasafe.permit(Struts2Utils.getRequest(), getPrivilege(), getModel(), getContextMap())){
        		if(getId()==null||userService.get(getId())==null){
        			throw new ServiceException("没有找到用户");
        		}
        		sysuser=userService.get(getId());
        		sysuser.setUserLevel(userLevel);
        		userService.save(sysuser);
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
	
	/**
	 * 用户等级设置
	 * @return
	 */
	public String authority(){
		return "authority";
	}
	
	
	@Override
	protected Object createNewInstance() {
		return new SysUser();
	}

	@Override
	protected IBaseService getManager() {
		return userService;
	}

	@Override
	public Object getModel() {
		return sysuser;
	}

	@Override
	public void setModel(Object obj) {
		sysuser=(SysUser)obj;
	}


	/**get and set method**/

	public SysUser getSysuser() {
		return sysuser;
	}
	
	public void setSysuser(SysUser sysuser) {
		this.sysuser = sysuser;
	}

	@Override
	public Map getContextMap() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String getPassword1() {
		return password1;
	}


	public void setPassword1(String password1) {
		this.password1 = password1;
	}


	public String getPassword2() {
		return password2;
	}


	public void setPassword2(String password2) {
		this.password2 = password2;
	}


	public String getPassword3() {
		return password3;
	}


	public void setPassword3(String password3) {
		this.password3 = password3;
	}

	public String getTts() {
		return tts;
	}

	public void setTts(String tts) {
		this.tts = tts;
	}
	public Long getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(Long userLevel) {
		this.userLevel = userLevel;
	}

	public String getUserStartPassword() {
		return userStartPassword;
	}

	public void setUserStartPassword(String userStartPassword) {
		this.userStartPassword = userStartPassword;
	}
}
