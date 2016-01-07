package com.xbwl.cus.service;

import java.util.Date;
import java.util.List;

import com.xbwl.common.orm.Page;
import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.CusAnalyse;

/**
 *@author LiuHao
 *@time Dec 17, 2011 11:11:00 AM
 */
public interface ICusAnalyseService extends IBaseService<CusAnalyse,Long> {
	/**
	 * �¶ȿͻ��ȼ�����
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public Page findCusRankMsg(Page page,Date startDate,Date endDate ,String dateType)throws Exception;
	/**
	 * �ͻ��ȼ����ͳ��
	 * @param countType
	 * @param countDate
	 * @param departId
	 * @return
	 * @throws Exception
	 */
	public Page findCusMonRankMsg(Page page,String countType,String startCount,String endCount,Long departId,String countRange,Long cusRecordId)throws Exception;
	/**
	 * �ͻ��Ƚ�
	 * @param countType
	 * @param startDate
	 * @param endDate
	 * @param cusType
	 * @return
	 * @throws Exception
	 */
	public Page findCusRankThan(Page page,String countType,Date startDate,Date endDate,String cusType)throws Exception;
	/**
	 * �ͷ�Ա�ͻ��Ա�
	 * @author LiuHao
	 * @time Apr 6, 2012 3:00:22 PM 
	 * @param countType
	 * @param startDate
	 * @param endDate
	 * @param cusService
	 * @return
	 * @throws Exception
	 */
	public Page findCusVsCus(Page page,String countType,Date startDate,Date endDate,String cusService)throws Exception;
}
