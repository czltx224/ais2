package com.xbwl.finance.Service;

import java.io.File;
import java.util.Date;
import java.util.List;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.orm.Page;
import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.BasCqStCorporateRate;

/**
 *@author LiuHao
 *@time 2011-7-21 ����11:36:38
 */
public interface ICqStCorporateRateService extends IBaseService<BasCqStCorporateRate, Long> {

	/**
	 * @param ids �����׼Э����ַ���ID����
	 * @param status Ҫ�޸ĵ� �����׼Э��� ״̬
	 * @throws ServiceException ������쳣 
	 */
	public void updateStatusService(String[] ids,Long status) throws Exception;
	/**
	 * ��ѯ������
	 * @param page
	 * @param trafficMode
	 * @param takeMode
	 * @param distributionMode
	 * @param addressType
	 * @param startCity
	 * @param city
	 * @param town
	 * @param street
	 * @param departId
	 * @return
	 * @throws Exception
	 */
	public Page<BasCqStCorporateRate> findCqStCorRate(Page page,String trafficMode,String takeMode,String distributionMode,String addressType,String startCity,String city,String town,String street,Long departId)throws Exception;
	
	/**
	 * �ж��Ƿ��������Э��۸�
	 * @param cqStCorporateRate
	 * @return
	 */
	public boolean allowSaveService(BasCqStCorporateRate cqStCorporateRate);
	/**
	 * �������ɴ���Э���
	 * @param cqId
	 * @param cusId
	 * @param cpName
	 * @param rebate
	 * @param startTime
	 * @param endTime
	 * @throws Exception
	 */
	public void discountCqRate(List cqId,Long cusId,String cpName,Double rebate,Date startTime,Date endTime)throws Exception;
	
	
	/**
	 * ����Excel����Э���
	 * @param excelFile
	 * @param fileNameString
	 * @throws Exception
	 */
	public void saveExcelOfExcel(File excelFile,String fileNameString) throws Exception;
}
