package com.xbwl.sys.service;

import java.util.List;

import org.apache.poi.hssf.record.formula.functions.T;

import com.xbwl.common.orm.Page;
import com.xbwl.common.orm.PropertyFilter;
import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.OprExceptionType;
import com.xbwl.entity.SysCommonMenu;

/**
 * author shuw
 * time Mar 1, 2012 3:23:20 PM
 * ϵͳ���ò˵� 
 */
public interface ISysCommonMenuService extends IBaseService<SysCommonMenu, Long>{

	/**
	 * �����û����ò˵����ϣ�����֤Ȩ��
	 * @param list
	 * @throws Exception
	 */
	public void saveList(List<SysCommonMenu> list) throws Exception;
	
	/**
	 * �û���ѯ���÷�������Ҫ���ڲ�ѯǰȥ���û�����Ȩ�޵Ĳ˵�
	 * @param page
	 * @param list ��������
	 * @return
	 * @throws Exception
	 */
	public Page<SysCommonMenu> queryList(Page  page,List<PropertyFilter> list ) throws  Exception;
}
