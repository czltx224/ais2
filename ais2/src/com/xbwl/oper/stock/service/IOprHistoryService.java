package com.xbwl.oper.stock.service;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.OprFaxIn;
import com.xbwl.entity.OprHistory;
import com.xbwl.entity.OprNode;
import com.xbwl.rbac.entity.SysUser;

/**
 * @author CaoZhili time Jul 21, 2011 9:58:15 AM
 * 
 * 历史运作操作记录服务层接口
 */
public interface IOprHistoryService extends IBaseService<OprHistory, Long> {

	/**
	 * 单票保存日志
	 * @param dno
	 * @param ms
	 * @param node
	 * @return
	 * @throws Exception
	 */
	public OprHistory saveLog(Long dno,String ms,OprNode node)throws Exception;
	
	/**多票保存日志
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
	
	
	/**单票保存日志
	 * @param dno
	 * @param ms
	 * @param node
	 * @return
	 * @throws Exception
	 */
	public OprHistory saveLog(Long dno,String ms,Long nodeId)throws Exception;
	
	/**多票保存日志
	 * @param dno
	 * @param ms
	 * @param node
	 * @return
	 * @throws Exception
	 */
	public OprHistory[] saveLog(Long[] dno,String ms,Long nodeId)throws Exception;
	
	/**多票保存日志
	 * @param dno
	 * @param ms
	 * @param node
	 * @return
	 * @throws Exception
	 */
	public OprHistory[] saveLog(Long[] dno,String[] ms,Long nodeId)throws Exception;
	
	
	/**单票保存日志
	 * @param fax
	 * @param ms
	 * @param nodeId
	 * @return
	 * @throws Exception
	 */
	public OprHistory saveLog(OprFaxIn fax,String ms,Long nodeId)throws Exception;
	
	/**通过配送单号查询历史操作日志
	 * @param dno 配送单号
	 * @return 日志查询SQL
	 * @throws Exception
	 */
	public String findHistoryByDno(Long dno) throws Exception;
	
	/**
	 * 保存财务日志
	 * @param dno
	 * @param ms
	 * @param nodeId
	 * @return
	 * @throws Exception
	 */
	public OprHistory saveFiLog(Long dno, String ms, Long nodeId) throws Exception;
	
	/**
	 * 中转公司日志保存
	 * @param oprHistory
	 * @throws Exception
	 */
	public void saveTransitLog(OprHistory oprHistory)throws Exception;
	
	/**
	 * 通过用户保存货物日志
	 * @param dno
	 * @param user
	 * @throws Exception
	 */
	public void saveLogByUser(Long dno,String msg,Long nodeId,SysUser user)throws Exception;
}
