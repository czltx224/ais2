package com.xbwl.finance.Service;

import java.util.Map;

import com.xbwl.common.orm.Page;
import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FiIncome;
import com.xbwl.finance.vo.FiIncomeBalanceVo;

/**
 *@author LiuHao
 *@time Aug 26, 2011 6:34:26 PM
 */
public interface IFiIncomeService extends IBaseService<FiIncome,Long> {

	/**
	 * �������
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String getTotalFiInIncome(Map map) throws Exception;


	/**
	 * ӯ��ƽ�ⱨ��
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public String getAllincomeBalanceVo(FiIncomeBalanceVo vo) throws Exception;
	
	/**
	 * ���ɲ������뽻�˱���
	 * @param departId ҵ����ID
	 * @param startTime ��ʼʱ��
	 * @param endTime ����ʱ��
	 * @param seq ���˵���
	 * @throws Exception
	 */
	public void addAccountSingle(Long departId,String startTime,String endTime,Long seq) throws Exception;
	
	/**
	 * ���ݽ��˵��Ų�ѯ������ϸ
	 * @param map
	 */
	public Page<FiIncome> findFiIncomeDetail(Page page,Map map) throws Exception;
}
