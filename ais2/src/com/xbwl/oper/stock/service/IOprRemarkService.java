package com.xbwl.oper.stock.service;

import java.util.List;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.OprRemark;

/**
 * @author CaoZhili time Jul 19, 2011 4:12:24 PM
 * 
 * ��ע��¼�����ӿ�
 */
public interface IOprRemarkService extends IBaseService<OprRemark, Long> {
	
	/**
	 * �ۺϲ�ѯ��Ʊ���һ����ע
	 * @param dnos ���͵��ż���	
	 * @param remark	��ע
	 * @throws Exception
	 */
	public void saveRemarks(List<Long>dnos,String remark) throws Exception;

	/**��ע���淽��
	 * @param dno ���͵���
	 * @param remark ��ע
	 * @throws Exception �쳣
	 */
	public void saveRemark(Long dno,String remark) throws Exception;
}
