package com.xbwl.oper.reports.service;

import java.util.Map;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.OprEnterPortReport;

/**
 * author CaoZhili
 * time Nov 9, 2011 4:34:21 PM
 */

public interface IOprEnterPortReportService extends IBaseService<OprEnterPortReport, Long>{

	/**����ʱЧ�����ѯ����ȡ����
	 * @param map ��ѯ��������
	 * @return ��ѯSQL
	 * @throws Exception ������쳣
	 */
	public String findListService(Map<String, String> map)throws Exception;

	/**����ʱЧ�����ѯ����ȡ����
	 * @param map ��ѯ��������
	 * @return ��ѯSQL
	 * @throws Exception ������쳣
	 */
	public String findOutListService(Map<String, String> map)throws Exception;

	/**���߳�ʱЧ�����ѯ����ȡ
	 * @param map ��ѯ��������
	 * @return ��ѯSQL
	 * @throws Exception ������쳣
	 */
	public String findArteryCarListService(Map<String, String> map)throws Exception;

	/**ǩ��ʱЧ�����ѯ����ȡ
	 * @param map ��ѯ��������
	 * @return ��ѯSQL
	 * @throws Exception ������쳣
	 */
	public String findSignListService(Map<String, String> map) throws  Exception;

	/**�ص�ȷ��ʱЧ�����ѯ����ȡ
	 * @param map ��ѯ��������
	 * @return ��ѯSQL
	 * @throws Exception ������쳣
	 */
	public String findReceiptComfirmListService(Map<String, String> map) throws Exception;

	/**ɨ��ʱЧ�����ѯ����ȡ
	 * @param map ��ѯ��������
	 * @return ��ѯSQL
	 * @throws Exception ������쳣
	 */
	public String findScanningListService(Map<String, String> map)throws Exception;

	/**����װ���ʱ����ѯ����ȡ
	 * @param map ��ѯ��������
	 * @return ��ѯSQL
	 * @throws Exception ������쳣
	 */
	public String findCarLoadingRateListService(Map<String, String> map) throws Exception;

	/**���߳�ͳ�Ʊ����ѯ����ȡ
	 * @param map ��ѯ��������
	 * @return ��ѯSQL
	 * @throws Exception ������쳣
	 */
	public String findArteryCarCountService(Map<String, String> map)throws Exception;

}
