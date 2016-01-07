package com.xbwl.oper.stock.service;

import java.util.List;
import java.util.Map;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.OprPrewired;
import com.xbwl.oper.stock.vo.OprPrewiredVo;

/**
 * author shuw
 * time 2011-7-19 ����11:12:37
 */
public interface IOprPrewiredService extends IBaseService<OprPrewired, Long>{

	/**
	 * ʵ���ѯSQLƴ��
	 * @param filterMap ��ѯ����Filter
	 * @return  ��ѯSQL
	 * @throws Exception
	 */
	public String getAllDetail(Map filterMap) throws Exception;
	
	/**
	 * ʵ���ѯ��ר����SQLƴ��
	 * @param filterMap
	 * @return ��ѯSQL
	 * @throws Exception
	 */
	public String getAllDetailByCar(Map filterMap)throws Exception;
	
	/**
	 * ʵ����Ϣ����SQLƴ��
	 * @param ids
	 * @return  ��ѯSQL
	 */
	public String getTotalDetail(String ids);
	
	
	/**
	 * ȡ��Ԥ��
	 * @param list  Ԥ��id����
	 * @throws Exception
	 */
	public void deleteStatusByIds(List<Long>list ) throws Exception;
	
	/**
	 * ����ʵ�䱣��
	 * @param aa �ύ��vo����
	 * @param fileMap  �ύ��ʵ�����
	 * @param dnos   û��ʵ�������Ԥ��Id
	 * @return ʵ�䵥��
	 * @throws Exception
	 */
	public Long saveOvemByIds(List<OprPrewiredVo>aa,Map fileMap,String dnos) throws Exception;
	
	/**
	 * ʵ���ѯ�����Ž��ӣ�SQLƴ��
	 * @param filterMap ��ѯ����
	 * @return  ��ѯsql
	 * @throws Exception
	 */
	public String getAllDetailByDepartId(Map filterMap) throws Exception;
	
	/**
	 * ʵ�䣨��Ʊ��ӣ�SQLƴ��
	 * @param requestStage  ����
	 * @return
	 */
	public String getInfoByDno(String requestStage);
}
