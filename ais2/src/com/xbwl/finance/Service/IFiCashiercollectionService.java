package com.xbwl.finance.Service;

import java.util.Map;

import org.ralasafe.user.User;
import org.springframework.stereotype.Service;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FiCashiercollection;

public interface IFiCashiercollectionService extends IBaseService<FiCashiercollection,Long> {
	/**
	* @Title: ��������տ 
	* @Description: TODO(1����������տʵ�壬2����¼�ʽ��˺���ˮ���ʽ��˺����.) 
	* @throws
	 */
	public void saveCashiercollection(FiCashiercollection fiCashiercollection) throws Exception;
	
	/**
	 * �ѵ����Excel���ݱ��浽�����տ��
	 * @param batchNo
	 * @throws Exception
	 */
	public void saveExcelData(Long batchNo) throws Exception;
	
	/**
	 * �������POS���ݱ��浽�����տ
	 * @param batchNo
	 * @throws Exception
	 */
	public void saveExcelPosData(Long batchNo) throws Exception;
	
	
	/**
	* @Title: ��������տ���� 
	* @param @param map request����
	 */
	public void saveVerification(Map map,User user) throws Exception;
	
	/**
	 * ���ϳ����տ
	 * @param fiCashiercollection
	 * @throws Exception
	 */
	public void invalidCashiercollection(FiCashiercollection fiCashiercollection) throws Exception;
	
	/**
	 * �ֹ�����
	 * @param map
	 * @param user
	 * @throws Exception
	 */
	public void manualVerification(Map map,User user) throws Exception;
}
