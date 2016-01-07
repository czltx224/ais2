package com.xbwl.cus.service;

import java.util.List;
import java.util.Map;

import org.ralasafe.user.User;

import com.xbwl.common.orm.Page;
import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.CusRecord;

/**
 *@author LiuHao
 *@time Oct 8, 2011 1:49:24 PM
 */
public interface ICusRecordService extends IBaseService<CusRecord,Long> {

	/**获取客户列表查询SQL
	 * @param map 条件集合
	 * @return 查询SQL
	 * @throws Exception
	 */
	public String findCustomerListService(Map<String, String> map) throws Exception;

	/**
	 * 停用客户方法
	 * @param strIds 需要修改状态的客户ID字符串数组
	 * @throws Exception
	 */
	public void stopCustomerService(String[] strIds)throws Exception;

	/**获取分配客户查询SQL
	 * @param map 条件集合
	 * @return 查询SQL
	 * @throws Exception
	 */
	public String findDistributionCustomerService(Map<String, String> map)throws Exception;

	/**把客户分配到部门
	 * @param idStrings 客户ID数组
	 * @param departId 部门ID
	 * @throws Exception 服务层异常
	 */
	public void referToDepartService(String[] idStrings, Long departId)throws Exception;

	/**查询表的备注
	 * @param map 条件集合,必须包含tableName
	 * @return 查询SQL
	 * @throws Exception
	 */
	public String findTableRemark(Map<String, String> map)throws Exception;

	/**客户列表自定义查询
	 * @param map 查询条件集合
	 * @return 查询SQL
	 * @throws Exception
	 */
	public String customSearchService(Map<String, String> map)throws Exception;
	/**
	 * 收货人转客户  回写收货人信息表
	 * @param cusRecord
	 * @return
	 * @throws Exception
	 */
	/*
	public boolean saveCusAndCon(CusRecord cusRecord,User user)throws Exception;
	*/
	/**
	 * 查询录单时发货代理信息
	 * @return
	 * @throws Exception
	 */
	public List findFaxcus(Long departId,String cusName)throws Exception;
	/**
	 * 新增客户
	 * @param cusRecord
	 * @param user
	 * @throws Exception
	 */
	public void saveCusRecord(CusRecord cusRecord,User user,String conType)throws Exception;
	/**
	 * 查询预警客户
	 * @author LiuHao
	 * @time May 10, 2012 4:33:48 PM 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public Page findWarnCus(Page page,Long cusRecordId,String cusService)throws Exception;

	/**
	 * 启用客户方法
	 * @param strIds 需要启用的客户数组
	 * @throws Exception
	 */
	public void startCustomerService(String[] strIds)throws Exception;
}
