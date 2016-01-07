package com.xbwl.oper.reports.service;

import java.util.Map;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.OprEnterPortKpi;

/**
 * author CaoZhili
 * time Nov 10, 2011 1:47:08 PM
 */

public interface IOprEnterPortKpiService  extends IBaseService<OprEnterPortKpi, Long> {

	/**KPIͳ�Ʊ���
	 * @param map ��������
	 * @return ��ѯSQL
	 * @throws Exception ������쳣
	 */
	public String findKpiReportService(Map<String, String> map)throws Exception;

}
