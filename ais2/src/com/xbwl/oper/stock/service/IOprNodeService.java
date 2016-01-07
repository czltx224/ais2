package com.xbwl.oper.stock.service;

import java.util.List;
import java.util.Map;

import com.xbwl.common.orm.Page;
import com.xbwl.common.orm.PropertyFilter;
import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.OprNode;

/**
 * author shuw
 * time Aug 9, 2011 8:41:36 AM
 */
public interface IOprNodeService extends IBaseService<OprNode, Long>{

	/**
	 * 综合查询获取部分节点类型，查询SQL
	 * @return
	 * @throws Exception
	 */
	public String getListOfQuery(Map<String,String> map) throws Exception;
}
