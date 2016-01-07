package com.xbwl.finance.Service;

import java.util.List;

import com.xbwl.common.orm.Page;
import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.BasStSpecialTrainRate;

/**
 *@author LiuHao
 *@time 2011-7-22 ����10:35:37
 */
public interface IStSpecialTrainRateService extends IBaseService<BasStSpecialTrainRate, Long> {
	/**
	 * ��ѯר�� ����Э��۸�
	 * @param page
	 * @param roadType
	 * @param town
	 * @param street
	 * @return
	 * @throws Exception
	 */
	public Page findStSpecialTrainRate(Page page,String roadType,String town,String street,Long departId,String city)throws Exception;
	/**
	 * ��������ר��Э���
	 * @param stId
	 * @param cusId
	 * @param rebate
	 * @throws Exception
	 */
	public void discountSpeRate(List stId,Long cusId,String cpName,Double rebate)throws Exception;
}
