package com.xbwl.oper.receipt.service;

import java.util.List;
import java.util.Map;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.OprReceipt;
import com.xbwl.oper.receipt.vo.ReceiptConfirmVo;

/**
 * author CaoZhili time Jul 25, 2011 6:03:31 PM
 * 
 * �ص������ӿ�
 */
public interface IOprReceiptService extends IBaseService<OprReceipt, Long> {
	
	/**
	 * ��ȡ��ѯSQL
	 * @param map ���ϲ�ѯ����
	 * @return
	 * @throws Exception
	 */
	public StringBuffer getSqlRalaListService(Map<String,String> map)throws Exception;
	
	/**
	 * �ص����㵽
	 * @param oprReceipt OprReceipt ����
	 * @param id �ص�ID
	 * @throws Exception �����쳣
	 */
	public void saveReportService(OprReceipt oprReceipt,Long id) throws Exception;
	
	/**
	 * �ص�����
	 * @param oprReceipt OprReceipt ����
	 * @param ids id�ϳɵ��ַ���,�ö��Ÿ���
	 * @throws Exception �����쳣 
	 */
	public void saveGetService(OprReceipt oprReceipt, String ids) throws Exception;
	
	/**
	 * �ص��ĳ�
	 * @param oprReceipt OprReceipt ����
	 * @param dnos �ĳ���������
	 * @throws Exception �����쳣 
	 */
	public void saveOutService(OprReceipt oprReceipt,String[] dnos) throws Exception;

	/**��ȡͼƬ��ַ��ͼƬ���ڵ��ļ���
	 * @param dno ���͵���
	 * @param url ͼƬ·������
	 * @return
	 * @throws Exception
	 */
	
	public List<String> getImageUrlList(Long dno,String url) throws Exception;

	/**
	 * �����ص�ȷ�շ���
	 * @param split
	 * @throws Exception
	 */
	public void cancelConfirmService(String[] split)throws Exception;

	/**
	 * �����ص��ĳ�
	 * @param split
	 * @throws Exception
	 */
	public void cancelOutService(String[] split)throws Exception;

	/**
	 * �ص�ȷ��
	 * @param confirmVo
	 * @throws Exception
	 */
	public void saveConfirmService(List<ReceiptConfirmVo> confirmVoList)throws Exception;
	
	/**
	 * ɾ��ͼƬ
	 * @param split
	 * @throws Exception
	 */
	public void delImageUrl(Long dno,String numI)throws Exception;

}
