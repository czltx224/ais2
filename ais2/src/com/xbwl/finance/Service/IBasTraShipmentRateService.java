package com.xbwl.finance.Service;

import java.io.File;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.BasTraShipmentRate;
import com.xbwl.common.orm.Page;

/**
 * @author CaoZhili time Aug 4, 2011 10:22:04 AM
 * 
 * ��תЭ��۷����ӿ�
 */
public interface IBasTraShipmentRateService extends
		IBaseService<BasTraShipmentRate, Long> {

	/**
	 * @param ids ��Ҫ�޸ĵ���תЭ���ID����
	 * @param status  ��Ҫ�޸ĵ�״̬
	 * @throws Exception ������쳣
	 */
	public void updateStatusService(String[] ids, Long status) throws Exception;

	/**
	 * �ж��Ƿ�������ר��Э���
	 * @param basTraShipmentRate
	 * @return
	 */
	public boolean allowSaveService(BasTraShipmentRate basTraShipmentRate);
	/**
	 * ��ѯ��תЭ���
	 * @author LiuHao
	 * @time Apr 26, 2012 5:06:10 PM 
	 * @param page
	 * @param cusName
	 * @param trafficMode
	 * @param takeMode
	 * @param addrType
	 * @param disdepartId
	 * @param valuationType
	 * @return
	 * @throws Exception
	 */
	public Page findTraRate(Page page,String cusName,String trafficMode,String takeMode,String addrType,Long disdepartId,String valuationType,String speTown)throws Exception;

	/**
	 * �ϴ�Excle ������Э���
	 * @param file
	 * @param fileName
	 * @throws Exception
	 */
	public void saveExcelOfExcel(File file,String fileName)	throws Exception;
}
