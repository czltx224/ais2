package com.xbwl.sys.service;

import java.util.Map;

import com.gdcn.bpaf.common.exception.ServiceException;
import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.BasDictionary;

/**
 * @author Administrator
 *�����ֵ�ӿ�
 */
public interface IBasDictionaryService extends IBaseService<BasDictionary,Long>{

	/**
	 * ƴ��SQL������дralaList������ȥ��Ȩ��
	 * @param map
	 * @return ƴ�Ӻ���ַ���
	 * @throws ServiceException
	 */
	public String findOverRalaList(Map<String, String> map)throws ServiceException;

}
