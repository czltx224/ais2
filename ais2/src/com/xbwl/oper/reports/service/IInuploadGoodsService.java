package com.xbwl.oper.reports.service;
import java.util.Map;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.OprLoadingbrigadeWeight;

/**
 *@author LiuHao
 *@time Sep 19, 2011 3:28:03 PM
 */
public interface IInuploadGoodsService extends IBaseService<OprLoadingbrigadeWeight,Long>{
	
	public String findInuploadGoods(Map<String, String> map) throws Exception;
	
	/**��ȡ�ֲ����������ѯSQL
	 * @param map ���϶��� Map<String, String>
	 * @return ƴ�Ӻ��SQL���
	 * @throws Exception
	 */
	public String getSeparateDialReportSQLService(Map<String, String> map)throws Exception;
}
