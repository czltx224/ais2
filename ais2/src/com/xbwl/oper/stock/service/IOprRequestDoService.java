package com.xbwl.oper.stock.service;

import java.util.List;
import java.util.Map;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.OprRequestDo;

/**
 * @author CaoZhili time Jul 12, 2011 10:22:54 AM
 * ���Ի�Ҫ������ӿ�
 */
public interface IOprRequestDoService extends IBaseService<OprRequestDo, Long> {

	/**��ȡ���Ի�Ҫ��ִ�������ѯ���
	 * @param filterParamMap
	 * @return
	 * @throws Exception
	 */
	public String getSqlRalaListService(Map<String, String> filterParamMap)throws Exception;
	/**
	 * ��ѯ���Ի�Ҫ��
	 * @author LiuHao
	 * @time Apr 16, 2012 3:39:29 PM 
	 * @param dno
	 * @return
	 * @throws Exception
	 */
	public List<OprRequestDo> getRequestByDno(Long dno)throws Exception;

}
