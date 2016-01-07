package com.xbwl.oper.stock.service;

import java.util.Date;
import java.util.List;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.OprValueAddFee;

/**
 *@author LiuHao
 *@time Sep 29, 2011 2:15:05 PM
 */
public interface IOprValueAddFeeService extends IBaseService<OprValueAddFee, Long>{
	/**
	 * ��ѯ�ı����ֵ�������ϸ
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public List<OprValueAddFee> findChangeAddFee(List<OprValueAddFee> list,Long dno)throws Exception;
	
	/**
	 * ��ֵ������¶�ͳ��
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public List findAddFeeMsg(Date date,String dateType,String departCode)throws Exception;
	/**
	 * ������ֵ�����ͳ��
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public List findDepartFeeMsg(Date date,String dateType)throws Exception;
	/**
	 * ��ֵ�����ͬ�ȡ�����                   
	 * @param beforeDate
	 * @param afterDate
	 * @return
	 * @throws Exception
	 */
	public List findFeeThan(Date beforeDate,Date afterDate)throws Exception;
}
