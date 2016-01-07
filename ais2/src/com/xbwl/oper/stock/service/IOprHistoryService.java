package com.xbwl.oper.stock.service;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.OprFaxIn;
import com.xbwl.entity.OprHistory;
import com.xbwl.entity.OprNode;
import com.xbwl.rbac.entity.SysUser;

/**
 * @author CaoZhili time Jul 21, 2011 9:58:15 AM
 * 
 * ��ʷ����������¼�����ӿ�
 */
public interface IOprHistoryService extends IBaseService<OprHistory, Long> {

	/**
	 * ��Ʊ������־
	 * @param dno
	 * @param ms
	 * @param node
	 * @return
	 * @throws Exception
	 */
	public OprHistory saveLog(Long dno,String ms,OprNode node)throws Exception;
	
	/**��Ʊ������־
	 * @param dno
	 * @param ms
	 * @param node
	 * @return
	 * @throws Exception
	 */
	public OprHistory[] saveLog(Long[] dno,String ms,OprNode node)throws Exception;

	/**
	 * @param dno
	 * @param ms
	 * @param node
	 * @return
	 * @throws Exception
	 */
	public OprHistory[] saveLog(Long[] dno,String[] ms,OprNode node)throws Exception;
	
	
	/**��Ʊ������־
	 * @param dno
	 * @param ms
	 * @param node
	 * @return
	 * @throws Exception
	 */
	public OprHistory saveLog(Long dno,String ms,Long nodeId)throws Exception;
	
	/**��Ʊ������־
	 * @param dno
	 * @param ms
	 * @param node
	 * @return
	 * @throws Exception
	 */
	public OprHistory[] saveLog(Long[] dno,String ms,Long nodeId)throws Exception;
	
	/**��Ʊ������־
	 * @param dno
	 * @param ms
	 * @param node
	 * @return
	 * @throws Exception
	 */
	public OprHistory[] saveLog(Long[] dno,String[] ms,Long nodeId)throws Exception;
	
	
	/**��Ʊ������־
	 * @param fax
	 * @param ms
	 * @param nodeId
	 * @return
	 * @throws Exception
	 */
	public OprHistory saveLog(OprFaxIn fax,String ms,Long nodeId)throws Exception;
	
	/**ͨ�����͵��Ų�ѯ��ʷ������־
	 * @param dno ���͵���
	 * @return ��־��ѯSQL
	 * @throws Exception
	 */
	public String findHistoryByDno(Long dno) throws Exception;
	
	/**
	 * ���������־
	 * @param dno
	 * @param ms
	 * @param nodeId
	 * @return
	 * @throws Exception
	 */
	public OprHistory saveFiLog(Long dno, String ms, Long nodeId) throws Exception;
	
	/**
	 * ��ת��˾��־����
	 * @param oprHistory
	 * @throws Exception
	 */
	public void saveTransitLog(OprHistory oprHistory)throws Exception;
	
	/**
	 * ͨ���û����������־
	 * @param dno
	 * @param user
	 * @throws Exception
	 */
	public void saveLogByUser(Long dno,String msg,Long nodeId,SysUser user)throws Exception;
}
