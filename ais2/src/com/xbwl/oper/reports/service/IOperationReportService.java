package com.xbwl.oper.reports.service;

import java.util.Map;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.OprLoadingbrigadeWeight;

/**
 * author CaoZhili
 * time Sep 30, 2011 9:38:30 AM
 */

public interface IOperationReportService  extends IBaseService<OprLoadingbrigadeWeight,Long>{

	/**��ȡ�ͻ�������ѯSQL
	 * @param map ��������
	 * @return ��ѯSQL
	 * @throws Exception
	 */
	public String getSendGoodsService(Map<String, String> map)throws Exception;
	
	/**��ȡ�ͻ�������ϸ��ѯSQL
	 * @param map ��������
	 * @return ��ѯSQL
	 * @throws Exception
	 */
	public String getSendGoodsDetailService(Map<String, String> map)throws Exception;

	/**��ȡж��ʱЧ�����ѯSQL
	 * @param map  ��������
	 * @return ��ѯSQL
	 * @throws Exception
	 */
	public String getUnloadingTimeListService(Map<String, String> map)throws Exception;

	/**��ȡж��ʱЧ������ϸ��ѯSQL
	 * @param map ��������
	 * @return ��ѯSQL
	 * @throws Exception
	 */
	public String getUnloadingTimeDetailListService(Map<String, String> map) throws Exception;

	/**�ͻ�������ϸ����ͳ��
	 * @param map
	 * @throws Exception
	 */
	public String findSendGoodsDetailCount(Map<String, String> map)throws Exception;

	/**ƴ���ͻ�ӯ������ͳ�Ʋ�ѯSQL
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String findSendGoodsProfitsService(Map<String, String> map)throws Exception;
	
	/**
	 * ��ת����ͳ�Ʋ�ѯSQL��ȡ
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String findTransitCountService(Map<String,String> map)throws Exception;
	
	
	/**
	 * ��ת��ϸ��ѯSQL��ȡ
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String findTransitDetailFindService(Map<String,String> map)throws Exception;

	/**
	 * ��ת����ͳ�Ʋ�ѯ�ϼ�SQL��ȡ
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String findTransitCountTotalService(Map<String, String> map)throws Exception;

	/**
	 * ��ת��ϸ��ѯ�ϼ�SQL��ȡ
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String findTransitDetailTotalService(Map<String, String> map)throws Exception;

	/**
	 * EDI����ʱЧ��ѯSQL��ȡ
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String findEdiAgingQueryService(Map<String, String> map)throws Exception;

	/**
	 * EDI����ʱЧ����ͳ�Ʋ�ѯSQL��ȡ
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String findEdiAgingCountService(Map<String, String> map)throws Exception;

	/**
	 * EDI����ʱЧ��ѯ�ϼ�SQL��ȡ
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String findEdiAgingQueryTotalService(Map<String, String> map)throws Exception;

	/**
	 * EDI����ʱЧ���ܺϼ�SQL��ȡ
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String findEdiAgingCountTotalService(Map<String, String> map)throws Exception;

}
