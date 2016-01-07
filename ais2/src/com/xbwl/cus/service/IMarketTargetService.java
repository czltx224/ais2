package com.xbwl.cus.service;

import java.util.Date;
import java.util.List;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.MarketingTarget;

/**
 *@author LiuHao
 *@time Dec 20, 2011 6:03:29 PM
 */
public interface IMarketTargetService extends IBaseService<MarketingTarget,Long> {
	/**
	 * ָ���ѯ
	 * @param targetType
	 * @param countDate
	 * @return
	 * @throws Exception
	 */
	public List findMargetTargetMsg(String countArea,String targetType,Date countDate,Long isTarget)throws Exception;
	/**
	 * ��ѯָ����ָ��Ŀͷ���������
	 * @author LiuHao
	 * @time Jun 21, 2012 2:17:57 PM 
	 * @return
	 * @throws Exception
	 */
	public List findTargetDepartCode(Long departId)throws Exception;
}
