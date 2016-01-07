package com.xbwl.sys.service;

import java.util.Map;

import com.gdcn.bpaf.common.exception.ServiceException;
import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.BasDictionary;

/**
 * @author Administrator
 *数据字典接口
 */
public interface IBasDictionaryService extends IBaseService<BasDictionary,Long>{

	/**
	 * 拼接SQL用来重写ralaList方法，去除权限
	 * @param map
	 * @return 拼接后的字符串
	 * @throws ServiceException
	 */
	public String findOverRalaList(Map<String, String> map)throws ServiceException;

}
