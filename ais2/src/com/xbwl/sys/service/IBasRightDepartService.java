package com.xbwl.sys.service;

import java.util.Map;

import com.gdcn.bpaf.common.exception.ServiceException;
import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.BasRightDepart;

public interface IBasRightDepartService extends IBaseService<BasRightDepart, Long> {

	/**查找权限部门
	 * @param map
	 * @return
	 * @throws ServiceException
	 */
	public String findDepartName(Map<String, String> map)throws ServiceException;

}
