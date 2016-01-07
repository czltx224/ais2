package com.xbwl.finance.Service;

import java.io.File;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.orm.Page;
import com.xbwl.common.service.IBaseService;
import com.xbwl.cus.vo.ReportSerarchVo;
import com.xbwl.entity.BasCqCorporateRate;
import com.xbwl.finance.vo.FaxrateSerarchVo;

/**
 *@author LiuHao
 *@time 2011-7-21 ����11:21:53
 */
public interface ICqCorporateRateService extends IBaseService<BasCqCorporateRate, Long> {
	
	
	/**
	 * ����Э��۱���Excel
	 * @param excle        excel�ļ�
	 * @param excelName         �ļ���
	 * @throws Exception
	 */
	public void saveExcelOfExcel(File excle ,String excelName) throws Exception;

	/**
	 * @param ids ����Э����ַ���ID����
	 * @param status Ҫ�޸ĵ� ����Э��� ״̬
	 * @throws ServiceException ������쳣 
	 */
	public void updateStatusService(String[] ids,Long status) throws Exception;

	/**
	 * ͨ��
	 * @param basCqCorporateRate ����Э���ʵ�����
	 * @return �ۿۼ�
	 * @throws ServiceException ������쳣
	 */
	public Double getDiscountService(BasCqCorporateRate basCqCorporateRate)throws Exception;
	/**
	 * ��ѯ����Э��۸�
	 * @param page
	 * @param trafficMode ���䷽ʽ
	 * @param takeMode �����ʽ
	 * @param distributionMode ���ͷ�ʽ
	 * @param addressType ��ַ����
	 * @param startCity ʼ��վ
	 * @param city ��
	 * @param town ��
	 * @param street ��/�ֵ�
	 * @param departId
	 * @return
	 * @throws Exception
	 */
	public Page<BasCqCorporateRate> findCqCorRate(Page page,String trafficMode,String takeMode,String distributionMode,String addressType,String startCity,String city,String town,String street,String valuationType,Long cusId,Long departId)throws Exception;

	/**
	 * �ж��Ƿ��������ظ���Э���
	 * @param cqCorporateRate
	 * @return
	 */
	public boolean allowSaveService(BasCqCorporateRate cqCorporateRate);
	/**
	 * �۸��ѯ
	 * @author LiuHao
	 * @time Apr 26, 2012 10:47:38 AM 
	 * @param page
	 * @param rateSearchVo
	 * @return
	 * @throws Exception
	 */
	public Page findRate(Page page,FaxrateSerarchVo rateSearchVo)throws Exception;
}
