package com.xbwl.rbac.Service;

import java.util.List;
import java.util.Map;

import com.gdcn.bpaf.common.exception.ServiceException;
import com.xbwl.common.orm.Page;
import com.xbwl.common.orm.PropertyFilter;
import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.SysDepart;

/**
 *author LiuHao
 *time Jun 9, 2011 2:12:39 PM
 */
public interface IDepartService extends IBaseService<SysDepart,Long> {
	
	/**根据部门ID，查找下面部门树
	 * @param id
	 * @return
	 */
	public String getDepartTreeById(Long id);
	//根据上级部门编号查询部门信息
	public Page<SysDepart> getDepartByParentId(Page page,Long parentId) throws Exception;
	//查询所有部门信息
	public Page<SysDepart> findAllDepart(Page page,List<PropertyFilter> filter)throws Exception;
	/**
	 * 根据岗位ID查询部门信息
	 * @author LiuHao
	 * @time Feb 29, 2012 4:13:32 PM 
	 * @param page
	 * @param stationId
	 * @return
	 * @throws Exception
	 */
	public Page<SysDepart> findDepartBySationId(Page page,Long stationId)throws Exception;
	/**
	 * 根据部门编码查询部门信息
	 * @author LiuHao
	 * @time Feb 29, 2012 4:14:28 PM 
	 * @param departNo
	 * @return
	 * @throws Exception
	 */
	public SysDepart getDepartByDepartNo(String departNo)throws Exception;
	
	/**
	 * 查询部门名称和ID
	 * @param map
	 * @return
	 * @throws ServiceException
	 */
	public String findDepartService(Map<String, String> map)throws ServiceException;
	/**
	 * 查询部门编码为空的部门信息
	 * @author LiuHao
	 * @time Mar 23, 2012 11:59:47 AM 
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	public List getMaxDepartno(Long parentId)throws Exception;
	
}
