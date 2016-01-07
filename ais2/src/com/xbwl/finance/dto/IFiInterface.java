package com.xbwl.finance.dto;

import java.util.List;

import com.xbwl.finance.dto.impl.FiInterfaceProDto;

public interface IFiInterface {

	/**
	 * @Title: ҵ����ò����ܽӿ�(ҵ���)
	 */
	public String addFinanceInfo(List<FiInterfaceProDto> listfiInterfaceDto)
			throws Exception;
	
	/**
	 * @Title: ���˵�����Ӧ��Ӧ���ӿ�
	 */
	public String reconciliationToFiPayment(List<FiInterfaceProDto> listfiInterfaceDto)throws Exception;
	
	/**
	 * @Title: ������ò����ܽӿ�
	 */
	public String outStockToFi(List<FiInterfaceProDto> listfiInterfaceDto)throws Exception;
	
	
	/**
	 *  @Title: ���ò������ֽ�ӿ�
	 */
	public String currentToFiPayment(List<FiInterfaceProDto> listfiInterfaceDto)throws Exception;
	
	/**
	 *  @Title: ����������ȷ�ϵ��ò����ڲ��ɱ��ӿ�
	 */
	public String internalCostToFi(List<FiInterfaceProDto> listfiInterfaceDto)throws Exception;
	
	
	/**
	 *  @Title:��������������ȷ��ʱ���������ڲ��ɱ��ӿ�
	 */
	public String invalidInternalCostToFi(List<FiInterfaceProDto> listfiInterfaceDto)throws Exception;
	
	
	/**
	 *  @Title: ����ȷ�ϣ��ֶ����������ò�������ɱ��ӿ�(����ɱ������ر�)
	 */
	public String storageToFiDeliverCost(List<FiInterfaceProDto> listfiInterfaceDto)throws Exception;
	
	/**
	 *  @Title: ����������ò���ӿ�
	 */
	public String changeToFi(List<FiInterfaceProDto> listfiInterfaceDto)throws Exception;
	

	/**
	 * ҵ����ò��������ܽӿ�
	 * @param listfiInterfaceDto
	 * @return ���ϵļ�¼��
	 * @throws Exception
	 */
	public int invalidToFi(List<FiInterfaceProDto> listfiInterfaceDto) throws Exception;
	
	/**
	 *  @Title: ����Ӧ��Ӧ���
	 */
	public int invalidToFiPayment(FiInterfaceProDto fiInterfaceProDto)throws Exception;
	
	/**
	 * �������͵�������Ӧ��Ӧ������
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public String invalidToFiPaymentByDno(FiInterfaceProDto fiInterfaceProDto)throws Exception;
	
	/**
	 * �������͵�������Ƿ����ϸ
	 * @param fiInterfaceProDto
	 * @return
	 * @throws Exception
	 */
	public String invalidToFiReceivabledetailByDno(FiInterfaceProDto fiInterfaceProDto)throws Exception;
	
	/**
	 *  @Title: ����Ƿ����ϸ
	 */
	public int invalidToFiReceivabledetail(FiInterfaceProDto fiInterfaceProDto)throws Exception;
	
	/**
	 *  @Title: ����Ԥ���
	 */
	public String invalidToFiAdvance(FiInterfaceProDto fiInterfaceProDto)throws Exception;
	
	/**
	 *  @Title: ���ĸ�����(ֻ������ɱ�����������)
	 */
	public String changePaymentAmount(List<FiInterfaceProDto> listfiInterfaceDto)throws Exception;
	
	/**
	 * ҵ��д�������ܽӿ�
	 * @param listfiInterfaceDto
	 * @return
	 * @throws Exception
	 */
	public String currentToFiIncome(List<FiInterfaceProDto> listfiInterfaceDto)throws Exception;
	
	/**
	 * ҵ��д��ɱ��ܽӿ�
	 * @param listfiInterfaceDto
	 * @return
	 * @throws Exception
	 */
	public String currentToFiFiCost(List<FiInterfaceProDto> listfiInterfaceDto)throws Exception;
	
	/**
	 * ��������Ӧ��Ӧ��
	 * @param listfiInterfaceDto
	 * @return
	 * @throws Exception
	 */
	public String oprReturnToFi(List<FiInterfaceProDto> listfiInterfaceDto)throws Exception;
	
	/**
	 * �����ɱ�д����ת�ɱ�
	 * @param listfiInterfaceDto
	 * @return
	 * @throws Exception
	 */
	public String oprReturnToFiTransitcost(List<FiInterfaceProDto> listfiInterfaceDto)throws Exception;
	
	/**
	 * �޸�����ɱ����
	 * @param listfiInterfaceDto(String sourceData:������Դ,Long sourceNo:��Դ����,double amount:�����ܽ��)
	 * @return
	 * @throws Exception
	 */
	public String revocationFiDeliveryCost(List<FiInterfaceProDto> listfiInterfaceDto)throws Exception;
	
	/**
	 * �����ⷢ�ɱ�
	 * @param listfiInterfaceDto
	 * @return
	 * @throws Exception
	 */
	//public String invalidOutCost(List<FiInterfaceProDto> listfiInterfaceDto)throws Exception;
	
}
