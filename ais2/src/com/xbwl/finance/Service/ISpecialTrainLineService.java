package com.xbwl.finance.Service;

import java.util.Map;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.BasSpecialTrainLine;

/**
 *@author LiuHao
 *@time 2011-7-22 ����02:58:07
 */
public interface ISpecialTrainLineService extends IBaseService<BasSpecialTrainLine, Long> {

	/**
	 * �ж�ר��·���Ƿ����ɾ������
	 * @param ids Ҫɾ����ID����
	 * @return �����Ƿ����ɾ��
	 * @throws Exception ������׳��쳣
	 */
	public boolean getIsNotDeleteService(String[] ids) throws Exception;

	/**
	 * ����ר����·��ѯSQL
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String findListService(Map<String, String> map)throws Exception;

}
