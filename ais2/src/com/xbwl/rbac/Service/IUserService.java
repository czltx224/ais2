package com.xbwl.rbac.Service;

import java.util.List;

import com.xbwl.common.orm.Page;
import com.xbwl.common.service.IBaseService;
import com.xbwl.rbac.entity.SysUser;

public interface IUserService extends IBaseService<SysUser,Long> {
	
	// �û���ɾ����
	/**�û����ɾ������
	 * @param id
	 * @throws Exception
	 */
	public void deleteStatusById(List<Long> id) throws Exception;
	
	/**
	 * �л��û�
	 * @param userId �û�ID
	 * @return
	 * @throws Exception
	 */
	public String changeUser(Long userId) throws  Exception;
	
	/**
	 * ��ѯһ���û��Ķ��˺� ͨ�����Ź���
	 * @param userId �û�ID
	 * @return
	 * @throws Exception
	 */
	public List<SysUser> getUserlist(Long  userId) throws Exception;
	
	/**
	 * �û��޸�����
	 * @param userId  �ύ�������û�ID
	 * @param password1  ����1 ������
	 * @param password2 ����2 ������
	 * @param password3 ȷ��������
	 * @param String ts  ʱ���
	 * @return
	 * @throws Exception
	 */
	public String savePassword(Long  userId,String password1,String password2,String password3,String ts) throws Exception;
	
	/**�����û�ID��ȡ�û���ɫ
	 * @param userId
	 * @return
	 */
	public Page getUserRoleByUserId(String userId);
	
	/**
	 * ͨ���û����ƻ�ȡΨһ�û�
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	public SysUser findUniqueUserByUserName(String userName)throws Exception;
	
}
