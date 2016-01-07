package com.xbwl.rbac.Service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.orm.Page;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.common.utils.MD5Utils;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.rbac.Service.IDepartService;
import com.xbwl.rbac.Service.IUserService;
import com.xbwl.rbac.dao.IUserDao;
import com.xbwl.rbac.entity.SysUser;
import com.xbwl.sys.service.IStationService;

@Service("userServiceImpl")
@Transactional(rollbackFor={Exception.class})
public class UserServiceImpl extends BaseServiceImpl<SysUser,Long> implements IUserService {

	@Value("${sys.role}")
	private int sysRoleRight;

	@Value("${sysUser.password}")
	private String userStartPassword ;
	
	@Resource(name="departServiceImpl")
	private IDepartService departService;  //部门
	
	@Resource(name="stationServiceImpl")
	private IStationService stationService;  //岗位
	
	@Override
	public void save(SysUser entity) {
		if(entity.getId()==null){
			List<SysUser>listName =find("from SysUser s where s.userName=? and s.status=? ", entity.getUserName(),"1");
			if(listName.size()!=0){
				throw new ServiceException("此用户姓名已经存在，不能重复");
			}
			try {
				entity.setPassword(MD5Utils.strToMd5(userStartPassword));
				entity.setUserLevel(0l);
			} catch (Exception e) {
				logger.debug("MD5转换失败！");
				e.printStackTrace();
			}
		}
		super.save(entity);
	}

	@Resource
	private  IUserDao userDao;
	
	public IBaseDAO<SysUser,Long> getBaseDao() {
		return userDao;
	}

	public void deleteStatusById(List<Long> ids) {
		 for(Long id: ids){
	           SysUser user2= get(id);
	           user2.setStatus(0+"");
	        	save(user2);
		 }
	}
	
	public Page getUserRoleByUserId(String userId){
		
		Map map=new HashMap();
		
		Page page=new Page();
		page.setLimit(50);
		
		getPageBySql(page,"select u.ROLEID from RALASAFE.RALASAFE_RALASAFE_USERROLE u where  U.USERID=? ",userId);
		
		return page;
	}

  //用户修改密码
	public String savePassword(Long userId, String password1, String password2,
			String password3, String ts) throws Exception {
		SysUser user =get(userId);
		user.setTs(ts);  // 验证时间戳
		if(userId==null||password1==null||password2==null||password3==null||ts==null){
			throw new ServiceException("提交的信息不完整！");
		}
		if(!MD5Utils.strToMd5(password1).equals(user.getPassword())&&!password1.equals(user.getPassword())){
			throw new ServiceException("输入的当前密码与系统密码不一致！");
		}
		if(!password2.equals(password3)){
			throw new ServiceException("输入的新密码与确认新密码不一致！");
		}
		Pattern prt=Pattern.compile("[0-9]+");
		Pattern prt2=Pattern.compile("[a-zA-Z]+");
		if(!(prt.matcher(password3).find()&&prt2.matcher(password3).find())){  //加密和没有加密的密码验证
			throw new ServiceException("输入的新密码格式不符合要求，必输包含字母和数字");
		}
		//将密码转换为MD5
		try{
			user.setPassword(MD5Utils.strToMd5(password3));
		}catch (Exception e) {
			logger.debug("MD5转化失败！");
			e.printStackTrace();
		}
		save(user);
		return "密码修改成功";
	}

	//查询多账号 工号关联 
	public List<SysUser> getUserlist(Long userId) throws Exception {
		SysUser user = get(userId);
		List<SysUser> list =find("from SysUser s where s.userCode=? and s.status=1 and s.workstatus=1 ",user.getUserCode());  //查询出同一工号 没有离职，没有删除的账号
		return list;
	}

	// 切换用户
	public String  changeUser(Long userId) throws Exception {
		if(userId==null ){
			throw new ServiceException("提交数据不完全");
		}
		SysUser sysUser = get(userId);
		if(sysUser==null){
			throw new ServiceException("用户已不存在");
		}
		
		User user=new User();
		user.set("name", sysUser.getUserName());
		user.set("id", sysUser.getId());
		user.set("departId", sysUser.getDepartId());
		
		user.set("departName", departService.get(sysUser.getBussDepart()).getDepartName());
		user.set("stationName", stationService.get(sysUser.getStationId()).getStationName());
		user.set("loginName", sysUser.getLoginName());
		user.set("password", sysUser.getPassword());
		user.set("userLevel",sysUser.getUserLevel());
		
		user.set("hrstatus", sysUser.getHrstatus());
		user.set("workstatus", sysUser.getWorkstatus());
		user.set("stationId", sysUser.getStationId());
		user.set("userCode", sysUser.getUserCode());
		user.set("rightDepart",departService.get(sysUser.getBussDepart()).getDepartName() );
		user.set("bussDepart", sysUser.getBussDepart());
        HttpServletRequest req=Struts2Utils.getRequest();
    	WebRalasafe.setCurrentUser(req, user);

		return "更改成功";
	}

	public SysUser findUniqueUserByUserName(String userName) throws Exception {
		SysUser user = null;
		List<SysUser> list =find("from SysUser s where s.userName=? and s.status=1 and s.workstatus=1 ",userName);  //必须是未删除状态(status),在职状态(workstatus)
		if(null!=list && list.size()>0){
			if(list.size()>1){
				throw new ServiceException("该用户名称存在重复，请维护！");
			}
			user = list.get(0);
		}
		return user;
	} 
}
