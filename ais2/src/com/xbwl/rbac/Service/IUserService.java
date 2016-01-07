package com.xbwl.rbac.Service;

import java.util.List;

import com.xbwl.common.orm.Page;
import com.xbwl.common.service.IBaseService;
import com.xbwl.rbac.entity.SysUser;

public interface IUserService extends IBaseService<SysUser,Long> {
	
	// 用户软删方法
	/**用户软件删除方法
	 * @param id
	 * @throws Exception
	 */
	public void deleteStatusById(List<Long> id) throws Exception;
	
	/**
	 * 切换用户
	 * @param userId 用户ID
	 * @return
	 * @throws Exception
	 */
	public String changeUser(Long userId) throws  Exception;
	
	/**
	 * 查询一个用户的多账号 通过工号关联
	 * @param userId 用户ID
	 * @return
	 * @throws Exception
	 */
	public List<SysUser> getUserlist(Long  userId) throws Exception;
	
	/**
	 * 用户修改密码
	 * @param userId  提交过来的用户ID
	 * @param password1  密码1 旧密码
	 * @param password2 密码2 新密码
	 * @param password3 确认新密码
	 * @param String ts  时间戳
	 * @return
	 * @throws Exception
	 */
	public String savePassword(Long  userId,String password1,String password2,String password3,String ts) throws Exception;
	
	/**根据用户ID获取用户角色
	 * @param userId
	 * @return
	 */
	public Page getUserRoleByUserId(String userId);
	
	/**
	 * 通过用户名称获取唯一用户
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	public SysUser findUniqueUserByUserName(String userName)throws Exception;
	
}
