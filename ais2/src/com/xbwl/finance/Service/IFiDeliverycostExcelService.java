package com.xbwl.finance.Service;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FiCost;
import com.xbwl.entity.FiDeliverycostExcel;

/**
 * author shuw
 * time Oct 22, 2011 10:55:11 AM
 */

public interface IFiDeliverycostExcelService extends IBaseService<FiDeliverycostExcel,Long>{

	
 
	/**
	 * �������
	 * @param batchNo  ���κ�
	 * @param cusName ��������
	 * @param cusId ����ID
	 * @param money �ܽ��
	 * @param code  ID��
	 * @return
	 * @throws Exception
	 */
	public String auditFi(String batchNo,String cusName,Long cusId,Double money,String code) throws Exception;
	
	/**
	 * ��ȡ���κţ�Ψһ��
	 * @param bussDepartId
	 * @return
	 * @throws Exception
	 */
	public Long getBatchNO(Long bussDepartId) throws Exception;
	
	/**
	 * �Զ�ƥ���˵�
	 * @param batchNo
	 * @return
	 * @throws Exception
	 */
	public String  compareStatus(String batchNo) throws Exception;
	
	/**
	 * �����˵����ɶ���٣�
	 * @param batchNo
	 * @return
	 * @throws Exception
	 */
	public String  updateFax(String batchNo) throws Exception;
	
	/**
	 * �����˵���ȫ����
	 * @param batchNo
	 * @return
	 * @throws Exception
	 */
	public String  updateAllFax(String batchNo) throws Exception;
	
	/**
	 * ����SQL
	 * @param batchNo
	 * @return
	 * @throws Exception
	 */
	public String  getTotalAmount(String batchNo) throws Exception;
	
	
}
