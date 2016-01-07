package com.xbwl.finance.Service;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FiInternalDivide;

/**
 * author CaoZhili time Oct 21, 2011 4:30:35 PM
 * �ڲ����㻮�ַ��ʲ�ӿ�
 */

public interface IFiInternalDivideService extends
		IBaseService<FiInternalDivide, Long> {

	/**�ڲ����㻮����˷���
	 * @param fiInternalDivide Ҫ��˵�ʵ�����
	 * @throws Exception ������쳣
	 */
	public void auditService(FiInternalDivide fiInternalDivide)throws Exception;

	/**�ڲ����㻮�ֳ�����˷���
	 * @param strIds Ҫ������ID����
	 * @throws Exception ������쳣
	 */
	public void cancelAuditService(String[] strIds)throws Exception;

	/**�ڲ����㻮�����Ϸ���
	 * @param strIds Ҫ���ϵ�ID����
	 * @throws Exception ������쳣
	 */
	public void invalidService(String[] strIds) throws Exception;

}
