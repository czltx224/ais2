package com.xbwl.finance.Service;

import java.io.File;
import java.util.List;

import org.ralasafe.user.User;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FiCost;
import com.xbwl.entity.FiDeliverycost;

/**
 * author shuw
 * time Oct 8, 2011 5:46:10 PM
 */

public interface IFiDeliverycostService extends IBaseService<FiDeliverycost,Long> {

	
	/**
	 * ����ɱ�����ģ�� ������
	 * @param list
	 * @throws Exception
	 */
	public void saveFiAudit (List<FiDeliverycost> list,User user) throws  Exception ;
	
	/**
	 * ����Excel����
	 * @param excelFile
	 * @param fileName
	 * @throws Exception
	 */
	public String saveFiExcel(File excelFile,String fileName) throws Exception;
	
	/**
	 * ȡ���ֹ�ƥ��
	 * @param id
	 * @param ts
	 * @return
	 * @throws Exception
	 */
	public String qxAudit(List<FiDeliverycost>aa) throws Exception;

	/**
	 * �����ֹ�ƥ��
	 * @param fiDeliverycost
	 * @throws Exception
	 */
	public void saveMat(List<FiDeliverycost>aa) throws Exception;
	
	/**
	 * �������
	 * @param aa
	 * @param ts
	 * @throws Exception
	 */
	public void saveQxFiAudit(List<FiDeliverycost> aa ) throws Exception;
	

	/**
	 * ���ݶ��˵��Ÿ��¸���״̬Ϊ��1��֧��
	 * @param batchNo ���˵���
	 * @throws Exception
	 */
	public void payConfirmationBybatchNo(Long batchNo) throws Exception;
	
	/**
	 * ���ݶ��˵��ų�������״̬Ϊ��0��֧��
	 * @param batchNo ���˵���
	 * @throws Exception
	 */
	public void payConfirmationRegisterBybatchNo(Long batchNo) throws Exception;
}
