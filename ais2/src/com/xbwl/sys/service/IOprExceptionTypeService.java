package com.xbwl.sys.service;

import java.util.Map;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.OprExceptionType;

/**
 * author shuw
 * time Aug 17, 2011 9:24:13 AM
 */
public interface IOprExceptionTypeService  extends IBaseService<OprExceptionType, Long>{

	/**
	 * 返回异常里面的节点数据SQL
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public  String getExceTypeNodeSql(Map<String, String> map) throws Exception;
	
	/*
	public String getExceptionTreeById(Long node);
	
	public Page getExceptionByParentId(Page page,Long id);
	
	public Page findAllException(Page page , List  filter);
	
	public void saveExec(Long parentId,OprExceptionType oprExceptionType);*/
}
